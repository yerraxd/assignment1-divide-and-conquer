public class ClosestPairSolver {
    public static int maxDepth = 0;
    public static long comparisons = 0;
    private static int curDepth = 0;

    public static double closestPair(Point[] points){
        maxDepth = 0;
        comparisons = 0;
        curDepth = 0;

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
            while (j >= lo){
                comparisons++;
                if (pts[j].x > key.x){
                    pts[j+1] = pts[j];
                    j--;
                } else {
                    break;
                }
            }
            pts[j+1] = key;
        }
    }

    private static void sortByY(Point[] pts, int lo, int hi){
        for (int i = lo + 1; i <= hi; i++){
            Point key = pts[i];
            int j = i - 1;
            while (j >= lo){
                comparisons++;
                if (pts[j].y > key.y){
                    pts[j+1] = pts[j];
                    j--;
                } else {
                    break;
                }
            }
            pts[j+1] = key;
        }
    }

    private static double solve(Point[] pts, int l, int r){
        curDepth++;
        if (curDepth > maxDepth) maxDepth = curDepth;

        int n = r - l + 1;
        if (n <= 3){
            double best = bruteForce(pts, l, r);
            curDepth--;
            return best;
        }

        int mid = (l + r) / 2;
        double midX = pts[mid].x;

        double dl = solve(pts, l, mid);
        double dr = solve(pts, mid+1, r);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[n];
        int stripCount = 0;
        for (int i = l; i <= r; i++){
            comparisons++;
            if (Math.abs(pts[i].x - midX) < d){
                strip[stripCount] = pts[i];
                stripCount++;
            }
        }

        sortByY(strip, 0, stripCount - 1);

        for (int i = 0; i < stripCount; i++){
            for (int j = i+1; j < stripCount; j++){
                comparisons++;
                if (strip[j].y - strip[i].y >= d) break;
                double dist = strip[i].distanceTo(strip[j]);
                if (dist < d) d = dist;
            }
        }

        curDepth--;
        return d;
    }

    private static double bruteForce(Point[] pts, int l, int r){
        double best = Double.MAX_VALUE;
        for (int i = l; i <= r; i++){
            for (int j = i+1; j <= r; j++){
                comparisons++;
                double dist = pts[i].distanceTo(pts[j]);
                if (dist < best) best = dist;
            }
        }
        return best;
    }
}