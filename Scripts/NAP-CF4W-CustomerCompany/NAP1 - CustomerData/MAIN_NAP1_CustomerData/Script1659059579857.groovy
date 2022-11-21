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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

'delcare POStat'
String POStat

'click Menu customer main data'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

//Verify sort & paging
pagingTesting()

'Ambil nilai office login dari confins'
String[] officeLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OfficeLocLogin')).replace(
    ',', ';').split(';')

if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    8).length() > 1) {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
}

if (GlobalVariable.Role == 'Data Entry') {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Add'))

        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 9))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'GlobalVariable.FlagFailed =1 karena lookup gagal'
                GlobalVariable.FlagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
            
            if ((POStat == 'DEACT') || (POStat == 'EXP')) {
                WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2, FailureHandling.OPTIONAL)
            }
        } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            if (GlobalVariable.Role == 'Testing') {
                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlconnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                if (WebUI.verifyEqual(totalDataPO, countPO) == false) {
                    'Write To Excel GlobalVariable.StatusFailed'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                    'Write To Excel GlobalVariable.ReasonFailedDataLookup'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                        1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDataLookup)

                    GlobalVariable.FlagFailed = 1
                }
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_ProductOfferingCode'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                GlobalVariable.FlagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Add'))

        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

        POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 9))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
        
        if ((POStat == 'DEACT') || (POStat == 'EXP')) {
            WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2, FailureHandling.OPTIONAL)
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)
    }
    
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) || (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call Testcase tab Customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabCustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
    1); (GlobalVariable.NumofMS)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofMS, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofMS

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('No')) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabManagementShareholder'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                1, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('No')) {
        'call test case guarantor tanpa copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                1, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/VerifyApplicant'), [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Add'))

        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 9))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'GlobalVariable.FlagFailed =1 karena lookup gagal'
                GlobalVariable.FlagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
            
            if ((POStat == 'DEACT') || (POStat == 'EXP')) {
                WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2, FailureHandling.OPTIONAL)
            }
        } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            if (GlobalVariable.Role == 'Testing') {
                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlconnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                if (WebUI.verifyEqual(totalDataPO, countPO) == false) {
                    'Write To Excel GlobalVariable.StatusFailed'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                    'Write To Excel GlobalVariable.ReasonFailedDataLookup'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                        1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDataLookup)

                    GlobalVariable.FlagFailed = 1
                }
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_ProductOfferingCode'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                GlobalVariable.FlagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Add'))

        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

        POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 9))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
        
        if ((POStat == 'DEACT') || (POStat == 'EXP')) {
            WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2, FailureHandling.OPTIONAL)
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)
    }
    
    if ((findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) || (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call Testcase tab Customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabCustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
    1); (GlobalVariable.NumofMS)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofMS, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofMS

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('No')) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabManagementShareholder'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                1, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('No')) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorData'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.CopyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                1, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/VerifyApplicant'), [:], FailureHandling.STOP_ON_FAILURE)
}

def pagingTesting() {
    driver = DriverFactory.getWebDriver()

    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
        ArrayList<Boolean> resultReset = new ArrayList<Boolean>()

        ArrayList<Boolean> checkVerifySort = new ArrayList<Boolean>()

        ArrayList<Boolean> checkVerifyFooter = new ArrayList<Boolean>()

        'Verif reset'
        resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()

        ArrayList<Boolean> listString = new ArrayList<Boolean>()

        'click button search'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Search'))

        'Inisialisasi variabel'
        ArrayList<Boolean> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'Klik header office'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_Office'))

        'Verif tidak ada alert yang muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

        'Klik header appno'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span/a', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort appno ascending'
        Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        listApp = new ArrayList<Boolean>()

        'Klik header appno'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span/a', true)

            listApp.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname 2x supaya descending'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_custName'))

        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_custName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

        listString = new ArrayList<Boolean>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_POName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

        listString = new ArrayList<Boolean>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_POName'))

        listString = new ArrayList<Boolean>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort poname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x header appno supaya urutan descending'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))

        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))

        'Ambil count data dari confins'
        String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/countData')).replace(
            ' ', '').replace(':', ';').split(';')

        Integer countDt = Integer.parseInt(textCountData[1])

        'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
        if (countDt > rowData.size()) {
            'Klik page 2'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/nextPage'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/nextPage'), 
                    'aria-current', 2))

            listString = new ArrayList<Boolean>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPaging'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button prev'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Prev'))

            'Verify page 1 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/pageOne'), 
                    'aria-current', 2))

            listApp = listString

            listString = new ArrayList<Boolean>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPaging'(listString)

            'Verif appno yang ada di page 1 tidak ada di page 2'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button next'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Next'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/nextPage'), 
                    'aria-current', 2))

            listApp = listString

            listString = new ArrayList<Boolean>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPaging'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
        }
        
        'Klik button page 1'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/pageOne'))

        checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP1CountDataInPage'(), true))

        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 1))) {
            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0, 
                GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)

            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 1, 
                GlobalVariable.NumofColm - 1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 2).replace('-', '') + ((GlobalVariable.ReasonFailedReset + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 1))) {
            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0, 
                GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)

            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 1, 
                GlobalVariable.NumofColm - 1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 2).replace('-', '') + ((GlobalVariable.ReasonFailedSort + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 1))) {
            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0, 
                GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)

            new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 1, 
                GlobalVariable.NumofColm - 1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 2).replace('-', '') + ((GlobalVariable.ReasonFailedFooter + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
    }
}

