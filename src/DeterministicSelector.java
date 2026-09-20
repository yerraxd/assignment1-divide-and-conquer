public class DeterministicSelector {
    private static int lt;
    private static int gt;

    public static int select(int arr[], int k){
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("k is out of range");
        }
        return select(arr, 0, arr.length-1, k);
    }

    private static int select(int arr[], int l, int r, int k){
        if (l == r) return arr[l];
        int pivotValue = medianOfMedians(arr, l, r);
        partition3(arr, l, r, pivotValue);

        if (k >= lt && k <= gt) return pivotValue;
        if (k < lt) return select(arr, l, lt-1, k);
        return select(arr, gt+1, r, k);
    }

    private static int medianOfMedians(int arr[], int l, int r){
        int n = r - l + 1;
        if (n <= 5){
            insertionSort(arr, l, r);
            return arr[l + (r-l)/2];
        }

        int groups = (n + 4) / 5;
        for (int g = 0; g < groups; g++){
            int gl = l + g*5;
            int gr = Math.min(gl+4, r);
            insertionSort(arr, gl, gr);
            int med = gl + (gr-gl)/2;
            int t = arr[l+g];
            arr[l+g] = arr[med];
            arr[med] = t;
        }
        return select(arr, l, l+groups-1, l + groups/2);
    }

    private static void partition3(int arr[], int l, int r, int p){
        lt = l;
        gt = r;
        int i = l;
        while (i <= gt){
            if (arr[i] < p){
                int t = arr[lt];
                arr[lt] = arr[i];
                arr[i] = t;
                lt++;
                i++;
            }
            else if (arr[i] > p){
                int t = arr[i];
                arr[i] = arr[gt];
                arr[gt] = t;
                gt--;
            }
            else {
                i++;
            }
        }
    }

    private static void insertionSort(int arr[], int lo, int hi){
        for (int i = lo + 1; i <= hi; i++){
            int key = arr[i];
            int j = i - 1;
            while (j >= lo && arr[j] > key){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }
}