package _ch01_iterator;

import java.util.ArrayList;
import java.util.List;

public class BookShelf implements Aggregate {
    
    private List<Book> books;
    private int last = 0;
    
    public BookShelf(int capacity) {
        this.books = new ArrayList<>(capacity);
    }

    public Book getBookAt(int index) {
        return books.get(index);
    }

    public void appendBook(Book book) {
        this.books.add(this.last, book);
        ++(this.last);
    }

    public int getLength() {
        return this.last;
    }

    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
