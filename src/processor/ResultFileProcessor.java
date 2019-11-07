package processor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResultFileProcessor {

    private String path;
    private int[] result;

    public ResultFileProcessor(String path, int[] result) {
        this.path = path;
        this.result = result;
    }

    public void processResult() throws IOException {
        Path path = Paths.get(this.path);
        PrintWriter writer = new PrintWriter(new FileWriter(path.toFile()));

        for (int i = 0; i < result.length; i++) {
            StringBuilder age = new StringBuilder(Integer.toBinaryString(i));
            StringBuilder count = new StringBuilder(Integer.toBinaryString(result[i]));
            if (age.length() < 16)
                while (age.length() < 16)
                    age.insert(0, "0");
            if (count.length() < 16)
                while (count.length() < 16)
                    count.insert(0, "0");
            writer.println(age.append(count));
        }
        writer.close();
    }
}
