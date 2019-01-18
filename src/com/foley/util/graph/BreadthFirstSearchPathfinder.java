package com.foley.util.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Finds paths in a graph utilizing Breadth First Search Algorithm
 *
 * @author Evan Foley
 * @version 17 Jan 2019
 */
public class BreadthFirstSearchPathfinder<E> extends Pathfinder<E> {
    /**
     * Creates a new pathfinder utilizing the breadth first search algorithm
     *
     * @param g the graph to search
     */
    public BreadthFirstSearchPathfinder(Graph<E> g) {
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
        Queue<E> q = new LinkedList<>();
        // Get and prepare the source node
        SearchNode<E> node = nodes.get(from);
        // Error out if source not found
        if(node == null) {
            throw new IllegalArgumentException("Could not find source node based off of provided starting point");
        }
        node.setScore(0);
        node.visit();
        q.offer(from);

        // Search while there are nodes in the queue
        while(!q.isEmpty()) {
            E current = q.poll();
            node = nodes.get(current);

            // Early exit
            if(to != null && to == current) {
                return;
            }

            // Process the neighbors for this node
            for(E e : g.getNeighbors(current)) {
                SearchNode<E> neighbor = nodes.get(e);
                int newScore = node.getScore() + g.getEdgeCost(current, e);
                // Process neighbor if it has not been visited
                if(!neighbor.isVisited()) {
                    neighbor.setScore(newScore);
                    neighbor.setParent(node);
                    neighbor.visit();
                    q.offer(e);
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
