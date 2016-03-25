package fr.mgs.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestCoAdmin {
	private static WebDriver driver;
	private static String baseUrl;
	private boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8088";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testCoAdmin() throws Exception {
		driver.get(baseUrl + "/gestion_magasin/connection");
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("mariana");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("mariana");
		driver.findElement(By.name("valide_connect")).click();
		driver.findElement(By.linkText("Modification")).click();
		driver.findElement(By.linkText("Visibilité")).click();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
