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

'Klik tab application'
WebUI.click(findTestObject('Object Repository/AppView/Application/Application Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'5. Application')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get mo data arraylist from db'
ArrayList<String> resultMO = CustomKeywords.'appView.verifyAppView.checkMOInfo'(sqlconnection, appno)

'get app & restructuring data arraylist from db'
ArrayList<String> resultAppRestr = CustomKeywords.'appView.verifyAppView.checkAppInfoAndRestructuringData'(sqlconnection, appno)

'get app attribute data arraylist from db'
ArrayList<String> resultAttr = CustomKeywords.'appView.verifyAppView.checkApplicationAttribute'(sqlconnection, appno)

'declare int index'
int index = 0

'verify mo spv'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/SPV')).toUpperCase(),
		(resultMO[index++]).toUpperCase(), false))

'verify mo officer'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/Officer')).toUpperCase(),
		(resultMO[index++]).toUpperCase(), false))

'verify mo notes'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/MONotes')),"(?i)"+
		(resultMO[index++]), true))

'verify mo recommendation'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/MORecomm')).toUpperCase(),
		(resultMO[index++]).toUpperCase(), false))

index = 0

'verify App source'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/AppSrc')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify first inst type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/FirstInstType')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify paymentfreq'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/PaymentFreq')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify tenor'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/Tenor')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify num of inst'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/NumofInst')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify interest type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/InterestType')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify floating period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/FloatingPeriod')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify inst scheme'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/InstScheme')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify way of payment'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/WOP')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify customer notification by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/CustNotif')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify characteristic of credit'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/COC')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify previous agreement'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/PrevAgrmnt')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify way of restructure'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/WOR')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify SLIK ECO'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/ECOSEC')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

'verify MOU No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/MOUNo')).toUpperCase(),
		(resultAppRestr[index++]).toUpperCase(), false))

index = 0

'verify blacklist appi'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/BlacklistAPPI')).toUpperCase(),
		(resultAttr[index++]).toUpperCase(), false))

'verify appi score'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/APPIScore')).toUpperCase(),
		(resultAttr[index++]).toUpperCase(), false))

'verify date app data'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/DATEAPPDATA')).toUpperCase(),
		(resultAttr[index++]).toUpperCase(), false))

'verify ref master app data'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/REFMASTERAPPDATA')).toUpperCase(),
		(resultAttr[index++]).toUpperCase(), false))

'verify jumlah asset'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Application/JumlahAsset')).toUpperCase(),
		(resultAttr[index++]).toUpperCase(), false))

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5. Application', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5. Application', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5. Application', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}


