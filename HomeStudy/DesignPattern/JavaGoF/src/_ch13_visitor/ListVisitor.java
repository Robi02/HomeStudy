package _ch13_visitor;

import java.util.Iterator;

public class ListVisitor extends Visitor {

    private String currentdir = "";

    @Override
    public void visit(File file) {
        System.out.println(this.currentdir + "/" + file);
    }

    @Override
    public void visit(Directory directory) {
        System.out.println(this.currentdir + "/" + directory);

        String savedir = this.currentdir;
        this.currentdir = this.currentdir + "/" + directory.getName();
        Iterator<Entry> it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = it.next();
            entry.accept(this);
        }
        this.currentdir = savedir;
    }
    

}
