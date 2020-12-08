package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {

    @Mock
    private RegisterView view;
    @Mock
    private RegisterService service;
    private RegisterPresenter presenter;

    @Before
    public void setUp() throws Exception
    {
        presenter = new RegisterPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("");
        System.out.println("username : " + view.getUsername());
        presenter.onRegisterClicked();
        verify(view).showUsernameError("All field must be filled");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("gerardoblesly");
        System.out.println("username : " + view.getUsername());
        when(view.getPassword()).thenReturn("");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisterClicked();
        verify(view).showPasswordError("All field must be filled");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("gerardoblesly");
        System.out.println("username : " + view.getUsername());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("password : " + view.getPassword());
        when(view.getEmail()).thenReturn("");
        System.out.println("email : " + view.getEmail());
        presenter.onRegisterClicked();
        verify(view).showEmailError("All field must be filled");
    }

    @Test
    public void shouldShowErrorMessageWhenNoHPIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("gerardoblesly");
        System.out.println("username : " + view.getUsername());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("password : " + view.getPassword());
        when(view.getEmail()).thenReturn("gerardoleonel34@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getNoHp()).thenReturn("");
        System.out.println("no hp : " + view.getNoHp());
        presenter.onRegisterClicked();
        verify(view).showNoHpError("All field must be filled");
    }

    @Test
    public void shouldShowErrorMessageWhenAddressIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("gerardoblesly");
        System.out.println("username : " + view.getUsername());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("password : " + view.getPassword());
        when(view.getEmail()).thenReturn("gerardoleonel34@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getNoHp()).thenReturn("081559967878");
        System.out.println("no hp : " + view.getNoHp());
        when(view.getAddress()).thenReturn("");
        System.out.println("address : " + view.getAddress());
        presenter.onRegisterClicked();
        verify(view).showAddressError("All field must be filled");
    }

    @Test
    public void registerSukses() throws
            Exception {
        when(view.getUsername()).thenReturn("gerardoblesly");
        System.out.println("Username: " + view.getUsername());
        when(view.getEmail()).thenReturn("gerardoleonel34@gmail.com");
        System.out.println("Email : " + view.getEmail());
        when(view.getPassword()).thenReturn("asdasd");
        System.out.println("Password : " + view.getPassword());
        when(view.getNoHp()).thenReturn("0815596678");
        System.out.println("No HP : " + view.getNoHp());
        when(view.getAddress()).thenReturn("Solo Baru City");
        System.out.println("Address : " + view.getAddress());
        when(service.getValid(view, view.getUsername(), view.getPassword(),view.getEmail(), view.getNoHp(), view.getAddress())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getUsername(), view.getPassword(),view.getEmail(), view.getNoHp(), view.getAddress()));
        presenter.onRegisterClicked();
    }
}