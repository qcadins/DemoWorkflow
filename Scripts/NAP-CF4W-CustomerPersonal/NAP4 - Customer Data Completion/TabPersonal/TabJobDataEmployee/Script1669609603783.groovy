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

GlobalVariable.FlagWarning = 0

'call function check ddl'
checkDDL()

'click lookup profesi'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Profession Name_btn btn-raised btn-primary'))

'input kode profesi'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Profession Code_professionCodeId'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

'verify input error'
verifyInputError()

'pilih job position'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_JobPosition'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16), false)

'pilih contract/permanent'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Contract  Permanent'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 17), false)

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Internal Employee_ng-untouched ng-pristine ng-valid'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'check internal employee'
    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Internal Employee_ng-untouched ng-pristine ng-valid'), 
    GlobalVariable.TimeOut)) {
    'check internal employee'
    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
    }
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Is Well Known Company'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'check is well known company'
    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 19) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Is Well Known Company'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Is Well Known Company'), 
    GlobalVariable.TimeOut)) {
    'check is well known company'
    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 19) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Is Well Known Company'))
    }
}

'input nama perusahaan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_CompanyName'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 20))

'click button lookup industry type'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Industry Type Code_industryTypeCodeId'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 21))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

'verify input error'
verifyInputError()

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 23).length() > 0) {
    'pilih big/medium/small'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Big  Medium  Small'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 23), false, FailureHandling.OPTIONAL)
}

'input start working date'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_StartWorkingDate'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 24))

'input job title'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Job Title Name'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 25))

'input number of employee'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Number of Employee_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 26))

'input address'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 55))

'input RT'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 56))

'input RW'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 57))

'click search zipcode'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn btn-raised btn-primary'))

'input zipcode'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_ZipCode'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 58))

'input Kecamatan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_kecamatan'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 60))

'input kelurahan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_Kelurahan'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 59))

'input kota'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_kota'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 61))

'click search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

'verify input error'
verifyInputError()

'input note'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_notes'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 62), FailureHandling.OPTIONAL)

'input phone1 Area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 63), FailureHandling.OPTIONAL)

'input phone1 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 64), FailureHandling.OPTIONAL)

'input phone1 Extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Ext'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 65), FailureHandling.OPTIONAL)

'input phone2 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 66), FailureHandling.OPTIONAL)

'input phone2 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 67), FailureHandling.OPTIONAL)

'input phone2 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 68), FailureHandling.OPTIONAL)

'input phone3 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 69), FailureHandling.OPTIONAL)

'input phone3 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 70), FailureHandling.OPTIONAL)

'input phone3 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Ext'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 71), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 72), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 73), FailureHandling.OPTIONAL)

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 74).length() > 0) {
    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 74), false, FailureHandling.OPTIONAL)
}

if(GlobalVariable.APPSTEP == 'CUSTOMER' && findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 126)=="Yes"){
'click eKYC'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Check eKYC'))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 76).length() > 0) {
    'input Previous Company Name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Previous Company Name_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 76))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 77).length() > 0) {
    'input previous employment date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Previous Employment Date_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 77))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 79).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 79))

    'input RT'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT  PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 80))

    'input RW'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 81))

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 82).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn PreviousJobAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_PreviousJobAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 82))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_PreviousJobAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 84))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_PreviousJobAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 83))

        'input kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_PreviousJobAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 85))

        'click search'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/TabApplicationData/Button_X'))

            GlobalVariable.FlagWarning++
        }
    }
    
    'input note'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Notes_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 86))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 87))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 88))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_ext_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 89))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 90))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 91))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 92))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 93))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 94))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Ext_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 95))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 96))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number_PreviousJobAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 97))

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 98).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_OwnerShip PreviousJobAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 98), false, FailureHandling.OPTIONAL)
    }
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 100).length() > 0) {
    'input other business name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Name_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 100))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 101).length() > 0) {
    'input other business type'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Type_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 101))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 102).length() > 0) {
    'input other business industry type'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Industry Type_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 102))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 103).length() > 0) {
    'input other job position'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Job Position_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 103))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 104).length() > 0) {
    'input establishment date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Establishment Date_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 104))
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 106).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 106))

    'input RT'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT  OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 107))

    'input RW'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 108))

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 109).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn OtherBusinessAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_OtherBusinessAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 109))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_OtherBusinessAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 111))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_OtherBusinessAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 110))

        'input kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_OtherBusinessAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 112))

        'click search'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/TabApplicationData/Button_X'))

            GlobalVariable.FlagWarning++
        }
    }
    
    'input note'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Notes_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 113))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 114))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 115))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_ext_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 116))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 117))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 118))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 119))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 120))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 121))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_ext_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 122))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 123))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number_OtherBusinessAddress'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 124))

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 125).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Ownership OtherBusinessAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 125), false, FailureHandling.OPTIONAL)
    }
}

