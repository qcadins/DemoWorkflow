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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

int flagFailed=0

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'TERM AND CONDITION', false, FailureHandling.OPTIONAL)
}

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Inisialisasi variabel'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#TC-tab > app-tc-data > div > div > div > div > div > form > div > app-term-conditions > div > table > tbody tr'))

//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya dokumen'
int count = variable.size()

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

'Ambil text customer model dari confins'
String custModel = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/label_custModel'))

'Ambil text appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/span_appNo'))

'Hashmap untuk mengambil nilai tccode, tcmandatory, tc priorto dan tc is waivable berdasarkan condition-condition dari rule excel'
HashMap<String,ArrayList> result = CustomKeywords.'tcData.verifTCData.verifyTermConditionData'(sqlConnectionLOS, sqlConnectionFOU,custModel,appNo)

ArrayList<String> TCCode, TCMandatory, TCPrior, TCWaive
TCCode = result.get("TCCode")
TCMandatory = result.get("TCMdt")
TCPrior = result.get("TCPrior")
TCWaive = result.get("TCWaive")

'Looping data dokumen'
for (int i = 1; i <= count; i++) {
	String newxpathRequired

	String newxpathcheckbox

	newxpathRequired = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[4]')

	newxpathcheckbox = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[5]/input')
	
	newxpathpriorto = "//*[@id='TC-tab']/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr["+i+"]/td[3]"

	modifyObjectRequired = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'),
		'xpath', 'equals', newxpathRequired, true)

	modifyObjectCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_Checkbox'),
		'xpath', 'equals', newxpathcheckbox, true)

	modifyObjectPriorTo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_PriorTo'),'xpath','equals',newxpathpriorto,true)
	
	newxpathDocumentName = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[2]')

	modifyObjectDocumentName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'),
		'xpath', 'equals', newxpathDocumentName, true)
	
	newxpathWaived = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
		 i) + ']/td[6]/input')
	
	modifyObjectWaived = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_waived'),
		'xpath', 'equals', newxpathWaived, true)
	
	'Variabel yang digunakan untuk menyimpan isi dari nama dokumen'
	String textDocumentName = WebUI.getText(modifyObjectDocumentName)

	if(GlobalVariable.RoleCompany=="Testing"){
		'verif document name based on rule'
		WebUI.verifyMatch(CustomKeywords.'tcData.verifTCData.checkTCCode'(sqlConnectionFOU,textDocumentName),TCCode.get(i-1),false)
		if(TCMandatory.get(i-1)=="false"){
			'verif required based on rule'
			WebUI.verifyElementText(modifyObjectRequired,"NO")
		}
		else if(TCMandatory.get(i-1)=="true"){
			'verif required based on rule'
			WebUI.verifyElementText(modifyObjectRequired,"YES")
		}
		'verif prior to based on rule'
		WebUI.verifyMatch(WebUI.getText(modifyObjectPriorTo),TCPrior.get(i-1),false)
		if(TCWaive.get(i-1)=="false"){
			'verif waive terlock based on rule'
			WebUI.verifyElementHasAttribute(modifyObjectWaived,"disabled",1)
		}
		else if(TCWaive.get(i-1)=="true"){
			'verif waive tidak terlock/ dapat dicentang based on rule'
			WebUI.verifyElementNotHasAttribute(modifyObjectWaived,"disabled",1)
		}
	}

	'Variabel text digunakan untuk menyimpan isi dari kolom Required'
	String textRequired = WebUI.getText(modifyObjectRequired)
	
	'Pengecekan nilai kolom required'
	if (textRequired == 'NO') {
		'Jika sudah tercentang'
		if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
			'Un-centang yang required no'
			WebUI.uncheck(modifyObjectCheckbox)
		}
		
		def checkNO = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
			GlobalVariable.NumofColm, 14).split(';', -1)

		'Pengecekan jika perlu dokumen yang required no tercentang'
		if (checkNO.size() > 0) {
			'Looping dokumen checkno'
			for (j = 1; j <= checkNO.size(); j++) {
				'Pengecekan jika nama dokumen sama dengan dokumen checkno pada excel'
				if (textDocumentName.equalsIgnoreCase(checkNO[(j - 1)])) {
					'Jika belum tercentang'
					if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
						'Centang'
						WebUI.check(modifyObjectCheckbox)
					}
				}
			}
		}
	} else {
		'Jika belum tercentang'
		if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
			'Centang yang required yes'
			WebUI.check(modifyObjectCheckbox)
		}
		
		def uncheckYES = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
			GlobalVariable.NumofColm, 12).split(';', -1)

		newxpathPromiseDate = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
		i) + ']/td[7]/input')

		modifyObjectPromiseDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'),
			'xpath', 'equals', newxpathPromiseDate, true)

		def PromiseDate = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
			GlobalVariable.NumofColm, 13).split(';', -1)

		'Pengecekan jika perlu dokumen required yes uncentang'
		if (uncheckYES.size() > 0) {
			'Looping dokumen uncheckYES'
			for (j = 1; j <= uncheckYES.size(); j++) {
				'Pengecekan jika nama dokumen sama dengan dokumen uncheckYES pada excel'
				if (textDocumentName.equalsIgnoreCase(uncheckYES[(j - 1)])) {
					'Jika sudah tercentang'
					if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
						'Un-centang'
						WebUI.uncheck(modifyObjectCheckbox)
					}
					
					if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 13).length() > 0) {
						'Input Promise Date'
						WebUI.setText(modifyObjectPromiseDate, PromiseDate[(j - 1)])
					}
				}
			}
		}
	}
	
	newxpathExpiredDate = (('//*[@id="TC-tab"]/app-tc-data/div/div/div/div/div/form/div/app-term-conditions/div/table/tbody/tr[' +
	i) + ']/td[8]/input')

	modifyObjectExpiredDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/input_expiredDate'),
		'xpath', 'equals', newxpathExpiredDate, true)

	def expiredDateDocument = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
		GlobalVariable.NumofColm, 15).split(';', -1)

	def expiredDate = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
		GlobalVariable.NumofColm, 16).split(';', -1)

	def waivedDocument = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
			GlobalVariable.NumofColm, 17).split(';', -1)
	
	'Pengecekan jika waive dapat diklik'
	if (WebUI.verifyElementClickable(modifyObjectWaived, FailureHandling.OPTIONAL)) {
		'Pengecekan jika kondisi awal waived sudah tercentang'
		if (WebUI.verifyElementChecked(modifyObjectWaived, 1, FailureHandling.OPTIONAL)) {
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
			if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL) && textDocumentName.equalsIgnoreCase(
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

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 4)) == 0) {
'Check alert'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '10.TabTermConditionData')
}

'check save process write to excel'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 4)),
	findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/span_VIEW APPLICATION  0002APP20211201128_spanMenu'), GlobalVariable.NumofColm, '10.TabTermConditionData')


if(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 4)) == 0){
	
'check error validasi'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/errorvalidasi'), GlobalVariable.NumofColm, '10.TabTermConditionData')
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
	'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))

}
