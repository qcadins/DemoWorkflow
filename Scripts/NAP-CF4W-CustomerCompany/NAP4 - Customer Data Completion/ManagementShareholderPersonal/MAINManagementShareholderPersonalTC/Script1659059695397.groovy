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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderPersonal)

'call keyword openclosefile'
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(GlobalVariable.DataFilePath)

def CountofFamily = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail').getColumnNumbers()

GlobalVariable.NAP4 = 'SHAREHOLDER PERSONAL'

WebUI.delay(10)

for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= CountofFamily; (GlobalVariable.NumofMS)++) {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofMS, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofMS, 13).equalsIgnoreCase(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail')))) {
        if (GlobalVariable.RoleCompany == 'Data Entry') {
			'call test case customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case address info'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case job data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test caseemergency contact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call test case other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttribute'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (GlobalVariable.RoleCompany == 'Testing') {
			'call test case customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case address information'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case tab job data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case emergency contact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case tab customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call test case other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttribute'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
}

