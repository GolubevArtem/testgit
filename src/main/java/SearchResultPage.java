import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultPage {
    private By breadCrumbItem = By.xpath("//ul[@class='breadcrumb']/li[last()]");
    private By bookTitle = By.xpath("//a[@class='product-name']");
    private By bookPrice = By.xpath("//div[@class='tocart']");

    WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getBreadCrumbItemText() {
        return driver.findElement(breadCrumbItem).getText();
    }

    public String getResultNoteText(String bookName) {
        String xpathForResultNote = "//small[contains(text(), '%bookName%')]";
        xpathForResultNote = xpathForResultNote.replace("%bookName%", bookName);
        WebElement resultNote = driver.findElement(By.xpath(xpathForResultNote));
        return resultNote.getText();
    }

    public boolean containsKeyWord(String bookName) {
        List<WebElement> bookTitles = driver.findElements(bookTitle);
        boolean containsKeyWord = false;
        for (WebElement bookTitle: bookTitles){
            if (bookTitle.getText().contains(bookName)){
                containsKeyWord = true;
                break;
            }
        }
        return containsKeyWord;
    }

    public boolean priceIsValid() {
        List<WebElement> bookPrices = driver.findElements(bookPrice);
        boolean priceIsValid = true;
        for (WebElement bookPrice: bookPrices){
            priceIsValid = priceIsValid && (bookPrice.getText().endsWith("руб.") ||bookPrice.getText().endsWith("на складе") );
            return priceIsValid;
        }
        return priceIsValid;
    }
}
