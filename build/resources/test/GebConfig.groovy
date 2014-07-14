import org.openqa.selenium.Dimension

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

waiting {
	timeout = 2
}
driver = {
    println "Running Geb suite using default (PhantomJS)"
    def d = new PhantomJSDriver(new DesiredCapabilities())
    d.manage().window().setSize(new Dimension(1028, 768))
    d
}

environments {
	
	// run via “./gradlew firefoxTest”
	// See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	firefox {
		driver = { new FirefoxDriver() }
	}

    phantomJs {
        driver = {
            def d = new PhantomJSDriver(new DesiredCapabilities())
            d.manage().window().setSize(new Dimension(1028, 768))
            d
        }
    }

}

