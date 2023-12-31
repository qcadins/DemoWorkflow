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

'get appno from confins'
String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

'get custname from confins'
String custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

'get customer detail data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4CustomerDetailPersonalStoreData'(sqlconnectionLOS, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindex = 0

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).length() > 0){
	'verify gelar depan nama'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 15).length() > 0){
	'verify num of dependent'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).length() > 0){
	'verify num of residence'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 17).length() > 0){
	'verify family card num'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify is RIP'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify is VIP'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify is AFF MF'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

//'verify customer group'
//arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(
//        GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify salutation'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).length() > 0){
	'verify nickname'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).length() > 0){
	'verify gelar belakang nama'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify nationality'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).equalsIgnoreCase('Local')) {
	'verify country'
	arrayMatch.add(WebUI.verifyMatch('INDONESIA', (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
	'verify country'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}

'verify education'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify religion'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'verify vip notes'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.CustomerDetail', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
	
}

