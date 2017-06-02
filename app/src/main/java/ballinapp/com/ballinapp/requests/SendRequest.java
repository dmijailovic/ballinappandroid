package ballinapp.com.ballinapp.requests;

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
import ballinapp.com.ballinapp.data.Request;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.team.MyProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendRequest extends AppCompatActivity {

    EditText state, city, address, date, time, contact, message;
    TextView error;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        getSupportActionBar().hide();

        state = (EditText) findViewById(R.id.state_input_send_request);
        city = (EditText) findViewById(R.id.city_input_send_request);
        address = (EditText) findViewById(R.id.address_input_send_request);
        date = (EditText) findViewById(R.id.date_input_send_request);
        time = (EditText) findViewById(R.id.time_input_send_request);
        contact = (EditText) findViewById(R.id.contact_input_send_request);
        message = (EditText) findViewById(R.id.message_input_send_request);
        error = (TextView) findViewById(R.id.error_tv_send_request);
        id = getIntent().getExtras().getLong("receiver_id");
    }

    public Request getData() {
        Team senderTeam = new Team();
        senderTeam.setTeam_id(HomeActivity.teamId);

        Team receiverTeam = new Team();
        receiverTeam.setTeam_id(id);

        Request request = new Request();

        request.setSenderTeamId(senderTeam);
        request.setReceiverTeamId(receiverTeam);
        request.setState(state.getText().toString());
        request.setCity(city.getText().toString());
        request.setAddress(address.getText().toString());
        request.setDate(date.getText().toString());
        request.setTime(time.getText().toString());
        request.setContact(contact.getText().toString());
        request.setMessage(message.getText().toString());

        return request;
    }

    public void sendRequest(View view) {
        if(validateRequest(getData())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Void> call = apiService.sendRequest(getData());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), R.string.request_sent, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MyProfile.class));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } else {
            error.setText(R.string.invalid_info);
        }
    }

    private boolean validateRequest(Request request) {

        String message = request.getMessage();
        String contact = request.getContact();
        String state = request.getState();
        String city = request.getCity();
        String address = request.getAddress();
        String date = request.getDate();
        String time = request.getTime();

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
