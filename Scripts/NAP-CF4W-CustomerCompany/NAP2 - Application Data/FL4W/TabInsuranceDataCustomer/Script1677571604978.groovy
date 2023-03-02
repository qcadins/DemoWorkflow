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

'declare excelPathTabInsurance'
excelPathTabInsurance = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabInsuranceData'

'Input Insco Branch Name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_Insco Branch Name Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 14))

'Input Coverage Amount'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_Coverage Amount Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 15))

'Input Policy No'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_Policy No Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 16))

'Input Policy Name'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_Policy Name Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 17))

'Input Start Date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_Start Date Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 18))

'Input End Date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/input_End Date Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 19))

'Input Insurance Notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/FL4W/textarea_Insurance Notes Cust'),
	findTestData(excelPathTabInsurance).getValue(
		GlobalVariable.NumofColm, 20))

