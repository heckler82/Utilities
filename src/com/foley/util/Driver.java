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
        Graph<Integer> graph = new Graph<>(false);
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

        Searchable<Integer> search = new DijkstraPathfinder<>(graph, null);
        search.searchGraphFrom(0);
        System.out.println("=============================");
        System.out.println("STARTING NODE: 0");
        System.out.println("NODE\tDISTANCE");
        for(int i = 0; i < 9; i++) {
            System.out.println(i + "\t\t" + search.getPathCostTo(i));
        }
        System.out.println("=============================");

        System.out.println("=============================");
        System.out.println("STARTING NODE: 0");
        for(int i = 0; i < 9; i++) {
            Path<Integer> path = search.getPathTo(i);
            System.out.print("DESTINATION NODE " + i + ": 0");
            for(int j : path) {
                System.out.print(" -> " + j);
            }
            System.out.println();
        }
        System.out.println("=============================");
        timer.tick();
        timer.printTick();

        Graph<Point> pGraph = new Graph<>(false);
        pGraph.addVertex(new Point(0, 0));
        pGraph.addVertex(new Point(0, 1));
        pGraph.addVertex(new Point(1, 1));
        pGraph.addVertex(new Point(1, 0));

        pGraph.addEdge(new Point(0, 0), new Point(0, 1), 5);
        pGraph.addEdge(new Point(0, 1), new Point(1, 1), 11);
        pGraph.addEdge(new Point(1, 1), new Point(1, 0), 5);
        pGraph.addEdge(new Point(1, 0), new Point(0, 0), 10);
        pGraph.addEdge(new Point(1, 0), new Point(0, 1), 4);
        Searchable<Point> pSearch = new DijkstraPathfinder<>(pGraph, new PointComparator());
        //Searchable<Point> pSearch = new DijkstraPathfinder<>(pGraph, null);
        pSearch.searchGraphFrom(new Point(0, 0));
        System.out.println("=============================");
        System.out.println("STARTING NODE: 0, 0");
        System.out.println("NODE\tDISTANCE");
        for(Point p : pGraph.getVertices()) {
            System.out.println(p.x + ", " + p.y + "\t\t" + pSearch.getPathCostTo(p));
        }
        System.out.println("=============================");

        System.out.println("=============================");
        System.out.println("STARTING NODE: 0, 0");
        for(Point p : pGraph.getVertices()) {
            Path<Point> path = pSearch.getPathTo(p);
            System.out.print("DESTINATION NODE (" + p.x + ", " + p.y + "): (0, 0)");
            for(Point p2 : path) {
                System.out.print(" -> (" + p2.x + ", " + p2.y + ")");
            }
            System.out.println();
        }
        System.out.println("=============================");
        timer.tick();
        timer.printTick();
    }
}
