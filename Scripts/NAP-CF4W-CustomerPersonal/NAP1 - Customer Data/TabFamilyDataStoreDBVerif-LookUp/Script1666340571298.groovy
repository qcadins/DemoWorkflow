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


ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.FamilyDataStoreDBPersonalLookUp'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 12), findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 16), findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 27))

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'ganti value null > "" (String kosong)'
for (int i = 0; i <= (result.size() - 1); i++) {
    if ((result[i]) == null || (result[i]).equalsIgnoreCase('IDN')) {
        (result[i]) = ''
    }
}

'ganti value null > "" (String kosong)'
for (int i = 0; i <= (GlobalVariable.Confinsdata.size() - 1); i++) {
	if ((GlobalVariable.Confinsdata[i]) == null || (GlobalVariable.Confinsdata[i]).equalsIgnoreCase('IDN')) {
		(GlobalVariable.Confinsdata[i]) = ''
	}
}

'declare array for confins data'
def confinsdata = []

for (int i = 0; i < result.size(); i++) {
	'verify result == confinsdata'
	arrayMatch.add(WebUI.verifyMatch(result[i], "(?i)"+GlobalVariable.Confinsdata[i], true, FailureHandling.OPTIONAL))
	
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData',
		0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData',
		1, GlobalVariable.NumofFamily - 1, GlobalVariable.ReasonFailedStoredDB)

}

