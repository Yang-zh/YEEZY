package com.fangzhich.sneakerlab.product.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.blankj.utilcode.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.cart.ui.PaymentManager;
import com.fangzhich.sneakerlab.main.ui.SupportActivity;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.presentation.ProductDetailContract;
import com.fangzhich.sneakerlab.product.presentation.ProductDetailPresenter;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.user.ui.LoginActivity;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ProductDetailActivity
 * Created by Khorium on 2016/11/11.
 */

public class ProductDetailActivity extends BaseActivity implements ProductDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;


    //-----------------product object---------------
    private ProductEntity mProduct;
    private String productId;
    PaymentManager manager;

    //-----------------banner------------------
    private ArrayList<String> imageUrls = new ArrayList<>();
    @BindView(R.id.product_Image)
    ConvenientBanner<String> banner;

    //-----------------product info-------------
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_price_original)
    TextView productPriceOriginal;
    @BindView(R.id.product_discount)
    TextView productDiscount;
    @BindView(R.id.discount_type)
    TextView discountType;
    @BindView(R.id.product_sale_volume)
    TextView productSaleVolume;
    @BindView(R.id.product_name)
    TextView productName;

    //------------------product detail--------------
    @OnClick(R.id.bt_product_size_chart)
    void onProductSizeChartClick() {
        new CustomDialog().initPopup(this, R.layout.dialog_size_chart, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                LinearLayout linearLayout = (LinearLayout) content.findViewById(R.id.chart_content);

                String[] size_us = getResources().getStringArray(R.array.size_us);
                String[] size_eu = getResources().getStringArray(R.array.size_eu);

                for (int i=0; i<size_us.length; i++) {
                    View innerLinearLayout = View.inflate(ProductDetailActivity.this, R.layout.item_product_chart_size_item,null);
                    ((TextView)innerLinearLayout.findViewById(R.id.size_us)).setText(size_us[i]);
                    ((TextView)innerLinearLayout.findViewById(R.id.size_eu)).setText(size_eu[i]);
                    linearLayout.addView(innerLinearLayout);
                }

                content.findViewById(R.id.bt_continue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDismiss(PopupWindow dialog, View content) {

            }
        }).showPopup(getWindow().getDecorView(),Gravity.BOTTOM);
    }
    @OnClick(R.id.bt_product_shipping)
    void onProductShippingClick() {
        Intent intent = new Intent(this, ProductShippingInfoActivity.class);
        intent.putExtra("mProduct",mProduct);
        startActivity(intent);
    }

    @BindView(R.id.rating_bar)
    SimpleRatingBar ratingBar;
    @BindView(R.id.rating_number)
    TextView ratingNumber;
    @OnClick(R.id.bt_product_rating)
    void onProductRatingClick() {
        Intent intent = new Intent(this, ProductRatingActivity.class);
        intent.putExtra("mProduct",mProduct);
        startActivity(intent);
    }
    @BindView(R.id.rv_product_detail_rating)
    RecyclerView rvProductRating;

    //------------------product description------------
    @BindView(R.id.tv_productDescription)
    WebView productDescription;
    @BindView(R.id.description_layout)
    RelativeLayout descriptionLayout;

    //-------------bottom bar-------------------
    private boolean isLiked = false;
    @BindView(R.id.bt_like)
    ImageView likeIcon;

    @OnClick(R.id.bt_like)
    void likeOrNot() {
        if (!Const.isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (isLiked) {
            UserApi.deleteWish(mProduct.product_id, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    isLiked = false;
                    likeIcon.setImageResource(R.mipmap.like);
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
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error.getMessage());
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
    }
    @BindView(R.id.bt_support)
    ImageView btSupport;

    @OnClick(R.id.bt_support)
    void iNeedSupport() {
        Intent intent = new Intent(this, SupportActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.bt_cart)
    ImageView btCart;

    @OnClick(R.id.bt_cart)
    void openCart() {
        manager.startShoppingCartDialog();
    }

    @OnClick(R.id.bt_buy)
    void buy() {
        startChooseSize(PaymentManager.ChargeType.AddCart);
    }

    @OnClick(R.id.bt_buy_now)
    void buyNow() {
        startChooseSize(PaymentManager.ChargeType.BuyNow);
    }

    private void startChooseSize(PaymentManager.ChargeType type) {
        if (mProduct != null) {
            manager.withProductDetailControl(this).startPayment(type,mProduct,"1");
        }
    }

    //------------------------------------------------------------------------------
    //-----------------methods--------------------------------------------------------
    //------------------------------------------------------------------------------
    @Override
    public int setContentLayout() {
        return R.layout.activity_product_detail;
    }

    private ProductDetailPresenter mPresenter;

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        mPresenter = (ProductDetailPresenter) presenter;
    }

    @Override
    protected void initContentView() {
        setPresenter(new ProductDetailPresenter(this));
        productId = getIntent().getStringExtra("product_id");
        initToolbar();
        initBottomBarAndPopup();

        //dynamic set banner height
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth(this);
        banner.setLayoutParams(layoutParams);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.ProductDetail);
    }

    private void initBottomBarAndPopup() {
        productPriceOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        manager = new PaymentManager(this, getWindow().getDecorView());
    }

    @Override
    protected void loadData() {
        mPresenter.getProductDetail(productId);
    }

    @Override
    public void onGetProductDetailSuccess(ProductEntity product) {
        this.mProduct = product;

        initProductInfo();
    }

    private void initProductInfo() {
        //-------------product info---------------------
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            final ImageView transitionImage = (ImageView) findViewById(R.id.product_Image_for_transition);
//            rx.Observable.timer(2000, TimeUnit.MILLISECONDS)
//                    .asObservable()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Long>() {
//                        @Override
//                        public void call(Long aLong) {
//                            transitionImage.setVisibility(View.GONE);
//                        }
//                    });
//        }
        imageUrls.clear();
        imageUrls.addAll(mProduct.images);
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, imageUrls)
                .setPageIndicator(new int[]{R.mipmap.point_grey, R.mipmap.point_black})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        productName.setText(mProduct.name);
        productPrice.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price", String.valueOf(mProduct.special_price))
                .format());
        if (Double.valueOf(mProduct.special_price) < Double.valueOf(mProduct.original_price)) {
            productPriceOriginal.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                    .with("price", String.valueOf(mProduct.original_price))
                    .format());
        } else {
            productPriceOriginal.setText("");
        }
        if (TextUtils.isEmpty(mProduct.discount) || mProduct.discount.equals("0")) {
            productDiscount.setVisibility(View.GONE);
            discountType.setVisibility(View.GONE);
        } else {
            productDiscount.setText("-"+mProduct.discount);
        }
        productSaleVolume.setText(TagFormatUtil.from(getResources().getString(R.string.SellNumberFormat))
                .with("number", mProduct.sales_volume)
                .format());

        //--------------product detail-----------------
        ratingBar.getAnimationBuilder()
                .setRatingTarget(mProduct.rating)
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator())
                .start();
        ratingNumber.setText(TagFormatUtil.from(getResources().getString(R.string.RatingNumberFormat))
                .with("number", mProduct.reviews)
                .format());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvProductRating.addItemDecoration(new LinearLayoutItemDecoration(this, LinearLayoutItemDecoration.VERTICAL_LIST,R.drawable.background_half_line));
        ReviewListAdapter adapter = new ReviewListAdapter(mProduct.product_id,"2");
        rvProductRating.setLayoutManager(manager);
        rvProductRating.setAdapter(adapter);

        //---------------product description-------------
        if (mProduct.description!=null && mProduct.description.length()>0) {
            descriptionLayout.setVisibility(View.VISIBLE);
            productDescription.setVerticalScrollBarEnabled(false);
            productDescription.loadData("<html><body>" +
                    "<style>" +
                    "img{max-width:100%;height:auto}" +
                    "video{max-width:100%;height:auto}" +
                    "</style>"+
                    Html.fromHtml(mProduct.description)+
                    "</body></html>", "text/html;charset=utf-8", null);
        }
        //-------------bottom bar-----------------
        if (Const.isLogin()) {
            UserApi.getWishList(new SingleSubscriber<ArrayList<WishEntity>>() {
                @Override
                public void onSuccess(ArrayList<WishEntity> value) {
                    for (WishEntity wish : value) {
                        if (wish.product_id.equals(mProduct.product_id)) {
                            Timber.d("liked!");
                            likeIcon.setImageResource(R.mipmap.like_red);
                            isLiked = true;
                            break;
                        }
                    }
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error.getMessage());
                }
            });
        }
