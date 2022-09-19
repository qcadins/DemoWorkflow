import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#family-tab > app-family-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

for(i = 1; i <= variableData.size(); i++){
	
	'modify object family name'
	modifyNewFamilyName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
		'xpath', 'equals', '//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[2]', true)
	
	'modify object button edit'
	modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
		'xpath', 'equals', '//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr['+ i +']/td[6]/span/span[1]/span/span/span/span/span/a/i', true)
	
	'modify object button delete'
	modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
		'xpath', 'equals', '//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr['+ i +']/td[6]/span/span[2]/span/span/span/span/span/a/i', true)
	
	'Loop Multiple family data'
	for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
		if(WebUI.getText(modifyNewFamilyName).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 19))){
		if(WebUI.verifyElementPresent(modifyNewButtonEdit, 5, FailureHandling.OPTIONAL)){
			
		
		'click button edit'
		WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)	
			
			
		}
		
		
		}else {
			if(GlobalVariable.NumofFamily == Integer.parseInt(GlobalVariable.CountAFamily) + 1){
				if(WebUI.verifyElementPresent(modifyNewButtonDelete, 5, FailureHandling.OPTIONAL)){
					
				
				'click button Delete'
				WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)
					
				'accept alert'
				WebUI.acceptAlert()
				}
			}
		}
	}
}
