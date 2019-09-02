import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pointsIn) {
        if (pointsIn == null) {
            throw new IllegalArgumentException("invalid points");
        }
        points = Arrays.copyOf(pointsIn, pointsIn.length);
        if (hasNulls() || hasPairs()) {
            throw new IllegalArgumentException("invalid points");
        }

        LineSegment[] lines = new LineSegment[points.length];
        int segmentIndex = 0;
        for (int i = 0; i < points.length; i++) {
            double[][] temp = getSlopeAndIndexArrayForPoint(i);

            Arrays.sort(temp, (o1, o2) -> {
                if (o1[0] == o2[0]) {
                    return points[(int)o1[1]].compareTo(points[(int)o2[1]]);
                }
                return Double.compare(o1[0], o2[0]);
            });

            int startIndex = 0;
            int current = 1;
            while (current < temp.length) {

                while (current < temp.length && temp[startIndex][0] == temp[current][0]) {
                    current++;
                }

                if ((current - startIndex >= 3) && (points[i].compareTo(points[(int)temp[startIndex][1]]) < 0)) {
                    lines[segmentIndex] = new LineSegment(points[i], points[(int)temp[current - 1][1]]);
                        segmentIndex++;
                }
                startIndex = current;
                current++;
            }
        }
        this.segments = Arrays.copyOf(lines, segmentIndex);
    }

    private double[][] getSlopeAndIndexArrayForPoint(int pointIndex) {
        double[][] temp = new double[points.length][2];
        for (int j = 0; j < points.length; j++) {
            if (pointIndex == j) {
                temp[j][0] = Integer.MAX_VALUE;
                temp[j][1] = j;
                continue;
            }
            temp[j][0] = points[pointIndex].slopeTo(points[j]);
            temp[j][1] = j;
        }
        return temp;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    private boolean hasNulls() {
        for (Point point : points) {
            if (point == null) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPairs() {
        Point[] temp = Arrays.copyOf(points, points.length);
        Arrays.sort(temp);
        for (int i = 0; i < temp.length - 1; i++) {
            if (temp[i].compareTo(temp[i+1]) == 0) {
                return true;
            }
        }
        return false;
    }
}
