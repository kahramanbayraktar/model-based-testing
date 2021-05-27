package com.example.interfaces;

import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;

@Model(file = "com/example/graph/SpotifyPlaylist.json")
public interface ISpotifyPlaylist {
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
    void e_OpenPlaylist();

    @Vertex()
    void v_PlaylistIsOpen();

    @Edge()
    void e_StartPlaylistRadio();

    @Vertex()
    void v_PlaylistRadioStarted();
}
