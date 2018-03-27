package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.Player;


public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdapter(@NonNull Context context, Player[] players) {
        super(context, R.layout.players_layout, players);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.players_layout, parent, false);

        Player player = getItem(position);

        TextView playerNickname = (TextView) customView.findViewById(R.id.player_nickname);
        TextView playerBirthyear = (TextView) customView.findViewById(R.id.player_birthyear);
        TextView playerContact = (TextView) customView.findViewById(R.id.player_contact);
        ImageView profilePicture = (ImageView) customView.findViewById(R.id.player_profile_picture);

        playerNickname.setText(player.getNickname());
        playerBirthyear.setText(String.valueOf(player.getBirthyear()));
        profilePicture.setImageResource(R.drawable.player_profile);

        if(player.getContact() == null) {
            playerContact.setText("");
        } else {
            playerContact.setText(player.getContact());
        }

        return customView;
    }
}
