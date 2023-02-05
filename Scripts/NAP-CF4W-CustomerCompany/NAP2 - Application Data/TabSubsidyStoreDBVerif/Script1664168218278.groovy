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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get subsidy data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2SubsidyStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13))

int arrayindexdb = 0

'declare datafileTabFinancial'
datafileTabFinancial = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData')

def SubsidyTypeArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 12).split(';', -1)

def SubsidyfromValueArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def AllocationformArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def SubsidySourceArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def SubsidyValueTypeArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def SubsidyValueAmountArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

def SubsidyValuePercentageArray = datafileTabFinancial.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

if(result.size() > 0){
	for(int arrayindexexcel = 0; arrayindexexcel <= SubsidyTypeArray.size() - 1; arrayindexexcel++){
		
		Boolean isMatch = false
		for (int subsidydb = 0; subsidydb < result.size()/6; subsidydb++) {
			if ((((WebUI.verifyMatch((SubsidyTypeArray[arrayindexexcel]).toUpperCase(), '(?i)' + (result[0+(6*subsidydb)]), true, FailureHandling.OPTIONAL) ==
				false) || (WebUI.verifyMatch((SubsidyfromValueArray[arrayindexexcel]).toUpperCase(), '(?i)' + (result[1+(6*subsidydb)]), true, FailureHandling.OPTIONAL) ==
				false)) || (WebUI.verifyMatch((AllocationformArray[arrayindexexcel]).toUpperCase(), '(?i)' + (result[2+(6*subsidydb)]), true, FailureHandling.OPTIONAL) ==
				false)) || (WebUI.verifyMatch((SubsidySourceArray[arrayindexexcel]).toUpperCase(), '(?i)' + (result[3+(6*subsidydb)]), true, FailureHandling.OPTIONAL) ==
				false)) {
					continue
	
			} else {
			
					if(SubsidyValueTypeArray[arrayindexexcel].equalsIgnoreCase("Amount")){
						'verify subsidy amount'
						if(WebUI.verifyEqual(Integer.parseInt((SubsidyValueAmountArray[arrayindexexcel].replace(',', ''))), (result[4+(6*subsidydb)]),
							FailureHandling.OPTIONAL)==true){
						
							isMatch = true
							
							break
						}
						
					}
					else if(SubsidyValueTypeArray[arrayindexexcel].equalsIgnoreCase("Percentage")){
						
						'verify subsidy percentage'
						if(WebUI.verifyEqual(Integer.parseInt((SubsidyValuePercentageArray[arrayindexexcel].replace(',', ''))), (result[5+(6*subsidydb)]),
							FailureHandling.OPTIONAL)==true){
						
							isMatch = true
						
							break
						}
						
					}
			}
		}
	//'verify subsidy from value type'
	//arrayMatch.add(WebUI.verifyMatch((SubsidyTypeArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false,
	//	FailureHandling.OPTIONAL))
	//
	//'verify subsidy from value'
	//arrayMatch.add(WebUI.verifyMatch((SubsidyfromValueArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(),
	//	false, FailureHandling.OPTIONAL))
	//
	//'verify allocation from'
	//arrayMatch.add(WebUI.verifyMatch((AllocationformArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false,
	//	FailureHandling.OPTIONAL))
	//
	//'veirfy subsidy source'
	//arrayMatch.add(WebUI.verifyMatch((SubsidySourceArray[arrayindexexcel]).toUpperCase(), (result[arrayindexdb++]).toUpperCase(), false,
	//	FailureHandling.OPTIONAL))
	//
	//'verify subsidy amount'
	//arrayMatch.add(WebUI.verifyEqual(Integer.parseInt((SubsidyValueAmountArray[arrayindexexcel].replace(',', ''))), (result[arrayindexdb++]),
	//	FailureHandling.OPTIONAL))
	//
	//'verify subsidy percentage'
	//arrayMatch.add(WebUI.verifyEqual(Integer.parseInt((SubsidyValuePercentageArray[arrayindexexcel].replace(',', ''))), (result[arrayindexdb++]),
	//	FailureHandling.OPTIONAL))
		arrayMatch.add(isMatch)
	}
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)

}