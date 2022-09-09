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
import groovy.sql.Sql as Sql
import org.openqa.selenium.Keys as Keys

'Inisialisasi Driver'
WebDriver driver = DriverFactory.getWebDriver()

'Inisialisasi Variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

'Mengambil nilai row keberapa dimulai data additional coverage section edit generated insurance table pada excel'
def addCovTableRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table') +
6

'Mengambil nilai row keberapa dimulai data edit generated insurance table pada excel'
def editGenTableRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table')+1

'Mengambil nilai row keberapa dimulai data sum insured amount pada excel'
def sumInsuredAmountRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Sum Insured Amount') +
1

'Mengambil nilai row keberapa data main premi rate pada excel'
def Rate = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Rate') +
2

'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
def AddRate = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Additional Premi Rate') +
1

'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
def TotalPremium = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Total Premium') +
1

int counterPaidByMF=0

ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

'Mengambil dan menyimpan jumlah additional coverage'
int countAddCov = variableAddCovAll.size()

'Looping data tabel insurance untuk input data'
for (int i = 1; i <= count; i++) {
		
	//Paid By
	paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[1]/div/select', true)

	'Ambil nilai paid by dari excels'
	paidByValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, editGenTableRow+2)

	paidByValueArray = paidByValue.split(';', -1)

	'Pengecekan paid by terisi pada excel'
	if (paidByValue.length() > 0) {
		'Pengecekan array paid by tidak kosong'
		if ((paidByValueArray[(i - 1)]) != '') {
			'Select opsi paid by'
			WebUI.selectOptionByLabel(paidByObject, '(?i)' + (paidByValueArray[(i - 1)]), true)
		}
	}
	
	if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true,20,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
		counterPaidByMF = 1
	}
	
	//Sum insured (%)
	sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[4]/div/input', true)

	'Ambil nilai sum insured percentage dari excel'
	sumInsuredPercentValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, editGenTableRow+3)

	sumInsuredPercentValueArray = sumInsuredPercentValue.split(';', -1)

	'Pengecekan sum insured percentage terisi pada excel'
	if (sumInsuredPercentValue.length() > 0) {
		'Pengecekan sum insured percentage array tidak kosong'
		if ((sumInsuredPercentValueArray[(i - 1)]) != '') {
			'input sum insured percentage'
			WebUI.setText(sumInsuredPercentObject, sumInsuredPercentValueArray[(i - 1)])
		}
	}
	
	//Main Coverage
	mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[5]/span[1]/select',
		true)

	'Ambil nilai main coverage dari excel'
	mainCoverageValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, editGenTableRow+4)

	mainCoverageValueArray = mainCoverageValue.split(';', -1)

	'Pengecekan field main coverage di excel terisi'
	if (mainCoverageValue.length() > 0) {
		'Pengecekan array pada field main coverage tidak kosong'
		if ((mainCoverageValueArray[(i - 1)]) != '') {
			'Select opsi main coverage'
			WebUI.selectOptionByLabel(mainCoverageObject, '(?i)' + (mainCoverageValueArray[(i - 1)]), true)
		}
	}
	
	//Main Premi Rate
	mainPremiRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[7]/div/input",true)
	
	'Pengecekan jika rate dapat terisi atau tidak'
	if(WebUI.verifyElementNotHasAttribute(mainPremiRateObject,"disabled",2,FailureHandling.OPTIONAL)){
		'Ambil nilai main premi rate dari excel'
		mainPremiRateValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, Rate)
		
		mainPremiRateValueArray = mainPremiRateValue.split(';',-1)
		
		'Pengecekan field main premi rate di excel terisi'
		if (mainPremiRateValue.length() > 0) {
			'Pengecekan array pada field main premi rate tidak kosong'
			if ((mainPremiRateValueArray[(i - 1)]) != '') {
				
				WebUI.click(mainPremiRateObject)
				
				'Input main premi rate tahun ke i-1'
				WebUI.setText(mainPremiRateObject, mainPremiRateValueArray[(i-1)])
				
			}
		}
	}
	
	
	int flagLoading = 0

	
	
	//AdditionalCoverage & Sum Insured Amount
	'Looping additional coverage & sum insured amount'
	for (int j = 1; j <= countAddCov; j++) {
		int countSumInsuredAmount = 0

		addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
			'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label/input',
			true)

		labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
			'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label',
			true)

		'Ambil nilai dari additional coverage per year num pada excel'
		addCovYearValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, addCovTableRow + j -flagLoading)

		addCovYearValueArray = addCovYearValue.split(';', -1)

		'Supaya checkbox bisa diklik'
		if ((i == 1) && (j == 1)) {
			WebUI.click(addCovYearCheckbox)

			WebUI.click(addCovYearCheckbox)
		}
		
		'Pengecekan jika label = loading'
		if (WebUI.getText(labelAddCovPerYear).equalsIgnoreCase('LOADING')) {
			flagLoading = 1

			continue
		}
		
		'Pengecekan additional coverage field dan arraynya tidak kosong'
		if ((addCovYearValue.length() > 0) && ((addCovYearValueArray[((i - 1))]) != '')) {
			'Pengecekan nilai additional coverage dari stiap arraynya'
			if ((addCovYearValueArray[((i - 1))]).equalsIgnoreCase('Yes')) {
				'Jika belum tercentang additional coverage'
				if (WebUI.verifyElementNotChecked(addCovYearCheckbox, 5, FailureHandling.OPTIONAL)) {
					'centang additional coverage'
					WebUI.check(addCovYearCheckbox)
				}
			} else if ((addCovYearValueArray[((i - 1))]).equalsIgnoreCase('No')) {
				'Jika sudah tercentang additional coverage'
				if (WebUI.verifyElementChecked(addCovYearCheckbox, 5, FailureHandling.OPTIONAL)) {
					'Uncentang additional coverage'
					WebUI.uncheck(addCovYearCheckbox)
				}
			}
		}
		
		modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
			'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/select',
			true)

		'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
		if (WebUI.verifyElementPresent(modifySumInsuredAmount, 2, FailureHandling.OPTIONAL)) {
			countSumInsuredAmount = 1
		}
		
		'Jika sum insured amount ada dan checkbox additional coverage tercentang'
		if ((countSumInsuredAmount == 1) && WebUI.verifyElementChecked(addCovYearCheckbox, 5, FailureHandling.OPTIONAL)) {
			'Ambil nilai sum insured amount dari excel'
			SumInsuredValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, sumInsuredAmountRow + j)

			SumInsuredValueArray = SumInsuredValue.split(';', -1)
			
			'Pengecekan jika sum insured amount kosong atau tidak pada excel'
			if(SumInsuredValue.length()>0){
				'Pengecekan jika array sum insured amount berisi nilai'
				if(SumInsuredValueArray[i-1]!=""){
					
					'Select index sum insured amount'
					WebUI.selectOptionByIndex(modifySumInsuredAmount, SumInsuredValueArray[((i - 1))], FailureHandling.OPTIONAL)
				}
			}
		   
		}
		
		modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/span/div/input",true)
		
		if(WebUI.verifyElementNotHasAttribute(modifyAddtRateObject,"disabled",2)){
			'Ambil nilai additional premi rate dari excel'
			AddtRateValue = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, AddRate + j)

			AddtRateValueArray = AddtRateValue.split(';', -1)
			
			'Pengecekan additional premi rate pada excel kosong atau tidak'
			if(AddtRateValue.length()>0){
				'Pengecekan additional premi rate array tidak kosong'
				if(AddtRateValueArray[i-1]!=""){
					WebUI.click(modifyAddtRateObject)
					
					'Input Rate Additional Premi'
					WebUI.setText(modifyAddtRateObject, AddtRateValueArray[((i - 1))])
				}
			}
			
		}
		
	}
}

