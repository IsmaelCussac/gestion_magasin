package fr.mgs.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestStoreKeeperProduct {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testStoreKeeperProduct() throws Exception {
    driver.get(baseUrl + "/gestion_magasin/connection");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("ismael");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("ismael");
    driver.findElement(By.name("valide_connect")).click();
    driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[5]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[5]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[10]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion:9:form:items:2:box']/span[2]")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:2:j_idt51")).click();
    driver.findElement(By.id("conditioning")).clear();
    driver.findElement(By.id("conditioning")).sendKeys("400.0");
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:2:j_idt51")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("j_idt38:j_idt39")).click();
    driver.findElement(By.xpath("//div[@id='sub']/div[3]")).click();
    driver.findElement(By.id("sub_2")).click();
    driver.findElement(By.id("designation")).clear();
    driver.findElement(By.id("designation")).sendKeys("Stylo 4 couleurs");
    driver.findElement(By.cssSelector("#box > span.ui-button-text.ui-c")).click();
    driver.findElement(By.id("j_idt72")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.linkText("Entrée de stock")).click();
    driver.findElement(By.id("j_idt40")).click();
    driver.findElement(By.xpath("//div[@id='prod']/div[3]")).click();
    driver.findElement(By.id("prod_0")).click();
    driver.findElement(By.id("j_idt50")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.linkText("Etat de stock")).click();
    driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[9]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[9]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[10]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[10]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[11]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.id("j_idt70")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[5]/a/span[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[6]/a/span[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[4]/a/span[2]")).click();
    driver.findElement(By.id("j_idt40:j_idt41")).click();
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
