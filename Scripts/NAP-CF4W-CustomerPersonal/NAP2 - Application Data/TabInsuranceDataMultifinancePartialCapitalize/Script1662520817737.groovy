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

'koneksi db los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi db fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

def textCapitalizeAmount, totalFeeResult

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData')

'Inisialisasi Variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

'declare arraysuminsured'
ArrayList<String> arraysuminsured = new ArrayList<>()

'declare arrayaddpremi'
ArrayList<String> arrayaddpremi = new ArrayList<>()

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
int count = variable.size()

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/span_AppNo'))

'Ambil string opsi yang dipilih pada dropdownlist insco branch name excel'
selectedInscoBranch = datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 26)

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

'declare variableaddcovall'
ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

'Mengambil dan menyimpan jumlah additional coverage'
int countAddCov = variableAddCovAll.size()

'get region from confins'
String selectedRegion = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_AssetRegionMF'),'value')

'get coverage amount from confins'
String covAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")

'Ambil nilai string road worthiness document dari db'
String rwd = CustomKeywords.'assetData.checkAssetRoadWorthinessDoc.checkRWD'(sqlConnectionLOS,appNo)

'Looping data tabel insurance untuk input data'
for (int i = 1; i <= count; i++) {
	
	//modify object year num
	yearNumObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/td_YearNo'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]",true)
	
	if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
		'modify object sum insured percentage'
		sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[4]/div/input', true)
		
		'Ambil nilai sum insured percent dari confins'
		sumInsuredPercentValue = WebUI.getAttribute(sumInsuredPercentObject,'value').replace(" %","")
		
		'Membaca rule excel untuk menentukan year num dan default sum insured percentage'
		HashMap<String, ArrayList> resultSumInsured = CustomKeywords.'insuranceData.verifySumInsured.verifySumInsuredMainCov'(sqlConnectionLOS, sqlConnectionFOU,appNo,selectedInscoBranch)
		
		'declare yearno,suminsuredpercentage'
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
		
	//Paid By
	paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[1]/div/select', true)

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
	
	if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true,GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
		counterPaidByMF = 1
	}
	
	//Sum insured (%)
	sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[4]/div/input', true)

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
	mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),
		'xpath', 'equals', ('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[1]/td[5]/span[1]/select',
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
			WebUI.selectOptionByLabel(mainCoverageObject, '(?i)' + (mainCoverageValueArray[(i - 1)]), true)
			
			WebUI.delay(3)
		}
	}
	
	//Main Premi Rate
	mainPremiRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[7]/div/input",true)
	
	//Verif Main Premi Rate Based on Rule
	if(GlobalVariable.Role=="Testing"  && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
		'Mencari nilai main premi rate berdasarkan kondisi-kondisi pada rule excel'
		HashMap<String,ArrayList> resultMainCvg = CustomKeywords.'insuranceData.verifyMainCvg.verifyMainPremiRate'(sqlConnectionLOS, sqlConnectionFOU,appNo,selectedInscoBranch,selectedRegion,covAmt)
		
		'modify random object'
		modifyRandomObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/testobject'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[2]/td[5]",true)
			
		'klik modify random object'
		WebUI.click(modifyRandomObject)
		
		'Ambil nilai main premi rate dari confins'
		String mainPremiVal = WebUI.getAttribute(mainPremiRateObject,'value').replace(" %","")
		
		'declare maincvgtype, mainpremirate arraylist'
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
	
	//Pengecekan road worthiness doc bernilai off the road
	if(rwd=="OFF_THE_ROAD"){
		'Ambil nilai main premi rate dari excel'
		mainPremiRateValue = datafileTabInsurance.getValue(
						GlobalVariable.NumofColm, Rate)
						
		mainPremiRateValueArray = mainPremiRateValue.split(';',-1)
					
		if(GlobalVariable.Role=="Testing"){
			'Verif rate tidak terlock'
			WebUI.verifyElementNotHasAttribute(mainPremiRateObject, "disabled", GlobalVariable.TimeOut)
		}
		'Pengecekan field main premi rate di excel terisi'
		if (mainPremiRateValue.length() > 0) {
			'Pengecekan array pada field main premi rate tidak kosong'
			if ((mainPremiRateValueArray[(i - 1)]) != '') {
					'Klik main premi rate object'
					WebUI.click(mainPremiRateObject)
					'Input main premi rate tahun ke i-1'
					WebUI.setText(mainPremiRateObject, mainPremiRateValueArray[(i-1)])
			}
		}
	}
	//Road worthiness doc bernilai on the road atau tidak ada attribute road wortiness document pada asset attribute
	else if(rwd=="ON_THE_ROAD"||rwd==null){
		if(GlobalVariable.Role=="Testing"){
				'Verif rate terlock'
				WebUI.verifyElementHasAttribute(mainPremiRateObject, "disabled", GlobalVariable.TimeOut)
		}			
	}
	'declare flagloading, flagload'
	int flagLoading = 0, flagLoad=0
	
	'declare result'
	result = new HashMap<String,ArrayList>()
	
	'declare variableaddcov'
	ArrayList<WebElement> variableAddCov = driver.findElements(By.cssSelector("#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table > tbody:nth-child("+(i+1)+") > tr > td.text-left > div > div > label"))
	
	'declare countaddcov'
	countAddCov = variableAddCov.size()
	
	'declare arraylist addtcvgtype, addtpremirate, suminsuredamt, addcvg'
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
			'modify label add cov per year'
			labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (addCovIndex + 2)) + ']/td[5]/div/div/label',
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
		addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
			'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label/input',
			true)

		'modify labeladdcovperyear'
		labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
			'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label',
			true)
		
		'Ambil nilai dari additional coverage per year num pada excel'
		addCovYearValue = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, addCovTableRow + j -flagLoading)

		addCovYearValueArray = addCovYearValue.split(';', -1)

		'Supaya checkbox bisa diklik'
		if ((i == 1) && (j == 1)) {
			'double klik add cov year checkbox'
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
			modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
				'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/select',
				true)
	
			'modify additional rate object'
			modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/span/div/input",true)
		
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
		modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
			'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/select',
			true)

		'modify additional rate object'
		modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/span/div/input",true)
		
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
						if(WebUI.verifyMatch(WebUI.getAttribute(modifySumInsuredAmount,'value'),sumInsuredAmt.get(k),false, FailureHandling.OPTIONAL)){
							'Verif additional premi rate sesuai dengan nilai dari rule'
							if(WebUI.verifyEqual(Long.parseLong(WebUI.getAttribute(modifyAddtRateObject,'value').replace(",","")),Long.parseLong(addtPremiRate.get(k)))==false){
								'write to excel failed reason verify rule'
								writeFailedReasonVerifyRule()
							}
							break
						}
					}
					//jika tidak terdapt sum insured amount
					else{
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
					'klik modifyaddtrateobject'
					WebUI.click(modifyAddtRateObject)
									
					'Input Rate Additional Premi'
					WebUI.setText(modifyAddtRateObject, AddtRateValueArray[((i - 1))])
				}
			}
		}
		//road worthiness document bernilai on the road atau tidak ada attribute road worthiness document pada asset attribute
		else if(rwd=="ON_THE_ROAD"||rwd==null){
			if(GlobalVariable.Role=="Testing"){
				if(flagLoad==0){
					'verif rate terlock'
					WebUI.verifyElementHasAttribute(modifyAddtRateObject, "disabled", GlobalVariable.TimeOut)
				}
			}
		}
	}
}

