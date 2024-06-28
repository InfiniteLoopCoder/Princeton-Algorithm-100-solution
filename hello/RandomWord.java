import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String result = "";
        String current;
        double i = 1;
        while (StdIn.isEmpty() == false) {
            current = StdIn.readString();
            if (StdRandom.bernoulli(1 / i)) {
                result = current;
            }
            i++;
        }
        StdOut.println(result);
    }
}
