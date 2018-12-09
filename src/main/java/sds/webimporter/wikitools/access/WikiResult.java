
package sds.webimporter.wikitools.access;

import java.util.Map;

public class WikiResult{
    public byte[] Content;
    public Map<String, Object> Meta;

    public WikiResult(byte[] content, Map<String,Object> meta){
        Content = content;
        Meta = meta;
    }
}
