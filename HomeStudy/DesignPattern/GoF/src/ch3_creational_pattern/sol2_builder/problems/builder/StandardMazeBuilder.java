package ch3_creational_pattern.sol2_builder.problems.builder;

import ch3_creational_pattern.sol2_builder.problems.*;
import ch3_creational_pattern.sol2_builder.problems.mapsite.*;

public class StandardMazeBuilder extends MazeBuilder {
    
    private Maze currentMaze;

    public StandardMazeBuilder() {
        this.currentMaze = null;
    }

    @Override
    public void buildMaze() {
        this.currentMaze = new Maze();
    }

    @Override
    public void buildRoom(int n) {
        if (this.currentMaze.roomNo(n) == null) {
            Room room = new Room(n);
            this.currentMaze.addRoom(room);
        }
    }

    @Override
    public void buildDoor(int roomFrom, int roomTo) {

    }
    
    @Override
    public Maze getMaze() {
        return this.currentMaze;
    }

    private Direction commanWall(Room r1, Room r2) {
        return curr
    }
}
