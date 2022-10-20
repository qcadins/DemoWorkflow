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

appno = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    13)

ArrayList<String> result = new ArrayList<String>()

result = CustomKeywords.'dbconnection.CustomerDataVerif.checkAppViewDataDB'(sqlconnection, appno)

'ganti value null > "" (String kosong)'
for (i = 0; i < result.size(); i++) {
    if ((result[i]) == null) {
        (result[i]) = '-'
    }
}

int index = 0

'click menu app inquiry'
WebUI.click(findTestObject('AppView/MENU APP INQUIRY'))

'input app no'
WebUI.setText(findTestObject('AppView/input_AppNo'), appno)

'click button search'
WebUI.click(findTestObject('AppView/button_Search'))

'click hyperlink'
WebUI.click(findTestObject('AppView/hyperlink_APPNO'))

'swicth window ke tab baru'
WebUI.switchToWindowIndex('1')

'verify alert'
WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 5, FailureHandling.OPTIONAL)

'delay 5 detik'
WebUI.delay(5)

'verify app no'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(appno, WebUI.getText(findTestObject('Object Repository/AppView/Label App No')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify office'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('Login/Login').getValue(4, 1).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Label Office')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify customer no'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 16).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Label Cust No')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify customer name'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 19).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Customer Name')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify product offering'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Product Offering Name')).toString().toUpperCase(),
	(result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

//CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 12), WebUI.getText(findTestObject('Object Repository/AppView/Product Offering Name')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify tenor'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 20).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Tenor')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify asset name'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset Name')).toString().toUpperCase(), (result[
	index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

//CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 17), WebUI.getText(findTestObject('Object Repository/AppView/Asset Name')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify customer model'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 25).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Customer Model')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Label App No')).toString().toUpperCase(), (result[
//    index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)
//
//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Label Office')).toString().toUpperCase(), (result[
//    index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)
//
//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Label Cust No')).toString().toUpperCase(), (result[
//    index++]).toString().toString().toString().toUpperCase(), false, FailureHandling.OPTIONAL)
//
//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Customer Name')).toString().toUpperCase(), (result[
//    index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)
//
//
//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Tenor')).toString().toUpperCase(), (result[index++]).toString().toUpperCase(), 
//    false, FailureHandling.OPTIONAL)
//
//WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Customer Model')).toString().toUpperCase(), (result[
//    index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

WebUI.switchToWindowIndex('0')



