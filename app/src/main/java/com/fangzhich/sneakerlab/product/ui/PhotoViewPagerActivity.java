package com.fangzhich.sneakerlab.product.ui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.product.widget.PhotoViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * PhotoViewPager
 * Created by Khorium on 2016/9/5.
 */
public class PhotoViewPagerActivity extends BaseActivity {

    PhotoAdapter adapter = new PhotoAdapter();
    ArrayList<String> imageUrls = new ArrayList<>();

    @BindView(R.id.viewpager_productImage)
    PhotoViewPager viewPager;
    @Override
    public int setContentLayout() {
        return R.layout.activity_photo_view_pager;
    }

    @Override
    protected void initContentView() {
        adapter = new PhotoAdapter();
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        adapter.setData(imageUrls);
        adapter.notifyDataSetChanged();
    }

    private class PhotoAdapter extends PagerAdapter {

        private ArrayList<String> imageUrls = new ArrayList<>();

        public void setData(ArrayList<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView itemView = (ImageView) View.inflate(container.getContext(),R.layout.item_photo_view_pager,null);
            Glide.with(itemView.getContext())
                    .load(imageUrls.get(position))
                    .fitCenter()
                    .crossFade()
                    .into(itemView);
//            itemView.setImageResource(R.mipmap.product_image_placeholder);
            new PhotoViewAttacher(itemView).setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    onBackPressed();
                }
            });
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
