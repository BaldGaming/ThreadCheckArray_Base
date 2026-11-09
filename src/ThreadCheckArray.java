import java.util.ArrayList;

/**
 * Searches for a subset of integer values whose sum equals a target.
 *
 * <p>Each instance explores one half of the search space: the worker named
 * {@code thread1} starts with the final input value included, while the other
 * worker starts with it excluded. The remaining values are tested recursively
 * using include and exclude branches. Workers sharing the same
 * {@link SharedData} instance coordinate through its solution-found flag and
 * publish their selection in a primitive {@code boolean[]}.</p>
 *
 * <p>The search uses exponential time in the worst case and recursion depth
 * proportional to the number of input values.</p>
 */
public class ThreadCheckArray implements Runnable {
    /** Indicates whether this worker's search branch found a solution. */
    private boolean flag;
    /** Marks the input positions selected by this worker, if successful. */
    private boolean[] winArray;
    /** Shared sequence, target, completion flag, and result. */
    SharedData sd;
    /** Sequence of values examined by this worker as an {@code ArrayList<Integer>}. */
    ArrayList<Integer> array;
    /** Target sum that the selected values must match. */
    int b;

    /**
     * Creates a search worker and initializes its position markers.
     *
     * <p>The worker obtains the shared sequence and target while holding the
     * shared-state monitor. Its private selection array is sized to match the
     * number of values in the sequence.</p>
     *
     * @param sd shared sequence, target, and result state used by this worker
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
     * Recursively searches the first {@code n} values for the remaining sum.
     *
     * <p>For each value, this method first explores the branch that includes
     * it, subtracting its value from the required sum, and then explores the
     * branch that excludes it. If another worker has already found a
     * solution, this branch returns early. The selected positions are recorded
     * in this worker's {@code winArray}.</p>
     *
     * <p>This method expects {@code n} to be at least one; the worker's
     * {@link #run()} method handles the one-element input separately.</p>
     *
     * @param n number of values in the current search prefix
     * @param b remaining sum required from the first {@code n} values
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
     * Runs this worker's assigned search branch.
     *
     * <p>The worker named {@code thread1} begins with the final input value
     * included; the other worker begins with it excluded. For a one-element
     * input, the value is checked directly. If this worker finds a solution,
     * it records the corresponding position markers and publishes them to the
     * shared state.</p>
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
