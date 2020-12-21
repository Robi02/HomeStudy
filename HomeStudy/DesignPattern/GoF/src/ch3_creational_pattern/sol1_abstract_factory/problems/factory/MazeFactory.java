package ch3_creational_pattern.sol1_abstract_factory.problems.factory;

import ch3_creational_pattern.sol1_abstract_factory.problems.Maze;
import ch3_creational_pattern.sol1_abstract_factory.problems.mapsite.*;

public class MazeFactory implements IMazeFactory {
    
    public MazeFactory() {}

    public Maze makeMaze() {
        return new Maze();
    }

    public Wall makeWall() {
        return new Wall();
    }

    public Room makeRoom(int n) {
        return new Room(n);
    }

    public Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }
}
