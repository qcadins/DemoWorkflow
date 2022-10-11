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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorCompanyCompany

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany')

'input name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Name_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 12))

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Job Title_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 13))

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 14).length() > 2) {
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/select_Id Type'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 14), false)

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Id No_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 15))
}

if (((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 14) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 14) != 'AKTA')) && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 14) != 'NPWP')) {
    'input idexpired date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Id Expired Date'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 16))
}

'input mobile phone 1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Mobile Phone 1_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 17))

'input email1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Email 1_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 18))

'select gender'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/select_Select One Female  Male'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 19), false)

'select job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/select_Select Job Position'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 20).toUpperCase(), false)

'input birth place'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Birth Place_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 21))

'input birth date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Birth Date_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 22))

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 23).length() > 2) {
    'select relation'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/select_Relation'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 23), false)
}

'input mobile 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Mobile Phone 2'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 24))

'input email 2'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Email 2_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 25))

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/textarea_Address_'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 27))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_RT'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 28))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_RW'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 29))

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofFamily, 30).length() > 2) {
    'click button zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/button_Zipcode_btn btn-raised btn-primary'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Zip Code_ZipCode'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 30))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Kecamatan_kecamatan'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 31))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Kelurahan_Kelurahan'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 32))

    'input kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Kota_kota'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 33))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/Button_X'))
    }
}

'input phone 1 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 1_Area'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 34))

'input phone 1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 1_Number'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 35))

'input phone 1 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 1_Ext'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 36))

'input phone 2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 2_Area'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 37))

'input phone 2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 2_Number'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 38))

'input phone 2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Phone 2_Ext'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 39))

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Fax_Area'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 40))

'input fax number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/input_Fax_Number'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 41))

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/button_Save  Continue'))

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 4)) == 0) {
    'Check alert'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, '3.ContactInformation')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofFamily, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Add'), 
        GlobalVariable.NumofFamily, '3.ContactInformation')

    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/ContactInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofFamily, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofFamily, '3.ContactInformation')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation - Company/label_Name'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonalCompany=="Yes") {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofFamily

    'call test case verify contact info store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabContactInfoVerifStoreData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

