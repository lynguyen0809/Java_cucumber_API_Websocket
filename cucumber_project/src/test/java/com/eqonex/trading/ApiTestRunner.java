package com.eqonex.trading;

import com.eqonex.trading._support.GlobalResource;
import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        plugin = {"pretty",
        },
        // Features folder path
        features = {"src/test/java/com/eqonex/trading/features"},
        //Glue to make TestRunner understand the step definition required for above features folder
        glue = {"com.eqonex.trading.steps"},
        tags = "@connect_wss"
)

public class ApiTestRunner {
    protected static TestNGCucumberRunner testNGCucumberRunner;
    public static GlobalResource globalResource;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        globalResource = new GlobalResource();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void oscenario(PickleWrapper pickleEvent, FeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass
    public void tearDown(){
        globalResource.clear();
    }
}
