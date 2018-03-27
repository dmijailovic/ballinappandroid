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
import ballinapp.com.ballinapp.adapter.MyRequestsAdapter;
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

public class Requests extends AppCompatActivity {

    List<NewRequest> newRequests = new ArrayList<>();
    int requestId = 0;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NewRequest>> call = apiService.getRequests(HomeActivity.teamId, accessToken);
        call.enqueue(new Callback<List<NewRequest>>() {
            @Override
            public void onResponse(Call<List<NewRequest>> call, Response<List<NewRequest>> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    newRequests = response.body();
                    NewRequest[] array = new NewRequest[newRequests.size()];
                    array = newRequests.toArray(array);

                    ListAdapter adapter = new MyRequestsAdapter(getApplicationContext(), array);
                    ListView listView = (ListView) findViewById(R.id.list_view_requests);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            requestId = newRequests.get(position).getId();
                            AlertDialog.Builder alert = new AlertDialog.Builder(Requests.this);
                            alert.setTitle(R.string.requests);
                            alert.setMessage(R.string.choose_action);
                            alert.setPositiveButton(R.string.accept_request, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestResponse(requestId, true, "Request accepted!");
                                }
                            });

                            alert.setNegativeButton(R.string.reject_request, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestResponse(requestId, false, "Request rejected!");
                                }
                            });
                            alert.setNeutralButton(R.string.remove_from_requests, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    removeFromMyRequests(requestId);
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

    private void requestResponse(int requestId, boolean response, final String toast) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.requestResponse(requestId, response, accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromMyRequests(int requestId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.removeFromMyRequests(requestId, accessToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.removed_from_requests, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sentRequests(View view) {
        startActivity(new Intent(this, SentRequests.class));
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
