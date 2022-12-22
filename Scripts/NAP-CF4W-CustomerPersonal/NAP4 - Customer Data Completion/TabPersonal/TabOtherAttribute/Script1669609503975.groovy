import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagWarning = 0

GlobalVariable.FlagFailed = 0

if(GlobalVariable.APPSTEP == 'CUSTOMER'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerPersonal)
	
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute')
}else if(GlobalVariable.APPSTEP == 'FAMILY'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)
	
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/OtherAttribute')
}else if(GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)
	
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/OtherAttribute')
}

ArrayList<Boolean> arrayMatch = new ArrayList<>()

if (GlobalVariable.Role == 'Testing') {
    
	'connect DB FOU'
	Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

    'get count total attribute list dari db'
    ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#AttributeList > div Label'))

    'verify total data attribute list == total data attribute list db'
    checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countAttributeListPersonal'(sqlconnectionFOU), 
            variable.size()))

    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Debtor Group_'))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countDebtorGroup'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Debtor Business Scale'))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countDebtorBusinessScale'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Counterpart Category_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countCounterpartCategory'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Sustainable Financial Business'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countSustainableBusiness'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_DEBTOR GROUP SLIK'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countDebtorGroupSLIK'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countAffiliateMultifinanceSLIK'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup department AML'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_DEPARTMENT AML'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countDepartmentAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_CSPUSL SOURCE AML'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countCSPUSLSourceAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click payment type lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_PAYMENT TYPE AML'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countPaymentType'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click authority AML lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Authority AML'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countAuthorityAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countBusinessSourceAML'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))

    'click building lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_BUILDING_'))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'nap4Data.checkNAP4.countBuildingOwnership'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/Button_X'))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.OtherAttribute', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDataLookup)

    GlobalVariable.FlagFailed = 1
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 13).length() > 1) {
    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Debtor Group_'))

    'input code Debtor'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Debtor Group Code_LbppmsDebtGrpCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 12))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 15).length() > 1) {
    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Debtor Business Scale'))

    'input code debtor business scale'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Debtor Group Code_LbppmsBizSclCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 14))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 17).length() > 1) {
    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Counterpart Category_btn btn-raised btn-primary'))

    'input Counterpart Code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_LBPP Code_LbppCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16))

    'click button search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 19).length() > 1) {
    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Sustainable Financial Business'))

    'input LBPP Code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_LBPP Code_LbppCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 18))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 21).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/select_NO  YES Split Treasure'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 21), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 23).length() > 1) {
    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_DEBTOR GROUP SLIK'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 22))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 24).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/select_NO  YES Violate BMPK'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 24), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 25).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/select_NOYES Exceed BMPK'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 25), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 27).length() > 1) {
    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 26))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 29).length() > 1) {
    'click lookup department AML'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_DEPARTMENT AML'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 28))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 31).length() > 1) {
    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_CSPUSL SOURCE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 30))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
   verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 33).length() > 1) {
    'click payment type lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_PAYMENT TYPE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 32))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 34).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/select_-Select One- NO  YES KYC Report'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 34), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 36).length() > 1) {
    'click authority AML lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Authority AML'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 35))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        GlobalVariable.FlagWarning++
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 37).length() > 1) {
    'input attend date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_ATTEND_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 37))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 39).length() > 1) {
    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 38))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

	'verify input error'
	verifyInputError()
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 40).length() > 1) {
    'input Business Period AML'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_BUSINESS PERIOD AML'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 40))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 42).length()) {
    'click building lookup'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_BUILDING_'))

    'input code'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 41))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        GlobalVariable.FlagWarning++
    }
}

GlobalVariable.Custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

'click button save'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/button_saveOtherAttribute'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, 
        '7.OtherAttribute')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/CustomerType'), 
        GlobalVariable.ColmNAP4, '7.OtherAttribute')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.ColmNAP4, '7.OtherAttribute')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/label_Debtor Group'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
    if (GlobalVariable.FlagWarning > 0) {
		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.ColmNAP4 - 1, GlobalVariable.StatusWarning)
    }
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

    'call test case verify store data other attr'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabOtherAttributeVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.OtherAttribute', GlobalVariable.ColmNAP4, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
        GlobalVariable.FlagFailed = 1
    }
}

def verifyInputError(){
	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

		'click button back'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.OtherAttribute', GlobalVariable.ColmNAP4, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

		GlobalVariable.FlagFailed = 1
	}
}