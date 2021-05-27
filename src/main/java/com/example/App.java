package com.example;

import java.io.IOException;

import com.example.implementations.SpotifyLogin;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Executor executor = new TestExecutor(SpotifyLogin.class);
        executor.getMachine().getCurrentContext().setPathGenerator(new RandomPath(new EdgeCoverage(100)));
        Result result = executor.execute(true);
        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }
    }
}
