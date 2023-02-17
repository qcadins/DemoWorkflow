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

if(GlobalVariable.LOB == 'CF4W'){
		
	'click menu customer application data'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/Applicant/a_APPLICATION DATA CF4W'))
	
}else if(GlobalVariable.LOB == 'FL4W'){

	'click menu customer application data'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/Applicant/a_APPLICATION DATA FL4W'))
	
}

'declare findTestData(excelPathCustomerCompany)'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'input appNo'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/Applicant/input_Application No_AppNoId'), 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        13))

'Looping delay untuk handling copy app selama +- 2 menit'
for(int i = 1;i<=8;i++){
	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/Applicant/button_Search'))
	
	'Pengecekan ada/tidak adanya button action pencil yang muncul'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
		break
	}
	else{
		'delay 14 detik'
		WebUI.delay(14)
	}
}

'verify data'
WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/Applicant/td_0002APP20211200947'),
	findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm,
		13), FailureHandling.CONTINUE_ON_FAILURE)