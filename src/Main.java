public class Main {
    public static void main(String[] args) {
        int[] sizes = {100, 1_000, 10_000};
        long seed = 42L;

        for (int n : sizes) {
            int[] random = Experiment.randomArray(n, seed);
            System.out.println(Experiment.runMergeSort(random, "random"));
            System.out.println(Experiment.runQuickSort(random, "random"));
        }
    }
}