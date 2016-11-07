package com.lucek.androidgameengine2d.gameplay;

import android.graphics.Point;

import com.lucek.androidgameengine2d.game.Field;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Daniel on 2016-10-15.
 */

public class Group {
    public java.util.LinkedList<Point> elements = new java.util.LinkedList<Point>();
    public Field colour;

    /**
     * Looks for all groups on a board passed as parameter and returns them.
     * Based on Kosaraju's algorithm for finding Strongly Connected Components,
     * therefore runs in O(n) time.
     * @param board Boardstate for which group finding alghorithm is performed.
     * @return An array of all WHITE and BLACK groups found on given board.
     */
    public static Group[] FindAllGroups(Field[][] board){
        boolean[][] visited = new boolean[board.length][board[0].length]; //visited - which fields you've already gone through


        LinkedList<Group> groups = new LinkedList<Group>();

        for (int y = 0; y < visited[0].length; y++){
            for (int x = 0; x < visited.length; x++){
                if ((visited[x][y] == false)){
                    Group newGroup = new Group();
                    Stack<Point> toVisit = new Stack<>();
                    toVisit.push(new Point(x, y));
                    visited[x][y] = true;
                    newGroup.colour = board[x][y];

                    while (toVisit.isEmpty()==false){
                        //process current coordinate
                        Point current = toVisit.pop();
                        newGroup.elements.add(current);

                        //add its on-board-neighbours
                        Stack<Point> neighbours = new Stack<Point>();
                        if (current.x > 0) neighbours.push(new Point(current.x - 1, current.y));
                        if (current.x < board.length - 1) neighbours.push(new Point(current.x + 1, current.y));
                        if (current.y > 0) neighbours.push(new Point(current.x, current.y - 1));
                        if (current.y < board[0].length - 1) neighbours.push(new Point(current.x, current.y + 1));

                        //validate neighbours - have we checked them already? Are they the same colour as the last one?
                        for(Point neighbour : neighbours){
                            if ((visited[neighbour.x][neighbour.y] == false) && (board[neighbour.x][neighbour.y] == board[current.x][current.y])){
                                visited[neighbour.x][neighbour.y] = true;
                                toVisit.push(neighbour);
                            }
                        }
                        neighbours.clear();
                    }

                    //newGroup is now containing area-group of fields of the same color. We're not interested in colorless,
                    //so we have to get rid of those
                    if (newGroup.colour != Field.EMPTY) groups.add(newGroup);
                }
            }
        }
        Group[] retValue = new Group[groups.size()];
        retValue = groups.toArray(retValue);

        return retValue;
    }

    /**
     * Finds a group the given point is a part of and returns it.
     * Warning!: If point is EMPTY, the function will return what would-be a group of EMPTY fields.
     * @param board selected boardstate (possibly simulated) for which the search is commenced.
     * @param location starting point of the group search.
     * @return Group containing point passed as parameter.
     */
    public static Group FindGroup(Field[][] board, Point location){
        Stack<Point> toVisit = new Stack<Point>();
        Group group = new Group();
        group.colour = board[location.x][location.y];
        boolean[][] visited = new boolean[board.length][board[0].length];


        toVisit.push(location);
        visited[location.x][location.y] = true;

        while(toVisit.isEmpty()==false){
            //process current coordinate
            Point current = toVisit.pop();
            group.elements.add(current);

            //add its on-board-neighbours
            Stack<Point> neighbours = new Stack<>();
            if (current.x > 0) neighbours.push(new Point(current.x - 1, current.y));
            if (current.x < board.length - 1) neighbours.push(new Point(current.x + 1, current.y));
            if (current.y > 0) neighbours.push(new Point(current.x, current.y - 1));
            if (current.y < board[0].length - 1) neighbours.push(new Point(current.x, current.y + 1));

            //validate neighbours - have we checked them already? Are they the same colour as the last one?
            for(Point neighbour : neighbours){
                if ((visited[neighbour.x][neighbour.y] == false)&& (board[neighbour.x][neighbour.y] == board[current.x][current.y])){
                    visited[neighbour.x][neighbour.y] = true;
                    toVisit.push(neighbour);
                }
            }
            neighbours.clear();

        }

        return group;
    }

    /**
     * Checks whether this group has a liberty - at least one EMPTY field surrounding it.
     * If any group would be left without a liberty, move is considered invalid.
     * @param board selected boardstate for which the check is made.
     * @return true if on given board this group has a liberty, false otherwise.
     */
    public boolean HasLiberty(Field[][] board)
    {
        for(Point coords : elements)
        {
            if (coords.x > 0)
            {
                if (board[coords.x-1][coords.y] == Field.EMPTY) return true;
            }
            if (coords.x < board.length - 1)
            {
                if (board[coords.x+1][coords.y] == Field.EMPTY) return true;
            }
            if (coords.y > 0)
            {
                if (board[coords.x][coords.y-1] == Field.EMPTY) return true;
            }
            if (coords.y < board[0].length-1)
            {
                if (board[coords.x][coords.y+1] == Field.EMPTY) return true;
            }
        }
        return false;
    }
}
