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
import ballinapp.com.ballinapp.data.Team;

public class TeamsResultAdapter extends ArrayAdapter<Team> {
    public TeamsResultAdapter(@NonNull Context context, Team[] teams) {
        super(context, R.layout.teams_result_layout, teams);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.teams_result_layout, parent, false);

        Team team = getItem(position);

        TextView teamName = (TextView) customView.findViewById(R.id.team_name_result_layout);
        TextView teamState = (TextView) customView.findViewById(R.id.team_state_result_layout);
        TextView teamCity = (TextView) customView.findViewById(R.id.team_city_result_layout);
        TextView open = (TextView) customView.findViewById(R.id.team_open_result_layout);
        TextView appPlus = (TextView) customView.findViewById(R.id.team_app_plus_result_layout);
        TextView appMinus = (TextView) customView.findViewById(R.id.team_app_minus_result_layout);

        teamName.setText(team.getName());
        teamState.setText(team.getState());
        teamCity.setText(team.getCity());
        appPlus.setText(String.valueOf(team.getAppearancePlus()));
        appMinus.setText(String.valueOf(team.getAppearanceMinus()));

        if(team.isOpen()) {
            open.setText(R.string.open);
        } else {
            open.setText(R.string.close);
        }

        return customView;
    }
}
