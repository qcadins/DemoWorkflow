import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select as Select
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
import groovy.sql.Sql as Sql

def modifyNewAppNo, modifyNewAgrNo, modifyNewCustName, modifyObjectPOName

ArrayList<String> crossfaileddelete = new ArrayList<>()

ArrayList<Integer> posAddCross = new ArrayList<>()

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_CF4W_Company.xlsx")

'declare excelPathTabApplication'
excelPathTabApplication = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabApplicationData'

'Klik tab application'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/buttonTabApplication'))

GlobalVariable.FlagFailed = 0

Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if (appLastStep.equalsIgnoreCase("REFERANTOR")||appLastStep.equalsIgnoreCase("APPLICATION DATA")) {
	'refresh browser supaya tidak muncul error data has been modified'
	WebUI.refresh()
	
	WebUI.delay(3)
	
	if(GlobalVariable.LOB == 'Cf4W'){
		'click menu consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
		
		'click menu new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}else if(GlobalVariable.LOB == 'FL4W'){
		'Klik menu finance leasing 4W'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_FinanceLeasing4W'))

		'click menu new finance leasing'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
	}
}

if((!appLastStep.equalsIgnoreCase("REFERANTOR") && !appLastStep.equalsIgnoreCase("APPLICATION DATA")) && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.RoleCompany == 'Testing') {
	
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'APPLICATION DATA', false, FailureHandling.OPTIONAL))
	
    'Ambil text product offering dari confins'
    String POName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_ProductOfferingCF4W'))

    'Pengecekan interest type dari db product offering '
    String InterestType = CustomKeywords.'applicationData.checkInterestType.checkInterest'(sqlConnectionLOS, POName)

    'Ambil text interest type dari confins'
    String textInterest = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
        'value')

    'Verif interest type pada confins dengan db'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(textInterest, '(?i)' + InterestType, true,FailureHandling.OPTIONAL))
	
	ArrayList<String> appsource = CustomKeywords.'applicationData.checkDDL.checkDDLApplicationSource'(sqlConnectionLOS)
	ArrayList<String> installmentscheme = CustomKeywords.'applicationData.checkDDL.checkDDLInstallmentScheme'(sqlConnectionLOS, POName)
	ArrayList<String> wop = CustomKeywords.'applicationData.checkDDL.checkDDLWOP'(sqlConnectionFOU)
	ArrayList<String> InstType = CustomKeywords.'applicationData.checkDDL.checkDDLInstallmentType'(sqlConnectionFOU)
	ArrayList<String> COC = CustomKeywords.'applicationData.checkDDL.checkDDLCOC'(sqlConnectionFOU)
	ArrayList<String> PayFreq = CustomKeywords.'applicationData.checkDDL.checkDDLPayFreq'(sqlConnectionLOS)
	ArrayList<String> PayType = CustomKeywords.'applicationData.checkDDL.checkDDLPaymentType'(sqlConnectionFOU)
	ArrayList<String> CustNotif = CustomKeywords.'applicationData.checkDDL.checkDDLCustNotif'(sqlConnectionFOU)
	
	'get total label from ddl app source'
	int totalddlappsource = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'))
	
	'get total label from ddl installment scheme'
	int totalddlinstallmentscheme = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme'))
	
	'get total label from ddl wop'
	int totalddlwop = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment'))
	
	'get total label from ddl InstType'
	int totalddlInstType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'))
	
	'get total label from ddl COC'
	int totalddlCOC = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'))
	
	'get total label from ddl PayFreq'
	int totalddlPayFreq = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'))
	
	'get total label from ddl DPSrcPayType'
	int totalddlDPSrcPayType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'))
	
	'get total label from ddl InstSrcPayType'
	int totalddlInstSrcPayType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType'))
	
	'get total label from ddl CustNotif'
	int totalddlCustNotif = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification'))

	'verify total ddl app source confins = total ddl db'
	WebUI.verifyEqual(totalddlappsource - 1, appsource.size())
	
	'verify total ddl installment scheme confins = total ddl db'
	WebUI.verifyEqual(totalddlinstallmentscheme - 1, installmentscheme.size())
	
	'verify total ddl way of payment confins = total ddl db'
	WebUI.verifyEqual(totalddlwop, wop.size())
	
	'verify total ddl InstType confins = total ddl db'
	WebUI.verifyEqual(totalddlInstType - 1, InstType.size())
	
	'verify total ddl coc confins = total ddl db'
	WebUI.verifyEqual(totalddlCOC - 1, COC.size())
	
	'verify total ddl PayFreq confins = total ddl db'
	WebUI.verifyEqual(totalddlPayFreq - 1, PayFreq.size())
	
	'verify total ddl DPSrcPayType confins = total ddl db'
	WebUI.verifyEqual(totalddlDPSrcPayType - 1, PayType.size())
	
	'verify total ddl InstSrcPayType confins = total ddl db'
	WebUI.verifyEqual(totalddlInstSrcPayType - 1, PayType.size())
	
	'verify total ddl CustNotif confins = total ddl db'
	WebUI.verifyEqual(totalddlCustNotif, CustNotif.size())

	'verify isi ddl app source confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'),
		appsource) == false) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Application Source')

		(GlobalVariable.FlagFailed)++
	}
		
	'verify isi ddl installment scheme confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme'),
			installmentscheme) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Installment Scheme')
		
		(GlobalVariable.FlagFailed)++
	}
	
	'verify isi ddl Way of Payment confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment'),
			wop) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'WOP')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl InstType confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'),
			InstType) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'InstType')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl coc confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'),
			COC) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'COC')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl PayFreq confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'),
			PayFreq) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'PayFreq')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl DPSrcPayType confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'),
			PayType, FailureHandling.OPTIONAL) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'DP Source PayType')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl InstSrcPayType confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType'),
			PayType, FailureHandling.OPTIONAL) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Installment Source PayType')
		
		(GlobalVariable.FlagFailed)++
	}
			
	'verify isi ddl CustNotif confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification'),
			CustNotif) == false) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'CustNotif')
		
		(GlobalVariable.FlagFailed)++
	}
}

