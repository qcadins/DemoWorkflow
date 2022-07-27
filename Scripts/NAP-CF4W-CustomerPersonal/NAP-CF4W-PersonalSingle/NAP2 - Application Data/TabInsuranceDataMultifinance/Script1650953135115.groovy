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


int countFlood = 0, countTPL = 0, countAOG = 0, countSRCC = 0, countTJHTP = 0, countKDUP = 0, countTerrorist = 0
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),2,FailureHandling.OPTIONAL)){
	countFlood = 1
}
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTPL'),2,FailureHandling.OPTIONAL)){
	countTPL = 1
}
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountAOG'),2,FailureHandling.OPTIONAL)){
	countAOG = 1
}
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountSRCC'),2,FailureHandling.OPTIONAL)){
	countSRCC = 1
}

if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTJHTP'),2,FailureHandling.OPTIONAL)){
	countTJHTP = 1
}
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountKDUP'),2,FailureHandling.OPTIONAL)){
	countKDUP = 1
}
if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTerrorist'),2,FailureHandling.OPTIONAL)){
	countTerrorist = 1
}

'Inisialisasi Driver'
WebDriver driver = DriverFactory.getWebDriver()

'Inisialisasi Variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

'Looping data insurance untuk input data'
for(int i = 1;i<=count;i++){
	 //Capitalize
	 capitalizeObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Capitalized'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[1]/div/input",true)
	 
	 capitalizeValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 35)
	 capitalizeValueArray = capitalizeValue.split(";",-1)
	 
	 if(capitalizeValue.length()>0){
		 if(capitalizeValueArray[i-1]!=""){
			 if(capitalizeValueArray[i-1].equalsIgnoreCase("YES")){
				 if(WebUI.verifyElementNotChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
					 WebUI.check(capitalizeObject)
				 }
			 }
			 else if(capitalizeValueArray[i-1].equalsIgnoreCase("NO")){
				 if(WebUI.verifyElementChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
					 WebUI.uncheck(capitalizeObject)
				 }
			 }
		 }
	 }
	 //Paid By
	 paidByObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]/div/select",true)
	 
	 paidByValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 36)
	 paidByValueArray = paidByValue.split(";",-1)
	 
	 if(paidByValue.length()>0){
		 if(paidByValueArray[i-1]!=""){
			 WebUI.selectOptionByLabel(paidByObject,"(?i)"+paidByValueArray[i-1],true)
		 }
	 }
	 
	 //Sum insured
	 sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
		 "//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/div/input", true)
	 
	 sumInsuredPercentValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 37)
	 sumInsuredPercentValueArray = sumInsuredPercentValue.split(";",-1)
	 if(sumInsuredPercentValue.length()>0){
		 if(sumInsuredPercentValueArray[i-1]!=""){
			 WebUI.setText(sumInsuredPercentObject,sumInsuredPercentValueArray[i-1])
		 }
	 }
	 
	 //Main Coverage
	 mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[6]/span[1]/select",true)
	 mainCoverageValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 38)
	 mainCoverageValueArray = mainCoverageValue.split(";",-1)
	 if(mainCoverageValue.length()>0){
		 if(mainCoverageValueArray[i-1]!=""){
			 WebUI.selectOptionByLabel(mainCoverageObject,"(?i)"+mainCoverageValueArray[i-1],true)
		 }
	 }
	 
	 //AdditionalCoverage
	  Object floodYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[6]/div/div/label/input",true)
	  Object tplYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[6]/div/div/label/input",true)
	  Object aogYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[6]/div/div/label/input",true)
	  Object srccYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[6]/div/div/label/input",true)
	  Object tjhtpYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[6]/div/div/label/input",true)
	  Object kdupYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[6]/div/div/label/input",true)
	  Object terroristYearCheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[6]/div/div/label/input",true)

	  floodYearValue  = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 40)
	  floodYearValueArray = floodYearValue.split(";",-1)
	  tplYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 41)
	  tplYearValueArray= tplYearValue.split(";",-1)
	  aogYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 42)
	  aogYearValueArray= aogYearValue.split(";",-1)
	  srccYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 43)
	  srccYearValueArray= srccYearValue.split(";",-1)
	  tjhtpYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 44)
	  tjhtpYearValueArray= tjhtpYearValue.split(";",-1)
	  kdupYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 45)
	  kdupYearValueArray= kdupYearValue.split(";",-1)
	  terroristYearValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 46)
	  terroristYearValueArray= terroristYearValue.split(";",-1)
	  if(i==1){
		  WebUI.click(floodYearCheckbox)
	  }
	  
	  if(floodYearValue.length()>0 && floodYearValueArray[i-1]!=""){
		  if(floodYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(floodYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(floodYearCheckbox)
			  }
			 
		  }
		  else if(floodYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(floodYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(floodYearCheckbox)
			  }
		  }
	  }
	  
	  if(tplYearValue.length()>0 && tplYearValueArray[i-1]!=""){
		  if(tplYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(tplYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(tplYearCheckbox)
			  }
			 
		  }
		  else if(tplYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(tplYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(tplYearCheckbox)
			  }
		  }
	  }
	  
	  if(aogYearValue.length()>0 && aogYearValueArray[i-1]!=""){
		  if(aogYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(aogYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(aogYearCheckbox)
			  }
			 
		  }
		  else if(aogYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(aogYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(aogYearCheckbox)
			  }
		  }
	  }
	  
	  if(srccYearValue.length()>0 && srccYearValueArray[i-1]!=""){
		  if(srccYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(srccYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(srccYearCheckbox)
			  }
			 
		  }
		  else if(srccYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(srccYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(srccYearCheckbox)
			  }
		  }
	  }
	  
	  if(tjhtpYearValue.length()>0 && tjhtpYearValueArray[i-1]!=""){
		  if(tjhtpYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(tjhtpYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(tjhtpYearCheckbox)
			  }
			 
		  }
		  else if(tjhtpYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(tjhtpYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(tjhtpYearCheckbox)
			  }
		  }
	  }
	  
	  if(kdupYearValue.length()>0 && kdupYearValueArray[i-1]!=""){
		  if(kdupYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(kdupYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(kdupYearCheckbox)
			  }
			 
		  }
		  else if(kdupYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(kdupYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(kdupYearCheckbox)
			  }
		  }
	  }
	  
	  if(terroristYearValue.length()>0 && terroristYearValueArray[i-1]!=""){
		  if(terroristYearValueArray[i-1].equalsIgnoreCase("Yes")){
			  if(WebUI.verifyElementNotChecked(terroristYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.check(terroristYearCheckbox)
			  }
			 
		  }
		  else if(terroristYearValueArray[i-1].equalsIgnoreCase("No")){
			  if(WebUI.verifyElementChecked(terroristYearCheckbox,5,FailureHandling.OPTIONAL)){
				  WebUI.uncheck(terroristYearCheckbox)
			  }
		  }
	  }
	  
	 
	 //SumInsuredAmount
	 if(countFlood==1&&WebUI.verifyElementChecked(floodYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtFlood = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[7]/div/div/select",true)
		 SumInsuredFloodValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 48)
		 SumInsuredFloodValueArray = SumInsuredFloodValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtFlood,SumInsuredFloodValueArray[i-1])
	 }
	 if(countTPL==1&&WebUI.verifyElementChecked(tplYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtTPL = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTPL'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[7]/div/div/select",true)
		 SumInsuredTPLValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 49)
		 SumInsuredTPLValueArray = SumInsuredTPLValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtTPL,SumInsuredTPLValueArray[i-1])
	 }
	 if(countAOG==1&&WebUI.verifyElementChecked(aogYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtAOG = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountAOG'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[7]/div/div/select",true)
		 SumInsuredAOGValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 50)
		 SumInsuredAOGValueArray = SumInsuredAOGValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtAOG,SumInsuredAOGValueArray[i-1])
	 }
	 if(countSRCC==1&&WebUI.verifyElementChecked(srccYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtSRCC = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountSRCC'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[7]/div/div/select",true)
		 SumInsuredSRCCValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 51)
		 SumInsuredSRCCValueArray = SumInsuredSRCCValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtSRCC,SumInsuredSRCCValueArray[i-1])
	 }
	 if(countTJHTP==1&&WebUI.verifyElementChecked(tjhtpYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtTJHTP = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTJHTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[7]/div/div/select",true)
		 SumInsuredTJHTPValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 52)
		 SumInsuredTJHTPValueArray = SumInsuredTJHTPValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtTJHTP,SumInsuredTJHTPValueArray[i-1])
	 }
	 if(countKDUP==1&&WebUI.verifyElementChecked(kdupYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtKDUP = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountKDUP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[7]/div/div/select",true)
		 SumInsuredKDUPValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 53)
		 SumInsuredKDUPValueArray = SumInsuredKDUPValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtKDUP,SumInsuredKDUPValueArray[i-1])
	 }
	 if(countTerrorist==1&&WebUI.verifyElementChecked(terroristYearCheckbox,5,FailureHandling.OPTIONAL)){
		 modifyObjectSumInsuredAmtTerrorist = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTerrorist'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[7]/div/div/select",true)
		 SumInsuredTerroristValue = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 54)
		 SumInsuredTerroristValueArray = SumInsuredTerroristValue.split(";",-1)
		 WebUI.selectOptionByIndex(modifyObjectSumInsuredAmtTerrorist,SumInsuredTerroristValueArray[i-1])
	 }
	
}

'Klik calculate insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))

ArrayList<BigDecimal> totalResult
totalResult = CustomKeywords.'insuranceData.verifInsuranceData.verifyTabInsuranceData'()


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
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 56))
	
}

'Ambil nilai total premi to customer after discount dari confins'
String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(",","")
'ambil nilai diskon pada confins'
String textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),'value').replace(",","")
BigDecimal discountAmt = Long.parseLong(textDiscountAmt)

if(totalResult[2]==1){
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 56, GlobalVariable.NumofColm -
		1, textDiscountAmt)
}


'Perhitungan total premi to customer after discount'
totalPremitoCustAftDiscountResult = totalPremitoCustResult-discountAmt


'Verif total premi to customer after discount sesuai perhitungan'
WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format("%.2f", totalPremitoCustAftDiscountResult), false)

String textCapitalizeAmount = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),'value').replace(",","")

'Verif total premi to customer after discount sesuai perhitungan'
WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]+totalFeeResult).toString(), false)

GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))

GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
	'value', FailureHandling.OPTIONAL)