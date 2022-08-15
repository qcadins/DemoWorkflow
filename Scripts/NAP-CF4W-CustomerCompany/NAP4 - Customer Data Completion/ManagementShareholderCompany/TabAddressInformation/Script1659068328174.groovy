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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileManagementShareholderCompany

GlobalVariable.DataFilePath = filePath

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
    GlobalVariable.NumofGuarantor, 2).length() > 1) {
    'click button add'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Add'))

    'pilih address type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 2), false, FailureHandling.OPTIONAL)

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofColm, 3).length() > 1) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_CopyAddressFrom'), 
            5, FailureHandling.OPTIONAL)) {
            'Select option dropdownlist Copy Address From '
            WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_CopyAddressFrom'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                    GlobalVariable.NumofColm, 3), false, FailureHandling.OPTIONAL)
        }
        
        'Click copy'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Copy'))

        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 22), false)
    } else {
        'input alamat'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/textarea_Address_'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 4))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_RT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 5))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_RW'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 6))

        'click button search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Zipcode_btn btn-raised btn-primary'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Zip Code_ZipCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 7))

        'input kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kecamatan_kecamatan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 8))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kelurahan_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 9))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kota_kota'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
                GlobalVariable.NumofGuarantor, 10))

        'click search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Search'), 
            FailureHandling.OPTIONAL)

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/Button_X'))

            'Click cancel'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'))

            'write to excel if failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
        }
    }
    
    'input phone1 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_Area'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 11))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_Number'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 12))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_ Ext'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 13))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Area'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 14))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Number'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 15))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Ext'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 16))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Area'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 17))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Number'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 18))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Ext'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 19))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Fax_Area'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 20))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Fax_Number'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 21))

    'pilih status rumah'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            GlobalVariable.NumofGuarantor, 22), false)

    'click save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Save'))
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation', 
        0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation', 
        0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusSuccess)
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Save  Continue'))

