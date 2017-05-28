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
import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
        private List<Game> games;
        private int rowLayout;
        private Context context;

        public static class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            RelativeLayout gamesLayout;
            TextView state;
            TextView city;
            TextView address;
            TextView message;
            TextView contact;
            TextView date;
            TextView time;
            ItemClickListener itemClickListener;

            public GameViewHolder(View v) {
                super(v);
                gamesLayout = (RelativeLayout) v.findViewById(R.id.games_layout);
                state = (TextView) v.findViewById(R.id.state_game_row);
                city = (TextView) v.findViewById(R.id.city_game_row);
                address = (TextView) v.findViewById(R.id.address_game_row);
                message = (TextView) v.findViewById(R.id.message_game_row);
                contact = (TextView) v.findViewById(R.id.contact_game_row);
                date = (TextView) v.findViewById(R.id.date_game_row);
                time = (TextView) v.findViewById(R.id.time_game_row);
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

        public GameAdapter(List<Game> games, int rowLayout, Context context) {
            this.games = games;
            this.rowLayout = rowLayout;
            this.context = context;
        }

        @Override
        public GameAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
            return new GameViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GameViewHolder holder, final int position) {
            holder.state.setText(games.get(position).getState());
            holder.city.setText(games.get(position).getCity());
            holder.address.setText(games.get(position).getAddress());
            holder.message.setText(games.get(position).getMessage());
            holder.contact.setText(games.get(position).getContact());
            holder.date.setText(games.get(position).getDate());
            holder.time.setText(games.get(position).getTime());
            holder.setOnClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    joinGame(games.get(pos).getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return games.size();
        }

        private void joinGame(int gameId) {
            Team team = new Team();
            team.setTeam_id(HomeActivity.teamId);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Void> call = apiService.joinGame(gameId, team);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context, R.string.joined_game, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