String spvName

'Pengecekan job title pada excel cmo atau bukan'
if (findTestData('Login/Login').getValue(5, 2).toLowerCase().contains('Credit Marketing Officer'.toLowerCase())) {
	if (GlobalVariable.RoleCompany == 'Testing') {
		
		'Ambil nilai username dari confins'
		String[] userLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_userLogin')).replace(
			' ', '').replace('|', ';').split(';')
		
		String usernameLogin = userLogin[0]
		
		'Ambil text label officer dari confins'
		String textOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_Officer'))

		'Cek nama officer pada db dari username login confins'
		String officerName = CustomKeywords.'applicationData.checkOfficer.checkOfficerName'(sqlConnectionFOU, usernameLogin)

		'Verif username login dengan text label officer'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textOfficer, '(?i)' + officerName, true))

		'Ambil nama spv dari db'
		spvName = CustomKeywords.'applicationData.checkOfficer.checkSPV'(sqlConnectionFOU, usernameLogin)

		'Pengecekan jika nama spv dari db = null'
		if (spvName == null) {
			'Ubah hasil nama spv dari db menjadi -'
			spvName = '-'
		}
		
		'Ambil text spv dari confins'
		String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_SPV'))

		'Verif text spv dari confins sesuai dengan nama spv dari db'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
	}
} else {

	'Pengecekan jika button lookup ada'
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		
		'Ambil text original office dari confins'
		String office = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_OriginalOfficeCF4W'))
	
		'Click Lookup Officer'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'))

		if (GlobalVariable.RoleCompany == 'Testing') {
			'Click Search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))

			'Cek total data officer pada db'
			Integer countOfficer = CustomKeywords.'applicationData.checkOfficer.countOfficerLookup'(sqlConnectionFOU, office)

			'Ambil nilai total data officer pada lookup confins'
			String[] textTotalDataOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
				' ', '').replace(':', ';').split(';')

			'Parsing nilai total data officer confins ke integer(angka)'
			Integer totalDataOfficer = Integer.parseInt(textTotalDataOfficer[1])

			'Verif total data officer confins sesuai dengan db'
			if(WebUI.verifyEqual(totalDataOfficer, countOfficer)==false){
			
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)
				
				GlobalVariable.FlagFailed=1
			}
		}
		
		'Input MO Code'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_MO Code'),
			findTestData(excelPathTabApplication).getValue(
				GlobalVariable.NumofColm, 12))

		'Input MO Head Name'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_MO Head Name'),
			findTestData(excelPathTabApplication).getValue(
				GlobalVariable.NumofColm, 13))

		'Input MO Office Name'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_MOOfficeName'),
			findTestData(excelPathTabApplication).getValue(
				GlobalVariable.NumofColm, 14))

		'Click Search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))

		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/a_Select'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			if (GlobalVariable.RoleCompany == 'Testing') {
				'Ambil nama spv pada lookup confins'
				spvName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/span_SPVLookup'))

				'Pengecekan jika mo officer yang dipilih dari lookup spvnya kosong'
				if (spvName == '') {
					spvName = '-'
				}
			}
			
			'Click Select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/a_Select'),
				FailureHandling.OPTIONAL)

			if (GlobalVariable.RoleCompany == 'Testing') {
				'Ambil nama spv pada confins'
				String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_LookupSPV'))

				'Verif nama spv pada lookup yang diselect sama dengan yang muncul pada tab application data confins'
				checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
			}
		} else {
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/Button_X'))
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)

			GlobalVariable.FlagFailed = 1

			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
			}
		}
	}
}

