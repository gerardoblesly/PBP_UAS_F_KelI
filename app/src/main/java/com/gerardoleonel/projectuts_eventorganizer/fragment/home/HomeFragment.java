package com.gerardoleonel.projectuts_eventorganizer.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.gerardoleonel.projectuts_eventorganizer.HomeActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.FragmentHomeBinding;
import com.gerardoleonel.projectuts_eventorganizer.events.CreateEvent;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.content.Location;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.content.LocationAdapter;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.content.Package;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.content.PackageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewPager pagerAdapter;
    private int currentPage = 0;
    private final int NUM_PAGES = 3;
    boolean isFirstTime = true;
    private Handler handler = new Handler();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_home, container, false);
        initPageAdapter();
        contentHome();
        
        binding.btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateEvent.class));
                
            }
        });
        
        return binding.getRoot();
    }

    //https://stackoverflow.com/questions/38472916/how-to-swipe-viewpager-images-automatically-using-timetask
    private void initPageAdapter() {
        List<CarouselItem> listCarousel = getCarouselItems();
        pagerAdapter = new HomeViewPager(listCarousel, getActivity());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.indicator.setViewPager(binding.viewPager);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(currentPage == NUM_PAGES) currentPage = 0;
                binding.viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
                isFirstTime = false;
            }
        }, 700, 4000);
    }

    private List<CarouselItem> getCarouselItems() {
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.carousel_p1, getActivity().getTheme()), "Capture every precious moment with us, Filography"));
        list.add(new CarouselItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.carousel_p2, getActivity().getTheme()), "Dont miss your beautiful moments, make sure you snap it!"));
        list.add(new CarouselItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.carousel_p3, getActivity().getTheme()), "Photography is a way of feeling, of touching, of loving"));
        return list;
    }

    private void contentHome()
    {

        ArrayList<Package> packageList = new ArrayList<>();
        ArrayList<Location> locationList = new ArrayList<>();

        packageList.add(new Package(R.drawable.newborn,"New Born Package","Cutest Quality"));
        packageList.add(new Package(R.drawable.familyportrait,"Family Portrait","Fam(ILY)"));
        packageList.add(new Package(R.drawable.intimatewedding,"Intimate Wedding","Unforgetable Moment"));
        packageList.add(new Package(R.drawable.bridalshower,"Bridal Shower","Beautiful Matter"));

        locationList.add(new Location(R.drawable.penglipuran,"Penglipuran","BALI"));
        locationList.add(new Location(R.drawable.keraton,"Keratonan","YOGYAKARTA"));
        locationList.add(new Location(R.drawable.kotatua,"Kota Tua","JAKARTA"));
        locationList.add(new Location(R.drawable.rajaampat,"Raja Ampat","PAPUA"));

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.packageRv.setLayoutManager(manager1);

        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.locationRv.setLayoutManager(manager2);

        PackageAdapter adaptor1 = new PackageAdapter(getActivity(), packageList);
        binding.packageRv.setAdapter(adaptor1);

        LocationAdapter adaptor2 = new LocationAdapter(getActivity(), locationList);
        binding.locationRv.setAdapter(adaptor2);
    }
}
