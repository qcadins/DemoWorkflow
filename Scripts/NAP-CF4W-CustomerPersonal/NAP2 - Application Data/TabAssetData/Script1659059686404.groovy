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
import org.openqa.selenium.support.ui.Select

import groovy.sql.Sql as Sql

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'Klik tab asset'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabAsset'))

'koneksi los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare datafileTabCustomer'
datafileTabCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData')

'declare datafileTabAsset'
datafileTabAsset = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData')

WebUI.delay(5)

'get app last step from confins'
String appLastStep = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/label_AppLastStep'))

if (!(appLastStep.equalsIgnoreCase('APPLICATION DATA')) && !(appLastStep.equalsIgnoreCase('ASSET & COLLATERAL DATA')) &&  (GlobalVariable.FirstTimeEntry == 'Yes')) {
    GlobalVariable.FirstTimeEntry = 'No'
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'ASSET & COLLATERAL DATA', false, FailureHandling.OPTIONAL))
}

'declare suppname'
String suppName

'Ambil text product offering dari confins'
String POName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_ProductOffering'))

'Ambil text original office dari confins'
String office = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))

'click button supplier lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'))

if (GlobalVariable.Role == 'Testing') {
    'Ambil text supplier scheme dari db'
    String suppschm = CustomKeywords.'assetData.checkSupplier.checkSupplierScheme'(sqlConnectionLOS, POName)

    'click search button'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

    'Ambil nilai total count supplier data dari db'
    Integer countSupplierData = CustomKeywords.'assetData.checkSupplier.countSupplierData'(sqlConnectionFOU, suppschm, office)

    'Ambil nilai total data supplier pada lookup confins'
    String[] textTotalDataSupplier = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/label_totalSupplier')).replace(
        ' ', '').replace(':', ';').split(';')

    'Parsing nilai total data supplier confins ke integer(angka)'
    Integer totalDataSupplier = Integer.parseInt(textTotalDataSupplier[1])

    'Verify total count data lookup supplier pada confins sama dengan db'
    if (WebUI.verifyEqual(totalDataSupplier, countSupplierData) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDataLookup)

        GlobalVariable.FlagFailed = 1
    }
}

'input supplier code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_SupplierCode'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 12))

'input supplier name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 13))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

'verify input error'
CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData'), 
    '7.TabAssetData', GlobalVariable.NumofColm)

if (GlobalVariable.Role == 'Testing') {
    'declare arraylist adminhead,salesperson'
    ArrayList<String> adminHead

    ArrayList<String> salesPerson

    'Ambil array string admin head dari db'
    adminHead = CustomKeywords.'assetData.checkSupplier.checkAdminHead'(sqlConnectionFOU, suppName)

    'Ambil array string sales person dari db'
    salesPerson = CustomKeywords.'assetData.checkSupplier.checkSalesPerson'(sqlConnectionFOU, suppName)

    'Verify array sales person dari db sama dengan opsi dropdownlist sales person confins'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_SalesPerson'), 
        salesPerson) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

        GlobalVariable.FlagFailed = 1
    }
    
    'Verify array admin head dari db sama dengan opsi dropdownlist admin head confins'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_AdminHead'), 
        adminHead) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

        GlobalVariable.FlagFailed = 1
    }
}

'select sales person'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_SalesPerson'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 14), false)

'Jika admin head pada excel tidak kosong'
if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 15).length() > 0) {
    'select admin head'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_AdminHead'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 15), false)
}

'click button asset lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Asset Name_btn btn-raised btn-primary'))

