package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {

    TextView name, state, city, open, plus, minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().hide();

        name = (TextView) findViewById(R.id.team_name_tv_profile);
        state = (TextView) findViewById(R.id.state_tv_profile);
        city = (TextView) findViewById(R.id.city_tv_profile);
        open = (TextView) findViewById(R.id.open_tv_profile);
        plus = (TextView) findViewById(R.id.app_plus_profile);
        minus = (TextView) findViewById(R.id.app_minus_profile);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Team> call = apiService.getTeamById(HomeActivity.teamId);

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                Team team = response.body();

                name.setText(team.getName());
                state.setText(team.getState());
                city.setText(team.getCity());
                plus.setText(String.valueOf(team.getAppearance_plus()));
                minus.setText(String.valueOf(team.getAppearance_minus()));

                if(team.isOpen()) {
                    open.setText(R.string.open);
                } else {
                    open.setText(R.string.close);
                }

            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }
        });
    }

    public void players(View view) {
        startActivity(new Intent(this, Players.class));
        Toast.makeText(getApplicationContext(), R.string.tap_to_delete, Toast.LENGTH_LONG).show();
    }

    public void myGames(View view) {
        startActivity(new Intent(this, MyGames.class));
        Toast.makeText(getApplicationContext(), R.string.tap_to_leave, Toast.LENGTH_LONG).show();
    }
}
