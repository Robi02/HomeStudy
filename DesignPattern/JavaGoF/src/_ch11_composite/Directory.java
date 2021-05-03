package _ch11_composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Directory extends Entry {

    private String name;
    private List<Entry> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        int size = 0;
        Iterator<Entry> it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = it.next();
            size += entry.getSize();
        }
        return size;
    }

    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this.toString());
        for (Entry entry : this.directory) {
            entry.printList(prefix + "/" + this.name);
        }
    }
}