if (GlobalVariable.Role == 'Testing') {
    'click search button'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Asset'))

    'Ambil nilai total count asset data dari db'
    Integer countAssetData = CustomKeywords.'assetData.checkAssetData.countAssetName'(sqlConnectionLOS, sqlConnectionFOU, 
        POName)

    'Ambil nilai total data asset pada lookup confins'
    String[] textTotalDataAsset = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/label_totalSupplier')).replace(
        ' ', '').replace(':', ';').split(';')

    'Parsing nilai total data Asset confins ke integer(angka)'
    Integer totalDataAsset = Integer.parseInt(textTotalDataAsset[1])

    'Verify total count data lookup asset pada confins sama dengan db'
    if (WebUI.verifyEqual(totalDataAsset, countAssetData) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDataLookup)

        GlobalVariable.FlagFailed = 1
    }
}

'input asset name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_FullAssetCode'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 17))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Asset'))

'verify input error'
CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData'), 
    '7.TabAssetData', GlobalVariable.NumofColm)

'select asset condition'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 18), false)

'input asset price'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Asset Price_assetPriceAmt'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 19))

if (GlobalVariable.Role == 'Testing') {
	'Ambil array string (text) asset usage dari db'
	ArrayList<String> assetUsage = CustomKeywords.'assetData.checkAssetData.checkAssetUsageDDL'(sqlConnectionFOU)
	
	'Ambil array string (text) asset condition dari db'
	ArrayList<String> assetCondition = CustomKeywords.'assetData.checkAssetData.checkAssetConditionDDL'(sqlConnectionFOU)

	'Ambil nilai jumlah option/pilihan asset usage dari confins'
	Integer totalAssetUsage = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'))
	
	'Ambil nilai jumlah option/pilihan asset Condition dari confins'
	Integer totalAssetCondition = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'))
	
	'Verif jumlah asset usage yang muncul pada confins sesuai dengan jumlah asset usage pada db'
	if (WebUI.verifyEqual(totalAssetUsage - 1, assetUsage.size()) == false) {
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm,
			GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
	
	'Verif jumlah asset condition yang muncul pada confins sesuai dengan jumlah asset usage pada db'
	if (WebUI.verifyEqual(totalAssetCondition, assetCondition.size()) == false) {
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm,
			GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
	
	'Verif dropdownlist asset usage yang muncul pada confins sesuai dengan array string asset usage dari db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'),
		assetUsage) == false) {
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm,
			GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

		GlobalVariable.FlagFailed = 1
	}
		
	'Verif dropdownlist asset condition yang muncul pada confins sesuai dengan array string asset condition dari db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'),
			assetCondition) == false) {
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)
		
		GlobalVariable.FlagFailed = 1
	}
}

'select asset usage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 20), false)

'input color'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Color'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 21))

'Jika district code pada excel tidak kosong'
if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 22).length() > 0) {
    'click button BPKB Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_BPKB City Issuer_btn btn-raised btn-primary'))

    'input district code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_District Code'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 22))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_SearchBPKB city Issuer'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_XAccessories'))

        'Jika asset condition pada excel = new'
        if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 20) == 'New') {
            flagWarning++
        }
    }
}

'input BPKP issue date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_BPKB Issue Date'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 23))

'input notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/textarea_Notes'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 24))

ArrayList<String> resultManuYear, resultDP

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal == "Yes"){
	'check rule validasi tab asset'
	HashMap<String, ArrayList> resultAssetValidation =  CustomKeywords.'assetData.checkAssetValidation.checkValidation'(sqlConnectionLOS, sqlConnectionFOU, datafileTabCustomer.getValue(GlobalVariable.NumofColm,12), datafileTabAsset.getValue(GlobalVariable.NumofColm,12), datafileTabAsset.getValue(GlobalVariable.NumofColm,17), datafileTabAsset.getValue(GlobalVariable.NumofColm,18).toUpperCase(), datafileTabAsset.getValue(GlobalVariable.NumofColm,25), datafileTabApplication.getValue(GlobalVariable.NumofColm,20))
	resultManuYear = resultAssetValidation.get("ManuYearVldt")
	resultDP = resultAssetValidation.get("DPVldt")

	'Verify manufacturing year default nilainya sesuai dengan rule'
	WebUI.verifyMatch(resultManuYear.get(0),WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'),'value'),false)
	
	'Pengecekan behaviour pada inputan manufacturing year'
	if(resultManuYear.get(1)=="DEF"){
		'Verify manufacturing year tidak terlock'
		WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'),'readonly',GlobalVariable.TimeOut)
	}
	else if(resultManuYear.get(1)=="LOCK"){
		'Verify manufacturing year terlock'
		WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'),'readonly',GlobalVariable.TimeOut)
	}
}

'input manufacturing year'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 25))

