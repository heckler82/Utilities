package com.foley.util;

import java.awt.Point;
import java.util.Comparator;

/**
 * Compares two points and orders according to read order (top/down, left/right)
 *
 * @author Evan Foley
 * @version 15 January 2019
 */
public class PointComparator implements Comparator<Point> {
    @Override
    /**
     * Compares its two arguments for order
     *
     * @param p1 the first object to be compared
     * @param p2 the second object to be compared
     * @return
     */
    public int compare(Point p1, Point p2) {
        // Get initial comparison
        int comp = Integer.compare(p1.y, p2.y);
        // Return if this is the top level point (lower y values are towards the top)
        if(comp < 0) {
            return comp;
        }
        // If two points are at same y position, test left/right order
        if(comp == 0) {
            comp = Integer.compare(p1.x, p2.x);
            // Return if this is the left point
            if(comp < 0) {
                return comp;
            }
        }
        // This point is either lower level or right level point
        return comp;
    }
}