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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4LegalDocStoreData'(sqlconnection, appno, custname)

'ganti value null > "" (String kosong)'
for (i = 0; i <= (result.size() - 1); i++) {
    if ((result[i]).equalsIgnoreCase('null')) {
        (result[i]) = ''
    } else if ((result[i]).equalsIgnoreCase('true')) {
        (result[i]) = 'Yes'
    } else if ((result[i]).equalsIgnoreCase('false')) {
        (result[i]) = 'No'
    }
}

int arrayindex = 0

def legaldoctypearray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 12).split(';', -1)

def docnoarray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 13).split(';', -1)

def dateissuedarray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 14).split(';', -1)

def expireddatearray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 15).split(';', -1)

def notarynamearray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 16).split(';', -1)

def notarylocationarray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 17).split(';', -1)

def notesarray = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 18).split(';', -1)


for(legaldocarrayexcel = 0 ; legaldocarrayexcel < result.size()/7 ; legaldocarrayexcel++){
	
	'verify document type'
	WebUI.verifyMatch(legaldoctypearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL)
	
	'verify document no'
	WebUI.verifyMatch(docnoarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL)
	
	'verify date issued'
	WebUI.verifyMatch(dateissuedarray[legaldocarrayexcel].split(',').join(), (result[arrayindex++]).split(',').join(), 
			false, FailureHandling.OPTIONAL)
	
	'verify expired date'
	WebUI.verifyMatch(expireddatearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(), 
			false, FailureHandling.OPTIONAL)
	
	'verify notary name'
	WebUI.verifyMatch(notarynamearray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL)
	
	'verify notary location'
	WebUI.verifyMatch(notarylocationarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL)
	
	'verify notes'
	WebUI.verifyMatch(notesarray[legaldocarrayexcel].toUpperCase(), (result[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL)
}