'select security deposit type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 26), false)

'click untuk refresh page'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'))

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal == "Yes"){
	'verify dp percentage default sesuai rule'
	WebUI.verifyEqual(Double.parseDouble(resultDP.get(0)),Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),'value').replace(" %","")))
	
	'Pengecekan behaviour pada inputan dp'
	if(resultDP.get(1)=="DEF" && datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Percentage'){
		'verify dp percentage tidak terlock'
		WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),'readonly',GlobalVariable.TimeOut)
	}
	else if(resultDP.get(1)=="DEF" && datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Amount'){
		'verify dp amount tidak terlock'
		WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'),'readonly',GlobalVariable.TimeOut)
	}
	else if(resultDP.get(1)=="LOCK"){
		'verify dp percentage terlock'
		WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'),'readonly',GlobalVariable.TimeOut)
		
		'verify dp amount terlock'
		WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'),'readonly',GlobalVariable.TimeOut)
	}
}

if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Percentage') {
    'Untuk handle jika field input percentage terlock'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'), 
        'Amount', false)

    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment Asset Information'), 
        'Percentage', false)

    'input down payment percentage'
    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'), 
        Keys.chord(Keys.CONTROL, 'a'))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'), 
        Keys.chord(Keys.BACK_SPACE))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'), 
        Keys.chord(Keys.HOME))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'), 
        Keys.chord(Keys.RIGHT, datafileTabAsset.getValue(GlobalVariable.NumofColm, 27)))
} else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Amount') {
    'input down payment amount'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 28))
}

'input chasis number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 29))

'input engine number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 30))

if (GlobalVariable.Role == 'Testing') {
    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Percentage') {
        'get downpaymentamount from confins'
        DownPaymentAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentAmt'), 
            'value')

        DownPaymentAmtExcel = datafileTabAsset.getValue(GlobalVariable.NumofColm, 70)

        'verify down payment value equal'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(DownPaymentAmt, DownPaymentAmtExcel, false))
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 26) == 'Amount') {
        'get downpayment percentage from confins'
        DownPaymentPrctg = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)_downPaymentPrctg'), 
            'value').replaceAll(' %', '')

        'get dp percentage dari excel'
        DownPaymentPrctgExcel = datafileTabAsset.getValue(GlobalVariable.NumofColm, 69)

        'verify down payment value equal'
        checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(DownPaymentPrctg)), Math.round(Double.parseDouble(
                        DownPaymentPrctgExcel))))
    }
}

'input license plate number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'), 
    datafileTabAsset.getValue(GlobalVariable.NumofColm, 31))

'Jika ada serial 4 pada confins'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input serial 4'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
}

'Jika ada serial 5 pada confins'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input serial 5'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
}

'Jika asset condition excel = used'
if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 18) == 'Used') {
    'Jika check rapindo = yes'
    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 62) == 'Yes') {
        'click button check rapindo'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_CheckRapindo'))
    }
}

'get full asset code from excel'
String Fullassetcode = datafileTabAsset.getValue(GlobalVariable.NumofColm, 17)

'count asset attribute'
String countAssetAtrtibute = CustomKeywords.'customizeKeyword.getRowAssetAttribute.countRowAssetAttribute'(sqlConnectionFOU, 
    sqlConnectionLOS, Fullassetcode, POName)

