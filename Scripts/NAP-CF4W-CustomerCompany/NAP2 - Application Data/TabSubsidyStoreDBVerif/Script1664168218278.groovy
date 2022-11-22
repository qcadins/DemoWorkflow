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

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2SubsidyStoreDB'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

int arrayindexdb = 0

def datafilefinancial = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData')

def SubsidyTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 12).split(';', -1)

def SubsidyfromValueArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def AllocationformArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def SubsidySourceArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def SubsidyValueTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def SubsidyValueAmountArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

def SubsidyValuePercentageArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

println(result)
ArrayList<Boolean> arrayMatch = new ArrayList<>()

for(int arrayindexexcel = 0; arrayindexexcel < SubsidyTypeArray.size() ; arrayindexexcel++){
	
'verify subsidy from value type'
arrayMatch.add(WebUI.verifyMatch((SubsidyTypeArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL))

'verify subsidy from value'
arrayMatch.add(WebUI.verifyMatch((SubsidyfromValueArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL))

'verify allocation from'
arrayMatch.add(WebUI.verifyMatch((AllocationformArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL))

'veirfy subsidy source'
arrayMatch.add(WebUI.verifyMatch((SubsidySourceArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL))

if(SubsidyValueTypeArray[arrayindexexcel].equalsIgnoreCase('Amount')){
'verify subsidy amount'
arrayMatch.add(WebUI.verifyEqual(Integer.parseInt((SubsidyValueAmountArray[arrayindexexcel].replace(',', ''))), (result[arrayindexdb++]), 
    FailureHandling.OPTIONAL))

'skip percentage'
arrayindexdb++

}else if(SubsidyValueTypeArray[arrayindexexcel].equalsIgnoreCase('Percentage')){

'skip amount'
arrayindexdb++

'verify subsidy percentage'
arrayMatch.add(WebUI.verifyEqual(Integer.parseInt((SubsidyValuePercentageArray[arrayindexexcel].replace(',', ''))), (result[arrayindexdb++]), 
    FailureHandling.OPTIONAL))
}
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)

}
