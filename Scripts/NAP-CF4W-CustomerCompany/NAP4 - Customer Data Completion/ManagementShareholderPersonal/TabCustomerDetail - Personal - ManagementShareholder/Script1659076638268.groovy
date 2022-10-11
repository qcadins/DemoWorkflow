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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileManagementShareholderPersonal

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal')

'input gelar nama depan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Gelar Depan Nama'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 14))

'input num of dependents'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Num Of Dependents'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 15))

'input num of residence'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Num Of Residence (Last 3 Years)'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 16))

'input family card num'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Family Card No'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 17))

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2, FailureHandling.OPTIONAL)) {
    'check RIP'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 18) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'), 
    2)) {
    'check RIP'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 18) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Rest In Peace'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2, FailureHandling.OPTIONAL)) {
    'check VIP'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 19) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'), 
    2)) {
    'check VIP'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 19) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_VIP_'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2, FailureHandling.OPTIONAL)) {
    'Check Affiliation'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 20) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'), 
    2)) {
    'Check Affiliation'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 20) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Affiliation with multifinance checkbox'))
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 21).length() > 2) {
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Customer Group'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Customer Name_custNameId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 21))

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
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 22), false)

'Input Nickname'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Nick Name_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 23))

'input gelar nama belakang'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Gelar Belakang Nama'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 24))

'pilih local/foreigner'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_ForeignerLocal'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 25), false)

'pilih country jika foreigner'
if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 25) == 'Foreigner') {
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Country'))

    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/input_Country Code_countryCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 26))

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
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel Reason Lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        'flagfailed +1 karena gagal melakukan lookup'
        (GlobalVariable.FlagFailed)++
    }
}

'pilih pendidikan'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_S1S2S3SDSMASMP'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 28), false)

'pilih religion'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/select_AGAMA'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 29), false)

'input VIP Notes'
if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 30).length() > 1) {
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/textarea_VIP Notes'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 30))
}

'click save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/button_Save  Continue'))

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 4)) == 0) {
    'Check alert'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, '1.CustomerDetail')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/button_Add'), 
        GlobalVariable.NumofFamily, '1.CustomerDetail')

    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofFamily, '1.CustomerDetail')
    }
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/label_Full Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.CustomerDetail', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
    }
}

if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
	GlobalVariable.NumofVerifStore = GlobalVariable.NumofFamily
			
	'call test case verify customer detail store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabCustomerDetailVerifStoreData'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}