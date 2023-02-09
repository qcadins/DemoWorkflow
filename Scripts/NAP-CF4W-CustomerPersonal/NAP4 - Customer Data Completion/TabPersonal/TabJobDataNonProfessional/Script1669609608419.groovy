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

'klik button lookup profession'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/button_Profession Name_btn btn-raised btn-primary'))

'input profession code'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/input_Profession Code_professionCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 38))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/a_Select'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/button_Back'))

	getDataFile()
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.FindDataFile(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.StatusReasonLookup)
	
    GlobalVariable.FlagFailed = 1
}

'input job title'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/input_Job Title Name'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 40))

if(GlobalVariable.APPSTEP == 'CUSTOMER' && GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 126)=="Yes"){
'Click check ekyc'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Check eKYC'))
}

'klik button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataNonProfessional/button_Save  Continue'))

if(GlobalVariable.APPSTEP == 'CUSTOMER' && GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 126)=="No"){
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

def getDataFile(){
	if(GlobalVariable.APPSTEP == 'CUSTOMER'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerPersonal)
		
		'declare data file Global variable'
		GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData')
	}else if(GlobalVariable.APPSTEP == 'FAMILY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)
		
		'declare data file Global variable'
		GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/JobData')
	}else if(GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)
		
		GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData')
	}
}