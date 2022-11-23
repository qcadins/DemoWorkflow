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

'declare arraymatch arraylist'
ArrayList<String> arrayMatch = new ArrayList<String>()

'call keyword Ms data company store db lookup'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.MSDataStoreDBCompanyLookUp'(sqlconnectionLOS, 
    GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofMS, 12), GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofMS, 50))

'ganti value null > "" (String kosong)'
for (int i = 0; i <= (result.size() - 1); i++) {
    if ((result[i]).equalsIgnoreCase('TRUE')) {
        (result[i]) = 'Yes'
    } else if ((result[i]).equalsIgnoreCase('FALSE')) {
        (result[i]) = 'No'
    }
}

'looping verif confinsdata = db'
for (int i = 0; i < GlobalVariable.Confinsdata.size(); i++) {
    'verify result == confinsdata'
    arrayMatch.add(WebUI.verifyMatch((result[i]).toUpperCase(), (GlobalVariable.Confinsdata[i]).toUpperCase().replace('0 ', 
                '0'), true, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
        1, GlobalVariable.NumofMS - 1, GlobalVariable.ReasonFailedStoredDB)
}

