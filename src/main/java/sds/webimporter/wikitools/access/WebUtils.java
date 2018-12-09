package sds.webimporter.wikitools.access;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import sds.webimporter.wikitools.configs.WikiConfigSection;

@Configuration
@Import(WikiConfigSection.class)
public class WebUtils {
    
    @Autowired
    private WikiConfigSection wikiConfig;
    
    public WikiConfigSection GetConfig(){
        return wikiConfig;
    }
    
    
    public static byte[] GetBinaryWebContent(String url) throws MalformedURLException, IOException{
        URL link = new URL(url);

        URLConnection connection = link.openConnection();

        InputStream input = connection.getInputStream();

        byte[] buffer = new byte[4096];
        int n;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((n = input.read(buffer)) != -1) 
        {
            output.write(buffer, 0, n);
        }
        
        byte[] result = output.toByteArray();
        output.close();
        return result;
    }  
}
