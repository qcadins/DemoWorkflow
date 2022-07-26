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
if(coverPeriod == "Over Tenor"||coverPeriod == "Partial Tenor"){
	'Input Insurance Length'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Insurance Length MF'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 19))
}

'Select option dropdownlist payment type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_Payment Type MF'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 16), false )

'Select option insco branch name'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 17), false)


insuranceNotesCompany = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/textarea_Insurance Notes MF')
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 3)=="Customer - Multifinance"){
	'Modify properti untuk insurance notes bagian company insured by customer multifinance'
	insuranceNotesCompany = WebUI.modifyObjectProperty(insuranceNotesCompany, 'xpath', 'equals',
		"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-nap-detail-form/div/div/div[2]/div/div[1]/div[2]/div[4]/app-insurance-multi-asset-data/app-uc-insurance/div/div/div/div/div/app-uc-insurance-detail/div/form/div[1]/div/div[3]/div[5]/div/textarea", true)
}

'Input insurance notes'
WebUI.setText(insuranceNotesCompany,findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 18))

'Klik Generate Insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Generate Insurance'))

'Verify muncul tidaknya insurance fee setelah klik generate insurance'
if(WebUI.verifyTextNotPresent("INSURANCE FEE",false,FailureHandling.OPTIONAL)){
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

	'write to excel failed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, "8.TabInsuranceData", 0, GlobalVariable.NumofColm - 1,
		GlobalVariable.StatusFailed)
}

'Verifikasi/memastikan isfeeusedefault pada excel'
if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 21)=='NO'){
	'Input Admin Fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 22),FailureHandling.OPTIONAL)
	
	'Input Customer Stampduty Fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 23),FailureHandling.OPTIONAL)
}

'Select option dropdownlist main coverage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_MainCoverage'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 25), false)

'Ambil inputan Additional Coverage dari Excel'
String flood=findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 27)
String tpl = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 28)
String actOfGod =findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 29)
String SRCC =findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 30)
String tanggungJawab =findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 31)
String kecelakaanDiri = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 32)
String teroris = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 33)


'Verifikasi input Additional Coverage'
if(flood =="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang flood'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Uncentang flood'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'))
	}
}

if(tpl =="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'),1,FailureHandling.OPTIONAL)){
		'Centang tpl'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'),1,FailureHandling.OPTIONAL)){
		'Uncentang tpl'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel ng-valid ng-dirty ng-touched'))
	}
}



if(actOfGod=="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang act of god'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Uncentang act of god'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel ng-valid'))
	}
}



if(SRCC=="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang SRCC'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Uncentang SRCC'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel ng-valid'))
	}
}



if(tanggungJawab=="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang Tanggung Jawab Hukum Terhadap Penumpang'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Uncentang Tanggung Jawab Hukum Terhadap Penumpang'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel ng-valid'))
	}
}



if(kecelakaanDiri=="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang Kecelakaan Diri untuk Penumpang'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'),1,FailureHandling.OPTIONAL)){
		'Uncentang kecelakaan diri untuk penumpang'
		WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel ng-valid'))
	}
}



