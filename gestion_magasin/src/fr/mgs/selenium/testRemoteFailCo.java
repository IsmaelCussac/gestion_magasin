package fr.mgs.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class testRemoteFailCo {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "https://addons.mozilla.org/");
		selenium.start();
	}

	@Test
	public void testTestFailToLoad1() throws Exception {
		selenium.open("/en-US/firefox/addon/selenium-expert-selenium-ide/");
		selenium.click("id=main-wrapper");
		selenium.type("name=username", "azjeka");
		selenium.type("name=password", "azeadfq");
		selenium.click("name=valide_connect");
		selenium.waitForPageToLoad("30000");
		selenium.type("name=username", "mariana");
		selenium.type("name=password", "azer");
		selenium.click("name=valide_connect");
		selenium.waitForPageToLoad("30000");
		selenium.type("name=username", "malzk");
		selenium.type("name=password", "mariana");
		selenium.click("name=valide_connect");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
