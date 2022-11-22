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

int flagWarning = 0

 GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/CustomerDetail - Personal - Guarantor')

'input gelar nama depan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Gelar Depan Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 14))

'input num of dependents'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Num Of Dependents'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 15))

'input num of residence'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Num Of Residence (Last 3 Years)'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 16))

'input family card num'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Family Card No'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 17))

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2, FailureHandling.OPTIONAL)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 18) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 18) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2, FailureHandling.OPTIONAL)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 19) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 19) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2, FailureHandling.OPTIONAL)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 20) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 20) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 21).length() > 2) {
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/button_Customer Group'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Customer Name_custNameId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 21))

    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        flagWarning++
    }
}

'pilih salutation'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/select_MrMrsMs'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 22), false)

'Input Nickname'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Nick Name_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 23))

'input gelar nama belakang'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Gelar Belakang Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 24))

'pilih local/foreigner'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/select_ForeignerLocal'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 25), false)

'pilih country jika foreigner'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 25) == 'Foreigner') {
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/button_Country'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/input_Country Code_countryCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 26))

    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/button_SearchCountry'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

'pilih pendidikan'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/select_S1S2S3SDSMASMP'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 28), false)

'pilih religion'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/select_AGAMA'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 29), false)

'input VIP Notes'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 30).length() > 1) {
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/textarea_VIP Notes'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 30))
}

'click save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantor, '1.CustomerDetail')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Add'), 
        GlobalVariable.NumofGuarantor, '1.CustomerDetail')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantor, 
            '1.CustomerDetail')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/label_Full Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusWarning)
    }
}

if (GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes") {
	
	GlobalVariable.NumofVerifStore = GlobalVariable.NumofGuarantor
	
    'call test case store data verif customer detail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabCustomerDetailVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

