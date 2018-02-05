import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookDetailedPage {
    private By bookLink = By.xpath("//a[@href='/razdel/1']");
    private By addToCartImg = By.id("cart-state");
    private By bookName = By.xpath("//div[@class='page-header']");
    private By cartQty = By.xpath("//ul[@class='nav pull-right visible-desktop']");
    private By bookPrice = By.id("price");
    private By promoCodeButton = By.xpath("//a[@id='promo-code'][@class]");
    private By promoCodeInput = By.name("ActivationCode");
    private By discountBanner = By.xpath("//div[not(contains(@class, 'mobile'))]/span[@class='bonus']");
    private By qtyInCart = By.xpath("//ul[@class='nav pull-right visible-desktop']//a[@href='/my/Cart']") ;

    WebDriver driver;

    public BookDetailedPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getBookSectionName() {
        return driver.findElement(bookLink).getText();
    }

    public String getCartStateClassName() {
        //WebDriverWait wait = new WebDriverWait(driver,  20);
        //WebElement addToCart = wait.until(ExpectedConditions.attributeContains());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(addToCartImg).getAttribute("class");
    }

    public void addToCart() {
        driver.findElement(addToCartImg).click();
    }

    public String getBookName() {
        String newBookName = driver.findElement(bookName).getText();
        if (newBookName.endsWith(".")){
            newBookName = newBookName.substring(0, newBookName.length()-1);
        }
        return newBookName;
    }

    public String getQtyInCart() {
        return driver.findElement(cartQty).getText();
    }

    public String getBookPrice() {
        return driver.findElement(bookPrice).getText().split("\n")[0];
    }

    public void clickPromoCodeButton() {
        driver.findElement(promoCodeButton).click();
    }

    public void sendPromoCode(String code) {
        driver.findElement(promoCodeInput).sendKeys(code);
        driver.findElement(promoCodeInput).sendKeys(Keys.ENTER);
    }

    public boolean isDiscountBannerDisplayed() {
        return driver.findElement(discountBanner).isDisplayed();
    }

    public int getDiscountRate() {
        String discountBannerText = driver.findElement(discountBanner).getText();
        discountBannerText = discountBannerText.split(" ")[1];
        discountBannerText = discountBannerText.replace("%", "");
        return Integer.valueOf(discountBannerText);
    }

    public float getOldPrice() {
        //        WebElement priceInfo = driver.findElement(By.xpath("//div[@id='price']"));
//
//        String priceInfoText = priceInfo.getText();
//
//        String oldPrice = priceInfoText.split("\n")[0];
//        float oldPriceValue =Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));
//
//        String newPrice = priceInfoText.split("\n")[1];
//        float newPriceValue = Float.valueOf(newPrice.split(" ")[0].replace(",", "."));
        String priceInfoText = driver.findElement(bookPrice).getText();
        String oldPrice = priceInfoText.split("\n")[0];
        return Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));

    }

    public float getNewPrice() {
        //        WebElement priceInfo = driver.findElement(By.xpath("//div[@id='price']"));
//
//        String priceInfoText = priceInfo.getText();
//
//        String oldPrice = priceInfoText.split("\n")[0];
//        float oldPriceValue =Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));
//
//        String newPrice = priceInfoText.split("\n")[1];
//        float newPriceValue = Float.valueOf(newPrice.split(" ")[0].replace(",", "."));
        String priceInfoText = driver.findElement(bookPrice).getText();
        String newPrice = priceInfoText.split("\n")[1];
        return Float.valueOf(newPrice.split(" ")[0].replace(",", "."));

    }

    public void goToCartPage() {
        driver.findElement(qtyInCart).click();


    }
}
