package ballinapp.com.ballinapp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.data.util.AppearanceUpdateBean;
import ballinapp.com.ballinapp.data.util.AppearanceUpdateEnum;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import ballinapp.com.ballinapp.requests.SendRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoundProfile extends AppCompatActivity {

    TextView name, state, city, open, plus, minus;
    int id;
    ImageButton plus_img;
    ImageButton minus_img;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_profile);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        id = getIntent().getExtras().getInt("receiver_id");

        plus_img = findViewById(R.id.plus_imgb);
        minus_img = findViewById(R.id.minus_imgb);

        name = findViewById(R.id.team_name_tv_profile_src);
        state = findViewById(R.id.state_tv_profile_src);
        city = findViewById(R.id.city_tv_profile_src);
        open = findViewById(R.id.open_tv_profile_src);
        plus = findViewById(R.id.app_plus_profile_src);
        minus = findViewById(R.id.app_minus_profile_src);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Team> call = apiService.getTeamById(id, accessToken);
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
                        open.setText(R.string.open);
                    } else {
                        open.setTextColor(getResources().getColor(R.color.red));
                        open.setText(R.string.closed);
                    }
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

    public void appearancePlus(View view) {
        minus_img.setEnabled(false);
        plus_img.setEnabled(false);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateAppearance(new AppearanceUpdateBean(id, AppearanceUpdateEnum.PLUS), accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.submited, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void appearanceMinus(View view) {
        minus_img.setEnabled(false);
        plus_img.setEnabled(false);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateAppearance(new AppearanceUpdateBean(id, AppearanceUpdateEnum.MINUS), accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.submited, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TeamSearch.class));
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
