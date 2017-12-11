package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.parser.Parser;
import com.patrickfeltes.graphingprogram.parser.Tokenizer;
import com.patrickfeltes.graphingprogram.parser.exceptions.InvalidExpressionException;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter that handles equation input in EquationFragment.
 */
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
        holder.bind(equationList.get(position));
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

        public EquationViewHolder(View itemView) {
            super(itemView);

            equationLabel = itemView.findViewById(R.id.tv_equation_label);
            equationField = itemView.findViewById(R.id.et_equation_field);

            equationField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        // we should add to database if it's an empty equation
                        String equation = equationField.getText().toString();
                        if (equation.trim().length() == 0) {
                            Log.d("TAG","hello");
                            equationList.set(getLayoutPosition(), equation);
                            FirebaseRoutes.getGraphEquationsRoute(graphKey).setValue(equationList);
                            return;
                        }
                        try {
                            // will throw exception if the expression is invalid
                            new Parser(new Tokenizer(equationField.getText().toString())).parse();
                            equationList.set(getLayoutPosition(), equation);
                            FirebaseRoutes.getGraphEquationsRoute(graphKey).setValue(equationList);
                        } catch(InvalidExpressionException e) {
                            Toast.makeText(view.getContext(), "Invalid expression.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        void bind(String equation) {
            equationLabel.setText("y" + (getLayoutPosition() + 1) + "(x)=");
            equationField.setText(equation);
        }
    }
}
