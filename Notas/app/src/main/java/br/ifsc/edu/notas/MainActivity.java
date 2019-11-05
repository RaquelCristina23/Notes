package br.ifsc.edu.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = openOrCreateDatabase("database",MODE_PRIVATE,null);

        bd.execSQL("CREATE TABLE IF NOT EXISTS notas (" +
                  "id integer primary key autoincrement," +
                  "titulo varchar not null," +
                  "texto varchar );");

//        bd.execSQL("INSERT INTO notas (id, titulo, texto) VALUES (null, \"Primeira nota\", \"primeiro texto\");");

        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo","Segunda Nota");
        contentValues.put("texto","segundo texto");
        bd.insert("notas", null,contentValues);

        Cursor  cursor = bd.rawQuery("SELECT * FROM notas", null,null);
        cursor.moveToFirst();

        String id, titulo, texto;

        while (!cursor.isAfterLast()) {
            id = cursor.getString(cursor.getColumnIndex("id"));
            titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            texto = cursor.getString(cursor.getColumnIndex("texto"));
            Log.d("notas", id + titulo + texto);
            cursor.moveToNext();
        }


    }
}
