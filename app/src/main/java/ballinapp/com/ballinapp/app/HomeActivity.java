package ballinapp.com.ballinapp.app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import ballinapp.com.ballinapp.public_games.CreateGame;
import ballinapp.com.ballinapp.requests.Requests;
import ballinapp.com.ballinapp.public_games.GameSearch;
import ballinapp.com.ballinapp.search.TeamSearch;
import ballinapp.com.ballinapp.team.MyProfile;

public class HomeActivity extends AppCompatActivity {

    public static String accessToken;
    public static String refresh;
    public static int teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        JWTInfo loginInfo = getLoginInfo();
        accessToken = loginInfo.getAccessToken();
        refresh = loginInfo.getRefresh();
        teamId = loginInfo.getTeamId();

     }

     public JWTInfo getLoginInfo() {
         final SQLiteOpenHelper helper = new DBHelper(this, null, null, 1);
         SQLiteDatabase rdb = helper.getReadableDatabase();

         JWTInfo jwtInfo = new JWTInfo();

         Cursor cursor = rdb.rawQuery("SELECT * FROM login_data order by id desc", new String[]{});
         if(cursor.getCount() > 0) {
             cursor.moveToFirst();
             jwtInfo.setAccessToken(cursor.getString(cursor.getColumnIndex("token")));
             jwtInfo.setRefresh(cursor.getString(cursor.getColumnIndex("refresh")));
             jwtInfo.setTeamId(cursor.getInt(cursor.getColumnIndex("team_id")));
         }
         cursor.close();
         rdb.close();
         helper.close();
         return jwtInfo;
     }

     public void profile(View view) {
         startActivity(new Intent(this, MyProfile.class));
     }

     public void findTeams(View view) {
        startActivity(new Intent(this, TeamSearch.class));
     }

     public void findGames(View view) {
        startActivity(new Intent(this, GameSearch.class));
     }

     public void requests(View view) {
         startActivity(new Intent(this, Requests.class));
     }

     public void createGame(View view) {
         startActivity(new Intent(this, CreateGame.class));
     }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}