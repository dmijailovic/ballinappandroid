package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ballinapp.com.ballinapp.HomeActivity;
import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.api.ApiClient;
import ballinapp.com.ballinapp.api.ApiInterface;
import ballinapp.com.ballinapp.data.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>  {
    private List<Player> players;
    private int rowLayout;
    private Context context;


    public static class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout playersLayout;
        TextView playerNickname;
        TextView playerBirthyear;
        TextView playerContact;
        ItemClickListener itemClickListener;

        public PlayerViewHolder(View v) {
            super(v);
            playersLayout = (RelativeLayout) v.findViewById(R.id.players_layout);
            playerNickname = (TextView) v.findViewById(R.id.player_nickname);
            playerBirthyear = (TextView) v.findViewById(R.id.player_birthyear);
            playerContact = (TextView) v.findViewById(R.id.player_contact);
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

    public PlayerAdapter(List<Player> players, int rowLayout, Context context) {
        this.players = players;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PlayerAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, final int position) {
        holder.playerNickname.setText(players.get(position).getNickname());
        holder.playerBirthyear.setText(String.valueOf(players.get(position).getBirthyear()));
        holder.playerContact.setText(players.get(position).getContact());
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                deletePlayer(players.get(pos).getId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return players.size();
    }

    private void deletePlayer(int playerId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.deletePlayer(HomeActivity.teamId, playerId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, R.string.player_deleted, Toast.LENGTH_SHORT).show();
                swap(players);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void swap(List<Player> datas){
        players.clear();
        players.addAll(datas);
        notifyDataSetChanged();
    }

}
