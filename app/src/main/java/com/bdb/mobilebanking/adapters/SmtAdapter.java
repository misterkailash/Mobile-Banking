package com.bdb.mobilebanking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.models.Trans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmtAdapter extends RecyclerView.Adapter<SmtAdapter.TransHolder> {

    private List<Trans> data;
    private LayoutInflater inflater;
    private Context myContext;

    public SmtAdapter(Context context, List<Trans> data) {
        myContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_history, parent, false);
        return new TransHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransHolder holder, int position) {
        Trans current = data.get(position);
        holder.mode.setText(current.getTRANSACTION_MODE());
        holder.amount.setText(current.getAMOUNT());
        switch (current.getTRANSACTION_MODE()) {
            case "DEPOSIT":
                holder.TransTag.setText(myContext.getResources().getString(R.string.dr));
                break;
            case "WITHDRAWAL":
                holder.TransTag.setText(myContext.getResources().getString(R.string.cr));
                holder.TransTag.setTextColor(ContextCompat.getColor(myContext, R.color.bootstrap_brand_success));
                break;
            default:
                holder.TransTag.setText(myContext.getResources().getString(R.string.dr));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TransHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hist_trans_type)
        TextView TransTag;
        @BindView(R.id.hist_value_type)
        TextView mode;
        @BindView(R.id.hist_value_amount)
        TextView amount;

        TransHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
