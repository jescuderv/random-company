package jcescudero15.tuenti.es.mytuentiapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcescudero15.tuenti.es.mytuentiapp.R;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.Utils;

public class UserDetailsActivity extends AppCompatActivity {

    private final static String USER = "USER";
    private final static String GENDER_MALE = "male";


    @BindView(R.id.user_details_profile_image)
    ImageView mProfileImage;

    @BindView(R.id.user_details_name)
    TextView mName;

    @BindView(R.id.user_details_gender)
    TextView mGender;

    @BindView(R.id.user_details_registered_date_value)
    TextView mRegisteredDate;

    @BindView(R.id.user_details_location_street)
    TextView mStreetLocation;

    @BindView(R.id.user_details_location_city)
    TextView mCityLocation;

    @BindView(R.id.user_details_location_state)
    TextView mStateLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserViewModel userViewModel = (UserViewModel) extras.get(USER);
            if (userViewModel != null)
                showUserInfo(userViewModel);
            else showInfoNotAvailableMessage();

        } else showInfoNotAvailableMessage();
    }


    private void showInfoNotAvailableMessage() {
        Toast.makeText(this, getString(R.string.user_details_error), Toast.LENGTH_SHORT).show();
    }

    private void showUserInfo(UserViewModel userViewModel) {
        Glide.with(this)
                .load(userViewModel.getProfilePhotoURL())
                .into(mProfileImage);
        mName.setText(String.format("%s %s", Utils.capitalize(userViewModel.getName()),
                Utils.capitalize(userViewModel.getLastName())));
        if (userViewModel.getGender().equals(GENDER_MALE))
            mGender.setText(getString(R.string.user_details_male));
        else mGender.setText(getString(R.string.user_details_female));
        mRegisteredDate.setText(Utils.dateFormat(userViewModel.getRegisteredDate()));
        mStreetLocation.setText(userViewModel.getStreetLocation());
        mCityLocation.setText(Utils.capitalize(userViewModel.getCityLocation()));
        mStateLocation.setText(Utils.capitalize(userViewModel.getStateLocation()));
    }


}
