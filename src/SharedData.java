import java.util.ArrayList;

/**
 * Stores the input sequence, target sum, and solution state shared by search
 * workers.
 */
public class SharedData {
    /** The input values being searched. */
    private ArrayList<Integer> array;
    /** Marks which input positions are part of the published solution. */
    private boolean[] winArray;
    /** Indicates whether any worker has found a solution. */
    private boolean flag;
    /** The target sum. */
    private final int b;

    /**
     * Creates shared state for a sequence and its target sum.
     *
     * @param array sequence of integer values to search
     * @param b target sum
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
    }

    /**
     * Returns the position markers for the discovered solution.
     *
     * @return the solution markers, or {@code null} if none have been published
     */
    public boolean[] getWinArray() { return winArray; }

    /**
     * Publishes the position markers for a discovered solution.
     *
     * @param winArray solution markers, one per input value
     */
    public void setWinArray(boolean[] winArray) { this.winArray = winArray; }

    /**
     * Returns the input sequence.
     *
     * @return the sequence as an {@code ArrayList<Integer>}
     */
    public ArrayList<Integer> getArray() { return array; }

    /**
     * Returns the target sum.
     *
     * @return the target sum
     */
    public int getB() { return b; }

    /**
     * Reports whether a worker has found a solution.
     *
     * @return {@code true} if a solution has been found; otherwise {@code false}
     */
    public boolean getFlag() { return flag; }

    /**
     * Updates the shared solution-found state.
     *
     * @param flag {@code true} if a solution has been found; otherwise {@code false}
     */
    public void setFlag(boolean flag) { this.flag = flag; }
}
