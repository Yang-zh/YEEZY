package com.fangzhich.yeezy.product.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseFragment;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.fangzhich.yeezy.product.data.net.ProductApi;
import com.fangzhich.yeezy.util.ToastUtil;
import com.fangzhich.yeezy.util.TagFormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;

/**
 * OverView
 * Created by Khorium on 2016/8/31.
 */
public class ProductOverviewFragment extends BaseFragment {

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
        Intent intent = new Intent(getActivity(),ReviewListActivity.class);
        intent.putExtra("product_id",mProduct.product_id);
        startActivity(intent);
    }

    @BindView(R.id.comment1_layout)
    View comment1_layout;
    @BindView(R.id.sub_rating_bar1)
    RatingBar subRatingBar1;
    @BindView(R.id.comment1)
    TextView comment1;
    @BindView(R.id.name_comment1)
    TextView name1;

    @BindView(R.id.comment2_layout)
    View comment2_layout;
    @BindView(R.id.sub_rating_bar2)
    RatingBar subRatingBar2;
    @BindView(R.id.comment2)
    TextView comment2;
    @BindView(R.id.name_comment2)
    TextView name2;

    @BindView(R.id.comment3_layout)
    View comment3_layout;
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

    private ProductEntity mProduct;

    @OnClick(R.id.iv_productImage)
    void openPhotoViewPager() {
        Intent intent = new Intent(getActivity(), PhotoViewPagerActivity.class);
        intent.putStringArrayListExtra("imageUrls", imageUrls);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }


    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_overview;
    }

    @Override
    protected void initContentView() {
        mProduct = getArguments().getParcelable("mProduct");
    }

    @Override
    protected void loadData() {
        ProductApi.getReviews(0, 3, mProduct.product_id, new SingleSubscriber<ArrayList<ReviewEntity>>() {
            @Override
            public void onSuccess(ArrayList<ReviewEntity> value) {
                if (value.size()==0) {
                    return;
                }
                if (value.size()>=1) {
                    ReviewEntity review = value.get(0);
                    comment1_layout.setVisibility(View.VISIBLE);
                    comment1.setText(review.text);
                    subRatingBar1.setNumStars(review.rating);
                    name1.setText(review.author);
                }
                if (value.size()>=2) {
                    ReviewEntity review = value.get(1);
                    comment2_layout.setVisibility(View.VISIBLE);
                    comment2.setText(review.text);
                    subRatingBar2.setNumStars(review.rating);
                    name2.setText(review.author);
                }
                if (value.size()>=3) {
                    ReviewEntity review = value.get(2);
                    comment3_layout.setVisibility(View.VISIBLE);
                    comment3.setText(review.text);
                    subRatingBar3.setNumStars(review.rating);
                    name3.setText(review.author);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        Glide.with(getContext())
                .load(mProduct.images.get(0))
                .fitCenter()
                .crossFade()
                .into(productImage);
        imageUrls.clear();
        imageUrls.addAll(mProduct.images);

        likeText.setText(TagFormatUtil.from(getResources().getString(R.string.LikeFormat))
                .with("LikeCount", mProduct.points)
                .format());
        shareText.setText(TagFormatUtil.from(getResources().getString(R.string.ShareFormat))
                .with("ShareCount", getResources().getString(R.string.nulll))
                .format());
        product_name.setText(mProduct.name);
        ratingBar.setNumStars(mProduct.rating);
        commentCount.setText(TagFormatUtil.from(getResources().getString(R.string.BracketsFormat))
                .with("content", mProduct.reviews)
                .format());

        size.setText(getResources().getString(R.string.nulll));
        postage.setText(getResources().getString(R.string.nulll));
    }
}
