package ballinapp.com.ballinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.team.MyProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGame extends AppCompatActivity {

    EditText state, city, address, date, time, contact, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        getSupportActionBar().hide();

        state = (EditText) findViewById(R.id.state_input_create_game);
        city = (EditText) findViewById(R.id.city_input_create_game);
        address = (EditText) findViewById(R.id.address_input_create_game);
        date = (EditText) findViewById(R.id.date_input_create_game);
        time = (EditText) findViewById(R.id.time_input_create_game);
        contact = (EditText) findViewById(R.id.contact_input_create_game);
        message = (EditText) findViewById(R.id.message_input_create_game);
    }

    public Game getGame() {
        Game game = new Game();

        game.setState(state.getText().toString());
        game.setCity(city.getText().toString());
        game.setAddress(address.getText().toString());
        game.setDate(date.getText().toString());
        game.setTime(time.getText().toString());
        game.setContact(contact.getText().toString());
        game.setMessage(message.getText().toString());

        return game;
    }

    public void createGame(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.createGame(getGame());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
                Toast.makeText(getApplicationContext(), R.string.game_created, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
