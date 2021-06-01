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
    
    String actualErrorEmployeeName;
    String actualErrorUsername;
    String actualErrorPassword;
    String actualErrorConfirmPassword;
    
    //mulai test
    drpRole.selectByVisibleText("ESS");
    drpRole.selectByVisibleText("Admin");
    
    //test nama kosong
	elementNamaKaryawan.clear();
	elementNamaKaryawan.sendKeys("a");
	elementNamaKaryawan.clear();
	actualErrorEmployeeName = driver.findElement(By.xpath("//span[@for = 'systemUser_employeeName_empName']")).getText();
	Assert.assertEquals(expectedtErrorEmptyText, actualErrorEmployeeName, "Seharusnya muncul Error Required");
	
	//test nama tidak ada dalam list
	elementNamaKaryawan.sendKeys("a");
	actualErrorEmployeeName = driver.findElement(By.xpath("//span[@for = 'systemUser_employeeName_empName']")).getText();
	Assert.assertEquals(expectedErrorMsgName, actualErrorEmployeeName, "Seharusnya muncul Error Employee does not exist");
	
	//memasukkan nama yang benar
	elementNamaKaryawan.clear();
	elementNamaKaryawan.sendKeys("Linda Jane Anderson");
	
	//test username kosong
	elementUsername.clear();
	elementUsername.sendKeys("a");
	elementUsername.clear();
	actualErrorUsername = driver.findElement(By.xpath("//span[@for = 'systemUser_userName']")).getText();
	Assert.assertEquals(expectedtErrorEmptyText, actualErrorUsername, "Seharusnya muncul Error Required");
	
	//test username kurang dari 5 karakter
	elementUsername.sendKeys("a");
	actualErrorUsername = driver.findElement(By.xpath("//span[@for = 'systemUser_userName']")).getText();
	Assert.assertEquals(expectedErrorMsgUsername, actualErrorUsername, "Seharusnya muncul Error Should have at least 5 characters");
	
	//memasukkan username yang benar
	elementUsername.clear();
	elementUsername.sendKeys("Linda.Anderson");
	
	drpStatus.selectByVisibleText("Disabled");
	drpStatus.selectByVisibleText("Enabled");

	elementChangePasswordCheckBox.click();
	
	//test password kosong
	elementPassword.sendKeys("a");
	elementPassword.clear();
	actualErrorPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_password']")).getText();
	Assert.assertEquals(expectedtErrorEmptyText, actualErrorPassword, "Seharusnya muncul Error Required");
	
	//test password kurang dari 8 karakter
	elementPassword.clear();
	elementPassword.sendKeys("a");
	actualErrorPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_password']")).getText();
	Assert.assertEquals(expectedErrorPassword, actualErrorPassword, "Seharusnya muncul Error");
	
	//memasukkan password yang benar
	elementPassword.clear();
	elementPassword.sendKeys("Abc123!@#");
	
	//test konfirmasi password kosong/tidak sama
	elementConfirmPassword.sendKeys("a");
	elementConfirmPassword.clear();	
	actualErrorConfirmPassword = driver.findElement(By.xpath("//span[@for = 'systemUser_confirmPassword']")).getText();
	Assert.assertEquals(expectedErrorPasswordDoNotMatch, actualErrorConfirmPassword, "Seharusnya muncul Error");
	
	//memasukkan konfirmasi password yang benar/sama
	elementConfirmPassword.clear();
	elementConfirmPassword.sendKeys("Abc123!@#");
	
	//test apakah semua sudah benar/dapat disimpan editannya
	element = driver.findElement(By.id("btnSave"));
	element.click();
    String expectedCurrentURL = "https://opensource-demo.orangehrmlive.com/index.php/admin/saveSystemUser?userId=5";
    String actualCurrentURL = driver.getCurrentUrl();
    Assert.assertEquals(expectedCurrentURL, actualCurrentURL, "Test masih salah");	
    
    System.out.println("Selesai");
    
    
  }
  
//  public static void editUser(Select drpRole, WebElement elementNamaKaryawan, WebElement elementUsername, Select drpStatus, WebElement elementChangePasswordCheckBox, WebElement elementPassword, WebElement elementConfirmPassword, 
//		  String role, String namaKaryawan, String username, String status, String password, String confirmPassword) {
//	  drpRole.selectByVisibleText(role);
//	  elementNamaKaryawan.clear();
//	  elementNamaKaryawan.sendKeys(namaKaryawan);
//	  elementUsername.clear();
//	  elementUsername.sendKeys(username);
//	  drpStatus.selectByVisibleText(status);
//	  elementChangePasswordCheckBox.click();
//	  elementPassword.clear();
//	  elementPassword.sendKeys(password);
//	  elementConfirmPassword.clear();
//	  elementConfirmPassword.sendKeys(confirmPassword);
//  }

}