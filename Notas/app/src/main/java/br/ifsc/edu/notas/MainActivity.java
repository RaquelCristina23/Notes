package br.ifsc.edu.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bd;
    ListView listnotas;
    Button novaNota;
    String id, titulo, texto, s;
    ArrayList arrayNotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = openOrCreateDatabase("database", MODE_PRIVATE, null);
        listnotas = findViewById(R.id.listNotas);
        novaNota = findViewById(R.id.btnNovaNota);


        //CREATE TABLE
        bd.execSQL("CREATE TABLE IF NOT EXISTS notas (" +
                "id integer primary key autoincrement," +
                "titulo varchar not null," +
                "texto varchar );");

        carregarLista();

        listnotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                s = (String) arrayNotas.get(i);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        novaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNota.class);
                startActivity(intent);
            }
        });

        listnotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                bd.delete("notas",  "titulo = '" + titulo + "'",  null);
                Toast.makeText(getApplicationContext(), "Nota removida", Toast.LENGTH_SHORT).show();
                carregarLista();

                return true;
            }

        });

    }

    public void carregarLista() {

        //SELECT
        Cursor cursor = bd.rawQuery("SELECT * FROM notas", null, null);
        cursor.moveToFirst();

        arrayNotas = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            cursor.moveToNext();
            arrayNotas.add(titulo);
        }


        //LIST
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                arrayNotas

        );

        //ON CLICK BEHAVIOR: "PRINT"
        listnotas.setAdapter(adapter);

    }

}

