package com.foley.util.graph;

import java.util.List;

/**
 * Template for search objects
 *
 * @author Evan Foley
 * @version 26 Dec 2018
 */
public interface Searchable {
    /**
     * Gets the cost of the path to the destination node
     *
     * @param destination The destination node
     * @return The total cost value of the path to the destination
     */
    int getPathCost(Node destination);

    /**
     * Gets the path to the destination node
     *
     * @param destination The destination node
     * @return The path to the destination node
     */
    List<Node> getPath(Node destination);
}
