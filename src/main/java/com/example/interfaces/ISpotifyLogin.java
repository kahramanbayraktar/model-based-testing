package com.example.interfaces;

import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;

@Model(file = "com/example/graph/SpotifyLogin.json")
public interface ISpotifyLogin {
    // Making Start a vertex might be incorrect!
    @Vertex()
    void Start();

    @Edge()
    void e_StartBrowser();

    @Vertex()
    void v_BrowserStarted();

    @Edge()
    void e_OpenSpotify();

    @Vertex()
    void v_SpotifyWebPlayerHomePageIsOpen();

    @Edge()
    void e_OpenLoginPage();

    @Vertex()
    void v_LoginPageIsOpen();
    
    @Edge()
    void e_LoginViaValidCredentials();

    @Vertex()
    void v_UserLoggedIn();

    @Edge()
    void e_LoginAttemptViaInvalidCredentials();

    @Vertex()
    void v_InvalidCredentialsMessageReturned();

    // @Edge()
    // void e_ClearInput();

    @Edge()
    void e_LogOut();
}
