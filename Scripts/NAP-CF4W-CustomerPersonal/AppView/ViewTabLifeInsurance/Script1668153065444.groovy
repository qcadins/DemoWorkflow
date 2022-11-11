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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathAppInquiryPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

GlobalVariable.FlagWarning = 0

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'Klik tab life insurance'
WebUI.click(findTestObject('Object Repository/AppView/LifeInsurance/LifeInsurance Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'7. LifeInsurance')
}

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get life insurance data arraylist from db'
ArrayList<String> resultLIData = CustomKeywords.'dbconnection.VerifyAppView.checkLifeInsurance'(sqlconnection, appno)

'get life insurance data arraylist from db'
ArrayList<String> resultLIObject = CustomKeywords.'dbconnection.VerifyAppView.checkLifeInsuranceObject'(sqlconnection, appno)

int index = 0

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

if(resultLIObject.size()>0 && resultLIObject !=null && variableData.size()>0){
	LIindex = 1
	
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
else if(resultLIObject==null){
	'Verify tabel life insurance object tidak ada'
	checkVerifyEqualOrMatch(WebUI.verifyElementNotPresent(findTestObject('AppView/LifeInsurance/ModifyObj'),2))
}


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. LifeInsurance', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. LifeInsurance', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. LifeInsurance', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}

