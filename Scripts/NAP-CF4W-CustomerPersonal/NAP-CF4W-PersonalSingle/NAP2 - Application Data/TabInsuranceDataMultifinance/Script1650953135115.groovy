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
import java.text.NumberFormat as NumberFormat
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'Select option dropdownlist Asset Region'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 13), false)

'Input Coverage Amount'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 14))

coverPeriod = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 15)

'Select option dropdownlist cover period'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_CoverPeriodMF'), 
    coverPeriod, false)

'Verifikasi Jika cover period over tenor atau partial tenor'
if ((coverPeriod == 'Over Tenor') || (coverPeriod == 'Partial Tenor')) {
    'Input Insurance Length'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Insurance Length MF'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 19))
}

'Select option dropdownlist payment type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_Payment Type MF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 16), false)

'Select option insco branch name'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 17), false)

insuranceNotesCompany = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/textarea_Insurance Notes MF')

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 3) == 'Customer - Multifinance') {
    'Modify properti untuk insurance notes bagian company insured by customer multifinance'
    insuranceNotesCompany = WebUI.modifyObjectProperty(insuranceNotesCompany, 'xpath', 'equals', '/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-nap-detail-form/div/div/div[2]/div/div[1]/div[2]/div[4]/app-insurance-data/app-uc-insurance/div/div/div/div/div/app-uc-insurance-detail/div/form/div[1]/div/div[3]/div[5]/div/textarea', 
        true)
}

'Input insurance notes'
WebUI.setText(insuranceNotesCompany, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 18))

'Klik Generate Insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Generate Insurance'))

'Verify muncul tidaknya insurance fee setelah klik generate insurance'
if (WebUI.verifyTextNotPresent('INSURANCE FEE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

    'write to excel failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 0, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
}

'Verifikasi/memastikan isfeeusedefault pada excel'
if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 21) == 'NO') {
    'Input Admin Fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 22), FailureHandling.OPTIONAL)

    'Input Customer Stampduty Fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 23), FailureHandling.OPTIONAL)
}

'Select option dropdownlist main coverage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_MainCoverage'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 25), false)

'Ambil inputan Additional Coverage dari Excel'
String flood = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 27)

String tpl = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 28)

String actOfGod = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 29)

String SRCC = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 30)

String tanggungJawab = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 31)

String kecelakaanDiri = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 32)

String teroris = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 33)

'Verifikasi input Additional Coverage'
if (flood == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang flood'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang flood'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'))
    }
}

if (tpl == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang tpl'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang tpl'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'))
    }
}

if (actOfGod == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang act of god'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang act of god'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'))
    }
}

if (SRCC == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang SRCC'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang SRCC'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'))
    }
}

if (tanggungJawab == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang Tanggung Jawab Hukum Terhadap Penumpang'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang Tanggung Jawab Hukum Terhadap Penumpang'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'))
    }
}

if (kecelakaanDiri == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang Kecelakaan Diri untuk Penumpang'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang kecelakaan diri untuk penumpang'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'))
    }
}

if (teroris == 'YES') {
    'Jika kondisi awal belum tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Centang terrorist'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'))
    }
} else {
    'Jika kondisi awal sudah tercentang'
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'), 
        1, FailureHandling.OPTIONAL)) {
        'Uncentang terrorist'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'))
    }
}

'Klik apply to all'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Apply To All'))

not_run: WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/Checkbox Capitalize'))

'Klik calculate insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))

'Inisialisasi Driver'
WebDriver driver = DriverFactory.getWebDriver()

'Inisialisasi Variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div.ng-untouched.ng-pristine.ng-valid > table tbody'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

'Looping data insurance'
for (int i = 1; i <= count; i++) {
    'Inisialisasi Format untuk mendapatkan nilai desimal dari nilai persen'
    NumberFormat decimalFormat = NumberFormat.getPercentInstance()

    rateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Rate'), 
        'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[8]/div/input', true)

    'Ambil nilai dari rate'
    String textRate = WebUI.getAttribute(rateObject, 'value').replaceAll('\\s', '')

    'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi'
    Float rate = decimalFormat.parse(textRate).floatValue()

    sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 
        'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[5]/div/input', true)

    'Ambil nilai dari persentase sum insured'
    String textSumInsuredPercent = WebUI.getAttribute(sumInsuredPercentObject, 'value').replaceAll('\\s', '')

    'Mengubah nilai persen menjadi desimal'
    Float sumInsuredPercent = decimalFormat.parse(textSumInsuredPercent).floatValue()

    'Ambil nilai coverage amount'
    String textAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'), 
        'value').replace(',', '')

    BigDecimal Amount = Long.parseLong(textAmount)

    numberOfMonthObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_numberOfMonth'), 
        'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[4]', true)

    'Ambil nilai number of month'
    String textNumberOfMonth = WebUI.getText(numberOfMonthObject)

    BigDecimal numberOfMonth = Integer.parseInt(textNumberOfMonth)

    mainPremiObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_MainPremiumAmt'), 
        'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[9]', true)

    'Ambil nilai Main Premium Amount'
    String textMainPremi = WebUI.getText(mainPremiObject).replace(',', '').replace('.00', '')

    'Perhitungan Main Premium Amount'
    BigDecimal Result = Math.round((((Amount * sumInsuredPercent) * rate) * (numberOfMonth / 12)) / 100) * 100

    'Verifikasi/memastikan nilai main premium amount pada confins sama dengan perhitungan'
    WebUI.verifyMatch(textMainPremi, Result.toString(), false)
}

'Input diskon'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 35))
	
		

GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))


GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'), 
    'value', FailureHandling.OPTIONAL)

