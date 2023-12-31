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
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import groovy.sql.Sql as Sql

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare arraylist arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'call keyword get customer data store DB'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.CustomerDataStoreDBCompany'(sqlconnectionLOS, 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13), findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        19))

int arrayindex = 0

'verify customer name'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify company type'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RT'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 26).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify Zipcode'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify keluraham'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 29).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'veirfy ownership'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
	
}

