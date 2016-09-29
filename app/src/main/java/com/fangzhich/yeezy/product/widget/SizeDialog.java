package com.fangzhich.yeezy.product.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.yeezy.base.widget.DialogManager;
import com.fangzhich.yeezy.base.widget.NumberView;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.user.ui.LoginActivity;
import com.fangzhich.yeezy.util.Const;
import com.fangzhich.yeezy.util.TagFormatUtil;
import com.fangzhich.yeezy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ren.qinc.numberbutton.NumberButton;

/**
 * SizeDialog
 * Created by Khorium on 2016/9/29.
 */

public class SizeDialog {


    private List<ProductEntity.Option.ProductOptionValueBean> sizes;
    private List<ProductEntity.Option.ProductOptionValueBean> colors;


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
    @BindView(R.id.tv_productSize)
    TextView tvProductSize;
    @BindView(R.id.rv_color)
    RecyclerView rvColor;
    BaseRecyclerViewAdapter colorAdapter;

    @BindView(R.id.rv_size)
    RecyclerView rvSize;
    BaseRecyclerViewAdapter sizeAdapter;
    String[] chosenOptions = new String[2];

    @BindView(R.id.quantity_view)
    NumberView numberButton;

    @OnClick(R.id.bt_buy)
    void buy() {
        if (Const.isLogin()) {
            manager.startShoppingCartDialog(String.valueOf(product.product_id), String.valueOf(quantity), option, "0");
        } else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    private ProductEntity product;
    ArrayList<Integer> option = new ArrayList<>();

    private PopupWindow mPopupWindow;

    private Context mContext;
    private View mContentView;
    View mPopupContent;

    DialogManager manager;

    int quantity = 1;

    public SizeDialog initPopup(DialogManager manager, Context context) {
        mContext = context;
        this.manager = manager;
        mPopupContent = View.inflate(context, R.layout.dialog_choose_size, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
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

    public SizeDialog withProductDetail(ProductEntity product) {
        this.product = product;
        initRecyclerView();
        return this;
    }

    private void initRecyclerView() {
        tvProductPrice.setText(TagFormatUtil
                .from(mContext.getResources().getString(R.string.ProductPriceFormat))
                .with("number",product.price)
                .format());
        tvProductInventory.setText(TagFormatUtil
                .from(mContext.getResources().getString(R.string.ProductInventoryFormat))
                .with("number",product.quantity)
                .format());
        tvProductSize.setText(TagFormatUtil
                .from(mContext.getResources().getString(R.string.ProductOptionFormat))
                .with("content","")
                .format());
        Glide.with(mContext)
                .load(product.images==null?null:product.images.get(0))
                .fitCenter()
                .crossFade()
                .into(productImage);

        for (ProductEntity.Option option:product.options) {
            switch (option.product_option_id) {
                case "229": {
                    sizes = option.product_option_value;
                    break;
                }
                case "230": {
                    colors = option.product_option_value;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        if (colors!=null && colors.size()!=0) {
            rvColor.setLayoutManager(new GridLayoutManager(mContext,2,GridLayoutManager.HORIZONTAL,false));
            colorAdapter = new BaseRecyclerViewAdapter<ProductEntity.Option.ProductOptionValueBean,RecyclerView.ViewHolder>() {
                @Override
                public void loadData() {
                    mData = colors;
                }

                @Override
                public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                    View itemView = View.inflate(parent.getContext(),R.layout.item_option,null);
                    return new RecyclerView.ViewHolder(itemView) {};
                }

                @Override
                protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
                    View itemView = holder.itemView;
                    ((TextView)itemView.findViewById(R.id.text)).setText(mData.get(position).name);
                    CardView card = ((CardView)itemView.findViewById(R.id.cardItem));
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chosenOptions[0] = mData.get(position).name;
                            option.add(Integer.valueOf(mData.get(position).product_option_value_id));
                            tvProductSize.setText(TagFormatUtil
                                    .from(mContext.getResources().getString(R.string.ProductOptionFormat))
                                    .with("content",(chosenOptions[1]!=null&&chosenOptions[1].length()!=0)?("\""+chosenOptions[0]+"\",\""+chosenOptions[1]+"\""):("\""+chosenOptions[0]+"\""))
                                    .format());
                        }
                    });
                }
            };
            rvColor.setAdapter(colorAdapter);
            colorAdapter.loadData();
        } else {
            rvColor.setVisibility(View.INVISIBLE);
        }

        if (sizes!=null && sizes.size()!=0) {
            rvSize.setLayoutManager(new GridLayoutManager(mContext,2,GridLayoutManager.HORIZONTAL,false));
            sizeAdapter = new BaseRecyclerViewAdapter<ProductEntity.Option.ProductOptionValueBean,RecyclerView.ViewHolder>() {
                @Override
                public void loadData() {
                    mData = sizes;
                }

                @Override
                public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                    View itemView = View.inflate(parent.getContext(),R.layout.item_option,null);
                    return new RecyclerView.ViewHolder(itemView) {};
                }

                @Override
                protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
                    View itemView = holder.itemView;
                    ((TextView)itemView.findViewById(R.id.text)).setText(mData.get(position).name);
                    CardView card = ((CardView)itemView.findViewById(R.id.cardItem));
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chosenOptions[1] = mData.get(position).name;
                            option.add(Integer.valueOf(mData.get(position).product_option_value_id));
                            tvProductSize.setText(TagFormatUtil
                                    .from(mContext.getResources().getString(R.string.ProductOptionFormat))
                                    .with("content",(chosenOptions[0]!=null&&chosenOptions[0].length()!=0)?("\""+chosenOptions[0]+"\",\""+chosenOptions[1]+"\""):("\""+chosenOptions[1]+"\""))
                                    .format());
                        }
                    });
                }
            };
            rvSize.setAdapter(sizeAdapter);
            sizeAdapter.loadData();
        } else {
            rvSize.setVisibility(View.INVISIBLE);
        }
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

    public View getContentView() {
        return mPopupContent;
    }
}