//        productPriceBottom.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
//                .with("price", String.valueOf(mProduct.special_price))
//                .format());
    }

    @Override
    public void onGetProductDetailFailed(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeAll();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    private CallbackManager callBackManager;
    private ShareDialog shareDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.share:
                CustomDialog dialog = new CustomDialog();
                dialog.initPopup(this, R.layout.dialog_share, new CustomDialog.Listener() {
                    @Override
                    public void onInit(final PopupWindow dialog, View content) {
                        content.findViewById(R.id.icon_facebook).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Timber.d(String.valueOf(FacebookSdk.isInitialized()));
                                if (!FacebookSdk.isInitialized()) {
                                    ToastUtil.toast("Facebook service is unavailable，please check your internet connection");
                                    return;
                                }
                                callBackManager = CallbackManager.Factory.create();
                                shareDialog = new ShareDialog(ProductDetailActivity.this);
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
//                content.findViewById(R.id.icon_instagram).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtil.toast("share on instagram");
//                        Intent sendIntent = new Intent();
//                        sendIntent.setAction(Intent.ACTION_SEND);
////                        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab");
//                        sendIntent.setType("image/png");
//                        sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
//                        List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(sendIntent, 0);
//                        if (resInfo!= null && resInfo.size()>0) {
//                            for (ResolveInfo info : resInfo) {
//                                if (info.activityInfo.packageName.toLowerCase().contains("instagram") || info.activityInfo.name.toLowerCase().contains("instagram")) {
//                                    sendIntent.setPackage(info.activityInfo.packageName);
//                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.ShareTo)));
//                                    UserApi.share(mProduct.product_id, "", "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab", new SingleSubscriber<Object>() {
//                                        @Override
//                                        public void onSuccess(Object value) {
//
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable error) {
//                                            ToastUtil.toast("Connect to server failed,please");
//                                        }
//                                    });
//                                } else {
//                                    ToastUtil.toast("Didn't found Instagram installed in your phone");
//                                }
//                            }
//                        }
//                    }
//                });
                        content.findViewById(R.id.icon_twitter).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TweetComposer.Builder builder = new TweetComposer.Builder(ProductDetailActivity.this)
                                        .text("https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n" +
                                                " --From SneakLab");
                                builder.show();
                            }
                        });
                        content.findViewById(R.id.icon_share).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab");
                                sendIntent.setType("text/plain");
                                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.ShareTo)));
                                    UserApi.share(mProduct.product_id, "", "https://play.google.com/store/apps/details?id=com.fangzhich.sneakerlab \n --From SneakLab", new SingleSubscriber<Object>() {
                                        @Override
                                        public void onSuccess(Object value) {

                                        }

                                        @Override
                                        public void onError(Throwable error) {
                                            ToastUtil.toast("Connect to server failed,please");
                                        }
                                    });
                                } else {
                                    ToastUtil.toast("no target to share in your phone");
                                }
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
                }).showPopup(getWindow().getDecorView(), Gravity.BOTTOM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
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
            Glide.with(ProductDetailActivity.this)
                    .load(data)
                    .placeholder(R.mipmap.product_image_placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
        }
    }
}
