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

if(GlobalVariable.RoleCompany == 'Testing'){
	'connect DB LOS'
	Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()
	
	String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))
	
	ArrayList<String> resultMSData = CustomKeywords.'nap4Data.checkNAP4.getMSData'(sqlconnectionLOS, appno)
	
	'count table MS row di confins'
	ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#shrholder-tab > app-mgmnt-shrholder > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
	 
	index = 0
	
	for(i = 1 ; i <= variable.size(); i++){
		
	'modify object customer ms name'
	modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[2]', true)
	
	'modify object customer ms type'
	modifyNewMSType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[3]', true)
	
	'modify object customer ms share'
	modifyNewMSShare = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[4]', true)
	
	'modify object customer ms is active'
	modifyNewMSIsActive = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[5]', true)
	
	'modify object customer ms is owner'
	modifyNewMSIsOwner = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[6]', true)
	
	'modify object customer ms is signer'
	modifyNewMSIsSigner = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
		'xpath', 'equals', ('//*[@id="shrholder-tab"]/app-mgmnt-shrholder/div[1]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[7]', true)
	
	'verify Ms name'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSName), resultMSData[index++], false)
	
	'verify Ms type'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSType), resultMSData[index++], false)
	
	'verify Ms share'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSShare).replace('  %',''), resultMSData[index++], false)
	
	'verify Ms is active'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSIsActive), resultMSData[index++], false)
	
	'verify Ms is owner'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSIsOwner), resultMSData[index++], false)
	
	'verify Ms is signer'
	WebUI.verifyMatch(WebUI.getText(modifyNewMSIsSigner), resultMSData[index++], false)
	
	'skip 1 colm'
	index++
	}
}

'click save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ManagementShareholder/save and continue'))

