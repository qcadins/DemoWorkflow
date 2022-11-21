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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company - Guarantor')

GlobalVariable.FlagFailed = 0

'input name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Name_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 12))

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Job Title_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 13))

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14).length() > 2) {
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/select_Id Type'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14), false)

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Id No_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 15))
}

if (((GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14) != 'E-KTP') && (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofColm, 14) != 'AKTA')) && (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14) != 
'NPWP')) {
    'input idexpired date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Id Expired Date'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 16))
}

'input mobile phone 1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Mobile Phone 1_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 17))

'input email1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Email 1_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 18))

'select gender'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/select_Select One Female  Male'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 19), false)

'select job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/select_Select Job Position'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 20), false)

'input birth place'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Birth Place_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 21))

'input birth date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Birth Date_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 22))

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 23).length() > 2) {
    'select relation'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/select_Relation'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 23), false)
}

'input mobile 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Mobile Phone 2'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 24))

'input email 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Email 2_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 25))

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/textarea_Address_'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 27))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_RT'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 28))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_RW'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 29))

'click button zipcode'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/button_Zipcode_btn btn-raised btn-primary'))

'input Zipcode'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Zip Code_ZipCode'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 30))

'input kecamatan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Kecamatan_kecamatan'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 32))

'input kelurahan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Kelurahan_Kelurahan'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 31))

'input kota'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Kota_kota'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 33))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))
}

'input phone 1 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 1_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 34))

'input phone 1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 1_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 35))

'input phone 1 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 1_Ext'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 36))

'input phone 2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 2_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 37))

'input phone 2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 2_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 38))

'input phone 2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Phone 2_Ext'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 39))

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Fax_Area'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 40))

'input fax number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/input_Fax_Number'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 41))

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantor, 
        '3.ContactInformation')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/FinancialData - Company/button_Add'), 
        GlobalVariable.NumofGuarantor, '3.ContactInformation')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantor, 
            '3.ContactInformation')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/ContactInformation - Company/label_Name'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofGuarantor

    'call test case verify store data contact info'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabContactInfoVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