'looping asset attribute'
for (int i = 1; i <= Integer.parseInt(countAssetAtrtibute); i++) {
    'modify Asset Attribute Input Text'
    modifyObjectAssetAttributeInputText = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetAttribute'), 
        'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="text"]', 
        true)

    'modify Asset Attribute Select Dropdownlist'
    modifyObjectAssetAttributeList = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetAttribute'), 
        'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/select', true)

    'modify Asset Attribute Input Date'
    modifyObjectAssetAttributeInputDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetAttribute'), 
        'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="date"]', 
        true)

    'modify Asset Attribute Input Number '
    modifyObjectAssetAttributeInputNumber = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetAttribute'), 
        'xpath', 'equals', ('//*[@id="RefAttrContentAppAssetAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@formcontrolname="AttrValue"]', 
        true)

    if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputText, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'input "Attribute"'
        WebUI.setText(modifyObjectAssetAttributeInputText, 'Attr', FailureHandling.OPTIONAL)
    } else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeList, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'select option index 1'
        WebUI.selectOptionByIndex(modifyObjectAssetAttributeList, 1, FailureHandling.OPTIONAL)
    } else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputDate, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'input date 01/01/2000'
        WebUI.setText(modifyObjectAssetAttributeInputDate, '01/01/2000', FailureHandling.OPTIONAL)
    } else if (WebUI.verifyElementPresent(modifyObjectAssetAttributeInputNumber, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'input number 0'
        WebUI.setText(modifyObjectAssetAttributeInputNumber, '0', FailureHandling.OPTIONAL)
    }
}

'keyword untuk mencari nama-nama asset document yang muncul pada tab asset'
ArrayList<String> docName = CustomKeywords.'assetData.checkAssetDoc.checkAssetDocument'(sqlConnectionFOU, Fullassetcode)

'looping asset document'
for (int i = 0; i < docName.size(); i++) {
    'modify document name'
    modifyDocumentName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/td_AssetDocument'), 
        'xpath', 'equals', ('//*[@id=\'AddAssetDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[1]', true)

    'get text document name'
    textDocumentName = WebUI.getText(modifyDocumentName)

    'Verif document name yang muncul pada confins sesuai dengan db'
    if (WebUI.verifyMatch(textDocumentName, docName[i], false) == false) {
        checkVerifyEqualOrMatch(false)

        break
    }
    
    'modify object received'
    modifyReceived = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Received'), 
        'xpath', 'equals', ('//*[@id=\'AddAssetDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[2]/input', true)

    'modify object document no'
    modifyDocumentNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_DocumentNo'), 
        'xpath', 'equals', ('//*[@id=\'AddAssetDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[3]/input', true)

    'modify object expired date'
    modifyExpiredDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_ExpiredDate'), 
        'xpath', 'equals', ('//*[@id=\'AddAssetDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[4]/input', true)

    'modify document notes'
    modifyDocumentNotes = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_DocumentNotes'), 
        'xpath', 'equals', ('//*[@id=\'AddAssetDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[5]/input', true)

    'Pengecekan nilai received pada excel "yes" atau "no" dan pengecekan kondisi checkbox tercentang atau tidak'
    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 64).equalsIgnoreCase('Yes') && WebUI.verifyElementNotChecked(
        modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'centang received'
        WebUI.check(modifyReceived)
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 64).equalsIgnoreCase('No') && WebUI.verifyElementChecked(
        modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'uncentang received'
        WebUI.uncheck(modifyReceived)
    }
    
    'input documentNo'
    WebUI.setText(modifyDocumentNo, datafileTabAsset.getValue(GlobalVariable.NumofColm, 65))

    'input expired date'
    WebUI.setText(modifyExpiredDate, datafileTabAsset.getValue(GlobalVariable.NumofColm, 66))

    'input document notes'
    WebUI.setText(modifyDocumentNotes, datafileTabAsset.getValue(GlobalVariable.NumofColm, 67))
}

