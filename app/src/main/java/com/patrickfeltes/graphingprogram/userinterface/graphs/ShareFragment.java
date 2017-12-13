package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.database.objects.User;
import com.patrickfeltes.graphingprogram.recyclerview.UserHolder;

public class ShareFragment extends Fragment {

    private FirebaseRecyclerAdapter<User, UserHolder> adapter;
    private String graphKey;
    private String graphName;
    private EditText emailField;

    private RecyclerView rvUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_layout, container, false);

        graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);
        graphName = getArguments().getString(ExtraKeys.GRAPH_NAME);

        emailField = view.findViewById(R.id.et_username_share);

        rvUsers = view.findViewById(R.id.rv_users);
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(container.getContext()));

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapter != null) {
                    adapter.stopListening();
                }

                createAdapter(charSequence.toString());

                adapter.startListening();
                rvUsers.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    /**
     * This method takes an input and creates an adapter for the recycler view to show a user
     * @param input the email input
     */
    private void createAdapter(String input) {
        Query query = FirebaseDatabase.getInstance().getReference(FirebaseRoutes.USER_ROUTE).
                orderByChild(FirebaseRoutes.USERNAME_KEY).equalTo(input);

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<User, UserHolder>(options) {
            @Override
            public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user, parent, false);

                return new UserHolder(view, graphKey, graphName, emailField);
            }

            @Override
            protected void onBindViewHolder(UserHolder holder, int position, User model) {
                holder.bind(model);
            }
        };
    }
}
