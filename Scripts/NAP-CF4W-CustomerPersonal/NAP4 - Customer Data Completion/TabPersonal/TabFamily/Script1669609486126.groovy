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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

if(GlobalVariable.Role == 'Testing'){
	'connect DB LOS'
	Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()
	
	String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))
	
	ArrayList<String> resultMSData = CustomKeywords.'nap4Data.checkNAP4.getFamilyData'(sqlconnectionLOS, appno)
	
	'count table MS row di confins'
	ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#family-tab > app-family-tab > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
	 
	index = 0
	
	for(i = 1 ; i <= variable.size(); i++){
		
	'modify object customer family name'
	modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="family-tab"]/app-family-tab/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[2]', true)
	
	'modify object customer family realtion'
	modifyNewMSType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="family-tab"]/app-family-tab/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[3]', true)
	
	'modify object customer family birth place'
	modifyNewMSShare = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="family-tab"]/app-family-tab/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[4]', true)
	
	'modify object customer family birth date'
	modifyNewMSIsActive = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="family-tab"]/app-family-tab/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[5]', true)
	
	'verify family name'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSName), resultMSData[index++], false)
	
	'verify family relation'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSType), resultMSData[index++], false)
	
	'verify birth place'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSShare).replace('  %',''), resultMSData[index++], false)
	
	'verify birth date'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSIsActive), resultMSData[index++], false)
	
	}
}

'click save and continue button'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/Family/button_Save  Continue'))