//excelGetRow.getRow
'Mengambil nilai row keberapa dimulai data additional coverage section edit generated insurance table pada excel'
def capPartialRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Capitalize if GS_Value Partial') +
1

//centang or uncentang cap berdasarkan excel
if(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, capPartialRow+1)=="YES"){
	if(WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'),3,FailureHandling.OPTIONAL)){
		WebUI.check(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
	}
	
}
else if(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, capPartialRow+1)=="NO"){
	if(WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'),3,FailureHandling.OPTIONAL)){
		WebUI.uncheck(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
	}
}


'Klik calculate insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))

if(WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'),2,FailureHandling.OPTIONAL)&&findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, capPartialRow+2).length()>0){
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, capPartialRow+2))
}
//if verif uncheck set cap amts
//kenapa karena jika set merupakan inputan user excel tidak perlu diverif
ArrayList<WebElement> totalResult
BigDecimal totalPremitoCustResult
BigDecimal totalFeeResult
if(GlobalVariable.RoleCompany=="Testing"){
	
	'keyword untuk verify tabel hasil generate insurance (main premi, additional premi,total premi per year, total premi'
	totalResult = CustomKeywords.'insuranceData.verifInsuranceData.verifyTabInsuranceDataCapPartial'()
	
	'ambil nilai total main premi dari confins'
	String textTotalMainPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalMainPremium')).replace(
		',', '')
	
	'ambil nilai total additional premi dari confins'
	String textTotalAdditionalPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalAdditionalPremium')).replace(
		',', '')
	
	'ambil nilai total fee dari confins'
	String textTotalFeeAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalFee')).replace(
		',', '')
	
	'ambil nilai total premi to customer dari confins'
	String textTotalPremitoCust = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomer')).replace(
		',', '')
	
	'ambil nilai admin fee dari confins'
	String adminFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),
		'value').replace(',', '')
	
	'Parsing adminfee ke tipe data angka (long)'
	BigDecimal adminFeeAmt = Long.parseLong(adminFeeAmtText)
	
	'ambil nilai stampduty fee dari confins'
	String stampdutyFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),
		'value').replace(',', '')
	
	'Parsing stampdutyfee ke tipe data angka (long)'
	BigDecimal stampdutyFeeAmt = Long.parseLong(stampdutyFeeAmtText)
	
	'Perhitungan total fee'
	totalFeeResult = (adminFeeAmt + stampdutyFeeAmt)
	
	'Perhitungan total premi to customer'
	totalPremitoCustResult = (((totalResult[0]) + (totalResult[1])) + totalFeeResult)
	
	'Verif total main premi sesuai perhitungan'
	WebUI.verifyMatch(textTotalMainPremiAmt, String.format('%.2f', totalResult[0]), false)
	
	'Verif total additional premi sesuai perhitungan'
	WebUI.verifyMatch(textTotalAdditionalPremiAmt, String.format('%.2f', totalResult[1]), false)
	
	'Verif total fee sesuai perhitungan'
	WebUI.verifyMatch(textTotalFeeAmt, String.format('%.2f', totalFeeResult), false)
	
	'Verif total premi to customer sesuai perhitungan'
	WebUI.verifyMatch(textTotalPremitoCust, String.format('%.2f', totalPremitoCustResult), false)
	
	if(totalResult[2]==0){
		'Input diskon'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, TotalPremium+1))
	}
	
}

