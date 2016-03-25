package fr.mgs.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestOrderClient {
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
  public void testOrderClient() throws Exception {
    driver.get(baseUrl + "/gestion_magasin/connection");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("claire");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("cliare");
    driver.findElement(By.name("valide_connect")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("claire");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("claire");
    driver.findElement(By.name("valide_connect")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34']/h3")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[6]")).click();
    driver.findElement(By.xpath("//span[@id='j_idt34:0:subAccordion:5:form:items:5:sp']/a")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[8]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[9]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:1:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:1:subAccordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:1:subAccordion']/h3[3]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:1:subAccordion']/h3[4]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:1:subAccordion']/h3[5]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34']/h3[3]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:2:subAccordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:2:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:2:subAccordion']/h3[3]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:2:subAccordion']/h3[4]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:2:subAccordion']/h3[5]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34']/h3[3]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34']/h3")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[13]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[12]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.xpath("//span[@id='list:j_idt32:1:sp']/a/span/span")).click();
    driver.findElement(By.xpath("//span[@id='list:j_idt32:1:sp']/a/span/span")).click();
    driver.findElement(By.id("buttons:save")).click();
    driver.findElement(By.cssSelector("span.ui-menuitem-text")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.cssSelector("span.ui-menuitem-text")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-triangle-1-e")).click();
    driver.findElement(By.xpath("//div[@id='j_idt34:0:subAccordion']/h3[12]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.id("buttons:remove")).click();
    driver.findElement(By.id("buttons:remove")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.id("j_idt17:logout")).click();
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
