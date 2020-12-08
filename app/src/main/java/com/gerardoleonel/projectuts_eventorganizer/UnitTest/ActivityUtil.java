package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

import android.content.Context;
import android.content.Intent;

import com.gerardoleonel.projectuts_eventorganizer.LoginActivity;

public class ActivityUtil {

    private Context context;
    public ActivityUtil(Context context) {
        this.context = context;
    }
    public void startMainActivity() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}
