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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1 DataFile_NAP_"+ GlobalVariable.LOB +".xlsx")

'Klik tab asset'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabAsset'))

'koneksi los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathTabApplication'
excelPathTabApplication = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabApplicationData'

'declare excelPathAccessories'
excelPathAccessories = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/Accessories'

'declare excelPathTabAsset'
excelPathTabAsset = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabAssetData'

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
String POName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_ProductOfferingFL4W'))

'Ambil text original office dari confins'
String office = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OriginalOfficeFL4W'))

'click button supplier lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_FL4W'))

'call function check SupplierScheme'
checkSupplierScheme(sqlConnectionLOS, sqlConnectionFOU, POName, office)

'input supplier code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_SupplierCode'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 12))

'input supplier name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 13))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

'verify input error'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'), GlobalVariable.TimeOut, 
    FailureHandling.OPTIONAL)) {
    'Ambil text supplier name dari lookup confins'
    suppName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/span_suppNameLookup'))

    'click select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/a_Select'))
} else {
    'click X'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_XAccessories'))

    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)

    GlobalVariable.FlagFailed = 1

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA FL4W'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
    }
}

'call function check ddl supplier info'
checkDDLSupplierInfo(sqlConnectionFOU, suppName)

'select sales person'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_SalesPerson'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 14), false)

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 15).length() > 0) {
    'select admin head'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_AdminHead'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 15), false)
}

'click button asset lookup'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Asset Name_FL4W'))

'call function check lookup asset'
checkLookupAsset(sqlConnectionLOS, sqlConnectionFOU, POName)

'input asset name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_FullAssetCode'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 17))

'click search button'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Asset'))

'verify input error'
CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabAsset), 
    '7.TabAssetData', GlobalVariable.NumofAsset)

'call function check asset info dll'
checkDDLAssetInfo(sqlConnectionFOU, sqlConnectionLOS)

'select asset condition'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 18), false)

'input asset price'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Asset Price_FL4W'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 19))

'select asset usage'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 20), false)

'input color'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Color'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 21))

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 22).length() > 0) {
    'click button BPKB Lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_BPKB City Issuer_FL4W'))

    'input district code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_District Code'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 22))

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

        if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 20) == 'New') {
            flagWarning++
        }
    }
}

'input BPKP issue date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_BPKB Issue Date'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 23))

'input notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/textarea_NotesFL4W'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 24))

ArrayList<String> resultManuYear, resultDP

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRuleCompany == "Yes"){
	'check rule validasi tab asset'
	HashMap<String, ArrayList> resultAssetValidation =  CustomKeywords.'assetData.checkAssetValidation.checkValidation'(sqlConnectionLOS, sqlConnectionFOU, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm,12), findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset,12), findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset,17), findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset,18).toUpperCase(), findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset,25), findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofAsset,20))
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
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Manufacturing Year'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 25))

'select security deposit type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment FL4W'), 
    findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26), false)

'click untuk refresh page'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'))

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRuleCompany == "Yes"){
	'verify dp percentage default sesuai rule'
	WebUI.verifyEqual(Double.parseDouble(resultDP.get(0)),Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'),'value').replace(" %","")))
	
	'Pengecekan behaviour pada inputan dp'
	if(resultDP.get(1)=="DEF" && findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Percentage'){
		'verify dp percentage tidak terlock'
		WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'),'readonly',GlobalVariable.TimeOut)
	}
	else if(resultDP.get(1)=="DEF" && findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Amount'){
		'verify dp amount tidak terlock'
		WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)-FL4W'),'readonly',GlobalVariable.TimeOut)
	}
	else if(resultDP.get(1)=="LOCK"){
		'verify dp percentage terlock'
		WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'),'readonly',GlobalVariable.TimeOut)
		
		'verify dp amount terlock'
		WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)-FL4W'),'readonly',GlobalVariable.TimeOut)
	}
}

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Percentage') {
    'Untuk handle jika field input percentage terlock'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment FL4W'), 
        'Amount', false)

    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Select_Downpayment FL4W'), 
        'Percentage', false)

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'), 
        Keys.chord(Keys.CONTROL, 'a'))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'), 
        Keys.chord(Keys.BACK_SPACE))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'), 
        Keys.chord(Keys.HOME))

    WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'), 
        Keys.chord(Keys.RIGHT, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 27)))
} else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Amount') {
    'input security deposit amount'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)-FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 28))
}

