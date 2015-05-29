package tesseractocr;

import java.io.*;
import java.nio.file.*;

import java.io.IOException;
import java.net.URL;

import org.apache.tika.Tika;

import net.sourceforge.tess4j.*;

import javax.swing.*;
import java.io.File;


public class OCR {

	private static String[] pageArray;

    public static void main(String[] args) {
        //new basicGUI();
    	runOCR("/Users/arafath/Documents/workspace/SolrPOC/solr-word.pdf","/Users/arafath/Documents/workspace/SolrPOC/solr-word.txt");
    	
    	
    }
    public static void runOCR(String browseLocation, String saveLocation) {
        try {
            String fileType = fileType(browseLocation);
            if (fileType.equals("application/pdf")) {
                pageArray = ConvertPDFPageToImage.convert(browseLocation);
            } else if (!fileType.substring(0,5).equals("image")) {
                System.out.println("File type " + fileType + " is not supported.");
            }
            doesFileExist(saveLocation);
            for (String aPageArray : pageArray) {
                File imageFile = new File(aPageArray);
                Tesseract instance = Tesseract.getInstance();
                String result = instance.doOCR(imageFile);
                System.out.println(result);
                appendToFile(result, saveLocation);
            }

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String fileType(String location){
        Tika tika = new Tika();
        //String fileType = tika.detect(new URL("http://tika.apache.org/tika.png"));
        //System.out.println("File type " + fileType + ".");
        return tika.detect(location);
    }
    public static void doesFileExist(String fileLocation) {
        try {
            File file =new File(fileLocation);
            if(!file.exists()){
                    file.createNewFile();
            } else {
                
                    Files.delete(Paths.get(fileLocation));
                    file.createNewFile();
                
            }
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", Paths.get(fileLocation));
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", Paths.get(fileLocation));
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
    public static void appendToFile(String data, String filename )
    {

        try {
            FileWriter newFile = new FileWriter(filename,true);
            BufferedWriter buffer = new BufferedWriter(newFile);
            buffer.write(data);
            buffer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
//    public static void openFile(String fileLocation){
//        try {
//            Desktop.getDesktop().open(new File(fileLocation));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//    }
    
}

