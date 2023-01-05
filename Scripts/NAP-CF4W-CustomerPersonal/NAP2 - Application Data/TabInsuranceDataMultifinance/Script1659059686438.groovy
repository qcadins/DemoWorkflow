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

'koneksi db los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi db fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData')

def totalFeeResult 

'Inisialisasi Driver'
WebDriver driver = DriverFactory.getWebDriver()

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/span_AppNo'))

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
	'Ambil nilai asset region dari rule excel berdasarkan condition-condition'
	String defaultAssetReg = CustomKeywords.'insuranceData.verifyAssetRegion.checkAssetRegionBasedOnRule'(sqlConnectionLOS, appNo)
	
//	'Verif default asset region based on rule'
//	if(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'),'value'),defaultAssetReg, false)==false){
//		writeFailedReasonVerifyRule()
//	}

}

'Select option dropdownlist Asset Region'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'), 
    datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 22), false)

'Input Coverage Amount'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'), 
    datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 23))

'get coverperiod from excel'
coverPeriod = datafileTabInsurance.getValue(
    GlobalVariable.NumofColm, 24)

'Select option dropdownlist cover period'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_CoverPeriodMF'), 
    coverPeriod, false)

'Verifikasi Jika cover period over tenor atau partial tenor'
if ((coverPeriod == 'Over Tenor') || (coverPeriod == 'Partial Tenor')) {
    'Input Insurance Length'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Insurance Length MF'), 
        datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 28))
}

'Select option dropdownlist payment type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_Payment Type MF'), 
    datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 25), false)

if(GlobalVariable.Role=="Testing"){
	'declare inscobranchname'
	ArrayList<WebElement> inscoBranchName = new ArrayList<WebElement>()
	
	'Ambil text original office dari confins'
	String officeName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
	'Ambil array string (text) insco branch name dari db'
	inscoBranchName = CustomKeywords.'insuranceData.checkInscoBranch.checkDDLInscoBranch'(sqlConnectionFOU, officeName)
	
	'Ambil nilai count insco branch name dari db'
	Integer countInscoBranch = CustomKeywords.'insuranceData.checkInscoBranch.countDDLInscoBranch'(sqlConnectionFOU, officeName)
	
	'Verif dropdownlist insco branch name yang muncul pada confins sesuai dengan array string insco branch name dari db'
	if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'),
		inscoBranchName)==false){
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
		
		GlobalVariable.FlagFailed=1
	}
	
	'Ambil nilai jumlah option/pilihan insco branch name dari confins'
	Integer totalInscoBranch = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'))
	
	'Verif jumlah insco branch name yang muncul pada confins sesuai dengan jumlah insco branch name pada db'
	if(WebUI.verifyEqual(totalInscoBranch - 1, countInscoBranch)==false){
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
		
		GlobalVariable.FlagFailed=1
	}
}

'Select option insco branch name'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_InscoBranchNameMF'), 
    datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 26), false)

insuranceNotesCompany = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/textarea_Insurance Notes MF')

if (datafileTabInsurance.getValue(
    GlobalVariable.NumofColm, 12) == 'Customer - Multifinance') {
    'Modify properti untuk insurance notes bagian company insured by customer multifinance'
    insuranceNotesCompany = WebUI.modifyObjectProperty(insuranceNotesCompany, 'xpath', 'equals', '/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-nap-detail-form/div/div/div[2]/div/div[1]/div[2]/div[4]/app-insurance-data/app-uc-insurance/div/div/div/div/div/app-uc-insurance-detail/div/form/div[1]/div/div[3]/div[5]/div/textarea', 
        true)
}

'Input insurance notes'
WebUI.setText(insuranceNotesCompany, datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 27))

buttonGenerateInsurance = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Generate Insurance')
if (datafileTabInsurance.getValue(
	GlobalVariable.NumofColm, 12) == 'Customer - Multifinance') {
	'Klik Generate Insurance'
	buttonGenerateInsurance = WebUI.modifyObjectProperty(buttonGenerateInsurance,'xpath','equals',"//*[@id='insuranceInformation']/div[3]/div[6]/button",true)
}

'Klik Generate Insurance'
WebUI.click(buttonGenerateInsurance)

'ambil nilai cust insurance length'
GlobalVariable.InsuranceLength = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/insurancelength'),
	'value',FailureHandling.OPTIONAL)

