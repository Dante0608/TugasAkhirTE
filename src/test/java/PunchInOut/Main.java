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
    WebElement elementTempatSembarang = driver.findElement(By.xpath("//label[@for = 'attendance_note']"));
    elementTempatSembarang.click();
    
    //test jam masuk salah
    WebElement elementTimePunchIn = driver.findElement(By.id("attendance_time"));
    elementTimePunchIn.clear();
    elementTimePunchIn.sendKeys("25:61");
    elementTimePunchIn.clear();
    String expectedErrorTime = "Should Be a Valid Time in HH:MM Format";
    String actualErrorTime = driver.findElement(By.id("timeErrorHolder")).getText();
    Assert.assertEquals(expectedErrorTime, actualErrorTime, "Seharusnya muncul Error");
    
    //memasukkan jam yang benar
    elementTimePunchIn.clear();
    elementTimePunchIn.sendKeys("08:00");
    
    //memasukkan note
    WebElement elementNotePunchIn = driver.findElement(By.id("attendance_note"));
    elementNotePunchIn.sendKeys("Masuk Pertama, percobaan pertama");
    
    //melakukan punch in
    WebElement elementSavePunch = driver.findElement(By.id("btnPunch"));
    elementSavePunch.click();
    String expectedCurrentURL = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
    String actualCurrentURL = driver.getCurrentUrl();
    Assert.assertEquals(expectedCurrentURL, actualCurrentURL, "Punch In belum berhasil/masih salah");
    
    //test tanggal keluar salah kosong/salah format (punch out)
    WebElement elementDatePunchOut = driver.findElement(By.id("attendance_date"));
    elementDatePunchOut.clear();
    elementDatePunchOut.sendKeys("11111");
    String expectedErrorDatePO = "Should Be a Valid Date in yy-mm-dd Format";
    String actualErrorDatePO = driver.findElement(By.id("dateErrorHolder")).getText();
    Assert.assertEquals(expectedErrorDatePO, actualErrorDatePO, "Seharusnya muncul Error");
    
    //test tanggal keluar kurang dari tanggal masuk
    elementDatePunchOut.clear();
    elementDatePunchOut.sendKeys("2021-06-01");
    elementTempatSembarang = driver.findElement(By.xpath("//label[@for = 'attendance_note']"));
    elementTempatSembarang.click();
    String expectedErrorDatePO2 = "Punch out Time Should Be Higher Than Punch in Time";
    String actualErrorDatePO2 = driver.findElement(By.id("timeErrorHolder")).getText();
    Assert.assertEquals(expectedErrorDatePO2, actualErrorDatePO2, "Seharusnya muncul Error");
    
    //memasukkan tanggal yang benar
    elementDatePunchOut.clear();
    elementDatePunchOut.sendKeys("2021-06-02");
    elementTempatSembarang.click();
    
    //test jam keluar salah format
    WebElement elementTimePunchOut = driver.findElement(By.id("attendance_time"));
    elementTimePunchOut.clear();
    elementTimePunchOut.sendKeys("1");
    String expectedErrorTimePO = "Should Be a Valid Time in HH:MM Format";
    String actualErrorTimePO = driver.findElement(By.id("timeErrorHolder")).getText();
    Assert.assertEquals(expectedErrorTimePO, actualErrorTimePO, "Seharusnya muncul Error");
    
    //test jam keluar kurang dari waktu masuk
    elementTimePunchOut.clear();
    elementTimePunchOut.sendKeys("07:00");
    elementTempatSembarang.click();
    String expectedErrorTimePO2 = "Punch out Time Should Be Higher Than Punch in Time";
    String actualErrorTimePO2 = driver.findElement(By.id("timeErrorHolder")).getText();
    Assert.assertEquals(expectedErrorTimePO2, actualErrorTimePO2, "Seharusnya muncul Error");
    
    //memasukkan jam yang benar
    elementTimePunchOut.clear();
    elementTimePunchOut.sendKeys("09:00");
    elementTempatSembarang.click();
    
    //memasukkan note punch out
    WebElement elementNotePunchOut = driver.findElement(By.id("attendance_note"));
    elementNotePunchOut.sendKeys("Keluar Pertama, percobaan pertama");
    
    //melakukan punch out
    WebElement elementSavePunchOut = driver.findElement(By.id("btnPunch"));
    elementSavePunchOut.click();
    expectedCurrentURL = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
    actualCurrentURL = driver.getCurrentUrl();
    Assert.assertEquals(expectedCurrentURL, actualCurrentURL, "Punch Out belum berhasil/masih salah");
    
    //test punch in kedua kalinya
    elementDatePunchIn = driver.findElement(By.id("attendance_date"));
    elementDatePunchIn.clear();
    elementTempatSembarang = driver.findElement(By.xpath("//label[@for = 'attendance_note']"));
    elementTempatSembarang.click();
    elementDatePunchIn = driver.findElement(By.id("attendance_date"));
    elementDatePunchIn.sendKeys("2021-06-02");
    elementTempatSembarang.click();
    
    //memasukkan jam/waktu di antara jam punch in-out yang pertama
    elementTimePunchIn = driver.findElement(By.id("attendance_time"));
    elementTimePunchIn.clear();
    elementTimePunchIn.sendKeys("08:10");
    elementTempatSembarang.click();
    expectedErrorTime = "Overlapping Records Found";
    actualErrorTime = driver.findElement(By.id("timeErrorHolder")).getText();
    Assert.assertEquals(expectedErrorTime, actualErrorTime, "Seharusnya muncul Error");
    
    //memasukkan jam/waktu yang benar (tidak di antara jam punch in-out yang pertama)
    elementTimePunchIn.clear();
    elementTimePunchIn.sendKeys("10:00");
    elementTempatSembarang.click();
    
    //mengikuti alur sampai selesai
    elementNotePunchIn = driver.findElement(By.id("attendance_note"));
    elementNotePunchIn.clear();
    elementNotePunchIn.sendKeys("Masuk Kedua, percobaan kedua");
    
    elementSavePunch = driver.findElement(By.id("btnPunch"));
    elementSavePunch.click();
    
