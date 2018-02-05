import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class BookDetailedPageSelenide {
    private static  By bookLink = By.xpath("//a[@href='/razdel/1']");
    private static By addToCartImg = By.id("cart-state");
    private static By bookName = By.xpath("//div[@class='page-header']");
    private static By cartQty = By.xpath("//ul[@class='nav pull-right visible-desktop']");
    private static By bookPrice = By.id("price");
    private static By promoCodeButton = By.xpath("//a[@id='promo-code'][@class]");
    private static By promoCodeInput = By.name("ActivationCode");
    private static By discountBanner = By.xpath("//div[not(contains(@class, 'mobile'))]/span[@class='bonus']");
    private static By qtyInCart = By.xpath("//ul[@class='nav pull-right visible-desktop']//a[@href='/my/Cart']") ;


    public String getBookSectionName() {
       return  $(bookLink).getText();
    }
    public static String getCartStateClassName() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  $(addToCartImg).getAttribute("class");
    }

    public static void addToCart() {
        $(addToCartImg).click();
    }





    public static String getBookName() {
        String newBookName = $(bookName).getText();
        if (newBookName.endsWith(".")){
            newBookName = newBookName.substring(0, newBookName.length()-1);
        }
        return newBookName;
    }





    public static String getQtyInCart() {
        return  $(cartQty).getText();
    }

    public static String getBookPrice() {
        return $(bookPrice).getText().split("\n")[0];
    }

    public static void clickPromoCodeButton() {
        $(promoCodeButton).click();
    }

    public static void sendPromoCode(String code) {
        $(promoCodeInput).sendKeys(code);
        $(promoCodeInput).sendKeys(Keys.ENTER);
    }

    public static boolean isDiscountBannerDisplayed() {
        return $(discountBanner).isDisplayed();
    }

    public static int getDiscountRate() {
        String discountBannerText = $(discountBanner).getText();
        discountBannerText = discountBannerText.split(" ")[1];
        discountBannerText = discountBannerText.replace("%", "");
        return Integer.valueOf(discountBannerText);
    }

    public static float getOldPrice() {
        //        WebElement priceInfo = driver.findElement(By.xpath("//div[@id='price']"));
//
//        String priceInfoText = priceInfo.getText();
//
//        String oldPrice = priceInfoText.split("\n")[0];
//        float oldPriceValue =Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));
//
//        String newPrice = priceInfoText.split("\n")[1];
//        float newPriceValue = Float.valueOf(newPrice.split(" ")[0].replace(",", "."));
        String priceInfoText = $(bookPrice).getText();
        String oldPrice = priceInfoText.split("\n")[0];
        return Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));

    }

    public static float getNewPrice() {
        //        WebElement priceInfo = driver.findElement(By.xpath("//div[@id='price']"));
//
//        String priceInfoText = priceInfo.getText();
//
//        String oldPrice = priceInfoText.split("\n")[0];
//        float oldPriceValue =Float.valueOf(oldPrice.split(" ")[0].replace(",", "."));
//
//        String newPrice = priceInfoText.split("\n")[1];
//        float newPriceValue = Float.valueOf(newPrice.split(" ")[0].replace(",", "."));
        String priceInfoText = $(bookPrice).getText();
        String newPrice = priceInfoText.split("\n")[1];
        return Float.valueOf(newPrice.split(" ")[0].replace(",", "."));

    }

    public void goToCartPage() {
        $(qtyInCart).click();


    }
}
