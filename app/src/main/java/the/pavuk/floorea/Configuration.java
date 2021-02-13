package the.pavuk.floorea;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static MainActivity activity;
    public static  String token = null;
    public static String templateString = null;


    public void createConfig() throws IOException{
        Properties props = new Properties();
        new File("/data/user/0/Floorea/config/config.ini").createNewFile();
        props.load(new FileInputStream(new File("/data/user/0/Floorea/config/config.ini")));
        token = props.getProperty("token");
        templateString = props.getProperty("templateString");
    }
    public void setToken(String token) throws IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(new File("/data/user/0/Floorea/config/config.ini")));
        props.setProperty("token", token);
    }
    public void setTemplateString(String templateString) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("/data/user/0/Floorea/config/config.ini")));
        props.setProperty("templateString", templateString);
    }
    public String getToken() throws IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(new File("/data/user/0/Floorea/config/config.ini")));
        return props.getProperty("token");
    }
    public String getTemplateString() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("/data/user/0/Floorea/config/config.ini")));
        return props.getProperty("templateString");
    }
}
