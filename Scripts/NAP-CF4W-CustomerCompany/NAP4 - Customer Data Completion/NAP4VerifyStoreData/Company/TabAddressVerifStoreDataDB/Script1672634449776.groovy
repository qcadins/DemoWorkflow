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

'get addresstype from excel'
String addresstype = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 12)

'get address from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4AddressStoreData'(sqlconnectionLOS, appno, custname, addresstype)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindex = 0

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 13).length() > 0){

	address = 0
	
'verify address type'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify rt'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify rw'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 1 Area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 1 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 2 Area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 2 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 3 area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 3'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 3 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify Fax area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify Fax'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify ownership'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

}else{

'verify address type'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 12).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify rt'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify rw'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 1 Area'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify phn 1'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).length() > 0){
	'verify phn 1 ext'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).length() > 0){
	'verify phn 2 Area'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).length() > 0){
	'verify phn 2'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 26).length() > 0){
	'verify phn 2 ext'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 27).length() > 0){
	'verify phn 3 area'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 28).length() > 0){
	'verify phn 3'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 29).length() > 0){
	'verify phn 3 ext'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 30).length() > 0){
	'verify Fax area'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 31).length() > 0){
	'verify Fax'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}


'verify ownership'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}