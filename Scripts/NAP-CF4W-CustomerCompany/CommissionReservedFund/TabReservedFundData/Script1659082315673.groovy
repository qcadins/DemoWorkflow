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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'Koneksi database'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

GlobalVariable.FlagFailed = 0

'Arraylist untuk menampung total amount dari allocate rsv (upping rate, admin fee, dsb)'
ArrayList<Double> TotalAllocateRsvAmt = new ArrayList<Double>()

'declare excelPathReservedFund'
excelPathReservedFund = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/CommissionReservedFund/TabReservedFundData'

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Arraylist untuk menampung remaining info reserved fund sebelum calculate'
ArrayList<WebElement> remainingInfoRsv = new ArrayList<WebElement>()

if(GlobalVariable.RoleCompany=="Testing"){
	
	'Arraylist untuk menampung remaining info commission'
	ArrayList<WebElement> remainingInfoCom = new ArrayList<WebElement>()
	
	'Arraylist untuk menampung income info'
	ArrayList<WebElement> varIncomeInfo = driver.findElements(By.cssSelector('#viewIncomeInfo label'))
	
	remainingInfoCom = GlobalVariable.ComRemainingInfoAmt
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countIncomeInfo = varIncomeInfo.size() / 2
	
	'Looping income info'
	for (int i = 1; i < countIncomeInfo; i++) {
		
		TotalAllocateRsvAmt.add(0.00)
		
		modifyRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount dari confins'
		String textRemainingInfoAmt = WebUI.getText(modifyRemainingInfoAmt).replace(',', '')
	
		'cek remaining info after calculate tab commission = remaining info reserved fund'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingInfoAmt,String.format('%.2f', remainingInfoCom[(i - 1)]),false), '13.TabReservedFundData',
			GlobalVariable.NumofColm)

		'Tambahkan data remaining info ke arraylist untuk keperluan verifikasi setelah calculate'
		remainingInfoRsv.add(textRemainingInfoAmt)
	}
}

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/span_appNo'))

//Pengecekan app last step sementara dilakukan dengan pengecekan dari db karena pengecekan melalui view confins masih issue.
String appLastStep = CustomKeywords.'dbConnection.checkStep.checkLastStep'(sqlConnectionLOS, appNo)

if(!appLastStep.equalsIgnoreCase("COM") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

'Ambil nilai string text lobcode dari db los berdasarkan appNo'
String lobCode = CustomKeywords.'commissionReserveFundData.verifRuleReserveFundData.checkLOBCode'(sqlConnectionLOS, appNo)

'HashMap untuk menampung alloc from, alloc amount, dan alloc behaviour dari excel rule'
HashMap<String,ArrayList> resultVerifRule = CustomKeywords.'commissionReserveFundData.verifRuleReserveFundData.checkReserveFundBasedOnRule'(lobCode)

'Arraylist untuk menampung nilai array alloc from'
ArrayList<String> allocFrom = resultVerifRule.get("From")

'Arraylist untuk menampung nilai array alloc amount default'
ArrayList<String> defAllocAmt = resultVerifRule.get("Amt")

'Arraylist untuk menampung nilai array alloc behaviour'
ArrayList<String> allocBhv = resultVerifRule.get("Bhv")

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 'Reserve Fund Amt')+2

'Arraylist untuk menampung remaining info'
ArrayList<WebElement> varRemainingBfrCalculate = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))

'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countRemainingInfoBfrCalculate = varRemainingBfrCalculate.size() / 2

modifyRemainingAllocatedAmountBfrCalculate = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
	'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfoBfrCalculate) + ']/div[2]/label', true)

