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

String servername = findTestData('Login/Login').getValue(1, 7)

String instancename = findTestData('Login/Login').getValue(2, 7)

String username = findTestData('Login/Login').getValue(3, 7)

String password = findTestData('Login/Login').getValue(4, 7)

String database = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 7)
String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.CustomerDataPersonal'(sqlconnection, WebUI.getAttribute(findTestObject(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
        'value')).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

if ((resultarray[3]).equalsIgnoreCase('null')) {
    (resultarray[3]) = ''
}

'verify customer name == db'
WebUI.verifyMatch(resultarray[0], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify birth place == db'
WebUI.verifyMatch(resultarray[1], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id type == db'
WebUI.verifyMatch(resultarray[2], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id expired date == db'
WebUI.verifyMatch(resultarray[3], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify marital status == db'
WebUI.verifyMatch(resultarray[4], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify mobile phone == db'
WebUI.verifyMatch(resultarray[5], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify customer model == db'
WebUI.verifyMatch(resultarray[6], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify gender == db'
WebUI.verifyMatch(resultarray[7], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify birth date == db'
WebUI.verifyMatch(resultarray[8], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id no == db'
WebUI.verifyMatch(resultarray[9], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify tax id no == db'
WebUI.verifyMatch(resultarray[10], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify mother maiden name == db'
WebUI.verifyMatch(resultarray[11], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify email == db'
WebUI.verifyMatch(resultarray[12], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify address == db'
WebUI.verifyMatch(resultarray[13], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RT == db '
WebUI.verifyMatch(resultarray[14], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RW == db'
WebUI.verifyMatch(resultarray[15], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify Zipcode == db'
WebUI.verifyMatch(resultarray[16], WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelZipcode')), 
    false, FailureHandling.OPTIONAL)

'verify kelurahan == db'
WebUI.verifyMatch(resultarray[17], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKelurahan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kecamatan == db'
WebUI.verifyMatch(resultarray[18], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKecamatan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kota == db'
WebUI.verifyMatch(resultarray[19], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKota'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify ownership == db'
WebUI.verifyMatch(resultarray[20], WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        'value'), false, FailureHandling.OPTIONAL)


println(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 'value'))