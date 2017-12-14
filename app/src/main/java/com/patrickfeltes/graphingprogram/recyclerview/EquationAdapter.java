package com.patrickfeltes.graphingprogram.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.parser.Parser;
import com.patrickfeltes.graphingprogram.parser.Tokenizer;
import com.patrickfeltes.graphingprogram.parser.exceptions.InvalidExpressionException;

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

            // code from:
            //https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
            equationField.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String equation = equationField.getText().toString();
                        updateEquations(equation);
                        return true;
                    }
                    return false;
                }
            });
        }

        void bind(String equation) {
            equationLabel.setText("y" + (getLayoutPosition() + 1) + "(x) = ");
            equationField.setText(equation);
        }

        /**
         * Given an equation, update the value at its position
         * @param equation the equation to use to update
         */
        void updateEquations(String equation) {
            if (equation.trim().length() == 0) {
                equationList.set(getLayoutPosition(), equation);
                FirebaseRoutes.getGraphEquationsRoute(graphKey).setValue(equationList);
            } else {
                try {
                    // will throw exception if the expression is invalid
                    new Parser(new Tokenizer(equationField.getText().toString())).parse();
                    equationList.set(getLayoutPosition(), equation);
                    FirebaseRoutes.getGraphEquationsRoute(graphKey).setValue(equationList);
                } catch(InvalidExpressionException e) {
                    Toast.makeText(equationField.getContext(), "Invalid expression.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
