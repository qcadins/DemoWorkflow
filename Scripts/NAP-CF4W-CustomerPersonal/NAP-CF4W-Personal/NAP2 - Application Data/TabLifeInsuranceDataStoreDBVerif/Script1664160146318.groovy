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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2LifeInsuranceStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
	if ((resultarray[i]).equalsIgnoreCase('null')) {
		(resultarray[i]) = ''
	}else if((resultarray[i]) == 'true'){
	(resultarray[i]) = 'Yes'
	}else if((resultarray[i]) == 'false'){
	(resultarray[i]) = 'No'
	}
}

int arrayindex = 0

'verify insco branch name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 13).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify premium payment method'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify premium percentage'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 15).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify notes'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify subject customer'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify subject guarantor'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify subject spouse'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

println(resultarray)

'verify admin fee'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
		GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)