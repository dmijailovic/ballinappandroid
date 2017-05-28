package ballinapp.com.ballinapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ballinapp.com.ballinapp.adapter.MyRequestsAdapter;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.NewRequest;
import ballinapp.com.ballinapp.data.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        getSupportActionBar().hide();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NewRequest>> call = apiService.getRequests(HomeActivity.teamId);
        call.enqueue(new Callback<List<NewRequest>>() {
            @Override
            public void onResponse(Call<List<NewRequest>> call, Response<List<NewRequest>> response) {
                List<NewRequest> newRequests = response.body();
                recyclerView.setAdapter(new MyRequestsAdapter(newRequests, R.layout.requests_result_layout, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<NewRequest>> call, Throwable t) {

            }
        });
    }
}
