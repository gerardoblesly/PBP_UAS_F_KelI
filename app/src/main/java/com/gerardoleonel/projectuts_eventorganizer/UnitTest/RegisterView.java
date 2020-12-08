package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

public interface RegisterView {

    String getUsername();
    void showUsernameError(String message);

    String getEmail();
    void showEmailError(String message);

    String getPassword();
    void showPasswordError(String message);

    String getNoHp();
    void showNoHpError(String message);

    String getAddress();
    void showAddressError(String message);


    void startMainActivity();

    void showRegisterError(String message);

    void showErrorResponse(String message);

}
