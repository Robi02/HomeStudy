package ch3_creational_pattern.sol1_abstract_factory.problems.factory;

import ch3_creational_pattern.sol1_abstract_factory.problems.Maze;
import ch3_creational_pattern.sol1_abstract_factory.problems.mapsite.*;

public interface IMazeFactory {

    public Maze makeMaze();
    public Wall makeWall();
    public Room makeRoom(int n);
    public Door makeDoor(Room r1, Room r2);
}
