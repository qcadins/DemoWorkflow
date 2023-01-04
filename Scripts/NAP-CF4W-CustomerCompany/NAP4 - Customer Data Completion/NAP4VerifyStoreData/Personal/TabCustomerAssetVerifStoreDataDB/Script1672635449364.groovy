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

'get appno from confins'
String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

'get custname from confins'
String custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

'get customer asset data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4CustomerAssetDataStoreData'(sqlconnectionLOS, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindex = 0

def assettypearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 13).split(';', -1)

def assetdescriptionarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 14).split(';', -1)

def assetvaluearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).split(';', -1)

def assetqtyarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).split(';', -1)

for(assetarrayexcel = 0 ; assetarrayexcel < result.size()/4 ; assetarrayexcel++){
	
	'verify asset type'
	arrayMatch.add(WebUI.verifyMatch(assettypearray[assetarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify asset desc'
	arrayMatch.add(WebUI.verifyMatch(assetdescriptionarray[assetarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify asset value'
	arrayMatch.add(WebUI.verifyMatch(assetvaluearray[assetarrayexcel].split(',').join(), (result[arrayindex++]).split(',').join(),
			false, FailureHandling.OPTIONAL))
	
	'verify asset qty'
	arrayMatch.add(WebUI.verifyMatch(assetqtyarray[assetarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.CustomerAsset', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
	
}
