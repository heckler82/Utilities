package com.foley.util.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A set of vertices and the edges connecting them
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 * @param <E> The type of the graph
 */
public class Graph<E> {
    private boolean directional;
    private Map<E, Map<E, Integer>> map;

    /**
     * Creates a new non directional graph
     */
    public Graph() {
        this(false);
    }

    /**
     * Creates a new graph
     *
     * @param directional true if the graph should be directional
     */
    public Graph(boolean directional) {
        map = new HashMap<>();
        this.directional = directional;
    }

    /**
     * Adds a vertex to the graph if it is not already in the graph
     *
     * @param e the vertex
     * @return true if the vertex was added
     */
    public boolean addVertex(E e) {
        // Only add the vertex if it is not already in the graph
        if(!map.containsKey(e)) {
            map.put(e, new HashMap<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a vertex from the graph
     *
     * @param e the vertex
     * @return true if the vertex was removed
     */
    public boolean removeVertex(E e) {
        // Operate only if the vertex is in the graph
        if(map.containsKey(e)) {
            // Remove all edges associated with the vertex
            Set<E> connected = map.get(e).keySet();
            for(E neighbor : connected) {
                map.get(neighbor).remove(e);
            }
            // Remove the vertex from the map
            map.remove(e);
            return true;
        }
        return false;
    }

    /**
     * Adds an edge to the graph
     *
     * @param from the source vertex
     * @param to the destination vertex
     */
    public void addEdge(E from, E to) {
        // Add the edge with a cost of 1
        addEdge(from, to, 1);
    }

    /**
     * Adds an edge to the graph
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @param cost the cost to travel along the edge
     */
    public void addEdge(E from, E to, int cost) {
        // If either vertex isn't in the graph, error out
        if(!map.containsKey(from) || !map.containsKey(to)) {
            throw new IllegalArgumentException("Edges must be added between vertices that are in the graph");
        }
        // Add the edge to the graph
        addEdgeInternal(from, to, cost);
    }

    /**
     * Adds an edge to the graph
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @param cost the cost to travel along the edge
     */
    private void addEdgeInternal(E from, E to, int cost) {
        map.get(from).put(to, cost);
        // Add reverse directional edge
        if(!directional) {
            map.get(to).put(from, cost);
        }
    }

    /**
     * Returns the cost to travel along the edge between the two vertices
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the cost to travel along the edge between the two vertices
     */
    public int getEdgeCost(E from, E to) {
        // If either vertex isn't in the graph, error out
        if(!map.containsKey(from) || !map.containsKey(to)) {
            throw new IllegalArgumentException("Cannot get cost for an edge between vertices that aren't in the graph");
        }
        return map.get(from).get(to);
    }

    /**
     * Returns true if the graph contains the vertex
     *
     * @param e the vertex
     * @return true if the vertex is in the graph
     */
    public boolean containsVertex(E e) {
        return map.containsKey(e);
    }

    /**
     * Returns true if an edge exists between the two vertices
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true if an edge is between the two vertices
     */
    public boolean areAdjacent(E from, E to) {
        // If either vertex isn't in the graph, error out
        if(!map.containsKey(from) || !map.containsKey(to)) {
            throw new IllegalArgumentException("Vertices must be in the graph to test for adjacency");
        }
        // Adjacency exists if value is greater than -1
        return map.get(from).getOrDefault(to, -1) > -1;
    }

    /**
     * Returns the vertices connected to the vertex
     *
     * @param e the vertex
     * @return the vertices that are connected to the vertex
     */
    public Set<E> getNeighbors(E e) {
        // Return empty set if vertex is not in the graph
        if(!map.containsKey(e)) {
            return new HashSet<E>();
        }
        return map.get(e).keySet();
    }

    /**
     * Returns the number of vertices in the graph
     *
     * @return the number of vertices in the graph
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns the vertices that are in the graph
     *
     * @return the vertices that are in the graph
     */
    public Set<E> getVertices() {
        return map.keySet();
    }
}
