package com.foley.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A collection of vertices and the edges connecting them
 *
 * @param <T> The type of data the vertices hold
 * @author Evan Foley
 * @version 05 Jan 2019
 */
public class Graph<T extends Comparable> {
    private boolean isDirectional;
    private T root;
    private Map<T, Map<T, Integer>> map;

    /**
     * Algorithms for graph searching
     */
    public enum SearchType {
        BFS, DFS, DIJSKTRA, ASTAR;
    }

    /**
     * Creates an empty non-directional graph
     */
    public Graph() {
        this(false);
    }

    /**
     * Creates an empty graph that is either directional or non-directional
     *
     * @param isDirectional true if the graph is directional
     */
    public Graph(boolean isDirectional) {
        this(isDirectional, null);
    }

    /**
     * Creates a new graph that is either directional or non-directional with the specified root
     *
     * @param isDirectional true if the graph is directional
     * @param root the root vertex
     */
    public Graph(boolean isDirectional, T root) {
        this.isDirectional = isDirectional;
        this.root = root;
        this.map = new HashMap<>();
        // Add the root to the map
        map.put(root, new HashMap<>());
    }

    /**
     * Returns true if the graph is directional
     *
     * @return true if the graph is directional
     */
    public boolean isDirectional() {
        return isDirectional;
    }

    /**
     * Sets the root of the graph
     *
     * @param root the root of the graph
     */
    public void setRoot(T root) {
        // Add to graph if not already in graph
        if(!map.containsKey(root)) {
            map.put(root, new HashMap<>());
        }
        this.root = root;
    }

    /**
     * Adds a vertex to the map if it's not already present
     *
     * @param vertex the vertex to add
     * @return true if the vertex was added
     */
    public boolean addVertex(T vertex) {
        if(!map.containsKey(vertex)) {
            map.put(vertex, new HashMap<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a vertex from the map
     *
     * @param vertex the vertex to remove
     */
    public void removeVertex(T vertex) {
        if(map.containsKey(vertex)) {
            // Get all connected neighbors
            Set<T> set = map.get(vertex).keySet();
            // Remove the vertex from all neighbors
            for(T neighbor : set) {
                map.get(neighbor).remove(vertex);
            }
            // Remove vertex from the graph
            map.remove(vertex);
        }
    }

    /**
     * Adds an edge to the graph
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @return true if the edge was added to the graph
     */
    public boolean addEdge(T from, T to) {
        return addEdge(from, to, 1);
    }

    /**
     * Adds an edge to the graph with the specified weight
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @param weight the weight of the edge
     * @return true if the edge was added to the graph
     */
    public boolean addEdge(T from, T to, int weight) {
        // If both vertices are not in the graph, error out
        if(!map.containsKey(from) || !map.containsKey(to)) {
            return false;
        }
        return addEdgeInternal(from, to, weight);
    }

    /**
     * Removes an edge from the graph
     *
     * @param from the starting vertex
     * @param to the ending vertex
     */
    public void removeEdge(T from, T to) {
        // Proceed only if both vertices are in the graph
        if(map.containsKey(from) && map.containsKey(to)) {
            map.get(from).remove(to);
            // If the graph is not directional, remove the other edge as well
            if(!isDirectional) {
                map.get(to).remove(from);
            }
        }
    }

    /**
     * Adds an edge to the graph
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @param weight the weight of the edge
     * @return true if the edge was added to the graph
     */
    private boolean addEdgeInternal(T from, T to, int weight) {
        map.get(from).put(to, weight);
        // If the graph is not directional, add the edge going the other way as well
        if(!isDirectional) {
            map.get(to).put(from, weight);
        }
        return true;
    }

    /**
     * Returns the weight of the edge connecting the two vertices
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @return the weight of the edge connecting the two vertices
     */
    public int getEdgeWeight(T from, T to) {
        // If starting vertex is not in the graph error out
        if(!map.containsKey(from)) {
            return 0;
        }
        return map.get(from).getOrDefault(to, 0);
    }

    /**
     * Returns true if the two vertices are connected by an edge within the graph. Only tests one way connection
     *
     * @param from the starting vertex
     * @param to the ending vertex
     * @return true if the two vertices are connected by an edge
     */
    public boolean areAdjacent(T from, T to) {
        if(!map.containsKey(from) || !map.containsKey(to)) {
            return false;
        }
        return map.get(from).getOrDefault(to, 0) > 0;
    }

    /**
     * Gets the vertices connected to the passed vertex
     *
     * @param vertex the vertex
     * @return the vertices connected to the passed vertex
     */
    public Set<T> getNeighbors(T vertex) {
        return map.getOrDefault(vertex, new HashMap<>()).keySet();
    }

    /**
     * Gets the number of vertices in the graph
     *
     * @return the number of vertices in the graph
     */
    public int getNumberOfVertices() {
        return map.keySet().size();
    }

    /**
     * Gets the vertices that are in the graph
     *
     * @return the vertices that are in the graph
     */
    public Set<T> getVertices() {
        return map.keySet();
    }

    /**
     * Searches a graph using the breadth first search algorithm
     *
     * @param <T> The type of the graph
     */
    private class BreadthFirstSearch<T extends Comparable> implements GraphSearch<T>{
        Map<T, SearchNode<T>> nodes;

        @Override
        public void searchGraph(Graph<T> g, T from) {
            // Prep and fill the node collection
            nodes = new HashMap<>();
            for(T t : g.getVertices()) {
                nodes.put(t, new SearchNode(t));
            }
            // Verify the source vertex is in the graph
            if(!nodes.containsKey(from)) {
                throw new IllegalArgumentException("[CRITICAL] The specified vertex to search from does not exist in the graph");
            }
            // Set up search
            Queue<SearchNode> q = new LinkedList<>();
            SearchNode<T> current = nodes.get(from);
            current.visited = true;
            current.score = 0;
            q.offer(current);

            // Process until no more nodes remain queued
            while(!q.isEmpty()) {
                current = q.poll();

                // Get and process all neighbors
                T t = current.data;
                for(T neighbor : g.getNeighbors(t)) {
                    SearchNode<T> n = nodes.get(neighbor);
                    if(!n.visited) {
                        n.score = current.score + g.getEdgeWeight(current.data, neighbor);
                        n.visited = true;
                        q.offer(n);
                    }
                }
            }
        }

        @Override
        public int getPathCost(T to) {
            if(!nodes.containsKey(to)) {
                return -1;
            }
            return nodes.get(to).score;
        }

        @Override
        public List<T> getPath(T to) {
            return null;
        }
    }

    private class SearchNode<T extends Comparable> implements Comparable<SearchNode<T>> {
        T data;
        boolean visited;
        int score;
        SearchNode prev;

        public SearchNode(T data) {
            this.data = data;
            this.score = Integer.MAX_VALUE;
        }

        public int compareTo(SearchNode<T> n) {
            return data.compareTo(n.data);
        }
    }
}
