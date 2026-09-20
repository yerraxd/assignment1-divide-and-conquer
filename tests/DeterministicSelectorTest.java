import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectorTest {

    @Test
    void randomArrays(){
        Random rnd = new Random(7);
        for (int t = 0; t < 200; t++){
            int n = 1 + rnd.nextInt(100);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(200) - 100;
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = rnd.nextInt(n);
            assertEquals(sorted[k], DeterministicSelector.select(a.clone(), k));
        }
    }
    @Test
    void smallSizesAllK(){
        Random rnd = new Random(3);
        for (int n = 1; n <= 12; n++){
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(20);
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            for (int k = 0; k < n; k++){
                assertEquals(sorted[k], DeterministicSelector.select(a.clone(), k));
            }
        }
    }

    @Test
    void allEqualValues(){
        int[] a = new int[10000];
        Arrays.fill(a, 5);
        assertEquals(5, DeterministicSelector.select(a, 5000));
    }

    @Test
    void invalidInput(){
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelector.select(new int[0], 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelector.select(new int[]{1, 2}, 2));
    }
}