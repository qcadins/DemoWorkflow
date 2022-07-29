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

'Input Promo pada Interest Income'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InterestIncome'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
        GlobalVariable.NumofColm, 2), FailureHandling.OPTIONAL)

'Input Promo pada Insurance Income'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InsuranceIncome'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
        GlobalVariable.NumofColm, 3), FailureHandling.OPTIONAL)

'Input Promo pada Life Insurance Income'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_LifeInsuranceIncome'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
        GlobalVariable.NumofColm, 4), FailureHandling.OPTIONAL)

'Input Promo pada Admin Fee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_AdminFee'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
        GlobalVariable.NumofColm, 5), FailureHandling.OPTIONAL)

'Input Promo pada Provision Fee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_ProvisionFee'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
        GlobalVariable.NumofColm, 6), FailureHandling.OPTIONAL)

'Menyimpan nilai promo interestincome'
String textInterestIncome = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InterestIncome'), 
    'value').replace(',', '')

BigDecimal interestIncome = Double.parseDouble(textInterestIncome)

'Menyimpan nilai promo insuranceincome'
String textInsuranceIncome = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InsuranceIncome'), 
    'value').replace(',', '')

BigDecimal insuranceIncome = Double.parseDouble(textInsuranceIncome)

'Menyimpan nilai promo lifeinsuranceincome'
String textLifeInsuranceIncome = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_LifeInsuranceIncome'), 
    'value').replace(',', '')

BigDecimal lifeInsuranceIncome = Double.parseDouble(textLifeInsuranceIncome)

'Menyimpan nilai promo admin fee'
String textAdminFee = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_AdminFee'), 
    'value').replace(',', '')

BigDecimal adminFee = Double.parseDouble(textAdminFee)

'Menyimpan nilai promo provision feee'
String textProvisionFee = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_ProvisionFee'), 
    'value').replace(',', '')

BigDecimal provisionFee = Double.parseDouble(textProvisionFee)

'Perhitungan Total Reserved Fund Amount'
BigDecimal totalAmt = (((interestIncome + insuranceIncome) + lifeInsuranceIncome) + adminFee) + provisionFee

'Klik Button Calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Calculate'))

WebUI.delay(5)

'Menyimpan nilai Total Reserved Fund Amount dari Web CONFINS'
String totalReservedFundAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_TotalReservedFundAmt')).replace(
    ',', '').replace('.00', '')

'Verifikasi hasil perhitungan total reserved fund amount sesuai dnegan nilai total reserved fund amount dari web'
WebUI.verifyMatch(totalReservedFundAmt, totalAmt.toString(), false)

WebUI.delay(5)

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Save'))

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'), 
    5, FailureHandling.OPTIONAL)) {
    'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))

    'Write to Excel FAILED'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
    }
} else {
    'Write to Excel SUCCESS'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

