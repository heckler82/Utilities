package com.foley.util.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Searches a graph
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 */
public abstract class Pathfinder<E> implements Searchable<E>{
    protected Graph<E> g;
    protected Map<E, SearchNode<E>> nodes;
    protected Comparator<E> comp;

    /**
     * Creates a new graph
     *
     * @param g the graph to search
     */
    public Pathfinder(Graph<E> g) {
        this(g, null);
    }

    /**
     * Creates a new pathfinder
     *
     * @param g the graph to search
     * @param comp the comparator
     */
    public Pathfinder(Graph<E> g, Comparator<E> comp) {
        this.g = g;
        this.nodes = new HashMap<>();
        this.comp = comp;
        // Create search nodes
        for(E e : g.getVertices()) {
            nodes.put(e, new SearchNode<>(e));
        }
    }

    @Override
    /**
     * Returns the total cost to travel to a target vertex
     *
     * @param to the target vertex
     * @return the total cost to travel to the target vertex
     */
    public int getPathCostTo(E to) {
        // Get the destination node
        SearchNode<E> node = nodes.get(to);
        // If the node exists, return the cost to get to the node
        if(node != null) {
            return node.getScore();
        }
        return -1;
    }

    @Override
    /**
     * Returns the path of vertices that lead to the target vertex
     *
     * @param to the target vertex
     * @return the path of vertices that lead to the target vertex
     */
    public Path<E> getPathTo(E to) {
        // Get the destination node
        SearchNode<E> node = nodes.get(to);
        Path<E> path = new Path<>();
        // If the node exists, add steps until the root node is reached
        if(node != null) {
            while(node != null) {
                path.addStep(node.data);
                node = node.parent;
            }
        }
        // Return the resulting path (path may be empty)
        return path;
    }

    @Override
    /**
     * Prints out the search results to the console
     */
    public void printSearch() {
        System.out.println(toString());
    }

    /**
     * Returns a string representation of the search
     *
     * @return the string representation of the search
     */
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Search Results\n");
        build.append("-------------------------------------------------------\n");
        build.append("Node\t\tCost\t\tPath\n");
        for(E e : g.getVertices()) {
            build.append(e.toString() + "\t\t\t" + getPathCostTo(e) + "\t\t\t" + null);
            Path<E> path = getPathTo(e);
            for(E step : path) {
                build.append(" -> " + step.toString());
            }
            build.append("\n");
        }
        build.append("-------------------------------------------------------\n");
        return build.toString();
    }

    /**
     * Wrapper node for searching a graph
     *
     * @author Evan Foley
     * @version 15 Jan 2019
     */
    protected class SearchNode<E> {
        private E data;
        private int score;
        private boolean visited;
        private SearchNode<E> parent;

        /**
         * Creates a new search node
         *
         * @param data the data payload
         */
        public SearchNode(E data) {
            this.data = data;
            this.score = Integer.MAX_VALUE;
            this.visited = false;
            this.parent = null;
        }

        /**
         * Returns the data of the node
         *
         * @return the data of the node
         */
        public E getData() {
            return data;
        }

        /**
         * Gets the score of the node
         *
         * @return the score of the node
         */
        public int getScore() {
            return score;
        }

        /**
         * Sets the score of the node
         *
         * @param score the score
         */
        public void setScore(int score) {
            this.score = score;
        }

        /**
         * Returns true if the node has been visited
         *
         * @return true if the node has been visited
         */
        public boolean isVisited() {
            return visited;
        }

        /**
         * Visits the node
         */
        public void visit() {
            this.visited = true;
        }

        /**
         * Sets the visited flag
         *
         * @param visited true for a visited node, false for a node that has not been visited
         */
        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        /**
         * Gets the parent of the node
         *
         * @return the parent of the node
         */
        public SearchNode<E> getParent() {
            return parent;
        }

        /**
         * Sets the parent of the node
         *
         * @param parent the parent
         */
        public void setParent(SearchNode<E> parent) {
            // Ensure proper data is stored
            if(parent == null || this == parent) {
                throw new IllegalArgumentException("SearchNode cannot set parent value to null or self");
            }
            this.parent = parent;
        }
    }
}
