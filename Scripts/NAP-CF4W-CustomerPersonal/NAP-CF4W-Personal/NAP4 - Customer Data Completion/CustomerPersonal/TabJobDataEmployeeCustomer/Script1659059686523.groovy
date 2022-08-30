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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileCustomerPersonal

GlobalVariable.DataFilePath = filePath

'click lookup profesi'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Profession Name_btn btn-raised btn-primary'))

'input kode profesi'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Profession Code_professionCodeId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 12))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
}

'pilih job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_JobPosition'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 14), false)

'pilih contract/permanent'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_--Select One-- Contract  Permanent'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 15), false)

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'), 
    2, FailureHandling.OPTIONAL)) {
    'check internal employee'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 16) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'), 
    2)) {
    'check internal employee'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 16) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'), 
    2, FailureHandling.OPTIONAL)) {
    'check is well known company'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 17) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'), 
    2)) {
    'check is well known company'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 17) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'))
    }
}

'input nama perusahaan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_CompanyName'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 18))

'click button lookup industry type'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Industry Type Code_industryTypeCodeId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 19))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 21).length() > 0) {
    'pilih big/medium/small'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_--Select One-- Big  Medium  Small'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 21), false, FailureHandling.OPTIONAL)
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 22).length() > 0) {
    'input start working date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_StartWorkingDate'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 22))
}

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Job Title Name'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 23))

'input number of employee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Number of Employee_'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 24))

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 53))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 54))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 55))

'click search zipcode'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn btn-raised btn-primary'))

'input zipcode'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_ZipCode'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 56))

'input Kecamatan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_kecamatan'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 58))

'input kelurahan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_Kelurahan'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 57))

'input kota'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_kota'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 59))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
}

'input note'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_notes'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 60), FailureHandling.OPTIONAL)

'input phone1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 61), FailureHandling.OPTIONAL)

'input phone1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 62), FailureHandling.OPTIONAL)

'input phone1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 63), FailureHandling.OPTIONAL)

'input phone2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 64), FailureHandling.OPTIONAL)

'input phone2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 65), FailureHandling.OPTIONAL)

'input phone2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 66), FailureHandling.OPTIONAL)

'input phone3 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 67), FailureHandling.OPTIONAL)

'input phone3 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 68), FailureHandling.OPTIONAL)

'input phone3 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 69), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 70), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 71), FailureHandling.OPTIONAL)

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 72).length() > 0) {
    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 72), false)
}

'click button check EKYC'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Check eKYC'))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 74).length() > 0) {
    'input Previous Company Name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Previous Company Name_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 74))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 75).length() > 0) {
    'input previous employment date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Previous Employment Date_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 75))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 77).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 77))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT  PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 78))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 79))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 80).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn PreviousJobAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 80))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 82))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 81))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 83))

        'click search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            flagWarning++
        }
    }
    
    'input note'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Notes_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 84))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 85))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 86))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 87))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 88))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 89))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 90))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 91))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 92))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 93))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 94))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 95))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 96).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_OwnerShip PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 96), false)
    }
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 98).length() > 0) {
    'input other business name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Name_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 98))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 99).length() > 0) {
    'input other business type'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Type_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 99))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 100).length() > 0) {
    'input other business industry type'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Industry Type_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 100))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 101).length() > 0) {
    'input other job position'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Job Position_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 101))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 102).length() > 0) {
    'input establishment date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Establishment Date_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 102))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
    GlobalVariable.NumofColm, 104).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 104))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT  OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 105))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 106))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 107).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn OtherBusinessAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 107))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 109))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 108))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 110))

        'click search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            flagWarning++
        }
    }
    
    'input note'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Notes_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 111))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 112))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 113))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 114))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 115))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 116))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 117))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 118))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 119))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 120))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 121))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
            GlobalVariable.NumofColm, 122))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
        GlobalVariable.NumofColm, 123).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_Ownership OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/JobData - Personal - Customer').getValue(
                GlobalVariable.NumofColm, 123), false)
    }
}

'click save and continue button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/label_Profession Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)

    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)
    }
}

