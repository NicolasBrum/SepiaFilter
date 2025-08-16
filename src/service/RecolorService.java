package service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class RecolorService {
    private File outputDirectory;
    private final File imagePath;

    public RecolorService(String outputDirectory, String imagePath) {
        this.outputDirectory = Paths.get(outputDirectory)
                .toFile();
        this.imagePath = Paths.get(imagePath)
                .toFile();
    }

    public void startApplicationSepiaFilter() {
        BufferedImage image;

        try{
            image = ImageIO.read(imagePath);

            runImageAndApplyFilter(image);

            var fileFormat = getFileFormat();
            this.outputDirectory = getOutputDirectory();

            ImageIO.write(image, fileFormat, this.outputDirectory);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private File getOutputDirectory() {
        var fileName = imagePath.getName();
        return Paths.get(this.outputDirectory + "\\" + fileName).toFile();
    }

    private String getFileFormat() {
        return imagePath.getName()
                .substring(
                        (imagePath.getName()
                                .lastIndexOf(".")) + 1);
    }

    private void runImageAndApplyFilter(BufferedImage image){
        for(int line = 0; line < image.getHeight(); line++){
            for(int column = 0; column < image.getWidth(); column++){
                int newPixel = getNewRgb(image.getRGB(column, line));
                image.setRGB(column, line, newPixel);
            }
        }
    }

    private int getNewRgb(int rgb){
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int alpha = 255;

        int sumRed = Math.min(255,(int) ((red * 0.393f) + (green * 0.769f) + (blue * 0.189f)));
        int sumGreen = Math.min(255,(int) ((red * 0.349f) + (green * 0.686f) + (blue * 0.168f)));
        int sumBlue  = Math.min(255,(int) ((red * 0.272f) + (green * 0.534f) + (blue * 0.131f)));

        return (alpha << 24) | (sumRed << 16) | (sumGreen << 8) | sumBlue;
    }
}
