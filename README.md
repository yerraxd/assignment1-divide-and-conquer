# Divide-and-Conquer Algorithms: Implementation and Experimental Analysis

## A. Project Overview

### Purpose of the Assignment

The purpose of this assignment is to implement and experimentally analyze several divide-and-conquer algorithms in Java. The project focuses on sorting, selection, and computational geometry algorithms, with attention to recursion depth, execution time, input structure, and theoretical complexity.

The experiments compare the theoretical behavior of the algorithms with their practical performance on different input sizes and input structures.

### Implemented Algorithms

The project contains four main algorithms:

1. **Merge Sort** — a divide-and-conquer sorting algorithm with an insertion-sort cutoff for small subarrays.
2. **QuickSort** — randomized QuickSort using a random pivot and smaller-first recursion.
3. **Deterministic Selection** — the Median-of-Medians algorithm for finding the `k`-th smallest element.
4. **Closest Pair of Points** — a divide-and-conquer algorithm for finding the minimum Euclidean distance between two points.

The experiments use the following input types:

- Random
- Sorted
- Reverse-sorted
- Duplicate-heavy

For Closest Pair, two input structures are tested:

- Random points
- Adversarial vertical-line points

A 20-iteration warm-up is performed before the actual measurements in order to allow the JVM JIT compiler to optimize the code.

---

# B. Algorithm Analysis

## 1. Merge Sort

### How It Works

Merge Sort recursively divides the array into two halves until the subarray is small enough. In this implementation, subarrays of size `16` or smaller are sorted using insertion sort.

After both halves are sorted, the `merge()` method combines them into one sorted segment by comparing the smallest remaining element of each half.

The implementation uses one auxiliary buffer array instead of allocating a new array for every merge.

### Complexity

| Case | Time Complexity | Space Complexity |
|---|---:|---:|
| Best | `O(n log n)` | `O(n)` |
| Average | `O(n log n)` | `O(n)` |
| Worst | `O(n log n)` | `O(n)` |
| Recursion depth | `O(log n)` | |

The insertion-sort cutoff does not change the asymptotic complexity. It can improve practical performance for small subarrays by reducing recursive overhead.

### Recurrence

The main recurrence is:

`T(n) = 2T(n/2) + O(n)`

Using the Master Theorem:

- `a = 2`
- `b = 2`
- `f(n) = O(n)`
- `n^(log_b a) = n`

Therefore:

`T(n) = O(n log n)`

---

## 2. QuickSort

### How It Works

QuickSort selects a random pivot using `ThreadLocalRandom`.

The `partition()` method places elements smaller than the pivot to the left and the remaining elements to the right.

A special part of the implementation is the **smaller-first recursion strategy**. After partitioning:

- the smaller partition is processed recursively;
- the larger partition is processed using the existing loop.

This limits the maximum recursion depth.

### Complexity

| Case | Time Complexity | Stack Space |
|---|---:|---:|
| Best / balanced | `O(n log n)` | `O(log n)` |
| Expected | `O(n log n)` | `O(log n)` |
| Worst | `O(n²)` | `O(log n)` with smaller-first recursion |

The random pivot makes highly unbalanced partitions less likely on average, but randomized QuickSort still has a theoretical worst case of `O(n²)`.

### Recurrence

For a balanced partition:

`T(n) = 2T(n/2) + O(n)`

which gives:

`T(n) = O(n log n)`

For a worst-case partition:

`T(n) = T(n-1) + O(n)`

which gives:

`T(n) = O(n²)`

### Why Smaller-First Recursion Helps

The smaller-first strategy does not change the running-time complexity of QuickSort. Its main purpose is to control recursion depth.

At every recursive step, the recursively processed partition has size at most half of the current partition. Therefore, the recursion stack grows by at most:

`O(log n)`

The larger partition is handled iteratively by the `while` loop, so it does not create another recursive stack frame.

---

## 3. Deterministic Selection — Median of Medians

### How It Works

The algorithm finds the `k`-th smallest element without fully sorting the array.

The implementation:

1. Divides the array into groups of five.
2. Sorts each group using insertion sort.
3. Takes the median of every group.
4. Recursively finds the median of those medians.
5. Uses that value as the pivot.
6. Performs a 3-way partition into:
   - elements smaller than the pivot;
   - elements equal to the pivot;
   - elements greater than the pivot.
7. Recursively continues only in the part containing `k`.

The 3-way partition is especially useful for duplicate-heavy inputs because all elements equal to the pivot are handled at once.

