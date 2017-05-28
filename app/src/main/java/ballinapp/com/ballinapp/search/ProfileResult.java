package ballinapp.com.ballinapp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.SendRequest;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileResult extends AppCompatActivity {

    TextView name, state, city, open, plus, minus;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_result);
        getSupportActionBar().hide();

        name = (TextView) findViewById(R.id.team_name_tv_profile_src);
        state = (TextView) findViewById(R.id.state_tv_profile_src);
        city = (TextView) findViewById(R.id.city_tv_profile_src);
        open = (TextView) findViewById(R.id.open_tv_profile_src);
        plus = (TextView) findViewById(R.id.app_plus_profile_src);
        minus = (TextView) findViewById(R.id.app_minus_profile_src);

        String keyword = getIntent().getExtras().getString("keyword");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Team> call = apiService.findTeamByName(keyword);
        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                Team team = response.body();

                id = team.getTeam_id();

                name.setText(team.getName());
                state.setText(team.getState());
                city.setText(team.getCity());
                plus.setText(String.valueOf(team.getAppearance_plus()));
                minus.setText(String.valueOf(team.getAppearance_minus()));

                if(team.isOpen()) {
                    open.setTextColor(getResources().getColor(R.color.blue));
                    open.setText(R.string.open);
                } else {
                    open.setTextColor(getResources().getColor(R.color.red));
                    open.setText(R.string.closed);
                }

            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }
        });

    }

    public void players(View view) {
        startActivity(new Intent(this, PlayersResult.class).putExtra("id", id));
    }

    public void sendRequest(View view) {
        startActivity(new Intent(this, SendRequest.class).putExtra("receiver_id", id));
    }
}
