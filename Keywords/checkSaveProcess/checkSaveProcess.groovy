package checkSaveProcess

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
import com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows


import internal.GlobalVariable

public class checkSaveProcess {

	//check status untuk write to excel success / failed + reason failed
	@Keyword
	public checkStatus (int count, TestObject object, int colm, String sheetname){
		if(WebUI.verifyElementPresent(object, 3, FailureHandling.OPTIONAL)){
			if(count==0){
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusSuccess)
			}
			else{
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.StatusReasonSystem)
			}
		}else{
			if(count==0){
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.StatusReasonSaveGagal)
			}
			else{
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.StatusReasonMandatoryEmpty)
			}
		}
	}

	//check validasi untuk write to excel jika failed
	@Keyword
	public checkValidasi (TestObject object, int colm, String sheetname){
		if(WebUI.verifyElementPresent(object, 5, FailureHandling.OPTIONAL)){

			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusFailed)
			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
					1, colm - 1, GlobalVariable.StatusFailedValidasi)
		}
	}

	//check alert pojok kanan atas jika failed akan write to excel failed + reason failed
	@Keyword
	public checkAlert(int colm, String sheetname){
		int flagFailed=0
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 3, FailureHandling.OPTIONAL) &&
		!WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert')).toUpperCase().contains("Success".toUpperCase())){

			String FailedAlertReason = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/texterroralert'), 'aria-label')

			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusFailed)
			(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
					1, colm - 1, FailedAlertReason)
			flagFailed=1
		}
		return flagFailed
	}

	//writeWarningAppView untuk write to excel jika tampilan app view muncul alert
	@Keyword
	public writeWarningAppView(int colm, String sheetname){
		String AlertReason = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/texterroralert'), 'aria-label')

		(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
				0, colm - 1, GlobalVariable.StatusWarning)
		(new customizeKeyword.writeExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
				1, colm - 1, AlertReason)
	}
}

