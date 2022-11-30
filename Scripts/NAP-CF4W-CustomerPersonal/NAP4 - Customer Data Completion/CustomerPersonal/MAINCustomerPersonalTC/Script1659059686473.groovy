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

if (GlobalVariable.Role == 'Data Entry') {
	'call tc customerdetail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabCustomerDetail'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc AddressInformation'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabAddressInformation'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc Family'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabFamily'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc JobData'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabJobData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc EmergencyContact'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabEmergencyContact'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc FinancialData'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabFinancialData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc CustomerAsset'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabCustomerAsset'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

	'call tc OtherAttribute'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabOtherAttribute'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
	
	'call tc customerdetail'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabCustomerDetail'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc AddressInformation'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabAddressInformation'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc Family'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabFamily'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc JobData'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabJobData'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc EmergencyContact'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabEmergencyContact'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc FinancialData'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabFinancialData'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc CustomerAsset'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabCustomerAsset'), 
        [:], FailureHandling.STOP_ON_FAILURE)
	
	'call tc OtherAttribute'
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/TabOtherAttribute'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}