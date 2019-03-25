package jcescudero15.tuenti.es.mytuentiapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.ImageView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import jcescudero15.tuenti.es.mytuentiapp.R;
import jcescudero15.tuenti.es.mytuentiapp.ui.contract.AllUsersContract;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.AllUsersFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.CloseUsersFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.fragment.FavoritesUserFragment;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;

public class UserListActivity extends DaggerAppCompatActivity implements BottomNavigationView.
        OnNavigationItemSelectedListener, AllUsersContract.OnUserSelectedListener {

    private final static String USER = "USER";
    private final static String FRAGMENT = "FRAGMENT";


    @Inject
    AllUsersFragment mAllUsersFragment;

    @Inject
    FavoritesUserFragment mFavoritesUserFragment;

    @Inject
    CloseUsersFragment mCloseUsersFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        if (!isInTwoPanelMode()) {
            BottomNavigationView bottomNavigation = findViewById(R.id.user_list_bottom_navigation);
            bottomNavigation.setOnNavigationItemSelectedListener(this);

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT);
            if (fragment == null) {
                loadFragment(mAllUsersFragment, R.id.user_list_frame_layout);
            }

        } else {
            loadFragment(mAllUsersFragment, R.id.user_list_frame_all);
            loadFragment(mFavoritesUserFragment, R.id.user_list_frame_favorites);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.bottom_navigation_all_users:
                fragment = mAllUsersFragment;
                break;

            case R.id.bottom_navigation_favorites:
                fragment = mFavoritesUserFragment;
                break;

            case R.id.bottom_navigation_close_users:
                fragment = mCloseUsersFragment;
                break;
        }
        return loadFragment(fragment, R.id.user_list_frame_layout);
    }


    @Override
    public void showUserInfo(UserViewModel userViewModel, ImageView profileImage) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(USER, userViewModel);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, profileImage,
                getResources().getString(R.string.trans_profile_image_users)).toBundle());
    }


    private boolean loadFragment(Fragment fragment, int frameId) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(frameId, fragment, FRAGMENT)
                    .commit();
            return true;
        }
        return false;
    }

    private boolean isInTwoPanelMode() {
        // If there is no user_list_frame_layout ID, then the application is in two-panel mode
        return findViewById(R.id.user_list_frame_layout) == null;

    }

}
