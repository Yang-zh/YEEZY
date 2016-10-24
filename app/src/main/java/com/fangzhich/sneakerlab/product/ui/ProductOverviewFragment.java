package com.fangzhich.sneakerlab.product.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseFragment;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.data.entity.ReviewEntity;
import com.fangzhich.sneakerlab.product.data.net.ProductApi;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.user.ui.LoginActivity;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * OverView
 * Created by Khorium on 2016/8/31.
 */
public class ProductOverviewFragment extends BaseFragment {

    ArrayList<String> imageUrls = new ArrayList<>();

    @BindView(R.id.likeIcon)
    ImageView likeIcon;
    @BindView(R.id.likeText)
    TextView likeText;

    private boolean isLiked = false;
    private CallbackManager callBackManager;
    private ShareDialog shareDialog;
    private int oldLikeCount;

    @OnClick(R.id.bt_left)
    void btLeft() {
        if (!Const.isLogin()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        if (isLiked) {
            UserApi.deleteWish(mProduct.product_id, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    isLiked = false;
                    likeIcon.setImageResource(R.mipmap.like);
                    likeText.setText(TagFormatUtil.from(getResources().getString(R.string.LikeFormat))
                            .with("LikeCount", oldLikeCount-1)
                            .format());
                    oldLikeCount = oldLikeCount-1;
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error.getMessage());
                    ToastUtil.toast(error.getMessage());
                }
            });
        } else {
            UserApi.addWish(mProduct.product_id, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    isLiked = true;
                    likeIcon.setImageResource(R.mipmap.like_red);
                    likeText.setText(TagFormatUtil.from(getResources().getString(R.string.LikeFormat))
                            .with("LikeCount", oldLikeCount+1)
                            .format());
                    oldLikeCount = oldLikeCount+1;
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error.getMessage());
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
    }
 
    @BindView(R.id.shareText)
    TextView shareText;

    @OnClick(R.id.bt_right)
    void btRight() {
        CustomDialog dialog = new CustomDialog();
        dialog.initPopup(getContext(), R.layout.dialog_share, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                content.findViewById(R.id.icon_facebook).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackManager = CallbackManager.Factory.create();
                        shareDialog = new ShareDialog(getActivity());
                        shareDialog.registerCallback(callBackManager, new FacebookCallback<Sharer.Result>() {
                            @Override
                            public void onSuccess(Sharer.Result result) {
                                UserApi.share(mProduct.product_id, "facebook", "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab", new SingleSubscriber<Object>() {
                                    @Override
                                    public void onSuccess(Object value) {

                                    }

                                    @Override
                                    public void onError(Throwable error) {
                                        Timber.d(error.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError(FacebookException error) {

                            }
                        });
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent content = new ShareLinkContent.Builder()
                                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab"))
                                    .setShareHashtag(new ShareHashtag.Builder()
                                            .setHashtag("#Sneaker")
                                            .build())
                                    .build();

                            shareDialog.show(content);
                        }

                    }
                });
                content.findViewById(R.id.icon_instagram).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.toast("share on instagram");
                        // todo ins share
                    }
                });
                content.findViewById(R.id.icon_twitter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.toast("share on twitter");
                        // todo twitter share
                    }
                });
                content.findViewById(R.id.icon_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab");
                        sendIntent.setType("text/plain");
                        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.ShareTo)));
                        } else {
                            ToastUtil.toast("no target to share in your phone");
                        }
                        UserApi.share(mProduct.product_id, "", "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab", new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {

                            }

                            @Override
                            public void onError(Throwable error) {
                                ToastUtil.toast("Connect to server failed,please");
                            }
                        });
                    }
                });
                content.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDismiss(PopupWindow dialog, View content) {

            }
        }).showPopup(getActivity().getWindow().getDecorView(), Gravity.BOTTOM);
    }

    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.rating_bar)
    SimpleRatingBar ratingBar;

    @BindView(R.id.comment_count)
    TextView commentCount;
    @OnClick(R.id.view_all)
    void viewAllComments() {
        ((ProductDetailActivity)getActivity()).onTabClick("Rating");
    }

    @BindView(R.id.comment1_layout)
    View comment1_layout;
    @BindView(R.id.sub_rating_bar1)
    SimpleRatingBar subRatingBar1;
    @BindView(R.id.comment1)
    TextView comment1;
    @BindView(R.id.name_comment1)
    TextView name1;

    @BindView(R.id.comment2_layout)
    View comment2_layout;
    @BindView(R.id.sub_rating_bar2)
    SimpleRatingBar subRatingBar2;
    @BindView(R.id.comment2)
    TextView comment2;
    @BindView(R.id.name_comment2)
    TextView name2;

    @BindView(R.id.comment3_layout)
    View comment3_layout;
    @BindView(R.id.sub_rating_bar3)
    SimpleRatingBar subRatingBar3;
    @BindView(R.id.comment3)
    TextView comment3;
    @BindView(R.id.name_comment3)
    TextView name3;

    @BindView(R.id.tv_size_detail)
    TextView size;
    @OnClick(R.id.tv_arriveTime_view_all)
    void viewAllArriveTime() {
        ((ProductDetailActivity)getActivity()).onTabClick("Shipping");
    }

    @BindView(R.id.tv_arriveTime_detail)
    TextView arriveTime;
    @OnClick(R.id.tv_postage_view_all)
    void viewAllPostage() {
        ((ProductDetailActivity)getActivity()).onTabClick("Shipping");
    }

    @BindView(R.id.tv_postage_detail)
    TextView postage;

    private ProductEntity mProduct;

    @BindView(R.id.iv_productImage)
    ConvenientBanner<String> banner;


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
        ProductApi.getReviews("0", "3", mProduct.product_id, new SingleSubscriber<ArrayList<ReviewEntity>>() {
            @Override
            public void onSuccess(ArrayList<ReviewEntity> value) {
                if (value.size()==0) {
                    return;
                }
                if (value.size()>=1) {
                    ReviewEntity review = value.get(0);
                    comment1_layout.setVisibility(View.VISIBLE);
                    comment1.setText(review.text);
                    subRatingBar1.getAnimationBuilder()
                            .setRatingTarget(review.rating)
                            .setDuration(1000)
                            .setRepeatCount(0)
                            .setInterpolator(new LinearInterpolator())
                            .start();
                    name1.setText(review.author);
                }
                if (value.size()>=2) {
                    ReviewEntity review = value.get(1);
                    comment2_layout.setVisibility(View.VISIBLE);
                    comment2.setText(review.text);
                    subRatingBar2.getAnimationBuilder()
                            .setRatingTarget(review.rating)
                            .setDuration(1000)
                            .setRepeatCount(0)
                            .setInterpolator(new LinearInterpolator())
                            .start();
                    name2.setText(review.author);
                }
                if (value.size()>=3) {
                    ReviewEntity review = value.get(2);
                    comment3_layout.setVisibility(View.VISIBLE);
                    comment3.setText(review.text);
                    subRatingBar3.getAnimationBuilder()
                            .setRatingTarget(review.rating)
                            .setDuration(1000)
                            .setRepeatCount(0)
                            .setInterpolator(new LinearInterpolator())
                            .start();
                    name3.setText(review.author);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });

        imageUrls.clear();
        imageUrls.addAll(mProduct.images);

        UserApi.getWishList(new SingleSubscriber<ArrayList<WishEntity>>() {
            @Override
            public void onSuccess(ArrayList<WishEntity> value) {
                for (WishEntity wish : value) {
                    if (wish.product_id.equals(mProduct.product_id)) {
                        Timber.d("liked!");
                        likeIcon.setImageResource(R.mipmap.like_red);
                        isLiked = true;
                    }
                }
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.getMessage());
            }
        });
        likeText.setText(Integer.valueOf(mProduct.collections)==0?"Like":TagFormatUtil.from(getResources().getString(R.string.LikeFormat))
                .with("LikeCount", mProduct.collections)
                .format());
        oldLikeCount = Integer.parseInt(mProduct.collections);
        shareText.setText(Integer.valueOf(mProduct.shares)==0?"Share":TagFormatUtil.from(getResources().getString(R.string.ShareFormat))
                .with("ShareCount", mProduct.shares)
                .format());
        product_name.setText(mProduct.name);
        ratingBar.getAnimationBuilder()
                .setRatingTarget(mProduct.rating)
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator())
                .start();
        commentCount.setText(TagFormatUtil.from(getResources().getString(R.string.BracketsFormat))
                .with("content", mProduct.reviews)
                .format());

        List<ProductEntity.Option.ProductOption> sizes = new ArrayList<>();

        for (ProductEntity.Option option:mProduct.options) {
            switch (option.name) {
                case "Size": {
                    sizes = option.product_option_value;
                    break;
                }
                case "size": {
                    sizes = option.product_option_value;
                    break;
                }
                case "Color": {
                    break;
                }
                case "color": {
                    break;
                }
                default: {
                    break;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        if (sizes!=null) {
            for (ProductEntity.Option.ProductOption option:sizes) {
                builder.append(option.name).append("/");
            }

        }
        if (builder.length()>=1) {
            builder.delete(builder.length()-1,builder.length());
        }
        size.setText(builder.toString());
        arriveTime.setText(mProduct.shipping_info.EstimatedArrival);
        postage.setText(mProduct.shipping_info.EstimatedShipping);

        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, imageUrls);
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            Glide.with(getContext())
                    .load(data)
                    .placeholder(R.mipmap.product_image_placeholder)
                    .fitCenter()
                    .crossFade()
                    .into(imageView);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode,resultCode,data);
    }
}
