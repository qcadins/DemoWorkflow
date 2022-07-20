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
    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabReferantorData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabApplicationData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabAssetData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabLifeInsuranceData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabFinancialData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabTermConditionData'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    not_run: WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabUploadDocument'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabReferantorData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabApplicationData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabAssetData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabInsuranceData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabLifeInsuranceData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabFinancialData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabTermConditionData'), 
        [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabUploadDocument'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}