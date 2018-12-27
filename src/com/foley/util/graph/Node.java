package com.foley.util.graph;

/**
 * A node in the graph
 *
 * @param <T> The type of the node
 * @author Evan Foley
 * @version 26 Dec 2018
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    private int id;
    private T t;

    public static int NODE_COUNT = 0;

    /**
     * Creates a new node
     *
     * @param t The payload of the node
     */
    public Node(T t) {
        this.id = NODE_COUNT++;
        this.t = t;
    }

    /**
     * Gets the data payload
     *
     * @return The data payload
     */
    public T getPayload() {
        return t;
    }

    /**
     * Gets the id of the node
     *
     * @return The node id
     */
    public int getID() {
        return id;
    }

    /**
     * Compares this node to another based on their payload
     *
     * @param n The node to compare with
     * @return The resulting comparison
     */
    public int compareTo(Node<T> n) {
        return t.compareTo(n.t);
    }
}