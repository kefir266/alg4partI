import jdk.nashorn.internal.ir.Block;

/**
 * Created by dmitrij on 11.10.2015.
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {

    private int[][] blocks;
    private int dimN;
    private int nN;
    private int outN;

    public Board(int[][] blocks) {         // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        nN = blocks.length;
        dimN = (int) Math.sqrt((double) nN);
    }
    public int dimension() {               // board dimension N

        return dimN;
    }
    public int hamming() {                   // number of blocks out of place

        int sum = 0;
        int num = 0;
        for (int i = 0; i < dimN; i++)
            for (int j = 0; j < dimN; j++) {
                num = this.blocks[i][j];
                int ii = num / dimN;
                int jj = nN - ii;
                sum += Math.min(Math.abs(i-ii),Math.abs(j -jj));
            }
        return sum;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal

        int sum = 0;
        int num = 0;
        for (int i = 0; i < dimN; i++)
            for (int j = 0; j < dimN; j++) {
                num = this.blocks[i][j];
                int ii = num / dimN;
                int jj = nN - ii;
                sum += Math.abs(i-ii) + Math.abs(j -jj);
            }
        return sum;
    }
    public boolean isGoal() {               // is this board the goal board?

        boolean f = false;
        int num = 0;

        for (int i = 0; i < dimN; i++)
            for (int j = 0; j < dimN; j++) {
                int ii = num / dimN;
                int jj = nN - ii;
                if (ii != i || jj != j) {
                    f = false;
                    break;
                }
                else f = true;
            }

        return f;
    }
    public Board twin() {                  // a board that is obtained by exchanging any pair of blocks

        Board twinBoard = new Board(blocks);

        int prevN = 0, curN = 0;
        int i = 0 , j = 0;
        for (int ii = 0; ii < nN; ii++) {
            if (prevN != 0) {
                i = (int) ii / dimN;
                j = ii - i * dimN;
                curN = twinBoard.blocks[i][j];
                if (curN != 0) {
                    twinBoard.blocks[i][j] = prevN;
                    i = (ii - 1) / dimN;
                    j = (ii - 1) - i * dimN;
                    twinBoard.blocks[i][j] = curN;
                    break;
                }
                else prevN = curN;
            }
        }

        return twinBoard;
    }

    public boolean equals(Object y) {      // does this board equal y?
        boolean f = false;
        if (y instanceof Board && this.dimN == ((Board) y).dimN ) {
            Board obj = (Board) y;
            f = true;
            for (int i = 0; i < dimN; i++)
                for (int j = 0; j < dimN; j++)
                    f = f && (this.blocks[i][j] == obj.blocks[i][j]);
        }

    return f;

    }
    public Iterable<Board> neighbors() {    // all neighboring boards

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }
    public String toString() {              // string representation of this board (in the output format specified below)
        String s = Integer.toString(dimension())+"/n";

        for (int i = 0; i < dimN; i++) {
            for (int j = 0; j < dimN; j++)
                s += String.valueOf(this.blocks[i][j]);
            s += "/n";
        }
        return s;
    }

    public static void main(String[] args) {// unit tests (not graded)

    }


}
