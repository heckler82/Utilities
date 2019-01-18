package com.foley.util.graph;

import java.util.Stack;

/**
 * Finds paths in a graph utilizing Depth First Search Algorithm
 *
 * @author Evan Foley
 * @version 17 Jan 2019
 */
public class DepthFirstSearchPathfinder<E> extends Pathfinder<E> {
    /**
     * Creates a new pathfinder utilizing the depth first search algorithm
     *
     * @param g the graph to search
     */
    public DepthFirstSearchPathfinder(Graph<E> g) {
        super(g);
    }

    @Override
    /**
     * Searches the graph from a source vertex to a target vertex
     *
     * @param from the vertex to search from
     * @param to the vertex to search to
     */
    public void searchGraphTo(E from, E to) {
        // Prepare the search space
        Stack<E> s = new Stack<>();
        // Get and prepare the source node
        SearchNode<E> node = nodes.get(from);
        // Error out if source not found
        if(node == null) {
            throw new IllegalArgumentException("Could not find source node based off of provided starting point");
        }
        node.setScore(0);
        s.push(from);

        // Search while there are still nodes on the stack
        while(!s.isEmpty()) {
            E current = s.pop();
            node = nodes.get(current);
            if(!node.isVisited()) {
                node.visit();

                // Process neighbors
                for(E e : g.getNeighbors(current)) {
                    SearchNode<E> neighbor = nodes.get(e);
                    int newScore = node.getScore() + g.getEdgeCost(current, e);
                    // Add to stack if it hasn't been visited
                    if(!neighbor.isVisited()) {
                        neighbor.setScore(newScore);
                        neighbor.setParent(node);
                        s.push(e);
                    } else {
                        if(newScore < neighbor.getScore()) {
                            neighbor.setScore(newScore);
                            neighbor.setParent(node);
                        }
                    }
                }
            }
        }
    }
}
