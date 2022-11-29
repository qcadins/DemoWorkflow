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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/JobData - Company - GuarantorPersonal')

if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
	'EMPLOYEE') {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/TabJobDataEmployee'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
	'PROFESSIONAL') {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/TabJobDataProfessional'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
	'NON PROFESSIONAL') {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/TabJobDataNonProfessional'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/TabJobDataSME'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(GlobalVariable.NumofGuarantor, 4))
	
	if (iscompleteMandatory && GlobalVariable.FlagFailed == 0) {
		'Check alert'
		GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS, '3.JobData')
	}
		
	if (GlobalVariable.FlagFailed == 0) {
		'Check save Process write to excel'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/JobData - Company - ManagementShareholderPersonal').getValue(
					GlobalVariable.NumofMS, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ContactPersonName'),
			GlobalVariable.NumofMS, '3.JobData')
	
		if (iscompleteMandatory == 0) {
			'Check error validasi'
			CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'),
				GlobalVariable.NumofMS, '3.JobData')
		}
	}
	
	if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
		GlobalVariable.NumofVerifStore = GlobalVariable.NumofMS
			
		'call test case verify job data store data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabJobDataVerifStoreDataDB'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	}