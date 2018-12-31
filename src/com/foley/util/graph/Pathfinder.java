package com.foley.util.graph;

/**
 * Find paths within a graph
 */
public abstract class Pathfinder<T extends Comparable<T>> implements Searchable {
    protected Graph<T> g;
    protected SearchNode[] nodes;

    /**
     * Creates a new pathfinder
     *
     * @param g The graph to search on
     */
    public Pathfinder(Graph<T> g) {
        this.g = g;
        // Setup the search nodes
        nodes = new SearchNode[g.getNumberOfNodes()];
        int index = 0;
    }

    /**
     * Node for searching a graph
     */
    protected class SearchNode {
        T t;
        int score;
        boolean visited;
        SearchNode prev;

        /**
         * Creates a new search node
         *
         * @param t The data
         */
        public SearchNode(T t) {
            this.t = t;
        }
    }
}
