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

TestData datafile

String SheetExcel

int returnRow

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

if(GlobalVariable.APPSTEP == 'COMMISSION'){
	
'declare datafileCommission'
datafile = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

SheetExcel = '12.TabCommissionData'

}else if(GlobalVariable.APPSTEP == 'RESERVED FUND'){

'declare datafileReservedFund'
datafile = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData')

SheetExcel = '13.TabReservedFundData'

}

'Mengambil nilai row keberapa dimulai data return pada excel'
returnRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, SheetExcel,
	'Return Commission & Reserved Fund') + 1

'get data from db'
ArrayList<String> resultHeader = CustomKeywords.'dbConnection.CustomerDataVerif.checkReturnHandlingH'(sqlconnectionLOS, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

ArrayList<String> resultDetail = CustomKeywords.'dbConnection.CustomerDataVerif.checkReturnHandlingD'(sqlconnectionLOS, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

String appcurrstep = CustomKeywords.'dbConnection.checkStep.checkAppCurrStep'(sqlconnectionLOS, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

String applaststep = CustomKeywords.'dbConnection.checkStep.checkLastStep'(sqlconnectionLOS, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindex = 0

'verify app Curr step'
arrayMatch.add(WebUI.verifyMatch(appcurrstep.toUpperCase(), 'RTN', false, FailureHandling.OPTIONAL))

'verify app last step'
arrayMatch.add(WebUI.verifyMatch(applaststep.toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Status from Header'
arrayMatch.add(WebUI.verifyMatch('REQUEST', (resultHeader[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify reason from Header'
arrayMatch.add(WebUI.verifyMatch(datafile.getValue(GlobalVariable.NumofColm, returnRow+3).toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify NOTE from Header'
arrayMatch.add(WebUI.verifyMatch(datafile.getValue(GlobalVariable.NumofColm, returnRow+4).toUpperCase(), (resultHeader[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

arrayindex = 0

'verify Return Task from Detail'
arrayMatch.add(WebUI.verifyMatch('EDIT APPLICATION DATA'.toUpperCase(), (resultDetail[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify NOTE from Detail'
arrayMatch.add(WebUI.verifyMatch(datafile.getValue(GlobalVariable.NumofColm, returnRow+4).toUpperCase(), (resultDetail[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify Status from Detail'
arrayMatch.add(WebUI.verifyMatch('REQUEST', (resultDetail[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'(SheetExcel, GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}