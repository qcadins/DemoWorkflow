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

GlobalVariable.FlagFailed = 0

int flagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/OtherAttribute - Company - GuarantorCompany')

ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

if (GlobalVariable.RoleCompany == 'Testing') {
	'connect DB FOU'
	Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connectFOU'()

    'get count total attribute list dari db'
    ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#AttributeList > div Label'))

    'verify total data attribute list == total data attribute list db'
    checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countAttributeListCompany'(sqlconnectionFOU), 
            variable.size()))

    'Click Lookup Debtor Group'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Debtor Group_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorGroup'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup debtor business scale'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Debtor Business Scale_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorBusinessScale'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup counterpart category'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Counterpart Category_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countCounterpartCategory'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup sustainable financial business'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Sustainable Financial Business'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countSustainableBusiness'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup debtor group SLIK'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_DEBTOR GROUP SLIK_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countDebtorGroupSLIK'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup Rating Institute'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_RATING INSTITUTE_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countRatingInstitute'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup affiliate with multifinance'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_AFFILIATE WITH MULTIFINANCE SLIK_btn'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countAffiliateMultifinanceSLIK'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup CSP/USL'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_CSPUSL SOURCE AML_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countCSPUSLSourceAML'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup Payment Type'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_PAYMENT TYPE AML_btn btn-raised btn-primary'))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countPaymentType'(sqlconnectionFOU), Integer.parseInt(
                totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))

    'click lookup Business Source AML'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Search'))

    'get text total data dari lookup confins'
    totaldata = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/LabelTotalData')).replace(
        'TOTAL DATA : ', '')

    'verify total data lookup confins == total data lookup db'
    arrayMatch.add(WebUI.verifyEqual(CustomKeywords.'dbconnection.checkNAP4db.countBusinessSourceAML'(sqlconnectionFOU), 
            Integer.parseInt(totaldata)))

    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/Button_X'))
}

'Click Lookup Debtor Group'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Debtor Group_btn btn-raised btn-primary'))

'input code Debtor'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Debtor Group Code_LbppmsDebtGrpCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 12))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'click lookup debtor business scale'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Debtor Business Scale_btn btn-raised btn-primary'))

'input code debtor business scale'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Debtor Group Code_LbppmsBizSclCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 14))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'click lookup counterpart category'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Counterpart Category_btn btn-raised btn-primary'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_LBPP Code_LbppCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 16))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'click lookup sustainable financial business'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Sustainable Financial Business'))

'input LBPP Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_LBPP Code_LbppCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 18))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'select yes/no'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/select_NO YES GoPublic'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 21), false)

'click lookup debtor group SLIK'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_DEBTOR GROUP SLIK_btn btn-raised btn-primary'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 22))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'select yes/no'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/select_NO  YES VIOLATE BMPK'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 24), false)

'select yes/no'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/select_NO  YES EXCCED BMPK'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 25), false)

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 26).length() > 1) {
    'input rating debitor'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_RATING DEBITOR'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 26))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 28).length() > 1) {
    'click lookup Rating Institute'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_RATING INSTITUTE_btn btn-raised btn-primary'))

    'input Code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 27))

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

        flagWarning++
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 29).length() > 1) {
    'input Rating date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_RATING DATE'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 29))
}

'click lookup affiliate with multifinance'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_AFFILIATE WITH MULTIFINANCE SLIK_btn'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 30))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'select yes/no'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/select_-Select One- NO  YES'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 32), false)

'click lookup CSP/USL'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_CSPUSL SOURCE AML_btn btn-raised btn-primary'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 33))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'click lookup Payment Type'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_PAYMENT TYPE AML_btn btn-raised btn-primary'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 35))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'click lookup Business Source AML'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_BUSINESS SOURCE AML_btn btn-raised btn-primary'))

'input Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_Code_MasterCodeId'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 37))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Search'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'), 
    10, FailureHandling.OPTIONAL)) {
    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/Button_X'))

    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

    'write to excel reason lookup'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, 
        GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusReasonLookup)

    'flagfailed +1 karena gagal melakukan lookup'
    (GlobalVariable.FlagFailed)++
}

'input tanggal business period'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/input_BUSINESS PERIOD AML'), 
    GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 39))

GlobalVariable.Custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Save'))

if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantor, 
        '7.OtherAttribute')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.NumofGuarantor, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
        GlobalVariable.NumofGuarantor, '7.OtherAttribute')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
            GlobalVariable.NumofGuarantor, '7.OtherAttribute')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/label_Debtor Group'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
} else {
    if (flagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.OtherAttribute', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusWarning)
    }
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofGuarantor

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabOtherAttributeVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '7.OtherAttribute', 0, GlobalVariable.NumofGuarantor - 
            1, GlobalVariable.StatusFailed)

        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '7.OtherAttribute', 1, GlobalVariable.NumofGuarantor - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

