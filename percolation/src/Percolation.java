
/**
 * Created by dmitrij on 25.01.2015.
 */

//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private short[] mGrid;
    private byte[][] fOpen;

    public Percolation(int N) {
        if (N > 0) {
        // create N-by-N grid, with all sites blocked
            this.N = N;
            mGrid = new short[N * N + N]; //x,y
            fOpen = new byte[N][N];
            //x,y flag  1-open, 2-close (y=0-top y=N+1-bottom -always opened)

            initGrid();
        }
        else {
            throw new IllegalArgumentException("N must be > 0");
        }
    }

    private void initGrid() {    //fulling cells by periodical numbers
        for (int x = 1; x <= N; x++)                                               //1.N+1...
           for (int y = 1; y <= N; y++)                                             //....
               mGrid[getLinearPosition(x, y)] = getLinearPosition(x, y);    //N.2N...N*N
        //for(int x=0; x <N; x++) fOpen[0][x]=1;
    }

    public void open(int y, int x) {
        if ((x <= N) && (y <= N) && (x > 0) && (y > 0)) {
            fOpen[x - 1][y - 1] = 1;
            isFull(y, x);
            percolates();
        }
        else
            throw new IndexOutOfBoundsException("Index out");
    }          // open site (row i, column j) if it is not open already

    public boolean isOpen(int y, int x) {
        if ((x <= N) && (y <= N) && (x > 0) && (y > 0)) // 0 and n+1 are possible for Y
            return (fOpen[x - 1][y - 1] == 1);
        else throw new IndexOutOfBoundsException("Index out");

    }     // is site (row i, column j) open?

    private boolean isFull(int linearPosition) {
        return (mGrid[linearPosition] == 0);
    }

    public boolean isFull(int y, int x) {
        if ((x <= N) && (y <= N) && (x > 0) && (y > 0)) {
            if (isOpen(y, x)) {
                int linearPosition = getLinearPosition(y, x);
                if (isFull(linearPosition))
                    return true;
                else
                    //optimized for change the order in which methods are called
                    if (y == 1 || isFull(getLinearPosition(y - 1, x))) {
                        setFull(linearPosition);
                        return true;
                    }
                    else
                        if (x > 1 && isFull(getLinearPosition(y, x - 1))) {
                            setFull(linearPosition);
                            return true;
                        }
                        else
                            if (y < N && isFull(getLinearPosition(y + 1, x))) {
                                setFull(linearPosition);
                                return true;
                            }
                            else
                                if (x < N && isFull(getLinearPosition(y, x + 1))) {
                                    setFull(linearPosition);
                                    return true;
                                }



////                                else return false;
//                    return isFull(y , x);
//                }
//            else
//                return false;

            }
        }
        else throw new IndexOutOfBoundsException("Index out");

        return false;
    }     // is site (row i, column j) full?


    public boolean percolates() {
        boolean mPerc = false;
        int linearPosition = 0;
        //boolean opened = true;


        //for (int nuberOfPassing = 0; nuberOfPassing < this.N && !mPerc && opened; nuberOfPassing++ ) {
        //    opened = false;
            for (int y = 1; y <= N; y++) {  //
                for (int x = 1; x <= N; x++) {
                    //if (isFull(y, x)) continue;
                    linearPosition = getLinearPosition(y, x);

                    if (!isOpen(y, x)) continue;

                        if (y == 1)
                            setFull(linearPosition);
                        else if  (isOpen(y - 1, x))
                            union(getLinearPosition(y - 1, x), linearPosition);


                    if (x > 1 && isOpen(y, x) && isOpen(y, x - 1)) {
                        union(getLinearPosition(y, x - 1), linearPosition);
                        //opened = true;
                    }
                    if ((y == N) && isFull(y, x)) { //&& connected(0,getLinearPosition(x,y), y)
                        mPerc = true;
                        //break;
                    }
                }
            }
       // }
        return mPerc;
    }             // does the system percolate?

    private short getLinearPosition(int y, int x) {
        return (short) ((x - 1) + y * N);
    }

//    private int root(int i)
//    {
//        int j = i;
//        while (i != mGrid[i])
//            j = mGrid[i];
//        return j;
//    }
//
//    private void union(int p, int q)
//    {
//
//        int i = root(p);
//        int j = root(q);
//        if (i < j)
//            mGrid[q] = i;
//        else
//            mGrid[p] = j;
//
//    }

    private void setFull(int linearPosition) {
        union(0, linearPosition);
    }
//
//    public boolean connected(int p, int q, int y)
//    {
//        if (y==1) return true;
//
//        return root(p) == root(q);
//    }

//    private boolean connected(int p, int q, int y) {
//
//        return mGrid[p] == mGrid[q];
//
//    }

    private void union(int p, int q) {
//        if (y == 1){
//            int qid = mGrid[q];
//            for (int i = 0; i < mGrid.length; i++)
//                if (mGrid[i] == qid)
//                    mGrid[i] = 0;
//        }
//        else {
            short pid = mGrid[p];
            short qid = mGrid[q];
            if (pid == qid) return;
            for (int i = N; i < mGrid.length; i++) {
                if (pid < qid) {
                    if (mGrid[i] == qid)
                        mGrid[i] = pid;
                }
                else {
                if (mGrid[i] == pid)
                    mGrid[i] = qid; }
             }
//        }
    }


    public static void main(String[] args) {

    }   // test client (optional)
}
