package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {

    @FindBy(className = "title")
    private WebElement title;

    public InventoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return title.getText();
    }
}
