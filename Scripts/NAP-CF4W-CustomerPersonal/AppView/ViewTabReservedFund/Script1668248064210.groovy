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

'Klik tab reserved fund'
WebUI.click(findTestObject('Object Repository/AppView/ReservedFund/RSV Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'12. ReservedFund')
}

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))


'get referantor data arraylist from db'
HashMap<String,ArrayList> resultRSV = CustomKeywords.'dbconnection.VerifyAppView.checkReservedFund'(sqlconnection, appno)
ArrayList<String> totalRSV = resultRSV.get("TotalRSVAmt")
ArrayList<String> listRSV = resultRSV.get("RSVList")


'count rsv table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#reservedFund > div > table > tbody > tr'))

Rsvindex = 1

for (dbindex = 0; dbindex < listRSV.size(); dbindex++) {
	'modify object rsv source'
	modifyNewRsvSrc = WebUI.modifyObjectProperty(findTestObject('AppView/ReservedFund/ModifyObj'), 'xpath', 'equals', "//*[@id='reservedFund']/div/table/tbody/tr["+Rsvindex+"]/td[2]", true)

	'modify object rsv allocation'
	modifyNewRsvAlloc = WebUI.modifyObjectProperty(findTestObject('AppView/ReservedFund/ModifyObj'), 'xpath', 'equals', "//*[@id='reservedFund']/div/table/tbody/tr["+Rsvindex+"]/td[3]", true)

	'modify object rsv amt'
	modifyNewRsvAmt = WebUI.modifyObjectProperty(findTestObject('AppView/ReservedFund/ModifyObj'), 'xpath', 'equals', "//*[@id='reservedFund']/div/table/tbody/tr["+Rsvindex+"]/td[4]", true)

	'verify reserved fund source'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRsvSrc).toUpperCase(), (listRSV[dbindex]).toUpperCase(),
			false))

	dbindex++

	'verify reserved fund allocation'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRsvAlloc).toUpperCase(), (listRSV[dbindex]).toUpperCase(),
			false))

	dbindex++

	'verify reserved fund amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRsvAmt).toUpperCase().replace(",",""), (listRSV[dbindex]).toUpperCase(),
			false))

	Rsvindex++
}


'verify total reserved fund amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/ReservedFund/TotalRsvFundAmt')).replace(",",""), (totalRSV[0].toString()),
		false))


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '12. ReservedFund', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '12. ReservedFund', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '12. ReservedFund', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}

