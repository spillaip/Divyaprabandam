package vaishnavism.eclass.ta.divyaprabandam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String DIVYAPRABANDAM = "Divyaprabandam";
    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    // Search ImageButton
    ImageButton ibSearch;

    // Filter ImageButton
    ImageButton ibFilter;

    // Bookmarks ImageButton
    ImageButton ibBookmark;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    // String Array content store
    String[] fillList;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillList = getResources().getStringArray(R.array.prabandam);

        final List<String> findMe = new ArrayList<String>(Arrays.asList(fillList));



        inputSearch = (EditText) findViewById(R.id.et_search);
        ibFilter = (ImageButton) findViewById(R.id.btn_filter);
        ibSearch = (ImageButton) findViewById(R.id.btn_search);
        //ibBookmark = (ImageButton) findViewById(R.id.btn_bookmark);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fillList);
        lv = (ListView) findViewById(R.id.lv_dp);
        lv.setAdapter(adapter);


        //set preferences
        sharedpreferences = getSharedPreferences(DIVYAPRABANDAM, Context.MODE_PRIVATE);

        ibFilter.animate();
        ibFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.getFilter().filter(inputSearch.getText());
                //inputSearch.setText(adapter.getCount());


            }
        });
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                //Iterator<String> iter;
                Iterator<String> iter = findMe.iterator();
                String curItem = "";
                int pos = 0;

                for (pos = 0; iter.hasNext(); pos++) {


                    if (findMe.get(pos).contains(inputSearch.getText())) {
                        Log.d("VC",findMe.get(pos));
                        break;
                    }
                }

                Log.d("VC", "Found row " + pos + " text entered is" + inputSearch.getText());
                lv.smoothScrollToPosition(pos);

            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //View lastTouchedView=null;
                Log.d("VC", "Position Long pressed is " + position);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (sharedpreferences.getInt(String.valueOf(position), -1) > -1) {
                    editor.remove(String.valueOf(position));
                    editor.commit();
                    view.setBackgroundColor(Color.WHITE);
                    Log.d("VC", "Removed bookmark as requested"+position);
                    Toast.makeText(getBaseContext(), "Removed bookmark as requested",
                            Toast.LENGTH_LONG).show();
                } else {
                    editor.putInt(String.valueOf(position), position);
                    view.setBackgroundColor(Color.CYAN);
                    editor.commit();
                    Log.d("VC", "Added bookmark as requested. To view click on the Bookmark Image");
                    Toast.makeText(getBaseContext(), "Added bookmark as requested. To view click on the Bookmark Image",
                            Toast.LENGTH_LONG).show();
                }

                //lastTouchedView = view;
                return false;

            }
        });
/*
        ibBookmark.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, ?> allEntries = sharedpreferences.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                        }
                        adapter.getFilter().filter(DIVYAPRABANDAM);
                    }

                });
                */
    }
}
