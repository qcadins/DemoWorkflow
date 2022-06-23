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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.Path

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

String insuredBy = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 3)

'Select option dropdownlist insured by'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'), insuredBy, false)

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
	'Memanggil test case Tab Insurance Data Customer'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceDataCustomer'), [:], FailureHandling.CONTINUE_ON_FAILURE)
} 
else if (insuredBy == 'Customer - Multifinance') {
	'Memanggil Test Case Tab Insurance Data Customer untuk mengisi insurance information bagian customer'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceDataCustomer'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	'Memanggil Test Case Tab Insurance Data Multifinance untuk mengisi insurance information bagian company beserta insurance coverage dan diskonnya'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceDataMultifinance'), [:], FailureHandling.CONTINUE_ON_FAILURE)
} 
else if (insuredBy == 'Multifinance') {
	'Memanggil test case Tab Insurance Data Multifinance'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceDataMultifinance'), [:], FailureHandling.CONTINUE_ON_FAILURE)
}else{
	'set value totalinsurance untuk verify di tab financial data'
	GlobalVariable.TotalInsurance = '0.00'
}

WebUI.delay(3)

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Save'))

WebUI.delay(5)

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/ApplicationCurrentStep')), 'INSURANCE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))

    'write to excel failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, "8.TabInsuranceData", 0, GlobalVariable.NumofColm - 1, 
        'FAILED')
} else {
    'write to excel success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, "8.TabInsuranceData", 0, GlobalVariable.NumofColm - 1, 
        'SUCCESS')
}


