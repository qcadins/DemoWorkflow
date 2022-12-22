import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData')

'get data from db'
ArrayList<String> resultHeader = CustomKeywords.'dbConnection.CustomerDataVerif.checkReturnHandlingH'(sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

ArrayList<String> resultDetail = CustomKeywords.'dbConnection.CustomerDataVerif.checkReturnHandlingD'(sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

String appcurrstep = CustomKeywords.'dbConnection.checkStep.checkAppCurrStep'(sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

String applaststep = CustomKeywords.'dbConnection.checkStep.checkLastStep'(sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindex = 0

'verify app Curr step'
arrayMatch.add(WebUI.verifyMatch(appcurrstep.toUpperCase(), 'RTN', false, FailureHandling.OPTIONAL))

'verify app last step'
arrayMatch.add(WebUI.verifyMatch(applaststep.toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Status from Header'
arrayMatch.add(WebUI.verifyMatch('REQUEST', (resultHeader[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify reason from Header'
arrayMatch.add(WebUI.verifyMatch(datafileCommission.getValue(GlobalVariable.NumofColm, 56).toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(), 
		false, FailureHandling.OPTIONAL))

'verify NOTE from Header'
arrayMatch.add(WebUI.verifyMatch(datafileCommission.getValue(GlobalVariable.NumofColm, 57).toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

arrayindex = 0

'verify Return Task from Detail'
arrayMatch.add(WebUI.verifyMatch('EDIT APPLICATION DATA'.toUpperCase(), (resultDetail[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify NOTE from Detail'
arrayMatch.add(WebUI.verifyMatch(datafileCommission.getValue(GlobalVariable.NumofColm, 57).toUpperCase(), (resultDetail[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify Status from Detail'
arrayMatch.add(WebUI.verifyMatch('REQUEST', (resultDetail[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}