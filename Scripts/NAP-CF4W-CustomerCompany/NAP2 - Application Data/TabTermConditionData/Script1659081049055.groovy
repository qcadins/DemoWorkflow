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
import com.kms.katalon.core.testobject.SelectorMethod as SelectorMethod
import groovy.sql.Sql as Sql

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

GlobalVariable.FlagFailed = 0

'declare excelPathTabTC'
excelPathTabTC = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabTermConditionData'

'Klik tab TC'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/buttonTabTC'))

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("FINANCIAL DATA") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'TERM AND CONDITION', false, FailureHandling.OPTIONAL))
}

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Inisialisasi variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#TC-tab > app-tc-data > div > div > div > div > div > form > div > app-term-conditions > div > table > tbody tr'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya dokumen'
int count = variable.size()

Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'Ambil text customer model dari confins'
String custModel = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/label_custModel'))

'Ambil text appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/span_appNo'))

'Hashmap untuk mengambil nilai tccode, tcmandatory, tc priorto dan tc is waivable berdasarkan condition-condition dari rule excel'
HashMap<String,ArrayList> result = CustomKeywords.'tcData.verifyTCData.verifyTCList'(sqlConnectionLOS, sqlConnectionFOU,custModel,appNo)

ArrayList<String> TCCode, TCMandatory, TCPrior, TCWaive
TCCode = result.get("TCCode")
TCMandatory = result.get("TCMdt")
TCPrior = result.get("TCPrior")
TCWaive = result.get("TCWaive")

