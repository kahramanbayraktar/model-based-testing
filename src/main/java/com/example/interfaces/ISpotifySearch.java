package com.example.interfaces;

import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;

@Model(file = "com/example/graph/SpotifySearch.json")
public interface ISpotifySearch {
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
    void e_OpenSearchPage();

    @Vertex()
    void v_SearchPageIsOpen();

    @Edge()
    void e_DoSearchWithResults();

    @Vertex()
    void v_SearchResultsReturned();

    @Edge()
    void e_DoSearchWithoutResults();

    @Vertex()
    void v_SearchResultsNotReturned();

    @Edge()
    void e_ClearSearch();
}
