package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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

    TextView name, state, city, plus, minus, open;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().hide();

        name = (TextView) findViewById(R.id.team_name_tv_profile);
        state = (TextView) findViewById(R.id.state_tv_profile);
        city = (TextView) findViewById(R.id.city_tv_profile);
        plus = (TextView) findViewById(R.id.app_plus_profile);
        minus = (TextView) findViewById(R.id.app_minus_profile);
        open = (TextView) findViewById(R.id.open_tv_profile);
        switch1 = (Switch) findViewById(R.id.switch1);

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
                    switch1.setChecked(false);
                    open.setText(R.string.open);
                } else {
                    switch1.setChecked(true);
                    open.setText(R.string.close);
                }

                switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        changeAvailability();
                        if(isChecked) {
                            open.setText(R.string.close);
                        } else {
                            open.setText(R.string.open);
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }
        });
    }

    public void players(View view) {
        startActivity(new Intent(this, Players.class));
    }

    public void myGames(View view) {
        startActivity(new Intent(this, MyGames.class));
    }

    public void changeAvailability() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateAvailability(HomeActivity.teamId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.av_changed, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
