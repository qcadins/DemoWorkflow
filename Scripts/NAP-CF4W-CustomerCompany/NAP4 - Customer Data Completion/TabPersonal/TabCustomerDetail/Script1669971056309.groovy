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

GlobalVariable.FlagWarning = 0

GlobalVariable.FlagFailed = 0

if(GlobalVariable.NAP4 == 'SHAREHOLDER PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderPersonal)
	
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail')
}else if(GlobalVariable.NAP4 == 'GUARANTOR PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)
	
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail')
}

'input gelar nama depan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Gelar Depan Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 14))

'input num of dependents'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Num Of Dependents'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 15))

'input num of residence'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Num Of Residence (Last 3 Years)'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16))

'input family card num'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Family Card No'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 17))

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Rest In Peace'), 
    2, FailureHandling.OPTIONAL)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Rest In Peace'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Rest In Peace'), 
    2)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Rest In Peace'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_VIP_'), 
    2, FailureHandling.OPTIONAL)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 19) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_VIP_'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_VIP_'), 
    2)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 19) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_VIP_'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Affiliation with multifinance checkbox'), 
    2, FailureHandling.OPTIONAL)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 20) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Affiliation with multifinance checkbox'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Affiliation with multifinance checkbox'), 
    2)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 20) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Affiliation with multifinance checkbox'))
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 21).length() > 2) {
	'klik lookup customer group'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/button_Customer Group'))

	'set text custname'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Customer Name_custNameId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 21))

	'klik button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        GlobalVariable.FlagWarning++
    }
}

'pilih salutation'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/select_MrMrsMs'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 22), false)

'Input Nickname'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Nick Name_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 23))

'input gelar nama belakang'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Gelar Belakang Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 24))

'pilih local/foreigner'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/select_ForeignerLocal'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 25), false)

'pilih country jika foreigner'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 25) == 'Foreigner') {
	'klik button lookup country'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/button_Country'))

	'input country code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/input_Country Code_countryCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 26))

	'klik search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/button_SearchCountry'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.CustomerDetail', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

'pilih pendidikan'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/select_S1S2S3SDSMASMP'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 28), false)

'pilih religion'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/select_AGAMA'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 29), false)

'input VIP Notes'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 30).length() > 1) {
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/textarea_VIP Notes'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 30))
}

'click save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, '1.CustomerDetail')
}

'check if flagfailed = 0'
if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Add'), 
        GlobalVariable.ColmNAP4, '1.CustomerDetail')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.ColmNAP4, '1.CustomerDetail')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/label_Full Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
	'check if flagwarning = 0'
    if (GlobalVariable.FlagWarning > 0) {
		'write to excel warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.ColmNAP4 - 1, GlobalVariable.StatusWarning)
    }
}

if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
	'declare numofverif = numof colm'
	GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4
	
    'call test case store data verif customer detail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabCustomerDetailVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}