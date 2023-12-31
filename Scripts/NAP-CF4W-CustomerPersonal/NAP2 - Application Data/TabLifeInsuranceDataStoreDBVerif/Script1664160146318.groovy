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

'connect DB los'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathTabLifeInsurance'
excelPathTabLifeInsurance = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData'

'get life insurance data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2LifeInsuranceStoreDB'(sqlconnection, findTestData(excelPathCustomerPersonal).getValue(
        GlobalVariable.NumofColm, 13))

'declare arraymatch'
ArrayList<Boolean> arrayMatch = new ArrayList<>()

'declare arrayindex'
int arrayindex = 0

'verify insco branch name'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 13).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify premium payment method'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Paid in Advance & Capitalized Mix')){
'verify premium percentage'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 15).replace('%',''), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'skip'
arrayindex++
}

'verify notes'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 16).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify subject customer'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 18).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify subject guarantor'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify subject spouse'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify admin fee'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabLifeInsurance).getValue(
		GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedStoredDB)
	
}