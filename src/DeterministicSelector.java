public class DeterministicSelector {

    public static int select(int arr[], int k){
        return select(arr, 0, arr.length-1, k);
    }

    private static int select(int arr[], int l, int r, int k){
        if (l == r) return arr[l];
        int pivotValue = medianOfMedians(arr, l, r);
        int pi = partition(arr, l, r, pivotValue);

        if (k == pi) return arr[pi];
        if (k < pi) return select(arr, l, pi-1, k);
        return select(arr, pi+1, r, k);
    }

    private static int medianOfMedians(int arr[], int l, int r){
        int n = r - l + 1;
        if (n <= 5){
            insertionSort(arr, l, r);
            return arr[l + (r-l)/2];
        }

        int groups = (n + 4) / 5;
        int medians[] = new int[groups];
        for (int g = 0; g < groups; g++){
            int gl = l + g*5;
            int gr = Math.min(gl+4, r);
            insertionSort(arr, gl, gr);
            medians[g] = arr[gl + (gr-gl)/2];
        }
        return select(medians, 0, groups-1, groups/2);
    }

    private static int partition(int arr[], int l, int r, int pivotValue){
        int idx = l;
        for (int i = l; i <= r; i++){
            if (arr[i] == pivotValue){
                idx = i;
                break;
            }
        }
        int t = arr[idx];
        arr[idx] = arr[r];
        arr[r] = t;

        int ind = l-1;
        for (int i = l; i < r; i++){
            if (arr[i] < pivotValue){
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