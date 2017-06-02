package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.NewRequest;

public class SentRequestsAdapter extends ArrayAdapter<NewRequest> {
    public SentRequestsAdapter(@NonNull Context context, NewRequest[] requests) {
        super(context, R.layout.requests_result_layout, requests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.requests_result_layout, parent, false);

        NewRequest request = getItem(position);

        TextView requestFrom = (TextView) customView.findViewById(R.id.request_from_team_name);
        TextView requestState = (TextView) customView.findViewById(R.id.request_from_state);
        TextView requestCity = (TextView) customView.findViewById(R.id.request_from_city);
        TextView requestDate = (TextView) customView.findViewById(R.id.request_from_date);
        TextView requestTime = (TextView) customView.findViewById(R.id.request_from_time);
        TextView requestAddress = (TextView) customView.findViewById(R.id.request_from_address);
        TextView requestContact = (TextView) customView.findViewById(R.id.request_from_contact);
        TextView requestMessage = (TextView) customView.findViewById(R.id.request_from_message);
        TextView requestReceivedAt = (TextView) customView.findViewById(R.id.request_from_received_at);
        TextView requestResponseTv = (TextView) customView.findViewById(R.id.request_response_tv);
        TextView requestFromTv = (TextView) customView.findViewById(R.id.request_from_tv);
        TextView sentAtTv = (TextView) customView.findViewById(R.id.request_from_received_at_tv);

        requestFrom.setText(request.getOpponentName());
        requestState.setText(request.getState());
        requestCity.setText(request.getCity());
        requestDate.setText(request.getDate());
        requestTime.setText(request.getTime());
        requestAddress.setText(request.getAddress());
        requestContact.setText(request.getContact());
        requestMessage.setText(request.getMessage());
        requestReceivedAt.setText(request.getSentAt());
        requestFromTv.setText(R.string.to);
        sentAtTv.setText(R.string.sent_at);

        if(request.isStatus()) {
            requestResponseTv.setText(R.string.toast_accept);
        } else {
            requestResponseTv.setText(R.string.toast_reject);
        }

        return customView;
    }
}
