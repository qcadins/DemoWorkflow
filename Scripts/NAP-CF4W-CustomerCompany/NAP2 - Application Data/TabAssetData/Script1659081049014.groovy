import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.text.NumberFormat as NumberFormat
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import groovy.sql.Sql as Sql

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare datafileTabAsset'
datafileTabAsset = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData')

WebUI.delay(5)

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if (!(appLastStep.equalsIgnoreCase('APPLICATION DATA')) && (GlobalVariable.FirstTimeEntry == 'Yes')) {
	GlobalVariable.FirstTimeEntry = 'No'
}

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'ASSET & COLLATERAL DATA', false, FailureHandling.OPTIONAL))
}

String suppName

'Ambil text product offering dari confins'
String POName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_ProductOffering'))

'Ambil text original office dari confins'
String office = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))

'click button supplier lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'))

if (GlobalVariable.RoleCompany == 'Testing') {
	
	'Ambil text supplier scheme dari db'
	String suppschm = CustomKeywords.'assetData.checkSupplier.checkSupplierScheme'(sqlConnectionLOS, POName)
	
	'click search button'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

	'Ambil nilai total count supplier data dari db'
	Integer countSupplierData = CustomKeywords.'assetData.checkSupplier.countSupplierData'(sqlConnectionFOU, suppschm,
		office)

	'Ambil nilai total data supplier pada lookup confins'
	String[] textTotalDataSupplier = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/label_totalSupplier')).replace(
		' ', '').replace(':', ';').split(';')

	'Parsing nilai total data supplier confins ke integer(angka)'
	Integer totalDataSupplier = Integer.parseInt(textTotalDataSupplier[1])

	'Verify total count data lookup supplier pada confins sama dengan db'
	if (WebUI.verifyEqual(totalDataSupplier, countSupplierData) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDataLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDataLookup)

		GlobalVariable.FlagFailed = 1
	}
}

'input supplier code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCode'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 12))

'input supplier name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 13))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
	5, FailureHandling.OPTIONAL)) {
	'Ambil text supplier name dari lookup confins'
	suppName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/span_suppNameLookup'))

	'click select'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
} else {
	'click X'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))

	'click button cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))

	'write to excel status failed'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.StatusReasonLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusReasonLookup)

	GlobalVariable.FlagFailed = 1

	'Pengecekan jika new consumer finance belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}
}

if (GlobalVariable.RoleCompany == 'Testing') {
	
	ArrayList<Boolean> adminHead,salesPerson
	
	'Ambil array string admin head dari db'
	adminHead = CustomKeywords.'assetData.checkSupplier.checkAdminHead'(sqlConnectionFOU, suppName)

	'Ambil array string sales person dari db'
	salesPerson = CustomKeywords.'assetData.checkSupplier.checkSalesPerson'(sqlConnectionFOU, suppName)

	'Verify array sales person dari db sama dengan opsi dropdownlist sales person confins'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_SalesPerson'),
		salesPerson) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
	
	'Verify array admin head dari db sama dengan opsi dropdownlist admin head confins'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_AdminHead'),
		adminHead) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
}

'select sales person'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_SalesPerson'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 14), false)

if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 15).length() > 0) {
	'select admin head'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_AdminHead'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 15), false)
}

'click button asset lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Asset Name_btn btn-raised btn-primary'))

if (GlobalVariable.RoleCompany == 'Testing') {
	'click search button'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Asset'))

	'Ambil nilai total count asset data dari db'
	Integer countAssetData = CustomKeywords.'assetData.checkAssetData.countAssetName'(sqlConnectionLOS, sqlConnectionFOU,
		POName)

	'Ambil nilai total data asset pada lookup confins'
	String[] textTotalDataAsset = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/label_totalSupplier')).replace(
		' ', '').replace(':', ';').split(';')

	'Parsing nilai total data Asset confins ke integer(angka)'
	Integer totalDataAsset = Integer.parseInt(textTotalDataAsset[1])

	'Verify total count data lookup asset pada confins sama dengan db'
	if (WebUI.verifyEqual(totalDataAsset, countAssetData) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDataLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDataLookup)

		GlobalVariable.FlagFailed = 1
	}
}

'input asset name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_FullAssetCode'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 17))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Asset'))

verifyInputErrorLookUp()

'select asset condition'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 18), false)

'input asset price'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Price_assetPriceAmt'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 19))

