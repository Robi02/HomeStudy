package _ch18_memento.game;

import java.util.ArrayList;
import java.util.List;

public class Memento {

    int money;
    List<String> fruits;

    public int getMoney() {
        return money;
    }

    Memento(int money) {
        this.money = money;
        this.fruits = new ArrayList<>();
    }

    void addFruit(String fruit) {
        fruits.add(fruit);
    }

    List<String> getFruits() {
        @SuppressWarnings("unchecked")
        List<String> rtList = (List<String>)((ArrayList<String>)fruits).clone();
        return rtList;
    }
}
