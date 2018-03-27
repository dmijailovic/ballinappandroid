package ballinapp.com.ballinapp.requests;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.SentRequestsAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.NewRequest;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentRequests extends AppCompatActivity {
    List<NewRequest> sentRequests = new ArrayList<>();
    int requestId = 0;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_requests);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NewRequest>> call = apiService.getSentRequests(HomeActivity.teamId, accessToken);
        call.enqueue(new Callback<List<NewRequest>>() {
            @Override
            public void onResponse(Call<List<NewRequest>> call, Response<List<NewRequest>> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    sentRequests = response.body();
                    NewRequest[] array = new NewRequest[sentRequests.size()];
                    array = sentRequests.toArray(array);

                    ListAdapter adapter = new SentRequestsAdapter(getApplicationContext(), array);
                    ListView listView = (ListView) findViewById(R.id.sent_requests_list_view);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            requestId = sentRequests.get(position).getId();
                            AlertDialog.Builder alert = new AlertDialog.Builder(SentRequests.this);
                            alert.setTitle(R.string.requests);
                            alert.setMessage(R.string.choose_action);
                            alert.setNegativeButton(R.string.cancel_request, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancelRequest(requestId);
                                }
                            });
                            alert.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<NewRequest>> call, Throwable t) {

            }
        });
    }

    private void cancelRequest(int requestId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.deleteRequest(requestId, accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.request_deleted, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.wrong, Toast.LENGTH_SHORT).show();
            }
        });
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