'Verify muncul tidaknya insurance fee setelah klik generate insurance'
if (WebUI.verifyTextNotPresent('INSURANCE FEE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonGenerateGagal'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonGenerateGagal)
	
	GlobalVariable.FlagFailed=1
}

'Ambil string opsi yang dipilih pada dropdownlist insco branch name excel'
selectedInscoBranch = datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 26)

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
	
	'Membaca rule excel untuk menentukan default admin fee dan customer stampduty beserta behaviournya'
	HashMap<String,ArrayList> result = CustomKeywords.'insuranceData.verifyInsuranceFee.verifyFee'(sqlConnectionLOS, appNo,selectedInscoBranch, sqlConnectionFOU)
	
	'declare feebhv, defamt'
	ArrayList<String> feeBhv, defAmt
	feeBhv = result.get("Bhv")
	defAmt = result.get("Amt")
	
	'Ambil nilai admin fee dari confins'
	adminFeeDefAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),'value')
	
	'Ambil nilai customer stamp duty dari confins'
	custStampDutyDefAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),'value')
	
	'Verif nilai default admin fee yang muncul pada confins sesuai rule'
	if(WebUI.verifyMatch(adminFeeDefAmt.replace(",",""),defAmt[0],false)==false){
		'write to excel failed reason verify rule'
		writeFailedReasonVerifyRule()
	}
	
	'Verif nilai default customer stampduty yang muncul pada confins sesuai rule'
	if(WebUI.verifyMatch(custStampDutyDefAmt.replace(",",""),defAmt[1],false)==false){
		'write to excel failed reason verify rule'
		writeFailedReasonVerifyRule()
	}
	
	'Pengecekan field terlock (lock, dan tidak bisa diedit) /tidak terlock (def, bisa diedit) berdasarkan rule'
	if(feeBhv[0]=="DEF"){
		'Verif field admin fee bisa diedit'
		if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),"readonly", GlobalVariable.TimeOut,FailureHandling.OPTIONAL)==false){
			'write to excel failed reason verify rule'
			writeFailedReasonVerifyRule()
		}
	}
	else if(feeBhv[0]=="LOCK"){
		'Verif field admin fee tidak bisa diedit'
		if(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),"readonly", GlobalVariable.TimeOut,FailureHandling.OPTIONAL)==false){
			'write to excel failed reason verify rule'
			writeFailedReasonVerifyRule()
		}
		else{
			'write to excel admin fee amount'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 30,
				GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'),'value').replace(",","")))
		}
	}
	if(feeBhv[1]=="DEF"){
		'Verif field customer stampduty bisa diedit'
		if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),"readonly", GlobalVariable.TimeOut,FailureHandling.OPTIONAL)==false){
			'write to excel failed reason verify rule'
			writeFailedReasonVerifyRule()
		}
	}
	else if(feeBhv[1]=="LOCK"){
		'Verif field customer stampduty tidak bisa diedit'
		if(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),"readonly", GlobalVariable.TimeOut,FailureHandling.OPTIONAL)==false){
			'write to excel failed reason verify rule'
			writeFailedReasonVerifyRule()
		}
		else{
			'write to excel cust stampduty amount'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 31,
				GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'),'value').replace(",","")))
		}
	}
}

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData')

'Verifikasi/memastikan isfeeusedefault pada excel'
if (datafileTabInsurance.getValue(
    GlobalVariable.NumofColm, 30) == 'NO') {
    'Input Admin Fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Admin Fee_adminFee'), 
        datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 31), FailureHandling.OPTIONAL)

    'Input Customer Stampduty Fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Customer Stampduty Fee_adminFee'), 
        datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
}

'Select option dropdownlist main coverage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverage'), 
    datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 34), false)

'Mengambil nilai row keberapa dimulai data additional coverage (apply to all) pada excel'
def addCovRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Insurance Coverage') + 
3

'declare variableaddcovall'
ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

'Mengambil dan menyimpan jumlah additional coverage'
int countAddCov = variableAddCovAll.size()

'declare countempty'
int countEmpty = 0

