package ru.spbau.selenium.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbau.selenium.data.User;
import ru.spbau.selenium.driver.WebDriverEntity;

/** Represents the YouTrack /login page. */
public class LoginPage extends WebDriverEntity {
  public LoginPage(WebDriver driver, WebDriverWait wait) {
    super(driver, wait);
  }

  public void loginUser(User user) {
    WebElement loginField = driver.findElement(By.id("id_l.L.login"));
    WebElement passwordField = driver.findElement(By.id("id_l.L.password"));
    WebElement loginButton = driver.findElement(By.id("id_l.L.loginButton"));
    loginField.sendKeys(user.login);
    passwordField.sendKeys(user.password);
    loginButton.click();

    wait.until(ExpectedConditions.urlContains("/dashboard"));
  }
}
