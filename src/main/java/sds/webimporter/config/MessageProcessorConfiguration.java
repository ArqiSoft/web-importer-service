package sds.webimporter.config;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.npspot.jtransitlight.JTransitLightException;
import com.npspot.jtransitlight.consumer.ReceiverBusControl;
import com.npspot.jtransitlight.consumer.setting.ConsumerSettings;
import com.npspot.jtransitlight.publisher.IBusControl;
import sds.webimporter.domain.commands.GeneratePdfFromHtml;
import sds.webimporter.commandhandlers.GeneratePdfFromHtmlMessageCallback;
import sds.messaging.callback.AbstractMessageProcessor;
import sds.webimporter.commandhandlers.ParseWebPageMessageCallback;
import sds.webimporter.commandhandlers.ProcessWebPageMessageCallback;
import sds.webimporter.domain.commands.ParseWebPage;
import sds.webimporter.domain.commands.ProcessWebPage;

@Component
public class MessageProcessorConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessorConfiguration.class);

    
    @Autowired
    public MessageProcessorConfiguration(IBusControl busControl, 
            ReceiverBusControl receiver, 
            AbstractMessageProcessor<GeneratePdfFromHtml> exportFileProcessor,
            BlockingQueue<GeneratePdfFromHtml> generatePdfFromHtmlQueue,
            @Value("${generatePdfFromHtmlQueueName}") String generatePdfFromHtmlQueueName,
            AbstractMessageProcessor<ParseWebPage> parseWebPageProcessor,
            BlockingQueue<ParseWebPage> parseWebPageQueue,
            @Value("${parseWebPageQueueName}") String parseWebPageQueueName,
            AbstractMessageProcessor<ProcessWebPage> processWebPageProcessor,
            BlockingQueue<ProcessWebPage> processWebPageQueue,
            @Value("${processWebPageQueueName}") String processWebPageQueueName,
            @Value("${EXECUTORS_THREAD_COUNT:5}") Integer threadCount) 
                    throws JTransitLightException, IOException, InterruptedException {
        
        receiver.subscribe(new GeneratePdfFromHtml().getQueueName(), generatePdfFromHtmlQueueName, 
                ConsumerSettings.newBuilder().withDurable(true).build(), 
                new GeneratePdfFromHtmlMessageCallback(GeneratePdfFromHtml.class, generatePdfFromHtmlQueue));
        
        receiver.subscribe(new ProcessWebPage().getQueueName(), processWebPageQueueName, 
                ConsumerSettings.newBuilder().withDurable(true).build(), 
                new ProcessWebPageMessageCallback(ProcessWebPage.class, processWebPageQueue));
        
        receiver.subscribe(new ParseWebPage().getQueueName(), parseWebPageQueueName, 
                ConsumerSettings.newBuilder().withDurable(true).build(), 
                new ParseWebPageMessageCallback(ParseWebPage.class, parseWebPageQueue));
        
        LOGGER.debug("EXECUTORS_THREAD_COUNT is set to {}", threadCount);
        
        Executors.newSingleThreadExecutor().submit(() -> {
            final ExecutorService threadPool = 
                    Executors.newFixedThreadPool(threadCount);
            
            while (true) {
                // wait for message
                final GeneratePdfFromHtml message = generatePdfFromHtmlQueue.take();
                
                // submit to processing pool
                threadPool.submit(() -> exportFileProcessor.doProcess(message));
                Thread.sleep(10);
            }
        });
        
        Executors.newSingleThreadExecutor().submit(() -> {
            final ExecutorService threadPool = 
                    Executors.newFixedThreadPool(threadCount);
            
            while (true) {
                // wait for message
                final ProcessWebPage message = processWebPageQueue.take();
                
                // submit to processing pool
                threadPool.submit(() -> processWebPageProcessor.doProcess(message));
                Thread.sleep(10);
            }
        });
        
        Executors.newSingleThreadExecutor().submit(() -> {
            final ExecutorService threadPool = 
                    Executors.newFixedThreadPool(threadCount);
            
            while (true) {
                // wait for message
                final ParseWebPage message = parseWebPageQueue.take();
                
                // submit to processing pool
                threadPool.submit(() -> parseWebPageProcessor.doProcess(message));
                Thread.sleep(10);
            }
        });
        
                
    }
}