'Input MO Notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/textarea_MONotes'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 15))

'Select option dropdownlist App Source'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 17), false, FailureHandling.OPTIONAL)

'Select option dropdownlist First Installment Type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 18), false)

'Inisialisasi Variabel'
String payFreq = findTestData(excelPathTabApplication).getValue(
	GlobalVariable.NumofColm, 19)

'Select option dropdownlist Payment Frequency'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'),
	payFreq, false)

'Inisialisasi Variabel'
String tenorString = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 20)

'Input tenor'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Tenor'),
	tenorString)

if (GlobalVariable.RoleCompany == 'Testing') {
	'Inisialisasi Variabel'
	double tenor = Double.parseDouble(tenorString)

	'Inisialisasi Variabel'
	int numOfInstallment

	'Verify numofinstallment berdasarkan tenor dan payment Frequency'
	if (payFreq == 'Monthly') {
		numOfInstallment = ((Math.ceil(tenor / 1)) as int)
	} else if (payFreq == 'Bimonthly') {
		numOfInstallment = ((Math.ceil(tenor / 2)) as int)
	} else if (payFreq == 'Quarterly') {
		numOfInstallment = ((Math.ceil(tenor / 3)) as int)
	} else if (payFreq == 'Trimester') {
		numOfInstallment = ((Math.ceil(tenor / 4)) as int)
	} else if (payFreq == 'Semi Annualy') {
		numOfInstallment = ((Math.ceil(tenor / 6)) as int)
	} else if (payFreq == 'Annualy') {
		numOfInstallment = ((Math.ceil(tenor / 12)) as int)
	} else if (payFreq == 'Weekly') {
		numOfInstallment = (((((4) as int) * tenor) + (Math.round(tenor - 1) / 3)) + 1)
	}
	
	'Click label numofInstallment untuk merefresh numofinstallment'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'))

	'Verify numofinstallment sesuai perhitungan'
	WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'),
		numOfInstallment.toString())
}

if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 21).length() > 0) {
	'Select option dropdownlist DPSourcePaymentType'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'),
		findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 21), false)
}

