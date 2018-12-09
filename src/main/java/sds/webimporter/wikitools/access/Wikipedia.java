/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sds.webimporter.wikitools.access;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class Wikipedia {


    public static WikiResult doIt(List<String> urls) throws IOException, MalformedURLException, NoSuchAlgorithmException, ParserConfigurationException, SAXException {
        
        byte[] content = getMol(urls.get(0));
        URL uri = new URL(urls.get(0));
        String[] splitedHost = uri.getHost().split(".");
        String host = (splitedHost.length > 2 ? splitedHost[splitedHost.length - 2] + "." + splitedHost[splitedHost.length - 1] : uri.getHost());

        Map<String, Object> meta = new HashMap<String, Object>() {
            {
                put("ImportedFrom", host);
                put("Url", uri.toString());
                put("Source Content Type", "chemical/x-mdl-molfile");
            }
        };
        return new WikiResult(content, meta);
        
    }

    private static byte[] getMol(String wikiUrl) {

        Reader r = null;
        try {
            URL url = new URL(wikiUrl);
            URLConnection con = url.openConnection();
            Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
            Matcher m = p.matcher(con.getContentType());
            String charset = m.matches() ? m.group(1) : "ISO-8859-1";
            r = new InputStreamReader(con.getInputStream(), charset);
            StringBuilder buf = new StringBuilder();
            while (true) {
                int ch = r.read();
                if (ch < 0) {
                    break;
                }
                buf.append((char) ch);
            }
            String str = buf.toString();
            Document doc = Jsoup.parse(str);
            String link = doc.select("a[href*=www.chemspider.com]").first().attr("href");
            url = new URL(link);
            con = url.openConnection();
            p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
            m = p.matcher(con.getContentType());
            charset = m.matches() ? m.group(1) : "ISO-8859-1";
            r = new InputStreamReader(con.getInputStream(), charset);
            buf = new StringBuilder();
            while (true) {
                int ch = r.read();
                if (ch < 0) {
                    break;
                }
                buf.append((char) ch);
            }
            str = buf.toString();
            doc = Jsoup.parse(str);
            link = doc.select("button[onclick*=/FilesHandler.ashx]").first().attr("onclick").replace("'", "");
            if (link != null && link.trim() != "") {
                String srcLink = "http://www.chemspider.com" + StringUtils.substringBetween(link, "window.location =", "; void 0;").trim();
                File directory = new File(System.getenv("OSDR_TEMP_FILES_FOLDER"));
                File tempFile = File.createTempFile("temp", ".tmp", directory);
                FileUtils.copyURLToFile(new URL(srcLink), tempFile);
                byte[] fileContent = Files.readAllBytes(tempFile.toPath());
                Files.delete(tempFile.toPath());
                return fileContent;
            }

        } catch (IOException ex) {
            Logger.getLogger(Wikipedia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Wikipedia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
