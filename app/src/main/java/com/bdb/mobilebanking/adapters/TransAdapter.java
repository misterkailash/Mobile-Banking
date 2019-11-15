package com.bdb.mobilebanking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.models.Trans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransHolder> {

    private List<Trans> data;
    private LayoutInflater inflater;

    public TransAdapter(Context context, List<Trans> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_trans, parent, false);
        return new TransHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransHolder holder, int position) {
        Trans current = data.get(position);
        holder.ref_no.setText(current.getTRAN_REF_NO());
        holder.date_time.setText(current.getDATE_TIME());
        holder.mode.setText(current.getTRANSACTION_MODE());
        holder.from_acc.setText(current.getFROM_FORACID());
        holder.to_acc.setText(current.getTO_FORACID());
        holder.amount.setText(current.getAMOUNT());
        switch (current.getTRANSACTION_MODE()) {
            case "DEPOSIT":
                holder.layoutFrom.setVisibility(View.GONE);
                holder.TransImage.setImageResource(R.mipmap.ic_deposit);
                break;
            case "WITHDRAWAL":
                holder.layoutTo.setVisibility(View.GONE);
                holder.TransImage.setImageResource(R.mipmap.ic_cashout);
                break;
            case "FUND TRANSFER":
                holder.TransImage.setImageResource(R.mipmap.ic_fundtransfer);
                break;
            default:
                holder.layoutFrom.setVisibility(View.GONE);
                holder.TransImage.setImageResource(R.mipmap.ic_loan);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TransHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_fromAcc)
        View layoutFrom;
        @BindView(R.id.layout_toAcc)
        View layoutTo;
        @BindView(R.id.trans_type)
        ImageView TransImage;
        @BindView(R.id.value_ref_no)
        TextView ref_no;
        @BindView(R.id.value_date)
        TextView date_time;
        @BindView(R.id.value_mode)
        TextView mode;
        @BindView(R.id.value_from_acc)
        TextView from_acc;
        @BindView(R.id.value_to_acc)
        TextView to_acc;
        @BindView(R.id.value_amount)
        TextView amount;

        TransHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