'Looping checkbox additional coverage (apply to all)'
for (int i = 1; i <= countAddCov; i++) {
	'modify checkbox additional coverage'
    modifyCheckboxAddtCov = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel ng-valid'), 
        'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[2]/div/label[' + i) + ']/div/label/input', true)

    'Ambil inputan additional coverage dari excel (apply to all)'
    String checkboxValue = datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, (addCovRow + i) - countEmpty)

    'Verify jika checkbox ada (terlihat di UI)'
    if (WebUI.verifyElementVisible(modifyCheckboxAddtCov, FailureHandling.OPTIONAL)) {
        'Verifikasi input Additional Coverage'
        if (checkboxValue == 'YES') {
            'Jika kondisi awal belum tercentang'
            if (WebUI.verifyElementNotChecked(modifyCheckboxAddtCov, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'Centang'
                WebUI.check(modifyCheckboxAddtCov)
            }
        } else if(checkboxValue == 'NO') {
            'Jika kondisi awal sudah tercentang'
            if (WebUI.verifyElementChecked(modifyCheckboxAddtCov, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'Uncentang'
                WebUI.uncheck(modifyCheckboxAddtCov)
            }
        }
    } else {
        countEmpty++

        continue
    }
}

'Klik apply to all'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Apply To All'))

'Mengambil nilai setting cap insurance dari db'
String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlConnectionFOU)

