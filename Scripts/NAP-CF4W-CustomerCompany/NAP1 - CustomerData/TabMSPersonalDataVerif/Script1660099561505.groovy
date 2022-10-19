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

String servername = findTestData('Login/Login').getValue(1, 7)

String instancename = findTestData('Login/Login').getValue(2, 7)

String username = findTestData('Login/Login').getValue(3, 7)

String password = findTestData('Login/Login').getValue(4, 7)

String database = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 7)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.CustomerDataPersonal'(sqlconnection, WebUI.getAttribute(findTestObject(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
        'value')).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
	if ((resultarray[i]).equalsIgnoreCase('null')) {
		(resultarray[i]) = ''
	}
	
	if ((resultarray[i]).equalsIgnoreCase('TRUE')) {
		(resultarray[i]) = 'Yes'
	} else if ((resultarray[i]).equalsIgnoreCase('FALSE')) {
		(resultarray[i]) = 'No'
	}
}

for (i = 0; i < resultarray.size(); i++) {
    'verify resultarray == confinsdata'
    WebUI.verifyMatch(resultarray[i], GlobalVariable.confinsdata[i], false, FailureHandling.OPTIONAL)
}
