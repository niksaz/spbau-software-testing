package ru.spbau.selenium;

public class User {
  public final String login;
  public final String password;

  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
      "login='" + login + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
