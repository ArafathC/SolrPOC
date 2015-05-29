package apachetika;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.LanguageProfile;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaAutoDetect {

	public static void main(String[] args) throws MalformedURLException, IOException, SAXException, TikaException {
		String filename = args[0];
	    MimeTypes mimeRegistry =
	                   TikaConfig.getDefaultConfig().getMimeRepository();
	    //<co id="get_mime_repo" />

	    System.out.println("Examining: [" + filename + "]");

	    System.out.println("The MIME type (based on filename) is: ["
	        + mimeRegistry.getMimeType(filename) + "]");
	    //<co id="guess_mime_filename" />

	    System.out.println("The MIME type (based on MAGIC) is: ["
	        + mimeRegistry.getMimeType(new File(filename)) + "]");
	    //<co id="guess_mime_magic" />

	    Detector mimeDetector = (Detector) mimeRegistry;
	    System.out.println(
			"The MIME type (based on the Detector interface) is: ["
	        + mimeDetector.detect(new File(filename).toURL().openStream(),
	            new Metadata()) + "]");//<co id="guess_detector" />


	    LanguageIdentifier lang = new LanguageIdentifier(new LanguageProfile(
	        FileUtils.readFileToString(new File(filename))));
	    //<co id="lang_detect"/>

	    System.out.println("The language of this content is: ["
	        + lang.getLanguage() + "]");

	    AutoDetectParser parser = new AutoDetectParser(); 
	    
	    /*
	    Parser parser = TikaConfig.getDefaultConfig().getParser(
	            MediaType.parse(
					mimeRegistry.getMimeType(filename).getName()));//<co id="parse"/> */
	    Metadata parsedMet = new Metadata();
	    ContentHandler handler = new BodyContentHandler(10*1024*1024);
	    parser.parse(new File(filename).toURL().openStream(), handler,   parsedMet, new ParseContext());

	    String[] metadataNames = parsedMet.names();
	    System.out.println("Parsed Metadata: ");
	    // Display all metadata
	    for(String name : metadataNames){
	        System.out.println(name + ": " + parsedMet.get(name));
	    }
	    
	    System.out.println("Parsed Text: ");
	    System.out.println(handler.toString());

	}

}
