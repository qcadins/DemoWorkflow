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

String DepartmentAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 33)

String AuthorityAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 35)

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.GuarantorStoreDataPersonal'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 12)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    }
}

'verify relationship'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 18).toUpperCase(), (resultarray[0]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify guarantor name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 19).toUpperCase(), (resultarray[1]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify birth place'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 20).toUpperCase(), (resultarray[2]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 21).toUpperCase(), (resultarray[3]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id expired date'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 22).toUpperCase(), (resultarray[4]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify marital status'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 23).toUpperCase(), (resultarray[5]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify mobile phone'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 24).toUpperCase(), (resultarray[6]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify customer model'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 25).toUpperCase(), (resultarray[7]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify gender'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 26).toUpperCase(), (resultarray[8]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify birth date'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 27).toUpperCase(), (resultarray[9]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 28).toUpperCase(), (resultarray[10]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify tax id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 29).toUpperCase(), (resultarray[11]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify mother maiden name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 30).toUpperCase(), (resultarray[12]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify email'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 31).toUpperCase(), (resultarray[13]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify department aml value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 33) == '#N/A') {
    'set department aml value menjadi string kosong'
    DepartmentAML = ''
}

'verify department aml'
WebUI.verifyMatch(DepartmentAML.toUpperCase(), (resultarray[14]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify authority aml value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 35) == '#N/A') {
    'set authority aml value menjadi kosong'
    AuthorityAML = ''
}

'verify authority aml'
WebUI.verifyMatch(AuthorityAML.toUpperCase(), (resultarray[15]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify copy address atau tidak'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 38).equalsIgnoreCase('Yes')) {
    'verify addres copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 24).toUpperCase(), (resultarray[16]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rt copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 25).toUpperCase(), (resultarray[17]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rw copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 26).toUpperCase(), (resultarray[18]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify zipcode copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 27).toUpperCase(), (resultarray[19]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 28).toUpperCase(), (resultarray[20]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 29).toUpperCase(), (resultarray[21]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 30).toUpperCase(), (resultarray[22]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantor, 31).toUpperCase(), (resultarray[23]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else {
    'verify address'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 39).toUpperCase(), (resultarray[16]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rt'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 40).toUpperCase(), (resultarray[17]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify rw'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 41).toUpperCase(), (resultarray[18]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify zipcode'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 42).toUpperCase(), (resultarray[19]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 43).toUpperCase(), (resultarray[20]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 44).toUpperCase(), (resultarray[21]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 45).toUpperCase(), (resultarray[22]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 46).toUpperCase(), (resultarray[23]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

