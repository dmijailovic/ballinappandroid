package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    EditText name, state, city, email;
    TextView error;
    Long teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.team_name_et_register_page);
        state = (EditText) findViewById(R.id.team_state_et_register_page);
        city = (EditText) findViewById(R.id.team_city_et_register_page);
        email = (EditText) findViewById(R.id.team_email_et_register_page);
        error = (TextView) findViewById(R.id.error_tv_update_team);
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
        if(validateTeam(getData())) {
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
        } else {
            error.setText(R.string.invalid_info);
        }
    }

    private boolean validateTeam(Team team) {
        String name = team.getName();
        String state = team.getState();
        String city = team.getCity();
        String email = team.getEmail();

        boolean nameB = false;
        boolean stateB = false;
        boolean cityB = false;
        boolean emailB = false;

        if (!name.isEmpty()) {
            nameB = true;
        }

        if (state.length() < 25 && !state.isEmpty()) {
            int character = 0;
            for (int i = 0; i < state.length(); i++) {
                if (Character.isDigit(state.charAt(i))) {
                    character++;
                }
            }
            if (character == 0) {
                stateB = true;
            }
        }

        if (city.length() < 20 && !city.isEmpty()) {
            int character = 0;
            for (int i = 0; i < city.length(); i++) {
                if (Character.isDigit(city.charAt(i))) {
                    character++;
                }
            }
            if (character == 0) {
                cityB = true;
            }
        }

        if(!email.isEmpty()) {
            int at = 0;
            int dot = 0;
            for(int i = 0; i < email.length(); i++) {
                if(email.charAt(i) == '@') {
                    at++;
                }

                if(email.charAt(i) == '.') {
                    dot++;
                }
            }
            if(at == 1 && dot > 0) {
                emailB = true;
            }
        }

        return nameB && stateB && cityB && emailB;
    }

}
