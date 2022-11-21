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

int flagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderPersonal)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal')

'input gelar nama depan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Gelar Depan Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 14))

'input num of dependents'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Num Of Dependents'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 15))

'input num of residence'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Num Of Residence (Last 3 Years)'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 16))

'input family card num'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Family Card No'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 17))

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2, FailureHandling.OPTIONAL)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 18) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2)) {
    'check RIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 18) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2, FailureHandling.OPTIONAL)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 19) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2)) {
    'check VIP'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 19) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2, FailureHandling.OPTIONAL)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 20) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2)) {
    'Check Affiliation'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 20) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 21).length() > 2) {
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Customer Group'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Customer Name_custNameId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 21))

    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/Button_X'))

        flagWarning++
    }
}

'pilih salutation'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_MrMrsMs'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 22), false)

'Input Nickname'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Nick Name_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 23))

'input gelar nama belakang'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Gelar Belakang Nama'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 24))

'pilih local/foreigner'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_ForeignerLocal'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 25), false)

'pilih country jika foreigner'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 25) == 'Foreigner') {
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Country'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Country Code_countryCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 26))

    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_SearchCountry'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusFailed)

        'write to excel Reason Lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            1, GlobalVariable.NumofMS - 1, GlobalVariable.StatusReasonLookup)

        'flagfailed +1 karena gagal melakukan lookup'
        (GlobalVariable.FlagFailed)++
    }
}

'pilih pendidikan'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_S1S2S3SDSMASMP'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 28), false)

'pilih religion'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_AGAMA'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 29), false)

'input VIP Notes'
if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 30).length() > 1) {
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/textarea_VIP Notes'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 30))
}

'click save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Save  Continue'))

if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS, '1.CustomerDetail')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.NumofMS, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/button_Add'), 
        GlobalVariable.NumofMS, '1.CustomerDetail')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofMS, '1.CustomerDetail')
    }
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/label_Full Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
    }
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonalCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofMS

    'call test case verify customer detail store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabCustomerDetailVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

