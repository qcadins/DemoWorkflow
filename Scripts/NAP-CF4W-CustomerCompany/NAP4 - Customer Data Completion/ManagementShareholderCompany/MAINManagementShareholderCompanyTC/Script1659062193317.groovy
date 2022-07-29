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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileManagementShareholderCompany

GlobalVariable.DataFilePath = filePath

CustomKeywords.'opencloseExcel.opencloseExcel.Open_File'(GlobalVariable.DataFilePath)

def CountofManagementShareholder = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getColumnNumbers()

WebUI.delay(10)

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= CountofManagementShareholder; (GlobalVariable.NumofFamily)++) {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany').getValue(
        GlobalVariable.NumofFamily, 3).equalsIgnoreCase(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail - Company/CustomerNameDetail')))) {
        if (GlobalVariable.Role == 'Data Entry') {
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerDetail'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabAddressInformation'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabContactInformation'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerAsset'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabLegalDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabOtherAttribute'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
        } else {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerDetail'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabAddressInformation'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabContactInformation'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabFinancialData'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabCustomerAsset'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabLegalDocument'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/TabOtherAttribute'), 
    [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
}



