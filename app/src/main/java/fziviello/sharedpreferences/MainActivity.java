package fziviello.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editCognome;
    private Button btnSalva;
    private Button btnStampa;
    private Button btnPulisci;

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = getSharedPreferences("MyShared", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("MyShared", MODE_PRIVATE);

        editNome = (EditText) findViewById(R.id.id_EditNome);
        editCognome = (EditText) findViewById(R.id.id_EditCognome);
        btnSalva = (Button) findViewById(R.id.id_BtnSalva);
        btnStampa = (Button) findViewById(R.id.id_BtnStampa);
        btnPulisci = (Button) findViewById(R.id.id_BtnPulisci);

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();
            }
        });

        btnStampa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

        btnPulisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearShared();
            }
        });

    }

    private void setUser(){

        Gson gson = new Gson();
        ModelUser user = new ModelUser(1,editNome.getText().toString(),editCognome.getText().toString());

        editor.putString("user", gson.toJson(user));
        editor.commit();

    }

    private void getUser(){

        Gson gson = new Gson();
        String json = prefs.getString("user", "");

        if (!json.equals(""))
        {
            ModelUser user = gson.fromJson(json, ModelUser.class);

            Toast.makeText(this, user.getCognome() + " " + user.getNome(), Toast.LENGTH_LONG).show();

            Log.i("id",String.valueOf(user.getId()));
            Log.i("nome",user.getNome());
            Log.i("cognome",user.getCognome());

        }
        else
        {
            Toast.makeText(this, "Shared Preferences Vuota", Toast.LENGTH_LONG).show();
        }

    }

    private void clearShared(){

        editor.clear();
        editor.commit();
    }

}
