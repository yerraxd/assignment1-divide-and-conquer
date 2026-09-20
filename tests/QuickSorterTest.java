import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSorterTest {

    private void check(int[] arr){
        int[] expected = arr.clone();
        Arrays.sort(expected);
        QuickSorter.quickSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void emptyArray(){
        check(new int[0]);
    }

    @Test
    void singleElement(){
        check(new int[]{5});
    }

    @Test
    void sortedArray(){
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) arr[i] = i;
        check(arr);
    }

    @Test
    void reverseSortedArray(){
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) arr[i] = 1000 - i;
        check(arr);
    }

    @Test
    void manyDuplicates(){
        Random rnd = new Random(1);
        int[] arr = new int[200];
        for (int i = 0; i < arr.length; i++) arr[i] = rnd.nextInt(3);
        check(arr);
    }
}