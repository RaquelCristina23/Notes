package br.ifsc.edu.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNota extends AppCompatActivity {
    EditText titulo, texto;
    Button add;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nota);

        add = findViewById(R.id.btnAdd);
        titulo = findViewById(R.id.txtTitulo);
        texto = findViewById(R.id.txtTexto);

        bd = openOrCreateDatabase("database", MODE_PRIVATE, null);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloS = titulo.getText().toString();
                String textoS = texto.getText().toString();

                Insert(tituloS, textoS);

                Toast.makeText(AddNota.this, "Nota adicionada!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddNota.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Insert(String titulo, String texto) {
        //INSERT
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", titulo);
        contentValues.put("texto", texto);
        bd.insert("listnotas", null, contentValues);
    }
}
