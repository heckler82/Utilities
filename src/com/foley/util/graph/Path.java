package com.foley.util.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Holds a list of steps that form a path
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 * @param <E> The type of the graph
 */
public class Path<E> implements Iterable<E>{
    private LinkedList<E> path;

    /**
     * Creates a new path
     */
    public Path() {
        path = new LinkedList<>();
    }

    /**
     * Adds a step to the path
     *
     * @param step the step to add
     */
    public void addStep(E step) {
        path.addFirst(step);
    }

    /**
     * Returns a step in the path
     *
     * @param index the index
     * @return a step in the path
     */
    public E get(int index) {
        rangeCheck(index);

        return path.get(index);
    }

    /**
     * Returns true if the path is empty
     *
     * @return true if the path is empty
     */
    public boolean isEmpty() {
        return path.isEmpty();
    }

    /**
     * Returns the number of steps in the path
     *
     * @return the number of steps in the path
     */
    public int getSize() {
        return path.size();
    }

    /**
     * Validates the passed index is within proper indexing range
     *
     * @param index the index
     */
    private void rangeCheck(int index) {
        if(index < 0 || index > path.size() - 1) {
            throw new IndexOutOfBoundsException("Cannot get a part of the path that is less than zero, or greater than the size of the path");
        }
    }

    @Override
    /**
     * Returns an iterator over a set of elements of type E
     *
     * @return an iterator over a set of elements of type E
     */
    public Iterator<E> iterator() {
        return path.iterator();
    }
}
