import java.util.concurrent.ThreadLocalRandom;

public class QuickSorter {
    public static void quickSort(int arr[],int l, int r)
    {
        if (l>=r) return;
        int pi = partition(arr,l,r);

        quickSort(arr, l,pi-1);
        quickSort(arr,pi+1,r);
    }

    private static int partition(int[] arr, int l, int r) {
        int rand = ThreadLocalRandom.current().nextInt(l, r+1);
        int t = arr[rand];
        arr[rand] = arr[r];
        arr[r] = t;

        int pi = arr[r];
        int ind = l-1;
        for (int i = l; i<r; i++){
            if (arr[i]<pi){
                ind++;
                int temp = arr[ind];
                arr[ind] = arr[i];
                arr[i] = temp;
            }
        }
        int temp = arr[r];
        arr[r] = arr[ind+1];
        arr[ind+1] = temp;
        return ind+1;
    }
}