'input chasis number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER_FL4W'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 29))

'input engine number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER-FL4W'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 30))

if (GlobalVariable.Role == 'Testing') {
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Percentage') {
        'get attribute dp amount dari confins'
        DownPaymentAmt = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Amt)-FL4W'), 
            'value')

        'get DP amount dari excel'
        DownPaymentAmtExcel = findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 70)

        'verify security deposit value equal'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(DownPaymentAmt, DownPaymentAmtExcel, false))
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26) == 'Amount') {
        'get attribute dp percentage dari confins'
        DownPaymentPrctg = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Down Payment (Prctg)-FL4W'), 
            'value').replace(' %', '')

        'get dp percentage dari excel'
        DownPaymentPrctgExcel = findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 69)

        'verify security deposit value equal'
        checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(DownPaymentPrctg)), Math.round(Double.parseDouble(
                        DownPaymentPrctgExcel))))
    }
}

'input license plate number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER_FL4W'), findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofAsset, 31))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4_FL4W'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input serial 4'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4_FL4W'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 32), FailureHandling.OPTIONAL)
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5_FL4W'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input serial 5'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5_FL4W'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 33), FailureHandling.OPTIONAL)
}

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 18) == 'Used') {
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 62) == 'Yes') {
        'click button check rapindo'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_CheckRapindo'))
    }
}

String Fullassetcode = findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 17)

'count asset attribute'
String countAssetAtrtibute = CustomKeywords.'customizeKeyword.getRowAssetAttribute.countRowAssetAttribute'(sqlConnectionFOU, 
    sqlConnectionLOS, Fullassetcode, POName)

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
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 64).equalsIgnoreCase('Yes') && WebUI.verifyElementNotChecked(
        modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'centang received'
        WebUI.check(modifyReceived)
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 64).equalsIgnoreCase('No') && WebUI.verifyElementChecked(
        modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'uncentang received'
        WebUI.uncheck(modifyReceived)
    }
    
    'input documentNo'
    WebUI.setText(modifyDocumentNo, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 65))

    'input expired date'
    WebUI.setText(modifyExpiredDate, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 66))

    'input document notes'
    WebUI.setText(modifyDocumentNotes, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 67))
}

//call test case aksesoris
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabAccessoriesAsset'), [:], FailureHandling.CONTINUE_ON_FAILURE)

if (CustomKeywords.'assetData.checkAssetData.checkSelfUsage'(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox Input FL4W'), GlobalVariable.NumofAsset, 37, excelPathTabAsset) == true) {
    'click self usage check box'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox FL4W'))
} else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 37) == 'No') {
    if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/status selfusage checkbox_FL4W'), 
        'aria-checked') == 'true') {
        'click self usage check box'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Usage Checkbox FL4W'))
    }
    
    'input user name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_User Name_FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 38))

    if (GlobalVariable.Role == 'Testing') {
        ArrayList<String> userRelation = new ArrayList<String>()

        'Ambil array string (text) user relationship dari db'
        userRelation = CustomKeywords.'assetData.checkAssetData.checkPersonalRelationshipDDL'(sqlConnectionFOU)

        Integer countUserRelation = userRelation.size()

        'Verif dropdownlist user relationship yang muncul pada confins sesuai dengan array string user relationship dari db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship-FL4W'), 
            userRelation) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Value User Relationship')

            GlobalVariable.FlagFailed = 1
        }
        
        'Ambil nilai jumlah option/pilihan user relationship dari confins'
        Integer totalUserRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship-FL4W'))

        'Verif jumlah user relationship yang muncul pada confins sesuai dengan jumlah user relationship pada db'
        if (WebUI.verifyEqual(totalUserRelation, countUserRelation) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Jumlah User Relationship')

            GlobalVariable.FlagFailed = 1
        }
    }
    
    'select user relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_User Relationship-FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 39), false)
}

