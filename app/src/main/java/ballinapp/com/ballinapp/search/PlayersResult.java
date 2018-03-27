package ballinapp.com.ballinapp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.PlayerAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.Player;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersResult extends AppCompatActivity {

    List<Player> players = new ArrayList<>();
    int id;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_result);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        id = getIntent().getExtras().getInt("id");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Player>> call = apiService.getPlayersByTeam(id, accessToken);
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    players = response.body();
                    Player[] array = new Player[players.size()];
                    array = players.toArray(array);

                    ListAdapter adapter = new PlayerAdapter(getApplicationContext(), array);
                    ListView listView = findViewById(R.id.players_list_view_src);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {

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
