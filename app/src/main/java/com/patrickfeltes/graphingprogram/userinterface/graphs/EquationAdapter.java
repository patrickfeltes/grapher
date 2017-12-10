package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.parser.Parser;
import com.patrickfeltes.graphingprogram.parser.Tokenizer;
import com.patrickfeltes.graphingprogram.parser.exceptions.InvalidExpressionException;

import java.util.ArrayList;
import java.util.List;

public class EquationAdapter extends RecyclerView.Adapter<EquationAdapter.EquationViewHolder> {
    private List<String> equationList;
    private String graphKey;

    public EquationAdapter(List<String> equationList, String graphKey) {
        this.equationList = equationList;
        this.graphKey = graphKey;
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
        equationList.add("");
        notifyDataSetChanged();
    }

    class EquationViewHolder extends RecyclerView.ViewHolder {

        TextView equationLabel;
        EditText equationField;

        private int position;

        public EquationViewHolder(View itemView) {
            super(itemView);

            equationLabel = itemView.findViewById(R.id.tv_equation_label);
            equationField = itemView.findViewById(R.id.et_equation_field);

            equationField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        if (equationField.getText().toString().trim().length() == 0) return;
                        try {
                            // will throw exception if the expression is invalid
                            new Parser(new Tokenizer(equationField.getText().toString())).parse();
                            equationList.set(position, equationField.getText().toString());
                            FirebaseDatabase.getInstance().getReference("graphs").child(graphKey).child("equations").setValue(equationList);
                        } catch(InvalidExpressionException e) {
                            Toast.makeText(view.getContext(), "Invalid expression.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        void bind(String equation, int position) {
            this.position = position;
            equationLabel.setText("y" + (position + 1) + "(x)=");
            equationField.setText(equation);
        }
    }
}
