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
import groovy.sql.Sql as Sql

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

'click menu customer main data'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

'click add button'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Add'))

'Ambil nilai office login dari confins'
String[] officeLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OfficeLocLogin')).replace(
    ',', ';').split(';')

'click button lookup product offering'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))


'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))


if(GlobalVariable.Role=="Testing"){
	'Koneksi database'
	String servername = findTestData('Login/Login').getValue(1, 8)
	
	String instancename = findTestData('Login/Login').getValue(2, 8)
	
	String username = findTestData('Login/Login').getValue(3, 8)
	
	String password = findTestData('Login/Login').getValue(4, 8)
	
	String databaseLOS = findTestData('Login/Login').getValue(5, 9)
	
	String driverclassname = findTestData('Login/Login').getValue(6, 8)
	
	String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS
	
	Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)
	
	String office = officeLogin[0]
	
	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))
	
	'Cek total data product offering pada db'
	Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlConnectionLOS,office)
	
	'Ambil nilai total data product offering pada lookup confins'
	String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
		' ', '').replace(':', ';').split(';')
	
	'Parsing nilai total data PO confins ke integer(angka)'
	Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])
	
	'Verif total data product offering confins sesuai dengan db'
	WebUI.verifyEqual(totalDataPO, countPO)
}


'input product offering code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Product Offering Code_prodOfferingCode'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        12))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 10, 
    FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

    'Click Button Cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
    }
}

'click button next'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Next'))

'Get Appno'
String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/Applicant No'))

'Write to excel Appno'
CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, 
    GlobalVariable.NumofColm - 1, appNo)

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
        'CUSTOMER', false, FailureHandling.OPTIONAL)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    14) == 'Input Data') {
    'click radio button company'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/span_ Company'))

    'input customer name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            19))

    'input tax id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            20))

    'select company type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            21), false)

    'select customer model'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            22), false)

    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/textarea_Address'), findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 24))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RT'), findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 25))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RW'), findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 26))

    'click lookup zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Zipcode_btn btn-raised btn-primary'))

    'input zip code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Zip Code_ZipCode'), findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 27))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kecamatan_kecamatan'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            28))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kelurahan_Kelurahan'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            29))

    'input kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kota_kota'), findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 30))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Back'))

        'Write To Excel GlobalVariable.StatusFailed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

        'Pengecekan jika new consumer finance belum diexpand'
        if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
            'Klik new consumer finance'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
        }
    }
    
    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            31), false)

    'click button save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Save'))

    'verify fail'
    if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
        'CUSTOMER', false, FailureHandling.OPTIONAL)) {
        'click menu Customer main'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

        'write to excel \'GlobalVariable.StatusFailed\''
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

        'Pengecekan jika new consumer finance belum diexpand'
        if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
            'Klik new consumer finance'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
        }
    } else {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (flagWarning > 0) {
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
        }
        
        (GlobalVariable.countNumofCustomer)++
    }
} else {
    'click radio button company'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/span_ Company'))

    'click button lookpup Customer'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Customer Legal Name_btn btn-raised btn-primary'))

    'input customer no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer No_custNoId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            16))

    'input customer name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Name_custNameId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            17))

    'input tax id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_NPWP_NpwpId'), findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 18))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Back'))

        'Write To Excel GlobalVariable.StatusFailed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

        'Pengecekan jika new consumer finance belum diexpand'
        if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
            'Klik new consumer finance'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        21).length() > 0) {
        'select company type'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                21), false)
    }
    
    if (GlobalVariable.RoleCompany == 'Testing') {
        'call test case verif customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabCustomerDataVerif'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    'click button save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Save'))

    'verify fail'
    if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
        'CUSTOMER', false, FailureHandling.OPTIONAL)) {
        'click menu Customer main'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

        'write to excel \'GlobalVariable.StatusFailed\''
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

        'Pengecekan jika new consumer finance belum diexpand'
        if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
            'Klik new consumer finance'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
        }
    } else {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (flagWarning > 0) {
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
        }
        
        (GlobalVariable.countNumofCustomer)++
    }
}

