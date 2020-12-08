package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

public class RegisterPresenter {

    private RegisterView view;
    private RegisterService service;

    public RegisterPresenter(RegisterView view, RegisterService service) {
        this.view = view;
        this.service = service;
    }

    public void onRegisterClicked() {
        if (view.getUsername().isEmpty())
        {
            view.showUsernameError("All field must be filled");
            return;
        }
        else if (view.getPassword().isEmpty())
        {
            view.showPasswordError("All field must be filled");
            return;
        }
        else if (view.getEmail().isEmpty())
        {
            view.showEmailError("All field must be filled");
            return;
        }
        else if (view.getNoHp().isEmpty())
        {
            view.showNoHpError("All field must be filled");
            return;
        }
        else if (view.getAddress().isEmpty())
        {
            view.showAddressError("All field must be filled");
            return;
        }
        else
        {
            service.register(view, view.getUsername(), view.getPassword(), view.getEmail(), view.getNoHp(), view.getAddress());
            return;
        }
    }
}
