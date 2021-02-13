package the.pavuk.floorea;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
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

public class MainActivity2 extends AppCompatActivity {
    public JSONObject retJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView txt = (TextView) findViewById(R.id.textView4);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1 = (EditText) findViewById(R.id.editTextTextEmailAddress2);
                Editable username = editText1.getEditableText();
                EditText editText2 = (EditText) findViewById(R.id.editTextTextPassword2);
                Editable password = editText2.getEditableText();
                    PostTaskReg pt = (PostTaskReg) new PostTaskReg().execute(username.toString(),password.toString());
                    if (username.toString().equals("") || password.toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Никнейм или пароль не введен", Toast.LENGTH_LONG).show();
                        editText1.setText("", TextView.BufferType.EDITABLE);
                        editText2.setText("", TextView.BufferType.EDITABLE);
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Идет регистрация...", Toast.LENGTH_LONG).show();
                    editText1.setText("", TextView.BufferType.EDITABLE);
                    editText2.setText("", TextView.BufferType.EDITABLE);
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnMenu();
            }
        });
    }

    private void returnMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public class PostTaskReg extends AsyncTask<String, Void, JSONObject> {
        JSONObject res = null;
        @Override
        protected JSONObject doInBackground(String ... args) {
            JSONObject request = null;
            try {
                request = new FlooreaApi().Register(args[1], args[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return request;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            System.out.println(result);
            if(!Boolean.parseBoolean(result.get("success").toString())){
                Toast.makeText(getApplicationContext(), result.get("error").toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Успешная регистрация!", Toast.LENGTH_LONG).show();
                returnMenu();
            }
        }
    }
    }

