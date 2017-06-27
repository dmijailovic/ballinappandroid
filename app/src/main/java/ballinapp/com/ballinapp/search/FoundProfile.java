package ballinapp.com.ballinapp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.requests.SendRequest;
import ballinapp.com.ballinapp.search.PlayersResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoundProfile extends AppCompatActivity {

    TextView name, state, city, open, plus, minus;
    Long id;
    ImageButton plus_img;
    ImageButton minus_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_profile);
        getSupportActionBar().hide();

        id = getIntent().getExtras().getLong("receiver_id");

        plus_img = (ImageButton) findViewById(R.id.plus_imgb);
        minus_img = (ImageButton) findViewById(R.id.minus_imgb);

        name = (TextView) findViewById(R.id.team_name_tv_profile_src);
        state = (TextView) findViewById(R.id.state_tv_profile_src);
        city = (TextView) findViewById(R.id.city_tv_profile_src);
        open = (TextView) findViewById(R.id.open_tv_profile_src);
        plus = (TextView) findViewById(R.id.app_plus_profile_src);
        minus = (TextView) findViewById(R.id.app_minus_profile_src);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Team> call = apiService.getTeamById(id, HomeActivity.token, HomeActivity.teamId);
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

    public void appearancePlus(View view) {
        minus_img.setEnabled(false);
        plus_img.setEnabled(false);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.updateAppearance(id, "plus", HomeActivity.token, HomeActivity.teamId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.submited, Toast.LENGTH_SHORT).show();
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
        Call<Void> call = apiService.updateAppearance(id, "minus", HomeActivity.token, HomeActivity.teamId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.submited, Toast.LENGTH_SHORT).show();
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
}
