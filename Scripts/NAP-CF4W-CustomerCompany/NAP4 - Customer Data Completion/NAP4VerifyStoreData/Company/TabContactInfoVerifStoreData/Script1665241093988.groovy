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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4ContactInformationStoreData'(sqlconnection, appno, custname).replace(
    'HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    } else if ((resultarray[i]).equalsIgnoreCase('true')) {
        (resultarray[i]) = 'Yes'
    } else if ((resultarray[i]).equalsIgnoreCase('false')) {
        (resultarray[i]) = 'No'
    }
}

int arrayindex = 0

'verify contact person name'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify job title'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 13).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL)

'verify ID Type'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify ID No'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify ID expired date'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify Mobile Phone 1'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify email 1'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify gender'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify job position'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL)

'verify birth place'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify birth date'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify Customer Relationship'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify mobile 2'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify email 2'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL)

int row = 27

for (i = 14; i < resultarray.size(); i++) {
    'verify Address'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (resultarray[
        i]).toUpperCase(), false, FailureHandling.OPTIONAL)
}
