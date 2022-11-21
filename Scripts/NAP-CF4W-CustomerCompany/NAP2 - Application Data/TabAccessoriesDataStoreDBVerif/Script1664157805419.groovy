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

'call custom keyword NAP2AccessoriesStoreDB'
ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2AccessoriesStoreDB'(sqlconnectionLOS, findTestData(
        'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 13))

'declare arraynum'
int arraynum = 0

for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (Integer.parseInt(GlobalVariable.CountofAccessories) + 
1); (GlobalVariable.NumofAccessories)++) {
    'verify supplier code'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            13).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify supplier name'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            14).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify accessories code'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            15).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify accessories name'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            16).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify accessories price'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            17).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
        18).equalsIgnoreCase('Percentage')) {
        'verify DP Percent'
        WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
                19).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)

        'skip amount'
        arraynum++
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
        18).equalsIgnoreCase('Amount')) {
        'skip percentage'
        arraynum++

        'verify DP Amount'
        WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
                20).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)
    }
    
    'verify notes'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 
            21).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}