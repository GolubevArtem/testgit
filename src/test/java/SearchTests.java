import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class SearchTests {
    static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public static void start(){
        driver = new ChromeDriver();
        driver.get("http://literatura.by/");
    }

    @AfterMethod
    public static void finish(){
        driver.quit();
    }

    @Test
    public static void cartVerification(){
        List<WebElement> bookList = driver.findElements(By.xpath("//div[@class='thumbnail small']"));
        WebElement bookForBuying = null;
        String bookName = "";
        String bookPrice1 = "";
        for (WebElement book:bookList) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookForBuying = book;
                bookName = bookText.split("\n")[0];
                bookPrice1 = bookText.split("\n")[1];
                break;
            }
        }

        Assert.assertNotEquals(bookForBuying, null, "Noting to buy");

        WebElement bookImg = bookForBuying.findElement(By.xpath(".//a[img]"));
        bookImg.click();

        String newBookName = driver.findElement(By.xpath("//div[@class='page-header']")).getText();
        if (newBookName.endsWith(".")){
            newBookName = newBookName.substring(0, newBookName.length()-1);
        }

        Assert.assertEquals(bookName, newBookName, "Book names should be the same");

        WebElement addToCartImg = driver.findElement(By.id("cart-state"));
        String classNameBefore = addToCartImg.getAttribute("class");
        addToCartImg.click();

        addToCartImg = driver.findElement(By.id("cart-state"));
        String classNameAfter = addToCartImg.getAttribute("class");

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");

        //WebElement cartQty = driver.findElement(By.xpath("//ul[@class='nav pull-right visible-desktop']//span[@class='badge badge-white']"));
        WebElement cartQty = driver.findElement(By.xpath("//ul[@class='nav pull-right visible-desktop']"));
        Assert.assertTrue(cartQty.getText().endsWith("1"), "Should be one book in the cart");

        WebElement bookInfo = driver.findElement(By.id("price"));
        String bookPrice2 = bookInfo.getText().split("\n")[0];

        Assert.assertEquals(bookPrice1, bookPrice2, "Prices should be the same");

        WebElement promoCodeButton = driver.findElement(By.xpath("//a[@id='promo-code'][@class]"));
        promoCodeButton.click();

        WebElement promoCodeInput = driver.findElement(By.name("ActivationCode"));
        promoCodeInput.sendKeys("813524");
        promoCodeInput.sendKeys(Keys.ENTER);

        WebElement discountBanner = driver.findElement(By.xpath("//div[not(contains(@class, 'mobile'))]/span[@class='bonus']"));

        Assert.assertTrue(discountBanner.isDisplayed(), "Discount banner should be displayed");

        String discountBannerText = discountBanner.getText();
        discountBannerText = discountBannerText.split(" ")[1];
        discountBannerText = discountBannerText.replace("%", "");

        int discountRate = Integer.valueOf(discountBannerText);

        WebElement priceInfo = driver.findElement(By.xpath("//div[@id='price']"));

        String priceInfoText = priceInfo.getText();

        String oldPrice = priceInfoText.split("\n")[0];
        float oldPriceValue =Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));

        String newPrice = priceInfoText.split("\n")[1];
        float newPriceValue = Float.valueOf(newPrice.split(" ")[0].replace(",", "."));

        float diff = oldPriceValue*(100-discountRate)/100-newPriceValue;

        Assert.assertTrue(diff<0.01 &&diff>-0.01, "Discount should be calculated right way");

    }

    @Test
    public static void promoVerification(){
        List<WebElement> bookList = driver.findElements(By.xpath("//div[@class='thumbnail small']"));
        WebElement bookForBuying = null;
        for (WebElement book:bookList) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookForBuying = book;
                break;
            }
        }

        Assert.assertNotEquals(bookForBuying, null, "Noting to buy");

        WebElement bookImg = bookForBuying.findElement(By.xpath(".//a[img]"));
        bookImg.click();

        WebElement addToCartImg = driver.findElement(By.id("cart-state"));
        String classNameBefore = addToCartImg.getAttribute("class");
        addToCartImg.click();

        addToCartImg = driver.findElement(By.id("cart-state"));
        String classNameAfter = addToCartImg.getAttribute("class");

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");



        int d = 0;
    }

    @Test(priority = 0)
    public static void HelloWorldTest(){
        WebElement searchField = driver.findElement(By.name("searchPhrase"));
        searchField.sendKeys("Hello, world!");

        driver.findElement(By.className("icon-search")).click();

        String currentURL = driver.getCurrentUrl();

        Assert.assertTrue(currentURL.contains("/kniga/"), "URL should contain '/kniga/'");

        WebElement bookLink = driver.findElement(By.xpath("//a[@href='/razdel/1']"));

        Assert.assertEquals(bookLink.getText(), "Книги", "Link should contain 'Книги'");

    }
    @Test(priority = 1, groups = {"group1"})
      public static void JavaTest(){

        WebElement searchField = driver.findElement(By.name("searchPhrase"));
        searchField.sendKeys("Java");

        driver.findElement(By.className("icon-search")).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        WebElement breadCrumbItem = driver.findElement(By.xpath("//ul[@class='breadcrumb']/li[last()]"));
        String breadCrumbItemText = breadCrumbItem.getText();

        Assert.assertTrue(breadCrumbItemText.contains("Java"), "Text should contain 'Java'");
    }

    @Test(priority = 0, groups = {"search", "group1"})
      public static void WindowsTest(){
        WebElement searchField = driver.findElement(By.name("searchPhrase"));
        searchField.sendKeys("Windows");

        driver.findElement(By.className("icon-search")).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        WebElement breadCrumbItem = driver.findElement(By.xpath("//ul[@class='breadcrumb']/li[last()]"));
        String breadCrumbItemText = breadCrumbItem.getText();

        Assert.assertTrue(breadCrumbItemText.contains("Windows"), "Text should contain 'Windows'");
    }

    @Test(dataProvider="getBookNames", priority = 5, groups = {"search"})
    public static void bookTest(String bookName){
        //String bookName = "Windows";

        WebElement searchField = driver.findElement(By.name("searchPhrase"));
        searchField.sendKeys(bookName);

        driver.findElement(By.className("icon-search")).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        WebElement breadCrumbItem = driver.findElement(By.xpath("//ul[@class='breadcrumb']/li[last()]"));
        String breadCrumbItemText = breadCrumbItem.getText();

        Assert.assertTrue(breadCrumbItemText.contains(bookName), "Text should contain '" + bookName + "'");

        String xpathForResultNote = "//small[contains(text(), '%bookName%')]";
        xpathForResultNote = xpathForResultNote.replace("%bookName%", bookName);


        WebElement resultNote = driver.findElement(By.xpath(xpathForResultNote));
        String resultNoteText = resultNote.getText();

        Assert.assertTrue(resultNoteText.contains(bookName), "Text should contain '" + bookName + "'");

        List<WebElement> bookTitles = driver.findElements(By.xpath("//a[@class='product-name']"));
        boolean containsKeyWord = false;
        for (WebElement bookTitle: bookTitles){
            if (bookTitle.getText().contains(bookName)){
                containsKeyWord = true;
                break;
            }
        }
        Assert.assertTrue(containsKeyWord, "Book titles should contain '" + bookName + "'");

        List<WebElement> bookPrices = driver.findElements(By.xpath("//div[@class='tocart']"));
        boolean priceIsValid = true;
        for (WebElement bookPrice: bookPrices){
            priceIsValid = priceIsValid && (bookPrice.getText().endsWith("руб.") ||bookPrice.getText().endsWith("на складе") );
        }

        Assert.assertTrue(priceIsValid, "Price should be valid");

    }

    @DataProvider
    public Object[][] getBookNames(){
        return new Object[][]{
                {"Java"}, {"Windows"}, {"USA"}, {"Scrum"}
        };
    }
}
