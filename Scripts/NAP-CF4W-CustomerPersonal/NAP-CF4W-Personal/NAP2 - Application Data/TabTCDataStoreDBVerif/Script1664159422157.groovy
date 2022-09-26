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

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TermConditionStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

//resultarray = result.split(', ')
resultarray = result.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    } else if ((resultarray[i]).equalsIgnoreCase('true')) {
        (resultarray[i]) = '1'
    } else if ((resultarray[i]).equalsIgnoreCase('false')) {
        (resultarray[i]) = '0'
    }
}

println(resultarray)

int arrayindex = 0

for (i = 1; i <= resultarray.size()/4; i++) {
    modifyObjectRequired = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'), 
        'xpath', 'equals', ('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' + 
        i) + ']/td[4]', true)

    modifyObjectCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/input_Checkbox'), 
        'xpath', 'equals', ('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' + 
        i) + ']/td[5]/input', true)

    modifyObjectPromiseDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'), 
        'xpath', 'equals', ('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' + 
        i) + ']/td[7]/input', true)

    modifyObjectExpiredDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/input_expiredDate'), 
        'xpath', 'equals', ('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' + 
        i) + ']/td[8]/input', true)

    modifyObjectWaived = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/input_waived'), 
        'xpath', 'equals', ('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' + 
        i) + ']/td[6]/input', true)

    if ((resultarray[arrayindex]).equalsIgnoreCase('1')) {
        'verify checkbox checked'
        WebUI.verifyElementChecked(modifyObjectCheckbox, 5, FailureHandling.OPTIONAL)
		arrayindex++
    } else if ((resultarray[arrayindex]).equalsIgnoreCase('0')) {
        'verify checkbox unchecked'
        WebUI.verifyElementNotChecked(modifyObjectCheckbox, 5, FailureHandling.OPTIONAL)
		arrayindex++
    }
    
    WebUI.verifyMatch(WebUI.getText(modifyObjectPromiseDate), resultarray[arrayindex++], false, FailureHandling.OPTIONAL)

    WebUI.verifyMatch(WebUI.getText(modifyObjectExpiredDate), resultarray[arrayindex++], false, FailureHandling.OPTIONAL)

    if ((resultarray[arrayindex]).equalsIgnoreCase('1')) {
        'verify checkbox checked'
        WebUI.verifyElementChecked(modifyObjectWaived, 5, FailureHandling.OPTIONAL)
		arrayindex++
    } else if ((resultarray[arrayindex]).equalsIgnoreCase('0')) {
        'verify checkbox unchecked'
        WebUI.verifyElementNotChecked(modifyObjectWaived, 5, FailureHandling.OPTIONAL)
		arrayindex++
    }
    
    continue
}

