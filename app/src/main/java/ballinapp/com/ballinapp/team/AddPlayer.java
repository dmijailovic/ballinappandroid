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
import ballinapp.com.ballinapp.data.Player;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayer extends AppCompatActivity {

    EditText nickname, birthyear, contact;
    TextView error;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        getSupportActionBar().hide();

        nickname = findViewById(R.id.add_player_nickname_et);
        birthyear = findViewById(R.id.add_player_birthyear_et);
        contact = findViewById(R.id.add_player_contact_et);
        error = findViewById(R.id.error_tv_add_player);

        accessToken = "Bearer " + HomeActivity.accessToken;

    }

    public Player getData() {
        return new Player(nickname.getText().toString(), Integer.parseInt(birthyear.getText().toString()), contact.getText().toString());
    }

    public void addPlayer(View view) {
        if(validatePlayer(getData())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Void> call = apiService.addPlayer(HomeActivity.teamId, getData(), accessToken);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.message().contains("expired")) {
                        refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                    } else {
                        Toast.makeText(getApplicationContext(), "Player successfully added!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MyProfile.class));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } else {
            error.setText(R.string.invalid_info);
        }

    }

    private boolean validatePlayer(Player player) {
        String nickname = player.getNickname();
        int birthyear = player.getBirthyear();
        String byString = String.valueOf(birthyear);
        String contact = player.getContact();

        boolean nick = false;
        boolean year = false;
        boolean con = false;


        if(!nickname.isEmpty() && nickname.length() < 20) {
            nick = true;
        }

        if(!byString.isEmpty() && byString.length() == 4) {
            int chr = 0;
            for(int i = 0; i < byString.length(); i++) {
                if(Character.isLetter(byString.charAt(i))) {
                    chr++;
                }
            }
            if(chr == 0) {
                year = true;
            }
        }

        if(contact.length() < 25) {
            con = true;
        }

        return nick && year && con;

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
