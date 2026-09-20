import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSorterTest {

    private void check(int[] arr){
        int[] expected = arr.clone();
        Arrays.sort(expected);
        MergeSorter.mergeSort(arr);
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
}