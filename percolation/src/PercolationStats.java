//import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;


/**
 * Created by dmitrij on 10.09.2015.
 */
public class PercolationStats {
    private int numPercolate = 0;
    private double[] ex;
    private int T;

    private Percolation percolation;

    public PercolationStats(int N, int T) {

        this.T = T;
        ex = new double[T];
        int x = 0;
        int y = 0;
        boolean prc;

//        PercolationVisualizer pv = new PercolationVisualizer();

        for (int i = 0; i < T; i++) {

            percolation = new Percolation(N);

//            percolation.randomFilling(StdRandom.uniform(1, N * N - N));
//
//            System.out.print("Fulling ratio " + percolation.fullingRatio());


            prc = false;
            int nn = 0;
            while (nn < (N * N) && !prc) {
                x = StdRandom.uniform(1, N + 1);
                y = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    nn++;
                }
                else {
                    continue;
                }


                if (percolation.percolates()) {
                    numPercolate++;
                    ex[i] = (double) nn / (N * N);
                    prc = true;
                    //System.out.println(" percolates");
                } else {
                    ex[i] = 0;
                }

//                pv.draw(percolation, N);
//                StdDraw.show(0);
            }
            percolation = null;

        }
    }    // perform T independent experiments on an N-by-N grid

    public double mean() {
        return  StdStats.mean(ex);
    }                      // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(ex);
    }                   // sample standard deviation of percolation threshold

    public double confidenceLo() {


        return (mean() - (1.96 * stddev() / Math.sqrt(T)));

    }              // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return (mean() + (1.96 * stddev() / Math.sqrt(T)));
    }             // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Must enter N and T");
        }

        int N = 0;
        int T = 0;
        try {
            N = Integer.parseInt(args[0].trim());
            T = Integer.parseInt(args[1].trim());
        }
        catch (NumberFormatException exc) { throw new IllegalArgumentException("N and T must be >0");
        }

        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N and T must be >0");

        PercolationStats perSt = new PercolationStats(N, T);

        System.out.println("mean                    =" + perSt.mean());
        System.out.println("stddev                  =" + perSt.stddev());
        System.out.println("95% confidence interval =" + perSt.confidenceLo() + ", " + perSt.confidenceHi());
        System.out.println();
    }



//    public double fullingRatio() {
//        int count = 0;
//        for (int x = 1; x <= N; x++)
//            for (int y = 1; y <= N; y++)
//                count += fOpen[x][y];
//        return (double) count / (N * N);
//    }
}