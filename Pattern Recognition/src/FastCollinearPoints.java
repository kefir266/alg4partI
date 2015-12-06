/**
 * Created by dmitrij on 29.09.2015.
 */
public class FastCollinearPoints {

    private LineSegmentPoints[] lineSegment;
    private LineSegment[] lineSegments;
    //private double[] lineSegmentSlope;
    private int nSeg = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();

        if (points.length == 0) throw new java.lang.IllegalArgumentException();



        for (Point point : points) {

            if (point == null) throw new java.lang.IllegalArgumentException();

        }

        sort(points);
        for (int i = 0; i < points.length - 1; i++) {

            if (points[i].compareTo(points[i + 1]) == 0) throw new java.lang.IllegalArgumentException();

        }

        lineSegment = new LineSegmentPoints[points.length * points.length / 2];
        //lineSegmentSlope = new double[points.length * points.length / 2];

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                lineSegment[nSeg++] = new LineSegmentPoints(points[i], points[j]);
            }
        }

        nSeg--;
        //cutArray(nSeg);
        sort(lineSegment, nSeg);
    }     // finds all line segments containing 4 or more points

    public           int numberOfSegments() {       // the number of line segments
        return nSeg;
    }

    public LineSegment[] segments() {
        int nFour = 0;
        int currentF = 0;
        int iLS = 0;
        boolean eqSlope = false;
        for (int i = 1; i < nSeg; i++) {
            if (
                //(lineSegment[i].slope == lineSegment[currentF].slope)
                    (lineSegment[i].isCol(lineSegment[currentF]))
                    ) {
                nFour++;
                eqSlope = true;
            }
            if (!eqSlope || (i == nSeg - 1)) {
                if (nFour >= 3) {
                    for (int j = currentF; j < i; j++) {
                        for (int cmp = currentF; cmp < j; cmp++) {
                            if ((lineSegment[cmp] != null && lineSegment[j] != null) && (lineSegment[cmp].p.compareTo(lineSegment[j].p) == 0 || lineSegment[cmp].q.compareTo(lineSegment[j].q) == 0)) {
                                if (lineSegment[j].include(lineSegment[cmp])) {
                                    lineSegment[j] = null;
                                    break;
                                }
                                if (lineSegment[cmp].include(lineSegment[j])) {
                                    lineSegment[cmp] = null;
                                    nFour--;
                                }
                            }
                        }
                    }
                    if (nFour >= 3) {
                        int startLS = iLS;
                        for (int j = currentF; j <= i - 1; j++) {
                            if (lineSegment[j] != null)
                                lineSegment[iLS++] = lineSegment[j];
                        }
                        //sortPoints(lineSegment, startLS, iLS - 1);
                        LineSegmentPoints ls = new LineSegmentPoints(lineSegment[startLS].p,lineSegment[iLS - 1].q);
                        lineSegment[iLS++] = (LineSegmentPoints) ls;
                    }
                }
                nFour = 0;
                currentF = i;
            }
            eqSlope = false;
        }
        nSeg = iLS;
        cutArray(iLS);


        return lineSegments;
    }

    private void cutArray(int iLS) {
//        LineSegmentPoints[] tLS = new LineSegmentPoints[iLS];
//        for (int i = 0; i < iLS; i++) {
//            tLS[i] = lineSegment[i];
//        }
//        lineSegment = null;
        //sortPoints(tLS);
        lineSegments = new LineSegment[iLS];
        for (int i = 0; i < iLS; i++) {
            lineSegments[i] = lineSegment[i].getSegment();
        }
    }

    private void exch(Point[] points, int lo, int hi) {

        Point swp = points[lo];
        points[lo] = points[hi];
        points[hi] = swp;
    }

    private void exch(LineSegmentPoints[] ls, int lo, int hi) {

        LineSegmentPoints swp = ls[lo];
        ls[lo] = ls[hi];
        ls[hi] = swp;
    }

    private int partition(Point[] points, int lo, int hi) {


        int i = lo, j = hi + 1;
        while (true)
        {
            while ((points[++i].compareTo(points[lo])) < 0)
                if (i == hi) break;
            while (points[lo].compareTo(points[--j]) < 0)
                if (j == lo) break;

            if (i >= j) break;
            exch(points, i, j);
        }
        exch(points, lo, j);
        return j;

    }



    private void sort(Point[] points) {

        sort(points, 0, points.length - 1);

    }

    private void sort(Point[] points, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(points, lo, hi);
        sort(points, lo, j - 1);
        sort(points, j + 1, hi);
    }

    private void sort(LineSegmentPoints[] a, int n)
    {
        LineSegmentPoints[] aux = new LineSegmentPoints[a.length];
        sort(a, aux, 0, n);
    }

    private void sort(LineSegmentPoints[] a, LineSegmentPoints[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(LineSegmentPoints[] a, LineSegmentPoints[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if ((aux[j].slope - aux[i].slope) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }


    private class LineSegmentPoints  {
        private Point p;
        private Point q;
        private LineSegment lineSegment;
        private double slope;

        public LineSegmentPoints(Point p, Point q) {
            this.p = p;
            this.q = q;
            if (this.p.compareTo(this.q) < 0)
                this.lineSegment = new LineSegment(p, q);
            else
                this.lineSegment = new LineSegment(q, p);

            this.slope = this.p.slopeTo(this.q);
        }

        boolean isCol(LineSegmentPoints that) {
            return (this.slope == that.slope || (this.slope + that.slope == Double.NaN)) && ((p.compareTo(that.p) * p.compareTo(that.q) * q.compareTo(that.p) * q.compareTo(that.q) == 0) || p.slopeTo(that.p) == p.slopeTo(that.q) || p.slopeTo(that.p) + p.slopeTo(that.q) == Double.NaN);
        }

        public LineSegment getSegment() {
            return lineSegment;
        }


        public boolean include(LineSegmentPoints that) {
            if (this.p.compareTo(that.p) == 0) {
                if (this.p.compareTo(this.q) * that.p.compareTo(that.q) >= 0) {
                    return (this.p.compareTo(this.q) * that.q.compareTo(this.q) >= 0);
                }
            }
            else if (this.q.compareTo(that.q) == 0) {
                if (this.q.compareTo(this.p) * that.q.compareTo(that.p) > 0) {
                    return (this.q.compareTo(this.p) * that.p.compareTo(this.p) >= 0);
                }
            }
            return false;
        }
    }


}
