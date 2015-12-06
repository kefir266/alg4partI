/**
 * Created by dmitrij on 28.09.2015.
 */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

//import java.util.DoubleSummaryStatistics;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Objects;

public class BruteCollinearPoints {

    private LineSegmentPoints[] lineSegment;
    private LineSegment[] lineSegments;
    //private double[] lineSegmentSlope;
    private int nSeg = 0;


    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

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
    }

    public           int numberOfSegments() {       // the number of line segments
        return nSeg;
    }

    public LineSegment[] segments() {
        int nFour = 0;
        int currentF = 0;
        int iLS = 0;
        boolean eqSlope = false;
        for (int i = 1; i < nSeg; i++) {
            //int ii = i;
            //int ij = currentF;
            while (lineSegment[i].slope == lineSegment[currentF].slope && currentF < nSeg) {
                if (lineSegment[i].isCol(lineSegment[currentF])) {
                    nFour++;
                    eqSlope = true;
                    currentF++;
                    continue;
                }
                    else {
                    i++;
                    currentF++;
                }
                if (!eqSlope || (i == nSeg - 1)) {
                    if (nFour >= 3) {
                        for (int j = currentF; j < i; j++) {
                            for (int cmp = currentF; cmp < j; cmp++) {
                                if ((lineSegment[cmp] != null && lineSegment[j] != null)
                                        && (lineSegment[cmp].p.compareTo(lineSegment[j].p) == 0
                                        || lineSegment[cmp].q.compareTo(lineSegment[j].q) == 0)) {
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

//                        LineSegmentPoints ls = new LineSegmentPoints(lineSegment[startLS].p, lineSegment[iLS - 1].q);
//                        lineSegment[iLS++] = (LineSegmentPoints) ls;

                            LineSegmentPoints ls = new LineSegmentPoints(lineSegment[startLS].p,lineSegment[iLS - 1].q);
                            iLS = startLS++;
                            lineSegment[iLS++] = (LineSegmentPoints) ls;
                            for (int j = startLS; j <= i - 1; j++) {
                                lineSegment[j] = null;
                            }
                        }
                    }
                    nFour = 0;
                    currentF = i;
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
        return (this.slope == that.slope )
                && ((p.compareTo(that.p) * p.compareTo(that.q) * q.compareTo(that.p) * q.compareTo(that.q) == 0)
                || p.slopeTo(that.p) == p.slopeTo(that.q) //|| p.slopeTo(that.p) + p.slopeTo(that.q) == Double.NaN
        );
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


    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point pp = new Point(0, 7);
        Point q = new Point(0, 8);
        Point r = new Point(0, 3);

        StdOut.println(pp.slopeTo(q));
        StdOut.println(pp.slopeOrder().compare(q,r));
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.setPenRadius();
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);

            segment.draw();
        }
        StdOut.println(collinear.lineSegments.length);
    }
}
