package ru.omen.app.pages;

import lib.Init;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmeN on 30.05.2016.
 */
public class TestPage extends AnyPages {

    @FindBy(id = "ui-select-departure")
    private WebElement from;

    @FindBy(id = "ui-select-country-to")
    private WebElement to;

    @FindBy(xpath = "//div[contains(@class, 'options_city-from')]//li[contains(@class, 'options-item_city-from') and not(contains(@class, 'item_hgroup'))]")
    private List<WebElement> listFromItem;

    @FindBy(xpath = "//li[contains(@class, 'uis-calendar-week') and contains(@id, 'false')]")
    private List<WebElement> listCalendareItem;

    @FindBy(xpath = "//div[contains(@class, 'options_children-age')]//li//span")
    private List<WebElement> listChildrenAge;
    private String id_inputchild = "ui-select-child-%s";

    private String xpath_main_page_root = "//div[contains(@class, 'main-page-root')]";
    private String xpath_filter_block = "//div[contains(@class, 'filter-block')]";
    private String xpath_hotel_raiting = "//div[contains(@class, 'uis-range_hotels-popup')]//div[contains(@class, 'value_hotels-popup')]";
    private String xpath_beach_line = "//div[contains(@class, 'popup-beach-line-bloc')]//label";
    private String xpath_btn_apply_filte = "//input[contains(@class, 'uis-button_hotels-filter-apply')]";
    private String xpath_btn_calendar = "//span[contains(@class, 'uis-text_departure')]";

    private String id_select_nightMin = "ui-select-nightsMin";
    private String xpath_children = "(//fieldset[contains(@class, 'uis-item__button-group_tourists')])[2]//button[%s]";

    public TestPage() {
        new WebDriverWait(Init.getDriver(), 30)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath(xpath_main_page_root)));

        System.out.println("url open_OK");
    }

    public void setSettings() throws InterruptedException {
        setFrom();
        setTo();
        setDate();
        setChildren();
    }

    private void setFrom() throws InterruptedException {
        Thread.sleep(8000);
        setText(from, "омск");
        Thread.sleep(3000);
        click(listFromItem.get(0));
    }

    private void setTo() {
        getElement(xpath_filter_block).click();
        List<WebElement> list = Init.getDriver().findElements(By.xpath(xpath_beach_line));
        list.get(0).click();
        list.get(1).click();
        list.get(2).click();
        list.clear();

        list = Init.getDriver().findElements(By.xpath(xpath_hotel_raiting));

        String need_value = "7"; // значение из критериев отбора

        for (WebElement elm : list) {
            if (elm.getText().trim().contains(need_value)) {
                elm.click();
            }
        }
        getElement(xpath_btn_apply_filte).click();
    }

    private void setDate() throws InterruptedException {
        getElement(xpath_btn_calendar).click();
        Thread.sleep(1000);
        String date1 = "01.07.2016";
        String date2 = "15.07.2016";

        for (WebElement elm : listCalendareItem) {
            if (elm.getAttribute("id").contains(date1) || elm.getAttribute("id").contains(date2))
                elm.click();
        }

        getElement(By.id(id_select_nightMin)).click();
        //Thread.sleep(1000);
        //((JavascriptExecutor)Init.getDriver()).executeScript("window.scrollBy(0,400)", "");
    }

    public void setChildren() throws InterruptedException {
        String valueCout = "4"; // индекс кнопок 1-4
        String[] valueAge = { "3", "6", "9"};

        click(getElement(String.format(xpath_children, valueCout)));
        Thread.sleep(1000);
        for(int i=0; i<(new Integer(valueCout))-1; i++) {
            getElement(By.id(String.format(id_inputchild, String.valueOf(i)))).click();
            Thread.sleep(1000);
            for(WebElement elm : listChildrenAge) {
                if(elm.getText().trim().contains(valueAge[i])) {
                    elm.click();
                }
            }
            Thread.sleep(500);
        }
    }
}
