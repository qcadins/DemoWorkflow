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
import java.text.NumberFormat;
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'Inisialisasi Driver'
WebDriver driver = DriverFactory.getWebDriver()

'Select option dropdownlist Asset Region'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 13), false)

'Input Coverage Amount'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 14))

coverPeriod = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 15)

'Select option dropdownlist cover period'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_CoverPeriodMF'), 
    coverPeriod, false)

'Verifikasi Jika cover period over tenor atau partial tenor'
if(coverPeriod == "Over Tenor"||coverPeriod == "Partial Tenor"){
	'Input Insurance Length'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Insurance Length MF'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 19))
}

'Select option dropdownlist payment type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_Payment Type MF'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 16), false )

'Select option insco branch name'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 17), false)


insuranceNotesCompany = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/textarea_Insurance Notes MF')
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 3)=="Customer - Multifinance"){
	'Modify properti untuk insurance notes bagian company insured by customer multifinance'
	insuranceNotesCompany = WebUI.modifyObjectProperty(insuranceNotesCompany, 'xpath', 'equals',
		"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-nap-detail-form/div/div/div[2]/div/div[1]/div[2]/div[4]/app-insurance-multi-asset-data/app-uc-insurance/div/div/div/div/div/app-uc-insurance-detail/div/form/div[1]/div/div[3]/div[5]/div/textarea", true)
}

'Input insurance notes'
WebUI.setText(insuranceNotesCompany,findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 18))

'Klik Generate Insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Generate Insurance'))

'Verify muncul tidaknya insurance fee setelah klik generate insurance'
if(WebUI.verifyTextNotPresent("INSURANCE FEE",false,FailureHandling.OPTIONAL)){
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

	'write to excel failed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, "8.TabInsuranceData", 0, GlobalVariable.NumofColm - 1,
		GlobalVariable.StatusFailed)
}

'Verifikasi/memastikan isfeeusedefault pada excel'
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 21)=='NO'){
	'Input Admin Fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 22),FailureHandling.OPTIONAL)
	
	'Input Customer Stampduty Fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 23),FailureHandling.OPTIONAL)
}

'Select option dropdownlist main coverage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverage'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 25), false)

'Mengambil nilai row keberapa dimulai data additional coverage (apply to all) pada excel'
def addCovRow = CustomKeywords.'getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Insurance Coverage')+3

ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

'Mengambil dan menyimpan jumlah additional coverage'
int countAddCov = variableAddCovAll.size()

int countEmpty = 0

'Looping checkbox additional coverage (apply to all)'
for(int i =1;i<=countAddCov;i++){
	modifyCheckboxAddtCov = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'),'xpath','equals',"//*[@id='insuranceCoverage']/div[2]/div/label["+i+"]/div/label/input",true)
	
	'Ambil inputan additional coverage dari excel (apply to all)'
	String checkboxValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, addCovRow+i-countEmpty)
	
	'Verify jika checkbox ada (terlihat di UI)'
	if(WebUI.verifyElementVisible(modifyCheckboxAddtCov,FailureHandling.OPTIONAL)){
		'Verifikasi input Additional Coverage'
		if(checkboxValue =="YES"){
			'Jika kondisi awal belum tercentang'
			if(WebUI.verifyElementNotChecked(modifyCheckboxAddtCov,1,FailureHandling.OPTIONAL)){
				'Centang'
				WebUI.check(modifyCheckboxAddtCov)
			}
		}
		else{
			'Jika kondisi awal sudah tercentang'
			if(WebUI.verifyElementChecked(modifyCheckboxAddtCov,1,FailureHandling.OPTIONAL)){
				'Uncentang'
				WebUI.uncheck(modifyCheckboxAddtCov)
			}
		}
	}
	else{
		countEmpty++
		continue
		
	}
	
}

'Klik apply to all'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Apply To All'))


'Inisialisasi Variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

'Mengambil nilai row keberapa dimulai data additional coverage section edit generated insurance table pada excel'
def addCovTableRow = CustomKeywords.'getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table')+6

'Mengambil nilai row keberapa dimulai data sum insured amount pada excel'
def sumInsuredAmountRow = CustomKeywords.'getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Sum Insured Amount')+1

