package ch3_creational_pattern.sol1_abstract_factory.problems;

import ch3_creational_pattern.sol1_abstract_factory.problems.factory.*;
import ch3_creational_pattern.sol1_abstract_factory.problems.mapsite.*;

public class MazeGame {

    public static Maze createMaze(IMazeFactory factory) {
        Maze aMaze = factory.makeMaze();
        Room r1 = factory.makeRoom(1);
        Room r2 = factory.makeRoom(2);
        Door theDoor = factory.makeDoor(r1, r2);

        r1.setSide(Direction.NORTH, factory.makeWall());
        r1.setSide(Direction.EAST , theDoor);
        r1.setSide(Direction.SOUTH, factory.makeWall());
        r1.setSide(Direction.WEST , factory.makeWall());
        
        r2.setSide(Direction.NORTH, factory.makeWall());
        r2.setSide(Direction.EAST , factory.makeWall());
        r2.setSide(Direction.SOUTH, factory.makeWall());
        r2.setSide(Direction.WEST , theDoor);

        return aMaze;
    }

    public static void main(String[] args) {
        createMaze(new MazeFactory());              // 일반 미로
        createMaze(new EnchantedMazeFactory());     // 마법이 걸린 미로
        createMaze(new BombedMazeFactory());        // 폭탄이 들어있는 미로
    }
}