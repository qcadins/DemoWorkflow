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

'click lookup profesi'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Profession Name_btn btn-raised btn-primary'))

'input kode profesi'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Profession Code_professionCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 28))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Search'))

'verify input error'
verifyInputError()

if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Is Well Known Company'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'check is well known company'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 30) == 'Yes') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Is Well Known Company'))
    }
} else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Is Well Known Company'), 
    GlobalVariable.TimeOut)) {
    'check is well known company'
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 30) == 'No') {
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Is Well Known Company'))
    }
}

'input nama perusahaan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Company Name'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 31))

'click button lookup industry type'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Industry Type Code_industryTypeCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 32))

'click button search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Search'))

'verify input error'
verifyInputError()

'input professional no'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Professional No'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 34), FailureHandling.CONTINUE_ON_FAILURE)

'input start working date'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Start Working Date'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 35))

'input job title'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/input_Job Title Name'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 36))

'input address'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 55))

'input RT'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 56))

'input RW'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 57))

'click search zipcode'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn btn-raised btn-primary'))

'input zipcode'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_ZipCode'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 58))

'input Kecamatan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_kecamatan'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 60))

'input kelurahan'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_Kelurahan'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 59))

'input kota'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_kota'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 61))

'click search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Search'))

'verify input error'
verifyInputError()

'input note'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_notes'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 62), FailureHandling.OPTIONAL)

'input phone1 Area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 63), FailureHandling.OPTIONAL)

'input phone1 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 64), FailureHandling.OPTIONAL)

'input phone1 Extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Ext'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 65), FailureHandling.OPTIONAL)

'input phone2 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 66), FailureHandling.OPTIONAL)

'input phone2 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 67), FailureHandling.OPTIONAL)

'input phone2 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 68), FailureHandling.OPTIONAL)

'input phone3 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 69), FailureHandling.OPTIONAL)

'input phone3 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 70), FailureHandling.OPTIONAL)

'input phone3 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Ext'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 71), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 72), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 73), FailureHandling.OPTIONAL)

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 74).length() > 0) {
    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 74), false, FailureHandling.OPTIONAL)
}

if(GlobalVariable.APPSTEP == 'CUSTOMER' && GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 126)=="Yes"){
	'click eKYC'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Check eKYC'))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 76).length() > 0) {
    'input Previous Company Name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Previous Company Name_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 76))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 77).length() > 0) {
    'input previous employment date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Previous Employment Date_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 77))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 79).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 79))

    'input RT'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT  PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 80))

    'input RW'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 81))

    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 82).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn PreviousJobAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_PreviousJobAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 82))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_PreviousJobAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 84))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_PreviousJobAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 83))

        'input kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_PreviousJobAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 85))

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
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 86))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 87))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 88))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_ext_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 89))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 90))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 91))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 92))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 93))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 94))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Ext_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 95))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 96))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number_PreviousJobAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 97))

    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 98).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_OwnerShip PreviousJobAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 98), false, FailureHandling.OPTIONAL)
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 100).length() > 0) {
    'input other business name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Name_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 100))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 101).length() > 0) {
    'input other business type'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Type_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 101))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 102).length() > 0) {
    'input other business industry type'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Business Industry Type_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 102))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 103).length() > 0) {
    'input other job position'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Other Job Position_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 103))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 104).length() > 0) {
    'input establishment date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Establishment Date_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 104))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 106).length() > 0) {
    'input address'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/textarea_Address_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 106))

    'input RT'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RT  OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 107))

    'input RW'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_RW OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 108))

    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 109).length() > 0) {
        'click search zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/button_Zipcode_btn OtherBusinessAddress'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Zip Code_OtherBusinessAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 109))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kecamatan_OtherBusinessAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 111))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kelurahan_OtherBusinessAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 110))

        'input kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Kota_OtherBusinessAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 112))

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
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 113))

    'input phone1 Area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Area_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 114))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_Number_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 115))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 1_ext_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 116))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Area_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 117))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Number_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 118))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 2_Ext_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 119))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Area_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 120))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_Number_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 121))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Phone 3_ext_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 122))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Area_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 123))

    'input fax'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/input_Fax_Number_OtherBusinessAddress'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 124))

    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 125).length() > 0) {
        'select ownership'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee/select_Ownership OtherBusinessAddress'), 
            GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 125), false, FailureHandling.OPTIONAL)
    }
}

'click save and continue button'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Save  Continue'))

if(GlobalVariable.APPSTEP == 'CUSTOMER' && GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 126)=="No"){
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME/label_Profession Name'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataSME/button_Back'))
} else {
    if (GlobalVariable.FlagWarning > 0) {
        'write to excel if warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.ColmNAP4 - 
            1, GlobalVariable.StatusWarning)
    }
}

def verifyInputError(){
	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/a_Select'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_X'))
	
		'click button back'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataProfessional/button_Back'))
		
		getDataFile()
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.FindDataFile(GlobalVariable.ColmNAP4, 2) + ';' +GlobalVariable.StatusReasonLookup)
		
		GlobalVariable.FlagFailed = 1
	}
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
