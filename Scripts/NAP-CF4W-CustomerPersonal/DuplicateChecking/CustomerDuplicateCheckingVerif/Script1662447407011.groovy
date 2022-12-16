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

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabFamily'
datafileTabFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

'declare datafileTabGuarantorPersonal'
datafileTabGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'declare datafileTabGuarantorCompany'
datafileTabGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'declare datafileDupcheckRule'
datafileDupcheckRule = findTestData('DownloadRule/DuplicateCheckingRule')

'array untuk menampung hasil status dupcheck'
def CustomerPersonalStatus = '', FamilyPersonalStatus = '', GuarantorPersonalStatus = '', GuarantorCompanyStatus = ''

'looping data customer'
for (i = 1; i <= 1; i++) {
    if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check rule 1'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 30), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 13)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 2'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 14)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 3'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 27), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 15)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 4'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(
                sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 16)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 5'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 17)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 6'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 30))) > 0) || 
        (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(sqlconnectionLOS, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 30), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 18)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
        
        'check rule 7'
        if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 27))) > 0) || 
        (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(sqlconnectionLOS, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 27), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = datafileDupcheckRule.getValue(2, 19)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            checkStatusDupcheck(CustomerPersonalStatus, DupcheckResult)

            break
        }
    } else {
        'declare no value'
        DupcheckResult.add('')
    }
}

'looping data family'
for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= datafileTabFamily.getColumnNumbers() - 1; (GlobalVariable.NumofFamily)++) {
    if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 12) == datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
        13)) {
        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 33))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 11), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 33), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 30), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        21), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 11), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        33))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 33), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 12))) > 
            0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(FamilyPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 30), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 12))) > 
            0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = datafileDupcheckRule.getValue(2, 19)

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

'looping data guarantor personal'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= datafileTabGuarantorPersonal.getColumnNumbers() - 1; (GlobalVariable.NumofGuarantorPersonal)++) {
    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, datafileTabFamily.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(
                CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 19)

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

'looping data guarantorcompany'
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= datafileTabGuarantorCompany.getColumnNumbers() - 1; (GlobalVariable.NumofGuarantorCompany)++) {
    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(
                    sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = datafileDupcheckRule.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorCompanyStatus, DupcheckResult)

                continue
            }
            
            'check rule company 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 0) || (Integer.parseInt(
                CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(sqlconnectionLOS, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = datafileDupcheckRule.getValue(2, 21)

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

'looping customer'
for (i = 1; i <= 1; i++) {
    if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check negative rule 1'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 2'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 3'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 27))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 4'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 5'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 28), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21), datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 6'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 7'
        if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, datafileCustomerPersonal.getValue(
                    GlobalVariable.NumofColm, 19), datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 27))) > 0) {
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

'looping data family'
for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= datafileTabFamily.getColumnNumbers() - 1; (GlobalVariable.NumofFamily)++) {
    if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 12) == datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
        13)) {
        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 33))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 31), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21), datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 33))) > 
            0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, datafileTabFamily.getValue(
                        GlobalVariable.NumofFamily, 19), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 30))) > 
            0) {
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

'looping data guarantor personal'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= datafileTabGuarantorPersonal.getColumnNumbers() - 1; (GlobalVariable.NumofGuarantorPersonal)++) {
    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) {
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

'looping data guarantor company'
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= datafileTabGuarantorCompany.getColumnNumbers() - 1; (GlobalVariable.NumofGuarantorCompany)++) {
    if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, datafileTabGuarantorPersonal.getValue(
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

'Jika dupcheckresult bernilai review atau lock atau negativeresult bernilai negative'
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

