package ballinapp.com.ballinapp.app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        int teamId = getTeamId();

        if(teamId > 0) {
            checkIfLoggedIn(teamId);
        } else {
            startActivity(new Intent(this, MiddleActivity.class));
        }

    }

    public void checkIfLoggedIn(int teamId) {
        Team team = new Team();
        team.setTeam_id(teamId);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiService.checkIfLoggedIn(team);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getTeamId() {
        final SQLiteOpenHelper helper = new DBHelper(this, null, null, 1);
        SQLiteDatabase rdb = helper.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("SELECT team_id FROM login_data order by team_id desc", new String[]{});
        int res = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            res = cursor.getInt(cursor.getColumnIndex("team_id"));
        }
        cursor.close();
        return res;
    }

}