'Looping data tabel insurance untuk input data'
for(int i = 1;i<=count;i++){
	 //Capitalize
	 capitalizeObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Capitalized'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[1]/div/input",true)
	 
	 'Ambil nilai capitalize dari excel'
	 capitalizeValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 35)
	 capitalizeValueArray = capitalizeValue.split(";",-1)
	 
	 'Pengecekan field capitalize excel terisi'
	 if(capitalizeValue.length()>0){
		 'Pengecekan array tidak kosong'
		 if(capitalizeValueArray[i-1]!=""){
			 'Pengecekan nilai capitalize pada excel'
			 if(capitalizeValueArray[i-1].equalsIgnoreCase("YES")){
				 'Jika belum tercentang'
				 if(WebUI.verifyElementNotChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
					 'centang capitalize'
					 WebUI.check(capitalizeObject)
				 }
			 }
			 else if(capitalizeValueArray[i-1].equalsIgnoreCase("NO")){
				 'Jika sudah tercentang'
				 if(WebUI.verifyElementChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
					 'Uncentang capitalize'
					 WebUI.uncheck(capitalizeObject)
				 }
			 }
		 }
	 }
	 //Paid By
	 paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]/div/select",true)
	 
	 'Ambil nilai paid by dari excels'
	 paidByValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 36)
	 paidByValueArray = paidByValue.split(";",-1)
	 'Pengecekan paid by terisi pada excel'
	 if(paidByValue.length()>0){
		 'Pengecekan array paid by tidak kosong'
		 if(paidByValueArray[i-1]!=""){
			 'Select opsi paid by'
			 WebUI.selectOptionByLabel(paidByObject,"(?i)"+paidByValueArray[i-1],true)
		 }
	 }
	 
	 //Sum insured (%)
	 sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
		 "//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/div/input", true)
	 
	 'Ambil nilai sum insured percentage dari excel'
	 sumInsuredPercentValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 37)
	 sumInsuredPercentValueArray = sumInsuredPercentValue.split(";",-1)
	 'Pengecekan sum insured percentage terisi pada excel'
	 if(sumInsuredPercentValue.length()>0){
		 'Pengecekan sum insured percentage array tidak kosong'
		 if(sumInsuredPercentValueArray[i-1]!=""){
			 'input sum insured percentage'
			 WebUI.setText(sumInsuredPercentObject,sumInsuredPercentValueArray[i-1])
		 }
	 }
	 
	 //Main Coverage
	 mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[6]/span[1]/select",true)
	 'Ambil nilai main coverage dari excel'
	 mainCoverageValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 38)
	 mainCoverageValueArray = mainCoverageValue.split(";",-1)
	 'Pengecekan field main coverage di excel terisi'
	 if(mainCoverageValue.length()>0){
		 'Pengecekan array pada field main coverage tidak kosong'
		 if(mainCoverageValueArray[i-1]!=""){
			 'Select opsi main coverage'
			 WebUI.selectOptionByLabel(mainCoverageObject,"(?i)"+mainCoverageValueArray[i-1],true)
		 }
	 }
	 int flagLoading = 0
	 //AdditionalCoverage & Sum Insured Amount
	 'Looping additional coverage & sum insured amount'
	 for(int j = 1;j<=countAddCov;j++){
		 int countSumInsuredAmount = 0
		 addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[6]/div/div/label/input",true)
		 labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[6]/div/div/label",true)
		 'Ambil nilai dari additional coverage per year num pada excel'
		 addCovYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, addCovTableRow+j)
		 addCovYearValueArray = addCovYearValue.split(";",-1)
		 'Supaya checkbox bisa diklik'
		 if(i==1&&j==1){
			 WebUI.click(addCovYearCheckbox)
			 WebUI.click(addCovYearCheckbox)
		 }
		 'Pengecekan jika label = loading'
		 if(WebUI.getText(labelAddCovPerYear).equalsIgnoreCase("LOADING")){
			 flagLoading = 1
			 continue
		 }
		 'Pengecekan additional coverage field dan arraynya tidak kosong'
		 if(addCovYearValue.length()>0 && addCovYearValueArray[i-1-flagLoading]!=""){
			 'Pengecekan nilai additional coverage dari stiap arraynya'
			 if(addCovYearValueArray[i-1-flagLoading].equalsIgnoreCase("Yes")){
				'Jika belum tercentang additional coverage'
			 	if(WebUI.verifyElementNotChecked(addCovYearCheckbox,5,FailureHandling.OPTIONAL)){
					'centang additional coverage'
			 		WebUI.check(addCovYearCheckbox)
			 	}
			 
			 }
			 else if(addCovYearValueArray[i-1-flagLoading].equalsIgnoreCase("No")){
				'Jika sudah tercentang additional coverage'
			 	if(WebUI.verifyElementChecked(addCovYearCheckbox,5,FailureHandling.OPTIONAL)){
					'Uncentang additional coverage'
			 		WebUI.uncheck(addCovYearCheckbox)
			 	}
			 }
		 }
		 
		 modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/div/select",true)
		 'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
		 if(WebUI.verifyElementPresent(modifySumInsuredAmount,2,FailureHandling.OPTIONAL)){
		 	countSumInsuredAmount = 1
		 }
		 
		 'Jika sum insured amount ada dan checkbox additional coverage tercentang'
		 if(countSumInsuredAmount==1&&WebUI.verifyElementChecked(addCovYearCheckbox,5,FailureHandling.OPTIONAL)){
			 'Ambil nilai sum insured amount dari excel'
			 SumInsuredValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, sumInsuredAmountRow+j)
			 SumInsuredValueArray = SumInsuredValue.split(";",-1)
			 'Select index sum insured amount'
			 WebUI.selectOptionByIndex(modifySumInsuredAmount,SumInsuredValueArray[i-1-flagLoading], FailureHandling.OPTIONAL)
		 }
		 
	 }
	
}

