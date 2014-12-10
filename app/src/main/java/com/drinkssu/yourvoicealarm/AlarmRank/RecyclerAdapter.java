package com.drinkssu.yourvoicealarm.AlarmRank;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drinkssu.yourvoicealarm.R;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 14. 12. 5..
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<RankInfoModel> rankInfoModels;

    public RecyclerAdapter(ArrayList<RankInfoModel> rankInfoModels) {
        this.rankInfoModels = rankInfoModels;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemLayoutView;

        itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ranking, null);

        RecyclerAdapter.ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {
        viewHolder.numText.setText("  "+rankInfoModels.get(position).getRankNum() + " ");
        viewHolder.alarmNameText.setText(rankInfoModels.get(position).getAlarm_text() + "");
        viewHolder.rankPoint.setText(rankInfoModels.get(position).getLank_point() + "");
        viewHolder.userNickName.setText(rankInfoModels.get(position).getNickname() + "");
    }

    @Override
    public int getItemCount() {
        return rankInfoModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView numText;
        public TextView alarmNameText;
        public TextView rankPoint;
        public TextView userNickName;


        public ViewHolder(View itemView) {
            super(itemView);
            numText = (TextView) itemView.findViewById(R.id.row_ranking_ranknum);
            alarmNameText = (TextView) itemView.findViewById(R.id.row_ranking_alarmname);
            rankPoint = (TextView) itemView.findViewById(R.id.row_ranking_rankpoint);
            userNickName = (TextView) itemView.findViewById(R.id.row_ranking_usernickname);
        }
    }
//
//    class VHHeader extends RecyclerView.ViewHolder {
//
//        public VHHeader(View itemView) {
//            super(itemView);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (isPositionHeader(position))
//            return TYPE_HEADER;
//
//        return TYPE_ITEM;
//    }
//
//    private boolean isPositionHeader(int position) {
//        return position == 0;
//    }
}
