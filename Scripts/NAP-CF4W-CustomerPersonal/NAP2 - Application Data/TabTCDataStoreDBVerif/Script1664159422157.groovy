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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TermConditionStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

int arrayindex = 0

int flagFailed = 0

datafiletermcondition = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData')

def YesUncheckArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 12).split(';', -1)

def PromiseDateArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def RequiredNoCheckArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def ExpiredDocArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def ExpiredDateArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def WaivedCheckArray = datafiletermcondition.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

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
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}