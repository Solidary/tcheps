package com.tcheps.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.activities.UserProfileActivity;
import com.tcheps.models.Problem;
import com.tcheps.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class ProblemsFeedAdapter extends RecyclerView.Adapter<ProblemsFeedAdapter.ViewHolder> implements
        View.OnClickListener {

    private Context mContext;
    private List<Problem> mProblems;

    private OnProblemsFeedClickListener onProblemsFeedClickListener;

    public ProblemsFeedAdapter(Context context, List<Problem> problems) {
        mProblems = problems;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View problemFeedView = inflater.inflate(R.layout.problems_feed_item, parent, false);
        ViewHolder holder = new ViewHolder(problemFeedView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProblemsFeedAdapter.ViewHolder holder, int position) {
        Problem problem = mProblems.get(position);
        problem.setDescription(mContext.getString(R.string.lorem_ipsum));


        int rc = ColorGenerator.MATERIAL.getRandomColor();
        holder.authorAvatar.setImageDrawable(
            TextDrawable.builder()
                .buildRound(problem.getAuthor().getInitials(), rc)
        );
        /*holder.statImgLikers.setImageDrawable(Utils.getTintedDrawable(mContext.getResources(),
                R.drawable.ic_favorite_black_18dp,
                rc));*/
        holder.authorDisplayName.setText(problem.getAuthor().getDisplayName());
        holder.authorDescription.setText(problem.getAuthor().getDescription());
        holder.description.setText(problem.getDescription());

        holder.authorAvatar.setTag(problem.getAuthor().getObjectId());
        holder.authorDisplayName.setTag(problem.getAuthor().getObjectId());
        holder.authorDescription.setTag(problem.getAuthor().getDescription() + ", " /*+ problem.getAuthor().getType()*/);
        holder.comment.setTag(problem.getObjectId());

        holder.authorAvatar.setOnClickListener(this);
        holder.authorDisplayName.setOnClickListener(this);
        holder.authorDescription.setOnClickListener(this);
        holder.comment.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mProblems.size();
    }

    public void setOnProblemsFeedClickListener(OnProblemsFeedClickListener onProblemsFeedClickListener) {
        this.onProblemsFeedClickListener = onProblemsFeedClickListener;
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == R.id.problems_feed_author_avatar ||
                viewId == R.id.problems_feed_author_display_name) {
            if (onProblemsFeedClickListener != null) {
                onProblemsFeedClickListener.onProfileClick(view, view.getTag().toString());
            }
        } else if (viewId == R.id.problems_feed_author_description) {
            if (onProblemsFeedClickListener != null) {
                onProblemsFeedClickListener.onUsersFromDescriptionClick(view,
                        view.getTag().toString());
            }
        } else if (viewId == R.id.problems_feed_comment_btn) {
            if (onProblemsFeedClickListener != null) {
                onProblemsFeedClickListener.onCommentClick(view, view.getTag().toString());
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*@Bind(R.id.problems_feed_stats_likers_iv)
        public ImageView statImgLikers;*/

        @Bind(R.id.problems_feed_author_avatar)
        public ImageView authorAvatar;
        @Bind(R.id.problems_feed_author_display_name)
        public TextView authorDisplayName;
        @Bind(R.id.problems_feed_author_description)
        public TextView authorDescription;
        @Bind(R.id.problems_feed_description)
        public TextView description;

        @Bind(R.id.problems_feed_comment_btn)
        public ImageButton comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnProblemsFeedClickListener {
        public void onProfileClick(View v, String tag);
        public void onUsersFromDescriptionClick(View v, String tag);
        public void onCommentClick(View v, String tag);
    }
}
