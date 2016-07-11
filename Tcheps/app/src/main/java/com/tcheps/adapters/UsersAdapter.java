package com.tcheps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.models.User;
import com.tcheps.models.User;
import com.tcheps.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements
        View.OnClickListener {

    private Context mContext;
    private List<User> mUsers;

    private OnUsersClickListener onUsersClickListener;

    public UsersAdapter(Context context, List<User> Users) {
        mUsers = Users;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View UserFeedView = inflater.inflate(R.layout.users_item, parent, false);
        ViewHolder holder = new ViewHolder(UserFeedView);

        return holder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);

        int rc = ColorGenerator.MATERIAL.getRandomColor();
        holder.uAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(user.getInitials(), rc)
        );
        holder.uDisplayName.setText(user.getName());
        holder.uDescription.setText(user.getDescription());

        holder.uAvatar.setTag(user.getObjectId());
        holder.uDisplayName.setTag(user.getObjectId());
        holder.uDescription.setTag(user.getDescription() + ", " /*+ user.getType()*/);

        holder.uAvatar.setOnClickListener(this);
        holder.uDisplayName.setOnClickListener(this);
        holder.uDescription.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setOnUsersFeedClickListener(OnUsersClickListener onUsersClickListener) {
        this.onUsersClickListener = onUsersClickListener;
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == R.id.problems_feed_author_avatar ||
                viewId == R.id.problems_feed_author_display_name) {
            if (onUsersClickListener != null) {
                onUsersClickListener.onProfileClick(view, view.getTag().toString());
            }
        } else if (viewId == R.id.users_follow_btn) {
            if (onUsersClickListener != null) {
                onUsersClickListener.onFollowClick(view, view.getTag().toString());
            }
        } else if (viewId == R.id.users_description) {
            if (onUsersClickListener != null) {
                onUsersClickListener.onUsersFromDescriptionClick(view,
                        view.getTag().toString());
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.users_avatar)
        public ImageView uAvatar;

        @Bind(R.id.users_display_name)
        public TextView uDisplayName;
        @Bind(R.id.users_description)
        public TextView uDescription;

        @Bind(R.id.users_follow_btn)
        public Button uFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnUsersClickListener {
        public void onProfileClick(View v, String tag);
        public void onUsersFromDescriptionClick(View v, String tag);
        public void onFollowClick(View v, String tag);
    }
}
