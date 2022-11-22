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
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

ArrayList<String> arrayMatch = new ArrayList<String>()

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.MSDataStoreDBCompanyLookUp'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofMS, 12), findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofMS, 50))



'ganti value null > "" (String kosong)'
for (int i = 0; i <= (result.size() - 1); i++) {
//    if ((result[i]) == null) {
//        (result[i]) = ''
//    }
//    
    if ((result[i]).equalsIgnoreCase('TRUE')) {
        (result[i]) = 'Yes'
    } else if ((result[i]).equalsIgnoreCase('FALSE')) {
        (result[i]) = 'No'
    }
}

println(result)

int arrayindex = 0

int confinsindex = 0

'verify shareholder name'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify position slik'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(),
	false, FailureHandling.OPTIONAL))

'verify company type'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify share percent'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify is active'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify is owner'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify address'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RT'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify Zipcode'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyEqual((Math.round(Double.parseDouble(GlobalVariable.Confinsdata[confinsindex++]))), Math.round(Double.parseDouble((result[arrayindex++]))), FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify ownership'
arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        1, GlobalVariable.NumofMS - 1, GlobalVariable.ReasonFailedStoredDB)
}

