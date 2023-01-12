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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagFailed = 0

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompany)	
	
'declare data file Global variable'
GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail')

if(GlobalVariable.RoleCompany == 'Testing'){
	'connect DB FOU'
	Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
	
	'get custmodel ddl value from db'
	ArrayList<String> custmodel = CustomKeywords.'dbConnection.checkCustomer.checkCustomerModelCompany'(sqlConnectionFOU)
	
	'get total label from ddl custmodel'
	int totalddlcustmodel = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/select_Select One Corporate  Non Corporate'))
	
	'verify total ddl religion confins = total ddl db'
	WebUI.verifyEqual(totalddlcustmodel - 1, custmodel.size())
	
	'verify isi ddl custmodel confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/select_Select One Corporate  Non Corporate'),
		custmodel) == false) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.CustomerDetail', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'custmodel')

		(GlobalVariable.FlagFailed)++
	}
}

'input establishment date'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Establishment Date'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 14))

'input no of employee'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_No Of Employee'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 15))

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16).length() > 2) {
	'click button customer group'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/button_Customer Group_'))

	'input customer no'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Customer No_custNoId'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16))

	'input customer name'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Customer Name_custNameId'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 17))

	'click button search'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/button_Search'))

	'verify input error'
	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/a_Select'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/Button_X'))

		'flagwarning +1'
		GlobalVariable.FlagWarning++
	}
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_VIP_border'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18) == 'Yes') {
		'Click VIP checklist'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_VIP_border'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_VIP_border'),
	GlobalVariable.TimeOut)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18) == 'No') {
		'Click VIP checklist'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_VIP_border'))
	}
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 19) == 'Yes') {
		'click affiliation and multifinance'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'),
	GlobalVariable.TimeOut)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 19) == 'No') {
		'click affiliation and multifinance'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'))
	}
}

'click button industry type'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/input_Industry Type Code_industryTypeCodeId'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 20))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/a_Select'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click select'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/a_Select'))
} else {
	'click X'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/Button_X'))

	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.CustomerDetail', Address, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

	'Flagfailed +1 karena gagal melakukan lookup'
	(GlobalVariable.FlagFailed)++
}

'select customer model'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/select_Select One Corporate  Non Corporate'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 22), false)

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18) == 'Yes') {
	'input VIP note'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/textarea_VIP Notes'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 23))
}

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 4))

if (iscompleteMandatory == 0 && (GlobalVariable.FlagFailed == 0)) {
	'Check alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4,
		'1.CustomerDetail')
}

'check if flagfailed = 0'
if (GlobalVariable.FlagFailed == 0) {
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
				GlobalVariable.ColmNAP4, 4)), findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Add'),
		GlobalVariable.ColmNAP4, '1.CustomerDetail')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		
		'Check error validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP/NAP4-CustomerDataCompletion/errorvalidasi'),
			GlobalVariable.ColmNAP4, '1.CustomerDetail')
	}
}

'check iflabel estahblishment date is present'
if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerDetail/label_Establishment Date'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
	'check if flagwarning = 0'
	if (GlobalVariable.FlagWarning > 0) {
		'write to excel status warning'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.CustomerDetail',
			0, GlobalVariable.ColmNAP4 - 1, GlobalVariable.StatusWarning)
	}
}

'check if role = testing & store Db = yes'
if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
	'declare numofverif = numof colm'
    GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

    'call test case verify customer detail store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabCustomerDetailVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}