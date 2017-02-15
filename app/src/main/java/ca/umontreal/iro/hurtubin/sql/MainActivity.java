package ca.umontreal.iro.hurtubin.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.security.Timestamp;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbh = new DBHelper(getApplicationContext());

        db = dbh.getDB();

        Button button = (Button) findViewById(R.id.button);

        final EditText login = (EditText) findViewById(R.id.login);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText name = (EditText) findViewById(R.id.name);
        final Switch admin = (Switch) findViewById(R.id.access_level);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ContentValues values = new ContentValues();

                    values.put("login", login.getText().toString());
                    values.put("password", password.getText().toString());
                    values.put("name", name.getText().toString());
                    values.put("access_level", admin.isChecked() ? "admin" : "user");

                    long insertedId = db.insertOrThrow("users", null, values);

                    Cursor c = db.rawQuery("SELECT * FROM users", null);

                    Toast.makeText(getApplicationContext(), "Nombre d'utilisateurs : " + c.getCount(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "ID inséré : " + insertedId, Toast.LENGTH_SHORT).show();

                } catch(SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), "Impossible d'ajouter l'utilisateur : contrainte SQL non respectée", Toast.LENGTH_SHORT).show();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button listUsers = (Button) findViewById(R.id.list_users);

        listUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UsersActivity.class);

                startActivity(i);
            }
        });
    }
}
