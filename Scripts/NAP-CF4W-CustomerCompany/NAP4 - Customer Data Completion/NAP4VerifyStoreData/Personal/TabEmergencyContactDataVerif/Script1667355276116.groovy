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

'connect DB FOU'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4EmergencyContactDataVerif'(sqlconnectionFOU, 
    WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact/input_ContactPersonName'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact/input_ID No'), 
        'value'))

'ganti value null > "" (String kosong)'
for (i = 0; i <= (GlobalVariable.Confinsdata.size() - 1); i++) {
    if ((GlobalVariable.Confinsdata[i]) == null) {
        (GlobalVariable.Confinsdata[i]) = ''
    }
}

for (i = 0; i < result.size(); i++) {
    'verify resultarray == Confinsdata'
    WebUI.verifyMatch(result[i], GlobalVariable.Confinsdata[i], false, FailureHandling.OPTIONAL)
}

