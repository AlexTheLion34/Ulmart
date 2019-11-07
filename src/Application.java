import processor.FileProcessor;
import processor.ResultFileProcessor;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        String fileFrom = in.nextLine();
        String fileTo = in.nextLine();

        FileProcessor processor = new FileProcessor(fileFrom);

        try {
            int[] result = processor.processFile();
            ResultFileProcessor resultProcessor = new ResultFileProcessor(fileTo, result);
            resultProcessor.processResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