'Looping data allocation reserve fund'
for(int i = 0;i<allocFrom.size();i++){

	inputAlloc = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/input_PROMO_InterestIncome'),'xpath','equals',"//input[@id='ReservedFundAmt"+i+"']", true)
 
	allocFromSectionObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/h4_RSVAllocFrom'),'xpath','equals',"//*[@id='reserved-fund-tab']/reserved-fund/div/div/div/form/div/div[1]/div["+(i+1)+"]/lib-ucsubsection/div/form/div/h4", true)
	
	'Ambil nilai string text nama section allocation pada confins'
	String textAllocFromSection = WebUI.getText(allocFromSectionObject)
	
	if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckRuleCompany=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
		'Verify allocation from yang tampil pada confins sesuai dengan rule file'
		if(WebUI.verifyMatch(textAllocFromSection, ".*"+allocFrom[i].replace("_"," ")+".*",true)==false){
						
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)
			
			GlobalVariable.FlagFailed=1
		}
	}
	
	BigDecimal remainingInfoAmt
	
	Integer Allocindex
	
	'Looping remaining info'
	for(int j =1;j<=countRemainingInfoBfrCalculate;j++){
		
		modifyObjectRemainingInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingInfo'),'xpath','equals',(('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[1]/label'),true)
		
		modifyObjectRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),'xpath','equals',(('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[2]/label'), true)
		
		'Ambil nilai string text remaining info'
		String textRemainingInfo = WebUI.getText(modifyObjectRemainingInfo)
		
		'Pengecekan jika text remaining info pada confins sesuai dengan nama section allocation'
		if(textRemainingInfo == textAllocFromSection){
			'Ambil nilai amount remaining info'
			String textRemainingInfoAmt = WebUI.getText(modifyObjectRemainingInfoAmt).replace(",","")
			remainingInfoAmt = Double.parseDouble(textRemainingInfoAmt)
			
			Allocindex = j
			
			break
		}
	}
	'Ambil nilai amount dari allocation'
	String inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
	'Pengecekan remaining info bernilai 0 atau tidak'
	if(remainingInfoAmt>0){
		
		if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckRuleCompany=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
			'Verify amount yang tampil di confins sesuai dengan default amount pada rule file '
			if(WebUI.verifyMatch(inputAllocAmt.replace(",",""),defAllocAmt[i],false)==false){
								
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)
				
				GlobalVariable.FlagFailed=1
			}
		}
		
		'Pengecekan editable/tidaknya field-field allocation pada confins sesuai behaviour pada rule file'
		if(allocBhv[i].equalsIgnoreCase("def")){
			if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckRuleCompany=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
				'Verify field bisa diisi'
				if(WebUI.verifyElementNotHasAttribute(inputAlloc,'readonly', GlobalVariable.TimeOut)==false){
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)
					
					GlobalVariable.FlagFailed=1
				}
			}
			
			'Input Alloc Reserved Fund Amount'
			WebUI.setText(inputAlloc, findTestData(excelPathReservedFund).getValue(
				GlobalVariable.NumofColm, rsvAmtRow+i), FailureHandling.OPTIONAL)
		}
		else if(allocBhv[i].equalsIgnoreCase("lock")){
			
			if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckRuleCompany=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
				'Verify field tidak bisa diisi'
				if(WebUI.verifyElementHasAttribute(inputAlloc,'readonly', GlobalVariable.TimeOut)==false){
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)
					
					GlobalVariable.FlagFailed=1
				}
			}
			
			'write to excel locked reserve fund amount'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
				rsvAmtRow+i-1, GlobalVariable.NumofColm-1, Integer.parseInt(inputAllocAmt))
		}
	}
	else{
		
		'Verify field tidak bisa diisi'
		WebUI.verifyElementHasAttribute(inputAlloc,'readonly', GlobalVariable.TimeOut)
		
		'write to excel locked reserve fund amount'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
			rsvAmtRow+i-1, GlobalVariable.NumofColm-1, Integer.parseInt(inputAllocAmt))
	}
	
	inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
	TotalAllocateRsvAmt.set(Allocindex - 1, Double.parseDouble(inputAllocAmt.replace(",","")))

//	if(GlobalVariable.RoleCompany=="Testing"){
//		inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
//		'Tambahkan amount masing-masing allocation untuk perhitungan total reserved fund amount'
//		totalAmt+=Double.parseDouble(inputAllocAmt.replace(",",""))
//	}
}

'Klik Button Calculate'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Calculate'))

String alert

if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/alert_Commission'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	alert = WebUI.getText(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/alert_Commission'),FailureHandling.OPTIONAL)
}
else if(alert==null){
	alert = "def"
}

