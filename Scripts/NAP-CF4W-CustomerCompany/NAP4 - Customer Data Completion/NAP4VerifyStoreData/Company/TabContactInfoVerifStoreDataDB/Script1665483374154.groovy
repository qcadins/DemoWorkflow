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

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

ArrayList<Boolean> arrayMatch = new ArrayList<>() 

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4ContactInformationStoreData'(sqlconnectionLOS, appno, custname)

println(result)

'ganti value null > "" (String kosong)'
for (i = 0; i <= (result.size() - 1); i++) {
//    if ((result[i]) == null) {
//        (result[i]) = ''
//    } else 
	if ((result[i]).equalsIgnoreCase('true')) {
        (result[i]) = 'Yes'
    } else if ((result[i]).equalsIgnoreCase('false')) {
        (result[i]) = 'No'
    }
}

int arrayindex = 0

'verify contact person name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify job title'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 13).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify ID Type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify ID No'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify ID expired date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify Mobile Phone 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify email 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify job position'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify Customer Relationship'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify mobile 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify email 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

int row = 27

for (i = 14; i < result.size(); i++) {
    'verify Address'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (result[
        i]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.ContactInformation',
			0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.ContactInformation',
			1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)
}