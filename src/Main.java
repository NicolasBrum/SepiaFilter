import service.RecolorService;

public class Main {
    public static void main(String[] args) {
        String outputDirectory = args[0];
        String filePath = args[1];

        var service = new RecolorService(outputDirectory, filePath);
        service.startApplicationSepiaFilter();
    }
}