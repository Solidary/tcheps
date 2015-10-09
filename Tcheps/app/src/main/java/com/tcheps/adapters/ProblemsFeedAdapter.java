package com.tcheps.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.AuthPreferences;
import com.tcheps.activities.R;
import com.tcheps.activities.UserProfileActivity;
import com.tcheps.authenticator.TsAuthenticatorService;
import com.tcheps.models.Problem;
import com.tcheps.models.User;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.api.ProblemsAPI;
import com.tcheps.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class ProblemsFeedAdapter extends RecyclerView.Adapter<ProblemsFeedAdapter.ViewHolder> implements
        View.OnClickListener {
    public static final String TAG = "ProblemsFeedAdapter";

    private final static AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private final static OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private final Map<ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private final ArrayList<String> likedPositions = new ArrayList<>();


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
        AuthPreferences authPreferences = new AuthPreferences(mContext);

        int rc = ColorGenerator.MATERIAL.getRandomColor();
        holder.authorAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(problem.getAuthor().getInitials(), rc)
        );

        updateLikesCounter(new TsTag(holder, problem), false);
        updateLikeButton(new TsTag(holder, problem), false);

        updateFollowersCounter(new TsTag(holder, problem), false);
        updateFollowerButton(new TsTag(holder, problem), false);

        Date now = new Date();
        Log.d(TAG, "Now : " + now.getTime() + " --- Created : " + problem.getCreated().getTime());
        long seconds = Math.abs(now.getTime() - problem.getCreated().getTime())/1000;
        long minutes = seconds / 60;
        long hours = seconds / 3600;
        long days = seconds / 86400;
        Log.d(TAG, "Seconds : " + seconds + " --- Minutes : " + minutes + " --- Hours : " + hours + " --- Days : " + days);
        if (seconds < 59) {
            holder.created.setText(seconds + "s");
        } else if ( minutes < 60 ) {
            holder.created.setText(minutes + "m");
        } else if ( hours < 24 ) {
            holder.created.setText(hours + "h");
        } else if ( days < 365 ) {
            holder.created.setText(days + "d");
        }

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

        // holder.likes.setText(problem.getLikes().size() + " likes");
        // holder.followe.setText(problem.getFollowers().size() + " followers");

        if (!problem.isUserHasLiked(authPreferences.getUser())) {
            holder.likeBtn.setImageResource(R.drawable.ic_favorite_border_color_default_24dp);
        } else {
            holder.likeBtn.setImageResource(R.drawable.ic_favorite_col0rs_24dp_2);
        }
        holder.likeBtn.setOnClickListener(this);
        holder.likeBtn.setTag(new TsTag(holder, problem));
        if (likeAnimations.containsKey(holder)) {
            likeAnimations.get(holder).cancel();
        }
        resetLikeAnimationState(holder);

        holder.followerBtn.setOnClickListener(this);
        holder.followerBtn.setTag(new TsTag(holder, problem));
    }

    @Override
    public int getItemCount() {
        return mProblems.size();
    }

    public void updateData(List<Problem> problems) {
        mProblems = problems;
        notifyDataSetChanged();
    }

    public void setOnProblemsFeedClickListener(OnProblemsFeedClickListener onProblemsFeedClickListener) {
        this.onProblemsFeedClickListener = onProblemsFeedClickListener;
    }

    private void updateLikesCounter(TsTag likeTag, boolean animated) {
        Problem problem = likeTag.problem;
        ViewHolder holder = likeTag.holder;


        if (animated) {
            int currentLikesCount = problem.getLikes().size() + 1;
            String likesCountText = mContext.getResources().getQuantityString(
                    R.plurals.likes_count, currentLikesCount, currentLikesCount
            );

            holder.likesCounter.setText(likesCountText);
        } else {
            String likesCountText = mContext.getResources().getQuantityString(
                    R.plurals.likes_count, problem.getLikes().size(), problem.getLikes().size()
            );
            holder.likesCounter.setCurrentText(likesCountText);
        }
    }

    private void updateFollowersCounter(TsTag followerTag, boolean animated) {
        Problem problem = followerTag.problem;
        ViewHolder holder = followerTag.holder;


        if (animated) {
            int currentLFollowersCount = problem.getFollowers().size() + 1;
            String followersCountText = mContext.getResources().getQuantityString(
                    R.plurals.followers_count, currentLFollowersCount, currentLFollowersCount
            );

            holder.followersCounter.setText(followersCountText);
        } else {
            String followersCountText = mContext.getResources().getQuantityString(
                    R.plurals.followers_count, problem.getFollowers().size(), problem.getFollowers().size()
            );

            holder.followersCounter.setText(followersCountText);
        }
    }

    private void updateLikeButton(final TsTag likeTag, boolean animated) {
        final ViewHolder holder = likeTag.holder;
        Problem problem = likeTag.problem;

        if (animated) {
            if (!likeAnimations.containsKey(holder)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.likeBtn, "rotation", 0f, 360);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.likeBtn, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.likeBtn, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.likeBtn.setImageResource(R.drawable.ic_favorite_col0rs_24dp_2);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetLikeAnimationState(holder);
                    }
                });

                animatorSet.start();
            } else {
                if (likedPositions.contains(holder.getPosition())) {
                    holder.likeBtn.setImageResource(R.drawable.ic_favorite_col0rs_24dp_2);
                } else {
                    holder.likeBtn.setImageResource(R.drawable.ic_favorite_border_color_default_24dp);
                }
            }
        }
    }

    private void updateFollowerButton(final TsTag followerTag, boolean animated) {
        final ViewHolder holder = followerTag.holder;
        Problem problem = followerTag.problem;

        if (animated) {
            if (!likeAnimations.containsKey(holder)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.followerBtn, "rotation", 0f, 360);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.followerBtn, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.followerBtn, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.followerBtn.setImageResource(R.drawable.ic_flash_on_black_24dp);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetLikeAnimationState(holder);
                    }
                });

                animatorSet.start();
            } else {
                if (likedPositions.contains(holder.getPosition())) {
                    holder.followerBtn.setImageResource(R.drawable.ic_flash_on_black_24dp);
                } else {
                    holder.followerBtn.setImageResource(R.drawable.ic_flash_on_color_default_24dp);
                }
            }
        }
    }

    private void resetLikeAnimationState(ViewHolder holder) {
        likeAnimations.remove(holder);
        //holder.
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
        } else if (viewId == R.id.problems_feed_like_btn) {
            final TsTag likeTag = (TsTag)view.getTag();

            AuthPreferences authPreferences = new AuthPreferences(mContext);
            ProblemsAPI problemsAPI = TsServiceGenerator.create(ProblemsAPI.class, authPreferences.getToken());
            problemsAPI.like(likeTag.problem.getObjectId(), new Callback<Boolean>() {
                @Override
                public void success(Boolean state, Response response) {
                    Log.d(TAG, "Success Problem Like --- " + state);
                    if (state) {
                        updateLikesCounter(likeTag, true);
                        updateLikeButton(likeTag, true);
                    } else {
                        likeTag.holder.likeBtn.setImageResource(R.drawable.ic_favorite_border_color_default_24dp);

                        /*likeTag.holder.likesCounter.setCurrentText(likesCountText);*/
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e(TAG, "Error Problem Like --- " + retrofitError.getMessage());
                }
            });
        } else if (viewId == R.id.problems_feed_follow_btn) {
            final TsTag followTag = (TsTag)view.getTag();

            AuthPreferences authPreferences = new AuthPreferences(mContext);
            ProblemsAPI problemsAPI = TsServiceGenerator.create(ProblemsAPI.class, authPreferences.getToken());
            problemsAPI.follow(followTag.problem.getObjectId(), new Callback<Boolean>() {
                @Override
                public void success(Boolean state, Response response) {
                    Log.d(TAG, "Success Problem Follow --- " + state);
                    if (state) {
                        updateFollowersCounter(followTag, true);
                        updateFollowerButton(followTag, true);
                    } else {
                        followTag.holder.followerBtn.setImageResource(R.drawable.ic_flash_on_color_default_24dp);

                        /*likeTag.holder.likesCounter.setCurrentText(likesCountText);*/
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e(TAG, "Error Problem Follow --- " + retrofitError.getMessage());
                }
            });
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
        @Bind(R.id.problems_feed_created)
        public TextView created;

        @Bind(R.id.problems_feed_follow_btn)
        public ImageButton followerBtn;
        @Bind(R.id.problems_feed_like_btn)
        public ImageButton likeBtn;

        @Bind(R.id.problems_feed_likes_counter)
        public TextSwitcher likesCounter;
        @Bind(R.id.problems_feed_follows_counter)
        public TextSwitcher followersCounter;

        @Bind(R.id.problems_feed_comment_btn)
        public ImageButton comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class TsTag {
        public ViewHolder holder;
        public Problem problem;

        public TsTag(ViewHolder holder, Problem problem) {
            this.holder = holder;
            this.problem = problem;
        }
    }
    public interface OnProblemsFeedClickListener {
        public void onProfileClick(View v, String tag);
        public void onUsersFromDescriptionClick(View v, String tag);
        public void onCommentClick(View v, String tag);
        public void onLikeClick(View v, String tag);
        public void onFollowClick(View v, String tag);
    }

}
