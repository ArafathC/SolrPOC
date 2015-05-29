package tesseractocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class ConvertPDFPageToImage {
	private static String[] pageName;

    public static String[] convert(String source) {
        try {
            //setting up the file we want to convert, and what directory to put it in
            String destinationDir = "images/";
            File oldFile = new File(source);
            //remove the .pdf extension, later we'll add .png
            String fileName = oldFile.getName().replace(".pdf", "");
            if (oldFile.exists()) {

                PDDocument document = PDDocument.load(source);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                pageName = new String[document.getNumberOfPages()];

                int pageNumber = 0;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    File outputfile = new File(destinationDir + fileName + "_" + pageNumber + ".png");
                    ImageIO.write(image, "png", outputfile);
                    pageName[pageNumber] = destinationDir + fileName + "_" + pageNumber + ".png";
                    pageNumber++;
                }
                document.close();

            } else {
                System.err.println(fileName + "File does not exist");
            }

        } catch (IOException e) {
            System.err.println("File exists but empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageName;
    }
}
