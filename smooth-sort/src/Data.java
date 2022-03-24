import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Data {
    public static int[] generateArray(int n, FileWriter fileWriter) throws IOException {
        Random random = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(10000);
            fileWriter.write(arr[i] + " ");
        }

        fileWriter.write("\n");
        return arr;
    }
}
