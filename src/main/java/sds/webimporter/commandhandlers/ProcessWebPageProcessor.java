package sds.webimporter.commandhandlers;

import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.npspot.jtransitlight.JTransitLightException;
import com.npspot.jtransitlight.consumer.ReceiverBusControl;
import com.npspot.jtransitlight.publisher.IBusControl;
import com.sds.storage.BlobStorage;
import com.sds.storage.Guid;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sds.messaging.callback.AbstractMessageProcessor;
import sds.webimporter.domain.commands.ProcessWebPage;
import sds.webimporter.domain.events.WebPageProcessFailed;
import sds.webimporter.domain.events.WebPageProcessed;
import sds.webimporter.wikitools.access.WikiResult;
import sds.webimporter.wikitools.access.Wikipedia;

@Component
public class ProcessWebPageProcessor extends AbstractMessageProcessor<ProcessWebPage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessWebPageProcessor.class);

    ReceiverBusControl receiver;
    IBusControl bus;
    BlobStorage storage;
    MongoDatabase db;

    @Autowired
    public ProcessWebPageProcessor(ReceiverBusControl receiver, IBusControl bus,
            BlobStorage storage, MongoDatabase db) throws JTransitLightException, IOException {
        this.bus = bus;
        this.receiver = receiver;
        this.storage = storage;
        this.db = db;
    }

    public void process(ProcessWebPage message) {

            try
            {
                byte[] content = null;
                Map<String,Object> meta = new HashMap<String,Object>();

                if (message.getUrl().toLowerCase().contains("wikipedia"))
                {
                    try
                    {
                        List<String> urls = new ArrayList<>();
                        urls.add(message.getUrl());
                        WikiResult wiki = Wikipedia.doIt(urls);
                        content = wiki.Content;
                        meta = wiki.Meta;
                    }
                    catch(Throwable e)
                    {

                    }
                }


                if (content != null && content.length != 0)
                {
                    Guid blobId = Guid.newGuid();
                    String fileName = blobId + ".json";
                    storage.addFile(blobId, fileName, new ByteArrayInputStream(content), "application/json", message.getBucket(), meta);

                    WebPageProcessed pageProcessed = new WebPageProcessed();
                    pageProcessed.setId(message.getId());
                    pageProcessed.setCorrelationId(message.getCorrelationId());
                    pageProcessed.setUserId(message.getUserId());
                    pageProcessed.setBlobId(blobId);
                    pageProcessed.setBucket(message.getBucket());
                    
                    bus.publish(pageProcessed);
                }
                else
                {
                    WebPageProcessed pageProcessed = new WebPageProcessed();
                    pageProcessed.setId(message.getId());
                    pageProcessed.setCorrelationId(message.getCorrelationId());
                    pageProcessed.setUserId(message.getUserId());
                    
                    bus.publish(pageProcessed);
                    
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                
                WebPageProcessFailed processFailed = new WebPageProcessFailed();
                processFailed.setId(message.getId());
                processFailed.setCorrelationId(message.getCorrelationId());
                processFailed.setUserId(message.getUserId());
                processFailed.setMessage(e.getMessage());
            }
        
    }

    private String getTimestamp() {
        //("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return LocalDateTime.now().toString();
    }

}
