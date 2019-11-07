import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileCreator {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/home/alex/Рабочий стол/TestBigNew.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(path.toString()));
        Random random = new Random();
        String[] testData = {"0000000010010001",
                             "0000000010000100",
                             "0000000001011010",
                             "0000000001100100",
                             "0000000001000001",
                             "0000000000010101",
                             "0000000000101101",
                             "0000000001100011",
                             "0000000000111000",
                             "0000000000001100"};
        long i = 0L;
        while (i < 100000 * 17) {
            writer.write(testData[random.nextInt(testData.length)]);
            writer.println();
            i++;
        }
        writer.close();
    }
}
