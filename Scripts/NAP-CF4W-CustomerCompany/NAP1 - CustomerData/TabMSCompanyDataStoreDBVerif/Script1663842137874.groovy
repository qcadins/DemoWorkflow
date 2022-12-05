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

'declare arraymatch arraylist'
ArrayList<String> arrayMatch = new ArrayList<String>()

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'call keyword get ms data company store db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.MSDataStoreDBCompany'(sqlconnectionLOS, datafileMS.getValue(
        GlobalVariable.NumofMS, 12), datafileMS.getValue(GlobalVariable.NumofMS, 52))

int arrayindex = 0

'verify shareholder name'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 52).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 53).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify position slik'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 55).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify company type'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 56).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 57).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify share percent'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 58).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is active'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 59).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is owner'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 60).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is addres copy atau tidak'
if (datafileMS.getValue(GlobalVariable.NumofMS, 70).length() > 0) {
    'verify address copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 24).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify RT copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 25).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify RW copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 26).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify Zipcode copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 27).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 28).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 29).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 30).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 31).toUpperCase(), (result[
            arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 71).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify RT'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 72).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify RW'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 73).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify Zipcode'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 74).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 75).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 76).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 77).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify ownership'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 78).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

