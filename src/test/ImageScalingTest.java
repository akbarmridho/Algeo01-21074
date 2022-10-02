import main.bonus.ImageScaling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageScalingTest {
    public static void main(String[] args) {
        File imageFile = new File("./test/einstein.jpg");
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));


        try {
            BufferedImage image = ImageIO.read(imageFile);
            long startTime = System.nanoTime();
            BufferedImage result = ImageScaling.scale(image);
            long endTime = System.nanoTime();


            File output = new File("./test/" + imageFile.getName().split("\\.")[0] + "result.png");
            ImageIO.write(result, "png", output);
            System.out.println(endTime-startTime);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
