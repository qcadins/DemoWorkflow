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
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagFailed=0
if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'EMPLOYEE') {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobDataEmployeeGuarantor'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'PROFESSIONAL') {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobDataProfessionalGuarantor'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'NON PROFESSIONAL') {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobDataNonProfessionalGuarantor'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobDataSMEGuarantor'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(GlobalVariable.NumofGuarantor, 4))
if(GlobalVariable.FlagFailed==0){
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
			findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact-Customer/input_ContactPersonName'), GlobalVariable.NumofGuarantor, '3.JobData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantor, '3.JobData')
	}
	
}
