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
import org.openqa.selenium.WebElement

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileTabFinancial'
datafileTabFinancial = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData')

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2FinancialStoreDB'(sqlconnection, datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
		13))

ArrayList<String> resultFee = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2FinancialFeeStoreDB'(sqlconnection, datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
		13))

ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

int arrayindex = 0, arrayFeeIndex = 0

'Verif jika use default fee value no'
if(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 20).equalsIgnoreCase("No")){
	
	'Verif admin fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 21).replace(',', ''),resultFee[arrayFeeIndex++],false))
	
	arrayFeeIndex++
	
	'Verif is capitalize admin fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 26),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 26).equalsIgnoreCase("Yes")){
		
		'Verif admin fee capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 27).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 26).equalsIgnoreCase("No")){
		
		'Verif admin fee capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}
		
	'Verif additional admin'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 22).replace(',', ''),resultFee[arrayFeeIndex++],false))
	
	arrayFeeIndex++
	
	'Verif is capitalize additional admin'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 28),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 28).equalsIgnoreCase("Yes")){
		
		'Verif additional admin capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 29).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 28).equalsIgnoreCase("No")){
		
		'Verif additional admin capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}
	
	'verif notary fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 23).replace(',', ''),resultFee[arrayFeeIndex++],false))
	
	arrayFeeIndex++
	
	'verif is capitalize notary fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 30),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 30).equalsIgnoreCase("Yes")){
		
		'verif notary fee capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 31).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 30).equalsIgnoreCase("No")){
		
		'verif notary fee capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}

	'verif other fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 24).replace(',', ''),resultFee[arrayFeeIndex++],false))
	
	arrayFeeIndex++
	
	'verif is capitalize other fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 32),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 32).equalsIgnoreCase("Yes")){
			
		'verif other fee capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 33).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 32).equalsIgnoreCase("No")){
			
		'verif other fee capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}

	'verif fiducia fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 25).replace(',', ''),resultFee[arrayFeeIndex++],false))
	
	arrayFeeIndex++
	
	'verif fiducia fee is capitalize'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 34),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 34).equalsIgnoreCase("Yes")){
		
		'verif fiducia fee capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 35).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 34).equalsIgnoreCase("No")){
		
		'verif fiducia fee capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}
	
	
	'verif provision fee'
	if(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 36).equalsIgnoreCase("Amount")){
		
		'verif provision fee amount'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 39).replace(',', ''),resultFee[arrayFeeIndex++],false))
		
		arrayFeeIndex++
		
	}
	else if(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 36).equalsIgnoreCase("Percentage")){
		
		arrayFeeIndex++
		
		'verif provision fee percentage'
		arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 38)),Double.parseDouble(resultFee[arrayFeeIndex++])))
		
	}
	
	'verif provision fee is capitalize'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 40),resultFee[arrayFeeIndex++],false))
	
	if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 40).equalsIgnoreCase("Yes")){
		
		'verif provision fee capitalize'
		arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 41).replace(',', ''),resultFee[arrayFeeIndex++],false))
	}
	else if(datafileTabFinancial.getValue(
		GlobalVariable.NumofColm, 40).equalsIgnoreCase("No")){
		
		'verif provision fee capitalize'
		arrayMatch.add(WebUI.verifyMatch("0",resultFee[arrayFeeIndex++],false))
	}
	
}


'verify total fee amount'
arrayMatch.add(WebUI.verifyEqual(((((Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 21).replace(',', '')) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 22).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 23).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 24).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 25).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 39).replace(',', '')), Integer.parseInt(result[arrayindex++]), FailureHandling.OPTIONAL))

'verify total capitalize amt'
arrayMatch.add(WebUI.verifyEqual(((((Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 27).replace(',', '')) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 29).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 31).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 33).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 35).replace(',', ''))) + Integer.parseInt(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 41).replace(',', '')), Integer.parseInt(result[arrayindex++]), FailureHandling.OPTIONAL))

'verify provision fee calculation base'
arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 37).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if (datafileTabFinancial.getValue(
	GlobalVariable.NumofColm, 43).equalsIgnoreCase('Effective Rate')) {
	'verify effective rate'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 44).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'skip flat rate'
	arrayindex++
} else if (datafileTabFinancial.getValue(
	GlobalVariable.NumofColm, 43).equalsIgnoreCase('Flat Rate')) {
	'skip effective rate'
	arrayindex++

	'verify flat rate'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 45).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

if (datafileTabFinancial.getValue(
	GlobalVariable.NumofColm, 46).equalsIgnoreCase('Yes')) {
	'verify grace period'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 47).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify grace period type'
	arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
				GlobalVariable.NumofColm, 48).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}
else{
	arrayindex+=2
}

'verify TDP paid at MF'
arrayMatch.add(WebUI.verifyMatch(datafileTabFinancial.getValue(
			GlobalVariable.NumofColm, 49).replace(',', ''), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}

