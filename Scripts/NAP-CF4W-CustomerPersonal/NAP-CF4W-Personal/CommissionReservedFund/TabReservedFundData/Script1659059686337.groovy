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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

int flagFailed = 0

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Arraylist untuk menampung income info'
ArrayList<WebElement> varIncomeInfo = driver.findElements(By.cssSelector('#viewIncomeInfo label'))

'Arraylist untuk menampung remaining info commission'
ArrayList<WebElement> remainingInfoCom = new ArrayList<WebElement>()

'Arraylist untuk nilai yang dibagikan dari tab commission'
ArrayList<WebElement> AllocatedCommissionAmt = new ArrayList<WebElement>()

'Arraylist untuk menampung remaining info reserved fund sebelum calculate'
ArrayList<WebElement> remainingInfoRsv = new ArrayList<WebElement>()

if(GlobalVariable.Role=="Testing"){
	
	remainingInfoCom = GlobalVariable.ComRemainingInfoAmt
	
	AllocatedCommissionAmt = GlobalVariable.AllocatedCommissionAmt
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countIncomeInfo = varIncomeInfo.size() / 2
	
	'Looping income info'
	for (int i = 1; i < countIncomeInfo; i++) {
		modifyIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_IncomeInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai income info amount dari confins'
		String textincomeInfoAmt = WebUI.getText(modifyIncomeInfoAmt).replace(',', '')
	
		'verif remaining info tab commission data dengan section income info tab reserve fund'
		WebUI.verifyMatch(textincomeInfoAmt, String.format('%.2f', remainingInfoCom[(i - 1)]), false)
	
		modifyRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount dari confins'
		String textRemainingInfoAmt = WebUI.getText(modifyRemainingInfoAmt).replace(',', '')
	
		'Verifikasi untuk section remaining info di tab reserve fund, setelah save tab commission data maka akan hitung remaining info tab commission - nilai yg sudah dibagikan'
		WebUI.verifyEqual(Math.round(Double.parseDouble(textRemainingInfoAmt)), Math.round((remainingInfoCom[(i - 1)]) - (Math.round(
					(AllocatedCommissionAmt[(i - 1)]) * 100) / 100)))
	
		//	WebUI.verifyMatch(textRemainingInfoAmt,String.format("%.2f",remainingInfoCom[i-1]-Math.round(AllocatedCommissionAmt[i-1]*100)/100),false)
		'Tambahkan data remaining info ke arraylist untuk keperluan verifikasi setelah calculate'
		remainingInfoRsv.add(textRemainingInfoAmt)
	}

}

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/span_appNo'))

'Ambil nilai string text lobcode dari db los berdasarkan appNo'
String lobCode = CustomKeywords.'commissionReserveFundData.verifRuleReserveFundData.checkLOBCode'(sqlConnectionLOS, appNo)

'HashMap untuk menampung alloc from, alloc amount, dan alloc behaviour dari excel rule'
HashMap<String,String> resultVerifRule = CustomKeywords.'commissionReserveFundData.verifRuleReserveFundData.checkReserveFundBasedOnRule'(lobCode)

'Arraylist untuk menampung nilai array alloc from'
ArrayList<String> allocFrom = resultVerifRule.get("From")

'Arraylist untuk menampung nilai array alloc amount default'
ArrayList<String> defAllocAmt = resultVerifRule.get("Amt")

'Arraylist untuk menampung nilai array alloc behaviour'
ArrayList<String> allocBhv = resultVerifRule.get("Bhv")

BigDecimal totalAmt = 0

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(filePath, '14.TabReservedFundData', 'Reserve Fund Amt')+2

'Arraylist untuk menampung remaining info'
ArrayList<WebElement> varRemainingBfrCalculate = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))

'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countRemainingInfoBfrCalculate = varRemainingBfrCalculate.size() / 2

modifyRemainingAllocatedAmountBfrCalculate = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
	'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfoBfrCalculate) + ']/div[2]/label', true)

