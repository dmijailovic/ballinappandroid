package ballinapp.com.ballinapp.team;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.MyGamesAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGames extends AppCompatActivity {

    List<Game> gamesList = new ArrayList<>();
    int gameId = 0;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Game>> call = apiService.getMyGames(HomeActivity.teamId, accessToken);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    gamesList = response.body();
                    Game[] gamesArray = new Game[gamesList.size()];
                    gamesArray = gamesList.toArray(gamesArray);

                    ListAdapter adapter = new MyGamesAdapter(getApplicationContext(), gamesArray);
                    ListView listView = findViewById(R.id.list_view_my_games);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            gameId = gamesList.get(position).getId();
                            AlertDialog.Builder alert = new AlertDialog.Builder(MyGames.this);
                            alert.setTitle(R.string.my_games);
                            alert.setMessage(R.string.choose_action);
                            alert.setNegativeButton(R.string.leave_game, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    leaveGame(gameId);
                                }
                            });
                            alert.show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {

            }
        });
    }

    private void leaveGame(int gameId) {
        Team team = new Team();
        team.setTeam_id(HomeActivity.teamId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.leaveGame(gameId, team, accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.game_left, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void insertLoginData(JWTInfo jwtInfo) {
        dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.insertLoginData(jwtInfo);
        dbHelper.close();
    }

    public void refreshToken(int teamId, String refresh) {
        RefreshInfo refreshInfo = new RefreshInfo(teamId, refresh);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JWTInfo> call = apiService.refreshToken(refreshInfo);
        call.enqueue(new Callback<JWTInfo>() {
            @Override
            public void onResponse(Call<JWTInfo> call, Response<JWTInfo> response) {
                if(response.code() == 401) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    JWTInfo jwtInfo = response.body();
                    insertLoginData(jwtInfo);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<JWTInfo> call, Throwable t) {

            }
        });
    }
}
