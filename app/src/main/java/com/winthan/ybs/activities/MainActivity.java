package com.winthan.ybs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.winthan.ybs.R;
import com.winthan.ybs.adapters.ViewPagerAdapter;
import com.winthan.ybs.data.Bus;
import com.winthan.ybs.fragments.BusFragment;
import com.winthan.ybs.fragments.BusRouteFragment;
import com.winthan.ybs.utils.ItemClickListener;
import com.winthan.ybs.utils.MMFontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this,this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        viewPager.setOffscreenPageLimit(2);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        MMFontUtils.applyMMFontToTabLayout(tabLayout);

    }

    public void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new BusFragment(),"ကားလိုင္းမ်ား");
        mAdapter.addFragment(new BusRouteFragment(),"လမ္းေၾကာင္းမ်ား");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            new MaterialDialog.Builder(this)
                    .title("About Me")
                    .content("Logo Credit : www.flaticon.com\nImage Credit : yangonbus.com\nDeveloped By One Thing :)")
                    .positiveText("ok")
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTapBus(Bus bus, int position) {

        String[] busStop = bus.getBusStop().split(",");
        new MaterialDialog.Builder(this)
                .title("Bus No : "+bus.getBusNo()+"")
                .items(busStop)
                .positiveText("OK")
                .show();

    }

}
