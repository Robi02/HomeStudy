package ch3_creational_pattern.sol2_builder.problems;

import ch3_creational_pattern.sol2_builder.problems.builder.*;

public class MazeGame {

    public static Maze createMaze(MazeBuilder builder) {
        builder.buildRoom(1);
        builder.buildRoom(1001);

        return builder.getMaze();
    }

    public static void main(String[] args) {
        createMaze(new MazeBuilder()); // 일반 미로
    
    }
}