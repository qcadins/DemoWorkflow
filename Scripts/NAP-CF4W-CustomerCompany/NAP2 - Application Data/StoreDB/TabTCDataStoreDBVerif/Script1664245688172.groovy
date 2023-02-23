import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebElement

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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TermConditionStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13))

int arrayindex = 0

int flagFailed = 0

'declare excelPathTabTC'
excelPathTabTC = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabTermConditionData'

def YesUncheckArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 12).split(';', -1)

def PromiseDateArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def RequiredNoCheckArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def ExpiredDocArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def ExpiredDateArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def WaivedCheckArray = findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 17).split(';', -1)

for(index = 0 ; index < result.size()/9 ; index++){
	TCdoc = result[arrayindex++]
	
	docRequired = result[arrayindex++]
	
	isChecked = result[arrayindex++]
	
	PromiseDate = result[arrayindex++]
	
	ExpiredDate = result[arrayindex++]
	
	WaivedChecked = result[arrayindex++]
	
	if(docRequired.equalsIgnoreCase('false')){
		if(isChecked.equalsIgnoreCase('true')){
			if(!(RequiredNoCheckArray.contains(TCdoc))){
				flagFailed++
			}
		}
	}else if(docRequired.equalsIgnoreCase('true')){
		if(isChecked.equalsIgnoreCase('false')){
			if(!(YesUncheckArray.contains(TCdoc))){
				flagFailed++
			}
		}
	}
	
	if(PromiseDate != ''){
		if(!(PromiseDateArray.contains(PromiseDate))){
			flagFailed++
		}
	}
	
	if(ExpiredDate != ''){
		if(!(ExpiredDateArray.contains(ExpiredDate)) && !(ExpiredDocArray.contains(TCdoc))){
			flagFailed++
		}
	}
	
	if(WaivedChecked.equalsIgnoreCase('true')){
		if(!(WaivedCheckArray.contains(TCdoc))){
			flagFailed++
		}
	}
}

if(flagFailed > 0){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabTermConditionData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
}