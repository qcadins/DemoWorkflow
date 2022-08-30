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
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Profession Name_btn btn-raised btn-primary'))

'input kode profesi'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Profession Code_professionCodeId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 40))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusFailed)
}

'pilih job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/select_JobPosition'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 42), false)

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Is Well Known Company_ng-untouched ng-pristine ng-valid'), 
    2, FailureHandling.OPTIONAL)) {
    'check is well known company'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 43) == 'Yes') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Is Well Known Company_ng-untouched ng-pristine ng-valid'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Is Well Known Company_ng-untouched ng-pristine ng-valid'), 
    2)) {
    'check is well known company'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 43) == 'No') {
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Is Well Known Company_ng-untouched ng-pristine ng-valid'))
    }
}

'input nama perusahaan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Company Name'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 44))

'click button lookup industry type'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Industry Type_btn'))

'input industry code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Industry Type Code'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 45))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusFailed)
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 47).length() > 0) {
    'pilih big/medium/small'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/select_--Select One-- Big  Medium  Small'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 47), false)
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 48).length() > 0) {
    'input start working date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Start Working Date_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 48))
}

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Job Title Name_form-control ng-untouched ng-pristine ng-valid'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 49))

'input number of employee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/input_Number of Employee_form-control ng-untouched ng-pristine ng-valid'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 50))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 51).length() > 0) {
    'select investment type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/select_--Select One-- Domestic Investment  Foreign Investment'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 51), false)
}

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 53))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 54))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 55))

'click search zipcode'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn btn-raised btn-primary'))

'input zipcode'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_ZipCode'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 56))

'input Kecamatan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_kecamatan'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 58))

'input kelurahan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_Kelurahan'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 57))

'input kota'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_kota'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 59))

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
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusFailed)
}

'input note'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_notes'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 60), FailureHandling.OPTIONAL)

'input phone1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 61), FailureHandling.OPTIONAL)

'input phone1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 62), FailureHandling.OPTIONAL)

'input phone1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 63), FailureHandling.OPTIONAL)

'input phone2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 64), FailureHandling.OPTIONAL)

'input phone2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 65), FailureHandling.OPTIONAL)

'input phone2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 66), FailureHandling.OPTIONAL)

'input phone3 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 67), FailureHandling.OPTIONAL)

'input phone3 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 68), FailureHandling.OPTIONAL)

'input phone3 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 69), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 70), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 71), FailureHandling.OPTIONAL)

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 72).length() > 0) {
    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 72), false)
}

'click button check EKYC'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Check eKYC'))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 74).length() > 0) {
    'input Previous Company Name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Previous Company Name_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 74))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 75).length() > 0) {
    'input previous employment date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Previous Employment Date_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 75))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 77).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 77))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT  PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 78))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 79))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 80).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn PreviousJobAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 80))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 82))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 81))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 83))

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
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 84))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 85))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 86))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 87))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 88))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 89))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 90))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 91))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 92))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 93))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 94))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_PreviousJobAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 95))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 96).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_OwnerShip PreviousJobAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 96), false)
    }
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 98).length() > 0) {
    'input other business name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Name_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 98))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 99).length() > 0) {
    'input other business type'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Type_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 99))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 100).length() > 0) {
    'input other business industry type'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Industry Type_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 100))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 101).length() > 0) {
    'input other job position'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Other Job Position_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 101))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 102).length() > 0) {
    'input establishment date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Establishment Date_'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 102))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 104).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 104))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RT  OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 105))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_RW OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 106))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 107).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn OtherBusinessAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 107))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 109))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 108))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Kota_OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 110))

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
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 111))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 112))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 113))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 114))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 115))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 116))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 117))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 118))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 119))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_ext_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 120))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 121))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_OtherBusinessAddress'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 122))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
        GlobalVariable.NumofGuarantor, 123).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/select_Ownership OtherBusinessAddress'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/JobData - Personal - Guarantor').getValue(
                GlobalVariable.NumofGuarantor, 123), false)
    }
}

'click save and continue button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Save  Continue'))

WebUI.acceptAlert(FailureHandling.OPTIONAL)

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/label_Profession Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME - Personal/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusSuccess)

    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofGuarantor - 
            1, GlobalVariable.StatusWarning)
    }
}

