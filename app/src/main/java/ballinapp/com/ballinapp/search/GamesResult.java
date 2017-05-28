package ballinapp.com.ballinapp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.GameAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Game;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_result);
        getSupportActionBar().hide();

        String keyword = getIntent().getExtras().getString("keyword");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Game>> call = apiService.findGamesByCity(keyword);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> games = response.body();
                recyclerView.setAdapter(new GameAdapter(games, R.layout.list_item_game, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {

            }
        });
    }
}
