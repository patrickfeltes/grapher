package com.patrickfeltes.graphingprogram.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.userinterface.graphs.GraphingActivity;

import java.util.List;

public class GraphInfoAdapter extends RecyclerView.Adapter<GraphInfoAdapter.GraphInfoViewHolder> {

    private List<GraphInfo> graphInfoList;

    public GraphInfoAdapter(List<GraphInfo> graphInfoList) {
        this.graphInfoList = graphInfoList;
    }

    @Override
    public GraphInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_menu_list_item, parent, false);

        return new GraphInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GraphInfoViewHolder holder, int position) {
        holder.bind(graphInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return graphInfoList.size();
    }

    public void addGraph() {
        graphInfoList.add(new GraphInfo("random", "id"));
        notifyDataSetChanged();
    }

    class GraphInfoViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView graphName;

        public GraphInfoViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            graphName = itemView.findViewById(R.id.tv_graph_name);
        }

        public void bind(final GraphInfo info) {
            graphName.setText(info.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), GraphingActivity.class);
                    intent.putExtra("graphKey", info.id);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
