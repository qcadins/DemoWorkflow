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
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import groovy.sql.Sql as Sql

'declare excelPathGuarantorPersonal'
excelPathGuarantorPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal'

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare arraymatch arraylist'
ArrayList<String> arrayMatch = new ArrayList<String>()

'call keyword get guarantor data store db personal lookup'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.GuarantorDataStoreDBPersonalLookUp'(sqlconnectionLOS, 
    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 12), findTestData(excelPathGuarantorPersonal).getValue(
        GlobalVariable.NumofGuarantorPersonal, 16))

'looping verif db = confinsdata'
for (int i = 0; i < result.size(); i++) {
    'verify result == confinsdata'
    arrayMatch.add(WebUI.verifyMatch((result[i]).toUpperCase(), (GlobalVariable.Confinsdata[i]).toUpperCase(), true, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
}