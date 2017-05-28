package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTeam extends AppCompatActivity {

    TextView name, state, city, email;
    Long teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        getSupportActionBar().hide();

        name = (TextView) findViewById(R.id.team_name_et_register_page);
        state = (TextView) findViewById(R.id.team_state_et_register_page);
        city = (TextView) findViewById(R.id.team_city_et_register_page);
        email = (TextView) findViewById(R.id.team_email_et_register_page);
        teamId = getIntent().getExtras().getLong("id");

    }

    public Team getData() {
        Team team = new Team();

        team.setTeam_id(teamId);
        team.setName(name.getText().toString());
        team.setState(state.getText().toString());
        team.setCity(city.getText().toString());
        team.setEmail(email.getText().toString());
        team.setOpen(true);
        team.setActive(true);

        return team;
    }

    public void registerTeam(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.addTeam(getData());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("id", teamId));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
