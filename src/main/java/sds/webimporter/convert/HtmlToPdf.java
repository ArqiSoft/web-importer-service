package sds.webimporter.convert;

import com.itextpdf.html2pdf.HtmlConverter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HtmlToPdf {

    public static byte[] GetPdfAsByteArray(String url) throws MalformedURLException, IOException {
        
        File directory = new File(System.getenv("OSDR_TEMP_FILES_FOLDER"));
        File tempFile = File.createTempFile("temp", ".pdf", directory);
        
        try (OutputStream os = new FileOutputStream(tempFile.getPath())) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(url);
            builder.toStream(os);
            builder.run();
                    byte[] result = Files.readAllBytes(tempFile.toPath());
        Files.delete(tempFile.toPath());
        tempFile.delete();
        return result;
        } catch (Exception ex) {
            Logger.getLogger(HtmlToPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public static String getPageTitle(String url) throws MalformedURLException, IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream(),  "UTF8"));
    
        String line;
        String responseBody = "";
        while ((line = br.readLine()) != null) {
            responseBody += line;
        }
       
        String title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
        return title;
    }
}
