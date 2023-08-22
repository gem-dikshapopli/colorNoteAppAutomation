package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/feature"
        ,glue = "definition"
        ,tags = "@test1"
        ,monochrome = true
        ,publish = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}

