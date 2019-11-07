import processor.FileProcessor;
import processor.ResultFileProcessor;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Введите путь до файла с данными: ");
        String fileFrom = in.nextLine();
        System.out.print("Введите путь, по которому будет сохранен результат: ");
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
