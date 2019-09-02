import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsIn) {
        if (pointsIn == null) {
            throw new IllegalArgumentException("invalid points");
        }
        points = Arrays.copyOf(pointsIn, pointsIn.length);
        if (hasNulls() || hasPairs()) {
            throw new IllegalArgumentException("invalid points");
        }

        LineSegment[] lines = new LineSegment[points.length];
        int segmentIndex = 0;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[k].slopeTo(points[m])
                                && points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                            Point[] temp = new Point[]{points[i],points[j],points[k],points[m]};
                            Arrays.sort(temp);
                            lines[segmentIndex] = new LineSegment(temp[0], temp[3]);
                            segmentIndex++;
                        }
                    }
                }
            }
        }
        this.segments = Arrays.copyOf(lines, segmentIndex);
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