if (GlobalVariable.RoleCompany == 'Testing') {
	ArrayList<Boolean> assetUsage = new ArrayList<Boolean>()

	'Ambil array string (text) asset usage dari db'
	assetUsage = CustomKeywords.'assetData.checkAssetData.checkAssetUsageDDL'(sqlConnectionFOU)

	Integer countAssetUsage = assetUsage.size()

	'Verif dropdownlist asset usage yang muncul pada confins sesuai dengan array string asset usage dari db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'),
		assetUsage) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
	
	'Ambil nilai jumlah option/pilihan asset usage dari confins'
	Integer totalAssetUsage = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'))

	'Verif jumlah asset usage yang muncul pada confins sesuai dengan jumlah asset usage pada db'
	if (WebUI.verifyEqual(totalAssetUsage - 1, countAssetUsage) == false) {
		'Write To Excel GlobalVariable.StatusFailed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

		'Write To Excel GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1,
			GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
}

'select asset usage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 20), false)

'input color'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Color'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 21))

if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 22).length() > 0) {
	'click button BPKB Lookup'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_BPKB City Issuer_btn btn-raised btn-primary'))

	'input district code'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_District Code'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 22))

	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_SearchBPKB city Issuer'))

	'verify input error'
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
		5, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))

		if (datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 20) == 'New') {
			flagWarning++
		}
	}
}

'input BPKP issue date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_BPKB Issue Date'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 23))

'input notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/textarea_Notes'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 24))

'input manufacturing year'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 25))

'select security deposit type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 26), false)

if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 26) == 'Percentage') {
	'Untuk handle jika field input percentage terlock'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'),
		'Amount', false)

	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'),
		'Percentage', false)

	WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),
		Keys.chord(Keys.CONTROL, 'a'))

	WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),
		Keys.chord(Keys.BACK_SPACE))

	WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),
		Keys.chord(Keys.HOME))

	WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),
		Keys.chord(Keys.RIGHT, datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 27)))
} else if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 26) == 'Amount') {
	'input security deposit amount'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 28))
}

'input chasis number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 29))

'input engine number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 30))

if (GlobalVariable.RoleCompany == 'Testing') {
	NumberFormat decimalFormat = NumberFormat.getPercentInstance()

	def AssetPrice = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Price_assetPriceAmt'),
		'value').split(',').join()

	def DownPaymentAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'),
		'value').split(',').join()

	def DownPaymentPrctg = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),
		'value').replaceAll('\\s', '')

	BigDecimal intDownPaymentAmt = Integer.parseInt(DownPaymentAmt)

	BigDecimal intAssetPrice = Integer.parseInt(AssetPrice)

	float floatDownPaymentPrctg = decimalFormat.parse(DownPaymentPrctg)

	Number NumberDownPaymentPrctg = decimalFormat.parse(DownPaymentPrctg)

	if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 24) == 'Percentage') {
		int multiplyAssetPricexDownPaymentPrctg = intAssetPrice * NumberDownPaymentPrctg

		'verify security deposit value equal'
		checkVerifyEqualOrMatch(WebUI.verifyEqual(multiplyAssetPricexDownPaymentPrctg, intDownPaymentAmt))
	} else if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 24) == 'Amount') {
		float divideDownPaymentAmtAssetPrice = intDownPaymentAmt / intAssetPrice

		'verify security deposit value equal'
		checkVerifyEqualOrMatch(WebUI.verifyEqual(divideDownPaymentAmtAssetPrice, floatDownPaymentPrctg))
	}
}

'input license plate number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
	datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 31))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'),
	5, FailureHandling.OPTIONAL)) {
	'input serial 4'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'),
	5, FailureHandling.OPTIONAL)) {
	'input serial 5'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
}

if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 18) == 'Used') {
	if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 62) == 'Yes') {
		'click button check rapindo'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_CheckRapindo'))
	}
}

String Fullassetcode = datafileTabAsset.getValue(GlobalVariable.NumofColm, 17)

'count asset attribute'
String countAssetAtrtibute = CustomKeywords.'customizeKeyword.getRowAssetAttribute.countRowAssetAttribute'(sqlConnectionFOU,
	sqlConnectionLOS, Fullassetcode, POName)

