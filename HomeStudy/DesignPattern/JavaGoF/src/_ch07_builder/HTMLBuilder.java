package _ch07_builder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLBuilder extends Builder {
 
    private String filename;
    private PrintWriter writer;

	@Override
	public void makeTitle(String title) {
        this.filename = title + ".html";
        try {
            this.writer = new PrintWriter(new FileWriter(filename));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.writer.println("<thml><head><title>" + title + "</title></head><body>");
        this.writer.println("<h1>" + title + "</h1>");
	}

	@Override
	public void makeString(String str) {
		this.writer.println("<p>" + str + "</p>");
	}

	@Override
	public void makeItems(String[] items) {
        this.writer.println("<ul>");
        for (int i = 0; i < items.length; ++i) {
            this.writer.println("<li>" + items[i] + "</li>");
        }
        this.writer.println("</ul>");
	}

	@Override
	public void close() {
        this.writer.println("</body></html>");
        this.writer.close();
    }
    
    public String getResult() {
        return this.filename;
    }
}
