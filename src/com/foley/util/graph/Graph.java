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
    private Map<T, Node<T>> nodes;
    private Map<Node<T>, Map<Node<T>, Integer>> edges;
    private boolean isDirected;

    /**
     * Creates a new graph
     */
    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
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
     * @param n The node
     * @return True if the node was added
     */
    public boolean addNode(Node<T> n) {
        if(!nodes.containsKey(n.getData())) {
            nodes.put(n.getData(), n);
            // Add the edge entry for the node
            edges.put(n, new HashMap<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a node from the graph
     *
     * @param n The node to remove
     * @return True if the node was removed
     */
    public boolean removeNode(Node<T> n) {
        if(nodes.containsKey(n.getData())) {
            nodes.remove(n.getData());
            // Remove the edge entry for the node
            Set<Node<T>> set = edges.get(n).keySet();
            edges.remove(n);
            // Remove the edges to all nodes that were connected to the node
            for(Node<T> node : set) {
                edges.get(node).remove(n);
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
    public boolean addEdge(Node<T> source, Node<T> dest) {
        boolean added = addEdgeInternal(source, dest, 1);
        if(!isDirected) {
            addEdgeInternal(dest, source, 1);
        }
        return added;
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
    public boolean addEdge(Node<T> source, Node<T> dest, int cost) {
        boolean added = addEdgeInternal(source, dest, cost);
        if(!isDirected) {
            addEdgeInternal(dest, source, cost);
        }
        return added;
    }

    /**
     * Removes an edge from the graph
     *
     * @param source The source node
     * @param dest The destination node
     * @return True if the edge was removed
     */
    public boolean removeEdge(Node<T> source, Node<T> dest) {
        if(nodes.containsKey(source.getData()) && nodes.containsKey(dest.getData())) {
            edges.get(source).remove(dest);
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
    public boolean areAdjacent(Node<T> source, Node<T> dest) {
        if(nodes.containsKey(source.getData()) && nodes.containsKey(dest.getData())) {
            return edges.get(source).containsKey(dest);
        }
        return false;
    }

    /**
     * Gets the nodes connected to the passed node
     * @param n The node
     * @return
     */
    public Collection<Node<T>> getNeighbors(Node<T> n) {
        if(nodes.containsKey(n.getData())) {
            return edges.get(n).keySet();
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
    public int getEdgeCost(Node<T> source, Node<T> dest) {
        if(nodes.containsKey(source.getData()) && nodes.containsKey(dest.getData())) {
            return edges.get(source).getOrDefault(dest, -1);
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
    private boolean addEdgeInternal(Node<T> source, Node<T> dest, int cost) {
        if(nodes.containsKey(source.getData()) && nodes.containsKey(dest.getData())) {
            Map<Node<T>, Integer> map = edges.get(source);
            if(!map.containsKey(dest)) {
                map.put(dest, cost);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the number of nodes in the graph
     *
     * @return The number of nodes in the graph
     */
    public int getNumberOfNodes() {
        return nodes.keySet().size();
    }
}