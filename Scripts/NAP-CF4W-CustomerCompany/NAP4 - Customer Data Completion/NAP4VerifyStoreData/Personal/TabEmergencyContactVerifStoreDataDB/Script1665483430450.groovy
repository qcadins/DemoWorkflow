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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4EmergencyContactStoreData'(sqlconnection, appno, custname).replace(
	'HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')
ArrayList<Boolean> arrayMatch = new ArrayList<>()
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
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify ID Type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify ID No'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify ID expired date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify mobile 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify mobile 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))


int row = 27
	
for(i = 11 ; i < resultarray.size(); i++){
		
		'verify Address'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (resultarray[i]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

}
//'verify RT'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//    false, FailureHandling.OPTIONAL))
//
//'verify RW'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//    false, FailureHandling.OPTIONAL))
//
//'verify zipcode'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify kecamatan'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify kelurahan'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify kota'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 33).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 1 area'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 34).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 1'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 35).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 1 ext'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 36).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 2 area'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 37).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 2'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 2 ext'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 39).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 3 area'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 40).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 3'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 41).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))
//
//'verify phone 3 ext'
//arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 42).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(),
//	false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact',
		0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact',
		1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)

}