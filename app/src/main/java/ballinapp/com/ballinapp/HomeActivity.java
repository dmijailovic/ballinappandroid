package ballinapp.com.ballinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ballinapp.com.ballinapp.public_games.CreateGame;
import ballinapp.com.ballinapp.requests.Requests;
import ballinapp.com.ballinapp.public_games.GameSearch;
import ballinapp.com.ballinapp.search.TeamSearch;
import ballinapp.com.ballinapp.team.MyProfile;

public class HomeActivity extends AppCompatActivity {

    public static Long teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        if(teamId == null) {
            teamId = getIntent().getExtras().getLong("id");
        }

     }

     public void profile(View view) {
         startActivity(new Intent(this, MyProfile.class));
     }

     public void findTeams(View view) {
        startActivity(new Intent(this, TeamSearch.class));
     }

     public void findGames(View view) {
        startActivity(new Intent(this, GameSearch.class));
     }

     public void requests(View view) {
         startActivity(new Intent(this, Requests.class));
     }

     public void createGame(View view) {
         startActivity(new Intent(this, CreateGame.class));
     }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
