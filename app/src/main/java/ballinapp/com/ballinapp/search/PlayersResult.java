package ballinapp.com.ballinapp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.PlayerAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersResult extends AppCompatActivity {

    List<Player> players = new ArrayList<>();
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_result);
        getSupportActionBar().hide();

        id = getIntent().getExtras().getLong("id");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Player>> call = apiService.getPlayersByTeam(id);
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                players = response.body();
                Player[] array = new Player[players.size()];
                array = players.toArray(array);

                ListAdapter adapter = new PlayerAdapter(getApplicationContext(), array);
                ListView listView = (ListView) findViewById(R.id.players_list_view_src);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {

            }
        });

    }
}
