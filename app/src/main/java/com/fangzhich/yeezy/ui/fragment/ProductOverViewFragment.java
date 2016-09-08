package com.fangzhich.yeezy.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.ui.activity.PhotoViewPagerActivity;
import com.fangzhich.yeezy.util.LogUtils;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * OverView
 * Created by Khorium on 2016/8/31.
 */
public class ProductOverViewFragment extends BaseFragment {

    ArrayList<String> imageUrls = new ArrayList<>();

    @BindView(R.id.iv_productImage)
    ImageView productImage;

    @OnClick(R.id.iv_productImage)
    void openPhotoViewPager() {
        Intent intent = new Intent(getActivity(), PhotoViewPagerActivity.class);
        intent.putStringArrayListExtra("imageUrls", imageUrls);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    @BindView(R.id.likeText)
    TextView likeText;

    @OnClick(R.id.bt_left)
    void btLeft() {
        LogUtils.getInstance().toastInfo("like");
    }

    @BindView(R.id.shareText)
    TextView shareText;

    @OnClick(R.id.bt_right)
    void btRight() {
        LogUtils.getInstance().toastInfo("share");
    }

    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.comment_count)
    TextView commentCount;


    @BindView(R.id.recent_comments)
    TextView recentComments;
    @BindView(R.id.tv_arriveTime)
    TextView arriveTime;
    @BindView(R.id.tv_arriveTime_detail)
    TextView arriveTimeDetail;
    @BindView(R.id.tv_postage)
    TextView postage;
    @BindView(R.id.tv_postage_detail)
    TextView postageDetail;


    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_overview;
    }

    @Override
    protected void initContentView() {
    }

    @Override
    protected void loadData() {
        productImage.setImageResource(R.mipmap.product_image_placeholder);
        imageUrls.clear();
        for (int i = 0; i < 5; i++) {
            imageUrls.add("product_image_placeholder");
        }

        likeText.setText("Like(57)");
        shareText.setText("Share(162)");

        product_name.setText("Adidas NEO Flyer Shoes Red Men");
        commentCount.setText("(543)");
    }
}
