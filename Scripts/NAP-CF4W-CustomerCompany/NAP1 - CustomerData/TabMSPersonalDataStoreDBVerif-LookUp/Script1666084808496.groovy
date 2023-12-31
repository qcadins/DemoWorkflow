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
import org.openqa.selenium.WebElement as WebElement
'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare arraymatch arraylist'
ArrayList<Boolean> arrayMatch = new ArrayList<>()

'declare datafileMS'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'call keyword MS data store db personal lookup'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.MSDataStoreDBPersonalLookUp'(sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 12), datafileMS.getValue(GlobalVariable.NumofMS, 17), datafileMS.getValue(GlobalVariable.NumofMS, 27))

'looping verif db = confinsdata'
for (int i = 0; i < GlobalVariable.Confinsdata.size(); i++) {
	'verify result == confinsdata'
	arrayMatch.add(WebUI.verifyMatch(result[i].toUpperCase(), GlobalVariable.Confinsdata[i].toUpperCase(), true, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