String textInterestType = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InterestType'),
	'value', FailureHandling.OPTIONAL)

//'Select option dropdownlist Interest Type'
//WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InterestType'),
//    textInterestType, false)
//CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
//	21, GlobalVariable.NumofColm - 1, textInterestType)
scheme = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme')

wop = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment')

notif = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification')

inssource = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType')

'Verify/Jika Interest Type = Float Rate'
if (textInterestType.equalsIgnoreCase('Float Rate')) {
	'Select option dropdownlist Floating Period'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_FloatingPeriod'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 23), false)
}

'Select option dropdownlist installment scheme'
WebUI.selectOptionByLabel(scheme, findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 24), false)

textwop = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 25)

'Select option dropdownlist Way of Payment'
WebUI.selectOptionByLabel(wop, textwop, false)

'Verify/Jika Way of Payment = Auto Debit'
if (textwop == 'Auto Debit') {
    bankacc = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CustomerBankAccount')

    'Select option dropdownlist Customer Bank Account'
    WebUI.selectOptionByIndex(bankacc, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 26))

    Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//select[@formcontrolname = \'CustBankAcc\']')))

    GlobalVariable.BankAccount = select.getFirstSelectedOption().getText()
}

'Select option dropdownlist Customer Notification By'
WebUI.selectOptionByLabel(notif, findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 27), false)

if (findTestData(excelPathTabApplication).getValue(
	GlobalVariable.NumofColm, 28).length() > 0) {
	'Select option dropdownlist Installment Source Payment'
	WebUI.selectOptionByLabel(inssource, findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 28), false)
}

//Cross App/Agr
def appCross = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 63)
def agrCross = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 64)
def custCross = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 65)
	
'declare driver'
WebDriver driver = DriverFactory.getWebDriver()
	
'declare variable'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))
	
if(appCross.length()>0 && agrCross.length()>0 && custCross.length()>0){
	appCrossArr = appCross.split(";",-1)
	agrCrossArr = agrCross.split(";",-1)
	custCrossArr = custCross.split(";",-1)
		
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		for (int i = 1; i <= variable.size(); i++) {
			'modify object application no'
			modifyNewAppNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCross'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[1]", true)
		 
			'modify object agreement no'
			modifyNewAgrNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCrossAgr'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[2]", true)
		 
			'modify object cust name'
			modifyNewCustName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCrossCust'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[3]", true)
		
			for (int crossarray = 1; crossarray <= appCrossArr.size(); crossarray++) {
				if (((WebUI.getText(modifyNewAppNo).equalsIgnoreCase(appCrossArr[(crossarray - 1)]) && WebUI.getText(
					modifyNewAgrNo).equalsIgnoreCase(agrCrossArr[(crossarray - 1)])) && WebUI.getText(modifyNewCustName).equalsIgnoreCase(
					custCrossArr[(crossarray - 1)])) ) {
					
					break
				} else {
					
					'modify object button Delete'
					modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/buttonDelete'),
							'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[4]/a/i", true)
						
					if (crossarray == appCrossArr.size()) {
						if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
								'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
								
							'get agreement no before'
							agrnobefore = WebUI.getText(modifyNewAgrNo)
										
							'click button delete'
							WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)
								
							'accept alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
										
							if(i == variable.size()){
									if (WebUI.verifyElementPresent(modifyNewAgrNo, 5, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										crossfaileddelete.add(agrnobefore)
									}
							}else{
									'get cust name sebelum delete'
									agrNoAfter = WebUI.getText(modifyNewAgrNo)
													
									if (WebUI.verifyMatch(agrNoAfter, agrnobefore, false, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										crossfaileddelete.add(agrnobefore)
									}
							}
								
							i--
						} else {
							break
						}
					}
				}
			}
				
			'check if table cross application/agreement sudah kosong'
			variable = driver.findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))
				
			if(variable.size() == 1){
				break
			}
		}
	}
		
	'Jika ada delete cross app/ agr yang gagal'
	if(crossfaileddelete.size() > 0){
			
		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDelete + crossfaileddelete)
			
		GlobalVariable.FlagWarning++
	}
		
	'declare variabledata'
	ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))
		
	'declare countdata'
	int countData = variableData.size()
		
	'looping cross app excel'
	for (int s = 1; s <= appCrossArr.size(); s++) {
		'looping cross app confins'
		for (int CrossAppCheck = 1; CrossAppCheck <= countData; CrossAppCheck++) {
			if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
				'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		
				'modify object application no'
				modifyNewAppNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCross'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[1]", true)
		 
				'modify object agreement no'
				modifyNewAgrNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCrossAgr'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[2]", true)
					
				'modify object cust name'
				modifyNewCustName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/ModifyCrossCust'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[3]", true)
		
				if (((!((appCrossArr[(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewAppNo))) || !((agrCrossArr[
				(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewAgrNo)))) || !((custCrossArr[(s - 1)]).equalsIgnoreCase(
					WebUI.getText(modifyNewCustName)))) ) {
					if (countData == CrossAppCheck) {
						'add posisi data crossapp pada excel ke arraylist'
						posAddCross.add(s-1)
					}
				} else {
					break
				}
			} else if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
				'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
				if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 63) != '') {
					if (appCrossArr.size() > 0) {
						'add posisi data crossapp pada excel ke arraylist'
						posAddCross.add(s-1)
					}
				}
			}
		}
	}	
}

