package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.SendRequest;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Request;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.search.ProfileResult;
import ballinapp.com.ballinapp.team.MyProfile;
import retrofit2.Call;

public class TeamsResultAdapter extends RecyclerView.Adapter<TeamsResultAdapter.TeamsResultViewHolder>  {
    private List<Team> teams;
    private int rowLayout;
    private Context context;


    public static class TeamsResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout teamsLayout;
        TextView teamName;
        TextView teamState;
        TextView teamCity;
        TextView open;
        TextView appPlus;
        TextView appMinus;
        ItemClickListener itemClickListener;

        public TeamsResultViewHolder(View v) {
            super(v);
            teamsLayout = (RelativeLayout) v.findViewById(R.id.teams_result_layout);
            teamName = (TextView) v.findViewById(R.id.team_name_result_layout);
            teamState = (TextView) v.findViewById(R.id.team_state_result_layout);
            teamCity = (TextView) v.findViewById(R.id.team_city_result_layout);
            open = (TextView) v.findViewById(R.id.team_open_result_layout);
            appPlus = (TextView) v.findViewById(R.id.team_app_plus_result_layout);
            appMinus = (TextView) v.findViewById(R.id.team_app_minus_result_layout);
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

    public TeamsResultAdapter(List<Team> teams, int rowLayout, Context context) {
        this.teams = teams;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TeamsResultAdapter.TeamsResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TeamsResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamsResultViewHolder holder, final int position) {
        holder.teamName.setText(teams.get(position).getName());
        holder.teamState.setText(teams.get(position).getState());
        holder.teamCity.setText(teams.get(position).getCity());
        holder.appPlus.setText(String.valueOf(teams.get(position).getAppearance_plus()));
        holder.appMinus.setText(String.valueOf(teams.get(position).getAppearance_minus()));

        if(teams.get(position).isOpen()) {
            holder.open.setText(R.string.opened);
        } else {
            holder.open.setText(R.string.closed);
        }

        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                goToRequest(teams.get(pos).getTeam_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    private void goToRequest(Long id) {
        Toast.makeText(context, R.string.tap_to_request, Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, SendRequest.class).putExtra("receiver_id", id));
    }

}
