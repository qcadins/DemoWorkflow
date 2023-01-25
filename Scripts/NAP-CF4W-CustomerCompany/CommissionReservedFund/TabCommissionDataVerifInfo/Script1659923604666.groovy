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

'Koneksi database'
Sql sqlConnectionTAX = CustomKeywords.'dbConnection.connectDB.connectTAX'()

'Inisialisasi global variabel untuk penghitungan summary'
GlobalVariable.TotalCommissionAmt = 0.00
GlobalVariable.TotalCommissionAmtAftTax = 0.00
GlobalVariable.TotalTax = 0.00
GlobalVariable.TotalVat = 0.00
GlobalVariable.TotalDisburseAmt = 0.00
GlobalVariable.TotalExpenseAmt = 0.00

variableSupp = driver.findElements(By.cssSelector('#formInformationSupplier h4'))

variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))

variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))

'Arraylist untuk menampung tax rate (wht dan vat), penalty rate, persentase taxable serta tiernya (amt from dan amt to) dari db'
ArrayList<WebElement> taxRate = CustomKeywords.'commissionReserveFundData.taxCalculation.checkTaxRateCompany'(sqlConnectionTAX)

whtRate = (taxRate[0]).split(';')

vatRate = (taxRate[1]).split(';')

'Looping data Supplier'
for (int i = 1; i <= variableSupp.size(); i++) {

	modifyObjectSuppName = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_SupplierName'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[1]/label'), true)

	'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
	String supplierName = WebUI.getText(modifyObjectSuppName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSupplier'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[2]/div[2]/label'), true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommisssionAfterTaxAmtSupplier'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[3]/div[1]/label'), true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSupp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[1]/div[3]/label'), true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_VatAmtSupp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[2]/div[3]/label'), true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_DisburseAmtSupp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[3]/div[2]/label'), true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSupp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[4]/div[3]/label'), true)

	'modify object calculation method'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_CalculationMethod'),
		'xpath', 'equals', (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[4]/div[2]/label'), true)

	'Menghitung total amount untuk verify summary'
	CustomKeywords.'commissionReserveFundData.commission.calculateCommissionSummary'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax,
		modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)

	String textCommAmt = WebUI.getText(modifyObjectCommAmt).replace(',', '').trim()

	String textDisburseAmt = WebUI.getText(modifyObjectDisburse).replace(',', '').trim()

	String textExpenseAmt = WebUI.getText(modifyObjectExpense).replace(',', '').trim()

	String textCommAmtAftTax = WebUI.getText(modifyObjectAmtAftTax).replace(',', '').trim()

	String textWHT = WebUI.getText(modifyObjectTax).replace(',', '').trim()

	String textVat = WebUI.getText(modifyObjectVat).replace(',', '').trim()

	BigDecimal commAmt = Double.parseDouble(textCommAmt)

	BigDecimal disburseAmt = Double.parseDouble(textDisburseAmt)

	BigDecimal expenseAmt = Double.parseDouble(textExpenseAmt)

	BigDecimal commAmtAftTax = Double.parseDouble(textCommAmtAftTax)

	BigDecimal whTax = Double.parseDouble(textWHT)

	BigDecimal vaTax = Double.parseDouble(textVat)

	'Arraylist untuk menampung flag npwp exist dan is vat, serta tax kind code dari db'
	ArrayList<WebElement> taxpayerInfo = CustomKeywords.'commissionReserveFundData.taxCalculation.checkTaxpayerInfo'(sqlConnectionTAX,
		supplierName)

	'Pengecekan calculation method gross atau nett'
	if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('NETT')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	  
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	}
	
	'Pengecekan disburse amt pada confins sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
	'Pengecekan expense amt pada confins sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
}