//if(posAddCross.size()>0){
//	addCrossAppAgr(posAddCross,sqlConnectionLOS)
//}

'Jika/Verify Copy Address From ada isi/tidak kosong pada excel'
if (findTestData(excelPathTabApplication).getValue(
	GlobalVariable.NumofColm, 30).length() > 1) {
	'Select option dropdownlist Copy Address From '
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CopyAddressFrom'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 30), false)

	'Click copy'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Copy'))
	
	'call function get address'
	getAddress()
	
} else {
	'Input Address'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/textarea_Address'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 31))

	'Input RT'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_RT'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 32))

	'Input RW'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_RW'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 33))

	'Click Lookup Zipcode'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Zipcode'))

	'Input Zipcode'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_ZipCodeLookup'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 34))

	'Input Kecamatan'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Kecamatan_kecamatan'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 35))

	'Input Kelurahan'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Kelurahan_Kelurahan'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 36))

	'Input Kota'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Kota_kota'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 37))

	'Click search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))

	'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabApplication), '6.TabApplicationData', GlobalVariable.NumofColm)
}

'Input Phone 1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 area'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 38))

'Input Phone 1 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 number'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 39))

'Input Phone 1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 extension'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 40))

'Input Phone 2 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 area'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 41))

'Input Phone 2 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 number'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 42))

'Input Phone 2 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 extension'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 43))

'Input Phone 3 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 area'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 44))

'Input Phone 3 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 number'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 45))

'Input Phone 3 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 extension'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 46))

'Input Fax Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Fax area'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 47))

'Input Fax Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Fax number'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 48))

String coc = findTestData(excelPathTabApplication).getValue(
	GlobalVariable.NumofColm, 50)

slikbtn = findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Economic Sector (Slik)')

'Select option dropdownlist characteristic of credit'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'),
	coc, false)

'Verify/Jika coc selain lainnya'
if (coc != 'Lainnya') {
	'Input prev agreement no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_PrevAgreementNo'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 51))

	'Select option dropdownlist way of restructure '
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_WayOfRestructure'),
		findTestData(excelPathTabApplication).getValue(
			GlobalVariable.NumofColm, 52), false)
}

'Click lookup slik'
WebUI.click(slikbtn)

'Input Economic Sector Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Economic Sector Code'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 53))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabApplication), '6.TabApplicationData', GlobalVariable.NumofColm)

'Select option dropdownlist Blacklist APPI'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/select_BlackListAppi'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 56), false)

