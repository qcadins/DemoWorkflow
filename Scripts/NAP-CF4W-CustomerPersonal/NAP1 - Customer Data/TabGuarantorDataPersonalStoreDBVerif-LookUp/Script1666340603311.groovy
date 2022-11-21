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

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

ArrayList<Boolean> arrayMatch = new ArrayList<>()

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.GuarantorDataStoreDBPersonalLookUp'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
		GlobalVariable.NumofGuarantorPersonal, 12), findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
		GlobalVariable.NumofGuarantorPersonal, 16))

//'ganti value null > "" (String kosong)'
//for (int i = 0; i <= (result.size() - 1); i++) {
//	if ((result[i]) == null) {
//		(result[i]) = ''
//	}
//}

for (int i = 0; i < result.size(); i++) {
	'verify result == confinsdata'
	arrayMatch.add(WebUI.verifyMatch(result[i], "(?i)"+GlobalVariable.Confinsdata[i], true, FailureHandling.OPTIONAL))
	
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal',
			0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal',
			1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.ReasonFailedStoredDB)
}