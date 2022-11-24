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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

'call keyword openCloseFile'
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(GlobalVariable.DataFilePath)

def CountofManagementShareholder = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getColumnNumbers()

WebUI.delay(10)

for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= CountofManagementShareholder; (GlobalVariable.NumofMS)++) {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofMS, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofMS, 13).equalsIgnoreCase(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail - Company/CustomerNameDetail')))) {
        if (GlobalVariable.RoleCompany == 'Data Entry') {
			'call testcase customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerDetail'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case address information'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabAddressInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case contact info'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabContactInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabFinancialData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerAsset'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case legal doc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabLegalDocument'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test csae other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabOtherAttribute'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (GlobalVariable.RoleCompany == 'Testing') {
			'call test case customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerDetail'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case address information'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabAddressInformation'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case contact information'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabContactInformation'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabFinancialData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerAsset'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case leagl doc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabLegalDocument'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabOtherAttribute'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
}