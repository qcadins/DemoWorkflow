import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryCompany)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

GlobalVariable.FlagWarning = 0

'click tab guarantor'
WebUI.click(findTestObject('Object Repository/AppView/Guarantor/TabGuarantor'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	
	'write status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'3. Guarantor')
}

'get appno dari UI confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get arraylist guarantor from db'
ArrayList<String> resultGuar = CustomKeywords.'appView.verifyAppView.checkGuarantor'(sqlconnectionLOS, appno)

'count guarantor table'
ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guaAll > table > tbody tr'))

index = 0

dbindex = 0

for (Guarindex = 1; Guarindex <= variableData.size(); Guarindex++) {
	'modify object guar Name'
	modifyNewGuarName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
			('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[1]', true)
			
	'modify object Guar Type'
	modifyNewGuarType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
			('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[2]', true)
			
	'modify object relationship'
	modifyNewRelationship = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
			'equals', ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[3]', true)
			
	'modify object Mobile Phone'
	modifyNewMobilePhone = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
			'equals', ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[4]', true)
			
	'modify object button detail'
	modifyNewButtonDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath',
			'equals', ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[5]/a/i', true)
			
	if (WebUI.verifyMatch(WebUI.getText(modifyNewGuarName).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), false, 
			FailureHandling.OPTIONAL)) {
		'verify guarantor name'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewGuarName).toUpperCase(), (resultGuar[dbindex++]).toUpperCase(), 
				false))
				
		'verify guarantor type'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewGuarType).toUpperCase(), (resultGuar[dbindex++]).toUpperCase(), 
				false))
				
		'verify guarantor relationship'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRelationship).toUpperCase(), (resultGuar[dbindex++]).toUpperCase(), 
				false))
				
		'verify guarantor mobile phone'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMobilePhone).toUpperCase(), (resultGuar[dbindex++]).toUpperCase(), 
				false))
	}
	
	'get guarantor name'
	GlobalVariable.CustDetailName = WebUI.getText(modifyNewGuarName)
			
	'get guarantor type' 
	guartype = WebUI.getText(modifyNewGuarType)
			
	'click button detail'
	WebUI.click(modifyNewButtonDetail)
			
	if(guartype.equalsIgnoreCase('PERSONAL')){
		'call test case verify view Personal detail'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabCustomer-CustPersonalDetail'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	}else if(guartype.equalsIgnoreCase('COMPANY')){
		'call test case verify view Company detail'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabCustomer-CustCompanyDetail'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	'clik button X'
	WebUI.click(findTestObject('AppView/CustomerMainData/attributelist Company/buttonX'))
}


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}