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

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.requests.SendRequest;
import ballinapp.com.ballinapp.adapter.TeamsResultAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileResult extends AppCompatActivity {

    List<Team> teams = new ArrayList<>();
    Long receiverId;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_result);
        getSupportActionBar().hide();

        keyword = getIntent().getExtras().getString("keyword");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Team>> call = apiService.findTeamByName(keyword, HomeActivity.token, HomeActivity.teamId);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                teams = response.body();
                Team[] array = new Team[teams.size()];
                array = teams.toArray(array);

                ListAdapter adapter = new TeamsResultAdapter(getApplicationContext(), array);
                ListView listView = (ListView) findViewById(R.id.profile_result_list_view);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        receiverId = teams.get(position).getTeam_id();
                        AlertDialog.Builder alert = new AlertDialog.Builder(ProfileResult.this);
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

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {

            }
        });

    }
}
