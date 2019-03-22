

package com.nfcmeeting.nfcmeeting.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nfcmeeting.nfcmeeting.R;
import com.nfcmeeting.nfcmeeting.common.GlideApp;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.ui.Adapter.base.BaseAdapter;
import com.nfcmeeting.nfcmeeting.ui.Adapter.base.BaseViewHolder;
import com.nfcmeeting.nfcmeeting.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.nfcmeeting.nfcmeeting.util.PrefUtils;
import com.nfcmeeting.nfcmeeting.util.StringUtils;
import com.nfcmeeting.nfcmeeting.util.ViewUtils;


public class RepositoriesAdapter extends BaseAdapter<RepositoriesAdapter.ViewHolder, Repository> {

    @Inject
    public RepositoriesAdapter(Context context, BaseFragment fragment){
        super(context, fragment);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.layout_item_repository;
    }

    @NonNull
    @Override
    protected ViewHolder getViewHolder(@NonNull View itemView, int viewType) {
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_user_avatar) ImageView ivUserAvatar;
        @BindView(R.id.language_color) ImageView languageColor;
        @BindView(R.id.tv_repo_name) TextView tvRepoName;
        @BindView(R.id.tv_language) TextView tvLanguage;
        @BindView(R.id.tv_repo_description) TextView tvRepoDescription;
        @BindView(R.id.tv_star_num) TextView tvStarNum;
        @BindView(R.id.tv_fork_num) TextView tvForkNum;
        @BindView(R.id.tv_owner_name) TextView tvOwnerName;
        @BindView(R.id.tv_since_star_num) TextView tvSinceStarNum;
        @BindView(R.id.since_star_lay) LinearLayout sinceStarLay;
        @BindView(R.id.owner_lay) LinearLayout ownerLay;
        @BindView(R.id.fork_mark) View forkMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

//        @OnClick(R.id.iv_user_avatar)
//        public void onUserClick(){
//            if(getAdapterPosition() != RecyclerView.NO_POSITION){
//                ProfileActivity.show((Activity) context, ivUserAvatar, data.get(getAdapterPosition()).getOwner().getLogin(),
//                        data.get(getAdapterPosition()).getOwner().getAvatarUrl());
//            }
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Repository repository = data.get(position);
        boolean hasOwnerAvatar = !StringUtils.isBlank(repository.getModeratorName());
        holder.tvRepoName.setText(repository.getTitle());
        ViewUtils.setTextView(holder.tvRepoDescription, repository.getContent());
        holder.tvStarNum.setText(String.valueOf(repository.getBeginTime()));
        holder.tvForkNum.setText(String.valueOf(repository.getEndTime()));
        holder.tvOwnerName.setText(repository.getModeratorName());

//        if(StringUtils.isBlank(repository.getLanguage())){
//            holder.tvLanguage.setText("");
//            holder.languageColor.setVisibility(View.INVISIBLE);
//        } else {
//            holder.languageColor.setVisibility(View.VISIBLE);
//            holder.tvLanguage.setText(repository.getLanguage());
//            int languageColor = LanguageColorsHelper.INSTANCE.getColor(context, repository.getLanguage());
//            holder.languageColor.setImageTintList(ColorStateList.valueOf(languageColor));
//        }


        if(hasOwnerAvatar){
            holder.ivUserAvatar.setVisibility(View.VISIBLE);
            holder.ownerLay.setVisibility(View.VISIBLE);
            holder.sinceStarLay.setVisibility(View.GONE);
            GlideApp.with(fragment)
                    .load(repository.getModeratorAvatar())
                    .onlyRetrieveFromCache(!PrefUtils.isLoadImageEnable())
                    .into(holder.ivUserAvatar);
        }

    }
}
