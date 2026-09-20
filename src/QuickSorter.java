public class QuickSorter {
    public static void quickSort(int arr[],int l, int r)
    {
        if (l>=r) return;
        int pi = partition(arr,l,r);

        quickSort(arr, l,pi-1);
        quickSort(arr,pi+1,r);
    }

    private static int partition(int[] arr, int l, int r) {
        return l;
    }
}
