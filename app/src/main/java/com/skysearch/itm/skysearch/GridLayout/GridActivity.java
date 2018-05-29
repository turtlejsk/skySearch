package com.skysearch.itm.skysearch.GridLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skysearch.itm.skysearch.ListLayout.Program;
import com.skysearch.itm.skysearch.ListLayout.StickyActivity;
import com.skysearch.itm.skysearch.R;

import java.util.ArrayList;


public class GridActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    int progress;
    Button btn_switch;
    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // action bar 다루기
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        progress = 79;

        // Item 리스트에 아이템 객체 넣기
        ArrayList<Program> items = new ArrayList<Program>();

        items.add(new Program("CH.11 MBC", "나혼자 산다 237회",  R.drawable.ilivealone));
        items.add(new Program("CH.15 JTBC", "효리네 민박2 5회", R.drawable.hyori));
        items.add(new Program("CH.7 KBS", "1박 2일 시즌 3 327회", R.drawable.onetwo));
        items.add(new Program("CH.5 SBS", "미운우리새끼 76회",  R.drawable.child));
        items.add(new Program("CH.61 OCN", "7년의 밤",  R.drawable.nights));
        items.add(new Program("CH.103 SBS Sports", "잉글리시 프리미어...", R.drawable.sports));
        items.add(new Program("CH.20 tvN", "꽃보다 할배", R.drawable.flowers_grand));
        items.add(new Program("CH.159 OGN ", "LOL Champions League...", R.drawable.ogn));

        // setProgressValue(progress);

        // StaggeredGrid 레이아웃을 사용한다
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        Adapter = new GridAdapter(items, progress, mContext);
        recyclerView.setAdapter(Adapter);

        btn_switch = (Button) findViewById(R.id.switch_button);
        btn_switch.setOnClickListener(GridActivity.this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.search_item) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==btn_switch){
            Intent i = new Intent(GridActivity.this, StickyActivity.class);
            startActivity(i);
        }
    }
}