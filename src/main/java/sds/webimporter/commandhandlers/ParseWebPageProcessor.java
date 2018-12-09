package sds.webimporter.commandhandlers;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoObject;
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
import com.sds.storage.Blob;
import com.sds.storage.BlobInfo;
import com.sds.storage.BlobStorage;
import com.sds.storage.Guid;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.apache.commons.io.IOUtils;
import sds.messaging.callback.AbstractMessageProcessor;
import sds.webimporter.domain.commands.ParseWebPage;
import sds.webimporter.domain.events.RecordParsed;
import sds.webimporter.domain.events.WebPageParsed;
import sds.webimporter.domain.models.Field;

@Component
public class ParseWebPageProcessor extends AbstractMessageProcessor<ParseWebPage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseWebPageProcessor.class);

    private ReceiverBusControl receiver;
    private IBusControl bus;
    private BlobStorage storage;
    private MongoDatabase db;

    @Autowired
    public ParseWebPageProcessor(ReceiverBusControl receiver, IBusControl bus,
            BlobStorage storage, MongoDatabase db) throws JTransitLightException, IOException {
        this.bus = bus;
        this.receiver = receiver;
        this.storage = storage;
        this.db = db;
    }

    public void process(ParseWebPage message) {

        try {
            File directory = new File(System.getenv("OSDR_TEMP_FILES_FOLDER"));
            File tempFile = File.createTempFile("temp", ".tmp", directory);

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(storage.getFileStream(new Guid(message.getBlobId()), message.getBucket()), out);
            }

            Indigo indigo = new Indigo();
            indigo.setOption("ignore-stereochemistry-errors", "true");
            indigo.setOption("unique-dearomatization", "false");
            indigo.setOption("ignore-noncritical-query-features", "true");
            indigo.setOption("timeout", "600000");

            IndigoObject records = indigo.iterateSDFile(tempFile.getCanonicalPath());
            List<String> uniqueFieldNames = new ArrayList<String>();
            Iterator<IndigoObject> recordsIterator = records.iterator();
            int index = 0;
            while (records.iterator().hasNext()) {

                IndigoObject record = recordsIterator.next();

                String mol = record.molfile();

                Guid blobId = Guid.newGuid();

                storage.addFile(blobId, blobId.toString() + ".mol", mol.getBytes(), "chemical/x-mdl-molfile", message.getBucket(), null);
                List<Field> fields = new ArrayList<>();

                Iterator<IndigoObject> propertiesIterator = record.iterateProperties().iterator();
                while (propertiesIterator.hasNext()) {
                    IndigoObject property = propertiesIterator.next();
                    fields.add(new Field(property.name(), property.rawData()));

                    if (!uniqueFieldNames.contains(property.name())) {
                        uniqueFieldNames.add(property.name());
                    }
                }
                RecordParsed recordParsed = new RecordParsed();
                recordParsed.setId(UUID.randomUUID());
                recordParsed.setFileId(message.getId());
                recordParsed.setIndex(index);
                recordParsed.setFields(fields);
                recordParsed.setBucket(message.getBucket());
                recordParsed.setBlobId(blobId);
                recordParsed.setCorrelationId(message.getCorrelationId());
                recordParsed.setUserId(message.getUserId());
                recordParsed.setTimeStamp(getTimestamp());
                bus.publish(recordParsed);

                index++;

            }

            WebPageParsed pageParsed = new WebPageParsed();
            pageParsed.setId(message.getId());
            pageParsed.setCorrelationId(message.getCorrelationId());
            pageParsed.setUserId(message.getUserId());
            pageParsed.setTotalRecords(index);
            pageParsed.setFields(uniqueFieldNames);

            bus.publish(pageParsed);

            records.dispose();
            Files.delete(tempFile.toPath());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ParseWebPageProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    private String getTimestamp() {
        //("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return LocalDateTime.now().toString();
    }

}
