package adminEditUserRole;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    
    //masuk ke user management
    driver.get("https://opensource-demo.orangehrmlive.com/index.php/admin/viewSystemUsers");
    
    //masuk ke salah satu user
    driver.get("https://opensource-demo.orangehrmlive.com/index.php/admin/saveSystemUser?userId=5");
    
    //masuk ke menu edit
    element = driver.findElement(By.id("btnSave"));
    element.click();
    
    //elementCheckBoxUser.click();
    
    
  }

}