'Jika cap insurance bernilai yearly'
if(capinssetting=="YEARLY"){
	'get region from confins'
	String selectedRegion = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'),'value')
	
	'get coverage amount from confinss'
	String covAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")
	
	'Inisialisasi Variabel'
	ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))
	
	//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
	'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
	int count = variable.size()
	
	'Mengambil nilai row keberapa dimulai data additional coverage section edit generated insurance table pada excel'
	def addCovTableRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table') +
	6
	
	'Mengambil nilai row keberapa dimulai data edit generated insurance table pada excel'
	def editGenTableRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table')+1
	
	'Mengambil nilai row keberapa dimulai data sum insured amount pada excel'
	def sumInsuredAmountRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Sum Insured Amount') +
	1
	
	'Mengambil nilai row keberapa data main premi rate pada excel'
	def Rate = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Rate') +
	2
	
	'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
	def AddRate = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Additional Premi Rate') +
	1
	
	'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
	def TotalPremium = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Total Premium') +
	1
	
	'declare counterpaidbymf'
	int counterPaidByMF=0
	
	'Ambil nilai string road worthiness document dari db'
	String rwd = CustomKeywords.'assetData.checkAssetRoadWorthinessDoc.checkRWD'(sqlConnectionLOS,appNo)
	
	'Looping data tabel insurance untuk input data'
	for (int i = 1; i <= count; i++) {
		//modify object year num
		yearNumObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/td_YearNo'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[3]",true)
		if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
			'modify object sum insured percentage'
			sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'),
				'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[5]/div/input', true)
			
			'Ambil nilai sum insured percent dari confins'
			sumInsuredPercentValue = WebUI.getAttribute(sumInsuredPercentObject,'value').replace(" %","")
			
			'Membaca rule excel untuk menentukan year num dan default sum insured percentage'
			HashMap<String, ArrayList> resultSumInsured = CustomKeywords.'insuranceData.verifySumInsured.verifySumInsuredMainCov'(sqlConnectionLOS, sqlConnectionFOU,appNo,selectedInscoBranch)
			
			'declare yearno, suminsuredpercentage'
			ArrayList<String> yearNo, sumInsuredPctg
			
			yearNo = resultSumInsured.get("Year")
			
			sumInsuredPctg = resultSumInsured.get("Pctg")
			
			'Looping jumlah data result yang sesuai dengan condition dari rule excel'
			for(int j = 0;j<yearNo.size();j++){
				
				'Verify year num pada confins sesuai dengan year num ke-j dari rule excel'
				if(WebUI.verifyMatch(WebUI.getText(yearNumObject),yearNo.get(j),false,FailureHandling.OPTIONAL)){
					
					'Verify default sum insured percentage yang tampil pada confins sesuai dengan rule'
					if(WebUI.verifyEqual(Double.parseDouble(sumInsuredPercentValue),Double.parseDouble(sumInsuredPctg.get(j)))==false){
						'write to excel failed reason verify rule'
						writeFailedReasonVerifyRule()
					}
					
					break
				}
			}
		}
		
		//Capitalize
		capitalizeObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Capitalized'),
			'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[1]/div/input', true)
	
		'Ambil nilai capitalize dari excel'
		capitalizeValue = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, editGenTableRow+1)
	
		capitalizeValueArray = capitalizeValue.split(';', -1)
	
		'Pengecekan field capitalize excel terisi'
		if (capitalizeValue.length() > 0) {
			'Pengecekan array tidak kosong'
			if ((capitalizeValueArray[(i - 1)]) != '') {
				'Pengecekan nilai capitalize pada excel'
				if ((capitalizeValueArray[(i - 1)]).equalsIgnoreCase('YES')) {
					'Jika belum tercentang'
					if (WebUI.verifyElementNotChecked(capitalizeObject, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'centang capitalize'
						WebUI.check(capitalizeObject)
					}
				} else if ((capitalizeValueArray[(i - 1)]).equalsIgnoreCase('NO')) {
					'Jika sudah tercentang'
					if (WebUI.verifyElementChecked(capitalizeObject, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'Uncentang capitalize'
						WebUI.uncheck(capitalizeObject)
					}
				}
			}
		}
		
		//Paid By
		paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),
			'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[2]/div/select', true)
	
		'Ambil nilai paid by dari excels'
		paidByValue = datafileTabInsurance.getValue(
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
		
		if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true, GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
			counterPaidByMF = 1
		}
		
		//Sum insured (%)
		sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'),
			'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[5]/div/input', true)
	
		'Ambil nilai sum insured percentage dari excel'
		sumInsuredPercentValue = datafileTabInsurance.getValue(
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
		mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),
			'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[6]/span[1]/select',
			true)
	
		'Ambil nilai main coverage dari excel'
		mainCoverageValue = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, editGenTableRow+4)
	
		mainCoverageValueArray = mainCoverageValue.split(';', -1)
	
		'Pengecekan field main coverage di excel terisi'
		if (mainCoverageValue.length() > 0) {
			'Pengecekan array pada field main coverage tidak kosong'
			if ((mainCoverageValueArray[(i - 1)]) != '') {
				'Select opsi main coverage'
				WebUI.selectOptionByLabel(mainCoverageObject, '(?i)' + (mainCoverageValueArray[(i - 1)]), true)
				
				'Select opsi main coverage'
				WebUI.selectOptionByLabel(mainCoverageObject, '(?i)' + 'ALL RISK', true)
					
				'Select opsi main coverage'
				WebUI.selectOptionByLabel(mainCoverageObject, '(?i)' + (mainCoverageValueArray[(i - 1)]), true)
				
				WebUI.delay(3)
			}
		}
		
		//Main Premi Rate
		mainPremiRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]/div/input",true)
		
		String mainPremiVal
		
		//covAmt = Integer.parseInt()
		sumInsuredPercentObject
		//Verif Main Premi Rate Based on Rule
		if(GlobalVariable.Role=="Testing"  && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
			'Mencari nilai main premi rate berdasarkan kondisi-kondisi pada rule excel'
			HashMap<String,ArrayList> resultMainCvg = CustomKeywords.'insuranceData.verifyMainCvg.verifyMainPremiRate'(sqlConnectionLOS, sqlConnectionFOU,appNo,selectedInscoBranch,selectedRegion,covAmt)
			
			'modify random object'
			modifyRandomObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/testobject'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[2]/td[5]",true)
			
			'klik random object'
			WebUI.click(modifyRandomObject)
			
			'Ambil nilai main premi rate dari confins'
			mainPremiVal = WebUI.getAttribute(mainPremiRateObject,'value').replace(" %","")
			
			'declare main coverage type, main premi rate'
			ArrayList<String> mainCvgType, mainPremiRate
			mainCvgType = resultMainCvg.get("MainCvg")
			mainPremiRate = resultMainCvg.get("MainRate")
			
			'Looping data main premi rate yang didapat dari rule'
			for(int j = 0 ;j<mainPremiRate.size();j++){
				
				'Pengecekan jika main coverage type confins sesuai dengan main coverage type rule excel ke-j'
				if(WebUI.getAttribute(mainCoverageObject,'value').equalsIgnoreCase(mainCvgType.get(j))){
					
					'Verif main premi rate yang tampil pada confins sesuai dengan rule excel'
					if(WebUI.verifyEqual(Double.parseDouble(mainPremiVal),Double.parseDouble(mainPremiRate.get(j)))==false){
						'write to excel failed reason verify rule'
						writeFailedReasonVerifyRule()
					}
					break
				}
			}
		}
		'Pengecekan road worthiness document pada asset data bernilai off the road'
		if(rwd=="OFF_THE_ROAD"){
			'Ambil nilai main premi rate dari excel'
			mainPremiRateValue = datafileTabInsurance.getValue(
						GlobalVariable.NumofColm, Rate)
						
			mainPremiRateValueArray = mainPremiRateValue.split(';',-1)
					
			if(GlobalVariable.Role=="Testing"){
				'Verify rate tidak terlock'
				WebUI.verifyElementNotHasAttribute(mainPremiRateObject, "disabled", GlobalVariable.TimeOut)
			}
			
			'Pengecekan field main premi rate di excel terisi'
			if (mainPremiRateValue.length() > 0) {
				'Pengecekan array pada field main premi rate tidak kosong'
				if ((mainPremiRateValueArray[(i - 1)]) != '') {
						'klik main premi rate object'
						WebUI.click(mainPremiRateObject)
						
						'Input main premi rate tahun ke i-1'
						WebUI.setText(mainPremiRateObject, mainPremiRateValueArray[(i-1)])
				}
			}
		}
		//Road worthiness document pada asset data bernilai on the road atau tidak ada attribute rwd pada asset attribute
		else if(rwd=="ON_THE_ROAD"||rwd==null){
			if(GlobalVariable.Role=="Testing"){
				'Verif rate terlock'
				WebUI.verifyElementHasAttribute(mainPremiRateObject, "disabled",GlobalVariable.TimeOut)
			}			
		}
		'declare flagloading, flagload'
		int flagLoading = 0,flagLoad=0
	
		'declare result'
		result = new HashMap<String,ArrayList>()
		
		'declare variableaddcov'
		ArrayList<WebElement> variableAddCov = driver.findElements(By.cssSelector("#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table > tbody:nth-child("+(i+1)+") > tr > td.text-left > div > div > label"))
		
		'declare countaddcov'
		countAddCov = variableAddCov.size()
		
		'declare addtcvgtype, addtpremirate, suminsuredamt, addtcvg'
		ArrayList<String> addtCvgType, addtPremiRate, sumInsuredAmt, addtCvg
		
		if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
			'Hashmap untuk ambil nilai additional premi rate, sum insured amount, dan main coverage typenya dari rule excel berdasarkan condition'
			result = CustomKeywords.'insuranceData.verifyAddtCvg.verifyAddtPremiRate'(sqlConnectionLOS, sqlConnectionFOU,appNo,selectedInscoBranch,selectedRegion,covAmt,WebUI.getAttribute(mainCoverageObject,'value'),WebUI.getText(yearNumObject))
			
			addtCvgType = result.get("AddtCvg")
			addtPremiRate = result.get("AddtRate")
			sumInsuredAmt = result.get("SumInsuredAmt")
			addtCvg = result.get("AddCvgList")
			
			'looping additional coverage'
			for(int addCovIndex = 1 ; addCovIndex <= countAddCov ; addCovIndex++){
				'modify label additional coverage per year'
				labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
					'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (addCovIndex + 2)) + ']/td[6]/div/div/label',
					true)
				
				'Verif additional coverage yang tampil pada confins sesuai dengan rule'
				if(WebUI.verifyMatch(CustomKeywords.'insuranceData.verifyAddtCvg.checkAddtCvgCode'(sqlConnectionLOS, WebUI.getText(labelAddCovPerYear)),addtCvg.get(addCovIndex-1), false)==false){
					'write to excel failed reason verify rule'
					writeFailedReasonVerifyRule()
					break
				}
			}
			
			if(GlobalVariable.FlagFailed == 1){
				break
			}
		}
		
		//AdditionalCoverage & Sum Insured Amount
		'Looping additional coverage & sum insured amount'
		for (int j = 1; j <= countAddCov; j++) {
			'declare flagload'
			flagLoad=0
			
			'declare countsuminsuredamount'
			int countSumInsuredAmount = 0
			
			'modify addcovyearcheckbox'
			addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/label/input',
				true)
	
			'modify labeladdcovperyear'
			labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/label',
				true)
			
			'Ambil nilai dari additional coverage per year num pada excel'
			addCovYearValue = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, addCovTableRow + j -flagLoading)
	
			addCovYearValueArray = addCovYearValue.split(';', -1)
	
			'Supaya checkbox bisa diklik'
			if ((i == 1) && (j == 1)) {
				'double click addcovyearcheckbox'
				WebUI.doubleClick(addCovYearCheckbox)
			}
			
			'Pengecekan jika label = loading'
			if (WebUI.getText(labelAddCovPerYear).equalsIgnoreCase('LOADING')) {
				flagLoading = 1
				flagLoad=1
			}
			
			if(flagLoad==0){
				'Pengecekan additional coverage field dan arraynya tidak kosong'
				if ((addCovYearValue.length() > 0) && ((addCovYearValueArray[((i - 1))]) != '')) {
					'Pengecekan nilai additional coverage dari stiap arraynya'
					if ((addCovYearValueArray[((i - 1))]).equalsIgnoreCase('Yes')) {
						'Jika belum tercentang additional coverage'
						if (WebUI.verifyElementNotChecked(addCovYearCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'centang additional coverage'
							WebUI.check(addCovYearCheckbox)
						}
					} else if ((addCovYearValueArray[((i - 1))]).equalsIgnoreCase('No')) {
						'Jika sudah tercentang additional coverage'
						if (WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'Uncentang additional coverage'
							WebUI.uncheck(addCovYearCheckbox)
						}
					}
				}
				
				'modify sum insured amount'
				modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
					'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[7]/div/div/select',
					true)
				
				'modify additional rate object'
				modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[8]/div/span/div/input",true)
		
				'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
				if (WebUI.getAttribute(modifyAddtRateObject, 'value', FailureHandling.OPTIONAL).contains(",")) {
					countSumInsuredAmount = 1
				}
				
				'Jika sum insured amount ada dan checkbox additional coverage tercentang'
				if ((countSumInsuredAmount == 1) && WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'Ambil nilai sum insured amount dari excel'
					SumInsuredValue = datafileTabInsurance.getValue(
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
			}
			
			'modify sum insured amount'
			modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
				'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[7]/div/div/select',
				true)
	
			'modify additional rate object'
			modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[8]/div/span/div/input",true)
			
			'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
			if (WebUI.getAttribute(modifyAddtRateObject, 'value', FailureHandling.OPTIONAL).contains(",")) {
				countSumInsuredAmount = 1
			}
			
			
			//Verif additional premi rate based on rule
			if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
				'Looping berdasarkan jumlah additional coverage type pada rule excel'
				for(int k = 0;k<addtCvgType.size();k++){
					'Verif additional coverage type confins sesuai dengan rule'
					if(WebUI.verifyMatch(CustomKeywords.'insuranceData.verifyAddtCvg.checkAddtCvgCode'(sqlConnectionLOS, WebUI.getText(labelAddCovPerYear)),addtCvgType.get(k), false, FailureHandling.OPTIONAL)){
						'Pengecekan jika terdapat sum insured amount'
						if(countSumInsuredAmount == 1){
							'Verif sum insured amount yang dipilih pada confins sesuai dengan rule'
							if(WebUI.verifyMatch(WebUI.getAttribute(modifySumInsuredAmount,'value', FailureHandling.OPTIONAL),sumInsuredAmt.get(k),false, FailureHandling.OPTIONAL)){
								'Verif additional premi rate sesuai dengan nilai dari rule'
								if(WebUI.verifyEqual(Long.parseLong(WebUI.getAttribute(modifyAddtRateObject,'value').replace(",","")),Long.parseLong(addtPremiRate.get(k)))==false){
									'write to excel failed reason verify rule'
									writeFailedReasonVerifyRule()
								}
								break
							}
						}
						//jika tidak terdapt sum insured amount
						else if(countSumInsuredAmount==0 && WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
							'Verif additional premi rate sesuai dengan nilai dari rule'
							if(WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(modifyAddtRateObject,'value').replace(" %","")),Double.parseDouble(addtPremiRate.get(k)))==false){
								'write to excel failed reason verify rule'
								writeFailedReasonVerifyRule()
							}
							break
						}
					}
				}
			}
			'Pengecekan road worthiness document bernilai off the road'
			if(rwd=="OFF_THE_ROAD"){
							
				'Ambil nilai additional premi rate dari excel'
				AddtRateValue = datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, AddRate + j)
				
				AddtRateValueArray = AddtRateValue.split(';', -1)
							
				if(GlobalVariable.Role=="Testing"){
					
					if(flagLoad==0){
						'Verif rate tidak terlock'
						WebUI.verifyElementNotHasAttribute(modifyAddtRateObject, "disabled", GlobalVariable.TimeOut)
					}
				}
							
				'Pengecekan additional premi rate pada excel kosong atau tidak'
				if(AddtRateValue.length()>0){
					'Pengecekan additional premi rate array tidak kosong'
					if(AddtRateValueArray[i-1]!=""){
						'klik modify additional rate object'
						WebUI.click(modifyAddtRateObject)
									
						'Input Rate Additional Premi'
						WebUI.setText(modifyAddtRateObject, AddtRateValueArray[((i - 1))])
					}
				}
			}
			//Road worthiness document berniali on the road atau tidak ada attribute road worthiness document pada asset
			else if(rwd=="ON_THE_ROAD"||rwd==null){
				if(GlobalVariable.Role=="Testing"){
					
					if(flagLoad==0){
						'Verif rate terlock'
						WebUI.verifyElementHasAttribute(modifyAddtRateObject, "disabled", GlobalVariable.TimeOut)
					}
				}
			}
		}
	}
	
	'Klik calculate insurance'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
	
	'declare mainrate arraylist'
	ArrayList<String> MainRate = new ArrayList<String>()
	
	'declare additionalrate arraylist'
	ArrayList<String> AddtRate = new ArrayList<String>()
	
	'looping yearno'
	for (int i = 1; i <= count; i++) {
		'ambil nilai num of month'
		numOfMonthObj = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[4]",true)
		
		'get text num of month'
		Integer numofmonth = Integer.parseInt(WebUI.getText(numOfMonthObj))
		
		//Main Premi Rate
		mainPremiRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]/div/input",true)
		
		'get text mainrate'
		MainRate.add(WebUI.getAttribute(mainPremiRateObject,'value').replace(" %","").replace(",",""))
		
		'looping additional cvg'
		for (int j = 1; j <= countAddCov; j++) {
			
			'Ambil nilai string label jenis additional coverage'
			labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/label',
				true)
			
			'get label additional coverage'
			String labelAddCov = WebUI.getText(labelAddCovPerYear)
			
			'modify addcovyearcheckbox'
			addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/label/input',
				true)
			
			'ambil nilai additional coverage'
			modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[8]/div/span/div/input",true)
			
			'Pengecekan jika checkbox additional cvg tercentang dan jenisnya merupakan tpl, kdup, tjhtp atau bukan'
			if(WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&!labelAddCov.equalsIgnoreCase("TPL")&&!labelAddCov.equalsIgnoreCase("TANGGUNG JAWAB HUKUM TERHADAP PENUMPANG")
				&&!labelAddCov.equalsIgnoreCase("KECELAKAAN DIRI UNTUK PENUMPANG")){
				
				'Simpan nilai rate additional cvg'
				AddtRate.add(Double.parseDouble(WebUI.getAttribute(modifyAddtRateObject,'value').replace(" %","").replace(",","")))
			}
			else if(WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&(labelAddCov.equalsIgnoreCase("TPL")||labelAddCov.equalsIgnoreCase("TANGGUNG JAWAB HUKUM TERHADAP PENUMPANG")
				||labelAddCov.equalsIgnoreCase("KECELAKAAN DIRI UNTUK PENUMPANG"))){
				
				'Simpan nilai rate additional cvg'
				AddtRate.add(Math.round(Double.parseDouble(WebUI.getAttribute(modifyAddtRateObject,'value').replace(" %","").replace(",",""))*(numofmonth/12)))
			
			}
		}
	}
	
	GlobalVariable.MainPremiRate = MainRate
	GlobalVariable.AdditionalPremiRate = AddtRate

	if(GlobalVariable.FlagFailed==0){
		'cek alert'
		GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '8.TabInsuranceData')
	}
	
	'declare totalresult'
	ArrayList<WebElement> totalResult
	
	'declare totalpremitocustresult'
	BigDecimal totalPremitoCustResult

	if(GlobalVariable.Role=="Testing"){
		
		'keyword untuk verify tabel hasil generate insurance (main premi, additional premi,total premi per year, total premi'
		totalResult = CustomKeywords.'insuranceData.verifyInsuranceData.verifyInsuranceCvgResult'()
		
		'ambil nilai total main premi dari confins'
		String textTotalMainPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_TotalMainPremium')).replace(
			',', '')
		
		'ambil nilai total additional premi dari confins'
		String textTotalAdditionalPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_TotalAdditionalPremium')).replace(
			',', '')
		
		'ambil nilai total fee dari confins'
		String textTotalFeeAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_TotalFee')).replace(
			'.00', '')
		
		'ambil nilai total premi to customer dari confins'
		String textTotalPremitoCust = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomer')).replace(
			',', '')
		
		'get perhitungan total fee dari excel'
		totalFeeResult = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 87)
		
		'Perhitungan total premi to customer'
		totalPremitoCustResult = (((totalResult[0]) + (totalResult[1])) + Long.parseLong(totalFeeResult.replace(',','')))
		
		'Verif total main premi sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalMainPremiAmt, String.format('%.2f', totalResult[0]), false))
		
		'Verif total additional premi sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalAdditionalPremiAmt, String.format('%.2f', totalResult[1]), false))
		
		'Verif total fee sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalFeeAmt, totalFeeResult, false))
		
		'Verif total premi to customer sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalPremitoCust, String.format('%.2f', totalPremitoCustResult), false))
		
		'Jika tidak ada paid by mf'
		if(totalResult[2]==0){
			'Input diskon'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
				datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, TotalPremium+1))
		}
	}
	
	'Jika tidak ada paid by mf'
	if (counterPaidByMF == 0) {
		'Input diskon'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
			datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, TotalPremium+1))
	}
	'declare textdisocuntamt'
	String textDiscountAmt
	
	'ambil nilai diskon pada confins'
	textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
		'value').replace(',', '')
		
	'get capitalize amount from confins'
	String textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),
			'value').replace(',', '')
			
	if(GlobalVariable.Role=="Testing"){
		'Ambil nilai total premi to customer after discount dari confins'
		String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(
			',', '')
		
		'ambil nilai diskon pada confins'
		textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
			'value').replace(',', '')
		
		'parsing discount amount ke dalam long'
		BigDecimal discountAmt = Long.parseLong(textDiscountAmt)
		
		'Perhitungan total premi to customer after discount'
		totalPremitoCustAftDiscountResult = (totalPremitoCustResult - discountAmt)
		
		'Verif total premi to customer after discount sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format('%.2f', totalPremitoCustAftDiscountResult), false))
		
		'Jika capitalize amount tidak bernilai 0'
		if ((totalResult[3]) != 0) {
			'Verif capitalize amount sesuai perhitungan'
			checkVerifyEqualOrMatch(WebUI.verifyMatch(textCapitalizeAmount, ((totalResult[3]) + Long.parseLong(totalFeeResult.replace(',',''))).toString(), false))
		}
		//Jika capitalize amount bernilai 0
		else if(totalResult[3]==0) {
			'Verif capitalize amount sesuai perhitungan'
			checkVerifyEqualOrMatch(WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]).toString(), false))
		}
		'Jika ada paid by mf'
		if(totalResult[2]==1){
			'write to excel discount amount'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
				GlobalVariable.NumofColm - 1, textDiscountAmt)
		}
	}
	
	'Jika ada paid by mf'
	if (counterPaidByMF == 1) {
		'Write to excel discount amount'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
			GlobalVariable.NumofColm - 1, textDiscountAmt)
	}
	
	'write to excel capitalize amount'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+6-1,
		GlobalVariable.NumofColm - 1, textCapitalizeAmount)
}
//Jika cap insurance setting bernilai partial
else if (capinssetting=="PARTIAL"){
	'call tc tab insurance data multifinance partial capitalize'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceDataMultifinancePartialCapitalize'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}
	
'get text total insurance'
GlobalVariable.TotalInsurance = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/TotalInsurance'))
	
'get text insurance capitalize amount'
GlobalVariable.InsuranceCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
		'value', FailureHandling.OPTIONAL).replace(",","")

Integer cptlzAmount = Integer.parseInt(GlobalVariable.InsuranceCapitalizeAmount)

'Mengambil nilai row keberapa dimulai data capitalize section Capitalize if GS_Value Partial pada excel'
def capPartialRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Capitalize if GS_Value Partial') +
1

'write to excel capitalize amount'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '8.TabInsuranceData', capPartialRow+1,
	GlobalVariable.NumofColm - 1, cptlzAmount)

	
public writeFailedReasonVerifyRule(){
		
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
	
	GlobalVariable.FlagFailed = 1
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
		GlobalVariable.FlagFailed=1
	}
}