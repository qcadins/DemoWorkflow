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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

'call test case login R3'
WebUI.callTestCase(findTestCase('Login/LoginR3BranchManagerSuperuser - NEW'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'declare LOB'
GlobalVariable.LOB = 'CF4W'

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare findTestData(excelPathCustomerCompany)'
excelPathCustomerCompany = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

countCustomerCompany = findTestData(excelPathCustomerCompany).getColumnNumbers()

if (GlobalVariable.RoleCompany == 'Data Entry') {
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerCompany - 1); (GlobalVariable.NumofColm)++) {
        if (CustomKeywords.'customizeKeyword.function.checkNAPExist'(findTestData(excelPathCustomerCompany)) == true) {
            break
        }
        
        if (!(findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 1).equalsIgnoreCase('Unexecuted')) || (findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 12).length() == 0)) {
            continue
        }
        
        if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8) != '') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/EditNAP'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else {
            'call test case NAP1'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case Duplicate checking'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case ComResFund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case CDC'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    }
} else if (GlobalVariable.RoleCompany == 'Testing') {
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerCompany - 1); (GlobalVariable.NumofColm)++) {
        GlobalVariable.IsDataCancel = 0

        if (CustomKeywords.'customizeKeyword.function.checkNAPExist'(findTestData(excelPathCustomerCompany)) == true) {
            break
        }
        
        if (!findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 1).equalsIgnoreCase('Unexecuted') || (findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 12).length() == 0)) {
            continue
        }
        
        if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8) != '') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/EditNAP'), [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else {
            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }
            
            'call test case NAP1'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }

            'call test case Duplicate checking'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), 
            		[:], FailureHandling.CONTINUE_ON_FAILURE)
            
            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }
            
            'call test case NAP2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }
            
            'call test case Comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'declare datafileCommission'
            datafileCommission = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

            'declare datafileReservedFund'
            datafileReservedFund = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData')

            'Mengambil nilai row keberapa dimulai data return pada excel'
            def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                'Return Commission & Reserved Fund') + 1

            'Mengambil nilai row keberapa dimulai data return pada excel'
            def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                'Return Commission & Reserved Fund') + 1

            'Looping hingga return flag pada sheet commission dan reserved fund tidak bernilai "yes" lagi'
            for (int i = 1; i <= i; i++) {
                if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || datafileReservedFund.getValue(
                    GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')) {
                    if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes')) {
                        'write to excel flag return done'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                            returnRowCom, GlobalVariable.NumofColm - 1, 'Done')
                    } else if (datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase(
                        'Yes')) {
                        'write to excel flag return done'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
                            returnRowRsv, GlobalVariable.NumofColm - 1, 'Done')
                    }
                    
                    'call tc main comresfund'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), 
                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                } else {
                    break
                }
            }
            
            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }
            
            'call test case CDC NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            if (GlobalVariable.IsDataCancel == 1) {
                continue
            }
            
            'check if verif Appview = yes'
            if (GlobalVariable.CheckAppViewCompany == 'Yes') {
                'call test case verify app view'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ApplicationInquiry'), [:], FailureHandling.CONTINUE_ON_FAILURE)
            }
        }
    }
}