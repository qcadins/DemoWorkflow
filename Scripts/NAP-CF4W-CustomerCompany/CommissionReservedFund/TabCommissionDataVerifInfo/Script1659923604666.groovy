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
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String databaseTAX = findTestData('Login/Login').getValue(5, 10)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String urlTAX = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseTAX

Sql sqlConnectionTAX = CustomKeywords.'dbconnection.connectDB.connect'(urlTAX, username, password, driverclassname)

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
	newxpathSupplierName = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[1]/label')

	newxpathCommAmt = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[2]/div[2]/label')

	newxpathAmtAftTax = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[3]/div[1]/label')

	newxpathTax = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[1]/div[3]/label')

	newxpathVat = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[2]/div[3]/label')

	newxpathDisburse = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[3]/div[2]/label')

	newxpathExpense = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[4]/div[3]/label')

	newxpathCalMethod = (('//*[@id="formInformationSupplier"]/div[' + (i + 1)) + ']/div/div[2]/div[4]/div[2]/label')

	modifyObjectSuppName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SupplierName'),
		'xpath', 'equals', newxpathSupplierName, true)

	'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
	String supplierName = WebUI.getText(modifyObjectSuppName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSupplier'),
		'xpath', 'equals', newxpathCommAmt, true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommisssionAfterTaxAmtSupplier'),
		'xpath', 'equals', newxpathAmtAftTax, true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSupp'),
		'xpath', 'equals', newxpathTax, true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_VatAmtSupp'),
		'xpath', 'equals', newxpathVat, true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_DisburseAmtSupp'),
		'xpath', 'equals', newxpathDisburse, true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSupp'),
		'xpath', 'equals', newxpathExpense, true)

	'modify object calculation method'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_CalculationMethod'),
		'xpath', 'equals', newxpathCalMethod, true)

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
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

			//			 	WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
		//				  WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
	}
	
	'Pengecekan disburse amt pada confins sesuai perhitungan'
	WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false)

	'Pengecekan expense amt pada confins sesuai perhitungan'
	WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false)
}

'Looping data supplier employee'
for (int j = 1; j <= variableSuppEmp.size(); j++) {
	newxpathSupplierEmpName = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (j + 1)) + ']/div/div[1]/div[2]/div/div[1]/label')

	newxpathCommAmt = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[2]/div[2]/label')

	newxpathAmtAftTax = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[3]/div[1]/label')

	newxpathTax = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[1]/div[3]/label')

	newxpathVat = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[2]/div[3]/label')

	newxpathDisburse = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[3]/div[2]/label')

	newxpathExpense = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[4]/div[3]/label')

	newxpathCalMethod = (('//*[@id="formInformationSupplierEmployee"]/div[' + (j + 1)) + ']/div/div[3]/div[4]/div[2]/label')

	modifyObjectSuppEmpName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SuppEmpName'),
		'xpath', 'equals', newxpathSupplierEmpName, true)

	'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
	String supplierEmployeeName = WebUI.getText(modifyObjectSuppEmpName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSuppEmp'),
		'xpath', 'equals', newxpathCommAmt, true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtSuppEmp'),
		'xpath', 'equals', newxpathAmtAftTax, true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSuppEmp'),
		'xpath', 'equals', newxpathTax, true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_VatAmtSuppEmp'),
		'xpath', 'equals', newxpathVat, true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_DisburseAmtSuppEmp'),
		'xpath', 'equals', newxpathDisburse, true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSuppEmp'),
		'xpath', 'equals', newxpathExpense, true)

	'modify object calculation metthod'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_CalculationMethodSuppEmp'),
		'xpath', 'equals', newxpathCalMethod, true)

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
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

			//			 	WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
		//				 WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
	}
	
	'Pengecekan disburse amount pada confins sesuai perhitungan'
	WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false)

	'Pengecekan expense amount pada confins sesuai perhitungans'
	WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false)
}

'Looping data referantor'
for (int k = 1; k <= variableRef.size(); k++) {
	newxpathRefName = (('//*[@id=\'formInformationReferantor\']/div[' + (k + 1)) + ']/div/div[1]/div[2]/div/div[1]/label')

	newxpathCommAmt = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[2]/div[2]/label')

	newxpathAmtAftTax = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[3]/div[1]/label')

	newxpathTax = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[1]/div[3]/label')

	newxpathVat = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[2]/div[3]/label')

	newxpathDisburse = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[3]/div[2]/label')

	newxpathExpense = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[4]/div[3]/label')

	newxpathCalMethod = (('//*[@id="formInformationReferantor"]/div[' + (k + 1)) + ']/div/div[2]/div[4]/div[2]/label')

	modifyObjectRefName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_RefName'),
		'xpath', 'equals', newxpathRefName, true)

	'Variabel string untuk mengambil nama dari referantor ke-i dari web confins'
	String refName = WebUI.getText(modifyObjectRefName)

	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtRef'),
		'xpath', 'equals', newxpathCommAmt, true)

	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtRef'),
		'xpath', 'equals', newxpathAmtAftTax, true)

	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtRef'),
		'xpath', 'equals', newxpathTax, true)

	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_VatAmtRef'),
		'xpath', 'equals', newxpathVat, true)

	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_DisburseAmtRef'),
		'xpath', 'equals', newxpathDisburse, true)

	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_ExpenseAmtRef'),
		'xpath', 'equals', newxpathExpense, true)

	'modify object calculation method'
	modifyObjectCalMethod = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_CalculationMethodRef'),
		'xpath', 'equals', newxpathCalMethod, true)

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
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt - whTax), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateNettTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), taxpayerInfo[2])

			//			 	WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
		//				 WebUI.verifyLessThanOrEqual(Math.abs(whTax-taxAmount[0]),10)
	} else if (WebUI.getText(modifyObjectCalMethod).equalsIgnoreCase('GROSS')) {
		'Verif nilai comm amt after tax pada confins sesuai perhitungan'
		WebUI.verifyMatch(textCommAmtAftTax, String.format('%.2f', commAmt), false)

		'Pengecekan tax kind code'
		if ((taxpayerInfo[0]) == 'C') {
			'Arraylist untuk menampung amount dari wht dan vat berdasarkan penghitungan'
			ArrayList<WebElement> taxAmount = CustomKeywords.'commissionReserveFundData.taxCalculation.calculateGrossTaxCompany'(Double.parseDouble(
					whtRate[3]), commAmt, Double.parseDouble(whtRate[5]), Double.parseDouble(whtRate[1]), Double.parseDouble(
					vatRate[1]), Double.parseDouble(whtRate[2]), taxpayerInfo[2], taxpayerInfo[1])

			'Verify vat pada confins sesuai dengan penghitungan dengan batas toleransi +-10'
			WebUI.verifyLessThanOrEqual(Math.abs(vaTax - (taxAmount[1])), 10)
		}
	}
	
	'Pengecekan disburse amount pada confins sesuai perhitungan'
	WebUI.verifyMatch(textDisburseAmt, String.format('%.2f', commAmtAftTax + vaTax), false)

	'Pengecekan expense amount pada confins sesuai perhitungan'
	WebUI.verifyMatch(textExpenseAmt, String.format('%.2f', disburseAmt + whTax), false)
}

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

