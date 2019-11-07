package processor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileProcessor {

    private String path;
    private final int[] powersOfTwo = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
                                       1024, 2048, 4096, 8192, 16384, 32768};
    private final int[] result = new int[150];

    public FileProcessor(String path) {
        this.path = path;
    }

    public int[] processFile() throws IOException {

        Path path = Paths.get(this.path);
        RandomAccessFile file = new RandomAccessFile(path.toString(), "r");

        int bufferSize;

        if (isWindows())
            bufferSize = 18_000_000;
        else
            bufferSize = 17_000_000;

        long size = Files.size(path);
        int numOfChunks = (int) (size / bufferSize);

        int[][] tempResult = new int[numOfChunks][150];

        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(numOfChunks);

        for (int i = 0; i < numOfChunks; i++) {
            int finalI = i;
            byte[] currentBuffer = new byte[bufferSize];
            try {
                file.seek((long) finalI * bufferSize);
                file.read(currentBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            service.submit(() -> {
                int sum = 0;
                int power = 15;
                for (byte b : currentBuffer) {
                    if (b != 49 && b != 48) {
                        tempResult[finalI][sum]++;
                        sum = 0;
                        power = 15;
                        continue;
                    }
                    sum += ((b - 48) * powersOfTwo[power]);
                    power--;
                }
                latch.countDown();
            });
        }
        service.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < numOfChunks; j++) {
                result[i] += tempResult[j][i];
            }
        }
        return result;
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }
}
