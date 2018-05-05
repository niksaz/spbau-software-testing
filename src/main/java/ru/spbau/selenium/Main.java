package ru.spbau.selenium;

import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbau.selenium.driver.pages.LoginPage;
import ru.spbau.selenium.driver.pages.UsersPage;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());
  private static final String INITIAL_URL = "http://localhost:8080";

  private static final User rootUser = new User("root", "root");
  private static final User guestUser = new User("guest", "guest");

  private static void configureChromeDriver() {
    System.setProperty(
      "webdriver.chrome.driver",
      System.getProperty("user.dir") + "/drivers/chromedriver");
  }

  public static void main(String[] args) throws Exception {
    configureChromeDriver();

    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, 5);

    driver.get(INITIAL_URL);
    logger.info("currentUrl is " + driver.getCurrentUrl());

    LoginPage loginPage = new LoginPage(driver, wait);
    loginPage.loginUser(rootUser);
    logger.info("currentUrl is " + driver.getCurrentUrl());

    UsersPage usersPage = new UsersPage(driver, wait);
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    usersPage.ensureUserTableContains(rootUser);
    usersPage.ensureUserTableContains(guestUser);

    User userA = new User("userA", "userA");
    usersPage.registerUser(userA);
    usersPage.bringPageUp();
    usersPage.ensureUserTableContains(userA);

    User userB = new User("userB", "userB");
    usersPage.registerUser(userB);
    usersPage.bringPageUp();
    usersPage.ensureUserTableContains(userB);

    usersPage.deleteUser(userA);
    usersPage.deleteUser(userB);

    Thread.sleep(5000);
    driver.quit();
  }
}
