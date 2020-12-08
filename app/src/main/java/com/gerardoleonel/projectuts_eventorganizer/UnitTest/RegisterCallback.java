package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;

public interface RegisterCallback {
    void onSuccess(boolean value, User user);
    void onError();
}