'Klik calculate insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))

ArrayList<BigDecimal> totalResult
'keyword untuk verify tabel hasil generate insurance (main premi, additional premi,total premi per year, total premi'
totalResult = CustomKeywords.'insuranceData.verifInsuranceData.verifyTabInsuranceData'()


'ambil nilai total main premi dari confins'
String textTotalMainPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_TotalMainPremium')).replace(",","")
'ambil nilai total additional premi dari confins'
String textTotalAdditionalPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_TotalAdditionalPremium')).replace(",","")
'ambil nilai total fee dari confins'
String textTotalFeeAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_TotalFee')).replace(",","")
'ambil nilai total premi to customer dari confins'
String textTotalPremitoCust = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomer')).replace(",","")

'ambil nilai admin fee dari confins'
String adminFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),'value').replace(",","")
'Parsing adminfee ke tipe data angka (long)'
BigDecimal adminFeeAmt = Long.parseLong(adminFeeAmtText)
'ambil nilai stampduty fee dari confins'
String stampdutyFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),'value').replace(",","")
'Parsing stampdutyfee ke tipe data angka (long)'
BigDecimal stampdutyFeeAmt = Long.parseLong(stampdutyFeeAmtText)
'Perhitungan total fee'
totalFeeResult = adminFeeAmt+stampdutyFeeAmt
'Perhitungan total premi to customer'
totalPremitoCustResult = totalResult[0]+totalResult[1]+totalFeeResult

'Verif total main premi sesuai perhitungan'
WebUI.verifyMatch(textTotalMainPremiAmt,String.format("%.2f", totalResult[0]),false)
'Verif total additional premi sesuai perhitungan'
WebUI.verifyMatch(textTotalAdditionalPremiAmt,String.format("%.2f", totalResult[1]),false)
'Verif total fee sesuai perhitungan'
WebUI.verifyMatch(textTotalFeeAmt, String.format("%.2f", totalFeeResult), false)
'Verif total premi to customer sesuai perhitungan'
WebUI.verifyMatch(textTotalPremitoCust, String.format("%.2f", totalPremitoCustResult), false)

if(totalResult[2]==0){
	'Input diskon'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 56))
	
}

'Ambil nilai total premi to customer after discount dari confins'
String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(",","")
'ambil nilai diskon pada confins'
String textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),'value').replace(",","")
BigDecimal discountAmt = Long.parseLong(textDiscountAmt)

if(totalResult[2]==1){
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 56, GlobalVariable.NumofColm -
		1, textDiscountAmt)
}


'Perhitungan total premi to customer after discount'
totalPremitoCustAftDiscountResult = totalPremitoCustResult-discountAmt


'Verif total premi to customer after discount sesuai perhitungan'
WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format("%.2f", totalPremitoCustAftDiscountResult), false)

String textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),'value').replace(",","")

'Verif total premi to customer after discount sesuai perhitungan'
if(totalResult[3]!=0){
	WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]+totalFeeResult).toString(), false)
}
else{
	WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]).toString(), false)
}


GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))

GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
	'value', FailureHandling.OPTIONAL)