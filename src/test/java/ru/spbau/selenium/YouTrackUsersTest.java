package ru.spbau.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
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
  private static final int MAX_LOGIN_LENGTH = 50;

  private static final User rootUser = new User("root", "root");
  private static final User guestUser = new User("guest", "guest");

  private static WebDriver driver;
  private static UsersPage usersPage;

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
    WebDriverWait wait = new WebDriverWait(driver, 5);

    driver.get(INITIAL_URL);
    logger.info("currentUrl is " + driver.getCurrentUrl());

    LoginPage loginPage = new LoginPage(driver, wait);
    loginPage.loginUser(rootUser);
    logger.info("currentUrl is " + driver.getCurrentUrl());

    usersPage = new UsersPage(driver, wait);
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());
  }

  @Test
  public void baseUsersArePresent() {
    assertTrue(usersPage.userTableContains(rootUser));
    assertTrue(usersPage.userTableContains(guestUser));
  }

  @Test
  public void registerTwoUsers() {
    User userA = new User("userA", "userA");
    usersPage.registerUser(userA);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    assertTrue(usersPage.userTableContains(userA));

    User userB = new User("userB", "userB");
    usersPage.registerUser(userB);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    assertTrue(usersPage.userTableContains(userB));

    usersPage.deleteUser(userA);
    usersPage.deleteUser(userB);

    assertFalse(usersPage.userTableContains(userA));
    assertFalse(usersPage.userTableContains(userB));
  }

  @Test
  public void registerNumericalUser() {
    User numericalUser = new User("12345", "12345");
    registerSingleUser(numericalUser);
  }

  @Test
  public void registerCyrillicLettersLoginUser() {
    User cyrillicUser = new User("Владимир", "vlad");
    registerSingleUser(cyrillicUser);
  }

  @Test
  public void registerChineseLettersLoginUser() {
    User chineseUser = new User("漢字", "characters");
    registerSingleUser(chineseUser);
  }

  @Test
  public void registerSymbolicLoginUser() {
    User symbolicUser = new User("+-?()", "symbol");
    registerSingleUser(symbolicUser);
  }

  @Test
  public void registerMaxLengthLoginUser() {
    User maxUser =
      new User(
        String.join("", Collections.nCopies(MAX_LOGIN_LENGTH, "a")),
        "maximum");
    registerSingleUser(maxUser);
  }

  private void registerSingleUser(User user) {
    usersPage.registerUser(user);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    assertTrue(usersPage.userTableContains(user));

    usersPage.deleteUser(user);

    assertFalse(usersPage.userTableContains(user));
  }

  @Test
  public void registerLongerThanMaxLengthLoginUser() {
    User longUser =
      new User(
        String.join("", Collections.nCopies(MAX_LOGIN_LENGTH, "a")) +
          String.join("", Collections.nCopies(MAX_LOGIN_LENGTH, "b")),
        "maximum");
    usersPage.registerUser(longUser);
    logger.info("currentUrl is " + driver.getCurrentUrl());
    usersPage.bringPageUp();
    logger.info("currentUrl is " + driver.getCurrentUrl());

    User trimmedUser =
      new User(
        String.join("", Collections.nCopies(MAX_LOGIN_LENGTH, "a")),
        "maximum");
    assertTrue(usersPage.userTableContains(trimmedUser));

    usersPage.deleteUser(trimmedUser);

    assertFalse(usersPage.userTableContains(trimmedUser));
  }

  @Test
  public void startSpaceNotAllowedInLogin() {
    User spaceUser = new User(" Peter", "12345");
    usersPage.registerUserWithMessageError(spaceUser);
  }

  @Test
  public void middleSpaceNotAllowedInLogin() {
    User spaceUser = new User("Peter Johnson", "12345");
    usersPage.registerUserWithMessageError(spaceUser);
  }

  @Test
  public void endSpaceNotAllowedInLogin() {
    User spaceUser = new User("Johnson ", "12345");
    usersPage.registerUserWithMessageError(spaceUser);
  }

  @Test
  public void emptyLoginNotAllowed() {
    User noLoginUser = new User("", "9876");
    usersPage.registerUserWithBulbError(noLoginUser);
  }

  @After
  public void tearUp() {
    driver.quit();
  }
}
