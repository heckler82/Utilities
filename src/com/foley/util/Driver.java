package com.foley.util;

import com.foley.util.graph.*;

import java.awt.Point;

/**
 * Class description goes here
 *
 * @author Evan Foley
 * @version 13 Jan 2019
 */
public class Driver {
    public static void main(String[] args) {
        SimpleTimer timer = new SimpleTimer();

        // Depth first search
        Graph<Integer> graph = new Graph<>();
        // Add values to graph
        for(int i = 1; i < 6; i++) {
            graph.addVertex(i);
        }
        // Add edges
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);

        graph.printGraph();

        Searchable<Integer> search = new DepthFirstSearchPathfinder<>(graph);
        search.searchGraphFrom(1);
        search.printSearch();
        System.out.println();

        // Breadth first search
        graph = new Graph<>();
        // Add values to graph
        for(int i = 0; i < 8; i++) {
            graph.addVertex(i);
        }
        // Add edges
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(1, 4);
        graph.addEdge(1, 5);
        graph.addEdge(2, 6);
        graph.addEdge(2, 7);
        graph.addEdge(3, 7);

        search = new BreadthFirstSearchPathfinder<>(graph);
        search.searchGraphFrom(0);
        search.printSearch();
        System.out.println();

        // Dijkstra
        graph = new Graph<>();
        // Add values to graph
        for(int i = 0; i < 9; i++) {
            graph.addVertex(i);
        }
        // Add edges
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 5, 4);
        graph.addEdge(2, 8, 2);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);

        search = new DijkstraPathfinder<>(graph);
        search.searchGraphFrom(0);
        search.printSearch();

        timer.tick();
        timer.printTick();

    }
}
