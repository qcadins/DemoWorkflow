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

if(GlobalVariable.APPSTEP == 'SHAREHOLDER PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.2 CustomerDataCompletion-Company - ManagementShareholderPersonal.xlsm")
	
	'declare data file Global variable'
	GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/JobData'
}else if(GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.4 CustomerDataCompletion-Company - GuarantorPersonal.xlsm")
	
	GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/JobData'
}

if (WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/CustomerModelCode')) == 
'EMPLOYEE') {
	'call tc job data employee'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobDataEmployee'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/CustomerModelCode')) == 
'PROFESSIONAL') {
	'call tc job data professional'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobDataProfessional'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/CustomerModelCode')) == 
'NON PROFESSIONAL') {
	'call tc job data non professional'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobDataNonProfessional'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
	'call tc job data sme'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobDataSME'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

Integer iscompleteMandatory = Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4))
if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, '3.JobData')
}

if(GlobalVariable.FlagFailed==0){
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
			findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ContactPersonName'), GlobalVariable.ColmNAP4, '3.JobData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.ColmNAP4, '3.JobData')
	}
}

if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
	GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4
	
	'call test case store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabJobDataVerifStoreDataDB'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}