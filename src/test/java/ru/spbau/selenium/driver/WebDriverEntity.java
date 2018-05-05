package ru.spbau.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Represent an abstract entity that can be manipulated via {@link WebDriver}. */
public abstract class WebDriverEntity {
  protected final WebDriver driver;
  protected final WebDriverWait wait;

  public WebDriverEntity(WebDriver driver, WebDriverWait wait) {
    this.driver = driver;
    this.wait = wait;
  }
}
