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

GlobalVariable.NAP4 = 'CUSTOMER'

if (GlobalVariable.RoleCompany == 'Data Entry') {
    'call test case customer detail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabCustomerDetail'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case address information'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabAddressInformation'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case Tab MS'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabManagementShareholder'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case contact information'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabContactInformation'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case financial data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabFinancialData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case customer asset'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabCustomerAsset'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case legal doc'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabLegalDocument'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case other attribute'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabOtherAttribute'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (GlobalVariable.RoleCompany == 'Testing') {
    'call test case customer detail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabCustomerDetail'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case address information'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabAddressInformation'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case Tab MS'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabManagementShareholder'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case contact information'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabContactInformation'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case financial data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabFinancialData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case customer asset'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabCustomerAsset'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case legal doc'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabLegalDocument'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    'call test case other attribute'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabCompany/TabOtherAttribute'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}