'Looping data supplier employee'
for (int j = 1; j <= variableSuppEmp.size(); j++) {

	modifyObjectSuppEmpName = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_SuppEmpName'),
		'xpath', 'equals', (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (j + 1)) + ']/div/div[1]/div[2]/div/div[1]/label'), true)

	'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
	String supplierEmployeeName = WebUI.getText(modifyObjectSuppEmpName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[2]/div[2]/label'), true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[3]/div[1]/label'), true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSuppEmp'),
		'xpath', 'equals',  (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[1]/div[3]/label'), true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_VatAmtSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[2]/div[3]/label'), true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_DisburseAmtSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[3]/div[2]/label'), true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[4]/div[3]/label'), true)

	'modify object calculation metthod'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_CalculationMethodSuppEmp'),
		'xpath', 'equals', (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[4]/div[2]/label'), true)

	'Menghitung total amount untuk verify summary'
	CustomKeywords.'commissionReserveFundData.commission.calculateCommissionSummary'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax,
		modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)

	String textCommAmt = WebUI.getText(modifyObjectCommAmt).replace(',', '').trim()

	String textDisburseAmt = WebUI.getText(modifyObjectDisburse).replace(',', '').trim()

	String textExpenseAmt = WebUI.getText(modifyObjectExpense).replace(',', '').trim()

	String textCommAmtAftTax = WebUI.getText(modifyObjectAmtAftTax).replace(',', '').trim()

	String textWHT = WebUI.getText(modifyObjectTax).replace(',', '').trim()

	String textVat = WebUI.getText(modifyObjectVat).replace(',', '').trim()

	BigDecimal commAmt = Double.parseDouble(textCommAmt)

	BigDecimal disburseAmt = Double.parseDouble(textDisburseAmt)

	BigDecimal expenseAmt = Double.parseDouble(textExpenseAmt)

	BigDecimal commAmtAftTax = Double.parseDouble(textCommAmtAftTax)

	BigDecimal whTax = Double.parseDouble(textWHT)

	BigDecimal vaTax = Double.parseDouble(textVat)

	'Arraylist untuk menampung flag npwp exist dan is vat, serta tax kind code dari db'
	ArrayList<WebElement> taxpayerInfo = CustomKeywords.'commissionReserveFundData.taxCalculation.checkTaxpayerInfo'(sqlConnectionTAX,
		supplierEmployeeName)

	'Pengecekan calculation method pada confins gross atau nett'
	if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('NETT')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	   
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	}
	
	'Pengecekan disburse amount pada confins sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
	'Pengecekan expense amount pada confins sesuai perhitungans'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
}

'Looping data referantor'
for (int k = 1; k <= variableRef.size(); k++) {

	modifyObjectRefName = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_RefName'),
		'xpath', 'equals', (('//*[@id=\'formInformationReferantor\']/div[' + (k + 1)) + ']/div/div[1]/div[2]/div/div[1]/label'), true)

	'Variabel string untuk mengambil nama dari referantor ke-i dari web confins'
	String refName = WebUI.getText(modifyObjectRefName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[2]/div[2]/label'), true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[3]/div[1]/label'), true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[1]/div[3]/label'), true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_VatAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[2]/div[3]/label'), true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_DisburseAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[3]/div[2]/label'), true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_ExpenseAmtRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[4]/div[3]/label'), true)

	'modify object calculation method'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_CalculationMethodRef'),
		'xpath', 'equals', (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[4]/div[2]/label'), true)

	'Menghitung total amount yang akan digunakan untuk verify summary'
	CustomKeywords.'commissionReserveFundData.commission.calculateCommissionSummary'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax,
		modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)

	String textCommAmt = WebUI.getText(modifyObjectCommAmt).replace(',', '').trim()

	String textDisburseAmt = WebUI.getText(modifyObjectDisburse).replace(',', '').trim()

	String textExpenseAmt = WebUI.getText(modifyObjectExpense).replace(',', '').trim()

	String textCommAmtAftTax = WebUI.getText(modifyObjectAmtAftTax).replace(',', '').trim()

	String textWHT = WebUI.getText(modifyObjectTax).replace(',', '').trim()

	String textVat = WebUI.getText(modifyObjectVat).replace(',', '').trim()

	BigDecimal commAmt = Double.parseDouble(textCommAmt)

	BigDecimal disburseAmt = Double.parseDouble(textDisburseAmt)

	BigDecimal expenseAmt = Double.parseDouble(textExpenseAmt)

	BigDecimal commAmtAftTax = Double.parseDouble(textCommAmtAftTax)

	BigDecimal whTax = Double.parseDouble(textWHT)

	BigDecimal vaTax = Double.parseDouble(textVat)

	'Arraylist untuk menampung flag npwp exist dan is vat, serta tax kind code dari db'
	ArrayList<WebElement> taxpayerInfo = CustomKeywords.'commissionReserveFundData.taxCalculation.checkTaxpayerInfo'(sqlConnectionTAX,
		refName)

	'Pengecekan calculation method pada confins gross atau nett'
	if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('NETT')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

//			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
//			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	   
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false), '12.TabCommissionData',
			GlobalVariable.NumofColm)
		
		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

//			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
//			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 30)
		}
	}
	
	'Pengecekan disburse amount pada confins sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
	'Pengecekan expense amount pada confins sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false), '12.TabCommissionData',
		GlobalVariable.NumofColm)
	
}

