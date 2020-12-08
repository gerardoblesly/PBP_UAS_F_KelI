package com.gerardoleonel.projectuts_eventorganizer.fragment.aboutus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.FragmentAboutUsBinding;
import com.gerardoleonel.projectuts_eventorganizer.events.eventtype.EventTypeActivity;
import com.gerardoleonel.projectuts_eventorganizer.geolocation.MapActivity;

public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_about_us, container, false);

        binding.btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(binding.getRoot().getContext(), MapActivity.class));
            }
        });

        binding.btnOurEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(binding.getRoot().getContext(), EventTypeActivity.class));
            }
        });

        return binding.getRoot();
    }
}
