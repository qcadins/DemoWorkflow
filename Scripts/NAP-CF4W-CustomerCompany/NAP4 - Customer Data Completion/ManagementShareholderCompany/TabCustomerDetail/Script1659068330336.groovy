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

GlobalVariable.FlagFailed = 0

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany')

'input establishment date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Establishment Date'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14))

'input no of employee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_No Of Employee'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 15))

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 16).length() > 2) {
	'click button customer group'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/button_Customer Group_'))

	'input customer no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Customer No_custNoId'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 16))

	'input customer name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Customer Name_custNameId'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 17))

	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/button_Search'))

	'verify input error'
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/a_Select'),
		10, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/Button_X'))

		'flagwarning +1'
		GlobalVariable.FlagWarning++
	}
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_VIP_border'),
	2, FailureHandling.OPTIONAL)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 18) == 'Yes') {
		'Click VIP checklist'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_VIP_border'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_VIP_border'),
	2)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 18) == 'No') {
		'Click VIP checklist'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_VIP_border'))
	}
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'),
	2, FailureHandling.OPTIONAL)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 19) == 'Yes') {
		'click affiliation and multifinance'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'),
	2)) {
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 19) == 'No') {
		'click affiliation and multifinance'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Affiliation with Multifinance'))
	}
}

'click button industry type'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/input_Industry Type Code_industryTypeCodeId'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 20))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/a_Select'),
	10, FailureHandling.OPTIONAL)) {
	'click select'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/a_Select'))
} else {
	'click X'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/Button_X'))

	'click button back'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.CustomerDetail', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

	'Flagfailed +1 karena gagal melakukan lookup'
	(GlobalVariable.FlagFailed)++
}

'select customer model'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/select_Select One Corporate  Non Corporate'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 22), false)

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 18) == 'Yes') {
	'input VIP note'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/textarea_VIP Notes'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 23))
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && (GlobalVariable.FlagFailed == 0)) {
	'Check alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm,
		'1.CustomerDetail')
}

'check if flagfailed = 0'
if (GlobalVariable.FlagFailed == 0) {
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
				GlobalVariable.NumofColm, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation/button_Add'),
		GlobalVariable.NumofColm, '1.CustomerDetail')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		
		'Check error validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'),
			GlobalVariable.NumofColm, '1.CustomerDetail')
	}
}

'check iflabel estahblishment date is present'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail/label_Establishment Date'),
	5, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
} else {
	'check if flagwarning = 0'
	if (GlobalVariable.FlagWarning > 0) {
		'write to excel status warning'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.CustomerDetail',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
	}
}

'check if role = testing & store Db = yes'
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
	'declare numofverif = numof colm'
	GlobalVariable.NumofVerifStore = GlobalVariable.NumofColm

	'call test case verify customer detail store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabCustomerDetailVerifStoreDataDB'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}