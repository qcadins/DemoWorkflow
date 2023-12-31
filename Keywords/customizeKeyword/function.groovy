package customizeKeyword
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

public class function {
	//keyword verify input error lookup
	@Keyword
	public verifyInputLookup(TestData testdata, String sheet, int colm){
		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'),
		10, FailureHandling.OPTIONAL)) {
			'click select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'))
		} else {
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_X'))

			'Write To Excel GlobalVariable.StatusFailed'
			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheet,
					0, colm - 1, GlobalVariable.StatusFailed)

			'Write To Excel GlobalVariable.StatusReasonLookup'
			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheet,
					1, colm - 1, testdata.getValue(colm, 2) + ';' + GlobalVariable.StatusReasonLookup)

			'GlobalVariable.FlagFailed =1 karena lookup gagal'
			GlobalVariable.FlagFailed = 1

			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
			}
		}
	}

	//keyowrd untuk check apakah NAP exist
	@Keyword
	public checkNAPExist(TestData datafilecust){
		if(datafilecust.getValue(GlobalVariable.NumofColm, 8)=="" && datafilecust.getValue(GlobalVariable.NumofColm, 12)=="" &&datafilecust.getValue(GlobalVariable.NumofColm, 9)==""){
			return true
		}else {
			return false
		}
	}
}
