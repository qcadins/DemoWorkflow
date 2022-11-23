import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.util.ArrayList as ArrayList
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.ExcelData as ExcelData
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB FOU'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare variable untuk dupcheckresult'
ArrayList<String> DupcheckResult = new ArrayList<String>()

'array untuk menampung hasil status dupcheck'
def CustomerCompanyStatus = '', ManagementShareholderStatus = '', GuarantorPersonalStatus = ''. GuarantorCompanyStatus = ''

'check if input data / lookup'
if (GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'check rule company 1'
    if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerCompany.getValue(
                GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 
                20))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(
            sqlconnectionLOS, GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerCompany.getValue(
                GlobalVariable.NumofColm, 20), GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 
                13))) > 0)) {
        'add "REVIEW" kedalam array CustomerCompanyStatus'
        CustomerCompanyStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 20)

        'verify apakah hasil pengecekan mengandung status REVIEW'
        checkStatusDupcheck(CustomerCompanyStatus, DupcheckResult)

        break
    }
    
    'check rule company 2'
    if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerCompany.getValue(
                GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(
            sqlconnectionLOS, GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                19), GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13))) > 0)) {
        'add "REVIEW" kedalam array CustomerCompanyStatus'
        CustomerCompanyStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 21)

        'verify apakah hasil pengecekan mengandung status REVIEW'
        checkStatusDupcheck(CustomerCompanyStatus, DupcheckResult)

        break
    }
} else {
    'declare no value'
    DupcheckResult.add('')
}

'looping guarantor personal data from data file'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

'looping guarantor Company data from data file'
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompanyCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19))) > 
            0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(sqlconnectionLOS, 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19), 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12))) > 
            0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorCompanyStatus, DupcheckResult)

                continue
            }
            
            'check rule company 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 
            0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(sqlconnectionLOS, 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), 
                    GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12))) > 
            0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 21)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorCompanyStatus, DupcheckResult)

                continue
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

'looping MS data from data file'
for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofMS)++) {
    if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
            if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                ManagementShareholderStatus = ''

                'check rule 1'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            35))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            33), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 21), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 35), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 13)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 2'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            33), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 21), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 14)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 3'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            32))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            33), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 21), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 32), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 15)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 4'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19))) > 0) || 
                (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(sqlconnectionLOS, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 16)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 5'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            33), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 21), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 17)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 6'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 35))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 35), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 18)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 7'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 32))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 32), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 19)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
            } else if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase(
                'Company')) {
                'check rule company 1'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 52), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 53))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(
                        sqlconnectionLOS, GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            52), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 53), 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 20)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule company 2'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 52))) > 0) || 
                (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(sqlconnectionLOS, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 52), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 21)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

def NegativeResult = []

'check if input data / lookup'
if (GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'check rule company 1'
    if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerCompany.getValue(
                GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerCompany.getValue(GlobalVariable.NumofColm, 
                20))) > 0) {
        'add "NEGATIVE" kedalam array negative result'
        NegativeResult.add('NEGATIVE')

        break
    }
    
    'check Negative rule company 2'
    if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerCompany.getValue(
                GlobalVariable.NumofGuarantorCompany, 19))) > 0) {
        'add "NEGATIVE" kedalam array negative result'
        NegativeResult.add('NEGATIVE')

        break
    }
    
    'add "" kedalam array karena tidak kena negative check'
    NegativeResult.add('')
} else {
    'add "" kedalam array karena tidak kena negative check'
    NegativeResult.add('')
}

'looping Gaurantor Personal from data file'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check Negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        } else {
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        }
    }
}

'looping Gaurantor Company from data file'
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompanyCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonalCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorCompanyCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        } else {
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        }
    }
}

'looping MS from data file'
for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofMS)++) {
    if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 12) == GlobalVariable.findTestDataCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
            'check if company or personal'
            if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                'check negative rule personal 1'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            35))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 2'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 3'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            32))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 4'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 5'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 33), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 21), GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 
                            19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 6'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 35))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 7'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 19), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 32))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'add "" kedalam array karena tidak kena negative check'
                NegativeResult.add('')
            } else if (GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase(
                'Company')) {
                'check negative rule company 1'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 52), GlobalVariable.findTestDataManagementShareholder.getValue(
                            GlobalVariable.NumofMS, 53))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check Negative rule company 2'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, 
                        GlobalVariable.findTestDataManagementShareholder.getValue(GlobalVariable.NumofMS, 52))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'add "" kedalam array karena tidak kena negative check'
                NegativeResult.add('')
            }
        } else {
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        }
    }
}

println(DupcheckResult)

println(NegativeResult)

GlobalVariable.DupcheckVerifResult = DupcheckResult

GlobalVariable.NegativeverifResult = NegativeResult

if ((DupcheckResult.contains('REVIEW') || DupcheckResult.contains('LOCK')) || NegativeResult.contains('NEGATIVE')) {
    GlobalVariable.DupcheckVerif = 'Yes'
}

def checkStatusDupcheck(String Status, ArrayList<String> DupcheckResult) {
    'verify apakah hasil pengecekan mengandung status REVIEW'
    if (Status.equalsIgnoreCase('REVIEW')) {
        'declare value result = review'
        DupcheckResult.add('REVIEW')
    } else if (Status.equalsIgnoreCase('')) {
        'declare no value'
        DupcheckResult.add('')
    } else {
        'declare value result = lock'
        DupcheckResult.add('LOCK')
    }
}

