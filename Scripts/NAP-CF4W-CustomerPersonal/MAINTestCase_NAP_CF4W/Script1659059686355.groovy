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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

'call tc login'
WebUI.callTestCase(findTestCase('Login/LoginR3BranchManagerSuperuser - NEW'), [:], FailureHandling.STOP_ON_FAILURE)

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

countCustomerPersonal = datafileCustomerPersonal.getColumnNumbers()

'Jika role == data entry'
if (GlobalVariable.Role == 'Data Entry') {
    'looping countnumofcust'
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerPersonal - 1); (GlobalVariable.NumofColm)++) {
        'Jika status pada excel bukan unexecuted'
        if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 1) != 'Unexecuted' || datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13).length() == 0) {
            'skip ke appno selanjutnya'
            continue
        }
        
        'jika edit appno tidak kosong'
        if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 8) != '') {
            'call tc edit nap'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/EditNAP'), [:], FailureHandling.CONTINUE_ON_FAILURE) //Jika edit appno kosong
        } else {
            'call tc main nap1'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call tc main nap2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } //jika role == testing
    //jika edit appno kosong
} else {
    'looping countnumofcust'
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerPersonal - 1); (GlobalVariable.NumofColm)++) {
        'Jika status pada excel bukan unexecuted'
        if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 1) != 'Unexecuted' || datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13).length() == 0) {
            'skip ke appno selanjutnya'
            continue
        }
        
        'Jika edit appno pada excel tidak kosong'
        if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 8) != '') {
            'call tc edit nap'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/EditNAP'), [:], FailureHandling.STOP_ON_FAILURE)
        } else {
            try {
                'call tc main nap1'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), 
                    [:], FailureHandling.STOP_ON_FAILURE)

                'call tc custdupcheckverif'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), 
                    [:], FailureHandling.STOP_ON_FAILURE)

                'jika dupcheckverif bernilai yes'
                if (GlobalVariable.DupcheckVerif == 'Yes') {
                    'call tc custdupcheck'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), 
                        [:], FailureHandling.STOP_ON_FAILURE)
                }
                
                'call tc main nap2'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                    [:], FailureHandling.STOP_ON_FAILURE)

                'call tc main comresfund'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], 
                    FailureHandling.STOP_ON_FAILURE)

                'call tc nap4 cdc'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                    [:], FailureHandling.STOP_ON_FAILURE)

                'Jika flag checkappviewpersonal bernilai yes'
                if (GlobalVariable.CheckAppViewPersonal == 'Yes') {
                    'call test case verify app view'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ApplicationInquiry'), [:], FailureHandling.STOP_ON_FAILURE)
                }
            }
            catch (Exception e) {
                KeywordUtil.markFailed('gagal')

                continue
            } 
        }
    }
}

