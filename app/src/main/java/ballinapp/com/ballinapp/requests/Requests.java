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
import ballinapp.com.ballinapp.adapter.MyRequestsAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.NewRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requests extends AppCompatActivity {

    List<NewRequest> newRequests = new ArrayList<>();
    int requestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        getSupportActionBar().hide();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NewRequest>> call = apiService.getRequests(HomeActivity.teamId);
        call.enqueue(new Callback<List<NewRequest>>() {
            @Override
            public void onResponse(Call<List<NewRequest>> call, Response<List<NewRequest>> response) {
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
                        alert.show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<NewRequest>> call, Throwable t) {

            }
        });
    }

    private void requestResponse(int requestId, boolean response, final String toast) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.requestResponse(requestId, response);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
