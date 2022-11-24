import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.DriverManager

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
	
String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4EmergencyContactStoreData'(sqlconnectionLOS, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

int arrayindex = 0

'verify contact person name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ID Type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ID No'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ID expired date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(),
		(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify RT'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 29).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify zipcode'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 33).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 1 area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 34).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 1'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 35).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 1 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 36).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 2 area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 37).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 2'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 2 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 39).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 3 area'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 40).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 3'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 41).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify phone 3 ext'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 42).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))



'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.EmergencyContact', 0, 
        GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.EmergencyContact', 1, 
        GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)
}

CustomKeywords.'dbConnection.connectDB.ConnectDB'()


