package ca.umontreal.iro.hurtubin.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    static final int DATABASE_VERSION = 6;
    static final String DATABASE_NAME = "reservations.db";

    static final String[] TABLES = new String[]{
            "locaux",
            "reservations",
            "users"
    };

    static final String[] SQL_CREATE_DB = new String[]{
            "CREATE TABLE `locaux` (" +
                    " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `numero` TEXT NOT NULL," +
                    " `categorie` TEXT NOT NULL," +
                    " CHECK(`categorie` in ('reunion', 'cours'))" +
                    ")",
            "CREATE TABLE `reservations` (" +
                    " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `local_id` INTEGER NOT NULL," +
                    " `user_id` INTEGER NOT NULL," +
                    " `raison` TEXT NOT NULL," +
                    " `debut` INTEGER NOT NULL," +
                    " `duration` INTEGER NOT NULL," +
                    " `cours` TEXT NOT NULL" +
                    ")",
            "CREATE TABLE `users` (" +
                    " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `login` TEXT NOT NULL," +
                    " `password` TEXT NOT NULL," +
                    " `name` TEXT NOT NULL," +
                    " `access_level` TEXT NOT NULL," +
                    " `last_login` INTEGER DEFAULT NULL," +
                    " CHECK(`access_level` in ('admin', 'user'))" +
                    ")",
            "CREATE UNIQUE INDEX `unique_login` ON `users`(`login`)",
            // Données initiales
            "INSERT INTO `locaux`(`numero`, `categorie`) VALUES(2165, \"reunion\")",

            "INSERT INTO `users`(`login`, `password`, `name`, `access_level`) VALUES" +
                    "(\"admin\", \"$up3rP4$$\", \"Jack Kerouac\", \"admin\")," +
                    "(\"johnsmith\", \"superpass123\", \"John Smith\", \"user\")",

            "INSERT INTO `reservations`(`local_id`, `user_id`, `raison`, `debut`, `duration`, `cours`) VALUES" +
                    "(1, 2, \"Réunion pour le MILA\", 1487250000, 60 * 60, \"\")," +
                    "(1, 2, \"Réunion pour le MILA\", 1487422800, 60 * 60, \"\")",
    };


    private static SQLiteDatabase db = null;

    public DBHelper(Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);

        if (DBHelper.db == null)
            DBHelper.db = getWritableDatabase();
    }

    public SQLiteDatabase getDB() {
        return DBHelper.db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String statement : DBHelper.SQL_CREATE_DB) {
            db.execSQL(statement);
        }
        Log.i("DB", "Création de la base de données -- v" + DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete all tables
        for (String table : DBHelper.TABLES) {
            db.execSQL("DROP TABLE IF EXISTS `" + table + "`");
        }

        // Re-create the database with the new scheme
        onCreate(db);
    }
}
