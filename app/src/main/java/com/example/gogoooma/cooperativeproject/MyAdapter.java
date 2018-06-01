package com.example.gogoooma.cooperativeproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<Place> list;
    Place nowItem;

    //Item Click Interface를 만들어 onClick 함수를 Main에서 쓸 수 있게 함
    public ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    // ViewHolder로 위젯을 재사용하기 위해 class 생성
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        TextView cardPlace;
        TextView cardTime;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.cardName = (TextView) itemView.findViewById(R.id.cardName);
            this.cardPlace = (TextView) itemView.findViewById(R.id.cardPlace);
            this.cardTime = (TextView) itemView.findViewById(R.id.cardTime);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    public MyAdapter(Context context, List<Place> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        nowItem = list.get(position);
        holder.cardName.setText("장소 : "+nowItem.getPlace());
        holder.cardPlace.setText("관리자 : " + nowItem.getAdmin().getPhoneNum());
        String str = "일시 : " + nowItem.getDay() + "  " + nowItem.getStartHour()
                +":"+String.format("%02d",nowItem.getStartMin())+
                " - " + nowItem.getEndHour() + ":"+String.format("%02d",nowItem.getEndMin());
        holder.cardTime.setText(str);

        // position 정보를 사용하기 위해 interface에 position 정보를 넘겨줌
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null)
                    itemClick.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}