### Complexity

| Case | Time Complexity | Space Complexity |
|---|---:|---:|
| Best | `O(n)` | `O(log n)` recursion |
| Average | `O(n)` | `O(log n)` recursion |
| Worst | `O(n)` | `O(log n)` recursion |

The algorithm is deterministic, so the linear worst-case guarantee does not depend on random pivot selection.

### Recurrence

The Median-of-Medians recurrence can be represented approximately as:

`T(n) <= T(n/5) + T(7n/10) + O(n)`

The first recursive call works on the medians of the groups.

The second recursive call works on at most about `70%` of the original array.

The coefficients of the recursive terms satisfy:

`1/5 + 7/10 = 0.9 < 1`

Therefore, the total amount of work across recursive levels is bounded by a geometric series multiplied by `n`.

Hence:

`T(n) = O(n)`

### Why Median-of-Medians Guarantees O(n)

The key property is that the chosen pivot is guaranteed to be reasonably close to the middle.

With groups of five, a constant fraction of the elements is guaranteed to be eliminated after each partition. Therefore, the recursive subproblem cannot remain almost as large as the original array.

This prevents the repeated `n, n-1, n-2, ...` behavior that produces `O(n²)` in ordinary QuickSelect.

---

## 4. Closest Pair of Points

### How It Works

The algorithm first sorts all points by their `x` coordinate.

Then it recursively divides the points into left and right halves.

For each half, the minimum distance is calculated recursively:

`dl = left minimum`

`dr = right minimum`

The current best distance is:

`d = min(dl, dr)`

After that, the algorithm constructs a vertical strip containing points whose `x` coordinate is less than `d` away from the dividing line.

The strip is sorted by `y` coordinate and only nearby points are compared.

For subarrays containing three or fewer points, brute force is used.

### Complexity of This Implementation

The classical divide-and-conquer Closest Pair algorithm can achieve:

`O(n log n)`

However, this implementation performs:

```java
Arrays.sort(strip, 0, stripCount, ...)
```

for the strip at every recursive level.

Therefore, the complexity of this specific implementation is:

| Case | Time Complexity | Space Complexity |
|---|---:|---:|
| Best / Average | approximately `O(n log² n)` | `O(n)` |
| Worst | `O(n log² n)` | `O(n)` |

The recurrence is approximately:

`T(n) = 2T(n/2) + O(n log n)`

because sorting the strip costs `O(n log n)` at each level.

Using the Master Theorem:

`T(n) = O(n log² n)`

A more optimized implementation could maintain the points ordered by `y`, reducing the complexity to the classical `O(n log n)`.

### Why It Is Faster Than O(n²)

A brute-force solution compares every pair of points:

`n(n-1)/2`

which is `O(n²)`.

The divide-and-conquer algorithm avoids checking all possible pairs. It recursively solves smaller problems and only checks points close to the division boundary.

Even though the current implementation is `O(n log² n)`, this is still asymptotically better than `O(n²)` for large inputs.

---

# C. Experimental Results

All execution times were measured using `System.nanoTime()` after 20 warm-up iterations.

The following values are from the experimental run used for this report.

## Merge Sort — Execution Time

Time is shown in milliseconds.

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 0.0207 | 0.0205 | 0.0158 | 0.0296 |
| 1,000 | 0.2582 | 0.1842 | 0.2038 | 0.5960 |
| 10,000 | 10.6469 | 3.3768 | 3.4881 | 6.3895 |
| 100,000 | 32.1967 | 9.5348 | 19.1974 | 47.9164 |

The recursion depth is determined by the divide-and-conquer structure and is almost independent of input ordering.

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 4 | 4 | 4 | 4 |
| 1,000 | 7 | 7 | 7 | 7 |
| 10,000 | 11 | 11 | 11 | 11 |
| 100,000 | 14 | 14 | 14 | 14 |

---

## QuickSort — Execution Time

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 0.0215 | 0.0155 | 0.0143 | 0.0237 |
| 1,000 | 0.2734 | 0.1858 | 0.2042 | 0.4062 |
| 10,000 | 3.3691 | 2.0911 | 2.1803 | 11.1289 |
| 100,000 | 32.4508 | 16.1964 | 18.9856 | 35.6224 |

Maximum recursion depth:

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 5 | 6 | 5 | 3 |
| 1,000 | 8 | 8 | 7 | 4 |
| 10,000 | 10 | 9 | 9 | 6 |
| 100,000 | 11 | 11 | 11 | 9 |

