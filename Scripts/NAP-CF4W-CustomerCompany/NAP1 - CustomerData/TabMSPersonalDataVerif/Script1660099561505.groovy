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

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.CustomerDataPersonal'(sqlconnectionFOU, WebUI.getAttribute(findTestObject(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
        'value'), WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
        'value'))


'ganti value null > "" (String kosong)'
for (int i = 0; i <= (result.size() - 1); i++) {

	if ((result[i]).equalsIgnoreCase('TRUE')) {
		(result[i]) = 'Yes'
	} else if ((result[i]).equalsIgnoreCase('FALSE')) {
		(result[i]) = 'No'
	}
}

for (int i = 0; i < result.size(); i++) {
    'verify result == confinsdata'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(result[i], GlobalVariable.Confinsdata[i], false, FailureHandling.OPTIONAL))
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
				0, GlobalVariable.NumofMS-1, GlobalVariable.StatusFailed)

		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
				1, GlobalVariable.NumofMS-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}