for (int i = 1; i <= Integer.parseInt(countAssetAtrtibute); i++) {

	'modify Asset Attribute Input Text'
	modifyObjectAssetAttributeInputText = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetAttribute'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="text"]', true)

	'modify Asset Attribute Select Dropdownlist'
	modifyObjectAssetAttributeList = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetAttribute'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/select', true)

	'modify Asset Attribute Input Date'
	modifyObjectAssetAttributeInputDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetAttribute'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="date"]', true)

	'modify Asset Attribute Input Number '
	modifyObjectAssetAttributeInputNumber = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetAttribute'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@formcontrolname="AttrValue"]', true)

	if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputText, 1, FailureHandling.OPTIONAL)) {
		'input "Attribute"'
		WebUI.setText(modifyObjectAssetAttributeInputText, 'Attr', FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeList, 1, FailureHandling.OPTIONAL)) {
		'select option index 1'
		WebUI.selectOptionByIndex(modifyObjectAssetAttributeList, 1, FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputDate, 1, FailureHandling.OPTIONAL)) {
		'input date 01/01/2000'
		WebUI.setText(modifyObjectAssetAttributeInputDate, '01/01/2000', FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputNumber, 1, FailureHandling.OPTIONAL)) {
		'input number 0'
		WebUI.setText(modifyObjectAssetAttributeInputNumber, '0', FailureHandling.OPTIONAL)
	}
}

//call test case aksesoris
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAccessoriesAsset'), [:], FailureHandling.CONTINUE_ON_FAILURE)

if (CustomKeywords.'assetData.checkAssetData.checkSelfUsageCompany'() == true) {
	'click self usage check box'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox (1)'))
} else if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 37) == 'No') {
	if (WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/status selfusage checkbox'),
		'aria-checked') == 'true') {
		'click self usage check box'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox (1)'))
	}
	
	'input user name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_User Name_form-control ng-untouched ng-pristine ng-valid'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 38))

	if (GlobalVariable.RoleCompany == 'Testing') {
		ArrayList<Boolean> userRelation = new ArrayList<Boolean>()

		'Ambil array string (text) user relationship dari db'
		userRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

		Integer countUserRelation = userRelation.size()

		'Verif dropdownlist user relationship yang muncul pada confins sesuai dengan array string user relationship dari db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_User Relationship'),
			userRelation) == false) {
			'Write To Excel GlobalVariable.StatusFailed'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write To Excel GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

			GlobalVariable.FlagFailed = 1
		}
		
		'Ambil nilai jumlah option/pilihan user relationship dari confins'
		Integer totalUserRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_User Relationship'))

		'Verif jumlah user relationship yang muncul pada confins sesuai dengan jumlah user relationship pada db'
		if (WebUI.verifyEqual(totalUserRelation - 1, countUserRelation) == false) {
			'Write To Excel GlobalVariable.StatusFailed'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write To Excel GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

			GlobalVariable.FlagFailed = 1
		}
	}
	
	'select user relationship'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_User Relationship'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 39), false)
}

