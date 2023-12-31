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
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

ArrayList<Boolean> arrayMatch = new ArrayList<>() 

'get appno from confins'
String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

'get custname from confins'
String custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

'get contact information from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4ContactInformationStoreData'(sqlconnectionLOS, appno, custname)

int arrayindex = 0

'verify contact person name'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify job title'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 13).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify ID Type'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify ID No'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).equalsIgnoreCase('E-KTP') || findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).equalsIgnoreCase('AKTA') || findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).equals('NPWP')){
	'skip expired date'
	arrayindex++
}else{
	'verify ID expired date'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}

'verify Mobile Phone 1'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 18).length() > 0){
	'verify email 1'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify gender'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify job position'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 21).length() > 0){
	'verify birth place'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).length() > 0){
	'verify birth date'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).length() > 0){
	'verify Customer Relationship'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).length() > 0){
	'verify mobile 2'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).length() > 0){
	'verify email 2'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

int row = 27

for (i = 14; i < result.size(); i++) {
	'verify Address'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (result[
																																		i]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.ContactInformation', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}