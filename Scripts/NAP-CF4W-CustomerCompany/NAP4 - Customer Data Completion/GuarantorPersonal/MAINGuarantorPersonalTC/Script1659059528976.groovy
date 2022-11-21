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
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(GlobalVariable.DataFilePath)

def CountofGuarantor = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getColumnNumbers()

WebUI.delay(10)

for (GlobalVariable.NumofGuarantor = 2; GlobalVariable.NumofGuarantor <= CountofGuarantor; (GlobalVariable.NumofGuarantor)++) {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantor, 13).equalsIgnoreCase(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail')))) {
        if (GlobalVariable.RoleCompany == 'Data Entry') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabCustomerDetail - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabAddressInformation - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobData - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabEmergencyContact - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabFinancialData - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabCustomerAsset - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabOtherAttribute - Personal - Guarantor'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (GlobalVariable.RoleCompany == 'Testing') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabCustomerDetail - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabAddressInformation - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabJobData - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabEmergencyContact - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabFinancialData - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabCustomerAsset - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/TabOtherAttribute - Personal - Guarantor'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
}

