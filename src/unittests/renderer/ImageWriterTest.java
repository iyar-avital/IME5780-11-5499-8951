package unittests.renderer;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

public class ImageWriterTest {

    @Test
    public void ImageWriterWriteToImage()
    {
        renderer.ImageWriter imageWriter = new ImageWriter("imageWriterTest", 1600, 1000, 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.ORANGE);
                } else {
                    imageWriter.writePixel(i, j, Color.GREEN);
                }
            }
        }
        imageWriter.writeToImage();
    }
}
