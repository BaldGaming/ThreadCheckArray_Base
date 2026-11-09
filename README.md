# Multithreaded Subset Sum

A small Java console program that searches for a subset of integer values whose
sum equals a target. Two worker threads split the search into separate branches
and coordinate through a shared state object.

## Requirements

- Java Development Kit (JDK) with `javac`, `java`, and `javadoc` on `PATH`
- Git (for working with the project history and collaboration branches)

The project uses the default Java package and has no external dependencies.

## Build and run

From the repository root, compile the three source files and start the console
program:

```powershell
New-Item -ItemType Directory -Force out
javac -d out src\SharedData.java src\ThreadCheckArray.java src\TestThreadCheckArray.java
java -cp out TestThreadCheckArray
```

Enter the number of values, the values themselves, and the target sum. For
example:

```text
Enter array size
4
Enter numbers for array
1 2 3 7
Enter number
5
```

The program prints a binary selection row (`C`) for a found subset. In this
example, the values `2` and `3` make the target `5`. If no solution is found,
the program prints `Sorry`.

## Search algorithm and thread coordination

`ThreadCheckArray` implements `Runnable` and recursively explores include/exclude
choices for the input values. For each value, the recursive search considers
the branch that subtracts the value from the remaining target (include) and the
branch that leaves the target unchanged (exclude). The worker records included
positions in its private `boolean[] winArray`.

The two workers partition the search using the final input value:

- The thread named `thread1` starts with that value included.
- The other thread starts with that value excluded.

Together, those branches cover the possible subsets without both threads
starting from the same choice. The search is exhaustive, with worst-case
exponential time, `O(2^n)`, and recursion depth `O(n)`.

Both workers share one `SharedData` instance. Its `flag` indicates that a
solution has been found, and its `winArray` holds the published selection.
Workers synchronize on that shared object when checking or setting the flag
and when publishing their result. Once a worker finds a solution, it sets the
shared flag; the other worker checks the flag during recursion and can stop
early. Each worker's local `flag` and selection array are separate, while the
shared flag and result communicate the outcome. The runner joins both threads
before reading and printing the result.

## `ArrayList<Integer>` refactoring

The input sequence is represented as `ArrayList<Integer>` throughout the
program. `TestThreadCheckArray` reads each value into the list,
`SharedData` stores the list alongside the target and result state, and
`ThreadCheckArray` reads values by index during recursion. This replaces the
fixed-size primitive `int[]` representation with a generic, dynamically sized
collection; Java's `Integer` wrapper type is used because Java collections
store objects rather than primitive `int` values.

## Javadoc

The source types and documented API members include Javadoc comments describing
their purpose, parameters, and return values. Generate browsable HTML
documentation from the repository root with:

```powershell
javadoc -d docs src\SharedData.java src\ThreadCheckArray.java src\TestThreadCheckArray.java
```

Use `{@code ...}` for code fragments such as generic types inside Javadoc, so
angle brackets are not parsed as HTML tags.

## Authors and Git collaboration

The Git history records contributions by **Itay Rosen** and **Osher Amsalem**.
Itay's commits on `main` include the `SharedData`/`ThreadCheckArray`
`ArrayList<Integer>` refactor and Javadoc. Osher's work on
`feature/arraylist-refactor-b` includes changes to `TestThreadCheckArray` and
`ThreadCheckArray`. The feature branch was merged into `main` by Itay. The
branch history illustrates collaborative development: parallel work on
separate branches, integration, and merge-conflict resolution.

## License

This project is distributed under the **Braude College Academic License** for
instructional use in the Department of Software Engineering at Braude College
of Engineering. The course materials state that distribution, publication, or
sharing outside the official course boundary is prohibited. This is an
academic-use designation, not a standard open-source license; consult the
course or College for the authoritative license terms and permissions.
