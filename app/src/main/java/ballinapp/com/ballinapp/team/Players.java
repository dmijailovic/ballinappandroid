package ballinapp.com.ballinapp.team;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.adapter.PlayerAdapter;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Players extends AppCompatActivity {

    private static final String TAG = Players.class.getSimpleName();
    List<Player> players = new ArrayList<>();
    int playerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        getSupportActionBar().hide();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Player>> call = apiService.getPlayersByTeam(HomeActivity.teamId);
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                players = response.body();
                Player[] array = new Player[players.size()];
                array = players.toArray(array);

                ListAdapter adapter = new PlayerAdapter(getApplicationContext(), array);
                ListView listView = (ListView) findViewById(R.id.players_list_view);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        playerId = players.get(position).getId();
                        AlertDialog.Builder alert = new AlertDialog.Builder(Players.this);
                        alert.setTitle(R.string.players);
                        alert.setMessage(R.string.choose_action);
                        alert.setNegativeButton(R.string.delete_player, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePlayer(playerId);
                            }
                        });
                        alert.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void deletePlayer(int playerId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.deletePlayer(HomeActivity.teamId, playerId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.player_deleted, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void addPlayer(View view) {
        startActivity(new Intent(this, AddPlayer.class));
    }

}
