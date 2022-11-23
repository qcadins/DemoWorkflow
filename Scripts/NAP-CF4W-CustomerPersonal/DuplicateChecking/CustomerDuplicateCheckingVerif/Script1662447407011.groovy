import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
def CustomerPersonalStatus = ''

def FamilyPersonalStatus = ''

def GuarantorPersonalStatus = ''

def GuarantorCompanyStatus = ''

for (i = 1; i <= 1; i++) {
    if (GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check rule 1'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 30))) > 0) || (Integer.parseInt(
            CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 30), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 13)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 2'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(
            CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 14)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 3'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 27))) > 0) || (Integer.parseInt(
            CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 27), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 15)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 4'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(
                sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 16)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 5'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(
            CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 17)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 6'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 30), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 18)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 7'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                sqlconnectionLOS, GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 27), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 19)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
    } else {
        'declare no value'
        DupcheckResult.add('')
    }
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        33))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 11), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        33), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        19), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        30), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(
                CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 11), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        19), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 33))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 33), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 30), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 
            0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(sqlconnectionLOS, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        28), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
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

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), GlobalVariable.findTestDataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(
                    sqlconnectionLOS, GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        18), GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19), GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = GlobalVariable.findTestDataDupcheckRule.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorCompanyStatus, DupcheckResult)

                continue
            }
            
            'check rule company 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                    GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 0) || 
            (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(sqlconnectionLOS, 
                    GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), GlobalVariable.findTestDataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 12))) > 0)) {
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

def NegativeResult = []

for (i = 1; i <= 1; i++) {
    if (GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check negative rule 1'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 2'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 3'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 27))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 4'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 5'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    21), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 6'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 7'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, GlobalVariable.findTestDataCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), GlobalVariable.findTestDataCustomerPersonal.getValue(GlobalVariable.NumofColm, 
                    27))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'add "" kedalam array karena tidak kena negative check'
        NegativeResult.add('')
    } else {
        'declare no value'
        DupcheckResult.add('')
    }
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 33))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        33))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, GlobalVariable.findTestDataFamily.getValue(
                        GlobalVariable.NumofFamily, 19), GlobalVariable.findTestDataFamily.getValue(GlobalVariable.NumofFamily, 
                        30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == GlobalVariable.findTestDataCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (GlobalVariable.findTestDataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), GlobalVariable.findTestDataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, GlobalVariable.findTestDataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    }
}

GlobalVariable.DupcheckVerifResult = DupcheckResult

GlobalVariable.NegativeverifResult = NegativeResult

println(GlobalVariable.DupcheckVerifResult)

println(GlobalVariable.NegativeverifResult)

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

