import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat

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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData')

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/labelCustomerName'))

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabApplicationStoreDB'(sqlconnection, datafileCustomerCompany.getValue(
		GlobalVariable.NumofColm, 13), custname)
		
ArrayList<Boolean> arrayMatch = new ArrayList<>()

ArrayList<String> resultattr = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabApplicationAttrStoreDB'(sqlconnection, datafileCustomerCompany.getValue(
		GlobalVariable.NumofColm, 13))
		
//String bankaccount
//def bankarray, confinsdatabankacc
//
//	if(datafileTabApplication.getValue(
//        GlobalVariable.NumofColm, 25).equalsIgnoreCase('Auto Debit')){
//
//	 bankaccount = CustomKeywords.'dbConnection.CustomerDataVerif.BankAccountTabApplicationDataStoreDB'(sqlconnection, datafileCustomerCompany.getValue(
//		GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')
//			 bankarray = bankaccount.split(', ')
//			 confinsdatabankacc = GlobalVariable.BankAccount.split(' - ')
//	}

int arrayindex = 0, attrindex = 0
//int bankindex = 0
//int confinsindex = 0

'verify application source'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify First installment type'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify payment frequency'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tenor'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify dp source payment type'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify interest type'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 22) == 'Float Rate'){
	'verify floating period'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindex++
}

'verify installment scheme'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify WOP'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//if(datafileTabApplication.getValue(
//	GlobalVariable.NumofColm, 25).equalsIgnoreCase('Auto Debit')){
//'verify Bank Name'
//arrayMatch.add(WebUI.verifyMatch((confinsdatabankacc[confinsindex++]).toUpperCase(), (bankarray[bankindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//
//'verify Bank Acc No'
//arrayMatch.add(WebUI.verifyMatch((confinsdatabankacc[confinsindex++]).toUpperCase(), (bankarray[bankindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//
//'verify Bank Account Name'
//arrayMatch.add(WebUI.verifyMatch((confinsdatabankacc[confinsindex++]).toUpperCase(), (bankarray[bankindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//}
	
'verify customer notification'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify installment source payment type'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify if copy address'
if(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 30).length() > 0){

	'verify address'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 38).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 39).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 40).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify Zipcode'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 41).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 42).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 43).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(
			GlobalVariable.NumofColm, 44).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{

	'verify address'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify rt'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 32).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify rw'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 33).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify zipcode'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 34).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 35).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 36).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 37).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'verify phone area 1'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 38).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone number 1'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 39).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone ext 1'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 40).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone area 2'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 41).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone number 2'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 42).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone ext 2'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 43).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone area 3'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 44).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone number 3'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 45).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone ext 3'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 46).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify fax area'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 47).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify fax number'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 48).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Characteristic of credit'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 50).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify previous number of aggrmnt'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 51).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify WOS'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 52).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify economic sector code'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 53).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Blacklist APPI'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 56).toUpperCase(), (resultattr[attrindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify APPI score'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 57).toUpperCase(), (resultattr[attrindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(datafileTabApplication.getValue(GlobalVariable.NumofColm, 58).length() > 0){
'verify Date app data'
arrayMatch.add(WebUI.verifyMatch(convertDateFormat(datafileTabApplication.getValue(
			GlobalVariable.NumofColm, 58)), (resultattr[attrindex++]), false, FailureHandling.OPTIONAL))
}else{
attrindex++
}

'verify app data code'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 59).toUpperCase(), (resultattr[attrindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify jumlah asset'
arrayMatch.add(WebUI.verifyMatch(datafileTabApplication.getValue(
		GlobalVariable.NumofColm, 61).toUpperCase(), (resultattr[attrindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)

}

public convertDateFormat(String sentDate){
	'convert date confins dan excel agar sama'
	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')
	
	Date parsedDate = null
	
	parsedDate = sdf.parse(sentDate)
	
	sdf = new SimpleDateFormat('yyyy-MM-dd')
	
	String sDate = sdf.format(parsedDate)
	
	return sDate
}