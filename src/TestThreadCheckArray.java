import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console entry point for the two-thread subset-sum search.
 *
 * <p>Reads an integer sequence and target sum, stores the sequence in an
 * {@code ArrayList<Integer>}, creates shared search state, starts two
 * {@link ThreadCheckArray} workers, and prints the discovered subset when one
 * exists. The boolean selection array remains a primitive {@code boolean[]}
 * because it records whether each corresponding input position is selected.</p>
 */
public class TestThreadCheckArray {
    /**
     * Creates the console application entry-point object.
     */
    public TestThreadCheckArray() {
    }

    /**
     * Reads the sequence and target from standard input, runs both search
     * workers to completion, and prints the result.
     *
     * <p>Input consists of a sequence length, that many integer values, and
     * the target sum. The two workers divide the search according to whether
     * the final value is included or excluded. Their shared result is read
     * only after both threads have been joined.</p>
     *
     * @param args command-line arguments; not used by this program
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;
            System.out.println("Enter array size");
            int num  = input.nextInt();
            ArrayList<Integer> array = new ArrayList<>(num);
            System.out.println("Enter numbers for array");

            for (int index = 0; index < num; index++)
                array.add(input.nextInt());

            System.out.println("Enter number");
            num = input.nextInt();

            SharedData sd = new SharedData(array, num);

            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }
            System.out.println("Solution for b : " + sd.getB() + ",n = " + sd.getArray().size());
            System.out.print("I:    ");
            for(int index = 0; index < sd.getArray().size() ; index++)
                System.out.print(index + "    ");
            System.out.println();
            System.out.print("A:    ");
            for (int index : sd.getArray()) {
                System.out.print(index);
                int counter = 5;
                while (true) {
                    index = index / 10;
                    counter--;
                    if (index == 0) break;
                }
                for (int i = 0; i < counter; i++) System.out.print(" ");
            }
            System.out.println();
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index) System.out.print("1    ");
                else System.out.print("0    ");
            }
        }
    }
}
