package ahn.spring2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleFragment extends Fragment {
    ArrayList<String> days;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday"));

        listView = rootView.findViewById(R.id.schedule_list);
        arrayAdapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.list_item, R.id.list_item,
                days);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element = (String) listView.getItemAtPosition(position);

                Intent intent = new Intent(rootView.getContext(), EventsActivity.class);
                intent.putExtra("day", element);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
