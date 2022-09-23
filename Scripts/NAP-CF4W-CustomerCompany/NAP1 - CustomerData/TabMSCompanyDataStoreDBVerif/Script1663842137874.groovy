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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.MSDataStoreDBCompany'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofFamily, 12)).replace('HEADER:', '').replace('[', '').replace(']', '')

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

int arrayindex = 0

'verify shareholder name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        52).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify tax id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        53).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify position slik'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        54).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify company type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        56).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify customer model'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        57).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify share percent'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        58).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is active'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        59).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is owner'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        60).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is addres copy atau tidak'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    71).length() > 0) {
    'verify address copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RT copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RW copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify Zipcode copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else {
    'verify address'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 71).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RT'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 72).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RW'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 73).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify Zipcode'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 74).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 75).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 76).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 77).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 78).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

println(resultarray)

