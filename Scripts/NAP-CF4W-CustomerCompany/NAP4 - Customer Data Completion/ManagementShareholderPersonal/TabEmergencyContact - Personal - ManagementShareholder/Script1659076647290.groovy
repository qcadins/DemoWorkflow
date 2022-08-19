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

String filePath = userDir + GlobalVariable.DataFileManagementShareholderPersonal

GlobalVariable.DataFilePath = filePath

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 2).equalsIgnoreCase('Input Data')) {
    'input contact person name'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ContactPersonName'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 5))

    'select id type'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_ID Type'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 6), false, FailureHandling.OPTIONAL)

    'input id no'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ID No'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 7))

    if (((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 7) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 7) != 'NPWP')) && (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 7) != 'AKTA')) {
        'input id expired date'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ID Expired Date'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 8))
    }
    
    'select Customer Relationship'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_CustomerRelationship'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 9), false, FailureHandling.OPTIONAL)

    'input Email'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Email'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 10))

    'select gender'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Gender'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 11), false, FailureHandling.OPTIONAL)

    'input Birth Place'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_BirthPlace'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 12))

    'input Birth Date'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_BirthDate'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 13))

    'input mobile phone 1'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_MobilePhone'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 14))

    'input mobile phone 2'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_MobilePhone2'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 15))

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 16).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Legal  Residence  Job  Mailing'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 16), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Copy'))
    } else {
        'input address'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_TextAddress'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 17))

        'input RT'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 18))

        'input RW'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RW'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 19))

        'click button zipcode'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Zipcode'))

        'input zipcode'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ZipCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 20))

        'input Kecamatan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kecamatan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 21))

        'input Kelurahan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 22))

        'input Kota'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kota'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 23))

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
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 3))

    'input customer name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Customer Name_custNameId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 4))

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
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 9), false)

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 16).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/select_Legal  Residence  Job  Mailing'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 16), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Copy'))
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 17).length() > 1) {
        'input address'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_TextAddress'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 17))

        'input RT'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 18))

        'input RW'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_RW'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 19))

        'click button zipcode'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/button_Zipcode'))

        'input zipcode'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_ZipCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 20))

        'input Kecamatan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kecamatan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 21))

        'input Kelurahan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 22))

        'input Kota'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/EmergencyContact-Customer/input_Kota'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 23))

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
        GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.EmergencyContact', 0, 
        GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)
}

