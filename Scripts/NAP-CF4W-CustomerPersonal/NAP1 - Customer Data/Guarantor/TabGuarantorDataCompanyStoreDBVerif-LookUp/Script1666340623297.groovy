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

import org.openqa.selenium.WebElement

import groovy.sql.Sql as Sql

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathGuarantorCompany'
excelPathGuarantorCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabGuarantorDataCompany'

'get guarantorcompanydata from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.GuarantorDataStoreDBCompanyLookUp'(sqlconnectionLOS, 
    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 12), findTestData(excelPathGuarantorCompany).getValue(
        GlobalVariable.NumofGuarantorCompany, 16))

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'looping guarantorcompanydata'
for (int i = 0; i < result.size(); i++) {
    'verify result == confinsdata'
    arrayMatch.add(WebUI.verifyMatch(result[i], '(?i)' + (GlobalVariable.Confinsdata[i]), true, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 2) + ';' +GlobalVariable.ReasonFailedStoredDB)
}