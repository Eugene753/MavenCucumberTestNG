package Runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
        features = "@target/failed.txt",
        //glue is where we can find implementations for gherkin steps
        //we provide the path of package for steps
        glue = "steps",
        //if we set dry run to true, it will quickly scan all gherkin steps are implemented or not
        //if it is true, then no actual execution happens
        dryRun=false,
        //it means that the console output for the cucumber test is easily readable
        //it will remove unreadable character
        monochrome=true,
        //if strict is set to true at the time of execution if cucumber encounters any errors any undefined step, it will give
        //an error and stops execution.Also it gives code snippet for the unimplemented steps

        //tags = "@dashboardtabs",
        //tags will identify the scenarios based on the tags we will provide such as @smoke,
        //@regression etc
        //we can add multiple tags in the runner class to execute scenarios belong to different tags
        plugin = {"pretty"}

)

public class FailedRunner extends AbstractTestNGCucumberTests {


}
