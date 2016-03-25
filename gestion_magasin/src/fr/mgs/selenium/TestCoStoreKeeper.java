package fr.mgs.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestCoStoreKeeper {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8088";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testCoStoreKeeper() throws Exception {
		driver.get(baseUrl + "/gestion_magasin/connection");
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("ismael");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("ismael");
		driver.findElement(By.name("valide_connect")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.linkText("Sortie de stock")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[4]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[5]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34']/h3")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[8]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[7]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[6]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.cssSelector("span.ui-menuitem-text")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34']/h3")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[10]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[9]")).click();
		driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[4]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
		driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
		driver.findElement(By.id("j_idt40:j_idt41")).click();
		driver.findElement(By.xpath("//div[@id='j_idt40:pickList']/div/ul/li[6]")).click();
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
		driver.findElement(By.cssSelector("span.ui-menuitem-text")).click();
		driver.findElement(By.id("j_idt38:j_idt39")).click();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
		driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
		driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
		driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
		driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[8]")).click();
		driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[7]")).click();
		driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[6]")).click();
		driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion:5:form:items:1:box']/span")).click();
		driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion:5:form:items:1:box']/span")).click();
		driver.findElement(By.id("accordion:0:subAccordion:5:form:items:1:j_idt51")).click();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
	}

	@After
	public void tearDown() throws Exception {
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