'Mengambil nilai row keberapa dimulai data capitalize section Capitalize if GS_Value Partial pada excel'
def capPartialRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Capitalize if GS_Value Partial') +
1

'Pengecekan excel full capitalize amount bernilai yes/no'
if(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, capPartialRow+1)=="YES"){
	'Jika full capitalize amount pada confins belum tercentang'
	if(WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
		'Centang full capitalize amount'
		WebUI.check(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
	}
}
else if(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, capPartialRow+1)=="NO"){
	'Jika full capitalize amount pada confins sudah tercentang'
	if(WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
		'Uncentang full capitalize amounts'
		WebUI.uncheck(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
	}
}

'Klik calculate insurance'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))

'function additional rate to global variable'
AddRatetoGV(count,countAddCov)

if(GlobalVariable.FlagFailed==0){
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '8.TabInsuranceData')
}

'Pengecekan jika full capitalize amount pada confins tidak tercentang dan pada excel terisi nilai amountnya'
if(WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, capPartialRow+2).length()>0){
	'Input capitalize amount'
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, capPartialRow+2))
}
				
'declare totalresult'
ArrayList<WebElement> totalResult

'declare totalpremitocustresult'
BigDecimal totalPremitoCustResult

if(GlobalVariable.Role=="Testing"){
	
	'keyword untuk verify tabel hasil generate insurance (main premi, additional premi,total premi per year, total premi'
	totalResult = CustomKeywords.'insuranceData.verifyInsuranceData.verifyInsuranceCvgResultPartialCap'()
	
	'ambil nilai total main premi dari confins'
	String textTotalMainPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalMainPremium')).replace(
		',', '')
	
	'ambil nilai total additional premi dari confins'
	String textTotalAdditionalPremiAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalAdditionalPremium')).replace(
		',', '')
	
	'ambil nilai total fee dari confins'
	String textTotalFeeAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalFee')).replace(
		'.00', '')
	
	'ambil nilai total premi to customer dari confins'
	String textTotalPremitoCust = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomer')).replace(
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
	
	if(totalResult[2]==0){
		'Input diskon'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
			datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, TotalPremium+1))
	}
}

