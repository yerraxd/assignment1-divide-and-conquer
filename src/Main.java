import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1_000, 10_000, 100_000};
        int[] closestPairSizes = {100, 500, 1_000, 2_000, 5_000, 50_000};
        long seed = 42L;

        List<String> rows = new ArrayList<>();
        rows.add("algorithm,inputType,n,timeNs,maxDepth,metricName,metricValue");

        for (int n : sizes) {
            int[] random = Experiment.randomArray(n, seed);
            int[] sorted = Experiment.sortedArray(n);
            int[] reverse = Experiment.reverseSortedArray(n);
            int[] dupHeavy = Experiment.duplicateHeavyArray(n, Math.max(2, n / 100), seed);

            rows.add(Experiment.runMergeSort(random, "random"));
            rows.add(Experiment.runMergeSort(sorted, "sorted"));
            rows.add(Experiment.runMergeSort(reverse, "reverse-sorted"));
            rows.add(Experiment.runMergeSort(dupHeavy, "duplicate-heavy"));

            rows.add(Experiment.runQuickSort(random, "random"));
            rows.add(Experiment.runQuickSort(sorted, "sorted"));
            rows.add(Experiment.runQuickSort(reverse, "reverse-sorted"));
            rows.add(Experiment.runQuickSort(dupHeavy, "duplicate-heavy"));

            int k = n / 2;
            rows.add(Experiment.runSelect(random, "random", k));
            rows.add(Experiment.runSelect(sorted, "sorted", k));
            rows.add(Experiment.runSelect(reverse, "reverse-sorted", k));
            rows.add(Experiment.runSelect(dupHeavy, "duplicate-heavy", k));
        }

        for (int n : closestPairSizes) {
            Point[] random = Experiment.randomPoints(n, seed);
            Point[] verticalLine = Experiment.verticalLinePoints(n, seed);

            rows.add(Experiment.runClosestPair(random, "random"));
            rows.add(Experiment.runClosestPair(verticalLine, "adversarial-vertical-line"));
        }

        new File("results").mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter("results/results.csv"))) {
            for (String line : rows) writer.println(line);
        }

        System.out.println("Done: " + (rows.size() - 1) + " rows written to results/results.csv");
    }
}