if(alert.toLowerCase().contains("Must Be Less Than".toLowerCase())||WebUI.verifyElementPresent(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/error_maxnumber'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonCalculateGagal'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonCalculateGagal)

	'Klik cancel'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Cancel'))
	
	GlobalVariable.IsDataCancel = 1
	
	GlobalVariable.FlagFailed = 1
}

if(GlobalVariable.RoleCompany=="Testing"){
	'get nilai total reserved fund amount from excel'
	BigDecimal totalAmt = Long.parseLong(findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, rsvAmtRow+allocFrom.size()).replace(",",""))
	
	'Menyimpan nilai Total Reserved Fund Amount dari Web CONFINS'
	String totalReservedFundAmt = WebUI.getText(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/label_TotalReservedFundAmt')).replace(
		',', '').replace('.00', '')
	
	'Verifikasi hasil perhitungan total reserved fund amount pada excel sesuai dengan nilai total reserved fund amount dari web'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(totalReservedFundAmt, totalAmt.toString(), false), '13.TabReservedFundData',
		 GlobalVariable.NumofColm)
	
	'get data file path simulasi'
	def datafilepathsim = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathSimulasiFinancial)
	
	'write rsv amt'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',8,7,Double.parseDouble(totalReservedFundAmt))
	
	CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFileWithRefreshVal'(datafilepathsim)
	
	'get nilai gross yield from simulation'
	GrossYieldVal = findTestData('Simulasi/Simulasi Gross Yield').getValue(10, 84)
	
	'verify gross yield confins x excel'
	checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(WebUI.getText(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_GrossYield')).replace("%",""))), Math.round(Double.parseDouble(GrossYieldVal))), '13.TabReservedFundData',
			GlobalVariable.NumofColm)
	
	'Arraylist untuk menampung remaining info'
	ArrayList<WebElement> varRemainingInfo = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countRemainingInfo = varRemainingInfo.size() / 2
	
	modifyRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
		'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfo) + ']/div[2]/label', true)
	
	'Ambil nilai remaining allocated amount stelah calculate di reserve fund'
	String textRemainingAllocatedAmount = WebUI.getText(modifyRemainingAllocatedAmount).replace(',', '')
	
	'verif remaining allocated amount = remaining allocated amount (after calc di comision) - nilai yg dibagina di reserve fund'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingAllocatedAmount, String.format('%.2f', GlobalVariable.RemainingAllocatedAmt - totalAmt),
		false), '13.TabReservedFundData',
		GlobalVariable.NumofColm)
	
	'Looping remaining info amount setelah calculate'
	for (int i = 1; i < countRemainingInfo; i++) {
		modifyRemainingInfoAmtAftCal = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount'
		String textRemainingInfoAmtAftCal = WebUI.getText(modifyRemainingInfoAmtAftCal).replace(',', '')
	
		'verifikasi setelah calculate di tab reserve fund, nilai remaining info berubah mengikuti nilai yang dibagikan di rsv'
		checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(textRemainingInfoAmtAftCal), Double.parseDouble(remainingInfoRsv[(i - 1)])-TotalAllocateRsvAmt[(i-1)]), '13.TabReservedFundData',
			GlobalVariable.NumofColm)
		
	}
}

WebUI.delay(5)

'Klik save'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 4))

if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '13.TabReservedFundData')
}

if(GlobalVariable.FlagFailed==0 ){
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
		findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/alert_Submit'), GlobalVariable.NumofColm, '13.TabReservedFundData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '13.TabReservedFundData')
	}
}



'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
if (WebUI.verifyElementPresent(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Cancel'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'Klik cancel'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Cancel'))

	GlobalVariable.IsDataCancel = 1
	
	'Pengecekan jika new consumer finance belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
	}
}else{
	if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
		'call test case reserved fund datastore db verif'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
}
	
public checkVerifyEqualOrMatch(Boolean isMatch, String sheetname, int numofcolm){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
	
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'(sheetname, numofcolm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
		GlobalVariable.FlagFailed=1
	}
}