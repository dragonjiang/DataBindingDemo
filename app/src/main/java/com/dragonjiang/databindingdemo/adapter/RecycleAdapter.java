package com.dragonjiang.databindingdemo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ItemRcvBinding;
import com.dragonjiang.databindingdemo.handler.RcvItemEventHandler;
import com.dragonjiang.databindingdemo.model.RcvItemModel;

import java.util.List;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.VH> {

    private List<RcvItemModel> mDataList;

    public RecycleAdapter(List<RcvItemModel> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRcvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rcv,
                parent,
                false);
        binding.setHandler(new RcvItemEventHandler());
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.binding.setModel(mDataList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public ItemRcvBinding binding;

        public VH(ItemRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
