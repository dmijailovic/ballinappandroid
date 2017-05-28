package ballinapp.com.ballinapp.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayer extends AppCompatActivity {

    EditText nickname;
    EditText birthyear;
    EditText contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        getSupportActionBar().hide();

        nickname = (EditText) findViewById(R.id.add_player_nickname_et);
        birthyear = (EditText) findViewById(R.id.add_player_birthyear_et);
        contact = (EditText) findViewById(R.id.add_player_contact_et);


    }

    public Player getData() {
        return new Player(nickname.getText().toString(), Integer.parseInt(birthyear.getText().toString()), contact.getText().toString());
    }

    public void addPlayer(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.addPlayer(HomeActivity.teamId, getData());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Player successfully added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("id", HomeActivity.teamId));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
