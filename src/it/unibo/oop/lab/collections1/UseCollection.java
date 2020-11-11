package it.unibo.oop.lab.collections1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Example class using {@link java.util.List} and {@link java.util.Map}.
 * 
 */
public final class UseCollection {

    private UseCollection() {
    }

    /**
     * @param s
     *            unused
     */
    public static void main(final String... s) {
        /*
         * 1) Create a new ArrayList<Integer>, and populate it with the numbers
         * from 1000 (included) to 2000 (excluded).
         */
    	ArrayList<Integer> first = new ArrayList<>();
    	for(int i = 1000; i < 2000; i++) {
    		first.add(i);
    	}
        /*
         * 2) Create a new LinkedList<Integer> and, in a single line of code
         * without using any looping construct (for, while), populate it with
         * the same contents of the list of point 1.
         */
    	LinkedList<Integer> second = new LinkedList<>();
    	second.addAll(first);
        /*
         * 3) Using "set" and "get" and "size" methods, swap the first and last
         * element of the first list. You can not use any "magic number".
         * (Suggestion: use a temporary variable)
         */
    	int lastIndexInFirst = first.size() - 1;
    	int lastInFirst = first.get(lastIndexInFirst);
    	first.set(lastIndexInFirst, first.get(0));
    	first.set(0, lastInFirst);
        /*
         * 4) Using a single for-each, print the contents of the arraylist.
         */
    	for(int i: first) {
    		System.out.println(i);
    	}
        /*
         * 5) Measure the performance of inserting new elements in the head of
         * the collection: measure the time required to add 100.000 elements as
         * first element of the collection for both ArrayList and LinkedList,
         * using the previous lists. In order to measure times, use as example
         * TestPerformance.java.
         */
        long startingTime = System.currentTimeMillis();
        for(int i = 0; i < 100_000; i++) {
        	first.add(0, 0);
        }
        long endingTime = System.currentTimeMillis();
        System.out.println("It took " + (endingTime - startingTime) +
        		"ms to insert 100.000 elements into an ArrayList");
        
        startingTime = System.currentTimeMillis();
        for(int i = 0; i < 100_000; i++) {
        	second.add(0, 0);
        }
        endingTime = System.currentTimeMillis();
        System.out.println("It took " + (endingTime - startingTime) +
        		"ms to insert 100.000 elements into a LinkedList");
        /*
         * 6) Measure the performance of reading 1000 times an element whose
         * position is in the middle of the collection for both ArrayList and
         * LinkedList, using the collections of point 5. In order to measure
         * times, use as example TestPerformance.java.
         */
        final int numberOfItems = 1000;
        final int firstMiddlePoint = first.size() / 2;
        final int secondMiddlePoint = second.size() / 2;
        final int firstStart = firstMiddlePoint - numberOfItems / 2;
        final int secondStart = secondMiddlePoint - numberOfItems / 2;
        
        startingTime = System.currentTimeMillis();
        for(int i = firstStart; i < firstStart + numberOfItems; i++) {
        	first.get(i);
        }
        endingTime = System.currentTimeMillis();
        System.out.println("It took " + (endingTime - startingTime) +
        		"ms to read " + numberOfItems + " elements from an ArrayList");
        
        startingTime = System.currentTimeMillis();
        for(int i = secondStart; i < secondStart + numberOfItems; i++) {
        	second.get(i);
        }
        endingTime = System.currentTimeMillis();
        System.out.println("It took " + (endingTime - startingTime) +
        		"ms to read " + numberOfItems + " elements from a LinkedList");


        /*
         * 7) Build a new Map that associates to each continent's name its
         * population:
         * 
         * Africa -> 1,110,635,000
         * 
         * Americas -> 972,005,000
         * 
         * Antarctica -> 0
         * 
         * Asia -> 4,298,723,000
         * 
         * Europe -> 742,452,000
         * 
         * Oceania -> 38,304,000
         */
        /*
         * 8) Compute the population of the world
         */
        final Map<String, Long> continentsPopulation = Map.of(
        		"Africa", 1_110_635_000L,
        		"Americas", 972_005_000L,
        		"Antarctica", 0L,
        		"Asia", 4_298_723_000L,
        		"Europe", 742_452_000L,
        		"Oceania", 38_304_000L
        		);
        long worldPopulation = continentsPopulation.values()
        		.stream()
        		.mapToLong(e -> e)
        		.sum();
        System.out.println("The world population is " + worldPopulation);
    }
}
