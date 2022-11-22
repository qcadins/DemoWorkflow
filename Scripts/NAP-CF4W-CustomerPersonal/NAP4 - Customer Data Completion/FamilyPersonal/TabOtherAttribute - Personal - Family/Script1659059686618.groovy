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

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/OtherAttribute - Personal - Family')

ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

if (GlobalVariable.Role == 'Testing') {
	'connect DB FOU'
	Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

    'get count total attribute list dari db'
    ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#AttributeList > div Label'))

    'verify total data attribute list == total data attribute list db'
    checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countAttributeListPersonal'(sqlconnectionFOU), 
            variable.size()))

    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countDebtorGroup'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Debtor Business Scale'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countDebtorBusinessScale'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Counterpart Category_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countCounterpartCategory'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Sustainable Financial Business'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countSustainableBusiness'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_DEBTOR GROUP SLIK'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countDebtorGroupSLIK'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countAffiliateMultifinanceSLIK'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup department AML'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_DEPARTMENT AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countDepartmentAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_CSPUSL SOURCE AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countCSPUSLSourceAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click payment type lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_PAYMENT TYPE AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countPaymentType'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click authority AML lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Authority AML'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countAuthorityAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countBusinessSourceAML'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))

    'click building lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_BUILDING_'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbConnection.checkNAP4db.countBuildingOwnership'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/Button_X'))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofFamily - 1, GlobalVariable.ReasonFailedDataLookup)

    GlobalVariable.FlagFailed = 1
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 13).length() > 1) {
    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'))

    'input code Debtor'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Debtor Group Code_LbppmsDebtGrpCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 12))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 15).length() > 1) {
    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Debtor Business Scale'))

    'input code debtor business scale'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Debtor Group Code_LbppmsBizSclCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 14))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 17).length() > 1) {
    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Counterpart Category_btn btn-raised btn-primary'))

    'input Counterpart Code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_LBPP Code_LbppCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 16))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 19).length() > 1) {
    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Sustainable Financial Business'))

    'input LBPP Code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_LBPP Code_LbppCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 18))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 21).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/select_NO  YES Split Treasure'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 21), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 23).length() > 1) {
    'click button lookup Debtor Group SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_DEBTOR GROUP SLIK'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 22))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 24).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/select_NO  YES Violate BMPK'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 24), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 25).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/select_NOYES Exceed BMPK'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 25), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 27).length() > 1) {
    'click lookup Affiliate with multifinance SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_AFFILIATE WITH MULTIFINANCE SLIK_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 26))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 29).length() > 1) {
    'click lookup department AML'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_DEPARTMENT AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 28))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 31).length() > 1) {
    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_CSPUSL SOURCE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 30))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 33).length() > 1) {
    'click payment type lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_PAYMENT TYPE AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 32))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 34).length() > 1) {
    'pilih yes/no'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/select_-Select One- NO  YES KYC Report'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 34), false)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 36).length() > 1) {
    'click authority AML lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Authority AML'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 35))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        flagWarning++
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 37).length() > 1) {
    'input attend date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_ATTEND_'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 37))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 39).length() > 1) {
    'click business source AML Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 38))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'write to excel if failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)

        'write to excel reason lookup'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

        GlobalVariable.FlagFailed = 1
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 40).length() > 1) {
    'input Business Period AML'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_BUSINESS PERIOD AML'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 40))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 42).length()) {
    'click building lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_BUILDING_'))

    'input code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 41))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

        flagWarning++
    }
}

GlobalVariable.Custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/button_saveOtherAttribute'))

Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofFamily, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, 
        '7.OtherAttribute')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
        GlobalVariable.NumofFamily, '7.OtherAttribute')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofFamily, '7.OtherAttribute')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/OtherAttribute - Personal/label_Debtor Group'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
    }
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofFamily

    'call test case verify store data other attr'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabOtherAttributeVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, GlobalVariable.NumofFamily - 
            1, GlobalVariable.StatusFailed)

        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, GlobalVariable.NumofFamily - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

