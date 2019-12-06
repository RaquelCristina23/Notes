package br.ifsc.edu.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bd;
    ListView notas;
    Button novaNota;
    String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = openOrCreateDatabase("database", MODE_PRIVATE, null);
        notas = findViewById(R.id.listNotas);
        novaNota = findViewById(R.id.btnNovaNota);

        novaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNota.class);
                startActivity(intent);

            }
        });

        notas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                titulo = novaNota.getText().toString();
                bd.execSQL("DELETE FROM notas WHERE titulo = 'titulo'");

                Toast.makeText(getApplicationContext(), "Nota removida", Toast.LENGTH_SHORT).show();
                return true;
            }

        });

//CREATE TABLE
        bd.execSQL("CREATE TABLE IF NOT EXISTS notas (" +
                "id integer primary key autoincrement," +
                "titulo varchar not null," +
                "texto varchar );");


        //SELECT
        Cursor cursor = bd.rawQuery("SELECT * FROM notas", null, null);
        cursor.moveToFirst();

        String id, titulo, texto;
        final ArrayList<String> arrayList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            cursor.moveToNext();
            arrayList.add(titulo);
        }


        //LIST
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                arrayList

        );

//ON CLICK BEHAVIOR: "PRINT"
        notas.setAdapter(adapter);
        notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = arrayList.get(i);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

    }

}

