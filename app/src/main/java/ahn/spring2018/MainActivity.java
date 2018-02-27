package ahn.spring2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String[] APP_NAMES = new String[]{
            "Scarne's Dice",
            "Planner",
    };

    ListView listViewApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayAdapter<String> appNameAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, APP_NAMES);

        listViewApps = findViewById(R.id.list_apps);
        listViewApps.setAdapter(appNameAdapter);
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.openActivity(position);
            }
        });
    }

    private void openActivity(int position) {
        Intent launchIntent = null;

        switch (position) {
            case 0:
                launchIntent = new Intent(this, DiceActivity.class);
                break;

            case 1:
                launchIntent = new Intent(this, ScheduleActivity.class);
                break;

            default:
                Toast.makeText(this, "Invalid activity selected: " + position, Toast.LENGTH_SHORT).show();
        }

        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }
}
