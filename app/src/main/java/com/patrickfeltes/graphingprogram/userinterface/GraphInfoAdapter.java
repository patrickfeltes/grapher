package com.patrickfeltes.graphingprogram.userinterface;

import android.content.Intent;
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

    private List<String> names;

    public GraphInfoAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public GraphInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_menu_list_item, parent, false);

        return new GraphInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GraphInfoViewHolder holder, int position) {
        holder.bind(names.get(position), position);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public void addGraph() {
        names.add("random");
        notifyDataSetChanged();
    }

    class GraphInfoViewHolder extends RecyclerView.ViewHolder {

        TextView graphName;

        public GraphInfoViewHolder(View itemView) {
            super(itemView);

            Log.d("TAG:", "hello");

            graphName = (TextView) itemView.findViewById(R.id.tv_graph_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), GraphingActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bind(String name, int position) {
            Log.d("TAG", "bind");
            graphName.setText("Graph " + position);
        }
    }
}
