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

ArrayList<Boolean> arrayMatch = new ArrayList<>()

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4LegalDocStoreData'(sqlconnectionLOS, appno, custname)

int arrayindex = 0

def legaldoctypearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 12).split(';', -1)

def docnoarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 13).split(';', -1)

def dateissuedarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 14).split(';', -1)

def expireddatearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).split(';', -1)

def notarynamearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).split(';', -1)

def notarylocationarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).split(';', -1)

def notesarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 18).split(';', -1)

for(legaldocarrayexcel = 0 ; legaldocarrayexcel < result.size()/7 ; legaldocarrayexcel++){
	
	'verify document type'
	arrayMatch.add(WebUI.verifyMatch(legaldoctypearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL))
	
	'verify document no'
	arrayMatch.add(WebUI.verifyMatch(docnoarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL))
	
	'verify date issued'
	arrayMatch.add(WebUI.verifyMatch(dateissuedarray[legaldocarrayexcel].split(',').join(), (result[arrayindex++]).split(',').join(), 
			false, FailureHandling.OPTIONAL))
	
	'verify expired date'
	arrayMatch.add(WebUI.verifyMatch(expireddatearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL))
	
	'verify notary name'
	arrayMatch.add(WebUI.verifyMatch(notarynamearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toString().toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify notary location'
	arrayMatch.add(WebUI.verifyMatch(notarylocationarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toString().toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify notes'
	arrayMatch.add(WebUI.verifyMatch(notesarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toString().toUpperCase(),
			false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument',
			0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument',
			1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)
}