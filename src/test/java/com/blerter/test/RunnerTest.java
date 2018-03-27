package com.blerter.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions (
		strict = false,
		features = "src/test/java/features", 
		plugin = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber.json" },
		tags = "@TestFeature"
		)
public class RunnerTest {

}
