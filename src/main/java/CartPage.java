import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ARThouse on 11.12.17.
 */
public class CartPage {
    @FindBy(name = "Lines[0].Quantity")
    WebElement firstLineQtyInput;

    @FindBy(xpath = "//tr[td//input[@name='Lines[0].Quantity']]")
    WebElement firstLine;

    public void setQtyToFirstLine(String qty){
        firstLineQtyInput.sendKeys(Keys.BACK_SPACE);
        firstLineQtyInput.sendKeys(qty);


    }

    public float getPrice() {
        firstLine.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String priceText = firstLine.getText().split("\n")[1].split(" ")[0];
        priceText = priceText.replace("," , ".");
        return Float.valueOf(priceText);
    }

    public float getAmount() {

        String amountText = firstLine.getText().split("\n")[1].split(" ")[2];
        amountText = amountText.replace("," , ".");
        return Float.valueOf(amountText);
    }
}
