package tests;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverManager;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    // Константы
    private static final String BASE_URL = "https://www.saucedemo.com";

    // DataProvider — источник данных (браузеры)
    @DataProvider(name = "browsers")
    public Object[][] getBrowsers() {
        return new Object[][]{
                {"chrome"},
                {"firefox"}
        };
    }

    // 3. После каждого теста — закрываем драйвер
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // 4. Тесты с параметром browser
    @Test(dataProvider = "browsers")
    public void successfulLogin(String browser) {

        driver = DriverManager.getDriver(browser);
        driver.get(BASE_URL);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertEquals("Products", inventoryPage.getPageTitle());
    }

    @Test(dataProvider = "browsers")
    public void loginWithWrongPassword(String browser) {

        driver = DriverManager.getDriver(browser); // 1. Создаём драйвер
        driver.get(BASE_URL);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();

        assertTrue(loginPage.isErrorMessageDisplayed());
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }

    @Test(dataProvider = "browsers")
    public void lockedUserLogin(String browser) {

        driver = DriverManager.getDriver(browser);
        driver.get(BASE_URL);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertTrue(loginPage.isErrorMessageDisplayed());
        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(dataProvider = "browsers")
    public void emptyFieldsLogin(String browser) {

        driver = DriverManager.getDriver(browser);
        driver.get(BASE_URL);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        loginPage.clickLogin();

        assertTrue(loginPage.isErrorMessageDisplayed());
        assertTrue(loginPage.getErrorMessage().contains("Epic sadface: Username is required"));
    }
}