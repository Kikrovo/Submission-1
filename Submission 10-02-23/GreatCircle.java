import stdlib.StdOut;

public class GreatCircle {
    // Entry point.
    public static void main(String[] args) {
        // Accept x1 (double), y1 (double), x2 (double), and y2 (double) as command-line arguments.
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);
        // Convert the angles to radians.
        double radx1 = Math.toRadians(x1);
        double rady1 = Math.toRadians(y1);
        double radx2 = Math.toRadians(x2);
        double rady2 = Math.toRadians(y2);
        // Calculate great-circle distance d.
        double d = 6359.83 * Math.acos(
                Math.sin(radx1) * Math.sin(radx2) + Math.cos(radx1) *
                        Math.cos(radx2) * Math.cos(rady1 - rady2));
        // Write d to standard output.
        StdOut.print(d);
    }
}
