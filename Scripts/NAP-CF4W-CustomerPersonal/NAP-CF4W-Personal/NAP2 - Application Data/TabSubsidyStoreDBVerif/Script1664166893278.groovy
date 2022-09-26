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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2SubsidyStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

int arrayindexdb = 0

resultarray = result.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

def datafilefinancial = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData')

def SubsidyTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 12).split(';', -1)

def SubsidyfromValueArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def AllocationformArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def SubsidySourceArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def SubsidyValueTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def SubsidyValueAmountArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

def SubsidyValuePercentageArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

println(resultarray)

for(int arrayindexexcel = 0; arrayindexexcel <= SubsidyTypeArray.size() - 1; arrayindexexcel++){
	
'verify subsidy from value type'
WebUI.verifyMatch((SubsidyTypeArray[arrayindexexcel]).toUpperCase(), (resultarray[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL)

'verify subsidy from value'
WebUI.verifyMatch((SubsidyfromValueArray[arrayindexexcel]).toUpperCase(), (resultarray[arrayindexdb++]).toUpperCase(), 
    false, FailureHandling.OPTIONAL)

'verify allocation from'
WebUI.verifyMatch((AllocationformArray[arrayindexexcel]).toUpperCase(), (resultarray[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL)

'veirfy subsidy source'
WebUI.verifyMatch((SubsidySourceArray[arrayindexexcel]).toUpperCase(), (resultarray[arrayindexdb++]).toUpperCase(), false, 
    FailureHandling.OPTIONAL)

'verify subsidy amount'
WebUI.verifyEqual(Integer.parseInt((SubsidyValueAmountArray[arrayindexexcel].replace(',', ''))), (resultarray[arrayindexdb++]), 
    FailureHandling.OPTIONAL)

'verify subsidy percentage'
WebUI.verifyEqual(Integer.parseInt((SubsidyValuePercentageArray[arrayindexexcel].replace(',', ''))), (resultarray[arrayindexdb++]), 
    FailureHandling.OPTIONAL)

}
