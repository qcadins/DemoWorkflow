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

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = ""
result = CustomKeywords.'dbconnection.CustomerDataVerif.CustomerDataStoreDBPersonalLookUp'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13), findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 17))
result.replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
	if ((resultarray[i]).equalsIgnoreCase('null')) {
		(resultarray[i]) = ''
	}
}

int arrayindex = 0
int confinsindex = 0

'verify customer name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))


'verify id expired date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile phone 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify addr'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify rt'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify rw'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify building ownership'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[confinsindex++].toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//for (i = 0; i < resultarray.size(); i++) {
//	'verify resultarray == confinsdata'
//	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.confinsdata[i].toUpperCase(), (resultarray[i]).toUpperCase(), false, FailureHandling.OPTIONAL))
//}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
			1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}

