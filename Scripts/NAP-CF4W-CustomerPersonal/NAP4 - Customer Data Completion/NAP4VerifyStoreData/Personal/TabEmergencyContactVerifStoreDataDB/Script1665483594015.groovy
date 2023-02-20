import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.DriverManager

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

'get emergencycontact data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4EmergencyContactStoreData'(sqlconnectionLOS, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

int arrayindex = 0

'verify contact person name'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ID Type'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ID No'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).equalsIgnoreCase('E-KTP') || findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).equalsIgnoreCase('AKTA') || findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 16).equals('NPWP')){
	'skip expired date'
	arrayindex++
}else{
	'verify ID expired date'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
	    false, FailureHandling.OPTIONAL))
}

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(),
		(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 20).length() > 0){
	'verify email'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), 
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify gender'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), 
		(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).length() > 0){
	'verify birth place'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), 
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).length() > 0){
	'verify birth date'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), 
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify mobile 1'
arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), 
		(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).length() > 0){
	'verify mobile 2'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), 
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 26).length() > 0){
	
	address = 0
	
	'verify Address'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify RT'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify zipcode'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 1 area'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 1'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 1 ext'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 2 area'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 2'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 2 ext'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 3 area'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 3'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify phone 3 ext'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{

	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 27).length() > 0){
		'verify Address'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 28).length() > 0){
		'verify RT'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 29).length() > 0){
		'verify RW'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 30).length() > 0){
		'verify zipcode'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 31).length() > 0){
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 32).length() > 0){
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 33).length() > 0){
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 33).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 34).length() > 0){
		'verify phone 1 area'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 34).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 35).length() > 0){
		'verify phone 1'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 35).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 36).length() > 0){
		'verify phone 1 ext'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 36).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 37).length() > 0){
		'verify phone 2 area'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 37).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 38).length() > 0){
		'verify phone 2'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 39).length() > 0){
		'verify phone 2 ext'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 39).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 40).length() > 0){
		'verify phone 3 area'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 40).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 41).length() > 0){
		'verify phone 3'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 41).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 42).length() > 0){
		'verify phone 3 ext'
		arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofVerifStore, 42).toUpperCase(), 
				(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayindex++
	}

}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.EmergencyContact', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}