if (counterPaidByMF == 0) {
	'Input diskon'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
		datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, TotalPremium+1))
}

String textDiscountAmt

'ambil nilai diskon pada confins'
textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
	'value').replace(',', '')
	
if(GlobalVariable.Role=="Testing"){
	'Ambil nilai total premi to customer after discount dari confins'
	String textTotalPremitoCustAftDisc = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_TotalPremiumtoCustomerAfterDiscount')).replace(
		',', '')
	
	'ambil nilai diskon pada confins'
	textDiscountAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),
		'value').replace(',', '')
	
	'parsing discountamt ke bentuk long'
	BigDecimal discountAmt = Long.parseLong(textDiscountAmt)
	
	'Perhitungan total premi to customer after discount'
	totalPremitoCustAftDiscountResult = (totalPremitoCustResult - discountAmt)
	
	'Verif total premi to customer after discount sesuai perhitungan'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textTotalPremitoCustAftDisc, String.format('%.2f', totalPremitoCustAftDiscountResult), false))
	
	'get capitalize amount from confins'
	textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),
		'value').replace(',', '')
	
	'Verif untuk capitalize bukan 0 dan ada paid by mf'
	if ((totalResult[3]) != 0 && totalResult[2]==1) {
		'Verify capitalize amount sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCapitalizeAmount, ((totalResult[3]) + Long.parseLong(totalFeeResult.replace(',',''))).toString(), false))
		
		'write to excel discount amount'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
			GlobalVariable.NumofColm - 1, textDiscountAmt)
	}
	// Verif untuk capitalizze bukan 0 dan tidak ada paid by mf
	else if((totalResult[3]) != 0 && totalResult[2]==0){
		'Klik 2x untuk refresh capitalize amount di confins'
		WebUI.doubleClick(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
		
		'Mengambil nilai capitalize amount dari confins'
		textCapitalizeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Capitalize Amount_insCpltzAmt'),
			'value').replace(',', '')
			
		'Verify capitalize amount sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCapitalizeAmount, ((totalResult[3]) + Long.parseLong(totalFeeResult.replace(',','')) - discountAmt).toString(), false))
	}
	//Verif untuk capitalize bernilai 0, ada paid by mf, dan full capitalize tercentang
	else if((totalResult[3]) == 0 && totalResult[2]==1 && WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
		'Verify capitalize amount sesuai perhitungan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textCapitalizeAmount, (totalResult[3]+Long.parseLong(totalFeeResult.replace(',',''))).toString(),false))
	}
}

