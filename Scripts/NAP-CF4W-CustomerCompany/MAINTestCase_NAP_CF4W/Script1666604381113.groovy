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

WebUI.callTestCase(findTestCase('Login/LoginR3BranchManagerSuperuser - NEW'), [:], FailureHandling.STOP_ON_FAILURE)

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

if (GlobalVariable.RoleCompany == 'Data Entry') {
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (Integer.parseInt(GlobalVariable.CountNumofCustCompany) + 
    1); (GlobalVariable.NumofColm)++) {
        not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)

        not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
} else if (GlobalVariable.RoleCompany == 'Testing') {
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (Integer.parseInt(GlobalVariable.CountNumofCustCompany) + 
    1); (GlobalVariable.NumofColm)++) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)

        if (GlobalVariable.CheckAppViewCompany == 'Yes') {
            'call test case verify app view'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/VerifyAppView'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    }
}

