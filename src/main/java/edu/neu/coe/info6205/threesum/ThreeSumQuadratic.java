package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();

        int left = (j > 0) ? j - 1 : 0;
        int right = j + 1;

        while (left >= 0 && right < length && left < right) {
            int sum = a[left] + a[j] + a[right];

            if (sum == 0) {
                triples.add(new Triple(a[left], a[j], a[right]));
                while (left > 0 && a[left] == a[left - 1]) {
                    left--;
                }
                while (right < length - 1 && a[right] == a[right + 1]) {
                    right++;
                }

                left--;
                right++;
            } else if (sum < 0) {
                right++;
            } else {
                left--;
            }
        }

        return triples;

        // TO BE IMPLEMENTED  : for each candidate, test if a[i] + a[j] + a[k] = 0.














// throw new RuntimeException("implementation missing");
    }

    private final int[] a;
    private final int length;
}