'click save and continue button'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Save  Continue'))

if(GlobalVariable.APPSTEP == 'CUSTOMER' && findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 126)=="No"){
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/label_Profession Name'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Back'))
} else {
    if (GlobalVariable.FlagWarning > 0) {
		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.ColmNAP4 - 
            1, GlobalVariable.StatusWarning)
    }
}

def verifyInputError(){
	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/Button_X'))

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.StatusReasonLookup)
	
		GlobalVariable.FlagFailed = 1
	}
}

def checkDDL(){
	if(GlobalVariable.Role == 'Testing'){
        'connect DB FOU'
        Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

        'get jobposition ddl value from db'
        ArrayList<String> jobposition = CustomKeywords.'nap4Data.checkNAP4.checkjobPosition'(sqlConnectionFOU)

        'get total label from ddl jobposition'
        int totalddljobposition = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_JobPosition'))

        'verify total ddl jobposition confins = total ddl db'
        WebUI.verifyEqual(totalddljobposition - 1, jobposition.size())

        'verify isi ddl jobposition confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_JobPosition'), 
            jobposition) == false) {

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'jobposition')

            (GlobalVariable.FlagFailed)++
        }
        
        'get jobStatus ddl value from db'
        ArrayList<String> jobStatus = CustomKeywords.'nap4Data.checkNAP4.checkjobStatus'(sqlConnectionFOU)

        'get total label from ddl jobposition'
        int totalddljobStatus = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Contract  Permanent'))

        'verify total ddl jobStatus confins = total ddl db'
        WebUI.verifyEqual(totalddljobStatus - 1, jobStatus.size())

        'verify isi ddl jobStatus confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Contract  Permanent'), 
            jobStatus) == false) {

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'jobposition')

            (GlobalVariable.FlagFailed)++
        }
        
        'get companyScale ddl value from db'
        ArrayList<String> companyScale = CustomKeywords.'nap4Data.checkNAP4.checkcompanyScale'(sqlConnectionFOU)

        'get total label from ddl jobposition'
        int totalddlcompanyScale = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Big  Medium  Small'))

        'verify total ddl companyScale confins = total ddl db'
        WebUI.verifyEqual(totalddlcompanyScale - 1, companyScale.size())

        'verify isi ddl companyScale confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_--Select One-- Big  Medium  Small'), 
            companyScale) == false) {
		
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'jobposition')

            (GlobalVariable.FlagFailed)++
        }
        
        'get data array dari db'
        ArrayList<String> Ownership = CustomKeywords.'nap4Data.checkNAP4.checkOwnership'(sqlConnectionFOU)

        'get total label from ddl'
        int totalownershipjobaddr = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Select One Dinas  Family  KPR  Rented  Self - Owned'))

        int totalownershipprevjob = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_OwnerShip PreviousJobAddress'))

        int totalownershipothbiz = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Ownership OtherBusinessAddress'))

        'verify total ddl confins = total ddl db'
        WebUI.verifyEqual(totalownershipjobaddr - 1, Ownership.size())

        WebUI.verifyEqual(totalownershipprevjob - 1, Ownership.size())

        WebUI.verifyEqual(totalownershipothbiz - 1, Ownership.size())

        'verify array dari db == option list confins job addr'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
            Ownership) == false) {

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'job addr ownership')

            GlobalVariable.FlagFailed = 1
        }
        
        'verify array dari db == option list confins prev job addr'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_OwnerShip PreviousJobAddress'), 
            Ownership) == false) {

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'prev job ownership')

            GlobalVariable.FlagFailed = 1
        }
        
        'verify array dari db == option list confins othbiz'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Ownership OtherBusinessAddress'), 
            Ownership) == false) {

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, 
                GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath)(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'other biz ownership')

            GlobalVariable.FlagFailed = 1
        }
    }
}