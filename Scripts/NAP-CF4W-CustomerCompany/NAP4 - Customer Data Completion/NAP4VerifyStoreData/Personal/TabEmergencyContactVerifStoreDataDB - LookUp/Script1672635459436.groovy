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
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get appno from confins'
String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

'get custname from confins'
String custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

'get emergencycontact data lookup from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4EmergencyContactStoreDataLookUp'(sqlconnection, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

int arrayindex = 0

for(arrayindex = 0; arrayindex < result.size() ; arrayindex++){
'verify contact person name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[arrayindex].toUpperCase(), (result[arrayindex]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.EmergencyContact', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

