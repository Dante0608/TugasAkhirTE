package adminEditUser;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
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
    
    //var persiapan edit
    Select drpRole = new Select(driver.findElement(By.name("systemUser[userType]")));
    WebElement elementNamaKaryawan = driver.findElement(By.id("systemUser_employeeName_empName"));
    WebElement elementUsername = driver.findElement(By.id("systemUser_userName"));
    Select drpStatus = new Select(driver.findElement(By.name("systemUser[status]")));
    WebElement elementChangePasswordCheckBox = driver.findElement(By.id("systemUser_chkChangePassword"));
    WebElement elementPassword = driver.findElement(By.id("systemUser_password"));
    WebElement elementConfirmPassword = driver.findElement(By.id("systemUser_confirmPassword"));
    
    String expectedtErrorEmptyText = "Required";
    String expectedErrorMsgName = "Employee does not exist";
    String expectedErrorMsgUsername = "Should have at least 5 characters";
    String expectedErrorPassword = "Should have at least 8 characters";
    String expectedErrorPasswordDoNotMatch = "Passwords do not match";
    
    String expectedNoError = "";
    
    //test yang seharusnya tidak error
    editUser(drpRole, elementNamaKaryawan, elementUsername, drpStatus, elementChangePasswordCheckBox, elementPassword, elementConfirmPassword,
    		"ESS", "Linda Jane Anderson", "Linda.Anderson", "Disabled", "Abc123!@#", "Abc123!@#");
    WebElement elementErrorUsername = driver.findElement(By.xpath("//span[@for = 'systemUser_userName']"));
    WebElement elementErrorPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_password']"));
    WebElement elementErrorConfirmPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_confirmPassword']"));
    String expectedCurrentURL = "";
    
    String actualCurrentURL = driver.getCurrentUrl();
    String actualErrorEmployeeName = driver.findElement(By.xpath("//span[@for = 'systemUser_employeeName_empName']")).getText();
    String actualErrorUsername = driver.findElement(By.xpath("//span[@for = 'systemUser_userName']")).getText();
    String actualErrorPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_password']")).getText();
    String actualErrorConfirmPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_confirmPassword']")).getText();
    
    
//    Assert.assertEquals(expectedNoError, actualErrorEmployeeName, "Test edit nama belum benar");
//    Assert.assertTrue(!elementErrorUsername.isDisplayed(), "Test edit username belum benar");
//    Assert.assertTrue(!elementErrorPassword.isDisplayed(), "Test edit password belum benar");
//    Assert.assertTrue(!elementErrorConfirmPassword.isDisplayed(), "Test edit password belum benar");
//    Assert.assertEquals(expectedNoError, actualErrorPassword, "Test edit password belum benar");
//    Assert.assertEquals(expectedNoError, actualErrorConfirmPassword, "Test edit password belum benar");
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    
    //test yang seharusnya error karena kosong di hampir semua field
    
    
    
    System.out.println("Selesai?");
    
    
  }
  
  public static void editUser(Select drpRole, WebElement elementNamaKaryawan, WebElement elementUsername, Select drpStatus, WebElement elementChangePasswordCheckBox, WebElement elementPassword, WebElement elementConfirmPassword, 
		  String role, String namaKaryawan, String username, String status, String password, String confirmPassword) {
	  drpRole.selectByVisibleText(role);
	  elementNamaKaryawan.clear();
	  elementNamaKaryawan.sendKeys(namaKaryawan);
	  elementUsername.clear();
	  elementUsername.sendKeys(username);
	  drpStatus.selectByVisibleText(status);
	  elementChangePasswordCheckBox.click();
	  elementPassword.clear();
	  elementPassword.sendKeys(password);
	  elementConfirmPassword.clear();
	  elementConfirmPassword.sendKeys(confirmPassword);
	  elementConfirmPassword.submit();
  }

}