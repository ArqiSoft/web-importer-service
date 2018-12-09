package sds.webimporter.commandhandlers;
import java.util.concurrent.BlockingQueue;
import sds.messaging.callback.AbstractMessageCallback;
import sds.webimporter.domain.commands.ProcessWebPage;

public class ProcessWebPageMessageCallback extends AbstractMessageCallback<ProcessWebPage> {

    public ProcessWebPageMessageCallback(Class<ProcessWebPage> tClass, BlockingQueue<ProcessWebPage> queue) {
        super(tClass, queue);
    }

}
