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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1.2 CustomerDataCompletion-Personal - Family.xlsm")

CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(GlobalVariable.DataFilePath)

def CountofFamily = findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail').getColumnNumbers()

GlobalVariable.APPSTEP = 'FAMILY'

WebUI.delay(10)

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= CountofFamily; (GlobalVariable.NumofFamily)++) {
    if ((findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofFamily, 12) == findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) && findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail').getValue(
        GlobalVariable.NumofFamily, 13).equalsIgnoreCase(WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail')))) {
    	
		GlobalVariable.ColmNAP4 = GlobalVariable.NumofFamily
		
        if (GlobalVariable.Role == 'Data Entry') {		
            'call tc customerdetail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc AddressInformation'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc JobData'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc EmergencyContact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc FinancialData'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc CustomerAsset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            if(GlobalVariable.LOB == 'CF4W'){
            	'call tc OtherAttribute'
            	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttributeCF4W'),
            			[:], FailureHandling.CONTINUE_ON_FAILURE)
            }else if(GlobalVariable.LOB == 'FL4W'){
            	'call tc OtherAttribute'
            	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttributeFL4W'),
            			[:], FailureHandling.CONTINUE_ON_FAILURE)
            }
        } else {
            'call tc customerdetail'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabCustomerDetail'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc AddressInformation'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabAddressInformation'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc JobData'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabJobData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc EmergencyContact'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabEmergencyContact'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc FinancialData'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabFinancialData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc CustomerAsset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabCustomerAsset'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            if(GlobalVariable.LOB == 'CF4W'){
			'call tc OtherAttribute'
		    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttributeCF4W'), 
		        [:], FailureHandling.CONTINUE_ON_FAILURE)
			}else if(GlobalVariable.LOB == 'FL4W'){
			'call tc OtherAttribute'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/TabPersonal/TabOtherAttributeFL4W'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			}
        }
    }
}

