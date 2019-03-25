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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;
import jcescudero15.tuenti.es.mytuentiapp.R;
import jcescudero15.tuenti.es.mytuentiapp.ui.UserFilterSort;
import jcescudero15.tuenti.es.mytuentiapp.ui.adapter.UserAdapter;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

public class AllUsersFragment extends DaggerFragment implements AllUsersContract.View {

    private final static Integer USERS_RESULT_COUNT = 10;
    private final static String QUERY = "QUERY";
    private final static String FILTER = "FILTER";


    @BindView(R.id.all_users_search_view)
    SearchView mSearchView;

    @BindView(R.id.all_users_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.all_users_progress)
    ProgressBar mProgress;

    @BindView(R.id.all_users_empty_users)
    TextView mEmptyUsersMessage;

    @BindView(R.id.all_users_sort_button)
    ImageButton mSortButton;


    @Inject
    AllUsersContract.Presenter mPresenter;

    private UserAdapter mUserAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AllUsersContract.OnUserSelectedListener mListener;
    private UserFilterSort mFilterSort = UserFilterSort.DEFAULT;
    private String mQueryText = "";


    @Inject
    public AllUsersFragment() {
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
        mUserAdapter = new UserAdapter(new ArrayList<>(), getActivity(), mUserOptionsListener);
        mLayoutManager = new LinearLayoutManager(getActivity());

        if (savedInstanceState != null) {
            mQueryText = savedInstanceState.getString(QUERY);
            mFilterSort = (UserFilterSort) savedInstanceState.get(FILTER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);
        ButterKnife.bind(this, view);

        // Set up recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserAdapter);

        mPresenter.takeView(this);
        mPresenter.getUserList(mFilterSort, mQueryText);

        setSearchViewQueryListener();
        setSortFilterIcon();

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY, mQueryText);
        outState.putSerializable(FILTER, mFilterSort);
    }


    @OnClick(R.id.all_users_sort_button)
    public void onSortButtonClick() {
        changeSortFilter();
        mPresenter.getUserList(mFilterSort, mQueryText);
    }

    @OnClick(R.id.all_users_retrieve_button)
    public void onRetrieveRandomUsersButtonClick() {
        mPresenter.retrieveRandomUsers(USERS_RESULT_COUNT);
        mRecyclerView.scrollToPosition(mUserAdapter.getItemCount() - 1);
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

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }


    private void changeSortFilter() {
        switch (mFilterSort) {
            case DEFAULT:
                mFilterSort = UserFilterSort.SORT_BY_NAME;
                mSortButton.setImageResource(R.drawable.ic_sort_name);
                break;
            case SORT_BY_NAME:
                mFilterSort = UserFilterSort.SORT_BY_GENDER;
                mSortButton.setImageResource(R.drawable.ic_sort_gender);
                break;
            case SORT_BY_GENDER:
                mFilterSort = UserFilterSort.DEFAULT;
                mSortButton.setImageResource(R.drawable.ic_sort_default);
                break;
        }
    }

    private void setSortFilterIcon() {
        switch (mFilterSort) {
            case DEFAULT:
                mSortButton.setImageResource(R.drawable.ic_sort_default);
                break;
            case SORT_BY_NAME:
                mSortButton.setImageResource(R.drawable.ic_sort_name);
                break;
            case SORT_BY_GENDER:
                mSortButton.setImageResource(R.drawable.ic_sort_gender);
                break;
        }
    }

    private void setSearchViewQueryListener() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                mQueryText = text;
                mPresenter.getUserList(mFilterSort, text);
                return false;
            }
        });
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
