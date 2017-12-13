package com.patrickfeltes.graphingprogram.database.objects;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.patrickfeltes.graphingprogram.R;

public class UserHolder extends RecyclerView.ViewHolder {

    TextView username;

    public UserHolder(View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.tv_user);
    }

    public void bind(User user) {
        username.setText(user.username);
    }
}
