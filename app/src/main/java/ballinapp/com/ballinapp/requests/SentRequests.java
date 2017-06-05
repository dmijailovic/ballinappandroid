package ballinapp.com.ballinapp.requests;

import android.content.DialogInterface;
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

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.adapter.SentRequestsAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.NewRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentRequests extends AppCompatActivity {
    List<NewRequest> sentRequests = new ArrayList<>();
    int requestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_requests);
        getSupportActionBar().hide();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NewRequest>> call = apiService.getSentRequests(HomeActivity.teamId, HomeActivity.token, HomeActivity.teamId);
        call.enqueue(new Callback<List<NewRequest>>() {
            @Override
            public void onResponse(Call<List<NewRequest>> call, Response<List<NewRequest>> response) {
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

            @Override
            public void onFailure(Call<List<NewRequest>> call, Throwable t) {

            }
        });
    }

    private void cancelRequest(int requestId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.deleteRequest(requestId, HomeActivity.token, HomeActivity.teamId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), R.string.request_deleted, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
