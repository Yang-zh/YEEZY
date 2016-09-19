package com.fangzhich.yeezy.product.ui;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseFragment;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.presentation.ProductOverviewContract;
import com.fangzhich.yeezy.product.presentation.ProductOverviewPresenter;
import com.fangzhich.yeezy.util.ToastUtil;
import com.fangzhich.yeezy.util.TagFormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * OverView
 * Created by Khorium on 2016/8/31.
 */
public class ProductOverviewFragment extends BaseFragment implements ProductOverviewContract.View {

    ArrayList<String> imageUrls = new ArrayList<>();

    @BindView(R.id.likeText)
    TextView likeText;

    @OnClick(R.id.bt_left)
    void btLeft() {
        ToastUtil.toast("like");
    }

    @BindView(R.id.shareText)
    TextView shareText;

    @OnClick(R.id.bt_right)
    void btRight() {
        ToastUtil.toast("share");
    }

    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.iv_productImage)
    ImageView productImage;

    @BindView(R.id.comment_count)
    TextView commentCount;
    @OnClick(R.id.view_all)
    void viewAllComments() {
        ToastUtil.toast("view all comments");
    }

    @BindView(R.id.sub_rating_bar1)
    RatingBar subRatingBar1;
    @BindView(R.id.comment1)
    TextView comment1;
    @BindView(R.id.name_comment1)
    TextView name1;

    @BindView(R.id.sub_rating_bar2)
    RatingBar subRatingBar2;
    @BindView(R.id.comment2)
    TextView comment2;
    @BindView(R.id.name_comment2)
    TextView name2;

    @BindView(R.id.sub_rating_bar3)
    RatingBar subRatingBar3;
    @BindView(R.id.comment3)
    TextView comment3;
    @BindView(R.id.name_comment3)
    TextView name3;

    @BindView(R.id.tv_size_detail)
    TextView size;
    @OnClick(R.id.tv_arriveTime_view_all)
    void viewAllArriveTime() {
        ((ProductDetailActivity)getActivity()).onTabClick("Shipping");
        ToastUtil.toast("view all arriveTime");
    }

    @BindView(R.id.tv_arriveTime_detail)
    TextView arriveTime;
    @OnClick(R.id.tv_postage_view_all)
    void viewAllPostage() {
        ((ProductDetailActivity)getActivity()).onTabClick("Shipping");
        ToastUtil.toast("view all postage");
    }

    @BindView(R.id.tv_postage_detail)
    TextView postage;

    private int productId;

    @OnClick(R.id.iv_productImage)
    void openPhotoViewPager() {
        Intent intent = new Intent(getActivity(), PhotoViewPagerActivity.class);
        intent.putStringArrayListExtra("imageUrls", imageUrls);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    private ProductOverviewPresenter mPresenter;


    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_overview;
    }

    @Override
    protected void initContentView() {
        productId = getArguments().getInt("product_id");
        setPresenter(new ProductOverviewPresenter(this));
    }

    @Override
    public void setPresenter(ProductOverviewContract.Presenter presenter) {
        mPresenter = (ProductOverviewPresenter) presenter;
    }

    @Override
    protected void loadData() {
        mPresenter.getProductOverview(productId);
    }

    @Override
    public void onGetProductOverviewSuccess(ProductEntity product) {

        Glide.with(getContext())
                .load(product.images.get(0))
                .centerCrop()
                .crossFade()
                .into(productImage);
        imageUrls.clear();
        imageUrls.addAll(product.images);

        likeText.setText(TagFormatUtil.from(getResources().getString(R.string.LikeFormat))
                .with("LikeCount", product.points)
                .format());
        shareText.setText(TagFormatUtil.from(getResources().getString(R.string.ShareFormat))
                .with("ShareCount", getResources().getString(R.string.nulll))
                .format());
        product_name.setText(product.name);
        ratingBar.setNumStars(product.rating);
        commentCount.setText(TagFormatUtil.from(getResources().getString(R.string.BracketsFormat))
                .with("content", product.reviews)
                .format());

        size.setText(getResources().getString(R.string.nulll));
        postage.setText(getResources().getString(R.string.nulll));
    }

    @Override
    public void onGetProductOverviewFailed(Throwable throwable) {
        ToastUtil.toast(throwable.getMessage());
        ToastUtil.logTestError("OnGetProductOverview",throwable.getMessage());
    }
}
