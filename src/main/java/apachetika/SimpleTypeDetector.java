package apachetika;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;

public class SimpleTypeDetector {

	public static void main(String[] args) throws IOException {
		  Tika tika = new Tika();

	      for (String file : args) {
	          String type = tika.detect(new File(file));
	          System.out.println(file + ": " + type);
	      }

	}

}
