package com.foley.util.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of vertices and the edges connecting them
 *
 * @param <T> The type of the graph
 * @author Evan Foley
 * @version 26 Dec 2018
 */
public class Graph<T extends Comparable<T>> {
    // Stores nodes by their id
    private Map<Integer, Node<T>> nodeID;
    // Stores nodes by their payload
    private Map<T, Node<T>> nodePayload;
    // The graph root node
    private Node<T> root;
    // Adjacency list, value < zero is not connected
    private int[][] adjacency;
    // Directional flag
    private boolean isDirected;

    /**
     * Creates a new undirected graph
     */
    private Graph() {
        nodeID = new HashMap<>();
        nodePayload = new HashMap<>();
        adjacency = new int[10][10];
        // All costs to each node default to -1
        for(int[] arr : adjacency) {
            Arrays.fill(arr, -1);
        }
        isDirected = false;
    }

    /**
     * Creates a new undirected graph and sets the root to the given node
     *
     * @param n The root node
     */
    public Graph(Node<T> n) {
        this(n, false);
    }

    /**
     * Creates a new graph specified to be directed or undirected, and sets the root to the given node
     *
     * @param n The root node
     * @param isDirected True for a directed graph
     */
    public Graph(Node<T> n, boolean isDirected) {
        this();
        nodeID.put(n.getID(), n);
        nodePayload.put(n.getPayload(), n);
        this.root = n;
        this.isDirected = isDirected;
    }

    /**
     * Sets the root for the graph
     *
     * @param n The root node
     */
    public void setRoot(Node<T> n) {
        if(nodeID.containsKey(n.getID())) {
            root = nodeID.get(n.getID());
            return;
        }
        addNode(n);
        root = n;
    }

    /**
     * Gets the root node of the graph
     *
     * @return The root node of the graph
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * Adds a node to the graph. Expands the adjacency list when necessary
     *
     * @param n The node to add
     * @return True if the vertex was successfully added
     */
    public boolean addNode(Node<T> n) {
        if(nodeID.containsKey(n.getID())) {
            return false;
        }
        nodeID.put(n.getID(), n);
        nodePayload.put(n.getPayload(), n);
        // If necessary, expand adjacency list
        if(nodeID.keySet().size() >= adjacency.length) {
            adjacency = expandAdjacencyList();
        }
        // Set the node distance to itself to zero
        adjacency[n.getID()][n.getID()] = 0;
        return true;
    }

    /**
     * Gets a node from the graph based on its payload
     *
     * @param t The payload of the node to get
     * @return The node associated with the passed payload
     */
    public Node<T> getNode(T t) {
        return nodePayload.get(t);
    }

    /**
     * Gets a node from the graph based on its id
     *
     * @param i The id of the node
     * @return The node associated with the id
     */
    public Node<T> getNode(int i) {
        return nodeID.get(i);
    }

    /**
     * Gets all the nodes that are connected to the specified node
     *
     * @param n The parent node
     * @return The list of connected nodes
     */
    public List<Integer> getConnectedNodes(Node<T> n) {
        // Verify node id is within bounds
        if(n.getID() > -1 && n.getID() < nodeID.keySet().size()) {
            ArrayList<Integer> list = new ArrayList<>();
            for(int i : adjacency[n.getID()]) {
                if(i > -1) {
                    list.add(i);
                }
            }
            return list;
        }
        return new ArrayList<Integer>();
    }

    /**
     * Adds an edge between the two specified nodes
     *
     * @param from The source node
     * @param to The destination node
     * @return True if the edge was successfully added
     */
    public boolean addEdge(Node<T> from, Node<T> to) {
        return addEdge(from, to, 0);
    }

    /**
     * Adds an edge between the two specified nodes with the specified cost. An already existing edge will be overridden
     *
     * @param from The source node
     * @param to The destination node
     * @param cost The cost to travel along the edge
     * @return True if the edge was successfully added
     */
    public boolean addEdge(Node<T> from, Node<T> to, int cost) {
        // Only add the edge if both nodes exist in the graph
        if(nodeID.get(from.getID()) != null && nodeID.get(to.getID()) != null) {
            adjacency[from.getID()][to.getID()] = cost;
            // If graph is undirected then two edges will be added
            if(!isDirected) {
                adjacency[to.getID()][from.getID()] = cost;
            }
            return true;
        }
        return false;
    }

    /**
     * Tests if there is an edge between the source and the destination node
     *
     * @param from The source node
     * @param to The destination node
     * @return True if an edge exists between two nodes
     */
    public boolean areNodesConnected(Node<T> from, Node<T> to) {
        // Only test if both nodes exist in the graph
        if(nodeID.get(from.getID()) != null && nodeID.get(to.getID()) != null) {
            return adjacency[from.getID()][to.getID()] > -1;
        }
        return false;
    }

    /**
     * Gets the number of nodes in the graph
     *
     * @return The number of nodes in the graph
     */
    public int getNodeCount() {
        return nodeID.keySet().size();
    }

    /**
     * Finds the path from the graph root to all reachable nodes
     *
     * @return The search object
     */
    public Searchable findPaths() {
        return findPaths(root);
    }

    /**
     * Finds the path from the source node to all reachable nodes
     *
     * @param source The source node
     * @return The search object
     */
    public Searchable findPaths(Node<T> source) {
        return null;
    }

    /**
     * Expands the adjacency list
     *
     * @return The new adjacency list
     */
    private int[][] expandAdjacencyList() {
        // Double capacity of the adjacency list
        int[][] newList = new int[2 * nodeID.keySet().size()][2 * nodeID.keySet().size()];
        // For every row in the adjacency list, copy to the new one
        for(int i = 0; i < adjacency.length; i++) {
            int[] oldElem = adjacency[i];
            int[] newElem = newList[i];
            Arrays.fill(newElem, -1);
            System.arraycopy(oldElem, 0, newElem, 0, oldElem.length);
        }
        return newList;
    }
}