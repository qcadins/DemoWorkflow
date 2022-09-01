package checkFailedReason

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

public class checkFailedReason {

	@Keyword
	public checkStatus (int count, TestObject object, int colm, String sheetname){
		if(count == 0 && WebUI.verifyElementPresent(object, 10, FailureHandling.OPTIONAL)){
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusSuccess)
		}else if(count != 0 &&  WebUI.verifyElementNotPresent(object, 10, FailureHandling.OPTIONAL)){
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusFailed)
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					1, colm - 1, GlobalVariable.StatusReasonMandatoryEmpty)
		}else if(count != 0 &&  WebUI.verifyElementPresent(object, 10, FailureHandling.OPTIONAL)){
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusFailed)
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					1, colm - 1, GlobalVariable.StatusReasonSystem)
		}else if(count == 0 &&  WebUI.verifyElementNotPresent(object, 10, FailureHandling.OPTIONAL)){
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					0, colm - 1, GlobalVariable.StatusFailed)
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, sheetname,
					1, colm - 1, GlobalVariable.StatusReasonSaveGagal)
		}
	}
}
