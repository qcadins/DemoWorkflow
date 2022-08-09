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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.FamilyDataPersonal'(sqlconnection, WebUI.getAttribute(findTestObject(
            'Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        'value')).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

if ((resultarray[3]).equalsIgnoreCase('null')) {
    (resultarray[3]) = ''
}

'verify customer name == db'
WebUI.verifyMatch(resultarray[0], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify birth place == db'
WebUI.verifyMatch(resultarray[1], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id type == db'
WebUI.verifyMatch(resultarray[2], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_IDType'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id expired date == db'
WebUI.verifyMatch(resultarray[3], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Id Expired Date'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify marital status == db'
WebUI.verifyMatch(resultarray[4], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_Marital Status'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify mobile phone == db'
WebUI.verifyMatch(resultarray[5], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify customer model == db'
WebUI.verifyMatch(resultarray[6], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify gender == db'
WebUI.verifyMatch(resultarray[7], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_Gender'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify birth date == db'
WebUI.verifyMatch(resultarray[8], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify id no == db'
WebUI.verifyMatch(resultarray[9], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify tax id no == db'
WebUI.verifyMatch(resultarray[10], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify mother maiden name == db'
WebUI.verifyMatch(resultarray[11], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Mother Maiden Name'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify email == db'
WebUI.verifyMatch(resultarray[12], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_Email_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify Nationality == db'
WebUI.verifyMatch(resultarray[13], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
        'value'), false, FailureHandling.OPTIONAL)


'check if nationality = Local or Foreigner'
if (WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
    'value').equalsIgnoreCase('LOCAL')) {
    'verify country == db'
    WebUI.verifyMatch(resultarray[14], WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelCountry')), 
        false, FailureHandling.OPTIONAL)
} else {
    'verify country == db'
    WebUI.verifyMatch((resultarray[14]).toUpperCase(), WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelCountryLookup'), 
            'value'), false, FailureHandling.OPTIONAL)
}

'verify address == db'
WebUI.verifyMatch(resultarray[15], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/textarea_Address'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RT == db '
WebUI.verifyMatch(resultarray[16], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_RT'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify RW == db'
WebUI.verifyMatch(resultarray[17], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/input_RW'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify Zipcode == db'
WebUI.verifyMatch(resultarray[18], WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelZipcode')), 
    false, FailureHandling.OPTIONAL)

'verify kelurahan == db'
WebUI.verifyMatch(resultarray[19], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelKelurahan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kecamatan == db'
WebUI.verifyMatch(resultarray[20], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelKecamatan'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify kota == db'
WebUI.verifyMatch(resultarray[21], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/LabelKota'), 
        'value'), false, FailureHandling.OPTIONAL)

'verify ownership == db'
WebUI.verifyMatch(resultarray[22], WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/select_Ownership'), 
        'value'), false, FailureHandling.OPTIONAL)

