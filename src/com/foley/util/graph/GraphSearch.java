package com.foley.util.graph;

import java.util.List;

/**
 * Template for searching graphs
 *
 * @param <T> The type of graphs to search
 * @author Evan Foley
 * @version 05 Jan 2019
 */
public interface GraphSearch<T> {
    /**
     * Finds all paths from the passed vertex
     *
     * @param g the graph
     * @param from the vertex
     */
    void searchGraph(Graph<T> g, T from);

    /**
     * Gets the cost of the path to a vertex
     *
     * @param to the vertex
     * @return the cost of the path
     */
    int getPathCost(T to);

    /**
     * Gets the path to a vertex
     *
     * @param to the vertex
     * @return the path to the vertex
     */
    List<T> getPath(T to);
}
