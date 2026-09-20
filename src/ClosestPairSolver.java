public class ClosestPairSolver {

    public static double closestPair(Point[] points){
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("need at least 2 points");
        }

        int n = points.length;
        Point[] byX = new Point[n];
        for (int i = 0; i < n; i++){
            byX[i] = points[i];
        }

        sortByX(byX, 0, n-1);

        return solve(byX, 0, n - 1);
    }

    private static void sortByX(Point[] pts, int lo, int hi){
        for (int i = lo + 1; i <= hi; i++){
            Point key = pts[i];
            int j = i - 1;
            while (j >= lo && pts[j].x > key.x){
                pts[j+1] = pts[j];
                j--;
            }
            pts[j+1] = key;
        }
    }

    private static double solve(Point[] pts, int l, int r){
        int n = r - l + 1;
        if (n <= 3){
            return bruteForce(pts, l, r);
        }

        int mid = (l + r) / 2;
        double midX = pts[mid].x;

        double dl = solve(pts, l, mid);
        double dr = solve(pts, mid+1, r);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[n];
        int stripCount = 0;
        for (int i = l; i <= r; i++){
            if (Math.abs(pts[i].x - midX) < d){
                strip[stripCount] = pts[i];
                stripCount++;
            }
        }

        for (int i = 0; i < stripCount; i++){
            for (int j = i+1; j < stripCount; j++){
                double dist = strip[i].distanceTo(strip[j]);
                if (dist < d) d = dist;
            }
        }

        return d;
    }

    private static double bruteForce(Point[] pts, int l, int r){
        double best = Double.MAX_VALUE;
        for (int i = l; i <= r; i++){
            for (int j = i+1; j <= r; j++){
                double dist = pts[i].distanceTo(pts[j]);
                if (dist < best) best = dist;
            }
        }
        return best;
    }
}