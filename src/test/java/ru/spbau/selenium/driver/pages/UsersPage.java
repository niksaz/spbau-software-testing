package ru.spbau.selenium.driver.pages;

import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbau.selenium.data.User;
import ru.spbau.selenium.driver.WebDriverEntity;
import ru.spbau.selenium.driver.elements.CreateNewUserForm;

/** Represents YouTrack /users page. */
public class UsersPage extends WebDriverEntity {
  public UsersPage(WebDriver driver, WebDriverWait wait) {
    super(driver, wait);
  }

  public void bringPageUp() {
    WebElement settingsDropdown =
      driver.findElement(By.cssSelector(".ring-menu__item__i.ring-font-icon.ring-font-icon_cog"));
    settingsDropdown.click();
    By dropdownOptionsSelector = By.cssSelector(".ring-dropdown__item.ring-link");

    wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOptionsSelector));

    List<WebElement> dropdownOptions = driver.findElements(dropdownOptionsSelector);
    WebElement usersDropdown =
      dropdownOptions.stream()
        .filter(webElement -> webElement.getAttribute("href").endsWith("/users"))
        .findFirst()
        .get();
    usersDropdown.click();

    wait.until(ExpectedConditions.urlContains("/users"));
  }

  public boolean userTableContains(User user) {
    WebElement userTableRow = findUserTableRow(user);
    return userTableRow != null;
  }

  public void registerUser(User user) {
    WebElement createUserButton = driver.findElement(By.id("id_l.U.createNewUser"));
    createUserButton.click();

    CreateNewUserForm createNewUserForm = new CreateNewUserForm(driver, wait);
    createNewUserForm.registerUser(user);
  }

  public void deleteUser(User user) {
    WebElement userTableRow = findUserTableRow(user);
    if (userTableRow == null) {
      throw new IllegalStateException("deleteUser() for non-existent user");
    }
    WebElement deleteButton =
      userTableRow.findElement(By.cssSelector("*[cn='l.U.usersList.deleteUser']"));
    deleteButton.click();

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();
    wait.until(ExpectedConditions.stalenessOf(userTableRow));
  }

  private WebElement findUserTableRow(User user) {
    WebElement usersTable = driver.findElement(By.cssSelector(".table.users-table"));
    WebElement tableBody = usersTable.findElement(By.cssSelector("tbody"));
    List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
    for (WebElement tableRow : tableRows) {
      WebElement loginElement =
        tableRow.findElement(By.cssSelector("*[cn='l.U.usersList.UserLogin.editUser']"));
      if (loginElement.getText().equals(user.login)) {
        return tableRow;
      }
    }
    return null;
  }
}