'Looping data dokumen'
for (int i = 1; i <= count; i++) {

	modifyObjectRequired = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'),
		'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[4]'), true)

	modifyObjectCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_Checkbox'),
		'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[5]/input'), true)

	modifyObjectPriorTo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_PriorTo'),'xpath','equals',"//*[@id='TC-tab']/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr["+i+"]/td[3]",true)

	modifyObjectDocumentName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'),
		'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[2]'), true)
	
	modifyObjectWaived = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_waived'),
		'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
		 i) + ']/td[6]/input'), true)
	
	'Variabel yang digunakan untuk menyimpan isi dari nama dokumen'
	String textDocumentName = WebUI.getText(modifyObjectDocumentName)

	if(GlobalVariable.RoleCompany=="Testing" &&  GlobalVariable.CheckRuleCompany=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
//		'verif document name based on rule'
//		if(WebUI.verifyMatch(CustomKeywords.'tcData.verifyTCData.checkTCCode'(sqlConnectionFOU,textDocumentName), TCCode.get(i-1), false)==false){
//			writeToExcelFailedVerifRule()
//			GlobalVariable.FlagFailed=1
//		}
		if(TCMandatory.get(i-1)=="false"){
			'verif required based on rule'
			if(WebUI.verifyElementText(modifyObjectRequired,"NO")==false){
				writeToExcelFailedVerifRule()
				GlobalVariable.FlagFailed=1
			}
		}
		else if(TCMandatory.get(i-1)=="true"){
			'verif required based on rule'
			if(WebUI.verifyElementText(modifyObjectRequired,"YES")==false){
				writeToExcelFailedVerifRule()
				GlobalVariable.FlagFailed=1
			}
		}
		'verif prior to based on rule'
		if(WebUI.verifyMatch(WebUI.getText(modifyObjectPriorTo),TCPrior.get(i-1),false)==false){
			writeToExcelFailedVerifRule()
			GlobalVariable.FlagFailed=1
		}
		if(TCWaive.get(i-1)=="false"){
			'verif waive terlock based on rule'
			if(WebUI.verifyElementHasAttribute(modifyObjectWaived,"disabled",GlobalVariable.TimeOut)==false){
				writeToExcelFailedVerifRule()
				GlobalVariable.FlagFailed=1
			}
		}
		else if(TCWaive.get(i-1)=="true"){
			'verif waive tidak terlock/ dapat dicentang based on rule'
			if(WebUI.verifyElementNotHasAttribute(modifyObjectWaived,"disabled",GlobalVariable.TimeOut)==false){
				writeToExcelFailedVerifRule()
				GlobalVariable.FlagFailed=1
			}
		}
	}

	'Variabel text digunakan untuk menyimpan isi dari kolom Required'
	String textRequired = WebUI.getText(modifyObjectRequired)
	
	'Pengecekan nilai kolom required'
	if (textRequired == 'NO') {
		'Jika sudah tercentang'
		if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'Un-centang yang required no'
			WebUI.uncheck(modifyObjectCheckbox)
		}
		
		def checkNO = findTestData(excelPathTabTC).getValue(
			GlobalVariable.NumofColm, 14).split(';', -1)

		'Pengecekan jika perlu dokumen yang required no tercentang'
		if (checkNO.size() > 0) {
			'Looping dokumen checkno'
			for (j = 1; j <= checkNO.size(); j++) {
				'Pengecekan jika nama dokumen sama dengan dokumen checkno pada excel'
				if (textDocumentName.equalsIgnoreCase(checkNO[(j - 1)])) {
					'Jika belum tercentang'
					if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'Centang'
						WebUI.check(modifyObjectCheckbox)
					}
				}
			}
		}
	} else {
		'Jika belum tercentang'
		if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'Centang yang required yes'
			WebUI.check(modifyObjectCheckbox)
		}
		
		def uncheckYES = findTestData(excelPathTabTC).getValue(
			GlobalVariable.NumofColm, 12).split(';', -1)

		modifyObjectPromiseDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'),
			'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
		i) + ']/td[7]/input'), true)

		def PromiseDate = findTestData(excelPathTabTC).getValue(
			GlobalVariable.NumofColm, 13).split(';', -1)

		'Pengecekan jika perlu dokumen required yes uncentang'
		if (uncheckYES.size() > 0) {
			'Looping dokumen uncheckYES'
			for (j = 1; j <= uncheckYES.size(); j++) {
				'Pengecekan jika nama dokumen sama dengan dokumen uncheckYES pada excel'
				if (textDocumentName.equalsIgnoreCase(uncheckYES[(j - 1)])) {
					'Jika sudah tercentang'
					if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'Un-centang'
						WebUI.uncheck(modifyObjectCheckbox)
					}
					
					if (findTestData(excelPathTabTC).getValue(
						GlobalVariable.NumofColm, 13).length() > 0 && PromiseDate[(j-1)]!="") {
						'Input Promise Date'
						WebUI.setText(modifyObjectPromiseDate, PromiseDate[(j - 1)])
					}
				}
			}
		}
	}
   
	modifyObjectExpiredDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_expiredDate'),
		'xpath', 'equals', (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[8]/input'), true)

	def expiredDateDocument = findTestData(excelPathTabTC).getValue(
		GlobalVariable.NumofColm, 15).split(';', -1)

	def expiredDate = findTestData(excelPathTabTC).getValue(
		GlobalVariable.NumofColm, 16).split(';', -1)

	def waivedDocument = findTestData(excelPathTabTC).getValue(
		GlobalVariable.NumofColm, 17).split(';', -1)

	'Pengecekan jika waive dapat diklik'
	if (WebUI.verifyElementClickable(modifyObjectWaived, FailureHandling.OPTIONAL)) {
		'Pengecekan jika kondisi awal waived sudah tercentang'
		if (WebUI.verifyElementChecked(modifyObjectWaived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'Uncentang waive'
			WebUI.uncheck(modifyObjectWaived)
		}
		
		'Pengecekan jika ada dokumen yang perlu diwaive'
		if (waivedDocument.size() > 0) {
			'Looping dokumen yang perlu diwaive'
			for (j = 1; j <= waivedDocument.size(); j++) {
				'Pengecekan nama dokumen sama dengan nama dokumen yang perlu diwaive pada excel'
				if (textDocumentName.equalsIgnoreCase(waivedDocument[(j - 1)])) {
					'Centang Waive'
					WebUI.check(modifyObjectWaived)
				}
			}
		}
	}
	
	'Pengecekan jika ada dokumen yang perlu diisi expired date'
	if (expiredDateDocument.size() > 0) {
		'Looping dokumen yang perlu diisi expired date'
		for (j = 1; j <= expiredDateDocument.size(); j++) {
			'Pengecekan jika kolom check tercentang dan nama dokumen sama dengan dokumen yang perlu diisi expired date pada excel'
			if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL) && textDocumentName.equalsIgnoreCase(
				expiredDateDocument[(j - 1)])) {
				WebUI.delay(1)

				'Input expired date'
				WebUI.setText(modifyObjectExpiredDate, expiredDate[(j - 1)], FailureHandling.OPTIONAL)
			}
		}
	}
}

'Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabTC).getValue(
		GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '10.TabTermConditionData')
}

if (GlobalVariable.FlagFailed == 0) {
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/span_VIEW APPLICATION  0002APP20211201128_spanMenu'),
		GlobalVariable.NumofColm, '10.TabTermConditionData')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '10.TabTermConditionData')
	}
}

WebUI.delay(5)



'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
	'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))

	GlobalVariable.IsDataCancel = 1
}else{
	if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
		'call test case store db TC'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTCDataStoreDBVerif'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
}
	
public writeToExcelFailedVerifRule(){
		
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabTermConditionData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)
	
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabTermConditionData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
		GlobalVariable.FlagFailed=1
	}
}
