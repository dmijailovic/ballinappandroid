package ballinapp.com.ballinapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ballinapp.com.ballinapp.R;
import ballinapp.com.ballinapp.team.RegisterTeam;

public class MiddleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        getSupportActionBar().hide();
    }

    public void continueFlow(View view) {
        startActivity(new Intent(this, RegisterTeam.class));
    }

}
