Important note: You need to make sure the chromedriver.exe exists in the root folder to run the project.

Test Scenarios

1- Login
The home page opens, the login link with the text “Oturum Aç” is clicked.
Happy path case: Valid credentials are entered, the web player home page opens. Then user logs out.
Unhappy path case: Invalid credentials are entered. A related error message is displayed.

2- Play a Playlist
The home page opens, the login link with the text “Oturum Aç” is clicked. Valid credentials are entered, the web player home page opens.
Happy path case: A playlist is clicked and is played.
Unhappy path case: Not applicable.

3- Do a Search
The home page opens, the login link with the text “Oturum Aç” is clicked. Valid credentials are entered, the web player home page opens. Then the search page opens.
Happy path case: A search is performed with a valid artist name. The artist is displayed in the results.
Unhappy path case: A search is performed with an invalid artist name. A message denoting no results is found.
The search input is cleared.

4- Do a Support Search
The home page opens, the login link with the text “Oturum Aç” is clicked. Valid credentials are entered, the web player home page opens. Then the support page opens.
Happy path case: A search is performed with a valid topic. The artist is displayed in the results.
Unhappy path case: A search is performed with an invalid topic. A message denoting no results is found.
The search input is cleared.