//call test case aksesoris
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAccessoriesAsset'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'cek nilai self usage dari excel'
if (CustomKeywords.'assetData.checkAssetData.checkSelfUsagePersonal'() == true) {
    'click self usage check box'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox (1)'))
} else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 37) == 'No') {
    if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_self Usage Checkbox'), 
        'aria-checked') == 'true') {
        'click self usage check box'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox (1)'))
    }
    
    'input user name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_User Name_form-control ng-untouched ng-pristine ng-valid'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 38))

    if (GlobalVariable.Role == 'Testing') {
        'declare userrelation'
        ArrayList<String> userRelation = new ArrayList<String>()

        'Ambil array string (text) user relationship dari db'
        userRelation = CustomKeywords.'assetData.checkAssetData.checkPersonalRelationshipDDL'(sqlConnectionFOU)

        'declare countuserrelation'
        Integer countUserRelation = userRelation.size()

        'Verif dropdownlist user relationship yang muncul pada confins sesuai dengan array string user relationship dari db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship'), 
            userRelation) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

            GlobalVariable.FlagFailed = 1
        }
        
        'Ambil nilai jumlah option/pilihan user relationship dari confins'
        Integer totalUserRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship'))

        'Verif jumlah user relationship yang muncul pada confins sesuai dengan jumlah user relationship pada db'
        if (WebUI.verifyEqual(totalUserRelation - 1, countUserRelation) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

            GlobalVariable.FlagFailed = 1
        }
    }
    
    'select user relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 39), false)
}

