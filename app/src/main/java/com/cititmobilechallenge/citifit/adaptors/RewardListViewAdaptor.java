package com.cititmobilechallenge.citifit.adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.modal.RewardHolder;

import java.util.ArrayList;

/**
 * Created by ashwiask on 10/25/2015.
 */
public class RewardListViewAdaptor extends RecyclerView.Adapter<RewardListViewAdaptor.RewardDataHolder> {

    private static RewardListItemClickListener mClickListener;

    private ArrayList<RewardHolder> mRewardList = null;

    private Context mContext = null;

    public RewardListViewAdaptor(ArrayList<RewardHolder> rewardList, Context context, RewardListItemClickListener listener) {
        mRewardList = rewardList;
        mContext = context;
        mClickListener = listener;
    }

    @Override
    public RewardDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_view_row, parent, false);

        RewardDataHolder rewardDataHolder = new RewardDataHolder(view);
        return rewardDataHolder;
    }

    @Override
    public void onBindViewHolder(RewardDataHolder holder, int position) {
        holder.image.setImageBitmap(mRewardList.get(position).getImage());

        //TODO - Set other items of list, once the data is retrieved dynamically
    }

    @Override
    public int getItemCount() {
        return mRewardList.size();
    }

    public static class RewardDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private TextView price;
        private TextView goals;
        private TextView points;

        public RewardDataHolder(View itemView) {

            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.rewardImage);
            price = (TextView) itemView.findViewById(R.id.textPrice);
            points = (TextView) itemView.findViewById(R.id.textPoints);
            goals = (TextView) itemView.findViewById(R.id.textGoals);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface RewardListItemClickListener {
        void onItemClick(int position);
    }
}
