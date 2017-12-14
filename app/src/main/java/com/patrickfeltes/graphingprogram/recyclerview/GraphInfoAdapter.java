package com.patrickfeltes.graphingprogram.recyclerview;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.database.objects.GraphInfo;
import com.patrickfeltes.graphingprogram.userinterface.graphs.GraphingActivity;

import java.util.List;

public class GraphInfoAdapter extends RecyclerView.Adapter<GraphInfoAdapter.GraphInfoViewHolder> {

    private List<GraphInfo> graphInfoList;

    public GraphInfoAdapter(List<GraphInfo> graphInfoList) {
        this.graphInfoList = graphInfoList;
    }

    @Override
    public GraphInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.graph_menu_list_item, parent, false);

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

    class GraphInfoViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView graphName;
        ImageButton removeButton;

        GraphInfoViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            graphName = itemView.findViewById(R.id.tv_graph_name);
            removeButton = itemView.findViewById(R.id.ib_remove);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    graphInfoList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    FirebaseRoutes.getGraphInfoForUser(FirebaseAuth.getInstance().getUid())
                            .setValue(graphInfoList);
                }
            });
        }

        void bind(final GraphInfo info) {
            graphName.setText(info.name);
            // if a graph view holder is clicked, start a new GraphingActivity with its information
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), GraphingActivity.class);
                    intent.putExtra(ExtraKeys.GRAPH_KEY, info.id);
                    intent.putExtra(ExtraKeys.GRAPH_NAME, info.name);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
