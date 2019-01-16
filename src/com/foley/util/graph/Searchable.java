package com.foley.util.graph;

/**
 * Methods for searching graphs and pulling the results
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 */
public interface Searchable<E> {
    /**
     * Searches the graph from a source vertex to all other vertices
     *
     * @param from the vertex to search from
     */
    void searchGraphFrom(E from);

    /**
     * Searches the graph from a source vertex to a target vertex
     *
     * @param from the vertex to search from
     * @param to the vertex to search to
     */
    void searchGraphTo(E from, E to);

    /**
     * Returns the total cost to travel to a target vertex
     *
     * @param to the target vertex
     * @return the total cost to travel to the target vertex
     */
    int getPathCostTo(E to);

    /**
     * Returns the path of vertices that lead to the target vertex
     *
     * @param to the target vertex
     * @return the path of vertices that lead to the target vertex
     */
    Path<E> getPathTo(E to);
}
