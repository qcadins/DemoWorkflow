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

if (GlobalVariable.RoleCompany == 'Data Entry') {
    'Assign directori file excel ke global variabel'
    String userDir = System.getProperty('user.dir')

    'Assign directori file excel ke global variabel'
    String filePath = userDir + GlobalVariable.PathCompany

    'Assign directori file excel ke global variabel'
    GlobalVariable.DataFilePath = filePath

    'dijalankan tanpa copy app tab referantor'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case referantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE) 
		
		//dijalankan copy app referantor
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'REFERANTOR', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy app application data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case application data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        
		//dijalankan copy app Application data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        WebUI.delay(5)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
        }
    }
    
	'dijalankan tanpa copy app asset data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase(
        'No')) {
        'call test case asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        //dijalankan copy app Asset data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save'))

        'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
        }
    }
    
	'dijalankan tanpa copy app insurance data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab insurance data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        
		//dijalankan copy app insurance data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Save'))

        WebUI.delay(5)

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
        }
    }

	'dijalankan tanpa copy app financial data'    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab financial data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        //dijalankan copy app financial data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

        WebUI.delay(5)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy term and condition'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab term condition data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        //dijalankan copy app Term and condition
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy app tab upload document'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        
		//dijalankan copy app upload document
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button submit'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
} else {
    'Assign directori file excel ke global variabel'
    String userDir = System.getProperty('user.dir')

    'Assign directori file excel ke global variabel'
    String filePath = userDir + GlobalVariable.PathCompany

    'Assign directori file excel ke global variabel'
    GlobalVariable.DataFilePath = filePath

    'dijalankan tanpa copy app tab referantor'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case referantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.STOP_ON_FAILURE) 
		
		//dijalankan copy app referantor
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'REFERANTOR', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy app application data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case application data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], FailureHandling.STOP_ON_FAILURE)
        
		//dijalankan copy app Application data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        WebUI.delay(5)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
        }
    }
    
	'dijalankan tanpa copy app asset data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase(
        'No')) {
        'call test case asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)
        //dijalankan copy app Asset data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save'))

        'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
        }
    }
    
	'dijalankan tanpa copy app insurance data'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab insurance data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.STOP_ON_FAILURE)
        
		//dijalankan copy app insurance data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Save'))

        WebUI.delay(5)

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
        }
    }

	'dijalankan tanpa copy app financial data'    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab financial data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)

        //dijalankan copy app financial data
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

        WebUI.delay(5)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy term and condition'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab term condition data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.STOP_ON_FAILURE)

        //dijalankan copy app Term and condition
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
    
		'dijalankan tanpa copy app tab upload document'
    if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case tab upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.STOP_ON_FAILURE)
        
		//dijalankan copy app upload document
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button submit'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
}

