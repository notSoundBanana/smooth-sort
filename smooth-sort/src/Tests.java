import java.io.FileWriter;
import java.io.IOException;

public class Tests {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("results_time_smoothSort_nanoseconds.txt");
        for (int n = 100; n <= 10000; n += 100) {
            int[] arr = Data.generateArray(n, fileWriter);

            SmoothSort sorter = new SmoothSort();
            long lStartTime = System.nanoTime();
            sorter.sort(arr);
            long lEndTime = System.nanoTime();
            long output = lEndTime - lStartTime;

            fileWriter.write(n + " elements \n");
            fileWriter.write(sorter.getLastSortIterationCount() + " iterations \n");
            fileWriter.write("Elapsed time in nanoseconds: " + output + "\n\n");
        }
        fileWriter.close();
    }
}
