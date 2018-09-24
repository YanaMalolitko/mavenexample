package com.globalbluecalculator;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Yana on 9/23/2018.
 */


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "classpath:refundCalculator.feature")
public class RunCucumberFeature {


}
