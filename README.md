# YouTrack web-interface testing with Selenium

First, you need to obtain the [YouTrack jar](https://www.jetbrains.com/youtrack/download/get_youtrack.html) and run it on port 8080.

Then, download the [Selenium driver](https://www.seleniumhq.org/download/) for Google Chrome and place
it in `drivers` folder. It should be named `chromedriver` for Unix platforms and `chromedriver.exe` for Windows.

Run the tests with gradle on Unix:
```
./gradlew test
```

Or on Windows:
```
gradlew.bat test
```
