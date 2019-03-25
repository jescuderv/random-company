package jcescudero15.tuenti.es.mytuentiapp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import jcescudero15.tuenti.es.mytuentiapp.R;
import jcescudero15.tuenti.es.mytuentiapp.ui.adapter.UserAdapter;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.CloseUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

public class CloseUsersFragment extends DaggerFragment implements CloseUsersContract.View {

    @BindView(R.id.close_users_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.close_users_empty_users)
    TextView mEmptyUsersMessage;


    @Inject
    CloseUsersContract.Presenter mPresenter;

    private RecyclerView.LayoutManager mLayoutManager;
    private UserAdapter mUserAdapter;
    private AllUsersContract.OnUserSelectedListener mListener;


    @Inject
    public CloseUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AllUsersContract.OnUserSelectedListener)
            mListener = (AllUsersContract.OnUserSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mUserAdapter = new UserAdapter(new ArrayList<>(), getActivity(), mUserOptionsListener);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_close_users, container, false);
        ButterKnife.bind(this, view);

        mPresenter.takeView(this);

        // Set up recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void showUserList(List<UserViewModel> userList) {
        mEmptyUsersMessage.setVisibility(View.GONE);
        mUserAdapter.replaceData(userList);
    }

    @Override
    public void showEmptyUsersMessage() {
        mEmptyUsersMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetworkErrorMessage() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDatabaseErrorMessage() {
        Toast.makeText(getActivity(), getString(R.string.database_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserDeletedMessage() {
        Toast.makeText(getActivity(), getString(R.string.all_users_user_deleted), Toast.LENGTH_SHORT).show();
    }


    private UserAdapter.UserOptionsListener mUserOptionsListener = new UserAdapter.UserOptionsListener() {
        @Override
        public void onDeleteUser(UserViewModel userViewModel) {
            mPresenter.deleteUser(userViewModel);
        }

        @Override
        public void onSetUserAsFavorite(boolean isFavorite, UserViewModel userViewModel) {
            mPresenter.setUserFavorite(isFavorite, userViewModel);
        }

        @Override
        public void onUserSelected(UserViewModel userViewModel, ImageView profileImage) {
            mListener.showUserInfo(userViewModel, profileImage);
        }
    };

}
