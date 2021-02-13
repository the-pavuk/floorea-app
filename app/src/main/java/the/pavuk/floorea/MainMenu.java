package the.pavuk.floorea;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import the.pavuk.floorea.API.FlooreaApi;
import the.pavuk.floorea.json.JSONArray;
import the.pavuk.floorea.json.JSONObject;
import the.pavuk.floorea.json.parser.JSONParser;
import the.pavuk.floorea.json.parser.ParseException;

public class MainMenu extends AppCompatActivity {
    List<Teacher> teachersList = new ArrayList<Teacher>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.listView);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
            PostTask pt = (PostTask) new PostTask().execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private class PostTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject response = null;
            try {
                response = new FlooreaApi().getTeachers(MainActivity.TOKEN);
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }
            return response;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result.toString().equals("{\"teachers\":[]}")) {
                TextView textView = findViewById(R.id.textView5);
                textView.setVisibility(View.VISIBLE);
            } else {
                try {
                    JSONArray jsonArray = (JSONArray) result.get("teachers");
                    Iterator i = jsonArray.iterator();
                    while (i.hasNext()) {
                        JSONObject jsonObject = (JSONObject) i.next();
                        teachersList.add(new Teacher(jsonObject.get("name").toString(), jsonObject.get("email").toString(), (List<String>) jsonObject.get("subjects")));
                        System.out.println(teachersList);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unauthorized", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                ListView listView = findViewById(R.id.listView);
                ArrayAdapter<Teacher> adapter = new TeacherAdapter(getApplicationContext());
                listView.setAdapter(adapter);
            }
        }
    }
    private static class Teacher{
        public final String name;
        public final String email;
        public final List<String> subjects;

        public Teacher(String name, String email, List<String> subjects){
            this.name = name;
            this.email = email;
            this.subjects = subjects;
        }
    }
    private class TeacherAdapter extends ArrayAdapter<Teacher>{
        public TeacherAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, teachersList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Teacher teacher = getItem(position);
            String subjects = "";
            Iterator i = teacher.subjects.iterator();
            while(i.hasNext()){
                subjects = subjects + i.next().toString()+", ";
            }
            if(subjects.endsWith(", "))
                subjects = subjects.substring(0, subjects.length() - 2) + "";
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_2, null);
            }
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(teacher.name+"\n"+subjects);
            ((TextView) convertView.findViewById(android.R.id.text2))
                    .setText(teacher.email);
            return convertView;
        }
    }
}
/*
ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ ДЖАВА ХУЙНЯ
 */