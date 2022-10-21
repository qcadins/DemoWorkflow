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

String resultsupplierinformation = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TabAssetSupplierInfoStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

String resultassetinformation = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TabAssetStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13))

resultarrayassetinformation = resultassetinformation.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

resultarraysupplierinformation = resultsupplierinformation.split(', ')

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarrayassetinformation.size() - 1); i++) {
	if ((resultarrayassetinformation[i]).equalsIgnoreCase('null')) {
		(resultarrayassetinformation[i]) = ''
	}else if ((resultarrayassetinformation[i]).equalsIgnoreCase(',')) {
		(resultarrayassetinformation[i]) = ', '
	}
}

println(resultarrayassetinformation)

int arraysuppinfoindex = 0
int arrayassetinfoindex = 0

'verify supplier code'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 12).toUpperCase(), (resultarraysupplierinformation[arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify supplier name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 13).toUpperCase(), (resultarraysupplierinformation[arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify sales person'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 14).toUpperCase(), (resultarraysupplierinformation[arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify admin head'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 15).toUpperCase(), (resultarraysupplierinformation[arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset code'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 17).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset condition'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 18).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset price'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 19).replace(',',''), (resultarrayassetinformation[arrayassetinfoindex++]), false, FailureHandling.OPTIONAL))

'verify asset usage'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 20).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify color'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 21).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify BPKB ISSUER'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 22).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).replace(',', ', ').toUpperCase(), false, FailureHandling.OPTIONAL))

'verify BPKB ISSUE DATE'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 23).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify note'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 24).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify manufacturing year'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 25).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify DP percent'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 27).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify DP Amount'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 28).replace(',',''), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify chasis number'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 29).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify engine number'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 30).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify license plate'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 31).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify serial no 4'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 32).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify serial no 5'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 33).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset region'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 35).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify user name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 38).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify user relation'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 39).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))


'verify owner type'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 42).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify owner name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 43).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify owner relation'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 44).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 42).equalsIgnoreCase('Personal')){
'verify owner profession'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 46).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'verify owner profession'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 47).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}
		
'verify owner id type'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 48).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify owner id no'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 49).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify owner mobile no'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 50).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify copy address'
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 52).equalsIgnoreCase('Yes')){
	
'verify address'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 38).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Rt'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 39).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 40).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Zipcode'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 41).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 42).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 43).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 44).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{

'verify address'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 54).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify rt'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 55).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify rw'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 56).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 57).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 58).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))


'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 59).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 60).toUpperCase(), (resultarrayassetinformation[arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)

}