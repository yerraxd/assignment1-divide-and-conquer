import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestPairTest {

    private double brute(Point[] p){
        double best = Double.MAX_VALUE;
        for (int i = 0; i < p.length; i++){
            for (int j = i + 1; j < p.length; j++){
                best = Math.min(best, p[i].distanceTo(p[j]));
            }
        }
        return best;
    }

    @Test
    void randomPointsAgainstBruteForce(){
        int[] sizes = {2, 3, 4, 5, 10, 100, 500, 2000};
        for (int n : sizes){
            Point[] pts = Experiment.randomPoints(n, n);
            assertEquals(brute(pts), ClosestPairSolver.closestPair(pts), 1e-9);
        }
    }
    @Test
    void manyEqualXCoordinates(){
        Random rnd = new Random(5);
        Point[] pts = new Point[1000];
        for (int i = 0; i < pts.length; i++){
            pts[i] = new Point(rnd.nextInt(5), rnd.nextInt(1000));
        }
        assertEquals(brute(pts), ClosestPairSolver.closestPair(pts), 1e-9);
    }

    @Test
    void duplicatePoints(){
        Point[] pts = {new Point(1, 1), new Point(5, 5), new Point(1, 1), new Point(9, 2), new Point(7, 7)};
        assertEquals(0.0, ClosestPairSolver.closestPair(pts), 1e-9);
    }

    @Test
    void tooFewPoints(){
        assertThrows(IllegalArgumentException.class,
                () -> ClosestPairSolver.closestPair(new Point[]{new Point(0, 0)}));
    }
}