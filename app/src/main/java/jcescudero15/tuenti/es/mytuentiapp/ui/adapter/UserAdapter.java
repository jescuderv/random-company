package jcescudero15.tuenti.es.mytuentiapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jcescudero15.tuenti.es.mytuentiapp.R;
import jcescudero15.tuenti.es.mytuentiapp.ui.viewmodel.UserViewModel;
import jcescudero15.tuenti.es.mytuentiapp.utils.Utils;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public interface UserOptionsListener {
        void onDeleteUser(UserViewModel userViewModel);

        void onSetUserAsFavorite(boolean isFavorite, UserViewModel userViewModel);

        void onUserSelected(UserViewModel userViewModel, ImageView profileImage);
    }


    private List<UserViewModel> mUserList;
    private Context mContext;
    private UserOptionsListener mListener;


    public UserAdapter(List<UserViewModel> userList, Context mContext, UserOptionsListener mListener) {
        this.mUserList = userList;
        this.mContext = mContext;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public void replaceData(List<UserViewModel> userViewModelList){
        mUserList = userViewModelList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user_profile_image)
        ImageView mProfileImage;

        @BindView(R.id.item_user_name)
        TextView mName;

        @BindView(R.id.item_user_email)
        TextView mEmail;

        @BindView(R.id.item_user_phone)
        TextView mPhone;

        @BindView(R.id.item_user_favorite_button)
        ImageButton mFavoriteButton;


        private UserViewModel mUserViewModel;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(UserViewModel user) {
            mUserViewModel = user;
            mName.setText(String.format("%s %s", Utils.capitalize(user.getName()),
                    Utils.capitalize(user.getLastName())));
            mEmail.setText(user.getEmail());
            mPhone.setText(user.getPhone());
            Glide.with(mContext)
                    .load(user.getProfilePhotoURL())
                    .into(mProfileImage);

            if (user.isFavorite()) mFavoriteButton.setImageResource(R.drawable.ic_star_active);
            else mFavoriteButton.setImageResource(R.drawable.ic_star_deactivate);

        }


        @OnClick(R.id.item_user_layout)
        void onUserSelected() {
            mListener.onUserSelected(mUserViewModel, mProfileImage);
        }

        @OnClick(R.id.item_user_delete_button)
        void onDeleteUserClick() {
            mListener.onDeleteUser(mUserViewModel);
        }

        @OnClick(R.id.item_user_favorite_button)
        void onSetUserFavoriteClick() {
            if (mUserViewModel.isFavorite()) mListener.onSetUserAsFavorite(false, mUserViewModel);
            else mListener.onSetUserAsFavorite(true, mUserViewModel);
        }
    }
}