//    elementDatePunchOut = driver.findElement(By.id("attendance_date"));
//    elementDatePunchOut.clear();
//    elementDatePunchOut.sendKeys("2021-06-02");
    elementTempatSembarang = driver.findElement(By.xpath("//label[@for = 'attendance_note']"));
    elementTempatSembarang.click();
    
    elementTimePunchOut = driver.findElement(By.id("attendance_time"));
    elementTimePunchOut.clear();
    elementTimePunchOut.sendKeys("11:00");
    elementTempatSembarang.click();
    
    elementTimePunchOut.clear();
    elementTimePunchOut.sendKeys("11:00");
    elementTempatSembarang.click();
    
    elementNotePunchOut = driver.findElement(By.id("attendance_note"));
    elementNotePunchOut.sendKeys("Keluar Kedua, percobaan kedua");
    
    elementSavePunchOut = driver.findElement(By.id("btnPunch"));
    elementSavePunchOut.click();
    
    expectedCurrentURL = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
    actualCurrentURL = driver.getCurrentUrl();
    Assert.assertEquals(expectedCurrentURL, actualCurrentURL, "Punch Out belum berhasil/masih salah");

    driver.get("https://opensource-demo.orangehrmlive.com/index.php/attendance/viewMyAttendanceRecord");
    WebElement elementPilihTanggal = driver.findElement(By.id("attendance_date"));
    elementPilihTanggal.clear();
    elementPilihTanggal.sendKeys("2021-06-02");
    elementTempatSembarang = driver.findElement(By.xpath("//label[@for = 'attendance_date']"));
    elementTempatSembarang.click();
    elementTempatSembarang.click();
    
    System.out.println("Selesai");
  }

}