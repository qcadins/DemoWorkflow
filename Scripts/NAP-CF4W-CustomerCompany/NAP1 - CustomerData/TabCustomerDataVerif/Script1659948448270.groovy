import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.CustomerDataCompany'(sqlconnection, WebUI.getAttribute(findTestObject(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
        'value')).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

println(resultarray)

'verify customer name == db'
WebUI.verifyMatch(resultarray[0], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify tax id no == db'
WebUI.verifyMatch(resultarray[1], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify comapany type == db'
WebUI.verifyMatch(resultarray[2], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify customer model == db'
WebUI.verifyMatch(resultarray[3], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify address == db'
WebUI.verifyMatch(resultarray[4], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/textarea_Address'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RT == db '
WebUI.verifyMatch(resultarray[5], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RT'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RW == db'
WebUI.verifyMatch(resultarray[6], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RW'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify Zipcode == db'
WebUI.verifyMatch(resultarray[7], WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelZipcode')), 
    false, FailureHandling.OPTIONAL)

'verify kelurahan == db'
WebUI.verifyMatch(resultarray[8], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKelurahan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kecamatan == db'
WebUI.verifyMatch(resultarray[9], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKecamatan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kota == db'
WebUI.verifyMatch(resultarray[10], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKota'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify ownership == db'
WebUI.verifyMatch(resultarray[11], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        'value'), false, FailureHandling.OPTIONAL)

