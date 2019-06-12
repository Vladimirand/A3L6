package lesson5.rumpilstilstkin.ru.databaseex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

class UserDataSource {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] notesAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_LOGIN,
            DatabaseHelper.COLUMN_AVATAR
    };

    UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    void close() {
        dbHelper.close();
    }

    void addUser(Model user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, user.getUserId());
        values.put(DatabaseHelper.COLUMN_LOGIN, user.getLogin());
        values.put(DatabaseHelper.COLUMN_AVATAR, user.getAvatar());
        database.insert(DatabaseHelper.TABLE_USERS, null,
                values);
    }

    int deleteAll() {
        return database.delete(DatabaseHelper.TABLE_USERS, null, null);
    }

    List<Model> getAllUsers() {
        List<Model> notes = new ArrayList<Model>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS,
                notesAllColumn, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model user = cursorToUser(cursor);
            notes.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }

    private Model cursorToUser(Cursor cursor) {
        Model user = new Model();
        user.setId(cursor.getString(0));
        user.setLogin(cursor.getString(1));
        user.setAvatar(cursor.getString(2));
        return user;
    }
}