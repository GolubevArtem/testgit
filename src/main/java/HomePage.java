import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.util.List;

public class HomePage {
    @FindBy(name = "searchPhrase")
    WebElement searchField;
    @FindBy(className = "icon-search")
    WebElement searchIcon;
    @FindAll({@FindBy( xpath = "//div[@class='thumbnail small']")})
    List<WebElement>     bookList;

    public void searchBook(String bookName) {
        searchField.sendKeys(bookName);
        searchIcon.click();

    }
    public String getCurrentURL(WebDriver driver) {
        return driver.getCurrentUrl();
    }
    public void selectBookWithPrice() {
        WebElement bookForBuying = null;
        for (WebElement book:bookList) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookForBuying = book;
                break;
            }
        }
        Assert.assertNotEquals(bookForBuying, null, "Noting to buy");
        bookForBuying.findElement(By.xpath(".//a[img]")).click();
    }
    public String getFirstBookName() {
        String bookName = "";
        for (WebElement book:bookList) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookName = bookText.split("\n")[0];
                return bookName;
            }
        }
        return bookName;
    }
    public String getFirstBookPrice() {
        String bookPrice = "";
        for (WebElement book:bookList) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookPrice = bookText.split("\n")[1];
                return bookPrice;
            }
        }
        return bookPrice;
    }
}
