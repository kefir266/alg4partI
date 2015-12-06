import edu.princeton.cs.algs4.StdIn;

//import java.io.IOException;

/**
 * Created by dmitrij on 24.09.2015.
 */
public class Subset {

    public static void main(String[] args)  {
        if (args.length > 0) {
            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                queue.enqueue(StdIn.readString());
            }
            for (int i = 0; i < Integer.parseInt(args[0]) && i < (queue.size()); i++) {
                System.out.println(queue.dequeue());
            }
        }
    }
}
