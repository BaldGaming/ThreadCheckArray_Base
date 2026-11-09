import java.util.ArrayList;

/**
 * Holds the input sequence, target sum, and shared result state for the
 * subset-sum search.
 *
 * <p>The input values are stored as an {@code ArrayList<Integer>}. The
 * published selection is a separate {@code boolean[]} with one entry per
 * input position. This class does not synchronize its accessors; search
 * workers coordinate access to shared state using this instance as a monitor,
 * and the calling thread waits for worker completion before reading the
 * result.</p>
 */
public class SharedData {
    /** The input values being searched, in their original order. */
    private ArrayList<Integer> array;
    /** Marks which input positions are part of the published solution. */
    private boolean[] winArray;
    /** Indicates whether any worker has found and published a solution. */
    private boolean flag;
    /** The immutable target sum for this search. */
    private final int b;

    /**
     * Creates shared state for a sequence and its target sum. The supplied
     * list is retained as the shared input sequence.
     *
     * @param array sequence of integer values to search, in input order
     * @param b target sum that a subset of {@code array} must equal
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
    }

    /**
     * Returns the selection markers published by a worker.
     *
     * <p>Each array position corresponds to the value at the same position in
     * {@link #getArray()}. A value of {@code true} means that input value is in
     * the solution.</p>
     *
     * @return the selected-position markers, or {@code null} before a worker
     *         publishes a solution
     */
    public boolean[] getWinArray() { return winArray; }

    /**
     * Publishes the selected-position markers for a discovered solution.
     *
     * @param winArray boolean markers, one per input value; {@code true}
     *                 indicates that the value is selected
     */
    public void setWinArray(boolean[] winArray) { this.winArray = winArray; }

    /**
     * Returns the input sequence shared by the search workers.
     *
     * @return the input values as an {@code ArrayList<Integer>}
     */
    public ArrayList<Integer> getArray() { return array; }

    /**
     * Returns the target sum for this search.
     *
     * @return the required subset sum
     */
    public int getB() { return b; }

    /**
     * Reports whether a worker has found a solution.
     *
     * @return {@code true} after a worker signals success; {@code false}
     *         otherwise
     */
    public boolean getFlag() { return flag; }

    /**
     * Updates the shared solution-found state.
     *
     * @param flag {@code true} if a worker found a solution; {@code false}
     *             otherwise
     */
    public void setFlag(boolean flag) { this.flag = flag; }
}
