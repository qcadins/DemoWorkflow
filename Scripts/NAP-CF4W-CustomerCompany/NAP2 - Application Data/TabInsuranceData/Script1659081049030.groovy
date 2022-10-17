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

GlobalVariable.FlagFailed = 0

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'INSURANCE', false, FailureHandling.OPTIONAL)
	
	'Verifikasi perhitungan asset price'
	WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AssetPrice')).replace(
			',', ''), String.format('%.2f', GlobalVariable.AssetPrice), false)
	
	
	'Verifikasi perhitungan asset price incl accessories'
	WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/label_AssetPriceInclAcc')).replace(
			',', ''), String.format('%.2f', GlobalVariable.TotalAccessoriesPrice + GlobalVariable.AssetPrice), false)
}

String insuredBy = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
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

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 4)) == 0) {
'Check alert'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '8.TabInsuranceData')
}

WebUI.delay(5)

if(GlobalVariable.FlagFailed == 0){
'check save process write to excel'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 4)),
	findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), GlobalVariable.NumofColm, '8.TabInsuranceData')

if(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 4)) == 0){
'check error validasi'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/errorvalidasi'), GlobalVariable.NumofColm, '8.TabInsuranceData')
}

}

if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
	'call test case verify store to db'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceDataStoreDBVerif'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

'Verify input data'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
	5, FailureHandling.OPTIONAL)) {
	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

 
}