'pengecekan nilai self owner pada excel'
if (CustomKeywords.'assetData.checkAssetData.checkSelfOwnerPersonal'() == true) {
    'click self owner checkbox'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox (1)'))

	'call function get data asset owner address'
	getDataOwner()
	
    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 52) == 'Yes') {
        'function untuk copy address asset location'
        assetLocCopy()
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 52) == 'No') {
        'function untuk input address asset location'
        assetLocInput()
    }
} else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 41) == 'No') {
    if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_self Owner Checkbox'), 
        'aria-checked') == 'true') {
        'click self owner checkbox'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox (1)'))
    }
    
    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Personal') {
        'click radio button personal'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Personal'))

        if (GlobalVariable.Role == 'Testing') {
            'declare ownerpersonalrelation'
            ArrayList<String> ownerPersonalRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerPersonalRelation = CustomKeywords.'assetData.checkAssetData.checkPersonalRelationshipDDL'(sqlConnectionFOU)

            'declare countonwerpersonalrelation'
            Integer countOwnerPersonalRelation = ownerPersonalRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
                ownerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                    GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerPersonalRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerPersonalRelation - 1, countOwnerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                    GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

                GlobalVariable.FlagFailed = 1
            }
        }
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Company') {
        'click radio button company'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Company'))

        if (GlobalVariable.Role == 'Testing') {
            'declare ownercompanyrelation'
            ArrayList<String> ownerCompanyRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerCompanyRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

            'declare countownercompanyrelation'
            Integer countOwnerCompanyRelation = ownerCompanyRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
                ownerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                    GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerCompanyRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerCompanyRelation - 1, countOwnerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                    GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL)

                GlobalVariable.FlagFailed = 1
            }
			
			'get ownerprofession ddl value from db'
			ArrayList<String> ownerprofession = CustomKeywords.'dbConnection.checkCustomer.checkCompanyTypeDDL'(sqlConnectionFOU)
			
			'get total label from ddl ownerprofession'
			int totalddlownerprofession = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession'))
		
			'verify total ddl ownerprofession confins = total ddl db'
			WebUI.verifyEqual(totalddlownerprofession - 1, ownerprofession.size())
			
			'verify isi ddl ownerprofession confins = db'
			if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession'),
				ownerprofession) == false) {
		
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL + 'ownerprofession')
		
				(GlobalVariable.FlagFailed)++
			}
        }
    }
    
    'input owner name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Name_form-control ng-untouched ng-pristine ng-invalid'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 43))

    'select owner relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 44), false)

    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Personal') {
        if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 46).length() > 1) {
            'click button owner profession'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Owner Profession_btn btn-raised btn-primary'))

            'input profession code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Profession Code_professionCodeId'), 
                datafileTabAsset.getValue(GlobalVariable.NumofColm, 45))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
            }
        }
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 42) == 'Company') {
        'select company owner profession'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 47), false)
    }
    
	if(GlobalVariable.RoleCompany == 'Testing'){
		
		'get IDType ddl value from db'
		ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)
		
		'get total label from ddl IDType'
		int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-ID Type'))
	
		'verify total ddl IDType confins = total ddl db'
		WebUI.verifyEqual(totalddlIDType - 1, IDType.size())
		
		'verify isi ddl IDType confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-ID Type'),
			IDType) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL + 'IDType')
	
			(GlobalVariable.FlagFailed)++
		}
			
		'get copyaddr ddl value from db'
		ArrayList<String> copyaddr = CustomKeywords.'dbConnection.checkCustomer.checkCopyAddr'(sqlConnectionLOS, datafileTabCustomer.getValue(GlobalVariable.NumofColm, 13))
			
		'get total label from ddl copyaddr asset owner'
		int totalddlcopyaddrassetowner = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- JOB  LEGAL  MAILING  RESIDENCE'))
		
		'get total label from ddl copyaddr asset location'
		int totalddlcopyaddrassetlocation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'))
				
		'verify total ddl totalddlcopyaddrassetowner confins = total ddl db'
		WebUI.verifyEqual(totalddlcopyaddrassetowner - 1, copyaddr.size())
		
		'verify total ddl ownerprofession confins = total ddl db'
		WebUI.verifyEqual(totalddlcopyaddrassetlocation - 1, copyaddr.size())
			
		'verify isi ddl copyaddr asset owner confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- JOB  LEGAL  MAILING  RESIDENCE'),
				copyaddr) == false) {
				
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL + 'copyaddr asset owner')
					
			(GlobalVariable.FlagFailed)++
		}
				
		'verify isi ddl copyaddr asset location confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'),
				copyaddr) == false) {
						
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL + 'copyaddr asset location')
					
			(GlobalVariable.FlagFailed)++
		}
	}
	
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-ID Type'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 48), false)

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Id No'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 49))

    'input owner mobile no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Mobile Phone No'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 50))

    if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 52) == 'Yes') {
        'select copy address'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- JOB  LEGAL  MAILING  RESIDENCE'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 53), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Copy'))

		'call function get data asset owner address'
		getDataOwner()
		
        'function untuk copy address asset location'
        assetLocCopy()
    } else if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 52) == 'No') {
        'input address'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 54))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RT'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 55))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RW'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 56))

        'click button Zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Zipcode_btn btn-raised btn-primary'))

        'input Zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 57))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KecamatanAssetOwner'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 59))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KelurahanAssetOwner'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 58))

        'input Kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KotaAssetOwner'), 
            datafileTabAsset.getValue(GlobalVariable.NumofColm, 60))

        'click search button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData'), 
            '7.TabAssetData', GlobalVariable.NumofColm)

        'function untuk input address asset location'
        assetLocInput()
    }
}

//try{
'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Save'))
//}
//catch(Exception e){
//	'click button save'
//	WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/SaveAsset'))
//}

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal == "Yes"){
	'Pengecekan jika dp percentage dibawah nilai minimum atau diatas nilai maksimum atau manufacturing year dibawah minimum berdasarkan rule'
	if(Double.parseDouble(datafileTabAsset.getValue(GlobalVariable.NumofColm, 69))<Double.parseDouble(resultDP.get(2))||Double.parseDouble(datafileTabAsset.getValue(GlobalVariable.NumofColm, 69))>Double.parseDouble(resultDP.get(3)) || Integer.parseInt(datafileTabAsset.getValue(GlobalVariable.NumofColm, 25))<Integer.parseInt(resultManuYear.get(0))){
		'verify alert muncul'
		WebUI.verifyAlertPresent(GlobalVariable.TimeOut)
	}
}
else {
	'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
	WebUI.waitForAlert(3)
	
}

