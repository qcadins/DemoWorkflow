import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
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
import org.openqa.selenium.WebElement as WebElement

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB LOS'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'Klik tab life insurance'
WebUI.click(findTestObject('Object Repository/AppView/LifeInsurance/LifeInsurance Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'7. LifeInsurance')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get life insurance data arraylist from db'
ArrayList<String> resultLIData = CustomKeywords.'appView.verifyAppView.checkLifeInsurance'(sqlconnection, appno)

'get life insurance data object arraylist from db'
ArrayList<String> resultLIObject = CustomKeywords.'appView.verifyAppView.checkLifeInsuranceObject'(sqlconnection, appno)

'declare index'
int index = 0

'Pengcekan ada tidaknya lifeinsurancedata from db'
if(resultLIData.get(0)=="-"){
	'verify cover life insurance'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/CoverLifeInsurance')).toUpperCase(),
			"NO", false))
}
else{
	'verify cover life insurance'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/CoverLifeInsurance')).toUpperCase(),
			"YES", false))
}

'verify life insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/LifeInsuranceCompany')).toUpperCase(),
		(resultLIData[index++]).toUpperCase(), false))

'verify total premi to cust'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/PremiCust')).toUpperCase().replace(",",""),
		(resultLIData[index++]).toUpperCase(), false))

'verify total premi from insco'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/PremiInsco')).toUpperCase().replace(",",""),
		(resultLIData[index++]).toUpperCase(), false))

'verify admin fee to cust'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/AdmFeeCust')).toUpperCase().replace(",",""),
		(resultLIData[index++]).toUpperCase(), false))

'verify admin fee from insco'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/AdmFeeInsco')).toUpperCase().replace(",",""),
		(resultLIData[index++]).toUpperCase(), false))

'verify capitalized premi'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/LifeInsurance/CptlzPremi')).toUpperCase().replace(",",""),
		(resultLIData[index++]).toUpperCase(), false))

'count life insurance object table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mat-tab-content-0-6 > div > app-life-insurance > lib-ucgridview > div > table > tbody > tr'))

'Pengecekan ada tidaknya life insurance data object from db & from confins'
if(resultLIObject.size()>0 && resultLIObject !=null && variableData.size()>0){
	LIindex = 1
	'looping life insurance data object from db'
	for (dbindex = 0; dbindex < resultLIObject.size(); dbindex++) {
		'modify object name'
		modifyNewName = WebUI.modifyObjectProperty(findTestObject('AppView/LifeInsurance/ModifyObj'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-6']/div/app-life-insurance/lib-ucgridview/div/table/tbody/tr["+LIindex+"]/td[2]", true)
	
		'modify object age'
		modifyNewAge = WebUI.modifyObjectProperty(findTestObject('AppView/LifeInsurance/ModifyObj'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-6']/div/app-life-insurance/lib-ucgridview/div/table/tbody/tr["+LIindex+"]/td[3]", true)
	
		'modify object cust type'
		modifyNewCustType = WebUI.modifyObjectProperty(findTestObject('AppView/LifeInsurance/ModifyObj'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-6']/div/app-life-insurance/lib-ucgridview/div/table/tbody/tr["+LIindex+"]/td[4]", true)
	
		'verify name'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewName).toUpperCase(), (resultLIObject[dbindex]).toUpperCase(),
				false))
	
		dbindex++
	
		'verify age'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAge).toUpperCase(), (resultLIObject[dbindex]).toUpperCase(),
				false))
	
		dbindex++
	
		'verify cust type'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCustType).toUpperCase(), (resultLIObject[dbindex]).toUpperCase(),
				false))
	
		LIindex++
	}
}


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. LifeInsurance', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. LifeInsurance', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. LifeInsurance', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}