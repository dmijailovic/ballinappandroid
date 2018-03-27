package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {

    TextView name, state, city, plus, minus, open;
    Switch switch1;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().hide();

        name = findViewById(R.id.team_name_tv_profile);
        state = findViewById(R.id.state_tv_profile);
        city = findViewById(R.id.city_tv_profile);
        plus = findViewById(R.id.app_plus_profile);
        minus = findViewById(R.id.app_minus_profile);
        open = findViewById(R.id.open_tv_profile);
        switch1 = findViewById(R.id.switch1);

        String accessToken = "Bearer " + HomeActivity.accessToken;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Team> call = apiService.getTeamById(HomeActivity.teamId, accessToken);

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Team team = response.body();

                    name.setText(team.getName());
                    state.setText(team.getState());
                    city.setText(team.getCity());
                    plus.setText(String.valueOf(team.getAppearancePlus()));
                    minus.setText(String.valueOf(team.getAppearanceMinus()));

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
        String accessToken = "Bearer " + HomeActivity.accessToken;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateAvailability(HomeActivity.teamId, accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.av_changed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
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
