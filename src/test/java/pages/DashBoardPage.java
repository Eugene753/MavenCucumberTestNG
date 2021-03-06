package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public class DashBoardPage extends CommonMethods {

   @FindBy(id="welcome")
   public WebElement welcomemessage;

   @FindBy(id = "menu_pim_viewPimModule")
   public WebElement pimOption;

   @FindBy(id = "menu_pim_addEmployee")
   public WebElement addEmployeeButton;

   @FindBy(id = "menu_pim_viewEmployeeList")
   public WebElement employeeListOption;

   @FindBy(xpath = "//div[@class='menu']/ul/li")
   public List<WebElement> dashboardtabs;

   @FindBy(id = "menu_admin_viewAdminModule")
   public WebElement adminButton;



   public DashBoardPage(){
       PageFactory.initElements(webdriver.get(),this);
   }

}
