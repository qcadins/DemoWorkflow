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

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

'declare datafileReservedFund'
datafileReservedFund = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData')

'Klik extend menu return handling'
WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/ExtendReturnHandling'))

'klik return handling'
WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/MenuReturnHandling'))

'input application no'
WebUI.setText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), 
    datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

'klik search'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))

'klik icon pensil untuk select'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))







