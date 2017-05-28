package ballinapp.com.ballinapp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.PlayerAdapter;
import ballinapp.com.ballinapp.adapter.PlayerResultAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_result);
        getSupportActionBar().hide();

        Long id = getIntent().getExtras().getLong("id");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.players_recycler_view_src);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Player>> call = apiService.getPlayersByTeam(id);
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                List<Player> players = response.body();
                recyclerView.setAdapter(new PlayerResultAdapter(players, R.layout.players_layout, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {

            }
        });

    }
}