The depth remains relatively small even for `n = 100,000`, which demonstrates the practical benefit of the smaller-first recursion strategy.

---

## Deterministic Selection — Execution Time

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 0.0826 | 0.0587 | 0.0849 | 0.0326 |
| 1,000 | 0.1947 | 0.0934 | 0.1208 | 0.0566 |
| 10,000 | 2.2779 | 1.2800 | 1.6202 | 1.7652 |
| 100,000 | 27.3021 | 6.4014 | 8.7658 | 62.3782 |

Maximum recursion depth:

| n | Random | Sorted | Reverse | Duplicate-heavy |
|---:|---:|---:|---:|---:|
| 100 | 7 | 7 | 6 | 3 |
| 1,000 | 10 | 9 | 10 | 5 |
| 10,000 | 13 | 13 | 13 | 10 |
| 100,000 | 17 | 16 | 17 | 13 |

The measured recursion depth includes recursive calls made during the Median-of-Medians pivot construction.

---

## Closest Pair — Execution Time

| n | Random (ms) | Vertical Line (ms) |
|---:|---:|---:|
| 100 | 0.160 | 0.820 |
| 500 | 1.348 | 7.508 |
| 1,000 | 4.709 | 26.776 |
| 2,000 | 28.029 | 71.138 |
| 5,000 | 52.532 | 133.760 |
| 50,000 | 284.538 | 955.420 |

Maximum recursion depth:

| n | Random | Vertical Line |
|---:|---:|---:|
| 100 | 7 | 7 |
| 500 | 9 | 9 |
| 1,000 | 10 | 10 |
| 2,000 | 11 | 11 |
| 5,000 | 12 | 12 |
| 50,000 | 16 | 16 |

The vertical-line input produces more work in the strip-processing stage and is significantly slower than the random point distribution.

---

## Plots

### Time vs. Input Size

The project includes a plot showing how execution time changes as `n` increases.
<img width="722" height="344" alt="time" src="https://github.com/user-attachments/assets/95f18bd0-d2be-4a80-b9a8-9a9ce00cb3fb" />


### Recursion Depth vs. Input Size

The second plot shows how the maximum recursion depth changes as the input size increases.
<img width="728" height="350" alt="depth" src="https://github.com/user-attachments/assets/c73c2317-be5a-487a-a0ca-fb7c3e672513" />

---

# D. Discussion

## Do the Results Match the Theoretical Complexity?

Overall, the results are consistent with the expected asymptotic behavior.

Merge Sort shows increasing execution time close to `O(n log n)`, while its recursion depth grows logarithmically. The depth changes from `4` for `n = 100` to `14` for `n = 100,000`.

QuickSort also shows behavior consistent with expected `O(n log n)` performance for the tested inputs. Its recursion depth remains small because the larger partition is processed iteratively.

Deterministic Selection demonstrates near-linear growth for most inputs. For example, the random-input execution increases from `2.2779 ms` at `n = 10,000` to `27.3021 ms` at `n = 100,000`. The exact execution times are affected by implementation and JVM overhead, but the general growth is much closer to linear than to quadratic.

Closest Pair grows slower than a brute-force `O(n²)` algorithm, but the current implementation is theoretically `O(n log² n)` because the strip is sorted independently at every recursive level.

---

## How Does Input Structure Affect Performance?

Input structure has a visible effect on execution time.

For Merge Sort, the recursion structure is fixed, so input ordering does not change the recursion depth. However, the number of comparisons changes significantly. For example, at `n = 100,000`, the sorted input requires `744,016` comparisons, while the random input requires `1,639,363`.

QuickSort is more sensitive to input structure because partition quality directly affects performance. Random pivots reduce the probability of consistently unbalanced partitions. Duplicate-heavy data can still introduce extra work because this implementation uses a two-way partition rather than a 3-way partition.

For Deterministic Selection, duplicate-heavy input can benefit from the 3-way partition because values equal to the pivot are removed from further recursion. However, the `n = 100,000` duplicate-heavy experiment took significantly longer than the other input types. This shows that practical running time is affected not only by asymptotic complexity but also by constant factors and the exact behavior of the implementation.

For Closest Pair, the vertical-line dataset causes considerably more work in the strip stage. At `n = 50,000`, the random input takes `284.538 ms`, while the vertical-line input takes `955.420 ms`.

---

## Why Does Smaller-First Recursion Help QuickSort?

Smaller-first recursion reduces stack usage.

