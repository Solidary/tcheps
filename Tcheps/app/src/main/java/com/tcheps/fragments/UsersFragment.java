package com.tcheps.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcheps.activities.PoseProblemActivity;
import com.tcheps.activities.R;
import com.tcheps.activities.UserProfileActivity;
import com.tcheps.activities.UsersFromDescriptionActivity;
import com.tcheps.adapters.ProblemsFeedAdapter;
import com.tcheps.adapters.UsersAdapter;
import com.tcheps.models.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment  implements
        UsersAdapter.OnUsersClickListener {

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.users_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.users_rv)
    RecyclerView rvUsers;

    List<User> users;
    public static UsersFragment newInstance(List<User> users) {
        UsersFragment fragment = new UsersFragment(users);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public UsersFragment(List<User> users) {
        this.users = users;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUsers.setAdapter(new UsersAdapter(getActivity(), users));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onProfileClick(View view, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(UserProfileActivity.ARG_USER_OBJECTID, tag);

        Intent profileIntent = new Intent(getActivity(), UserProfileActivity.class);
        profileIntent.putExtras(bundle);
        startActivity(profileIntent);
    }

    @Override
    public void onUsersFromDescriptionClick(View v, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(UsersFromDescriptionActivity.ARG_TAG, tag);

        Intent usersFromDescriptionIntent = new Intent(getActivity(), UsersFromDescriptionActivity.class);
        usersFromDescriptionIntent.putExtras(bundle);
        startActivity(usersFromDescriptionIntent);
    }

    @Override
    public void onFollowClick(View v, String tag) {
        Snackbar.make(
                coordinatorLayout,
                "Follow ...",
                Snackbar.LENGTH_LONG
        ).show();
    }
}
