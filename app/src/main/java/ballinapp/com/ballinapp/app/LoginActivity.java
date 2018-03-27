package ballinapp.com.ballinapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.LoginInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText teamName;
    EditText password;
    TextView inputError;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        teamName = findViewById(R.id.team_name_login_screen);
        password = findViewById(R.id.password_login_screen);
        inputError = findViewById(R.id.invalid_input_error_login_activity);

    }

    public LoginInfo getData() {
        return new LoginInfo(teamName.getText().toString(), password.getText().toString());
    }

    public void login(View view) {
        LoginInfo info = getData();
        if(validateInput(info)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JWTInfo> call = apiService.login(info);
            call.enqueue(new Callback<JWTInfo>() {
                @Override
                public void onResponse(Call<JWTInfo> call, Response<JWTInfo> response) {
                    if(response.code() == 401) {
                        inputError.setText(R.string.wrong_credentials);
                    } else {
                        JWTInfo jwtInfo = response.body();
                        insertLoginData(jwtInfo);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }
                @Override
                public void onFailure(Call<JWTInfo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            inputError.setText(R.string.invalid_info);
        }
    }

    public void insertLoginData(JWTInfo jwtInfo) {
        dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.insertLoginData(jwtInfo);
        dbHelper.close();
    }

    public boolean validateInput(LoginInfo info) {
        String name = info.getTeamName();
        String pass = info.getPassword();

        boolean nameBool = false;
        boolean passBool = false;

        boolean emptyNameCheck = true;
        if(name.isEmpty()) {
           emptyNameCheck = false;
        }


        boolean charCheck = true;
        for(int i = 0; i < name.length(); i++) {
            if(!Character.isLetter(name.charAt(i))) {
                charCheck = false;
            }
        }

        if(emptyNameCheck && charCheck) {
            nameBool = true;
        }

        if(!pass.isEmpty()) {
            passBool = true;
        }

        return nameBool && passBool;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
