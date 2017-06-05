package ballinapp.com.ballinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ballinapp.com.ballinapp.team.UpdateTeam;
import retrofit2.Call;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        errorTv = (TextView) findViewById(R.id.error_tv_main_activity);

        if(isLoggedIn() != null) {
            String[] credentials = isLoggedIn();
            loginCheck(Long.parseLong(credentials[0]), credentials[1]);
        } else {
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    final String idString = loginResult.getAccessToken().getUserId();
                    final String token = loginResult.getAccessToken().getToken();
                    Long userId = Long.parseLong(idString);
                    loginCheck(userId, token);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    String fail = getResources().getString(R.string.login_failed);
                    errorTv.setText(fail);
                }
            });
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String[] isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        String id = "";
        String token = "";
        if(accessToken != null && !accessToken.isExpired()) {
            id = accessToken.getUserId();
            token = accessToken.getToken();
        } else {
            id = null;
            token = null;
        }
        return new String[] {id, token};
    }

    public void loginCheck(final Long id, final String token) {
        ApiInterface apiService = ApiClient.getStringClient().create(ApiInterface.class);
        Call<String> call = apiService.checkAccount(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String check = response.body();
                if(response.isSuccessful()) {
                    if("true".equals(check)) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("id", id).putExtra("token", token));
                    } else {
                        startActivity(new Intent(getApplicationContext(), UpdateTeam.class).putExtra("id", id).putExtra("token", token));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

}
