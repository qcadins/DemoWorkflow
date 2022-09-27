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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TabAssetStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
	if ((resultarray[i]).equalsIgnoreCase('null')) {
		(resultarray[i]) = ''
	}
}

int arrayindex = 0

'verify supplier code'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 12).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify supplier name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 13).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify sales person'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify admin head'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 15).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify asset code'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify asset condition'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify asset price'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify asset usage'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify color'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 21).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify BPKB ISSUER'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 22).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify BPKB ISSUE DATE'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 23).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify note'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify manufacturing year'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify DP percent'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify DP Amount'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify chasis number'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify engine number'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify license plate'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify serial no 4'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 32).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify serial no 5'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 33).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify asset region'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 35).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify user name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 38).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify user relation'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 39).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)


'verify owner type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 42).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 43).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner relation'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 44).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner profession'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 46).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner id type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 48).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 49).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify owner mobile no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 50).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify copy address'
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 52).equalsIgnoreCase('Yes')){
	
'verify address'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 38).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Rt'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 39).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify RW'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 40).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Zipcode'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 41).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify kecamatan'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 42).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify kelurahan'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 43).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify kota'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 44).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}else{

'verify address'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 54).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify rt'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 55).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify rw'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 56).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify zipcode'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 57).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify kecamatan'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 58).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)


'verify kelurahan'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 59).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify kota'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 60).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}