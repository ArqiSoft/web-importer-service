package sds.webimporter.wikitools.access;

import com.sun.jndi.toolkit.url.Uri;

public class ExternalPage extends PageBase {

    private Uri _url;

    public String getUrl() {
        return _url.toString();
    }

    public ExternalPage(Uri url, String title) {
        _url = url;
        Name = _url.getPath();
        Title = title;
    }

}
