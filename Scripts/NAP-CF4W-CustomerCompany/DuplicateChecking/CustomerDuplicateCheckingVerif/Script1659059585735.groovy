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

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'declare datafileGuarantorPersonal'
datafileGuarantorPersonal = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal')

'declare datafileGuarantorCompany'
datafileGuarantorCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany')

'declare datafileDupcheckRule'
datafileDupcheckRule = findTestData('DownloadRule/DuplicateCheckingRule')

'array untuk menampung hasil status dupcheck'
def CustomerCompanyStatus = '', ManagementShareholderStatus = '', GuarantorPersonalStatus = '', GuarantorCompanyStatus = ''

'check if input data / lookup'
if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'check rule company 1'
    if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, datafileCustomerCompany.getValue(
                GlobalVariable.NumofColm, 19), datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 20))) > 0) || 
    (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(sqlconnectionLOS, datafileCustomerCompany.getValue(
                GlobalVariable.NumofColm, 19), datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 20), datafileCustomerCompany.getValue(
                GlobalVariable.NumofColm, 13))) > 0)) {
        'add "REVIEW" kedalam array CustomerCompanyStatus'
        CustomerCompanyStatus = datafileDupcheckRule.getValue(2, 20)

        'verify apakah hasil pengecekan mengandung status REVIEW'
        checkStatusDupcheck(CustomerCompanyStatus, DupcheckResult)
    }
    
    'check rule company 2'
    if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, datafileCustomerCompany.getValue(
                GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(
            sqlconnectionLOS, datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19), datafileCustomerCompany.getValue(
                GlobalVariable.NumofGuarantorCompany, 13))) > 0)) {
        'add "REVIEW" kedalam array CustomerCompanyStatus'
        CustomerCompanyStatus = datafileDupcheckRule.getValue(2, 21)

        'verify apakah hasil pengecekan mengandung status REVIEW'
        checkStatusDupcheck(CustomerCompanyStatus, DupcheckResult)
    }
} else {
    'declare no value'
    DupcheckResult.add('')
}

'looping guarantor personal data from data file'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (datafileGuarantorPersonal.getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(
                CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(sqlconnectionLOS, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = datafileDupcheckRule.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorPersonalStatus, DupcheckResult)

                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(
                    sqlconnectionLOS, datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
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

'looping guarantor Company data from data file'
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (datafileGuarantorCompany.getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), datafileGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(
                    sqlconnectionLOS, datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), datafileGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19), datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = datafileDupcheckRule.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                checkStatusDupcheck(GuarantorCompanyStatus, DupcheckResult)

                continue
            }
            
            'check rule company 2'
            if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                    datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 0) || (Integer.parseInt(
                CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(sqlconnectionLOS, datafileGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
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

'looping MS data from data file'
for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (datafileMS.getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
    if (datafileMS.getValue(GlobalVariable.NumofMS, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13)) {
        if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
            if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                ManagementShareholderStatus = ''

                'check rule 1'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 35))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal1AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 
                            21), datafileMS.getValue(GlobalVariable.NumofMS, 35), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 13)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 2'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal2AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 
                            21), datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 14)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 3'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 32))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal3AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 
                            21), datafileMS.getValue(GlobalVariable.NumofMS, 32), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 15)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 4'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal4AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 16)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 5'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal5AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 
                            21), datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 17)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 6'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 35))) > 
                0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal6AppCust'(sqlconnectionLOS, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 35), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 18)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule 7'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 32))) > 
                0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRulePersonal7AppCust'(sqlconnectionLOS, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 32), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 19)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
            } else if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                'check rule company 1'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 52), datafileMS.getValue(GlobalVariable.NumofMS, 53))) > 
                0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany1AppCust'(sqlconnectionLOS, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 52), datafileMS.getValue(GlobalVariable.NumofMS, 53), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 20)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    checkStatusDupcheck(ManagementShareholderStatus, DupcheckResult)

                    continue
                }
                
                'check rule company 2'
                if ((Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2Cust'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 52))) > 0) || (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheckRuleCompany2AppCust'(
                        sqlconnectionLOS, datafileMS.getValue(GlobalVariable.NumofMS, 52), datafileMS.getValue(GlobalVariable.NumofMS, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = datafileDupcheckRule.getValue(2, 21)

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
if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'check rule company 1'
    if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, datafileCustomerCompany.getValue(
                GlobalVariable.NumofColm, 19), datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 20))) > 0) {
        'add "NEGATIVE" kedalam array negative result'
        NegativeResult.add('NEGATIVE')
    }
    
    'check Negative rule company 2'
    if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, datafileCustomerCompany.getValue(
                GlobalVariable.NumofGuarantorCompany, 19))) > 0) {
        'add "NEGATIVE" kedalam array negative result'
        NegativeResult.add('NEGATIVE')
    }
    
    'add "" kedalam array karena tidak kena negative check'
    NegativeResult.add('')
} else {
    'add "" kedalam array karena tidak kena negative check'
    NegativeResult.add('')
}

'looping Gaurantor Personal from data file'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (datafileGuarantorPersonal.getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check Negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, datafileGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) {
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
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (datafileGuarantorCompany.getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, datafileGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, datafileGuarantorCompany.getValue(
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
for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (datafileMS.getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
    if (datafileMS.getValue(GlobalVariable.NumofMS, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13)) {
        if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
            'check if company or personal'
            if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                'check negative rule personal 1'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 35))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 2'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 3'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 32))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 4'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 5'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 33), datafileMS.getValue(GlobalVariable.NumofMS, 21), 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 6'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 35))) > 
                0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 7'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 19), datafileMS.getValue(GlobalVariable.NumofMS, 32))) > 
                0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'add "" kedalam array karena tidak kena negative check'
                NegativeResult.add('')
            } else if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                'check negative rule company 1'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 52), datafileMS.getValue(GlobalVariable.NumofMS, 53))) > 
                0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check Negative rule company 2'
                if (Integer.parseInt(CustomKeywords.'dupCheck.dupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, 
                        datafileMS.getValue(GlobalVariable.NumofMS, 52))) > 0) {
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

