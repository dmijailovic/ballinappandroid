package ballinapp.com.ballinapp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.data.Game;

public class MyGamesAdapter extends ArrayAdapter<Game> {
    public MyGamesAdapter(@NonNull Context context, Game[] games) {
        super(context, R.layout.list_item_game, games);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_item_game, parent, false);

        Game game = getItem(position);

        TextView state = (TextView) customView.findViewById(R.id.state_game_row);
        TextView city = (TextView) customView.findViewById(R.id.city_game_row);
        TextView address = (TextView) customView.findViewById(R.id.address_game_row);
        TextView date = (TextView) customView.findViewById(R.id.date_game_row);
        TextView time = (TextView) customView.findViewById(R.id.time_game_row);
        TextView message = (TextView) customView.findViewById(R.id.message_game_row);
        TextView contact = (TextView) customView.findViewById(R.id.contact_game_row);

        state.setText(game.getState());
        city.setText(game.getCity());
        address.setText(game.getAddress());
        date.setText(game.getDate());
        time.setText(game.getTime());
        message.setText(game.getMessage());
        contact.setText(game.getContact());

        return customView;
    }
}
