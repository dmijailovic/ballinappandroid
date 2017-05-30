package ballinapp.com.ballinapp.public_games;

import android.content.DialogInterface;
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

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.MyGamesAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesResult extends AppCompatActivity {

    List<Game> games = new ArrayList<>();
    String keyword;
    int gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_result);
        getSupportActionBar().hide();

        keyword = getIntent().getExtras().getString("keyword");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Game>> call = apiService.findGamesByCity(keyword);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                games = response.body();
                Game[] array = new Game[games.size()];
                array = games.toArray(array);

                ListAdapter adapter = new MyGamesAdapter(getApplicationContext(), array);
                ListView listView = (ListView) findViewById(R.id.list_view_games);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gameId = games.get(position).getId();
                        AlertDialog.Builder alert = new AlertDialog.Builder(GamesResult.this);
                        alert.setTitle(R.string.games);
                        alert.setMessage(R.string.choose_action);
                        alert.setPositiveButton(R.string.join_game, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                joinGame(gameId);
                            }
                        });
                        alert.show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {

            }
        });
    }

    public void joinGame(int gameId) {
        Team team = new Team();
        team.setTeam_id(HomeActivity.teamId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.joinGame(gameId, team);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.joined_game, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
