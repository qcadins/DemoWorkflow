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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

ArrayList<String> arrayMatch = new ArrayList<String>()

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.MSDataStoreDBCompanyLOOKUP'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofFamily, 12), findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofFamily, 50)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    }
    
    if ((resultarray[i]).equalsIgnoreCase('TRUE')) {
        (resultarray[i]) = 'Yes'
    } else if ((resultarray[i]).equalsIgnoreCase('FALSE')) {
        (resultarray[i]) = 'No'
    }
}

println(resultarray)

int arrayindex = 0

int confinsindex = 0

'verify shareholder name'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify position slik'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify company type'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify share percent'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify is active'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify is owner'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RT'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify Zipcode'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyEqual((Math.round(Double.parseDouble(GlobalVariable.confinsdata[confinsindex++]))), Math.round(Double.parseDouble((resultarray[arrayindex++]))), FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify ownership'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.confinsdata[confinsindex++]).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        1, GlobalVariable.NumofFamily - 1, GlobalVariable.ReasonFailedStoredDB)
}