After partitioning, the algorithm recursively processes the smaller side and continues with the larger side using the loop. Since the recursive side has size at most half of the current subarray, the recursion depth is bounded by `O(log n)`.

This is important even when partitioning becomes unbalanced. Without this technique, a sequence of highly unbalanced partitions could produce very deep recursion and potentially a stack overflow.

The technique improves the space behavior of QuickSort but does not eliminate the `O(n²)` worst-case time complexity.

---

## Why Does Median-of-Medians Guarantee O(n)?

Median-of-Medians guarantees that the pivot is sufficiently close to the middle of the data.

After grouping elements into groups of five and taking their medians, the median of those medians provides a pivot that guarantees that a constant fraction of the elements is smaller and a constant fraction is larger.

Therefore, the recursive part of the algorithm operates on a subproblem of bounded fraction of the original input instead of potentially on `n - 1` elements.

The recurrence:

`T(n) <= T(n/5) + T(7n/10) + O(n)`

has linear total complexity.

---

## Why Is Divide-and-Conquer Closest Pair Faster Than O(n²) for Large Inputs?

A brute-force algorithm computes the distance between every pair of points, requiring approximately:

`n² / 2`

distance comparisons.

The divide-and-conquer approach avoids checking all pairs. It solves the left and right halves independently and checks only a limited set of candidate pairs near the dividing line.

This reduces the asymptotic complexity substantially compared with `O(n²)`.

In this implementation, repeated sorting of the strip results in `O(n log² n)` rather than the optimal `O(n log n)`, but it is still asymptotically better than quadratic complexity.

---

## Practical Factors Affecting Performance

The theoretical complexity does not completely determine measured execution time.

Several practical factors affect the results:

- **JVM JIT compilation** — Java code can become faster after repeated execution because the JVM compiles frequently executed methods into optimized machine code.
- **Warm-up effects** — the project performs 20 warm-up runs before collecting results.
- **CPU cache behavior** — sequential array access is generally cache-friendly, while object-heavy operations can introduce additional overhead.
- **Garbage Collection** — temporary arrays and objects can create allocation pressure and influence execution time.
- **Object overhead** — Closest Pair works with `Point` objects, which are more expensive to process than primitive `int` arrays.
- **Random number generation** — QuickSort and the generated datasets introduce additional runtime variability.
- **Operating system load** — background processes can affect wall-clock measurements.
- **Single-run measurement** — the current experiment records one timed execution for each case, so individual values can contain measurement noise.

Because of these factors, the experimental values should be interpreted as practical observations rather than exact universal benchmarks.

---

# E. Reflection

This assignment helped me understand divide-and-conquer algorithms from both the theoretical and implementation perspective. I became more familiar with how recursive problem decomposition affects time complexity and recursion depth. In particular, I learned that two algorithms can have similar theoretical running times but noticeably different practical performance because of implementation details such as memory access, allocation, recursion strategy, and input structure.

The main implementation challenges were handling recursion correctly, tracking maximum recursion depth, implementing the Median-of-Medians pivot selection, and making the Closest Pair algorithm work with coordinate-based data. Another important challenge was interpreting experimental results correctly. The measured execution time does not depend only on Big-O complexity; JVM optimization, cache behavior, garbage collection, and the structure of the input can all affect the final results. The experiments showed why theoretical analysis and practical benchmarking should be considered together.

---

# F. Screenshots

<img width="1920" height="1080" alt="results main" src="https://github.com/user-attachments/assets/8db70b86-57e3-4d07-989d-1f24010468e8" />
<img width="1920" height="1080" alt="test 2" src="https://github.com/user-attachments/assets/b60e6a02-08e4-4a9f-96a3-32b9085d5c3c" />
<img width="1920" height="1080" alt="test 1" src="https://github.com/user-attachments/assets/7433aea5-7b64-4476-bb5d-7cdc4cab96f3" />



# Conclusion

The experiments demonstrate the practical behavior of four important divide-and-conquer algorithms.

Merge Sort provides predictable `O(n log n)` performance and stable logarithmic recursion depth. Randomized QuickSort achieves strong practical performance while the smaller-first recursion strategy keeps stack usage at `O(log n)`. Median-of-Medians provides a deterministic linear-time guarantee for selection. Closest Pair reduces the amount of work compared with quadratic brute force, although the current implementation can be improved from `O(n log² n)` to `O(n log n)` by avoiding repeated sorting of the strip.

The experimental results generally agree with the theoretical analysis while also showing the importance of implementation details and the execution environment.