if (CustomKeywords.'assetData.checkAssetData.checkSelfOwner'(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox Input FL4W'), GlobalVariable.NumofAsset, 41, excelPathTabAsset) == true) {
    'click self owner checkbox'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox FL4W'))

	'call function get data asset owner address'
	getDataOwner()
	
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52) == 'Yes') {
        assetLocCopy()
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52) == 'No') {
        assetLocInput()
    }
} else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 41) == 'No') {
    if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/status self owner checkbox_FL4W'), 
        'aria-checked') == 'true') {
        'click self owner checkbox'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Self Owner CheckBox FL4W'))
    }
    
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42) == 'Personal') {
        'click radio button Personal'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Personal'))

        if (GlobalVariable.Role == 'Testing') {
            ArrayList<String> ownerPersonalRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerPersonalRelation = CustomKeywords.'assetData.checkAssetData.checkPersonalRelationshipDDL'(sqlConnectionFOU)
			
            Integer countOwnerPersonalRelation = ownerPersonalRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
                ownerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                    GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'value Owner relationship Personal')

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerPersonalRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerPersonalRelation - 1, countOwnerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                    GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Jumlah Owner Relationship Personal')

                GlobalVariable.FlagFailed = 1
            }
        }
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42) == 'Company') {
        'click radio button company'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/div_Company'))

        if (GlobalVariable.Role == 'Testing') {
            ArrayList<String> ownerCompanyRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerCompanyRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

            Integer countOwnerCompanyRelation = ownerCompanyRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
                ownerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                    GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Value Owner Relationship Company')

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerCompanyRelation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerCompanyRelation - 1, countOwnerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
                    GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Jumlah Owner Relationship Company')

                GlobalVariable.FlagFailed = 1
            }
			
			'get ownerprofession ddl value from db'
			ArrayList<String> ownerprofession = CustomKeywords.'dbConnection.checkCustomer.checkCompanyTypeDDL'(sqlConnectionFOU)
			
			'get total label from ddl ownerprofession'
			int totalddlownerprofession = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession-FL4W'))
		
			'verify total ddl ownerprofession confins = total ddl db'
			WebUI.verifyEqual(totalddlownerprofession - 1, ownerprofession.size())
			
			'verify isi ddl ownerprofession confins = db'
			if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession-FL4W'),
				ownerprofession) == false) {
		
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ownerprofession')
		
				(GlobalVariable.FlagFailed)++
			}
        }
    }
    
    'input owner name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Name_FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 43))

    'select owner relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Owner Relationship'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 44), false)

    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42) == 'Personal') {
        if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 46).length() > 1) {
            'click button owner profession'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Owner Profession_FL4W'))

            'input profession code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Profession Code_professionCodeId'), 
                findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 45))

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
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42) == 'Company') {
	    'select company owner profession'
	    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Company Owner Profession-FL4W'), 
	        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 47), false)
    }
    
	
	if(GlobalVariable.Role == 'Testing'){
		
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
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'IDType')
	
			(GlobalVariable.FlagFailed)++
		}
			
		'get copyaddr ddl value from db'
		ArrayList<String> copyaddr = CustomKeywords.'dbConnection.checkCustomer.checkCopyAddr'(sqlConnectionLOS, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))
			
		'get total label from ddl copyaddr asset owner'
		int totalddlcopyaddrassetowner = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset Owner Copy Address_FL4W'))
		
		'get total label from ddl copyaddr asset location'
		int totalddlcopyaddrassetlocation = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'))
				
		'verify total ddl totalddlcopyaddrassetowner confins = total ddl db'
		WebUI.verifyEqual(totalddlcopyaddrassetowner - 1, copyaddr.size())
		
		'verify total ddl ownerprofession confins = total ddl db'
		WebUI.verifyEqual(totalddlcopyaddrassetlocation - 1, copyaddr.size())
			
		'verify isi ddl copyaddr asset owner confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset Owner Copy Address_FL4W'),
				copyaddr) == false) {
				
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'copyaddr asset owner')
					
			(GlobalVariable.FlagFailed)++
		}
				
		'verify isi ddl copyaddr asset location confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'),
				copyaddr) == false) {
						
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'copyaddr asset location')
					
			(GlobalVariable.FlagFailed)++
		}
	}
	
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-ID Type'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 48), false)

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Id No_FL4W'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 49))

    'input owner mobile no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Mobile Phone No_FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 50))

    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52) == 'Yes') {
        'select copy address'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset Owner Copy Address_FL4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 53), false)

        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Copy - FL4W'))

		'call function get data asset owner address'
		getDataOwner()
		
		'call function asset location copy address'
        assetLocCopy()
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52) == 'No') {
        'input address'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 54))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RT'), findTestData(excelPathTabAsset).getValue(
                GlobalVariable.NumofAsset, 55))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_RW'), findTestData(excelPathTabAsset).getValue(
                GlobalVariable.NumofAsset, 56))

        'click button Zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Zipcode_btn btn-raised btn-primary'))

        'input Zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 57))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KecamatanAssetOwner'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 59))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KelurahanAssetOwner'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 58))

        'input Kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_KotaAssetOwner'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 60))

        'click search button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabAsset), 
            '7.TabAssetData', GlobalVariable.NumofAsset)

        assetLocInput()
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Save-FL4W'))

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRuleCompany == "Yes"){
	'Pengecekan jika dp percentage dibawah nilai minimum atau diatas nilai maksimum atau manufacturing year dibawah minimum berdasarkan rule'
	if(Double.parseDouble(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 69))<Double.parseDouble(resultDP.get(2))||Double.parseDouble(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 69))>Double.parseDouble(resultDP.get(3)) || Integer.parseInt(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 25))<Integer.parseInt(resultManuYear.get(0))){
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

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 62) == 'No') {
    'Menunggu Alert konfirmasi integrator muncul'
    WebUI.waitForAlert(1)

    'Accept Alert Konfirmasi Integrator'
    WebUI.acceptAlert(FailureHandling.OPTIONAL)
}

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofAsset, 
        '7.TabAssetData')
}

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetMultiple/buttonAddAsset'), 
        GlobalVariable.NumofAsset, '7.TabAssetData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofAsset, '7.TabAssetData')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_FL4W'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Cancel-FL4W'))
	
	GlobalVariable.IsDataCancel = 1
} else {
    if (flagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofAsset - 
            1, GlobalVariable.StatusWarning)
    }
	
    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    	if(findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories - 1, 1) == 'SUCCESS'){
    		'call test case store db accessories data'
    		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabAccessoriesDataStoreDBVerif'), 
    				[:], FailureHandling.CONTINUE_ON_FAILURE)
    	}
    	
    	'call test case store db asset data'
    	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabAssetDataStoreDBVerif'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}


