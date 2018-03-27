package ballinapp.com.ballinapp.public_games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ballinapp.com.ballinapp.R;

public class GameSearch extends AppCompatActivity {

    EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search);
        getSupportActionBar().hide();

        city = findViewById(R.id.enter_city_et);
    }

    public void search(View view) {
        String keyword = city.getText().toString();
        startActivity(new Intent(this, GamesResult.class).putExtra("keyword", keyword));
    }
}
