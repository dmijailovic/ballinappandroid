package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.Player;

public class PlayerResultAdapter extends RecyclerView.Adapter<PlayerResultAdapter.PlayerResultViewHolder>  {
    private List<Player> players;
    private int rowLayout;
    private Context context;


    public static class PlayerResultViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout playersLayout;
        TextView playerNickname;
        TextView playerBirthyear;
        TextView playerContact;

        public PlayerResultViewHolder(View v) {
            super(v);
            playersLayout = (RelativeLayout) v.findViewById(R.id.players_layout);
            playerNickname = (TextView) v.findViewById(R.id.player_nickname);
            playerBirthyear = (TextView) v.findViewById(R.id.player_birthyear);
            playerContact = (TextView) v.findViewById(R.id.player_contact);
        }

    }

    public PlayerResultAdapter(List<Player> players, int rowLayout, Context context) {
        this.players = players;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PlayerResultAdapter.PlayerResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PlayerResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerResultViewHolder holder, final int position) {
        holder.playerNickname.setText(players.get(position).getNickname());
        holder.playerBirthyear.setText(String.valueOf(players.get(position).getBirthyear()));
        holder.playerContact.setText(players.get(position).getContact());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

}