'function untuk merefresh nilai capitalize amount'
refreshCapitalize(counterPaidByMF)

'Pengecekan jika ada paid by mf'
if (counterPaidByMF == 1) {
	'Write to excel discount amount'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+2-1,
		GlobalVariable.NumofColm - 1, textDiscountAmt)
}

'write to excel capitalize amount'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', TotalPremium+6-1,
	GlobalVariable.NumofColm - 1, textCapitalizeAmount)

public writeFailedReasonVerifyRule(){
		
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
	
	GlobalVariable.FlagFailed=1
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
		GlobalVariable.FlagFailed=1
	}
}

public AddRatetoGV(int count, int countAddCov){
	'declare mainrate'
	ArrayList<String> MainRate = new ArrayList<String>()
	'declare addtrate'
	ArrayList<String> AddtRate = new ArrayList<String>()
	
	'looping yearno'
	for (int i = 1; i <= count; i++) {
		
		'ambil nilai num of month'
		numOfMonthObj = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[3]",true)
		
		'get numofmonth from confins'
		Integer numofmonth = Integer.parseInt(WebUI.getText(numOfMonthObj))
		
		//Main Premi Rate
		mainPremiRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Rate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[7]/div/input",true)
		
		'get mainpremirate from confins'
		MainRate.add(WebUI.getAttribute(mainPremiRateObject,'value').replace(" %","").replace(",",""))
		
		'looping additional cvg'
		for (int j = 1; j <= countAddCov; j++) {
			
			'Ambil nilai string label jenis additional coverage'
			labelAddCovPerYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/label_AddCovPerYear'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label',
				true)
			
			'get labeladdcovperyear from confins'
			String labelAddCov = WebUI.getText(labelAddCovPerYear)
			
			'modify addcovyearcheckbox'
			addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
				'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label/input',
				true)
			
			'ambil nilai additional coverage rate'
			modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/span/div/input",true)
			
			'Pengecekan jika checkbox additional cvg tercentang dan jenisnya merupakan tpl, kdup, tjhtp atau bukan'
			if(WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&!labelAddCov.equalsIgnoreCase("TPL")&&!labelAddCov.equalsIgnoreCase("TANGGUNG JAWAB HUKUM TERHADAP PENUMPANG")
				&&!labelAddCov.equalsIgnoreCase("KECELAKAAN DIRI UNTUK PENUMPANG")){
				
				'Simpan nilai rate additional cvg'
				AddtRate.add(Double.parseDouble(WebUI.getAttribute(modifyAddtRateObject,'value').replace(" %","").replace(",","")))
			}
			else if(WebUI.verifyElementChecked(addCovYearCheckbox, GlobalVariable.TimeOut,FailureHandling.OPTIONAL)&&(labelAddCov.equalsIgnoreCase("TPL")||labelAddCov.equalsIgnoreCase("TANGGUNG JAWAB HUKUM TERHADAP PENUMPANG")
				||labelAddCov.equalsIgnoreCase("KECELAKAAN DIRI UNTUK PENUMPANG"))){
				
				'Simpan nilai rate additional cvg'
				AddtRate.add(Double.parseDouble(WebUI.getAttribute(modifyAddtRateObject,'value').replace(" %","").replace(",",""))*(numofmonth/12))
			}
		}
	}
	
	GlobalVariable.MainPremiRate = MainRate
	GlobalVariable.AdditionalPremiRate = AddtRate
}

public refreshCapitalize(int counterPaidByMF){
	if (counterPaidByMF == 0 && WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/CapitalizeInsuranceAmount'),
		'value', FailureHandling.OPTIONAL)!='0' && GlobalVariable.Role=="Data Entry") {
		'Klik 2x untuk refresh capitalize amount di confins'
		WebUI.doubleClick(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'))
		
	}
}