WebUI.delay(10)

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, 
            GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def assetLocInput() {
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_AddressText-FL4W'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 54))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRT-FL4W'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 55))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_InputRW-FL4W'), findTestData(excelPathTabAsset).getValue(
            GlobalVariable.NumofAsset, 56))

    'click button zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonZipcode-FL4W'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inptZipcode'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 57))

    'input Kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKecamatan'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 59))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKelurahan'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 58))

    'input Kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_inputKota'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 60))

    'click search button'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Search Supplier'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabAsset), 
        '7.TabAssetData', GlobalVariable.NumofAsset)
}

def assetLocCopy() {
    'select copy address'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation _ Copy Address'), 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 53), false)

    'click button copy'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/AssetLocation_ButtonCopy-FL4W'))
}

def getDataOwner() {
	'declare array for confins data'
	def confinsdata = []
	
	if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 41) == 'Yes'){

		'add owner type to array'
		confinsdata.add('PERSONAL')
		
		'add owner name to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Name_FL4W'),
				'value'))
		
		Select ownerRelation = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="userRelationship"]')))
		Select ownerIdType = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="idType"]')))
		String ownerRelationLabel = ownerRelation.getFirstSelectedOption().getText()
		String ownerIdTypeLabel = ownerIdType.getFirstSelectedOption().getText()
		
		'add owner relation to array'
		confinsdata.add(ownerRelationLabel)
		
		'add owner profession to array'
		confinsdata.add(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelOwnerProfession - FL4W')))
		
		'add owner id type to array'
		confinsdata.add(ownerIdTypeLabel)
		
		'add owner id no to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Id No_FL4W'),
				'value'))
		
		'add owner owner mobile no to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Owner Mobile Phone No_FL4W'),
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

	if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 41) == 'No'){
		'modify Asset Owner Zipcode'
		modifyObjectAssetOwnerZipcode = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelZipcodeAssetOwnerFL4W'),
			'xpath', 'equals', '//*[@id="ownerData"]/div/div[2]/div/div[1]/div/lib-uclookupgeneric/div/div/input',
			true)
		
		'add zipcode to array'
		confinsdata.add(WebUI.getAttribute(modifyObjectAssetOwnerZipcode, 'value'))
	}else if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 41) == 'Yes'){
		'add zipcode to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/labelZipcodeAssetOwnerFL4W'), 'value'))
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

