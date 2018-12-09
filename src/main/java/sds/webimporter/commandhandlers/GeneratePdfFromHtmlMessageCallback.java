package sds.webimporter.commandhandlers;
import sds.webimporter.domain.commands.GeneratePdfFromHtml;
import java.util.concurrent.BlockingQueue;
import sds.messaging.callback.AbstractMessageCallback;

public class GeneratePdfFromHtmlMessageCallback extends AbstractMessageCallback<GeneratePdfFromHtml> {

    public GeneratePdfFromHtmlMessageCallback(Class<GeneratePdfFromHtml> tClass, BlockingQueue<GeneratePdfFromHtml> queue) {
        super(tClass, queue);
    }

}
