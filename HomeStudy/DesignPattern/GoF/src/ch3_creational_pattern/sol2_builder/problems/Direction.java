package ch3_creational_pattern.sol2_builder.problems;

public enum Direction {
    
    NORTH(0), SOUTH(1), EAST(2), WEST(3);

    private Direction(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return this.id;
    }
}
