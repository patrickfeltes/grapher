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
import com.patrickfeltes.graphingprogram.database.objects.User;
import com.patrickfeltes.graphingprogram.database.objects.UserHolder;

public class ShareFragment extends Fragment {

    private FirebaseRecyclerAdapter<User, UserHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_layout, container, false);

        final String graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);

        final EditText usernameField = view.findViewById(R.id.et_username_share);
        final RecyclerView rvUsers = view.findViewById(R.id.rv_users);
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(container.getContext()));

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapter != null) {
                    adapter.stopListening();
                }

                String input = charSequence.toString();
                Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(input);

                FirebaseRecyclerOptions<User> options =
                        new FirebaseRecyclerOptions.Builder<User>()
                                .setQuery(query, User.class)
                                .build();

                adapter = new FirebaseRecyclerAdapter<User, UserHolder>(options) {
                    @Override
                    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user, parent, false);

                        return new UserHolder(view, graphKey);
                    }

                    @Override
                    protected void onBindViewHolder(UserHolder holder, int position, User model) {
                        holder.bind(model);
                    }
                };

                adapter.startListening();
                rvUsers.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }
}
