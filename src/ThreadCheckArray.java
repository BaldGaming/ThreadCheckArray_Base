import java.util.ArrayList;

/**
 * Searches the input sequence for a subset whose values sum to a target.
 * Instances sharing the same {@link SharedData} coordinate through its
 * solution-found flag.
 */
public class ThreadCheckArray implements Runnable {
    /** Indicates whether this worker has found a solution. */
    private boolean flag;
    /** Marks the sequence positions selected by this worker. */
    private boolean[] winArray;
    /** Shared input and result state. */
    SharedData sd;
    /** Sequence of values examined by this worker. */
    ArrayList<Integer> array;
    /** Target sum to match. */
    int b;

    /**
     * Creates a worker using the sequence and target held in shared state.
     *
     * @param sd shared sequence, target, and result state
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }

    /**
     * Recursively searches the first {@code n} values, considering each value
     * either included in or excluded from the current sum.
     *
     * @param n number of values in the current search prefix
     * @param b remaining sum to match
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        if (n == 1) {
            if(b == 0 || b == array.get(n-1)) {
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
            if (b == array.get(n-1)) winArray[n-1] = true;
            return;
        }

        rec(n-1, b - array.get(n-1));
        if (flag) winArray[n-1] = true;
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        rec(n-1, b);
    }

    /**
     * Starts this worker's search. The worker named {@code thread1} begins by
     * including the last value; the other worker begins by excluding it.
     * Publishes this worker's position markers if it finds a solution.
     */
    public void run() {
        if (array.size() != 1)
            if (Thread.currentThread().getName().equals("thread1"))
                rec(array.size()-1, b - array.get(array.size() - 1));
            else
                rec(array.size()-1, b);
        if (array.size() == 1)
            if (b == array.get(0) && !flag) {
                winArray[0] = true;
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
        if (flag) {
            if (Thread.currentThread().getName().equals("thread1"))
                winArray[array.size() - 1] = true;
            synchronized (sd) { sd.setWinArray(winArray); }
        }
    }
}
