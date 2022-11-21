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

ArrayList<Boolean> arrayMatch = new ArrayList<>()

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4CustomerAssetDataStoreData'(sqlconnectionLOS, appno, custname)

'ganti value null > "" (String kosong)'
for (i = 0; i <= (result.size() - 1); i++) {
    if ((result[i]) == null) {
        (result[i]) = ''
    } else if ((result[i]).equalsIgnoreCase('true')) {
        (result[i]) = 'Yes'
    } else if ((result[i]).equalsIgnoreCase('false')) {
        (result[i]) = 'No'
    }
}

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

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.CustomerAsset',
			0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.CustomerAsset',
			1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)
}