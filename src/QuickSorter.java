import java.util.concurrent.ThreadLocalRandom;

public class QuickSorter {
    public static int maxDepth = 0;
    public static long swaps = 0;
    private static int curDepth = 0;

    public static void quickSort(int arr[]){
        maxDepth = 0;
        swaps = 0;
        curDepth = 0;
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int arr[],int l, int r)
    {
        curDepth++;
        if (curDepth > maxDepth) maxDepth = curDepth;

        while (l < r) {
            int pi = partition(arr, l, r);
            if (pi - l < r - pi) {
                quickSort(arr, l, pi - 1);
                l = pi + 1;
            } else {
                quickSort(arr, pi + 1, r);
                r = pi - 1;
            }
        }
        curDepth--;
    }

    private static int partition(int[] arr, int l, int r) {
        int rand = ThreadLocalRandom.current().nextInt(l, r+1);
        int t = arr[rand];
        arr[rand] = arr[r];
        arr[r] = t;
        swaps++;

        int pi = arr[r];
        int ind = l-1;
        for (int i = l; i<r; i++){
            if (arr[i]<pi){
                ind++;
                int temp = arr[ind];
                arr[ind] = arr[i];
                arr[i] = temp;
                swaps++;
            }
        }
        int temp = arr[r];
        arr[r] = arr[ind+1];
        arr[ind+1] = temp;
        swaps++;
        return ind+1;
    }
}
