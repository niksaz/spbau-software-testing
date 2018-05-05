package ru.spbau.selenium;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbau.selenium.data.User;
import ru.spbau.selenium.driver.pages.LoginPage;
import ru.spbau.selenium.driver.pages.UsersPage;

public class YouTrackUsersTest {
  private static final Logger logger = Logger.getLogger(YouTrackUsersTest.class.getName());
  private static final String INITIAL_URL = "http://localhost:8080";

  private static final User rootUser = new User("root", "root");
  private static final User guestUser = new User("guest", "guest");

  private static WebDriver driver;
  private static WebDriverWait wait;

  @BeforeClass
  public static void configureChromeDriver() {
    String os = System.getProperty("os.name").toLowerCase();
    String driverPath;
    if (os.contains("windows")) {
      driverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
    } else {
      driverPath = System.getProperty("user.dir") + "/drivers/chromedriver";
    }
    System.setProperty("webdriver.chrome.driver", driverPath);
  }

  @Before
  public void setUp() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 5);

    driver.get(INITIAL_URL);
    logger.info("currentUrl is " + driver.getCurrentUrl());

    LoginPage loginPage = new LoginPage(driver, wait);
    loginPage.loginUser(rootUser);
    logger.info("currentUrl is " + driver.getCurrentUrl());
  }

  @Test
  public void baseUsersArePresent() {
    UsersPage usersPage = new UsersPage(driver, wait);
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    assertTrue(usersPage.userTableContains(rootUser));
    assertTrue(usersPage.userTableContains(guestUser));
  }

  @Test
  public void registerTwoUsers() {
    UsersPage usersPage = new UsersPage(driver, wait);
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    User userA = new User("userA", "userA");
    usersPage.registerUser(userA);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    usersPage.userTableContains(userA);

    User userB = new User("userB", "userB");
    usersPage.registerUser(userB);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    usersPage.userTableContains(userB);

    usersPage.deleteUser(userA);
    usersPage.deleteUser(userB);
  }

  @After
  public void tearUp() {
    driver.quit();
  }
}