if(teroris =="YES"){
	'Jika kondisi awal belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'),1,FailureHandling.OPTIONAL)){
		'Centang terrorist'
		WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'))
	}
}
else{
	'Jika kondisi awal sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel ng-untouched ng-pristine ng-valid'),1,FailureHandling.OPTIONAL)){
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
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

BigDecimal totalMainPremiumResult = 0, totalAdditionalPremiumResult = 0
BigDecimal totalFeeResult = 0, totalPremitoCustResult = 0, totalPremitoCustAftDiscountResult=0
int counterPaidByMF=0
'Looping data insurance'
for (int i = 1; i <= count; i++) {
	println(count)
	'Inisialisasi Format untuk mendapatkan nilai desimal dari nilai persen'
	NumberFormat decimalFormat = NumberFormat.getPercentInstance()
	
	paidByObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]/div/select",true)
	
	if(WebUI.verifyOptionSelectedByLabel(paidByObject,'MULTIFINANCE',false,20,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
		counterPaidByMF = 1
	}
	
	rateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Rate'), 'xpath', 'equals',
			"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]/div/input", true)
	
	'Ambil nilai dari rate'
	String textRate = WebUI.getAttribute(rateObject,'value').replaceAll("\\s","")
	
	'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi'
	Float rate = decimalFormat.parse(textRate).floatValue()
	
	sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
			"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/div/input", true)
	
	'Ambil nilai dari persentase sum insured'
	String textSumInsuredPercent = WebUI.getAttribute(sumInsuredPercentObject,'value').replaceAll("\\s","")
	
	'Mengubah nilai persen menjadi desimal'
	Float sumInsuredPercent = decimalFormat.parse(textSumInsuredPercent).floatValue();
	
	'Ambil nilai coverage amount'
	String textAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")
	BigDecimal Amount = Long.parseLong(textAmount)

	numberOfMonthObject =  WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_numberOfMonth'), 'xpath', 'equals',
			"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[4]", true)
	'Ambil nilai number of month'
	String textNumberOfMonth = WebUI.getText(numberOfMonthObject)
	
	BigDecimal numberOfMonth = Integer.parseInt(textNumberOfMonth)

	mainPremiObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_MainPremiumAmt'), 'xpath', 'equals',
			"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[9]", true)
	
	'Ambil nilai Main Premium Amount'
	String textMainPremi = WebUI.getText(mainPremiObject).replace(",","").replace(".00","")

	'Perhitungan Main Premium Amount'
	BigDecimal Result = Math.round(Amount*sumInsuredPercent*rate*(numberOfMonth/12)/10)*10

	'Verifikasi/memastikan nilai main premium amount pada confins sama dengan perhitungan'
	WebUI.verifyMatch(textMainPremi,Result.toString(),false, FailureHandling.OPTIONAL)
	
	'Tambahkan main premium ke total main premium'
	totalMainPremiumResult+=Result
	
	
	
	floodRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[8]/div/span/div/input",true)
	tplRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[8]/div/span/div/input",true)
	aogRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[8]/div/span/div/input",true)
	srccRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[8]/div/span/div/input",true)
	tjhtpRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[8]/div/span/div/input",true)
	kdupRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[8]/div/span/div/input",true)
	terroristRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[8]/div/span/div/input",true)
	loadingRateObject= WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Loading_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[10]/td[8]/div/span/div/input",true)

	'Ambil nilai dari rate additional coverage flood'
	String textFloodRate = WebUI.getAttribute(floodRateObject,'value').replaceAll("\\s","")
	
	'Ambil nilai dari rate additional coverage tpl'
	String textTPLRate = WebUI.getAttribute(tplRateObject,'value').replace(",","")
	
	'Ambil nilai dari rate additional coverage act of god'
	String textAOGRate = WebUI.getAttribute(aogRateObject,'value').replaceAll("\\s","")
	
	'Ambil nilai dari rate additional coverage srcc'
	String textSRCCRate = WebUI.getAttribute(srccRateObject,'value').replaceAll("\\s","")
	
	'Ambil nilai dari rate additional coverage tanggung jawab hukum terhadap penumpang'
	String textTJHTPRate = WebUI.getAttribute(tjhtpRateObject,'value').replace(",","")
	
	'Ambil nilai dari rate additional coverage kecelakaan diri untuk penumpang'
	String textKDUPRate = WebUI.getAttribute(kdupRateObject,'value').replace(",","")
	
	'Ambil nilai dari rate additional coverage terrorist'
	String textTerroristRate = WebUI.getAttribute(terroristRateObject,'value').replaceAll("\\s","")
	
	'Ambil nilai dari rate additional coverage loading'
	String textLoadingRate = WebUI.getAttribute(loadingRateObject,'value').replaceAll("\\s","")
	
	
	floodYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[6]/div/div/label/input",true)
	tplYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[6]/div/div/label/input",true)
	aogYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[6]/div/div/label/input",true)
	srccYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[6]/div/div/label/input",true)
	tjhtpYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[6]/div/div/label/input",true)
	kdupYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[6]/div/div/label/input",true)
	terroristYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[6]/div/div/label/input",true)
	
	
	Float floodRate = 0.00
	'Jika additional coverage flood tercentang'
	if(WebUI.verifyElementChecked(floodYearCheckbox,2,FailureHandling.OPTIONAL)){
		'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
		floodRate = decimalFormat.parse(textFloodRate).floatValue()
	}
	BigDecimal tplRate = 0
	'Jika tpl tercentang'
	if(WebUI.verifyElementChecked(tplYearCheckbox,2,FailureHandling.OPTIONAL)){
		'simpan nilai ratenya'
		tplRate = Long.parseLong(textTPLRate)
	}
	Float aogRate = 0.00
	'jika act of god tercentang'
	if(WebUI.verifyElementChecked(aogYearCheckbox,2,FailureHandling.OPTIONAL)){
		'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
		aogRate = decimalFormat.parse(textAOGRate).floatValue()
	}
	Float srccRate = 0.00
	'jika srcc tercentang'
	if(WebUI.verifyElementChecked(srccYearCheckbox,2,FailureHandling.OPTIONAL)){
		'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
		srccRate = decimalFormat.parse(textSRCCRate).floatValue()
	}
	BigDecimal tjhtpRate = 0
	'jika tanggung jawab hukum terhadap penumpang tercentang'
	if(WebUI.verifyElementChecked(tjhtpYearCheckbox,2,FailureHandling.OPTIONAL)){
		'simpan nilai ratenya'
		tjhtpRate = Long.parseLong(textTJHTPRate)
	}
	BigDecimal kdupRate = 0
	'jika kecelekaan diri untuk penumpang tercentang'
	if(WebUI.verifyElementChecked(kdupYearCheckbox,2,FailureHandling.OPTIONAL)){
		'simpan nilai ratenya'
		kdupRate = Long.parseLong(textKDUPRate)
	}
	Float terroristRate = 0.00
	'jika terrorist tercentang'
	if(WebUI.verifyElementChecked(terroristYearCheckbox,2,FailureHandling.OPTIONAL)){
		'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
		terroristRate = decimalFormat.parse(textTerroristRate).floatValue()
	}
	Float loadingRate = 0.00
	'jika rate loading tidak kosong'
	if(textLoadingRate.length()>1){
		'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
		loadingRate = decimalFormat.parse(textLoadingRate).floatValue()
	}
	
	'Perhitungan nilai additional premi flood'
	BigDecimal resultFloodPremi = Math.round(Result*floodRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi tpl'
	BigDecimal resultTPLPremi = Math.round(tplRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi act of god'
	BigDecimal resultAOGPremi = Math.round(Result*aogRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi srcc'
	BigDecimal resultSRCCPremi = Math.round(Result*srccRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi tanggung jawab hukum terhadap penumpang'
	BigDecimal resultTJHTPPremi = Math.round(tjhtpRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi kecelakaan diri untuk penumpang'
	BigDecimal resultKDUPPremi = Math.round(kdupRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi terrorist'
	BigDecimal resultTerroristPremi = Math.round(Result*terroristRate*(numberOfMonth/12))
	'Perhitungan nilai additional premi loading'
	BigDecimal resultLoadingPremi = Math.round(Result*loadingRate*(numberOfMonth/12))
	
	floodPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_FloodAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[9]",true)
	tplPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_TPLAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[9]",true)
	aogPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_AOGAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[9]",true)
	srccPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_SRCCAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[9]",true)
	tjhtpPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_TJHTPAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[9]",true)
	kdupPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_KDUPAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[9]",true)
	terroristPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_TerroristAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[9]",true)
	loadingPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_LoadingAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[10]/td[9]",true)
	
	'ambil nilai additional premi flood dari confins'
	String textFloodPremiAmt = WebUI.getText(floodPremiAmtObject).replace(",","")
	'ambil nilai additional premi tpl dari confins'
	String textTPLPremiAmt = WebUI.getText(tplPremiAmtObject).replace(",","")
	'ambil nilai additional premi act of god dari confins'
	String textAOGPremiAmt = WebUI.getText(aogPremiAmtObject).replace(",","")
	'ambil nilai additional premi srcc dari confins'
	String textSRCCPremiAmt = WebUI.getText(srccPremiAmtObject).replace(",","")
	'ambil nilai additional premi tanggung jawab hukum terhdap penumpang dari confins'
	String textTJHTPPremiAmt = WebUI.getText(tjhtpPremiAmtObject).replace(",","")
	'ambil nilai additional premi kecelakaan diri untuk penumpang dari confins'
	String textKDUPPremiAmt = WebUI.getText(kdupPremiAmtObject).replace(",","")
	'ambil nilai additional premi terrorist dari confins'
	String textTerroristPremiAmt = WebUI.getText(terroristPremiAmtObject).replace(",","")
	'ambil nilai additional premi loading dari confins'
	String textLoadingPremiAmt = WebUI.getText(loadingPremiAmtObject).replace(",","")
	
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textFloodPremiAmt, String.format("%.2f", resultFloodPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textTPLPremiAmt, String.format("%.2f", resultTPLPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textAOGPremiAmt, String.format("%.2f", resultAOGPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textSRCCPremiAmt, String.format("%.2f", resultSRCCPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textTJHTPPremiAmt, String.format("%.2f", resultTJHTPPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textKDUPPremiAmt, String.format("%.2f", resultKDUPPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textTerroristPremiAmt, String.format("%.2f", resultTerroristPremi),false)
	'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
	WebUI.verifyMatch(textLoadingPremiAmt, String.format("%.2f", resultLoadingPremi),false)
	
	'tambahkan additional premi ke total additional premi'
	totalAdditionalPremiumResult+=(resultFloodPremi+resultAOGPremi+resultTPLPremi+resultSRCCPremi+resultTJHTPPremi+resultKDUPPremi+resultTerroristPremi+resultLoadingPremi)
	
	'tambahkan main premi dan additional premi untuk menghitung total premi per tahunnya'
	BigDecimal resultTotalPremiPerYear = Result+resultFloodPremi+resultAOGPremi+resultTPLPremi+resultSRCCPremi+resultTJHTPPremi+resultKDUPPremi+resultTerroristPremi+resultLoadingPremi
	
	totalPremiPerYearObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/td_TotalPremiPerYear'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[11]/td[9]",true)
	
	'ambil nilai total premi per tahun dari confins'
	String textTotalPremiPerYear = WebUI.getText(totalPremiPerYearObject).replace(",","")
	
	'verifikasi nilai total premi per tahun dari confins sesuai dengan perhitungan'
	WebUI.verifyMatch(textTotalPremiPerYear, String.format("%.2f", resultTotalPremiPerYear),false)
	
}

'ambil nilai total main premi dari confins'
String textTotalMainPremiAmt = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalMainPremium')).replace(",","")
'ambil nilai total additional premi dari confins'
String textTotalAdditionalPremiAmt = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalAdditionalPremium')).replace(",","")
'ambil nilai total fee dari confins'
String textTotalFeeAmt = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalFee')).replace(",","")
'ambil nilai total premi to customer dari confins'
String textTotalPremitoCust = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomer')).replace(",","")

'ambil nilai admin fee dari confins'
String adminFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),'value').replace(",","")
'Parsing adminfee ke tipe data angka (long)'
BigDecimal adminFeeAmt = Long.parseLong(adminFeeAmtText)
'ambil nilai stampduty fee dari confins'
String stampdutyFeeAmtText = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),'value').replace(",","")
'Parsing stampdutyfee ke tipe data angka (long)'
BigDecimal stampdutyFeeAmt = Long.parseLong(stampdutyFeeAmtText)
'Perhitungan total fee'
totalFeeResult = adminFeeAmt+stampdutyFeeAmt
'Perhitungan total premi to customer'
totalPremitoCustResult = totalMainPremiumResult+totalAdditionalPremiumResult+totalFeeResult

'Verif total main premi sesuai perhitungan'
WebUI.verifyMatch(textTotalMainPremiAmt,String.format("%.2f", totalMainPremiumResult),false)
'Verif total additional premi sesuai perhitungan'
WebUI.verifyMatch(textTotalAdditionalPremiAmt,String.format("%.2f", totalAdditionalPremiumResult),false)
'Verif total fee sesuai perhitungan'
WebUI.verifyMatch(textTotalFeeAmt, String.format("%.2f", totalFeeResult), false)
'Verif total premi to customer sesuai perhitungan'
WebUI.verifyMatch(textTotalPremitoCust, String.format("%.2f", totalPremitoCustResult), false)

if(counterPaidByMF==0){
	'Input diskon'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 56))
	
}

'Ambil nilai total premi to customer after discount dari confins'
String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(",","")
'ambil nilai diskon pada confins'
String textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),'value').replace(",","")
BigDecimal discountAmt = Long.parseLong(textDiscountAmt)
'Perhitungan total premi to customer after discount'
totalPremitoCustAftDiscountResult = totalPremitoCustResult-discountAmt

'Verif total premi to customer after discount sesuai perhitungan'
WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format("%.2f", totalPremitoCustAftDiscountResult), false)

GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))

GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
	'value', FailureHandling.OPTIONAL)