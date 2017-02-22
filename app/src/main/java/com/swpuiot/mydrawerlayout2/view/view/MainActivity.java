package com.swpuiot.mydrawerlayout2.view.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.controler.DataBaseFunction;
import com.swpuiot.mydrawerlayout2.view.controler.MyItemClickListener;
import com.swpuiot.mydrawerlayout2.view.controler.MyItemLongClickListener;
import com.swpuiot.mydrawerlayout2.view.controler.RecyclerViewAdapter;
import com.swpuiot.mydrawerlayout2.view.model.DiaryEntity;
import com.swpuiot.mydrawerlayout2.view.model.DiaryIInformationEntity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化
        initializete();
        //配置RecyclerView的Adapter
        if (diaryIInformationEntityList != null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, diaryIInformationEntityList);
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.winner_icon);
        MDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_diary);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
        idLooper = sharedPreferences.getInt("idLooper", idLooper);


        diaryEntityList = new ArrayList<DiaryEntity>();
        diaryIInformationEntityList = new ArrayList<DiaryIInformationEntity>();
        mDataBase = new DataBaseFunction(this);
        mDataBase.selectDiary(diaryEntityList);
        for (int i = 0; i < diaryEntityList.size(); i++) {
            diaryIInformationEntityList.add(new DiaryIInformationEntity(diaryEntityList.get(i).getDiaTitle(),
                    diaryEntityList.get(i).getDiaDate(),
                    diaryEntityList.get(i).getDiaWenkday(),
                    diaryEntityList.get(i).getDiaWeather(),
                    diaryEntityList.get(i).getDiaId()));
        }
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
                                mRecycleView.removeAllViews();
                                diaryIInformationEntityList.clear();
                                mDataBase.selectDiary(diaryEntityList);
                                for (int i = 0; i < diaryEntityList.size(); i++) {
                                    diaryIInformationEntityList.add(new DiaryIInformationEntity(diaryEntityList.get(i).getDiaTitle(),
                                            diaryEntityList.get(i).getDiaDate(),
                                            diaryEntityList.get(i).getDiaWenkday(),
                                            diaryEntityList.get(i).getDiaWeather(),
                                            diaryEntityList.get(i).getDiaId()));
                                }
                                if (diaryIInformationEntityList != null) {
                                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, diaryIInformationEntityList);
                                    mRecycleView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(MainActivity.this);
                                    adapter.setOnItemLongClickListener(MainActivity.this);
                                }
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
//        setPasswd = (TextView) dialogView.findViewById(R.id.text_addpasswd);
//        setPasswd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "暂时还不能加密，等待更新", Toast.LENGTH_SHORT).show();
//            }

    }

    //当界面返回时,刷新界面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                diaryEntityList.clear();
                mRecycleView.removeAllViews();
                diaryIInformationEntityList.clear();
                mDataBase.selectDiary(diaryEntityList);
                for (int i = 0; i < diaryEntityList.size(); i++) {
                    diaryIInformationEntityList.add(new DiaryIInformationEntity(diaryEntityList.get(i).getDiaTitle(),
                            diaryEntityList.get(i).getDiaDate(),
                            diaryEntityList.get(i).getDiaWenkday(),
                            diaryEntityList.get(i).getDiaWeather(),
                            diaryEntityList.get(i).getDiaId()));
                }
                if (diaryIInformationEntityList != null) {
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, diaryIInformationEntityList);
                    mRecycleView.setAdapter(adapter);
                    adapter.setOnItemClickListener(this);
                    adapter.setOnItemLongClickListener(this);
                } else
                    Toast.makeText(this, "快开始写日记吧", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //navigationView点击事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_dataconnecter:
                MDrawerLayout.closeDrawers();
                AlertDialog.Builder dialog = (AlertDialog.Builder) new AlertDialog
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
                int[] date = {year, mouth, day};
                Intent intent = new Intent(MainActivity.this, ReadDiaryActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
                MDrawerLayout.closeDrawers();

                break;
            case R.id.item_settimetonotificate:

                break;
            case R.id.item_:

                break;
            case R.id.item_setting:

                break;
            case R.id.item_aboutapp:

                break;
        }

        return true;
    }
}
