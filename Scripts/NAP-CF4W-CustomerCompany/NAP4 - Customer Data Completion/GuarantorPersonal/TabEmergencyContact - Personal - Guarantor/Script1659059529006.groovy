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

String filePath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

GlobalVariable.DataFilePath = filePath

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 12).equalsIgnoreCase('Input Data')) {
    'input contact person name'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ContactPersonName'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 15))

    'select id type'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_ID Type'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 16), false, FailureHandling.OPTIONAL)

    'input id no'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ID No'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 17))

    if (((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 16) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 16) != 'NPWP')) && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 16) != 'AKTA')) {
        'input id expired date'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ID Expired Date'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 18))
    }
    
    'select Customer Relationship'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_CustomerRelationship'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 19), false, FailureHandling.OPTIONAL)

    'input Email'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Email'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 20))

    'select gender'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Gender'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 21), false, FailureHandling.OPTIONAL)

    'input Birth Place'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_BirthPlace'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 22))

    'input Birth Date'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_BirthDate'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 23))

    'input mobile phone 1'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_MobilePhone'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 24))

    'input mobile phone 2'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_MobilePhone2'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 25))

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 26).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Legal  Residence  Job  Mailing'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 26), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Copy'))
    } else {
        'input address'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_TextAddress'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 27))

        'input RT'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 28))

        'input RW'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RW'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 29))

        'click button zipcode'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Zipcode'))

        'input zipcode'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ZipCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 30))

        'input Kecamatan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kecamatan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 31))

        'input Kelurahan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 32))

        'input Kota'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kota'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 33))

        'click button search'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Search'))

        'click button select'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/a_Select'))
    }
} else {
    'click lookup button contact person'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Contact Person Name_btn btn-raised btn-primary'))

    'input customer no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Customer No_custNoId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 13))

    'input customer name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Customer Name_custNameId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 14))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
    }
    
    'select customer relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_CustomerRelationship'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 19), false)

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 26).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Legal  Residence  Job  Mailing'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 26), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Copy'))
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 27).length() > 1) {
        'input address'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_TextAddress'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 27))

        'input RT'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 28))

        'input RW'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RW'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 29))

        'click button zipcode'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Zipcode'))

        'input zipcode'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ZipCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 30))

        'input Kecamatan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kecamatan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 31))

        'input Kelurahan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 32))

        'input Kota'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kota'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact - Company - GuarantorPersonal').getValue(
                GlobalVariable.NumofGuarantor, 33))

        'click button search'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Search'))

        'click button select'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/a_Select'))
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/label_Contact Person Name'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusSuccess)
}

