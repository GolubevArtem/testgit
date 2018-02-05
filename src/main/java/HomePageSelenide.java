import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.url;

public class HomePageSelenide {
    static By searchField = By.name("searchPhrase");
    static By searchIcon = By.className("icon-search");
    static By bookList = By.xpath("//div[@class='thumbnail small']");
    static  By  sendMessageIcon= By.xpath("//jdiv[@class='hoverl_6R']");
    static By messageField = By.id("message");
    static By sendMessageButton = By.id("submit");
    static By frame = By.id("jivo_container");

    public void searchBook(String bookName) {
        $(searchField).sendKeys(bookName);
        $(searchIcon).click();
    }
    public String getCurrentURL() {
        return url();
    }

    public static void selectBookWithPrice() {
        WebElement bookForBuying = null;
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookForBuying = book;
                break;
            }
        }
        Assert.assertNotEquals(bookForBuying, null, "Noting to buy");
        bookForBuying.findElement(By.xpath(".//a[img]")).click();
    }
    public static String getFirstBookName() {
        String bookName = "";
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookName = bookText.split("\n")[0];
                return bookName;
            }
        }
        return bookName;
    }
    public static String getFirstBookPrice() {
        String bookPrice = "";
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookPrice = bookText.split("\n")[1];
                return bookPrice;
            }
        }
        return bookPrice;
    }

    public  static void sendMessage(String messageText){
        $(sendMessageIcon).click();
        switchTo().frame($(frame));
        $(messageField).waitUntil(Condition.visible, 1000).sendKeys(messageText);
        $(sendMessageButton).click();
    }
}
