package _ch02_adapter.practice_02;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Properties;

public class FileProperties implements FileIO {

    private Properties properties;

    @Override
    public void readFromFile(String filename) throws IOException {
        FileChannel fc = FileChannel.open(Paths.get(filename), StandardOpenOption.READ);
        this.properties = new Properties();
        this.properties.load(Channels.newInputStream(fc));
    }

    @Override
    public void writeToFile(String filename) throws IOException {
        FileChannel fc = FileChannel.open(Paths.get(filename), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        fc.truncate(0);
        this.properties.store(Channels.newOutputStream(fc) , "written by FileProperties");
        this.properties = null;
    }

    @Override
    public void setValue(String key, String value) {
        this.properties.setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
