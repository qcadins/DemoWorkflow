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

String servername = findTestData('Login/Login').getValue(1, 7)

String instancename = findTestData('Login/Login').getValue(2, 7)

String username = findTestData('Login/Login').getValue(3, 7)

String password = findTestData('Login/Login').getValue(4, 7)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String databaseLOS = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 7)

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

'connect DB'
Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

'variable data file tab customer data'
dataCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'variable data file tab family data'
dataManagementShareholder = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'variable data file tab guarantor data personal'
dataGuarantorPersonal = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal')

'variable data file tab guarantor data company'
dataGuarantorCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany')

'variable data file Dupcheckrule'
ruledupcheck = findTestData('DownloadRule/DuplicateCheckingRule')

'declare variable untuk dupcheckresult'
def DupcheckResult = []

'array untuk menampung hasil status customer personal'
def CustomerCompanyStatus, ManagementShareholderStatus, GuarantorPersonalStatus, GuarantorCompanyStatus  = ''

for (int i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        14) == 'Input Data') {
        'check rule company 1'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnectionFOU, dataCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomerCompany.getValue(GlobalVariable.NumofColm, 20))) > 0) || 
        (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(sqlconnectionLOS, dataCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomerCompany.getValue(GlobalVariable.NumofColm, 20), dataCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerCompanyStatus'
            CustomerCompanyStatus = ruledupcheck.getValue(2, 20)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerCompanyStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerCompanyStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule company 2'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnectionFOU, dataCustomerCompany.getValue(
                    GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                sqlconnectionLOS, dataCustomerCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19), dataCustomerCompany.getValue(
                    GlobalVariable.NumofGuarantorCompany, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerCompanyStatus'
            CustomerCompanyStatus = ruledupcheck.getValue(2, 21)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerCompanyStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerCompanyStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
    }else {
        'declare no value'
        DupcheckResult.add('')
    }
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
            13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 2'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 3'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 4'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(
                CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(sqlconnectionLOS, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 5'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 6'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 7'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(
                    sqlconnectionLOS, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    } 
}

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompanyCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(GlobalVariable.NumofGuarantorCompany, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(GlobalVariable.NumofGuarantorCompany, 
            13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(
                    sqlconnectionLOS, dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule company 2'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnectionFOU, 
                    dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 0) || (Integer.parseInt(
                CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(sqlconnectionLOS, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 21)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    DupcheckResult.add('LOCK')
                }
                
                continue
            }
        } else {
            'declare no value'
            DupcheckResult.add('')
        }
    } 
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
            13) == 'Input Data') {
            if (dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal')) {
                ManagementShareholderStatus = ''

                'check rule 1'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            35))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            35), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 13)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 2'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 14)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 3'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            32))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            32), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "LOCK" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 15)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 4'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 16)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 5'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 17)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 6'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 35))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 35), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 18)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule 7'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 32))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 32), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 19)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
            } else if (dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Company')) {
                'check rule company 1'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 53))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 53), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 20)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
                    continue
                }
                
                'check rule company 2'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                        sqlconnectionLOS, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 12))) > 0)) {
                    'add "REVIEW" kedalam array ManagementShareholderStatus'
                    ManagementShareholderStatus = ruledupcheck.getValue(2, 21)

                    'verify apakah hasil pengecekan mengandung status REVIEW'
                    if (ManagementShareholderStatus.equalsIgnoreCase('REVIEW')) {
                        'declare value result = review'
                        DupcheckResult.add('REVIEW')
                    } else if (ManagementShareholderStatus.equalsIgnoreCase('')) {
                        'declare no value'
                        DupcheckResult.add('')
                    } else {
                        'declare value result = lock'
                        DupcheckResult.add('LOCK')
                    }
                    
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

for (i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        14) == 'Input Data') {
        'check rule company 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, dataCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomerCompany.getValue(GlobalVariable.NumofColm, 20))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule company 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, dataCustomerCompany.getValue(
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
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
            13) == 'Input Data') {
            'check Negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check Negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
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

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompanyCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(GlobalVariable.NumofGuarantorCompany, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(GlobalVariable.NumofGuarantorCompany, 
            13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, 
                    dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))) > 0) {
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

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
            13) == 'Input Data') {
            'check if company or personal'
            if (dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal')) {
                'check negative rule personal 1'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            35))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 2'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 3'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            32))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 4'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 5'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 6'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 35))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check negative rule 7'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 32))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'add "" kedalam array karena tidak kena negative check'
                NegativeResult.add('')
            } else if (dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Company')) {
                'check negative rule company 1'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 53))) > 0) {
                    'add "NEGATIVE" kedalam array negative result'
                    NegativeResult.add('NEGATIVE')

                    continue
                }
                
                'check Negative rule company 2'
                if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52))) > 0) {
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

