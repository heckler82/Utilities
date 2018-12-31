package com.foley.util.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A collection of nodes and the edges connecting them
 *
 * @param <T> The type of the graph
 * @author Evan Foley
 * @version 26 Dec 2018
 */
public class Graph<T extends Comparable<T>> {
    private Map<T, Map<T, Integer>> map;
    private boolean isDirected;

    /**
     * Creates a new graph
     */
    public Graph() {
        map = new HashMap<>();
        isDirected = false;
    }

    /**
     * Creates a new graph
     *
     * @param isDirected True for a directed graph
     */
    public Graph(boolean isDirected) {
        this();
        this.isDirected = isDirected;
    }

    /**
     * Adds a node to the graph
     *
     * @param t The node
     * @return True if the node was added
     */
    public boolean addNode(T t) {
        if(!map.containsKey(t)) {
            // Add the edge entry for the node
            map.put(t, new HashMap<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a node from the graph
     *
     * @param t The node to remove
     * @return True if the node was removed
     */
    public boolean removeNode(T t) {
        if(map.containsKey(t)) {
            // Remove the edge entry for the node
            Set<T> set = map.get(t).keySet();
            map.remove(t);
            // Remove the edges to all nodes that were connected to the node
            for(T nt : set) {
                map.get(nt).remove(t);
            }
            return true;
        }
        return false;
    }

    /**
     * Adds an edge to the graph. If graph is not directed, will also add the edge from the destination node
     * to the source node. Will only return the result of adding the edge from the source to the destination
     *
     * @param source The source node
     * @param dest The destination node
     * @return True if the edge was added between source and dest
     */
    public boolean addEdge(T source, T dest) {
        if(map.containsKey(source) && map.containsKey(dest)) {
            boolean added = addEdgeInternal(source, dest, 1);
            if (!isDirected) {
                addEdgeInternal(dest, source, 1);
            }
            return added;
        }
        return false;
    }

    /**
     * Adds an edge to the graph with the specified cost. If graph is not directed, will also add the edge from the
     * destination node to the source node. Will only return the result of adding the edge from the source to the
     * destination
     *
     * @param source The source node
     * @param dest The destination node
     * @param cost The cost to travel along the edge
     * @return True if the edge was added between source and dest
     */
    public boolean addEdge(T source, T dest, int cost) {
        if(map.containsKey(source) && map.containsKey(dest)) {
            boolean added = addEdgeInternal(source, dest, cost);
            if (!isDirected) {
                addEdgeInternal(dest, source, cost);
            }
            return added;
        }
        return false;
    }

    /**
     * Removes an edge from the graph
     *
     * @param source The source node
     * @param dest The destination node
     * @return True if the edge was removed
     */
    public boolean removeEdge(T source, T dest) {
        if(map.containsKey(source) && map.containsKey(dest)) {
            map.get(source).remove(dest);
            if(!isDirected) {
                map.get(dest).remove(source);
            }
            return true;
        }
        return false;
    }

    /**
     * Determines if two nodes are adjacent to each other. Only determines if an edge starting from source goes 
     * to dest. Will not test for the other direction.
     * 
     * @param source The source node
     * @param dest The destination node
     * @return True if the nodes are adjacent to each other
     */
    public boolean areAdjacent(T source, T dest) {
        if(map.containsKey(source) && map.containsKey(dest)) {
            return map.get(source).containsKey(dest);
        }
        return false;
    }

    /**
     * Gets the nodes connected to the passed node
     * @param t The node
     * @return
     */
    public Collection<T> getNeighbors(T t) {
        if(map.containsKey(t)) {
            return map.get(t).keySet();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the cost to travel along an edge in the graph
     *
     * @param source The source node
     * @param dest The destination node
     * @return The cost to travel along the edge
     */
    public int getEdgeCost(T source, T dest) {
        if(map.containsKey(source) && map.containsKey(dest)) {
            return map.get(source).getOrDefault(dest, -1);
        }
        return -1;
    }

    /**
     * Adds an edge to the graph
     *
     * @param source The source node
     * @param dest The destination node
     * @param cost The cost to travel along the edge
     * @return True if the edge was added
     */
    private boolean addEdgeInternal(T source, T dest, int cost) {
        Map<T, Integer> edgeMap = map.get(source);
        if(!edgeMap.containsKey(dest)) {
            edgeMap.put(dest, cost);
            return true;
        }
        return false;
    }

    /**
     * Gets the number of nodes in the graph
     *
     * @return The number of nodes in the graph
     */
    public int getNumberOfNodes() {
        return map.keySet().size();
    }

    /**
     * Gets the nodes from the graph
     *
     * @return The nodes from the graph
     */
    public Collection<T> getNodes() {
        return map.keySet();
    }
}