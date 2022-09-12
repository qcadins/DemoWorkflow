import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

GlobalVariable.DataFilePath = filePath

if (GlobalVariable.RoleCompany == 'Testing') {
    'Koneksi database'
    String servername = findTestData('Login/Login').getValue(1, 7)

    String instancename = findTestData('Login/Login').getValue(2, 7)

    String username = findTestData('Login/Login').getValue(3, 7)

    String password = findTestData('Login/Login').getValue(4, 7)

    String databaseFOU = findTestData('Login/Login').getValue(5, 7)

    String driverclassname = findTestData('Login/Login').getValue(6, 7)

    String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

    Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

	'get count total attribute list dari db'
	ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#AttributeList > div Label'))

	'verify total data attribute list == total data attribute list db'
	WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countAttributeListPersonal'(sqlConnectionFOU), variable.size())
	
    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorGroup'(sqlConnectionFOU), Integer.parseInt(totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Business Scale'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorBusinessScale'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Counterpart Category_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countCounterpartCategory'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Sustainable Financial Business'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countSustainableBusiness'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_DEBTOR GROUP SLIK'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorGroupSLIK'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countAffiliateMultifinanceSLIK'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup department AML'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_DEPARTMENT AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDepartmentAML'(sqlConnectionFOU), Integer.parseInt(totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_CSPUSL SOURCE AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countCSPUSLSourceAML'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click payment type lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_PAYMENT TYPE AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countPaymentType'(sqlConnectionFOU), Integer.parseInt(totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click authority AML lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Authority AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countAuthorityAML'(sqlConnectionFOU), Integer.parseInt(totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countBusinessSourceAML'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click building lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_BUILDING_'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countBuildingOwnership'(sqlConnectionFOU), Integer.parseInt(
            totaldata))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 13).length() > 1) {
    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'))

    'input code Debtor'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Debtor Group Code_LbppmsDebtGrpCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 12))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 15).length() > 1) {
    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Business Scale'))

    'input code debtor business scale'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Debtor Group Code_LbppmsBizSclCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 14))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 17).length() > 1) {
    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Counterpart Category_btn btn-raised btn-primary'))

    'input Counterpart Code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_LBPP Code_LbppCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 16))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 19).length() > 1) {
    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Sustainable Financial Business'))

    'input LBPP Code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_LBPP Code_LbppCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 18))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 21).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/select_NO  YES Split Treasure'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 21), false)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 23).length() > 1) {
    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_DEBTOR GROUP SLIK'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 22))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 24).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/select_NO  YES Violate BMPK'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 24), false)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 25).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/select_NOYES Exceed BMPK'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 25), false)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 27).length() > 1) {
    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 26))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 29).length() > 1) {
    'click lookup department AML'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_DEPARTMENT AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 28))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 31).length() > 1) {
    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_CSPUSL SOURCE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 30))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 33).length() > 1) {
    'click payment type lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_PAYMENT TYPE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 32))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 34).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/select_-Select One- NO  YES KYC Report'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 34), false)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 36).length() > 1) {
    'click authority AML lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Authority AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 35))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        flagWarning++
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 37).length() > 1) {
    'input attend date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_ATTEND_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 37))
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 30).length() > 1) {
    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 38))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 40).length() > 1) {
    'input Business Period AML'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_BUSINESS PERIOD AML'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 40))
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 42).length()) {
    'click building lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_BUILDING_'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 41))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/Button_X'))

        flagWarning++
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_saveOtherAttribute'))

'Check save Process write to excel'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/OtherAttribute - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
    GlobalVariable.NumofGuarantor, '7.OtherAttribute')

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/label_Debtor Group'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusWarning)
    }
}

