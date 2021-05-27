package com.example.interfaces;

import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;

@Model(file = "com/example/graph/SpotifySupport.json")
public interface ISpotifySupport {
    @Vertex()
    void Start();

    @Edge()
    void e_StartBrowser();

    @Vertex()
    void v_BrowserStarted();

    @Edge()
    void e_OpenSpotify();

    @Vertex()
    void v_SpotifyHomePageIsOpen();

    @Edge()
    void e_OpenSupportPage();

    @Vertex()
    void v_SupportPageIsOpen();

    @Edge()
    void e_DoSupportSearchWithResults();

    @Vertex()
    void v_SearchResultsReturned();

    @Edge()
    void e_DoSupportSearchWithoutResults();

    @Vertex()
    void v_SearchResultsNotReturned();

    @Edge()
    void e_ClearSearch();
}
