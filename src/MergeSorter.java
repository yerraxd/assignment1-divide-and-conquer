public class MergeSorter {
    public static void mergeSort(int arr[]){
        int n = arr.length;
        if (n<0) return;
        int mid = n/2;
        int[] l = new int[mid];
        int[] r = new int[n-mid];
        for (int i = 0; i<mid;i++){
            l[i] = arr[i];
        }
        for (int i = mid;i<n;i++){
            r[i-mid] = arr[i];
        }

        mergeSort(l);
        mergeSort(r);
    }
}
