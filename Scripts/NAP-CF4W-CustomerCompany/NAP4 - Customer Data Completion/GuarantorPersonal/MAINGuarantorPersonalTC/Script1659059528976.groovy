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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(GlobalVariable.DataFilePath)

def CountofGuarantor = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail').getColumnNumbers()

GlobalVariable.APPSTEP = 'GUARANTOR PERSONAL'

WebUI.delay(10)

for (GlobalVariable.NumofGuarantor = 2; GlobalVariable.NumofGuarantor <= CountofGuarantor; (GlobalVariable.NumofGuarantor)++) {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofGuarantor, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofGuarantor, 13).equalsIgnoreCase(WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail')))) {
        GlobalVariable.ColmNAP4 = GlobalVariable.NumofGuarantor

        if (GlobalVariable.RoleCompany == 'Data Entry') {
            'call test case tab customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab address'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab job data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab emergency contact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttribute'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (GlobalVariable.RoleCompany == 'Testing') {
            'call test case tab customer detail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab address information'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab job data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab emergency contact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab customer asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            'call test case tab other attribute'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttribute'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
}

