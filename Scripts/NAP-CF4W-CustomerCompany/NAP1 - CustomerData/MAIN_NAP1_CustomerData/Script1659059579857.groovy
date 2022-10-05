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

int copyAppColm = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

'click menu customer main data'
not_run: WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

'click add button'
not_run: WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Add'))

'Ambil nilai office login dari confins'
not_run: String[] officeLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OfficeLocLogin')).replace(
    ',', ';').split(';')

if (GlobalVariable.RoleCompany == 'Data Entry') {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed +1 karena lookup gagal'
                (GlobalVariable.FlagFailed)++

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            if (GlobalVariable.Role == 'Testing') {
                'Koneksi database'
                String servername = findTestData('Login/Login').getValue(1, 8)

                String instancename = findTestData('Login/Login').getValue(2, 8)

                String username = findTestData('Login/Login').getValue(3, 8)

                String password = findTestData('Login/Login').getValue(4, 8)

                String databaseLOS = findTestData('Login/Login').getValue(5, 9)

                String driverclassname = findTestData('Login/Login').getValue(6, 8)

                String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

                Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlConnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                WebUI.verifyEqual(totalDataPO, countPO)
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Product Offering Code_prodOfferingCode'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed +1 karena lookup gagal'
                (GlobalVariable.FlagFailed)++

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Next'))

        'call testcase tab customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabCustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), findTestData(
                'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Flagfailed +1 karena lookup gagal'
            (GlobalVariable.FlagFailed)++

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.countNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

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
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            copyAppColm = GlobalVariable.NumofFamily

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('No')) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabManagementShareholder'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
            0, copyAppColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                0, copyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                1, copyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

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
            copyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 10).equalsIgnoreCase(
        'No')) {
        'call test case guarantor tanpa copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, copyAppColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                0, copyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                1, copyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

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
    not_run: if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed +1 karena lookup gagal'
                (GlobalVariable.FlagFailed)++

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            if (GlobalVariable.Role == 'Testing') {
                'Koneksi database'
                String servername = findTestData('Login/Login').getValue(1, 8)

                String instancename = findTestData('Login/Login').getValue(2, 8)

                String username = findTestData('Login/Login').getValue(3, 8)

                String password = findTestData('Login/Login').getValue(4, 8)

                String databaseLOS = findTestData('Login/Login').getValue(5, 9)

                String driverclassname = findTestData('Login/Login').getValue(6, 8)

                String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

                Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlConnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                WebUI.verifyEqual(totalDataPO, countPO)
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Product Offering Code_prodOfferingCode'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                    12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed +1 karena lookup gagal'
                (GlobalVariable.FlagFailed)++

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Next'))

        'call test case tab customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabCustomerData'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), findTestData(
                'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Flagfailed +1 karena lookup gagal'
            (GlobalVariable.FlagFailed)++

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.countNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

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
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
            copyAppColm = GlobalVariable.NumofFamily

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('No')) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabManagementShareholder'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(copyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                0, copyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                1, copyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

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
            copyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 10).equalsIgnoreCase(
        'No')) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorData'), [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 
        10).equalsIgnoreCase('Edit')) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(copyAppColm, 
        10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel Success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, copyAppColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                0, copyAppColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                1, copyAppColm - 1, GlobalVariable.StatusFailedCopyApp)

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

