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
import com.kms.katalon.core.testdata.reader.ExcelFactory

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'Koneksi database'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

GlobalVariable.FlagFailed = 0

'declare datafileReservedFund'
datafileReservedFund = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData')

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Arraylist untuk menampung remaining info reserved fund sebelum calculate'
ArrayList<WebElement> remainingInfoRsv = new ArrayList<WebElement>()

if(GlobalVariable.Role=="Testing"){
	
	'Arraylist untuk menampung remaining info commission'
	ArrayList<WebElement> remainingInfoCom = new ArrayList<WebElement>()
	
	'Arraylist untuk menampung income info'
	ArrayList<WebElement> varIncomeInfo = driver.findElements(By.cssSelector('#viewIncomeInfo label'))
	
	remainingInfoCom = GlobalVariable.ComRemainingInfoAmt
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countIncomeInfo = varIncomeInfo.size() / 2
	
	'Looping income info'
	for (int i = 1; i < countIncomeInfo; i++) {
		
		'modify remaining info amt'
		modifyRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount dari confins'
		String textRemainingInfoAmt = WebUI.getText(modifyRemainingInfoAmt).replace(',', '')
	
		'cek remaining info after calculate tab commission = remaining info reserved fund'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingInfoAmt,String.format('%.2f', remainingInfoCom[(i - 1)]),false), '14.TabReservedFundData',
			GlobalVariable.NumofColm)

		'Tambahkan data remaining info ke arraylist untuk keperluan verifikasi setelah calculate'
		remainingInfoRsv.add(textRemainingInfoAmt)
	}
}

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/span_appNo'))

//Pengecekan app last step sementara dilakukan dengan pengecekan dari db karena pengecekan melalui view confins masih issue.
String appLastStep = CustomKeywords.'dbConnection.checkStep.checkLastStep'(sqlConnectionLOS, appNo)

'Pengecekn applaststep bukan com dan first time entry = yes'
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

//'declare totalamt'
//BigDecimal totalAmt = 0

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 'Reserve Fund Amt')+2

'Arraylist untuk menampung remaining info'
ArrayList<WebElement> varRemainingBfrCalculate = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))

'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countRemainingInfoBfrCalculate = varRemainingBfrCalculate.size() / 2

'modifyremainingallocatedamount before calculate'
modifyRemainingAllocatedAmountBfrCalculate = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
	'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfoBfrCalculate) + ']/div[2]/label', true)

'Looping data allocation reserve fund'
for(int i = 0;i<allocFrom.size();i++){

	'modify input alloc'
	inputAlloc = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InterestIncome'),'xpath','equals',"//input[@id='ReservedFundAmt"+i+"']", true)
 
	'modify allocfromsection'
	allocFromSectionObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/h4_RSVAllocFrom'),'xpath','equals',"//*[@id='reserved-fund-tab']/reserved-fund/div/div/div/form/div/div[1]/div["+(i+1)+"]/lib-ucsubsection/div/form/div/h4", true)
	
	'Ambil nilai string text nama section allocation pada confins'
	String textAllocFromSection = WebUI.getText(allocFromSectionObject)
	
	if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
		'Verify allocation from yang tampil pada confins sesuai dengan rule file'
		if(WebUI.verifyMatch(textAllocFromSection, ".*"+allocFrom[i].replace("_"," ")+".*",true)==false){
						
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
			
			GlobalVariable.FlagFailed=1
		}
	}
	
	'declare remaininginfoamt'
	BigDecimal remainingInfoAmt
	
	'Looping remaining info'
	for(int j =1;j<=countRemainingInfoBfrCalculate;j++){
		
		'modify remaining info'
		modifyObjectRemainingInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfo'),'xpath','equals',(('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[1]/label'),true)
		
		'modify remaining info amt'
		modifyObjectRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),'xpath','equals',(('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[2]/label'), true)
		
		'Ambil nilai string text remaining info'
		String textRemainingInfo = WebUI.getText(modifyObjectRemainingInfo)
		
		'Pengecekan jika text remaining info pada confins sesuai dengan nama section allocation'
		if(textRemainingInfo == textAllocFromSection){
			'Ambil nilai amount remaining info'
			String textRemainingInfoAmt = WebUI.getText(modifyObjectRemainingInfoAmt).replace(",","")
			
			'parsing double remaining info amt'
			remainingInfoAmt = Double.parseDouble(textRemainingInfoAmt)
			break
		}
	}
	'Ambil nilai amount dari allocation'
	String inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
	'Pengecekan remaining info bernilai 0 atau tidak'
	if(remainingInfoAmt>0){
		
		if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
			'Verify amount yang tampil di confins sesuai dengan default amount pada rule file '
			if(WebUI.verifyMatch(inputAllocAmt.replace(",",""),defAllocAmt[i],false)==false){
				
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
				
				GlobalVariable.FlagFailed=1
			}
		}
		
		'Pengecekan editable/tidaknya field-field allocation pada confins sesuai behaviour pada rule file'
		if(allocBhv[i].equalsIgnoreCase("def")){
			if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
				'Verify field bisa diisi'
				if(WebUI.verifyElementNotHasAttribute(inputAlloc,'readonly',2)==false){
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
					
					GlobalVariable.FlagFailed=1
				}
			}
			
			'Input Alloc Reserved Fund Amount'
			WebUI.setText(inputAlloc, datafileReservedFund.getValue(
				GlobalVariable.NumofColm, rsvAmtRow+i), FailureHandling.OPTIONAL)
		}
		else if(allocBhv[i].equalsIgnoreCase("lock")){
			
			if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
				'Verify field tidak bisa diisi'
				if(WebUI.verifyElementHasAttribute(inputAlloc,'readonly',2)==false){
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyRule)
					
					GlobalVariable.FlagFailed=1
				}	
			}
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				rsvAmtRow+i-1, GlobalVariable.NumofColm-1, Integer.parseInt(inputAllocAmt))
			
		}
	}
	else{
		
		'Verify field tidak bisa diisi'
		WebUI.verifyElementHasAttribute(inputAlloc,'readonly',2)
		
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
			rsvAmtRow+i-1, GlobalVariable.NumofColm-1, Integer.parseInt(inputAllocAmt))
	}
	