def checkDDLSupplierInfo(Sql sqlConnectionFOU, String suppName){
	if (GlobalVariable.Role == 'Testing') {
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
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Sales Person')
	
			GlobalVariable.FlagFailed = 1
		}
		
		'Verify array admin head dari db sama dengan opsi dropdownlist admin head confins'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_AdminHead'),
			adminHead) == false) {
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Admin Head')
	
			GlobalVariable.FlagFailed = 1
		}
	}
}

def checkSupplierScheme(Sql sqlConnectionLOS, Sql sqlConnectionFOU, String POName, String office){
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
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)
	
			GlobalVariable.FlagFailed = 1
		}
	}
}

def checkLookupAsset(Sql sqlConnectionLOS, Sql sqlConnectionFOU, String POName){
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
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)
	
			GlobalVariable.FlagFailed = 1
		}
	}
}

def checkDDLAssetInfo(Sql sqlConnectionFOU, Sql sqlConnectionLOS){
	if(GlobalVariable.Role == 'Testing'){
		
		'Ambil text product offering dari confins'
		String POName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_ProductOfferingFL4W'))
	
		'Ambil array string (text) asset usage dari db'
		ArrayList<String> assetUsage = CustomKeywords.'assetData.checkAssetData.checkAssetUsageDDL'(sqlConnectionLOS, POName)
		
		'Ambil array string (text) asset condition dari db'
		ArrayList<String> assetCondition = CustomKeywords.'assetData.checkAssetData.checkAssetConditionDDL'(sqlConnectionFOU)
	
		'Ambil nilai jumlah option/pilihan asset usage dari confins'
		Integer totalAssetUsage = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'))
		
		'Ambil nilai jumlah option/pilihan asset Condition dari confins'
		Integer totalAssetCondition = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'))
		
		'Verif jumlah asset usage yang muncul pada confins sesuai dengan jumlah asset usage pada db'
		if (WebUI.verifyEqual(totalAssetUsage - 1, assetUsage.size()) == false) {
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Jumlah Asset Usage')
	
			GlobalVariable.FlagFailed = 1
		}
		
		'Verif jumlah asset condition yang muncul pada confins sesuai dengan jumlah asset usage pada db'
		if (WebUI.verifyEqual(totalAssetCondition, assetCondition.size()) == false) {
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Jumlah Asset Condition')
	
			GlobalVariable.FlagFailed = 1
		}
		
		'Verif dropdownlist asset usage yang muncul pada confins sesuai dengan array string asset usage dari db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_-Select One- Commercial  Non Commercial'),
			assetUsage) == false) {
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
				GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Value Asset Usage')
	
			GlobalVariable.FlagFailed = 1
		}
			
		'Verif dropdownlist asset condition yang muncul pada confins sesuai dengan array string asset condition dari db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/select_Asset ConditionNew Used'),
				assetCondition) == false) {
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset,
					GlobalVariable.StatusFailed, findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'value Asset condition')
			
			GlobalVariable.FlagFailed = 1
		}
	}
}