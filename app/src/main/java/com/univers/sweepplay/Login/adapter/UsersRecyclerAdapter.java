package com.univers.sweepplay.Login.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.univers.sweepplay.Login.model.User;
import com.univers.sweepplay.R;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    /**
     *  Create the view (without activity)
     * @param parent
     * @param viewType
     */
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_first, parent, false);

        return new UserViewHolder(itemView);
    }

    /**
     *  Create new field
     * @param holder
     * @param position
     */
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }
    /**
     *  add one item in the data base
     */
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }

    /**
     * view of the data base
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        /**
         *  init the view (not used in snp)
         */
        public UserViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewEmail = view.findViewById(R.id.textViewEmail);
            textViewPassword = view.findViewById(R.id.textViewPassword);
        }
    }
}