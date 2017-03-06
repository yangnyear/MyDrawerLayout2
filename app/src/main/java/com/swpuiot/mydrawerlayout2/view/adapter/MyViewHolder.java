package com.swpuiot.mydrawerlayout2.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.clicklistener.MyItemClickListener;
import com.swpuiot.mydrawerlayout2.view.clicklistener.MyItemLongClickListener;

/**
 * Created by 羊荣毅_L on 2017/2/17.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener,OnLongClickListener{
    public TextView diaryTitle;
    public TextView datefiled;
    public TextView weekday;
    public TextView weather;
    public TextView diaId;
    private MyItemClickListener mListener;
    private MyItemLongClickListener mLongClickListener;

    public MyViewHolder(View itemView, MyItemClickListener listener, MyItemLongClickListener longClickListener) {
        super(itemView);
        diaId = (TextView) itemView.findViewById(R.id.text_diaryid);
        diaryTitle = (TextView) itemView.findViewById(R.id.text_itemdiarytitlt);
        datefiled = (TextView) itemView.findViewById(R.id.text_itemdate);
        weekday = (TextView) itemView.findViewById(R.id.text_itemweekday);
        weather = (TextView) itemView.findViewById(R.id.text_itemweather);
        this.mListener = listener;
        this.mLongClickListener = longClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getPosition());
        }
    }

    public boolean onLongClick(View arg0) {
        if (mLongClickListener != null) {
            mLongClickListener.onItemLongClick(arg0, getPosition());
        }
        return true;
    }
}
