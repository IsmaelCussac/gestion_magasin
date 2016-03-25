package fr.mgs.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestWrongData {
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
  public void testWrongData() throws Exception {
    driver.get(baseUrl + "/gestion_magasin/connection");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("ismael");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("ismael");
    driver.findElement(By.name("valide_connect")).click();
    driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[10]")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:0:j_idt51")).click();
    driver.findElement(By.id("conditioning")).clear();
    driver.findElement(By.id("conditioning")).sendKeys("");
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:0:j_idt51")).click();
    driver.findElement(By.id("price")).clear();
    driver.findElement(By.id("price")).sendKeys("");
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:0:j_idt51")).click();
    driver.findElement(By.id("price")).clear();
    driver.findElement(By.id("price")).sendKeys("-1");
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("accordion:0:subAccordion:9:form:items:0:j_idt51")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("j_idt38:j_idt39")).click();
    driver.findElement(By.id("designation")).clear();
    driver.findElement(By.id("designation")).sendKeys("az");
    driver.findElement(By.id("conditioning")).clear();
    driver.findElement(By.id("conditioning")).sendKeys("-1");
    driver.findElement(By.id("j_idt72")).click();
    driver.findElement(By.id("j_idt38:j_idt39")).click();
    driver.findElement(By.id("j_idt38:j_idt39")).click();
    driver.findElement(By.id("designation")).clear();
    driver.findElement(By.id("designation")).sendKeys("aze");
    driver.findElement(By.xpath("//div[@id='sub']/div[3]")).click();
    driver.findElement(By.id("sub_2")).click();
    driver.findElement(By.id("j_idt72")).click();
    driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='accordion:0:subAccordion']/h3[2]")).click();
    driver.findElement(By.xpath("//div[@id='accordion']/h3")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.id("j_idt40")).click();
    driver.findElement(By.xpath("//div[@id='prod']/div[3]/span")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.linkText("Etat de stock")).click();
    driver.findElement(By.id("j_idt38:j_idt39")).click();
    driver.findElement(By.xpath("//div[@id='sub']/div[3]/span")).click();
    driver.findElement(By.linkText("magasin")).click();
    driver.findElement(By.linkText("product_t")).click();
    driver.findElement(By.id("sub_label")).click();
    driver.findElement(By.id("sub_label")).click();
    driver.findElement(By.id("designation")).clear();
    driver.findElement(By.id("designation")).sendKeys("Stylo bleu");
    driver.findElement(By.id("j_idt72")).click();
    driver.findElement(By.cssSelector("img.icon.ic_s_reload")).click();
    driver.findElement(By.cssSelector("img.icon.ic_s_reload")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.xpath("//div[@id='j_idt21:j_idt22']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.id("tablist:0:itExpDate_input")).click();
    driver.findElement(By.id("tablist:0:itExpDate_input")).clear();
    driver.findElement(By.id("tablist:0:itExpDate_input")).sendKeys("1232");
    driver.findElement(By.cssSelector("div.well.well-sm")).click();
    driver.findElement(By.id("tablist:0:itExpDate_input")).click();
    driver.findElement(By.xpath("//form[@id='form']/div[5]/div/div[2]")).click();
    driver.findElement(By.id("j_idt70")).click();
    driver.findElement(By.id("j_idt70")).click();
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
