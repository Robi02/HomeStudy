package ch3_creational_pattern.sol2_builder.problems.mapsite;

import ch3_creational_pattern.sol1_abstract_factory.problems.Direction;

public class Room implements IMapSite {

    private IMapSite[] sides = new IMapSite[4];
    private int roomNumber;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public IMapSite getSide(Direction direction) {
        return this.sides[direction.getId()];
    }

    public void setSide(Direction direction, IMapSite mapSite) {
        this.sides[direction.getId()] = mapSite;
    }

    public int getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public void enter() {

    }
}
