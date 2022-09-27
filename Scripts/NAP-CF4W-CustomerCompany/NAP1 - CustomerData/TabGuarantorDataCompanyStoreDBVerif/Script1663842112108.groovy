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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.GuarantorDataStoreDBCompany'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 12)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    }
}

int arrayindex = 0

'verify relationship'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify guarantor name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify tax id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify company type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify customer model'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 21).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify copy addres atau tidak'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 23).equalsIgnoreCase('Yes')) {
    'verify address copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rt copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RW copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify zipcode copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else {
    'verify address'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify Rt'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rw'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify zipcode'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}


