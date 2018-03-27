package ballinapp.com.ballinapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ballinapp.com.ballinapp.data.util.JWTInfo;

/**
 * Created by dusan on 3/26/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "auth";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE login_data (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "team_id INTEGER, token TEXT, refresh TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insertLoginData(JWTInfo jwtInfo) {
        ContentValues cv = new ContentValues();
        cv.put("token", jwtInfo.getAccessToken());
        cv.put("refresh", jwtInfo.getRefresh());
        cv.put("team_id", jwtInfo.getTeamId());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("login_data", null, cv);
        db.close();
    }

}
