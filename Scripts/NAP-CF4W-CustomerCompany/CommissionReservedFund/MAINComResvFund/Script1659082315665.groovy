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
    'dijalankan tanpa copy app'
    if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case commision data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData'), [:], FailureHandling.CONTINUE_ON_FAILURE) //dijalankan dengan copy
    } else if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Assign directori file excel ke global variabel'
        String userDir = System.getProperty('user.dir')

        'Assign directori file excel ke global variabel'
        String filePath = userDir + GlobalVariable.PathCompany

        'Assign directori file excel ke global variabel'
        GlobalVariable.DataFilePath = filePath

        if (WebUI.verifyElementNotVisible(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'), 
            FailureHandling.OPTIONAL)) {
            'click menu Consumer finance 4w'
            WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/a_Consumer Finance'))
        }
        
        'Klik Commission Reserved Fund'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'))

        WebUI.delay(5)

        'Input Appno'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                13))

        'Klik Search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Search'))

        'Klik Select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/i_Select'))

        WebUI.delay(5)

        'Klik Calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Calculate'))

        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'Klik cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Cancel'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
            }
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case reserved fund data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Assign directori file excel ke global variabel'
        String userDir = System.getProperty('user.dir')

        'Assign directori file excel ke global variabel'
        String filePath = userDir + GlobalVariable.PathCompany

        'Assign directori file excel ke global variabel'
        GlobalVariable.DataFilePath = filePath

        'Klik Button Calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Calculate'))

        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Cancel'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'Klik cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Cancel'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
            }
        }
    }
    //dijalankan dengan copy
} else if (GlobalVariable.RoleCompany == 'Testing') {
    'Dijalankan tanpa Copy APP'
    if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case commision data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Assign directori file excel ke global variabel'
        String userDir = System.getProperty('user.dir')

        'Assign directori file excel ke global variabel'
        String filePath = userDir + GlobalVariable.PathCompany

        'Assign directori file excel ke global variabel'
        GlobalVariable.DataFilePath = filePath

        if (WebUI.verifyElementNotVisible(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'), 
            FailureHandling.OPTIONAL)) {
            'click menu Consumer finance 4w'
            WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/a_Consumer Finance'))
        }
        
        'Klik Commission Reserved Fund'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'))

        WebUI.delay(5)

        'Input Appno'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                13))

        'Klik Search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Search'))

        'Klik Select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/i_Select'))

        WebUI.delay(5)

        'Klik Calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Calculate'))

        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'Klik cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Cancel'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
            }
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No')) {
        'call test case reserved fund data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'Assign directori file excel ke global variabel'
        String userDir = System.getProperty('user.dir')

        'Assign directori file excel ke global variabel'
        String filePath = userDir + GlobalVariable.PathCompany

        'Assign directori file excel ke global variabel'
        GlobalVariable.DataFilePath = filePath

        'Klik Button Calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Calculate'))

        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Cancel'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'Klik cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/button_Cancel'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
            }
        }
    }
}

