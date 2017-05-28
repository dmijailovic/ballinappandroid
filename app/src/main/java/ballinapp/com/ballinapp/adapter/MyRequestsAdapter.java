package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.NewRequest;

public class MyRequestsAdapter extends RecyclerView.Adapter<MyRequestsAdapter.MyRequestsViewHolder>  {
    private List<NewRequest> requests;
    private int rowLayout;
    private Context context;


    public static class MyRequestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout requestsLayout;
        TextView requestFrom;
        TextView requestState;
        TextView requestCity;
        TextView requestDate;
        TextView requestTime;
        TextView requestAddress;
        TextView requestContact;
        TextView requestMessage;
        TextView requestReceivedAt;
        ItemClickListener itemClickListener;

        public MyRequestsViewHolder(View v) {
            super(v);
            requestsLayout = (LinearLayout) v.findViewById(R.id.requests_result_layout);
            requestFrom = (TextView) v.findViewById(R.id.request_from_team_name);
            requestState = (TextView) v.findViewById(R.id.request_from_state);
            requestCity = (TextView) v.findViewById(R.id.request_from_city);
            requestDate = (TextView) v.findViewById(R.id.request_from_date);
            requestTime = (TextView) v.findViewById(R.id.request_from_time);
            requestAddress = (TextView) v.findViewById(R.id.request_from_address);
            requestContact = (TextView) v.findViewById(R.id.request_from_contact);
            requestMessage = (TextView) v.findViewById(R.id.request_from_message);
            requestReceivedAt = (TextView) v.findViewById(R.id.request_from_received_at);
            v.setOnClickListener(this);
        }

        public void setOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

    }

    public MyRequestsAdapter(List<NewRequest> requests, int rowLayout, Context context) {
        this.requests = requests;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MyRequestsAdapter.MyRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRequestsViewHolder holder, final int position) {
        holder.requestFrom.setText(requests.get(position).getOpponentName());
        holder.requestState.setText(requests.get(position).getState());
        holder.requestCity.setText(requests.get(position).getCity());
        holder.requestDate.setText(requests.get(position).getDate());
        holder.requestTime.setText(requests.get(position).getTime());
        holder.requestAddress.setText(requests.get(position).getAddress());
        holder.requestContact.setText(requests.get(position).getContact());
        holder.requestMessage.setText(requests.get(position).getMessage());
        holder.requestReceivedAt.setText(requests.get(position).getSentAt());

        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
