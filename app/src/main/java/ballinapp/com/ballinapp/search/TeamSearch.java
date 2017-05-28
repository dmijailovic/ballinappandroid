package ballinapp.com.ballinapp.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ballinapp.com.ballinapp.R;

public class TeamSearch extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);
        getSupportActionBar().hide();

        radioGroup = (RadioGroup) findViewById(R.id.search_radio_group);
        searchWord = (EditText) findViewById(R.id.team_search_word);

    }

    public String getSearchWord() {
        return searchWord.getText().toString();
    }

    public String getCriteria() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        return radioButton.getText().toString();
    }

    public void search(View view) {
        Intent intent;

        if("name".equals(getCriteria())) {
            intent = new Intent(this, ProfileResult.class);
        } else {
            intent = new Intent(this, TeamsResult.class);
        }

        intent.putExtra("keyword", getSearchWord());
        startActivity(intent);
    }
}
