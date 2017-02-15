package ca.umontreal.iro.hurtubin.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsersActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ListView list = (ListView) findViewById(R.id.list);
        TextView title = (TextView) findViewById(R.id.title);

        db = new DBHelper(this).getDB();

        Cursor c = db.query("users", new String[]{"_id", "login", "name"}, null, null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c,
                new String[]{"_id", "login", "name"},
                new int[]{0, android.R.id.text1, android.R.id.text2},
                0
        );

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sql = "SELECT numero, datetime(debut, 'unixepoch') AS start " +
                        "FROM reservations " +
                        "JOIN locaux ON locaux._id=reservations.local_id " +
                        "WHERE user_id=? " +
                        "LIMIT 1";

                Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});

                if(c.getCount() > 0) {

                    c.moveToFirst();

                    String numero = c.getString(c.getColumnIndex("numero"));
                    String start = c.getString(c.getColumnIndex("start"));

                    Toast.makeText(UsersActivity.this,
                            "Prochaine réservation : local " + numero + ", le " + start,
                            Toast.LENGTH_LONG
                    ).show();

                } else {
                    Toast.makeText(UsersActivity.this, "Aucune réservation prévue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        title.setText(c.getCount() + " Utilisateurs");
    }
}
