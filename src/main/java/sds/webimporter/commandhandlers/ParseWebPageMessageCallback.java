package sds.webimporter.commandhandlers;
import java.util.concurrent.BlockingQueue;
import sds.messaging.callback.AbstractMessageCallback;
import sds.webimporter.domain.commands.ParseWebPage;

public class ParseWebPageMessageCallback extends AbstractMessageCallback<ParseWebPage> {

    public ParseWebPageMessageCallback(Class<ParseWebPage> tClass, BlockingQueue<ParseWebPage> queue) {
        super(tClass, queue);
    }

}