'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
WebUI.acceptAlert(FailureHandling.OPTIONAL)

if (datafileTabAsset.getValue(GlobalVariable.NumofColm, 62) == 'No') {
    'Menunggu Alert konfirmasi integrator muncul'
    WebUI.waitForAlert(3)

    'Accept Alert Konfirmasi Integrator'
    WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

WebUI.delay(2)

Integer iscompleteMandatory = Integer.parseInt(datafileTabAsset.getValue(GlobalVariable.NumofColm, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '7.TabAssetData')
}

WebUI.delay(13)

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'), 
        GlobalVariable.NumofColm, '7.TabAssetData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '7.TabAssetData')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
} else {
    if (flagWarning > 0) {
        'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)
    }
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/Accessories').getValue(GlobalVariable.NumofAccessories, 1) == 'SUCCESS'){		
    'call test case store db accessories data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAccessoriesDataStoreDBVerif'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
	}

    'call test case store db asset data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetDataStoreDBVerif'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(10)

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def assetLocInput() {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_AddressText'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 54))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRT'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 55))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRW'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 56))

    'click button zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonZipcode'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inptZipcode'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 57))

    'input Kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKecamatan'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 59))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKelurahan'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 58))

    'input Kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKota'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 60))

    'click search button'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData'), 
        '7.TabAssetData', GlobalVariable.NumofColm)
}

def assetLocCopy() {
    'select copy address'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'), 
        datafileTabAsset.getValue(GlobalVariable.NumofColm, 53), false)

    'click button copy'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonCopy'))
}

def getDataOwner() {
	'declare array for confins data'
	def confinsdata = []
	
	if(datafileTabAsset.getValue(GlobalVariable.NumofColm, 41) == 'Yes'){

		'add owner type to array'
		confinsdata.add('PERSONAL')
		
		'add owner name to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Name_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
		
		Select ownerRelation = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="userRelationship"]')))
		Select ownerIdType = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="idType"]')))
		String ownerRelationLabel = ownerRelation.getFirstSelectedOption().getText()
		String ownerIdTypeLabel = ownerIdType.getFirstSelectedOption().getText()
		
		'add owner relation to array'
		confinsdata.add(ownerRelationLabel)
		
		'add owner profession to array'
		confinsdata.add(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelOwnerProfession')))
		
		'add owner id type to array'
		confinsdata.add(ownerIdTypeLabel)
		
		'add owner id no to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Id No'),
				'value'))
		
		'add owner owner mobile no to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Mobile Phone No'),
				'value'))
	}

	'add address to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'),
			'value'))

	'add RT to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RT'),
			'value'))

	'add RW to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RW'),
			'value'))

	if(datafileTabAsset.getValue(GlobalVariable.NumofColm, 41) == 'No'){
		'modify Asset Owner Zipcode'
		modifyObjectAssetOwnerZipcode = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelZipcodeAssetOwner'),
			'xpath', 'equals', '//*[@id="ownerData"]/div/div[2]/div/div[1]/div/lib-uclookupgeneric/div/div/input',
			true)
		
		'add zipcode to array'
		confinsdata.add(WebUI.getAttribute(modifyObjectAssetOwnerZipcode, 'value'))
	}else if(datafileTabAsset.getValue(GlobalVariable.NumofColm, 41) == 'Yes'){
		'add zipcode to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelZipcodeAssetOwner'), 'value'))
	}
	
	'add kelurahan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelKelurahanAssetOwner'),
			'value'))

	'add kecamatan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelKecamatanAssetOwner'),
			'value'))

	'add kota to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelKotaAssetOwner'),
			'value'))
	
	GlobalVariable.Confinsdata = confinsdata
}