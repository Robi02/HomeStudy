package ch3_creational_pattern.sol1_abstract_factory.problems.mapsite;

public class Door implements IMapSite {
    
    private Room room1;
    private Room room2;
    private boolean isOpen;

    public Door(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public Room otherSideFrom(Room room) {
        return room != this.room1 ? this.room1 : this.room2;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void enter() {

    }
}
