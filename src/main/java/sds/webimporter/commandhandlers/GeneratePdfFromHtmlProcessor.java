package sds.webimporter.commandhandlers;

import com.mongodb.client.MongoDatabase;
import sds.webimporter.domain.commands.GeneratePdfFromHtml;
import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.npspot.jtransitlight.JTransitLightException;
import com.npspot.jtransitlight.consumer.ReceiverBusControl;
import com.npspot.jtransitlight.publisher.IBusControl;
import com.sds.storage.BlobInfo;
import com.sds.storage.BlobStorage;
import com.sds.storage.Guid;
import java.util.HashMap;
import java.util.UUID;
import sds.messaging.callback.AbstractMessageProcessor;
import sds.webimporter.convert.HtmlToPdf;
import sds.webimporter.domain.events.PdfGenerated;
import sds.webimporter.domain.events.PdfGenerationFailed;

@Component
public class GeneratePdfFromHtmlProcessor extends AbstractMessageProcessor<GeneratePdfFromHtml> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePdfFromHtmlProcessor.class);

    ReceiverBusControl receiver;
    IBusControl bus;
    BlobStorage storage;
    MongoDatabase db;

    @Autowired
    public GeneratePdfFromHtmlProcessor(ReceiverBusControl receiver, IBusControl bus,
            BlobStorage storage, MongoDatabase db) throws JTransitLightException, IOException {
        this.bus = bus;
        this.receiver = receiver;
        this.storage = storage;
        this.db = db;
    }

    public void process(GeneratePdfFromHtml message) {

        try {            
            byte[] pdfBytes = HtmlToPdf.GetPdfAsByteArray(message.getUrl());

            String title = HtmlToPdf.getPageTitle(message.getUrl());

            Guid blobId = Guid.newGuid();

            storage.addFile(blobId, title+".pdf", pdfBytes, "application/pdf", message.getBucket(), new HashMap<>());
            
            BlobInfo blobInfo = storage.getFileInfo(blobId, message.getBucket());
            
            PdfGenerated pdfGenerated = new PdfGenerated();
            pdfGenerated.setId(UUID.randomUUID());
            pdfGenerated.setCorrelationId(message.getCorrelationId());
            pdfGenerated.setUserId(message.getUserId());
            pdfGenerated.setBucket(message.getBucket());
            pdfGenerated.setTitle(title);
            pdfGenerated.setBlobId(blobId);
            pdfGenerated.setPageId(message.getId());
            pdfGenerated.setLenght(blobInfo.getLength());
            pdfGenerated.setMd5(blobInfo.getMD5());
            pdfGenerated.setTimeStamp(getTimestamp());
            
            bus.publish(pdfGenerated);
        } catch (Exception e) {
            
            e.printStackTrace();
            
            PdfGenerationFailed pdfGenerationFailed = new PdfGenerationFailed();
            pdfGenerationFailed.setId(UUID.randomUUID());
            pdfGenerationFailed.setCorrelationId(message.getCorrelationId());
            pdfGenerationFailed.setUserId(message.getUserId());
            pdfGenerationFailed.setMessage("Can not get pdf from url " + message.getUrl() + ". Details: " + e.getMessage());
            pdfGenerationFailed.setTimeStamp(getTimestamp());
            
            bus.publish(pdfGenerationFailed);
        }
    }

    private String getTimestamp() {
        //("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return LocalDateTime.now().toString();
    }

}
