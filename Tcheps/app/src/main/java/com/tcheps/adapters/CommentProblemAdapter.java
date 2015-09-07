package com.tcheps.adapters;

import android.content.Context;
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
import com.tcheps.models.Comment;
import com.tcheps.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class CommentProblemAdapter extends RecyclerView.Adapter<CommentProblemAdapter.ViewHolder> implements
        View.OnClickListener {

    private Context mContext;
    private List<Comment> mComments;

    private OnCommentProblemClickListener onCommentProblemClickListener;

    public CommentProblemAdapter(Context context, List<Comment> comments) {
        mComments = comments;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View problemFeedView = inflater.inflate(R.layout.comment_problem_item, parent, false);
        ViewHolder holder = new ViewHolder(problemFeedView);

        return holder;
    }

    @Override
    public void onBindViewHolder(CommentProblemAdapter.ViewHolder holder, int position) {
        Comment problem = mComments.get(position);
        problem.setDescription(mContext.getString(R.string.lorem_ipsum));


        int rc = ColorGenerator.MATERIAL.getRandomColor();
        holder.authorAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(problem.getAuthor().getInitials(), rc)
        );
        holder.authorDisplayName.setText(problem.getAuthor().getDisplayName());
        holder.authorDescription.setText(problem.getAuthor().getDescription());
        holder.description.setText(problem.getDescription());

        holder.authorAvatar.setTag(problem.getAuthor().getObjectId());
        holder.authorDisplayName.setTag(problem.getAuthor().getObjectId());
        holder.authorDescription.setTag(problem.getAuthor().getDescription() + ", " + problem.getAuthor().getType());

        holder.authorAvatar.setOnClickListener(this);
        holder.authorDisplayName.setOnClickListener(this);
        holder.authorDescription.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setOnCommentProblemClickListener(OnCommentProblemClickListener onCommentProblemClickListener) {
        this.onCommentProblemClickListener = onCommentProblemClickListener;
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == R.id.comment_problem_author_avatar ||
                viewId == R.id.comment_problem_author_display_name) {
            if (onCommentProblemClickListener != null) {
                onCommentProblemClickListener.onProfileClick(view, view.getTag().toString());
            }
        } else if (viewId == R.id.comment_problem_author_description) {
            if (onCommentProblemClickListener != null) {
                onCommentProblemClickListener.onUsersFromDescriptionClick(view,
                        view.getTag().toString());
            }
        } /*else if (viewId == R.id.comment_problem_comment_btn) {
            if (onCommentProblemClickListener != null) {
                onCommentProblemClickListener.onCommentClick(view, view.getTag().toString());
            }
        }*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // @Bind(R.id.comment_iv)
        public ImageView like;

        @Bind(R.id.comment_problem_item_author_avatar)
        public ImageView authorAvatar;
        @Bind(R.id.comment_problem_item_author_display_name)
        public TextView authorDisplayName;
        @Bind(R.id.comment_problem_item_author_description)
        public TextView authorDescription;
        @Bind(R.id.comment_problem_item_description)
        public TextView description;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCommentProblemClickListener {
        public void onProfileClick(View v, String tag);
        public void onUsersFromDescriptionClick(View v, String tag);
        public void onLikeClick(View v, String tag);
    }

}