'Input APPI Score'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_APPI SCORE'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 57))

'Input Date APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_DATE APP DATA'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 58))

'Click Lookup REF MASTER APP DATA'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_REF MASTER APP DATA'))

'Input Code REF MASTER APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Code_RefMasterAppData'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 59))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathTabApplication), '6.TabApplicationData', GlobalVariable.NumofColm)

'Input Jumlah Asset'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_JUMLAH ASSET'),
	findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 61))

WebUI.delay(3)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabApplication).getValue(
		GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '6.TabApplicationData')
}

WebUI.delay(5)

if (GlobalVariable.FlagFailed == 0) {
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Asset Name_CF4W'),
		GlobalVariable.NumofColm, '6.TabApplicationData')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '6.TabApplicationData')
	}
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
	'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
	
	GlobalVariable.IsDataCancel = 1
}else{
	if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
			'call test case store db application data'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabApplicationDataStoreDBVerif'),
					[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}

public addCrossAppAgr(ArrayList<Integer> s, Sql sqlConnectionLOS){
	Boolean verifCountDataLookup = false
	Integer countDataCross

	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		verifCountDataLookup = true
	}
	
	'klik button add'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_AddCrossAppAgr'))
	
	if(verifCountDataLookup == true && GlobalVariable.Role=="Testing"){
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))
		
		countDataCross = CustomKeywords.'applicationData.checkAppCrossData.checkCountAppAgr'(sqlConnectionLOS)
		
		'Ambil nilai total data cross agreement pada lookup confins'
		String[] textTotalDataCross = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_TotalDataCross')).replace(
			' ', '').replace(':', ';').split(';')

		'Parsing nilai total data cross confins ke integer(angka)'
		Integer totalDataCross = Integer.parseInt(textTotalDataCross[1])

		'Verif total data cross application confins sesuai dengan db'
		if(WebUI.verifyEqual(totalDataCross, countDataCross)==false){
		
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)
			
			GlobalVariable.FlagFailed=1
		}
		
	}
	for(int i=0;i<s.size();i++){
		'input application no'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_ApplicationNo'),appCrossArr[s[i]])
		
		'input ageement no'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_AgreementNo'),agrCrossArr[s[i]])
		
		'input customer name'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_CustName'),custCrossArr[s[i]])
		
		'klik button search'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Search'))
		
		'verify input lookup'
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_checkboxcross'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'centang checkbox'
			WebUI.check(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_checkboxcross'))
			
		} else {

			'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
			
			continue
		}
	
		'klik button add to temp'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Add To Temp'))
	}
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_SaveLookup'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'klik button save pada lookup'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_SaveLookup'))
	
	}
	else{
		'Klik button X'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/Button_X'))
	}
	
}

def getAddress(){
	
	'declare array for confins data'
	def confinsdata = []

	'add address to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/textarea_Address'),
			'value'))

	'add RT to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_RT'),
			'value'))

	'add RW to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_RW'),
			'value'))

	'add zipcode to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/labelZipcode'), 'value'))
	
	'add kelurahan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/labelKelurahan'),
			'value'))

	'add kecamatan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/labelKecamatan'),
			'value'))

	'add kota to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/labelKota'),
			'value'))
	
	'add Phone 1 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 area'),
			'value'))
	
	'add Phone 1 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 number'),
			'value'))
	
	'add Phone 1 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 1 extension'),
			'value'))
	
	'add Phone 2 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 area'),
			'value'))
	
	'add Phone 2 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 number'),
			'value'))
	
	'add Phone 2 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 2 extension'),
			'value'))
	
	'add Phone 3 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 area'),
			'value'))
	
	'add Phone 3 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 number'),
			'value'))
	
	'add Phone 3 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_Phone 3 extension'),
			'value'))
	
	'add Phone fax area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_fax area'),
			'value'))
	
	'add Phone fax number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/input_fax number'),
			'value'))
	
	GlobalVariable.Confinsdata = confinsdata

}