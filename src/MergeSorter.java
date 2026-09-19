public class MergeSorter {
    private static final int CUTOFF = 16;
    public static int maxDepth = 0;
    public static long comparisons = 0;

    public static void mergeSort(int arr[]){
        maxDepth = 0;
        comparisons = 0;
        int n = arr.length;
        if (n<2) return;
        int buf[] = new int[n];
        sort(arr, buf, 0, n-1,1);
    }

    private static void sort(int arr[], int buf[],int lo, int hi, int depth){
        if (depth > maxDepth) maxDepth = depth;
        if (hi - lo + 1 <= CUTOFF){
            insertionSort(arr, lo, hi);
            return;
        }
        int mid = (lo+hi)/2;
        sort(arr, buf, lo,mid,depth+1);
        sort(arr, buf, mid+1, hi,depth+1);
        merge(arr,buf,lo,mid,hi);
    }
    private static void merge(int arr[],int buf[],int lo, int mid, int hi){
        for (int x = lo; x<=hi; x++){
            buf[x] = arr[x];
        }

        int i = lo;
        int j = mid+1;
        int ind = lo;

        while(i<=mid && j<=hi){
            comparisons++;
            if (buf[i]<=buf[j]){
                arr[ind] = buf[i];
                i++;
                ind++;
            }
            else {
                arr[ind] = buf[j];
                j++;
                ind++;
            }
        }

        for (int ll = i; ll<=mid; ll++){
            arr[ind] = buf[ll];
            ind++;
        }
    }
    private static void insertionSort(int arr[], int lo, int hi){
        for (int i = lo + 1; i <= hi; i++){
            int key = arr[i];
            int j = i - 1;
            while (j >= lo){
                comparisons++;
                if (arr[j] > key){
                    arr[j+1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j+1] = key;
        }
    }
}
