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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData')

'Klik tab insurance'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabInsurance'))

GlobalVariable.FlagFailed = 0

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("ASSET & COLLATERAL DATA") && !appLastStep.equalsIgnoreCase("INSURANCE") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'INSURANCE', false, FailureHandling.OPTIONAL))

	if(GlobalVariable.FirstTimeEntry=="Yes"){
		'Verifikasi perhitungan asset price'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AssetPrice')).replace('.00',''), datafileTabInsurance.getValue(GlobalVariable.NumofColm, 88), false))
	
		'Verifikasi perhitungan asset price incl accessories'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AssetPriceInclAcc')).replace('.00',''), datafileTabInsurance.getValue(GlobalVariable.NumofColm, 89), false))
	}
	
	'koneksi fou'
	Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
	
	'get insuredby ddl value from db'
	ArrayList<String> insuredby = CustomKeywords.'insuranceData.checkInsRateBase.checkInsuredbyDDL'(sqlConnectionFOU)
	
	'get total label from ddl insuredby'
	int totalddlinsuredby = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'))

	'verify total ddl insuredby confins = total ddl db'
	WebUI.verifyEqual(totalddlinsuredby - 1, insuredby.size())
	
	'verify isi ddl insuredby confins = db'
	if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
		insuredby) == false) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'insuredby')

		(GlobalVariable.FlagFailed)++
	}
}

String insuredBy = datafileTabInsurance.getValue(
	GlobalVariable.NumofColm, 12)

'Select option dropdownlist insured by'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
	insuredBy, false)

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
	'Memanggil test case Tab Insurance Data Customer'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataCustomer'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (insuredBy == 'Customer - Multifinance') {
	'Memanggil Test Case Tab Insurance Data Customer untuk mengisi insurance information bagian customer'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataCustomer'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)

	'Memanggil Test Case Tab Insurance Data Multifinance untuk mengisi insurance information bagian company beserta insurance coverage dan diskonnya'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataMultifinance'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (insuredBy == 'Multifinance') {
	'Memanggil test case Tab Insurance Data Multifinance'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataMultifinance'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(3)

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Save'))

WebUI.delay(2)

Integer iscompleteMandatory = Integer.parseInt(datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed == 0) {
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm,
		'8.TabInsuranceData')
}

if (GlobalVariable.FlagFailed == 0) {
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'),
		GlobalVariable.NumofColm, '8.TabInsuranceData')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '8.TabInsuranceData')
	}
}

if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
	'call test case verify store to db'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataStoreDBVerif'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

'Verify input data'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
}

public checkVerifyEqualOrMatch(Boolean isMatch){
		if(isMatch==false && GlobalVariable.FlagFailed==0){
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
			GlobalVariable.FlagFailed=1
		}
}