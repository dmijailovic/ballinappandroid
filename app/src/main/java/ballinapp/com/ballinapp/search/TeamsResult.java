package ballinapp.com.ballinapp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.TeamsResultAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_result);
        getSupportActionBar().hide();

        String keyword = getIntent().getExtras().getString("keyword");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_teams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Team>> call = apiService.findTeamsByCity(keyword);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                List<Team> teams = response.body();
                recyclerView.setAdapter(new TeamsResultAdapter(teams, R.layout.teams_result_layout, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {

            }
        });
    }
}
