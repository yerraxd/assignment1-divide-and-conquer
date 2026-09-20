import java.util.Random;

public class Experiment {

    public static int[] randomArray(int n, long seed) {
        Random rnd = new Random(seed);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(n * 10 + 1);
        return arr;
    }

    public static int[] sortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    public static int[] reverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    public static int[] duplicateHeavyArray(int n, int distinctValues, long seed) {
        Random rnd = new Random(seed);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(distinctValues);
        return arr;
    }
    public static Point[] randomPoints(int n, long seed) {
        Random rnd = new Random(seed);
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(rnd.nextDouble() * n, rnd.nextDouble() * n);
        }
        return pts;
    }

    public static Point[] verticalLinePoints(int n, long seed) {
        Random rnd = new Random(seed);
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            double x = 500.0 + rnd.nextDouble() * 1e-6;
            pts[i] = new Point(x, i);
        }
        return pts;
    }
    public static String runMergeSort(int[] source, String inputType) {
        int[] arr = source.clone();
        long start = System.nanoTime();
        MergeSorter.mergeSort(arr);
        long timeNs = System.nanoTime() - start;
        return row("MergeSort", inputType, arr.length, timeNs,
                MergeSorter.maxDepth, "comparisons", MergeSorter.comparisons);
    }

    public static String runQuickSort(int[] source, String inputType) {
        int[] arr = source.clone();
        long start = System.nanoTime();
        QuickSorter.quickSort(arr);
        long timeNs = System.nanoTime() - start;
        return row("QuickSort", inputType, arr.length, timeNs,
                QuickSorter.maxDepth, "swaps", QuickSorter.swaps);
    }

    private static String row(String algo, String inputType, int n, long timeNs,
                              int depth, String metricName, long metricValue) {
        return algo + "," + inputType + "," + n + "," + timeNs + "," +
                depth + "," + metricName + "," + metricValue;
    }
}