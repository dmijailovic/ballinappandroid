package ballinapp.com.ballinapp.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.app.HomeActivity;
import ballinapp.com.ballinapp.app.LoginActivity;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import ballinapp.com.ballinapp.db.DBHelper;
import ballinapp.com.ballinapp.requests.SendRequest;
import ballinapp.com.ballinapp.adapter.TeamsResultAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsResult extends AppCompatActivity {

    List<Team> teams = new ArrayList<>();
    int receiverId;
    String keyword;
    String accessToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_result);
        getSupportActionBar().hide();
        accessToken = "Bearer " + HomeActivity.accessToken;

        keyword = getIntent().getExtras().getString("keyword");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Team>> call = apiService.findTeamsByCity(keyword, accessToken);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if(response.message().contains("expired")) {
                    refreshToken(HomeActivity.teamId, HomeActivity.refresh);
                } else {
                    teams = response.body();
                    Team[] array = new Team[teams.size()];
                    array = teams.toArray(array);

                    ListAdapter adapter = new TeamsResultAdapter(getApplicationContext(), array);
                    ListView listView = findViewById(R.id.list_view_teams);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            receiverId = teams.get(position).getTeam_id();
                            AlertDialog.Builder alert = new AlertDialog.Builder(TeamsResult.this);
                            alert.setTitle(R.string.teams);
                            alert.setMessage(R.string.choose_action);
                            alert.setNegativeButton(R.string.go_to_profile, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), FoundProfile.class).putExtra("receiver_id", receiverId));
                                }
                            });
                            alert.setPositiveButton(R.string.send_request, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), SendRequest.class).putExtra("receiver_id", receiverId));
                                }
                            });
                            alert.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {

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
