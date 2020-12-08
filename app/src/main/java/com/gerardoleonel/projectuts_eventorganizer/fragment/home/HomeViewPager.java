package com.gerardoleonel.projectuts_eventorganizer.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.CarouselItemBinding;

import java.util.List;

public class HomeViewPager extends PagerAdapter {

    private List<CarouselItem> items;
    private Context context;

    public HomeViewPager(List<CarouselItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CarouselItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.carousel_item, container, false);
        CarouselItem item = items.get(position);
        itemBinding.setCarouselItem(item);
        container.addView(itemBinding.getRoot());
        return itemBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
