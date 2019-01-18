package com.foley.util.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Finds paths in a graph utilizing Dijkstra's Algorithm
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 */
public class DijkstraPathfinder<E> extends Pathfinder<E> {
    /**
     * Creates a new pathfinder utilizing dijkstra's algorithm
     *
     * @param g the graph to search
     */
    public DijkstraPathfinder(Graph<E> g) {
        super(g);
    }
    /**
     * Creates a new pathfinder utilizing dijkstra's algorithm
     *
     * @param g the graph to search
     * @param comp the comparator to use
     */
    public DijkstraPathfinder(Graph<E> g, Comparator<E> comp) {
        super(g, comp);
    }

    @Override
    /**
     * Searches the graph from a source vertex to a target vertex
     *
     * @param from the vertex to search from
     * @param to the vertex to search to
     */
    public void searchGraphTo(E from, E to) {
        // Setup search space
        Queue<E> q;
        // Utilize custom comparator if one was provided
        if(comp != null) {
            q = new PriorityQueue<>(comp);
        } else {
            // PriorityQueue requires Comparable for natural-ordering. Error out if condition is not met
            if(!(from instanceof Comparable)) {
                throw new IllegalStateException("Dijkstra must work on objects of type Comparable. If type is not Comparable, please provide a Comparator for sorting");
            }
            q = new PriorityQueue<>();
        }
        // Get and prepare the source node
        SearchNode<E> node = nodes.get(from);
        // Error out if source not found
        if(node == null) {
            throw new IllegalArgumentException("Could not find source node based off of provided starting point");
        }
        node.setScore(0);
        q.offer(from);

        // Continue to process until queue is empty
        while(!q.isEmpty()) {
            // Poll the priority node based off of the queue priority item
            E current = q.poll();
            node = nodes.get(current);

            // Early exit
            if(to != null && to == current) {
                return;
            }

            // Process all neighbors
            for(E e : g.getNeighbors(current)) {
                SearchNode<E> neighbor = nodes.get(e);
                int newScore = node.getScore() + g.getEdgeCost(current, e);
                // Update neighbor if new score is better than the old score
                if(newScore < neighbor.getScore()) {
                    neighbor.setScore(newScore);
                    neighbor.setParent(node);
                    q.offer(e);
                }
            }
        }
    }
}
