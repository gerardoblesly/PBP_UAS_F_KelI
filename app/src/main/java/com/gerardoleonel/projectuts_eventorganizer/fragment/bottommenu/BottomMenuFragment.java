package com.gerardoleonel.projectuts_eventorganizer.fragment.bottommenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.gerardoleonel.projectuts_eventorganizer.HomeMenuItem;
import com.gerardoleonel.projectuts_eventorganizer.IMenuClickHandler;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.FragmentBottomMenuBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomMenuFragment extends BottomSheetDialogFragment {

    private FragmentBottomMenuBinding binding;
    private BottomMenuAdapter adapter;
    private List<HomeMenuItem> homeMenuItems = new ArrayList<>();
    private IMenuClickHandler clickHandler;

    public BottomMenuFragment(IMenuClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    //https://medium.com/@kosta.palash/using-bottomsheetdialogfragment-with-material-design-guideline-f9814c39b9fc
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_bottom_menu, container, false);
        generateMenu();
        initAdapter();
        return binding.getRoot();
    }

    private void initAdapter() {
        IBottomMenuAction iBottomMenuAction = new IBottomMenuAction() {
            @Override
            public void closeBottomMenu() {
                dismiss();
            }
        };
        adapter = new BottomMenuAdapter(homeMenuItems, getActivity(), clickHandler, iBottomMenuAction);
        binding.rvMenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.rvMenu.setAdapter(adapter);
    }

    private void generateMenu()
    {
        homeMenuItems = new ArrayList<>();
        homeMenuItems.add(new HomeMenuItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.home3, getActivity().getTheme()), "Home"));
        homeMenuItems.add(new HomeMenuItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.profile3, getActivity().getTheme()), "Profile"));
        homeMenuItems.add(new HomeMenuItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.transaction3, getActivity().getTheme()), "History"));
        homeMenuItems.add(new HomeMenuItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.about_company3, getActivity().getTheme()), "About Us"));
        homeMenuItems.add(new HomeMenuItem(ResourcesCompat.getDrawable(this.getResources(), R.drawable.logout3, getActivity().getTheme()), "Logout"));
    }

    public interface IBottomMenuAction {
        void closeBottomMenu();
    }
}
