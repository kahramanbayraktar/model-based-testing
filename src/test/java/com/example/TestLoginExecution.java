package com.example;

import java.io.IOException;

import com.example.implementations.SpotifyLogin;
import com.example.implementations.SpotifyPlaylist;
import com.example.implementations.SpotifySearch;
import com.example.implementations.SpotifySupport;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;
import org.junit.Test;

public class TestLoginExecution {
    @Test
    public void testExecutor() throws IOException {
        Execute(SpotifyLogin.class);
        Execute(SpotifySearch.class);
        Execute(SpotifyPlaylist.class);
        Execute(SpotifySupport.class);
    }

    private void Execute(Class<?>... tests) throws IOException {
        Executor executor = new TestExecutor(tests);
        executor.getMachine().getCurrentContext().setPathGenerator(new RandomPath(new EdgeCoverage(100)));
        
        Result result = executor.execute(true);

        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }

        System.out.println("Done: [" + result.getResults().toString(2) + "]");
    }
}