if (counterPaidByMF == 0) {
	'Input diskon'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
		findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, TotalPremium+1))
}

String textDiscountAmt

'ambil nilai diskon pada confins'
textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
	'value').replace(',', '')
	
if(GlobalVariable.RoleCompany=="Testing"){
	'Ambil nilai total premi to customer after discount dari confins'
	String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(
		',', '')
	
	'ambil nilai diskon pada confins'
	textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
		'value').replace(',', '')
	
	BigDecimal discountAmt = Long.parseLong(textDiscountAmt)
	
	'Perhitungan total premi to customer after discount'
	totalPremitoCustAftDiscountResult = (totalPremitoCustResult - discountAmt)
	
	'Verif total premi to customer after discount sesuai perhitungan'
	WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format('%.2f', totalPremitoCustAftDiscountResult), false)
	
		
	String textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),
		'value').replace(',', '')
	
	'Verif capitalize amount sesuai perhitungan'
	if ((totalResult[3]) != 0 && totalResult[2]==1) {
		WebUI.verifyMatch(textCapitalizeAmount, ((totalResult[3]) + totalFeeResult).toString(), false)
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
			GlobalVariable.NumofColm - 1, textDiscountAmt)
	} 
	else if((totalResult[3]) != 0 && totalResult[2]==0){
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
		textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),
			'value').replace(',', '')
		WebUI.verifyMatch(textCapitalizeAmount, ((totalResult[3]) + totalFeeResult - discountAmt).toString(), false)
	}
	else if((totalResult[3]) == 0 && totalResult[2]==1 && WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'),2,FailureHandling.OPTIONAL)){
		WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]+totalFeeResult).toString(),false)
	}
	
	
}



if (counterPaidByMF == 1) {
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
		GlobalVariable.NumofColm - 1, textDiscountAmt)
}



GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))

GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
	'value', FailureHandling.OPTIONAL)