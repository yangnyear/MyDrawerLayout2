package com.swpuiot.mydrawerlayout2.view.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.data.DataBaseFunction;
import com.swpuiot.mydrawerlayout2.view.clicklistener.MyItemClickListener;
import com.swpuiot.mydrawerlayout2.view.clicklistener.MyItemLongClickListener;
import com.swpuiot.mydrawerlayout2.view.adapter.RecyclerViewAdapter;
import com.swpuiot.mydrawerlayout2.view.entities.DiaryEntity;
import com.swpuiot.mydrawerlayout2.view.entities.DiaryIInformationEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyItemClickListener, MyItemLongClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolBar;
    private DrawerLayout MDrawerLayout;
    private NavigationView navigationView;
    private RecyclerView mRecycleView;
    private List<DiaryEntity> diaryEntityList;
    private List<DiaryIInformationEntity> diaryIInformationEntityList;
    private DataBaseFunction mDataBase;
    private DatePicker datePicker;
    private View view;
    private View dialogView;
    private LayoutInflater inflater;
    private TextView readDiary;
    private TextView delDiary;
    private DiaryEntity diaryEntitybeen;
    //    private TextView setPasswd;
    private String date, tody;
    private SharedPreferences sharedPreferences;
    private int idLooper = 0;
    private RadioGroup radioGroup;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化
        initializete();
        //配置RecyclerView的Adapter
        if (diaryEntityList != null) {
//        //设置布局的排版方向
//        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            adapter = new RecyclerViewAdapter(this, diaryEntityList);
            mRecycleView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
        } else
            Toast.makeText(this, "快开始写日记吧", Toast.LENGTH_SHORT).show();

    }

    private void initializete() {
        toolBar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolBar.setNavigationIcon(R.drawable.ic_abc_image_todrawlayout);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_menu_selectall_mtrl_alpha);

        MDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_diary);
        layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecycleView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);


        //初始化inflater
        inflater = LayoutInflater.from(MainActivity.this);
        //初始化NavigationView 并注册监听
        navigationView = (NavigationView) findViewById(R.id.navigation_main);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        view = inflater.inflate(R.layout.mydatepicker, null);
        datePicker = (DatePicker) view.findViewById(R.id.tatepicker_coisetime);

        sharedPreferences = getSharedPreferences("diaID", MODE_PRIVATE);



        diaryEntityList = new ArrayList<DiaryEntity>();
        diaryIInformationEntityList = new ArrayList<DiaryIInformationEntity>();
        mDataBase = new DataBaseFunction(this);
        mDataBase.selectDiary(diaryEntityList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                if (!isWrite()) {
                    Toast.makeText(MainActivity.this, "今天天写过日记啦，可以选择修改", Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(intent, 1);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    //判断今天是否写过日记
    public boolean isWrite() {
        if (diaryEntityList.size() < 1) {
            return true;
        } else {
            date = diaryEntityList.get(diaryEntityList.size() - 1).getDiaDate();
            tody = datePicker.getYear() + "年" + (datePicker.getMonth() + 1) + "月" + datePicker.getDayOfMonth() + "日";
        }
        if (date.equals(tody)) {
            return false;
        } else {
            return true;
        }
    }

    //点击某个日记进行查看
    @Override
    public void onItemClick(View view, int postion) {
        DiaryEntity diaryBeen = diaryEntityList.get(postion);
        Intent intent = new Intent(MainActivity.this, ReadDiaryActivity.class);
        intent.putExtra("readDiaryBeen", diaryBeen);
        startActivity(intent);
    }

    //长按弹出dialog
    @Override
    public void onItemLongClick(View view, int postion) {
        //首先给出编辑,删除选项
        dialogView = inflater.inflate(R.layout.longclickitem, null);
        final DiaryEntity diaryBeen = diaryEntityList.get(postion);
        final AlertDialog dialog = new AlertDialog
                .Builder(MainActivity.this)
                .setView(dialogView)
                .show();

        readDiary = (TextView) dialogView.findViewById(R.id.text_readdiary);
        //选择编辑时跳转到编辑界面
        readDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReviseDiaryActivity.class);
                intent.putExtra("DiaryBeen", diaryBeen);
                startActivityForResult(intent, 1);
                dialog.dismiss();
            }
        });

        delDiary = (TextView) dialogView.findViewById(R.id.text_delatdiary);
        //选择删除时弹出提示框
        delDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog.Builder warmDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提醒")
                        .setMessage("真的要删除此日记吗?")
                        .setCancelable(false)
                        .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDataBase.deletDiary(diaryBeen.getDiaId());
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                //删除成功后更新界面
                                diaryEntityList.clear();
                                mDataBase.selectDiary(diaryEntityList);
                                notifyDataSetChanged();
                            }
                        })
                                //取消,不进行删除
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                warmDialog.show();
            }
        });

    }

    //当界面返回时,刷新界面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                diaryEntityList.clear();
                mDataBase.selectDiary(diaryEntityList);
                notifyDataSetChanged();
                break;
        }
    }

    //navigationView点击事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        idLooper = sharedPreferences.getInt("idLooper", idLooper);
        final AlertDialog.Builder dialog;
        switch (item.getItemId()) {
            case R.id.item_dataconnecter:
                MDrawerLayout.closeDrawers();
               dialog = (AlertDialog.Builder) new AlertDialog
                        .Builder(MainActivity.this)
                        .setTitle("数据统计")
                        .setMessage("您现在有" + diaryEntityList.size() + "篇日记\n"
                                + "您总共写过" + idLooper + "篇日记\n"
                                + "您删掉了" + (idLooper - diaryEntityList.size()) + "篇日记");
                dialog.show();
                break;
            case R.id.item_todayoflastmouth:
                int year = datePicker.getYear();
                int mouth = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                int maxdayoflastmouth= mDataBase.maxDayOfMouth(year, mouth);
                if (day>maxdayoflastmouth){
                    dialog = (AlertDialog.Builder) new AlertDialog
                            .Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("上月没有"+day+"号");
                    dialog.show();
                }else {
                    int[] date = {year, mouth, day};
                    intent = new Intent(MainActivity.this, ReadDiaryActivity.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
                MDrawerLayout.closeDrawers();
                break;
            case R.id.item_settimetonotificate:
                // TODO: 2017/3/6 startservice
                break;
            case R.id.item_setting:
                 intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/yangnyear/MyDrawerLayout2/tree/master/app");
                intent.setData(content_url);
                startActivity(intent);

                MDrawerLayout.closeDrawers();
                break;
            case R.id.item_aboutapp:
                MDrawerLayout.closeDrawers();
                intent = new Intent(MainActivity.this, AboutAPPActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