//	if(GlobalVariable.Role=="Testing"){
//		'get attribute dari inputalloc pada confins'
//		inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
//		'Tambahkan amount masing-masing allocation untuk perhitungan total reserved fund amount'
//		totalAmt+=Double.parseDouble(inputAllocAmt.replace(",",""))
//	}
}

'Klik Button Calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Calculate'))

String alert

if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/alert_Commission'),1,FailureHandling.OPTIONAL)){
	'get text alert jika muncul'
	alert = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/alert_Commission'),FailureHandling.OPTIONAL)
}
else if(alert==null){
	alert = "def"
}

if(alert.toLowerCase().contains("Must Be Less Than".toLowerCase())||WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/error_maxnumber'),2,FailureHandling.OPTIONAL)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonCalculateGagal'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonCalculateGagal)

	'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))
	
	GlobalVariable.FlagFailed = 1
}

if(GlobalVariable.Role=="Testing"){
	'get nilai total reserved fund amount from excel'
	BigDecimal totalAmt = Long.parseLong(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, rsvAmtRow+allocFrom.size()).replace(",",""))
	
	'Menyimpan nilai Total Reserved Fund Amount dari Web CONFINS'
	String totalReservedFundAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_TotalReservedFundAmt')).replace(
		',', '').replace('.00', '')
	
	'Verifikasi hasil perhitungan total reserved fund amount sesuai dnegan nilai total reserved fund amount dari web'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(totalReservedFundAmt, totalAmt.toString(), false), '14.TabReservedFundData',
		 GlobalVariable.NumofColm)
	
	'Arraylist untuk menampung remaining info'
	ArrayList<WebElement> varRemainingInfo = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countRemainingInfo = varRemainingInfo.size() / 2
	
	'modify remaining allocated amount'
	modifyRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
		'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfo) + ']/div[2]/label', true)
	
	'Ambil nilai remaining allocated amount stelah calculate di reserve fund'
	String textRemainingAllocatedAmount = WebUI.getText(modifyRemainingAllocatedAmount).replace(',', '')
	
	'verif remaining allocated amount = remaining allocated amount (after calc di comision) - nilai yg dibagina di reserve fund'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingAllocatedAmount, String.format('%.2f', GlobalVariable.RemainingAllocatedAmt - totalAmt),
		false), '14.TabReservedFundData',
		GlobalVariable.NumofColm)
	
	'Looping remaining info amount setelah calculate'
	for (int i = 1; i < countRemainingInfo; i++) {
		'modify remaininginfoamtaftcal'
		modifyRemainingInfoAmtAftCal = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount'
		String textRemainingInfoAmtAftCal = WebUI.getText(modifyRemainingInfoAmtAftCal).replace(',', '')
	
		'verifikasi setelah calculate di tab reserve fund, nilai remaining info tidak berubah (masih sesuai setelah save dari tab commission)'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textRemainingInfoAmtAftCal, remainingInfoRsv[(i - 1)], false), '14.TabReservedFundData',
			GlobalVariable.NumofColm)
		
	}
}

WebUI.delay(5)

//'Klik save'
//WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Save'))
//
//'get nilai iscompletemandatory dari excel'
//Integer iscompleteMandatory = Integer.parseInt(datafileReservedFund.getValue(GlobalVariable.NumofColm, 4))
//
//if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
//	'cek alert'
//	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '14.TabReservedFundData')
//}
//
//if(GlobalVariable.FlagFailed==0 ){
//	'Check save Process write to excel'
//	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
//		findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/alert_Submit'), GlobalVariable.NumofColm, '14.TabReservedFundData')
//	if(iscompleteMandatory==0){
//		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
//		'cek validasi'
//		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '14.TabReservedFundData')
//	}
//}
//
//if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckVerifStoreDBPersonal=="Yes"){
//	'call test case reserved fund datastore db verif'
//	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/TabReservedFundDataStoreDBVerif'),
//			[:], FailureHandling.CONTINUE_ON_FAILURE)
//}
//
//'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
//if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'), 
//    5, FailureHandling.OPTIONAL)) {
//    'Klik cancel'
//    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))
//
//    'Pengecekan jika new consumer finance belum diexpand'
//    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
//        'Klik new consumer finance'
//        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
//    }
//} 
	
public checkVerifyEqualOrMatch(Boolean isMatch, String sheetname, int numofcolm){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname,
					0, numofcolm-1, GlobalVariable.StatusFailed)
	
		'write to excel reason failed verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname,
					1, numofcolm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
		GlobalVariable.FlagFailed=1
	}
}