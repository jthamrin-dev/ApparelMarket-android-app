package com.example.apparelmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apparelmarket.models.ApparelItem;
import com.example.apparelmarket.models.ApparelProvider;
import com.example.apparelmarket.models.ItemAdapter;
import com.example.apparelmarket.models.SearchClass;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    // Pulling together ItemAdapter and initialising the custom Layout XML with all the books.
    public static final String ITEM_DETAIL_KEY = "item";
    // From activity_list.xml
    RecyclerView lvItems;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Sets up a grid layout for displaying the items.
        GridLayoutManager gm = new GridLayoutManager(this,2);
        lvItems = (RecyclerView) findViewById(R.id.lvItems);

        // Intent passes a query for generating an ArrayList of ApparelItems for display.
        Intent thisIntent = getIntent();
        String query =  thisIntent.getStringExtra(MainActivity.ITEM_DETAIL_KEY);
        final ArrayList<ApparelItem> queriedItems = SearchClass.searchFunction(query, ApparelProvider.dataArray);

        // Sets the adapter for the ListView for items in specified category.
        itemAdapter = new ItemAdapter(queriedItems, ListActivity.this);

        lvItems.setLayoutManager(gm);
        lvItems.setAdapter(itemAdapter);

        // Item can be clicked only if results are shown.
        if (!(queriedItems.get(0).getId() == "null")) {
            itemAdapter.setOnItemClickListner(new ItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                    intent.putExtra(ITEM_DETAIL_KEY, queriedItems.get(position).getId());
                    startActivity(intent);
                    // Overrides the default animation when transitioning between activities.
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
    }

    // Changes default animation to a custom one.
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}