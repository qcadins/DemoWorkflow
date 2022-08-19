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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import groovy.sql.Sql as Sql
import org.codehaus.groovy.ast.stmt.ContinueStatement as ContinueStatement

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Mengambil nilai summary total commission amount dari confins'
String textTotalCommAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommisionAmt')).replace(
    ',', '')

'Mengambil nilai summary total commission after tax amount dari confins'
String textTotalCommAmtAftTax = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmt')).replace(
    ',', '')

'Mengambil nilai summary total disburse amount dari confins'
String textTotalDisburseAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalDisburseAmt')).replace(
    ',', '')

'Mengambil nilai summary total expense amount dari confins'
String textTotalExpenseAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalExpenseAmt')).replace(
    ',', '')

'Mengambil nilai summary total tax dari confins'
String textTotalTax = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalTax')).replace(
    ',', '')

'Mengambil nilai summary total vat dari confins'
String textTotalVat = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalVat')).replace(
    ',', '')

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalCommAmt, String.format('%.2f', GlobalVariable.TotalCommissionAmt), false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalCommAmtAftTax, String.format('%.2f', GlobalVariable.TotalCommissionAmtAftTax), false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalDisburseAmt, String.format('%.2f', GlobalVariable.TotalDisburseAmt), false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalExpenseAmt, String.format('%.2f', GlobalVariable.TotalExpenseAmt), false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalTax, String.format('%.2f', GlobalVariable.TotalTax), false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalVat, String.format('%.2f', GlobalVariable.TotalVat), false)

'Arraylist untuk menampung Income Information'
ArrayList<WebElement> varIncomeInfo = driver.findElements(By.cssSelector('#viewIncomeInfo label'))

'Arraylist untuk menampung Remaining Information'
ArrayList<WebElement> varRemainingInfo = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))

'Arraylist untuk menampung Remaining Information amount'
ArrayList<WebElement> remainingInfoAmt = new ArrayList<WebElement>()

'Inisialisasi Variabel untuk menghitung jumlah baris pada Remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countRemainingInfo = varRemainingInfo.size() / 2

'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countIncomeInfo = varIncomeInfo.size() / 2

GlobalVariable.ComRemainingInfoAmt = []

for (int i = 1; i < countRemainingInfo; i++) {
    xpathRemainingInfo = (('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label')

    modifyRemainingInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_UppingRateRemaining'), 
        'xpath', 'equals', xpathRemainingInfo, true)

    'Menambahkan data remaining info amount ke arraylist'
    remainingInfoAmt.add(Double.parseDouble(WebUI.getText(modifyRemainingInfo).replace(',', '')))
}

GlobalVariable.ComRemainingInfoAmt = remainingInfoAmt

modifyMaxAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_MaxAllocatedAmtIncome'), 
    'xpath', 'equals', ('//*[@id="viewIncomeInfo"]/div[' + countIncomeInfo) + ']/div[2]/label', true)

'Ambil nilai max allocated amount dari confins'
String textMaxAllocatedAmount = WebUI.getText(modifyMaxAllocatedAmount).replace(',', '')

'Penghitungan nilai remaining allocated amount tab commission'
BigDecimal remainingAllocatedAmount = Double.parseDouble(textMaxAllocatedAmount) - GlobalVariable.TotalExpenseAmt

modifyRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_RemainingAllocatedAmount'), 
    'xpath', 'equals', ('//div[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfo) + ']/div[2]/label', true)

'Ambil nilai remianing allocated amount dari confins'
String textRemainingAllocatedAmount = WebUI.getText(modifyRemainingAllocatedAmount).replace(',', '')

'Verif nilai remainingallocatedamount sesuai penghitungan'
WebUI.verifyMatch(textRemainingAllocatedAmount, String.format('%.2f', remainingAllocatedAmount), false)

GlobalVariable.RemainingAllocatedAmt = remainingAllocatedAmount

