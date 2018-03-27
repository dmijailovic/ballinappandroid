package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class RegisterTeam extends AppCompatActivity {

    EditText name, state, city, email, password;
    TextView error;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);
        getSupportActionBar().hide();

        name = findViewById(R.id.team_name_et_register_page);
        state = findViewById(R.id.team_state_et_register_page);
        city = findViewById(R.id.team_city_et_register_page);
        email = findViewById(R.id.team_email_et_register_page);
        error = findViewById(R.id.error_tv_update_team);
        password = findViewById(R.id.team_password_et_register_team);

    }

    public Team getData() {
        Team team = new Team();
        team.setName(name.getText().toString());
        team.setState(state.getText().toString());
        team.setCity(city.getText().toString());
        team.setEmail(email.getText().toString());
        team.setPassword(password.getText().toString());
        team.setOpen(true);
        team.setActive(true);
        return team;
    }

    public void registerTeam(View view) {
        Team team = getData();
        if(validateTeam(team)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JWTInfo> call = apiService.addTeam(team);
            call.enqueue(new Callback<JWTInfo>() {
                @Override
                public void onResponse(Call<JWTInfo> call, Response<JWTInfo> response) {
                    if(response.message().contains("expired")) {
                        refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                    } else {
                        JWTInfo jwtInfo = response.body();
                        insertLoginData(jwtInfo);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<JWTInfo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.wrong, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            error.setText(R.string.invalid_info);
        }
    }

    public void insertLoginData(JWTInfo jwtInfo) {
        dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.insertLoginData(jwtInfo);
        dbHelper.close();
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
