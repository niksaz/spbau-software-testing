package ru.spbau.selenium.driver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbau.selenium.data.User;
import ru.spbau.selenium.driver.WebDriverEntity;

/** An element of YouTrack /users page for registering a new user. */
public class CreateNewUserForm extends WebDriverEntity {
  public CreateNewUserForm(WebDriver driver, WebDriverWait wait) {
    super(driver, wait);
  }

  public void registerUser(User user) {
    By loginFieldSelector = By.id("id_l.U.cr.login");
    wait.until(ExpectedConditions.visibilityOfElementLocated(loginFieldSelector));

    WebElement loginField = driver.findElement(loginFieldSelector);
    WebElement passwordField = driver.findElement(By.id("id_l.U.cr.password"));
    WebElement confirmPasswordField = driver.findElement(By.id("id_l.U.cr.confirmPassword"));

    loginField.sendKeys(user.login);
    passwordField.sendKeys(user.password);
    confirmPasswordField.sendKeys(user.password);

    WebElement okButton = driver.findElement(By.id("id_l.U.cr.createUserOk"));
    okButton.click();
    wait.until(ExpectedConditions.urlContains("/editUser"));
  }
}
