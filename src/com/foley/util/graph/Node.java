package com.foley.util.graph;

import java.util.Objects;

/**
 * A node in the graph
 *
 * @param <T> The type of the node
 * @author Evan Foley
 * @version 26 Dec 2018
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    private T t;

    /**
     * Creates a new node
     *
     * @param t The data of the node
     */
    public Node(T t) {
        this.t = t;
    }

    /**
     * Gets the data
     *
     * @return The data
     */
    public T getData() {
        return t;
    }

    @Override
    /**
     * Determines if two objects are equal
     *
     * @param o The object to equate to
     * @return True if the objects are equal
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(t, node.t);
    }

    @Override
    /**
     * Computes the hash code
     *
     * @return The hash code
     */
    public int hashCode() {
        return Objects.hash(t);
    }

    /**
     * Compares this node to another based on their data
     *
     * @param n The node to compare with
     * @return The resulting comparison
     */
    public int compareTo(Node<T> n) {
        return t.compareTo(n.t);
    }
}