package com.example.demochatsms16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demochatsms16.Interface.EventCheckBox;
import com.example.demochatsms16.Interface.EventFloatingButon;
import com.example.demochatsms16.R;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMainSMG extends  RecyclerView.Adapter<AdapterMainSMG.ViewHolder>{
    private List<ObDataMess> listmsg;

    private EventFloatingButon iClickFloatingButon;

    private EventCheckBox iCheckBox;
    private Context context;

    public AdapterMainSMG(List<ObDataMess> listmsg,
                          Context context) {
        this.listmsg = listmsg;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ObDataMess obDataMess = listmsg.get(position);

        holder.tvNumber.setText(obDataMess.getNumber());
        holder.tvTime.setText(obDataMess.getTime());
        holder.tvSMG.setText(obDataMess.getSmg());
        holder.itemViewChat.setOnClickListener((new View.OnClickListener() {
            public final void onClick(View view) {
                iClickFloatingButon.onClickFloatingButonNewSMS(obDataMess.getNumber());
            }
        }));

        holder.itemViewChat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.checkBox.isChecked() == true){
                    holder.img.setImageResource(R.drawable.ic_circle_people);
                    // holder.checkBox.isChecked() = false;
                    iCheckBox.resultNumberSMS(obDataMess.getNumber(),false);

                }else{
                    holder.img.setImageResource(R.drawable.delete);
                    //holder.checkBox.isChecked = true
                    iCheckBox.resultNumberSMS(obDataMess.getNumber(),true);

                }
                return true;
            }
        });



    }

    public EventFloatingButon getiClickFloatingButon() {
        return iClickFloatingButon;
    }

    public void setiClickFloatingButon(EventFloatingButon iClickFloatingButon) {
        this.iClickFloatingButon = iClickFloatingButon;
    }

    public EventCheckBox getiCheckBox() {
        return iCheckBox;
    }

    public void setiCheckBox(EventCheckBox iCheckBox) {
        this.iCheckBox = iCheckBox;
    }

    @Override
    public int getItemCount() {
        return listmsg.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvSMG;
        TextView tvTime;
        ImageView img;
        CheckBox checkBox;
        LinearLayout itemViewChat;

        public ViewHolder(@NotNull View itemView) {

            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            tvSMG = (TextView) itemView.findViewById(R.id.tvMg);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            img = (ImageView) itemView.findViewById(R.id.img);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbItem);
            itemViewChat = (LinearLayout) itemView.findViewById(R.id.itemView);

        }
    }
}
