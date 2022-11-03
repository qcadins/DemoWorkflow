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

String addresstype = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 12)

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4AddressStoreData'(sqlconnection, appno, custname, addresstype)

'ganti value null > "" (String kosong)'
for (i = 0; i <= (result.size() - 1); i++) {
	if ((result[i]).equalsIgnoreCase('null')) {
		(result[i]) = ''
	} else if ((result[i]).equalsIgnoreCase('true')) {
		(result[i]) = 'Yes'
	} else if ((result[i]).equalsIgnoreCase('false')) {
		(result[i]) = 'No'
	}
}

int arrayindex = 0

ArrayList<Boolean> arrayMatch = new ArrayList<>()
'verify address type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify rt'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify rw'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 1 Area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 1 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 2 Area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 2 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 3 area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 3'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify phn 3 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify Fax area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify Fax'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify ownership'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
		0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
		1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)

}
