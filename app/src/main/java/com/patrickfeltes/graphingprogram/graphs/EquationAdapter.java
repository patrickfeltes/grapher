package com.patrickfeltes.graphingprogram.graphs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.patrickfeltes.graphingprogram.R;

import java.util.List;

public class EquationAdapter extends RecyclerView.Adapter<EquationAdapter.EquationViewHolder> {
    private List<Equation> equationList;

    public EquationAdapter(List<Equation> equationList) {
        this.equationList = equationList;
    }

    @Override
    public EquationAdapter.EquationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equation_list_item, parent, false);

        return new EquationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EquationAdapter.EquationViewHolder holder, int position) {
        holder.bind(equationList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return equationList.size();
    }

    public void addEquation() {
        equationList.add(new Equation(""));
        notifyDataSetChanged();
    }

    class EquationViewHolder extends RecyclerView.ViewHolder {

        TextView equationLabel;
        EditText equationField;

        public EquationViewHolder(View itemView) {
            super(itemView);

            equationLabel = (TextView) itemView.findViewById(R.id.tv_equation_label);
            equationField = (EditText) itemView.findViewById(R.id.et_equation_field);
        }

        void bind(Equation equation, int position) {
            equationLabel.setText("y" + (position + 1) + "(x)=");
            equationField.setText(equation.getEquation());
        }
    }
}
