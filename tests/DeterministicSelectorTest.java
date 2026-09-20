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
}