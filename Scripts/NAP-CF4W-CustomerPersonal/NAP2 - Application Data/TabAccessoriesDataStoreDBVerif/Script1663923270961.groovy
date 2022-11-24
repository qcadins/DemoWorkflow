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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2AccessoriesStoreDB'(sqlconnection, findTestData(
        'NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.CopyAppColm, 
        12))

int arraynum = 0

ArrayList<Boolean> arrayMatch = new ArrayList<>()

for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (Integer.parseInt(GlobalVariable.CountofAccessories) + 
1); (GlobalVariable.NumofAccessories)++) {
    'verify supplier code'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 13).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify supplier name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 14).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify accessories code'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 15).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify accessories name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 16).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify accessories price'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 17).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
        GlobalVariable.NumofAccessories, 18).equalsIgnoreCase('Percentage')) {
        'verify DP Percent'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
                GlobalVariable.NumofAccessories, 19).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'skip amount'
        arraynum++
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
        GlobalVariable.NumofAccessories, 18).equalsIgnoreCase('Amount')) {
        'skip percentage'
        arraynum++

        'verify DP Amount'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
                GlobalVariable.NumofAccessories, 20).replace(',', ''), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
    
    'verify notes'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(
            GlobalVariable.NumofAccessories, 21).toUpperCase(), (result[arraynum++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
		0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
		1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedStoredDB)

}
