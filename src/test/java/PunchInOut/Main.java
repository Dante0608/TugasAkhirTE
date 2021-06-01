package PunchInOut;

import java.awt.List;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("profile.default_content_setting_values.notifications", 2);
    ChromeOptions options = new ChromeOptions();
    options.setExperimentalOption("prefs", map);
    WebDriver driver = new ChromeDriver(options);
    //driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    
    //masuk ke halaman login
    driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
    
    //login lalu masuk ke dashboard
    WebElement element = driver.findElement(By.xpath("//*[@id = 'txtUsername']"));
    element.clear();
    element.sendKeys("admin");
    WebElement element2 = driver.findElement(By.xpath("//*[@id = 'txtPassword']"));
    element2.clear();
    element2.sendKeys("admin123");
    element2.submit();
    
    //masuk ke halaman on boarding events
    driver.get("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn#");
    
    //test tanggal masuk salah kosong/salah format
    WebElement elementDatePunchIn = driver.findElement(By.id("attendance_date"));
    elementDatePunchIn.clear();
    elementDatePunchIn.sendKeys("11111");
    String expectedErrorDate = "Should Be a Valid Date in yy-mm-dd Format";
    String actualErrorDate = driver.findElement(By.id("dateErrorHolder")).getText();
    Assert.assertEquals(expectedErrorDate, actualErrorDate, "Seharusnya muncul Error");
    
    //memasukkan tanggal yang benar
    elementDatePunchIn.clear();
    elementDatePunchIn.sendKeys("2021-06-02");
    
    //test jam masuk salah
    WebElement elementTimePunchIn = driver.findElement(By.id("attendance_time"));
    
    
    System.out.println("Selesai?");
  }

}