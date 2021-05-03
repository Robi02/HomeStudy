package _ch02_adapter.practice_02;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        FileIO f = new FileProperties();
        try {
            f.readFromFile("src/_ch02_adapter/practice_02/file.txt");
            f.setValue("year", "2020");
            f.setValue("month", "12");
            f.setValue("day", "22");
            f.writeToFile("src/_ch02_adapter/practice_02/newfile.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
