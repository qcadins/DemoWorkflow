package sortPaging

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class verifyReset {
	@Keyword
	public resetPaging(){

		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),"0002APP20211204835")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),"AMIR")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),"CF4W MRA")
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_taskClaimStatus'),"CLAIMED",false)
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_ClaimBy'),"USER5")
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),'value'),"",false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),'value'),"",false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),'value'),"",false)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_taskClaimStatus'),"ALL",false,2)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_ClaimBy'),'value'),"",false)
	}

	@Keyword
	public resetPagingAppInquiry(){

		WebUI.setText(findTestObject('Object Repository/AppView/input_AppNo'),"0002APP20210800615_1")
		WebUI.setText(findTestObject('Object Repository/AppView/input_CustNo'),"0002CUST20210802912	")
		WebUI.setText(findTestObject('Object Repository/AppView/input_CustName'),"CALVIN CEN")
		WebUI.setText(findTestObject('Object Repository/AppView/input_AgreementNo'),"0002AGR20210803930")
		WebUI.setText(findTestObject('Object Repository/AppView/input_POname'),"CF4W_ACP_AUG")
		WebUI.setText(findTestObject('Object Repository/AppView/input_appDate'),"12/01/2021")
		WebUI.setText(findTestObject('Object Repository/AppView/input_appDateKurangDari'),"12/15/2021")
		WebUI.selectOptionByLabel(findTestObject('Object Repository/AppView/select_currentAppStep'),"Approval",false)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/AppView/select_currentAgreementStep'),"CANCEL",false)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/AppView/select_ApplicationStatus'),"Cancel",false)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/AppView/select_ContractStatus'),"Cancel",false)
		WebUI.selectOptionByLabel(findTestObject('Object Repository/AppView/select_CustomerCheckingStep'),"Customer Data Completion Done",false)
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_AppNo'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_CustNo'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_CustName'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_AgreementNo'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_POname'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_appDate'), 'value'),"", false)
		WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/AppView/input_appDateKurangDari'), 'value'),"", false)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/AppView/select_currentAppStep'),"ALL",false,2)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/AppView/select_currentAgreementStep'),"ALL",false,2)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/AppView/select_ApplicationStatus'),"ALL",false,2)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/AppView/select_ContractStatus'),"ALL",false,2)
		WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/AppView/select_CustomerCheckingStep'),"ALL",false,2)
	}
}
