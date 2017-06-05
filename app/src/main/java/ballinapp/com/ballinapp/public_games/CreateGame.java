package ballinapp.com.ballinapp.public_games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.team.MyProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGame extends AppCompatActivity {

    EditText state, city, address, date, time, contact, message;
    TextView error;

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
        error = (TextView) findViewById(R.id.error_tv_create_game);
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
        if (validateGame(getGame())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Void> call = apiService.createGame(getGame(), HomeActivity.token, HomeActivity.teamId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    Toast.makeText(getApplicationContext(), R.string.game_created, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } else {
            error.setText(R.string.invalid_info);
        }

    }

    private boolean validateGame(Game game) {
        String message = game.getMessage();
        String contact = game.getContact();
        String state = game.getState();
        String city = game.getCity();
        String address = game.getAddress();
        String date = game.getDate();
        String time = game.getTime();

        boolean msg = false;
        boolean cnt = false;
        boolean sta = false;
        boolean cit = false;
        boolean add = false;
        boolean dat = false;
        boolean tim = false;

        if(!message.isEmpty()) {
            msg = true;
        }

        if(!contact.isEmpty() && contact.length() < 35) {
            cnt = true;
        }

        if (state.length() < 25 && !state.isEmpty()) {
            int character = 0;
            for (int i = 0; i < state.length(); i++) {
                if (Character.isDigit(state.charAt(i))) {
                    character++;
                }
            }
            if (character == 0) {
                sta = true;
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
                cit = true;
            }
        }

        if(!address.isEmpty() && address.length() < 35) {
            add = true;
        }

        if(!date.isEmpty() && date.length() < 20) {
            dat = true;
        }

        if (!time.isEmpty() && time.length() < 6) {
            int letter = 0;
            for(int i = 0; i < time.length(); i++) {
                if(Character.isLetter(time.charAt(i))) {
                    letter++;
                }
            }
            if(letter == 0) {
                tim = true;
            }
        }

        return msg && cnt && sta && cit && add & dat & tim;
    }

}
