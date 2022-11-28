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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany')
'input name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Name_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 12))

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Job Title_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 13))

if (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 14).length() > 2) {
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/select_Id Type'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 14), false)

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Id No_'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 15))
}

if (((GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 14) != 'E-KTP') && (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 14) != 'AKTA')) && (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 14) != 'NPWP')) {
    'input idexpired date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Id Expired Date'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 16))
}

'input mobile phone 1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Mobile Phone 1_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 17))

'input email1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Email 1_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 18))

'select gender'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/select_Select One Female  Male'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 19), false)

'select job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/select_Select Job Position'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 20).toUpperCase(), false)

'input birth place'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Birth Place_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 21))

'input birth date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Birth Date_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 22))

if (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 23).length() > 2) {
    'select relation'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/select_Relation'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 23), false)
}

'input mobile 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Mobile Phone 2'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 24))

'input email 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Email 2_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 25))

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/textarea_Address_'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 27))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_RT'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 28))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_RW'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 29))

if (GlobalVariable.FindDataFile.getValue(
    GlobalVariable.NumofMS, 30).length() > 2) {
    'click button zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/button_Zipcode_btn btn-raised btn-primary'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Zip Code_ZipCode'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 30))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Kecamatan_kecamatan'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 31))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Kelurahan_Kelurahan'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 32))

    'input kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Kota_kota'), 
        GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 33))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/Button_X'))
    }
}

'input phone 1 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 1_Area'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 34))

'input phone 1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 1_Number'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 35))

'input phone 1 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 1_Ext'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 36))

'input phone 2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 2_Area'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 37))

'input phone 2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 2_Number'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 38))

'input phone 2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Phone 2_Ext'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 39))

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Fax_Area'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 40))

'input fax number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/input_Fax_Number'), 
    GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 41))

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/button_Save  Continue'))

if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(
        GlobalVariable.NumofMS, 4)) == 0 && GlobalVariable.FlagFailed == 0) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS, '3.ContactInformation')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.NumofMS, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData/button_Add'), 
        GlobalVariable.NumofMS, '3.ContactInformation')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(
            GlobalVariable.NumofMS, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofMS, '3.ContactInformation')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation/label_Name'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofMS

    'call test case verify contact info store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabContactInfoVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

