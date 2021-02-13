package the.pavuk.floorea.API;

import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import the.pavuk.floorea.HttpRequest;
import the.pavuk.floorea.json.JSONArray;
import the.pavuk.floorea.json.JSONObject;
import the.pavuk.floorea.json.parser.JSONParser;
import the.pavuk.floorea.json.parser.ParseException;

public class FlooreaApi {
    public boolean Check(String token) throws MalformedURLException {
        HttpRequest isAuth = HttpRequest.get(new URL("https://floorea.herokuapp.com/api/isAuth")).header("token", token);
        return isAuth.ok();
    }
    public JSONObject Auth(String password, String username) throws MalformedURLException {
        Writer writer = new StringWriter();
        Map<String, String> data = new HashMap<String, String>();
        data.put("username", username);
        data.put("password", password);
        HttpRequest auth = HttpRequest.post(new URL("https://floorea.herokuapp.com/api/auth")).acceptJson().form(data).receive(writer);
        String response = writer.toString();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject Register(String password, String username) throws MalformedURLException {
        Writer writer = new StringWriter();
        Map<String, String> data = new HashMap<String, String>();
        data.put("username", username);
        data.put("password", password);
        HttpRequest register = HttpRequest.post(new URL("https://floorea.herokuapp.com/api/register")).acceptJson().form(data).receive(writer);
        String response = writer.toString();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject getTeachers(String token) throws MalformedURLException {
        Writer writer = new StringWriter();
        HttpRequest getTeachers = HttpRequest.get(new URL("https://floorea.herokuapp.com/api/getTeachers")).header("token", token).receive(writer);
        String response = writer.toString();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        System.out.println(response);
        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject addTeacher(String token, String teacherName, String teacherEmail, List<String> subjects) throws MalformedURLException {
        Writer writer = new StringWriter();
        Map<String, String> data = new HashMap<String, String>();
        data.put("email", teacherEmail);
        data.put("subjects", subjects.toString());
        data.put("name", teacherName);
        HttpRequest register = HttpRequest.post(new URL("https://floorea.herokuapp.com/api/addTeacher")).acceptJson().header("token", token).form(data).receive(writer);
        String response = writer.toString();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
