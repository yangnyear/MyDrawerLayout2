package com.swpuiot.mydrawerlayout2.view.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements MyItemClickListener, MyItemLongClickListener {
    private Toolbar toolBar;
    private DrawerLayout MDrawerLayout;
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
    private TextView setPasswd;


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
                if (isWrite()) {
                    Toast.makeText(MainActivity.this, "今天天写过日记啦，可以选择修改", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
//                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean isWrite() {
        String date = diaryEntityList.get(diaryEntityList.size() - 1).getDiaDate();
        view = inflater.inflate(R.layout.mydatepicker, null);
        datePicker = (DatePicker) view.findViewById(R.id.tatepicker_coisetime);
        String tody = datePicker.getMonth() + 1 + "年" + datePicker.getDayOfMonth() + "月" + datePicker.getYear() + "日";
        if (date.equals(tody)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onItemClick(View view, int postion) {
        DiaryEntity diaryBeen = diaryEntityList.get(postion);
        Toast.makeText(MainActivity.this, "选中第" + diaryBeen.getDiaId() + "个日记", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ReadDiaryActivity.class);
        intent.putExtra("readDiaryBeen", diaryBeen);
        startActivity(intent);


    }

    @Override
    public void onItemLongClick(View view, int postion) {
        dialogView = inflater.inflate(R.layout.longclickitem, null);
        final DiaryEntity diaryBeen = diaryEntityList.get(postion);
        final AlertDialog dialog = new AlertDialog
                .Builder(MainActivity.this)
                .setView(dialogView)
                .show();
        readDiary = (TextView) dialogView.findViewById(R.id.text_readdiary);
        readDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "选中第" + diaryBeen.getDiaId() + "个日记", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ReviseDiaryActivity.class);
                intent.putExtra("DiaryBeen", diaryBeen);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        delDiary = (TextView) dialogView.findViewById(R.id.text_delatdiary);
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
                                Toast.makeText(MainActivity.this,"删除成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                warmDialog.show();
            }
        });
        setPasswd = (TextView) dialogView.findViewById(R.id.text_addpasswd);
        setPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/19 加密
            }
        });
    }

}