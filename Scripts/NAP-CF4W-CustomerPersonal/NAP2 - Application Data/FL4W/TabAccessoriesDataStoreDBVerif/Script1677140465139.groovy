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
import org.openqa.selenium.WebElement as WebElement

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathAccessories'
excelPathAccessories = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/Accessories'

'declare excelPathTabAsset'
excelPathTabAsset = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabAssetData'

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'get accessories data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2AccessoriesStoreDB'(sqlconnection, findTestData(excelPathAccessories).getValue(GlobalVariable.StartIndex,
		12))

'declare arraynum'
int arraynum = 0

'declare arraymatch'
ArrayList<Boolean> arrayMatch = new ArrayList<>()

'looping data accessories'
for (GlobalVariable.NumofAccessories = GlobalVariable.StartIndex; GlobalVariable.NumofAccessories <= (findTestData(excelPathAccessories).getColumnNumbers() - 1); (GlobalVariable.NumofAccessories)++) {
	if(findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 12) == findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13) &&
		findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 10) == findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 17)){
	'verify supplier code'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 13).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify supplier name'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 14).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify accessories code'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 15).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify accessories name'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 16).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify accessories price'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 17).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	if (findTestData(excelPathAccessories).getValue(
		GlobalVariable.NumofAccessories, 18).equalsIgnoreCase('Percentage')) {
		'verify DP Percent'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
				GlobalVariable.NumofAccessories, 19).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

		'skip amount'
		arraynum++
	} else if (findTestData(excelPathAccessories).getValue(
		GlobalVariable.NumofAccessories, 18).equalsIgnoreCase('Amount')) {
		'skip percentage'
		arraynum++

		'verify DP Amount'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
				GlobalVariable.NumofAccessories, 20).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
	
	'verify notes'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 21).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
	else{
		break
	}
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.StartIndex, GlobalVariable.StatusFailed, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';'+GlobalVariable.ReasonFailedStoredDB)
	
}