'Looping data allocation reserve fund'
for(int i = 0;i<allocFrom.size();i++){
	xpathInputAlloc = "//input[@id='ReservedFundAmt"+i+"']"
	inputAlloc = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/input_PROMO_InterestIncome'),'xpath','equals',xpathInputAlloc, true)
	xpathAllocFromSection = "//*[@id='reserved-fund-tab']/reserved-fund/div/div/div/form/div/div[1]/div["+(i+1)+"]/lib-ucsubsection/div/form/div/h4"
	allocFromSectionObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/h4_RSVAllocFrom'),'xpath','equals',xpathAllocFromSection, true)
	
	'Ambil nilai string text nama section allocation pada confins'
	String textAllocFromSection = WebUI.getText(allocFromSectionObject)
	
	'Verify allocation from yang tampil pada confins sesuai dengan rule file'
	WebUI.verifyMatch(textAllocFromSection, ".*"+allocFrom[i].replace("_"," ")+".*",true)
	
	BigDecimal remainingInfoAmt
	'Looping remaining info'
	for(int j =1;j<=countRemainingInfoBfrCalculate;j++){
		newxpathRemainingInfo = (('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[1]/label')
		 
		newxpathRemainingInfoAmt = (('//*[@id="viewRemainIncomeInfo"]/div[' + j) + ']/div/div[2]/label')
		
		modifyObjectRemainingInfo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfo'),'xpath','equals',newxpathRemainingInfo,true)
		
		modifyObjectRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),'xpath','equals',newxpathRemainingInfoAmt, true)
		
		'Ambil nilai string text remaining info'
		String textRemainingInfo = WebUI.getText(modifyObjectRemainingInfo)
		
		String textRemainingInfoAmt 
		
		'Pengecekan jika text remaining info pada confins sesuai dengan nama section allocation'
		if(textRemainingInfo == textAllocFromSection){
			'Ambil nilai amount remaining info'
			textRemainingInfoAmt = WebUI.getText(modifyObjectRemainingInfoAmt).replace(",","")
			remainingInfoAmt = Double.parseDouble(textRemainingInfoAmt)
			break
		}
	}
	'Ambil nilai amount dari allocation'
	String inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
	'Pengecekan remaining info bernilai 0 atau tidak'
	if(remainingInfoAmt>0){
		'Verify amount yang tampil di confins sesuai dengan default amount pada rule file '
		WebUI.verifyMatch(inputAllocAmt.replace(",",""),defAllocAmt[i],false)
		
		'Pengecekan editable/tidaknya field-field allocation pada confins sesuai behaviour pada rule file'
		if(allocBhv[i].equalsIgnoreCase("def")){
			'Verify field bisa diisi'
			WebUI.verifyElementNotHasAttribute(inputAlloc,'readonly',2)
			
			'Input Alloc Reserved Fund Amount'
			WebUI.setText(inputAlloc, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(
				GlobalVariable.NumofColm, rsvAmtRow+i), FailureHandling.OPTIONAL)
		}
		else if(allocBhv[i].equalsIgnoreCase("lock")){
			'Verify field tidak bisa diisi'
			WebUI.verifyElementHasAttribute(inputAlloc,'readonly',2)
		}
		
	}
	else{
		'Verify field tidak bisa diisi'
		WebUI.verifyElementHasAttribute(inputAlloc,'readonly',2)
	}
	
	if(GlobalVariable.Role=="Testing"){
		inputAllocAmt = WebUI.getAttribute(inputAlloc, 'value')
		'Tambahkan amount masing-masing allocation untuk perhitungan total reserved fund amount'
		totalAmt+=Double.parseDouble(inputAllocAmt.replace(",",""))
	}
	
}

String alert

'Klik Button Calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Calculate'))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/alert_Commission'),1,FailureHandling.OPTIONAL)){
	alert = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/alert_Commission'),FailureHandling.OPTIONAL)
}
else if(alert==null){
	alert = "def"
}

if(alert.toLowerCase().contains("Must Be Less Than".toLowerCase())||WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/error_maxnumber'),2,FailureHandling.OPTIONAL)){
	'Write to Excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.StatusReasonCalculateGagal'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonCalculateGagal)

	'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))
	
	flagFailed = 1
}

if(GlobalVariable.Role=="Testing"){
	'Menyimpan nilai Total Reserved Fund Amount dari Web CONFINS'
	String totalReservedFundAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_TotalReservedFundAmt')).replace(
		',', '').replace('.00', '')
	
	'Verifikasi hasil perhitungan total reserved fund amount sesuai dnegan nilai total reserved fund amount dari web'
	WebUI.verifyMatch(totalReservedFundAmt, totalAmt.toString(), false)
	
	'Arraylist untuk menampung remaining info'
	ArrayList<WebElement> varRemainingInfo = driver.findElements(By.cssSelector('#viewRemainIncomeInfo label'))
	
	'Inisialisasi Variabel untuk menghitung jumlah baris pada remaining Information, dibagi 2 karena countremaininginfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
	int countRemainingInfo = varRemainingInfo.size() / 2
	
	modifyRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingAllocatedAmt'),
		'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + countRemainingInfo) + ']/div[2]/label', true)
	
	'Ambil nilai remaining allocated amount stelah calculate di reserve fund'
	String textRemainingAllocatedAmount = WebUI.getText(modifyRemainingAllocatedAmount).replace(',', '')
	
	'verif remaining allocated amount = remaining allocated amount (after calc di comision) - nilai yg dibagina di reserve fund'
	WebUI.verifyMatch(textRemainingAllocatedAmount, String.format('%.2f', GlobalVariable.RemainingAllocatedAmt - totalAmt),
		false)
	
	'Looping remaining info amount setelah calculate'
	for (int i = 1; i < countRemainingInfo; i++) {
		modifyRemainingInfoAmtAftCal = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/label_RemainingInfoAmt'),
			'xpath', 'equals', ('//*[@id="viewRemainIncomeInfo"]/div[' + i) + ']/div/div[2]/label', true)
	
		'Ambil nilai remaining info amount'
		String textRemainingInfoAmtAftCal = WebUI.getText(modifyRemainingInfoAmtAftCal).replace(',', '')
	
		'verifikasi setelah calculate di tab reserve fund, nilai remaining info tidak berubah (masih sesuai setelah save dari tab commission)'
		WebUI.verifyMatch(textRemainingInfoAmtAftCal, remainingInfoRsv[(i - 1)], false)
	}
	
}

WebUI.delay(5)

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, 4))

if(iscompleteMandatory==0){
	flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '14.TabReservedFundData')
}

if(flagFailed==0){
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
		findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/alert_Submit'), GlobalVariable.NumofColm, '14.TabReservedFundData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '14.TabReservedFundData')
	}
}

'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'), 
    5, FailureHandling.OPTIONAL)) {
    'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
    }
} 
