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
import groovy.sql.Sql as Sql

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathReservedFund'
excelPathReservedFund = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/CommissionReservedFund/TabReservedFundData'

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 'Reserve Fund Amt')+2

ArrayList<String> resultDB = CustomKeywords.'dbConnection.CustomerDataVerif.NAP3ReservedFundDataStoreDB'(sqlconnection, findTestData(excelPathCustomerCompany).getValue(
		GlobalVariable.NumofColm, 13))

for(int i=0;i<resultDB.size();i++){
	
	'verif reserved fund amt db dengan excel'
	if(WebUI.verifyEqual(Double.parseDouble(resultDB.get(i).toString()),Double.parseDouble(findTestData(excelPathReservedFund).getValue(
				GlobalVariable.NumofColm, rsvAmtRow+i).replace(",","")))==false){
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathReservedFund).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
	}
}