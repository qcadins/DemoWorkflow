package commissionData

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

import groovy.sql.Sql
import internal.GlobalVariable

public class commission {

	public calculateCommissionSummary(Object modifyObjectCommAmt, Object modifyObjectAmtAftTax, Object modifyObjectTax, Object modifyObjectVat, Object modifyObjectDisburse, Object modifyObjectExpense){
		Double commAmt = Double.parseDouble(WebUI.getText(modifyObjectCommAmt).replace(',',''))
		Double commAmtAftTax = Double.parseDouble(WebUI.getText(modifyObjectAmtAftTax).replace(',',''))
		Double taxAmt = Double.parseDouble(WebUI.getText(modifyObjectTax).replace(',',''))
		Double vatAmt = Double.parseDouble(WebUI.getText(modifyObjectVat).replace(',',''))
		Double disburseAmt = Double.parseDouble(WebUI.getText(modifyObjectDisburse).replace(',',''))
		Double expenseAmt = Double.parseDouble(WebUI.getText(modifyObjectExpense).replace(',',''))
		GlobalVariable.TotalCommissionAmt+=commAmt
		GlobalVariable.TotalCommissionAmtAftTax+=commAmtAftTax
		GlobalVariable.TotalDisburseAmt+=disburseAmt
		GlobalVariable.TotalExpenseAmt+=expenseAmt
		GlobalVariable.TotalTax+=taxAmt
		GlobalVariable.TotalVat+=vatAmt
	}
}
