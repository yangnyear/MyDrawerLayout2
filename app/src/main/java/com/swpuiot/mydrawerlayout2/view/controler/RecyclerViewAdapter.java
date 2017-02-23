package com.swpuiot.mydrawerlayout2.view.controler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.model.DiaryEntity;
import com.swpuiot.mydrawerlayout2.view.model.MyViewHolder;

import java.util.List;

/**
 * Created by 羊荣毅_L on 2017/1/14.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    private List<DiaryEntity> diaryIInformationEntityList;

    public RecyclerViewAdapter(Context context,List<DiaryEntity> diaryIInformationEntityList) {
        this.context = context;
        this.diaryIInformationEntityList = diaryIInformationEntityList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=layoutInflater.inflate(R.layout.diary_item,parent,false);
        MyViewHolder holder = new MyViewHolder( itemview, mItemClickListener, mItemLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.diaryTitle.setText(diaryIInformationEntityList.get(position).getDiaTitle());
        holder.datefiled.setText(diaryIInformationEntityList.get(position).getDiaDate());
        holder.weekday.setText(diaryIInformationEntityList.get(position).getDiaWenkday());
        holder.weather.setText(diaryIInformationEntityList.get(position).getDiaWeather());
        holder.diaId.setText(diaryIInformationEntityList.get(position).getDiaId()+"");

    }

    @Override
    public int getItemCount() {
        return diaryIInformationEntityList.size();
    }


    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }
}
