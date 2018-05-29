package com.skysearch.itm.skysearch.ListLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import com.skysearch.itm.skysearch.DB.DBHandler_schd;
import com.skysearch.itm.skysearch.GridLayout.GridActivity;
import com.skysearch.itm.skysearch.R;
import com.skysearch.itm.skysearch.Server.NetworkThread;
import com.skysearch.itm.skysearch.Server.ServerService;

public class StickyActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button btn_switch;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        //Intent intent = new Intent(getApplicationContext(), ServerService.class); // 이동할 컴포넌트
        //startService(intent);

        // DrawerToggle 보이게 하기
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

        // Fragment
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        btn_switch = (Button) findViewById(R.id.switch_button2);
        btn_switch.setOnClickListener(StickyActivity.this);

        String url = "http://211.211.54.158:3000/schd";
        NetworkThread nt2 = new NetworkThread(url, null, "schd", getApplicationContext());
        Log.i("NetworkThread::schd","create");
        nt2.mainProcessing();
        Log.i("NetworkThread::schd","run");
        url = "http://211.211.54.158:3000/prog";
        // AsyncTask를 통해 HttpURLConnection 수행.
        //NetworkTask networkTask = new NetworkTask(url, null, "prog");
        //networkTask.execute();
        NetworkThread nt1 = new NetworkThread(url,null,"prog", getApplicationContext());
        Log.i("NetworkThread::prog","create");
        nt1.mainProcessing();
        Log.i("NetworkThread::prog","run");

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
            Intent i = new Intent(StickyActivity.this, GridActivity.class);
            startActivity(i);
        }
    }
}
