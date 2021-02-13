package the.pavuk.floorea;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import the.pavuk.floorea.API.FlooreaApi;
import the.pavuk.floorea.json.JSONObject;
import the.pavuk.floorea.json.parser.JSONParser;
import the.pavuk.floorea.json.parser.ParseException;


public class MainActivity extends AppCompatActivity {
    public static String TOKEN = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txt = (TextView) findViewById(R.id.textView2);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    EditText editText1 = (EditText) findViewById(R.id.editTextTextEmailAddress);
                    Editable username = editText1.getEditableText();
                    EditText editText2 = (EditText) findViewById(R.id.editTextTextPassword);
                    Editable password = editText2.getEditableText();
                    PostTask pt = (PostTask) new PostTask().execute(username.toString(),password.toString());
                    if (username.toString().equals("") || password.toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Никнейм или пароль не введены", Toast.LENGTH_LONG).show();
                        editText1.setText("", TextView.BufferType.EDITABLE);
                        editText2.setText("", TextView.BufferType.EDITABLE);
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Идет вход...", Toast.LENGTH_LONG).show();
            }

        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterMenu();
            }
        });
    }

    public void openRegisterMenu() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PostTask extends AsyncTask<String, Void, JSONObject> {
        JSONObject res = null;

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject request = null;
            try {
               request = new FlooreaApi().Auth(args[1],args[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return request;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            System.out.println(result);
            System.out.println(getCacheDir());
            if(!Boolean.parseBoolean(result.get("success").toString())){
                Toast.makeText(getApplicationContext(), result.get("error").toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Успешная авторизация", Toast.LENGTH_LONG).show();
                TOKEN = result.get("token").toString();
                    //cfg.setToken(result.get("token").toString());
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        }
    }
}