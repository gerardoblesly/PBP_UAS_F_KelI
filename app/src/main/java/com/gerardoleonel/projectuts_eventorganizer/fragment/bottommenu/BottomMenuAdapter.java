package com.gerardoleonel.projectuts_eventorganizer.fragment.bottommenu;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardoleonel.projectuts_eventorganizer.HomeMenuItem;
import com.gerardoleonel.projectuts_eventorganizer.IMenuClickHandler;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.SplashActivity;
import com.gerardoleonel.projectuts_eventorganizer.databinding.HomeMenuItemLayoutBinding;
import com.gerardoleonel.projectuts_eventorganizer.fragment.aboutus.AboutUsFragment;
import com.gerardoleonel.projectuts_eventorganizer.fragment.history.HistoryFragment;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.HomeFragment;
import com.gerardoleonel.projectuts_eventorganizer.fragment.profile.ProfileFragment;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;

import java.util.List;

public class BottomMenuAdapter extends RecyclerView.Adapter<BottomMenuAdapter.MenuItemViewHolder> {

    private List<HomeMenuItem> homeMenuItemList;
    private Activity activity;
    private IMenuClickHandler clickHandler;
    private BottomMenuFragment.IBottomMenuAction iBottomMenuAction;
    private String CHANNEL_ID = "Channel 2";

    public BottomMenuAdapter(List<HomeMenuItem> homeMenuItemList, Activity activity,
                             IMenuClickHandler clickHandler, BottomMenuFragment.IBottomMenuAction iBottomMenuAction) {
        this.homeMenuItemList = homeMenuItemList;
        this.activity = activity;
        this.clickHandler = clickHandler;
        this.iBottomMenuAction = iBottomMenuAction;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeMenuItemLayoutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_menu_item_layout, parent, false);
        return new MenuItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        final HomeMenuItem homeMenuItem = homeMenuItemList.get(position);
        holder.itemLayoutBinding.setMenuItem(homeMenuItem);
        holder.itemLayoutBinding.homeMenuItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homeMenuItem.getMenuTitle().equalsIgnoreCase("logout"))
                {
                    handleLogoutClick();
                }
                else if(homeMenuItem.getMenuTitle().equalsIgnoreCase("profile")) {
                    handleProfileClick();
                }
                else if(homeMenuItem.getMenuTitle().equalsIgnoreCase("about us")) {
                    handleAboutUsClick();
                }
                else if(homeMenuItem.getMenuTitle().equalsIgnoreCase("history")) {
                    handleHistoryClick();
                }
                else if(homeMenuItem.getMenuTitle().equalsIgnoreCase("home")){
                    handleHomeClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeMenuItemList.size();
    }

    public void updateData(List<HomeMenuItem> homeMenuItems) {
        this.homeMenuItemList = homeMenuItems;
        notifyDataSetChanged();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private HomeMenuItemLayoutBinding itemLayoutBinding;

        public MenuItemViewHolder(HomeMenuItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }

    private void handleLogoutClick() {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("LOGOUT");
        alertDialogBuilder.setMessage("Are you sure want to Logout?");
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setPositiveButton("YES",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id) {
                SharedPref sharedPref = new SharedPref(activity);
                sharedPref.logout();
                Intent intent = new Intent(activity, SplashActivity.class);
                createNotificationChannel();
                addNotification();
                activity.startActivity(intent);
                activity.finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Channel 2";
            String description = "This is Channel 2";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = this.activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Thankyou, Filographer!")
                .setContentText("See you again!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this.activity, SplashActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this.activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        NotificationManager manager = (NotificationManager) this.activity.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void handleProfileClick() {
        clickHandler.onMenuClick(new ProfileFragment());
        iBottomMenuAction.closeBottomMenu();
    }

    private void handleAboutUsClick() {
        clickHandler.onMenuClick(new AboutUsFragment());
        iBottomMenuAction.closeBottomMenu();
    }

    private void handleHistoryClick() {
        clickHandler.onMenuClick(new HistoryFragment());
        iBottomMenuAction.closeBottomMenu();
    }

    private void handleHomeClick(){
        clickHandler.onMenuClick(new HomeFragment());
        iBottomMenuAction.closeBottomMenu();
    }

}
