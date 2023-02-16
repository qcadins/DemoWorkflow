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
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathTabFamily'
excelPathTabFamily = 'NAP-'+ GlobalVariable.LOB  +'-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData'

'get familydata from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.FamilyDataStoreDBPersonalLookUp'(sqlconnectionLOS, 
    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 12), findTestData(excelPathTabFamily).getValue(
        GlobalVariable.NumofFamily, 16), findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 27))

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'ganti value null > "" (String kosong)'
for (int i = 0; i <= (GlobalVariable.Confinsdata.size() - 1); i++) {
    if (((GlobalVariable.Confinsdata[i]) == null) || (GlobalVariable.Confinsdata[i]).equalsIgnoreCase('INA')) {
        (GlobalVariable.Confinsdata[i]) = ''
    }
}

'declare array for confins data'
def confinsdata = []

for (int i = 0; i < result.size(); i++) {
    'verify result == confinsdata'
    arrayMatch.add(WebUI.verifyMatch(result[i], '(?i)' + (GlobalVariable.Confinsdata[i]), true, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 2) + ';' +GlobalVariable.ReasonFailedStoredDB)
}

