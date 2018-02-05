import com.codeborne.selenide.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;

public class SearchTestsPageObject {
    static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public static void start(){
        System.setProperty("webdriver.chrome.driver","/Users/ARThouse/Drivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");

        Configuration.timeout = 10000;

        open("http://literatura.by");
    }

    @AfterMethod
    public static void finish(){

    }

    @Test
    public static void cartVerification(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        BookDetailedPage bookDetailedPage = new BookDetailedPage(driver);

        String bookName = homePage.getFirstBookName();
        String bookPrice1 = homePage.getFirstBookPrice();
        homePage.selectBookWithPrice();

        String newBookName = bookDetailedPage.getBookName();
        Assert.assertEquals(bookName, newBookName, "Book names should be the same");

        String classNameBefore = bookDetailedPage.getCartStateClassName();
        bookDetailedPage.addToCart();

        String classNameAfter = bookDetailedPage.getCartStateClassName();

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");

        String qtyInCart = bookDetailedPage.getQtyInCart();
        Assert.assertTrue(qtyInCart.endsWith("1"), "Should be one book in the cart");

        String bookPrice2 = bookDetailedPage.getBookPrice();
        Assert.assertEquals(bookPrice1, bookPrice2, "Prices should be the same");

        bookDetailedPage.clickPromoCodeButton();

        bookDetailedPage.sendPromoCode("813524");

        boolean isDiscountBannerDisplayed = bookDetailedPage.isDiscountBannerDisplayed();
        Assert.assertTrue(isDiscountBannerDisplayed, "Discount banner should be displayed");

        int discountRate = bookDetailedPage.getDiscountRate();

        float oldPriceValue = bookDetailedPage.getOldPrice();
        float newPriceValue = bookDetailedPage.getNewPrice();

        float diff = oldPriceValue*(100-discountRate)/100-newPriceValue;
        Assert.assertTrue(diff<0.01 &&diff>-0.01, "Discount should be calculated right way");

    }

    @Test
    public static void cartPageVerification(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        BookDetailedPage bookDetailedPage = new BookDetailedPage(driver);
        CartPage cartPage = PageFactory.initElements(driver, CartPage.class);

        homePage.selectBookWithPrice();
        bookDetailedPage.addToCart();
        bookDetailedPage.goToCartPage();

        int newQty = 2;
        cartPage.setQtyToFirstLine(new Integer(newQty).toString());

        float price = cartPage.getPrice();
        float amount = cartPage.getAmount();

        Assert.assertEquals(amount, price*newQty, "хто-та урот с ценой");

        int d = 0;
    }

    @Test
    public static void promoVerification(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        //HomePage homePage = new HomePage(driver);
        BookDetailedPage bookDetailedPage= new BookDetailedPage(driver);

        homePage.selectBookWithPrice();

        String classNameBefore = bookDetailedPage.getCartStateClassName();
        bookDetailedPage.addToCart();
        String classNameAfter = bookDetailedPage.getCartStateClassName();

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");

    }

    @Test(priority = 0)
    public static void HelloWorldTest(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        BookDetailedPage bookDetailedPage= new BookDetailedPage(driver);

        homePage.searchBook("Hello, world!");

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/kniga/"), "URL should contain '/kniga/'");

        String bookName = bookDetailedPage.getBookSectionName();
        Assert.assertEquals(bookName, "Книги", "Link should contain 'Книги'");
    }

    @Test(priority = 1, groups = {"group1"})
      public static void JavaTest(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        SearchResultPage searchResultPage = new SearchResultPage(driver);

        homePage.searchBook("Java");

        String currentURL = homePage.getCurrentURL(driver);
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String resultNoteText = searchResultPage.getResultNoteText("Java");
        Assert.assertTrue(resultNoteText.contains("Java"), "Text should contain 'Java'");
    }

    @Test(priority = 0, groups = {"search", "group1"})
      public static void windowsTest(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        SearchResultPage searchResultPage = new SearchResultPage(driver);

        homePage.searchBook("Windows");

        String currentURL = homePage.getCurrentURL(driver);
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String resultNoteText = searchResultPage.getResultNoteText("Windows");
        Assert.assertTrue(resultNoteText.contains("Windows"), "Text should contain 'Windows'");
    }

    @Test(dataProvider="getBookNames", priority = 5, groups = {"search"})
    public static void bookTest(String bookName){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        SearchResultPage searchResultPage = new SearchResultPage(driver);

        homePage.searchBook(bookName);
        String currentURL = homePage.getCurrentURL(driver);
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String breadCrumbItemText = searchResultPage.getBreadCrumbItemText();
        Assert.assertTrue(breadCrumbItemText.contains(bookName), "Text should contain '" + bookName + "'");

        String resultNoteText = searchResultPage.getResultNoteText(bookName);
        Assert.assertTrue(resultNoteText.contains(bookName), "Text should contain '" + bookName + "'");

        boolean containsKeyWord = searchResultPage.containsKeyWord(bookName);
        Assert.assertTrue(containsKeyWord, "Book titles should contain '" + bookName + "'");

        boolean priceIsValid = searchResultPage.priceIsValid();
        Assert.assertTrue(priceIsValid, "Price should be valid");
    }


    @DataProvider
    public Object[][] getBookNames(){
        return new Object[][]{
                {"Java"}, {"Windows"}, {"USA"}, {"Scrum"}
        };
    }
}
