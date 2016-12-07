package com.fangzhich.sneakerlab.product.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.cart.ui.PaymentManager;
import com.fangzhich.sneakerlab.base.widget.NumberView;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.user.ui.LoginActivity;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * SizeDialog
 * Created by Khorium on 2016/9/29.
 */

public class SizeDialog {

    enum OptionType{
        Color,Size
    }

    private List<ProductEntity.Option.ProductOption> sizes;
    private List<ProductEntity.Option.ProductOption> colors;
    private ProductEntity.Option sizeOption;
    private ProductEntity.Option colorOption;


    @OnClick(R.id.bt_close)
    void close() {
        mPopupWindow.dismiss();
    }

    @BindView(R.id.product_image)
    ImageView productImage;
    @BindView(R.id.tv_productPrice)
    TextView tvProductPrice;
    @BindView(R.id.tv_productInventory)
    TextView tvProductInventory;
    @BindView(R.id.tv_productInventoryDetail)
    TextView tvProductInventoryDetail;
    @BindView(R.id.tv_productSize)
    TextView tvProductSize;
    @BindView(R.id.tv_productSizeDetail)
    TextView tvProductSizeDetail;


    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.line_second)
    View lineSecond;
    @BindView(R.id.rv_color)
    RecyclerView rvColor;
    private OptionAdapter colorAdapter;

    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.line_third)
    View lineThird;
    @BindView(R.id.rv_size)
    RecyclerView rvSize;
    private OptionAdapter sizeAdapter;
    private String[] chosenOptions = new String[2];

    @BindView(R.id.quantity_view)
    NumberView numberButton;

    @BindView(R.id.bt_buy)
    CardView btBuy;
    @OnClick(R.id.bt_buy)
    void addCart() {
        if (!Const.isLogin()) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            if (isChosenOption()) {
                dismiss();
                manager.startShoppingCartDialog(String.valueOf(product.product_id), String.valueOf(quantity), option, "0");
            } else {
                ToastUtil.toast("Please choose product option you need");
            }
        }
    }
    @BindView(R.id.bt_buy_now)
    CardView btBuyNow;
    @OnClick(R.id.bt_buy_now)
    void buyNow() {
        if (!Const.isLogin()) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            if (isChosenOption()) {
                dismiss();
                manager.startCheckOut(mContext,String.valueOf(product.product_id), String.valueOf(quantity), option, "0");
            } else {
                ToastUtil.toast("Please choose product option you need");
            }
        }
    }

    private boolean colorNeed = false;
    private boolean sizeNeed = false;

    private boolean isChosenOption() {
        if (option.size()<=0) {
            return false;
        }
        if (colorNeed && sizeNeed && option.size()<2) {
            return false;
        } else if (option.size()<1) {
            return false;
        }
        return true;
    }

    private ProductEntity product;
    private HashMap<String,String> option = new HashMap<>();

    private PopupWindow mPopupWindow;

    private Context mContext;
    private View mContentView;
    private View mPopupContent;

    private PaymentManager manager;

    private int quantity = 1;

    public SizeDialog initPopup(PaymentManager manager, Context context) {

        mContext = context;
        this.manager = manager;
        mPopupContent = View.inflate(context, R.layout.dialog_choose_size, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.Dialog);

        numberButton.setGoods_storage(99);
        numberButton.setOnAmountChangeListener(new NumberView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                quantity = amount;
            }
        });

        return this;
    }

    public SizeDialog withProductDetail(ProductEntity product, PaymentManager.ChargeType type, String quantity) {
        this.product = product;
        dynamicSetView(type, quantity);
        initRecyclerView();
        return this;
    }

    private void dynamicSetView(PaymentManager.ChargeType type, String quantity) {
        numberButton.setAmount(Integer.parseInt(quantity));
        switch (type) {
            case AddCart:
                btBuy.setVisibility(View.VISIBLE);
                btBuyNow.setVisibility(View.GONE);
                break;
            case BuyNow:
                btBuy.setVisibility(View.GONE);
                btBuyNow.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initRecyclerView() {
        tvProductPrice.setText(TagFormatUtil
                .from(mContext.getResources().getString(R.string.ProductPriceFormat))
                .with("number", product.special_price)
                .format());
        tvProductInventoryDetail.setText(product.sales_volume);
        tvProductSizeDetail.setText("");

        Glide.with(mContext)
                .load(product.images == null ? null : product.images.get(0))
                .placeholder(R.mipmap.product_image_placeholder)
                .fitCenter()
                .crossFade()
                .into(productImage);

        for (ProductEntity.Option option : product.options) {
            switch (option.name) {
                case "Size": {
                    sizeOption = option;
                    sizes = option.product_option_value;
                    break;
                }
                case "size": {
                    sizeOption = option;
                    sizes = option.product_option_value;
                    break;
                }
                case "Color": {
                    colorOption = option;
                    colors = option.product_option_value;
                    break;
                }
                case "color": {
                    colorOption = option;
                    colors = option.product_option_value;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        if (colors != null && colors.size() != 0) {
            colorNeed = true;
            rvColor.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false));
            colorAdapter = new OptionAdapter(colors,OptionType.Color);
            colorAdapter.setOnOptionChooseListener(new OptionAdapter.OnOptionChooseListener() {
                @Override
                public void onOptionChoose(OptionType type, int position, boolean isChoose) {
                    chooseOption(type,position,isChoose);
                }
            });
            rvColor.setAdapter(colorAdapter);
            colorAdapter.loadData();
        } else {
            colorNeed = false;
            rvColor.setVisibility(View.GONE);
            tvColor.setVisibility(View.GONE);
            lineSecond.setVisibility(View.GONE);
        }

        if (sizes != null && sizes.size() != 0) {
            sizeNeed = true;
            rvSize.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false));
            sizeAdapter = new OptionAdapter(sizes,OptionType.Size);
            sizeAdapter.setOnOptionChooseListener(new OptionAdapter.OnOptionChooseListener() {
                @Override
                public void onOptionChoose(OptionType type, int position, boolean isChoose) {
                    chooseOption(type,position,isChoose);
                }
            });
            rvSize.setAdapter(sizeAdapter);
            sizeAdapter.loadData();
        } else {
            sizeNeed = false;
            rvSize.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);
            lineThird.setVisibility(View.GONE);
        }
    }

    private void chooseOption(OptionType type, int position, boolean isChoose) {
        switch (type) {
            case Color:
                if (isChoose) {
                    chosenOptions[0] = colors.get(position).name;
                    option.put(colorOption.product_option_id,colors.get(position).product_option_value_id);
                }else {
                    chosenOptions[0] = "";
                    option.remove(colorOption.product_option_id);
                }
                break;
            case Size:
                if (isChoose) {
                    chosenOptions[1] = sizes.get(position).name;
                    option.put(sizeOption.product_option_id,sizes.get(position).product_option_value_id);
                }else {
                    chosenOptions[1] = "";
                    option.remove(sizeOption.product_option_id);
                }
                break;
        }
        setChosenText();
    }

    private void setChosenText() {
        StringBuilder builder = new StringBuilder();
        for (String chosenOption:chosenOptions) {
            if (!TextUtils.isEmpty(chosenOption)) {
                builder.append("\"").append(chosenOption).append("\",");
            }
        }
        if (builder.length()>0) {
            builder.deleteCharAt(builder.length()-1);
        }
        tvProductSizeDetail.setText(builder);
    }

    public void showPopup(View contentView) {
        mContentView = contentView;
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public void hide() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.GONE);
        }
    }

    public void show() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.VISIBLE);
        }
    }

    public View getContentView() {
        return mPopupContent;
    }
}
