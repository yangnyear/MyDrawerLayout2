package com.swpuiot.mydrawerlayout2.view.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.swpuiot.mydrawerlayout2.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        inite();
    }
   public void inite(){
       setSupportActionBar((Toolbar) findViewById(R.id.toolbar_setting));
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
