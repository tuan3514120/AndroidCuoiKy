package com.example.covi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KBYTAdapter extends RecyclerView.Adapter<KBYTAdapter.KBYTViewHolder>{
    private List<KBYT>mListKBYT;
    private Click click;

    public interface Click{
        void updateKbyt(KBYT kbyt);
        void deleteKbyt(KBYT kbyt);
    }

    public KBYTAdapter(Click click) {
        this.click = click;
    }

    public void setData(List<KBYT>list){
        this.mListKBYT = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KBYTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_kbyt,parent,false);
        return new KBYTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KBYTViewHolder holder, int position) {
        final KBYT kbyt = mListKBYT.get(position);
        if (kbyt == null){
            return;
        }
        holder.tvName.setText(kbyt.getName());
        holder.tvDay.setText(kbyt.getDay());
        holder.tvGender.setText(kbyt.getGender());
        holder.tvAddress.setText(kbyt.getAddress());
        holder.tvPhone.setText(kbyt.getPhone());
        holder.tvStatus.setText(kbyt.getStatus());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.updateKbyt(kbyt);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.deleteKbyt(kbyt);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListKBYT !=null){
            return mListKBYT.size();
        }
        return 0;
    }

    public class KBYTViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvDay;
        private TextView tvGender;
        private TextView tvAddress;
        private TextView tvPhone;
        private TextView tvStatus;
        private Button btnUpdate;
        private Button btnDelete;

        public KBYTViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
