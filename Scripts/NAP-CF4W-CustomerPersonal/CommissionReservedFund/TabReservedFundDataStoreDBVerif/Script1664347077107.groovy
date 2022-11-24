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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(filePath, '14.TabReservedFundData', 'Reserve Fund Amt')+2

ArrayList<String> resultDB = CustomKeywords.'dbConnection.CustomerDataVerif.NAP3ReservedFundDataStoreDB'(sqlconnection, GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13))

for(int i=0;i<resultDB.size();i++){
	
	'verif reserved fund amt db dengan excel'
	if(WebUI.verifyEqual(Double.parseDouble(resultDB.get(i).toString()),Double.parseDouble(GlobalVariable.findTestDataReservedFundNAPPersonal.getValue(
				GlobalVariable.NumofColm, rsvAmtRow+i).replace(",","")))==false){
			'Write to Excel FAILED'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write To Excel GlobalVariable.ReasonFailedStoredDB'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
			
			
	}
}