if (CustomKeywords.'assetData.checkAssetData.checkSelfOwnerCompany'() == true) {
	'click self owner checkbox'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox (1)'))
	
	if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 52) == 'Yes') {
	
		assetLocCopy()
		
	} else if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 52) == 'No') {
	
		assetLocInput()
		
	}
} else if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 41) == 'No') {
	if (WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/status self owner checkbox'),
		'aria-checked') == 'true') {
		'click self owner checkbox'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox (1)'))
	}
	
	if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 42) == 'Company') {
		'click radio button Company'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Company'))

		if (GlobalVariable.RoleCompany == 'Testing') {
			ArrayList<Boolean> ownerCompanyRelation = new ArrayList<Boolean>()

			'Ambil array string (text) owner relationship dari db'
			ownerCompanyRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

			Integer countOwnerCompanyRelation = ownerCompanyRelation.size()

			'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
			if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'),
				ownerCompanyRelation) == false) {
				'Write To Excel GlobalVariable.StatusFailed'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

				'Write To Excel GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

				GlobalVariable.FlagFailed = 1
			}
			
			'Ambil nilai jumlah option/pilihan owner relationship dari confins'
			Integer totalOwnerCompanyRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

			'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
			if (WebUI.verifyEqual(totalOwnerCompanyRelation - 1, countOwnerCompanyRelation) == false) {
				'Write To Excel GlobalVariable.StatusFailed'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

				'Write To Excel GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

				GlobalVariable.FlagFailed = 1
			}
		}
	} else if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 42) == 'Company') {
		'click radio button company'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/div_Company'))

		if (GlobalVariable.RoleCompany == 'Testing') {
			ArrayList<Boolean> ownerCompanyRelation = new ArrayList<Boolean>()

			'Ambil array string (text) owner relationship dari db'
			ownerCompanyRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

			Integer countOwnerCompanyRelation = ownerCompanyRelation.size()

			'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
			if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'),
				ownerCompanyRelation) == false) {
				'Write To Excel GlobalVariable.StatusFailed'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

				'Write To Excel GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

				GlobalVariable.FlagFailed = 1
			}
			
			'Ambil nilai jumlah option/pilihan owner relationship dari confins'
			Integer totalOwnerCompanyRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

			'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
			if (WebUI.verifyEqual(totalOwnerCompanyRelation - 1, countOwnerCompanyRelation) == false) {
				'Write To Excel GlobalVariable.StatusFailed'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

				'Write To Excel GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
					1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)

				GlobalVariable.FlagFailed = 1
			}
		}
	}
	
	'input owner name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Owner Name_form-control ng-untouched ng-pristine ng-invalid'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 43))

	'select owner relationship'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 44), false)

	if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Personal') {
		if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 46).length() > 1) {
			'click button owner profession'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Owner Profession_btn btn-raised btn-primary'))

			'input profession code'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Profession Code_professionCodeId'),
				datafileTabAsset.getValue(
					GlobalVariable.NumofColm, 45))

			'click button search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search'))

			'verify input error'
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
				5, FailureHandling.OPTIONAL)) {
				'click select'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
			} else {
				'click X'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
			}
		}
	} else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Company') {
		'select company owner profession'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 47), false)
	}
	
	'select id type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-ID Type'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 48), false)

	'input id no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Owner Id No'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 49))

	'input owner mobile no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Owner Mobile Phone No'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 50))

	if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 52) == 'Yes') {
		'select copy address'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- JOB  LEGAL  MAILING  RESIDENCE'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 53), false)

		'click button copy'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Copy'))
		
		assetLocCopy()
		
	} else if (datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 52) == 'No') {
		'input address'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 54))

		'input RT'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_RT'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 55))

		'input RW'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_RW'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 56))

		'click button Zipcode'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Zipcode_btn btn-raised btn-primary'))

		'input Zipcode'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 57))

		'input Kecamatan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_KecamatanAssetOwner'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 59))

		'input kelurahan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_KelurahanAssetOwner'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 58))

		'input Kota'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_KotaAssetOwner'),
			datafileTabAsset.getValue(
				GlobalVariable.NumofColm, 60))

		'click search button'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

		verifyInputErrorLookUp()
		
		assetLocInput()
		
	}
}

GlobalVariable.AssetPrice += Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Price_assetPriceAmt'),
		'value').replace(',', ''))

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save'))

'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
WebUI.waitForAlert(3)

'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
WebUI.acceptAlert(FailureHandling.OPTIONAL)

if (datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 62) == 'No') {
	'Menunggu Alert konfirmasi integrator muncul'
	WebUI.waitForAlert(3)

	'Accept Alert Konfirmasi Integrator'
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

WebUI.delay(2)

Integer iscompleteMandatory = Integer.parseInt(datafileTabAsset.getValue(
		GlobalVariable.NumofColm, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm,
		'7.TabAssetData')
}

if (GlobalVariable.FlagFailed == 0) {
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
		GlobalVariable.NumofColm, '7.TabAssetData')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '7.TabAssetData')
	}
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'),
	5, FailureHandling.OPTIONAL)) {
	'click button cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
} else {
	if (flagWarning > 0) {
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
	}
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
	'call test case store db accessories data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAccessoriesDataStoreDBVerif'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)

	'call test case store db asset data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetDataStoreDBVerif'), [:],
		FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(10)

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)

		'write to excel status verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}

public assetLocInput(){
	'input address'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_AddressText'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 54))

	'input RT'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRT'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 55))

	'input RW'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRW'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 56))

	'click button zipcode'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonZipcode'))

	'input Zipcode'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_inptZipcode'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 57))

	'input Kecamatan'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKecamatan'),
		datafileTabAsset.getValue(GlobalVariable.NumofColm, 59))

	'input kelurahan'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKelurahan'),
		datafileTabAsset.getValue(GlobalVariable.NumofColm, 58))

	'input Kota'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKota'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 60))

	'click search button'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

	verifyInputErrorLookUp()
}

public assetLocCopy(){
	'select copy address'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'),
		datafileTabAsset.getValue(
			GlobalVariable.NumofColm, 53), false)

	'click button copy'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonCopy'))
}

public verifyInputErrorLookUp(){
	'verify input error'
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
		5, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
	
		'click button cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
	
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)
	
		'Write To Excel GlobalVariable.StatusReasonLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusReasonLookup)
	
		GlobalVariable.FlagFailed = 1
	
		'Pengecekan jika new consumer finance belum diexpand'
		if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
			'Klik new consumer finance'
			WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
		}
	}
}