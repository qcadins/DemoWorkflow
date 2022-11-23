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

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare arraylist arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'Row yang menandakan dimulainya data section reserve fund amount pada excel'
def rsvAmtRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
    'Reserve Fund Amt') + 2

'declare arraylist resultDB'
ArrayList<String> resultDB = CustomKeywords.'dbConnection.CustomerDataVerif.NAP3ReservedFundDataStoreDB'(sqlconnectionLOS, 
    GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

for (int i = 0; i < resultDB.size(); i++) {
    'verif reserved fund amt db dengan excel'
    arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(resultDB.get(i).toString()), Double.parseDouble(GlobalVariable.findTestDataReservedFundDataNAPCompany.getValue(
                    GlobalVariable.NumofColm, rsvAmtRow + i).replace(',', ''))))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 0, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 1, 
        GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}