'Mengambil nilai summary total commission amount dari confins'
String textTotalCommAmt = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommisionAmt')).replace(
	',', '')

'Mengambil nilai summary total commission after tax amount dari confins'
String textTotalCommAmtAftTax = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmt')).replace(
	',', '')

'Mengambil nilai summary total disburse amount dari confins'
String textTotalDisburseAmt = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalDisburseAmt')).replace(
	',', '')

'Mengambil nilai summary total expense amount dari confins'
String textTotalExpenseAmt = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalExpenseAmt')).replace(
	',', '')

'Mengambil nilai summary total tax dari confins'
String textTotalTax = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalTax')).replace(
	',', '')

'Mengambil nilai summary total vat dari confins'
String textTotalVat = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/label_TotalVat')).replace(
	',', '')

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalCommAmt, String.format('%.2f', GlobalVariable.TotalCommissionAmt), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalCommAmtAftTax, String.format('%.2f', GlobalVariable.TotalCommissionAmtAftTax), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalDisburseAmt, String.format('%.2f', GlobalVariable.TotalDisburseAmt), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalExpenseAmt, String.format('%.2f', GlobalVariable.TotalExpenseAmt), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalTax, String.format('%.2f', GlobalVariable.TotalTax), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

'Verify nilai pada section summary sesuai dengan perhitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalVat, String.format('%.2f', GlobalVariable.TotalVat), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

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

'Arraylist untuk nilai yang dibagikan'
ArrayList<WebElement> AllocatedCommissionAmt = new ArrayList<WebElement>()

AllocatedCommissionAmt = GlobalVariable.AllocatedCommissionAmt

for (int i = 1; i < countRemainingInfo; i++) {
	
	modifyIncomeInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_UppingRateRemaining'),
		'xpath', 'equals', (('//*[@id="viewIncomeInfo"]/div[' + i) + ']/div/div[2]/label'), true)

	modifyRemainingInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_UppingRateRemaining'),
		'xpath', 'equals', (('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label'), true)
	
	'Ambil nilai income info amount dari confins'
	String textIncomeInfoAmt = WebUI.getText(modifyIncomeInfo).replace(',', '')
	
	'Ambil nilai remaining info amount dari confins'
	String textRemainingInfoAmt = WebUI.getText(modifyRemainingInfo).replace(',', '')
	
	println(Double.parseDouble(textIncomeInfoAmt))
	println(AllocatedCommissionAmt[(i - 1)])
	
	'Verifikasi untuk section remaining info after calculate,  income info tab commission - nilai yg sudah dibagikan'
	checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(textRemainingInfoAmt)), Math.round(Double.parseDouble(textIncomeInfoAmt) - (Math.round(
						(AllocatedCommissionAmt[(i - 1)]) * 100) / 100))), '12.TabCommissionData',
				GlobalVariable.NumofColm)

	'Menambahkan data remaining info amount ke arraylist'
	remainingInfoAmt.add(Double.parseDouble(WebUI.getText(modifyRemainingInfo).replace(',', '')))
}

GlobalVariable.ComRemainingInfoAmt = remainingInfoAmt

modifyMaxAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_MaxAllocatedAmtIncome'),
	'xpath', 'equals', ('//*[@id="viewIncomeInfo"]/div[' + countIncomeInfo) + ']/div[2]/label', true)

'Ambil nilai max allocated amount dari confins'
String textMaxAllocatedAmount = WebUI.getText(modifyMaxAllocatedAmount).replace(',', '')

'Penghitungan nilai remaining allocated amount tab commission'
BigDecimal remainingAllocatedAmount = Double.parseDouble(textMaxAllocatedAmount) - GlobalVariable.TotalExpenseAmt

modifyRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/label_RemainingAllocatedAmount'),
	'xpath', 'equals', ('//div[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfo) + ']/div[2]/label', true)

'Ambil nilai remianing allocated amount dari confins'
String textRemainingAllocatedAmount = WebUI.getText(modifyRemainingAllocatedAmount).replace(',', '')

'Verif nilai remainingallocatedamount sesuai penghitungan'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingAllocatedAmount, String.format('%.2f', remainingAllocatedAmount), false), '12.TabCommissionData',
	GlobalVariable.NumofColm)

GlobalVariable.RemainingAllocatedAmt = remainingAllocatedAmount

public checkVerifyEqualOrMatch(Boolean isMatch, String sheetname, int numofcolm){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname,
				0, numofcolm-1, GlobalVariable.StatusFailed)

		'write to excel verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname,
				1, numofcolm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}