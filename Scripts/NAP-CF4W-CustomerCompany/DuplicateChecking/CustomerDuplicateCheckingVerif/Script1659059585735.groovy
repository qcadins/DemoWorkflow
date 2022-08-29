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

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'variable data file tab customer data'
dataCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'variable data file tab family data'
dataManagementShareholder = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'variable data file tab guarantor data personal'
dataGuarantorPersonal = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal')

'variable data file tab guarantor data company'
dataGuarantorCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany')

'variable data file Dupcheckrule'
ruledupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule')

'declare variable untuk dupcheckresult'
def DupcheckResult = []

'array untuk menampung hasil status customer personal'
def CustomerCompanyStatus = ''

'array untuk menampung hasil status Family personal'
def ManagementShareholderStatus = ''

'array untuk menampung hasil status Guarantor Personal'
def GuarantorPersonalStatus = ''

'array untuk menampung hasil status Guarantor Company'
def GuarantorCompanyStatus = ''

for (i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        14) == 'Input Data') {
        'check rule company 1'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnection, dataCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomerCompany.getValue(GlobalVariable.NumofColm, 20))) > 0) || 
        (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(sqlconnection, dataCustomerCompany.getValue(
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
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnection, dataCustomerCompany.getValue(
                    GlobalVariable.NumofGuarantorCompany, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                sqlconnection, dataCustomerCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19), dataCustomerCompany.getValue(
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
    }
    
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
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
            3) == 'Input Data') {
            if (dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal')) {
                ManagementShareholderStatus = ''

                'check rule 1'
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            35))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            32))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 11), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 21), dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 
                            19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 33), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 35))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 32))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 19), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
                            GlobalVariable.NumofFamily, 53))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
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
                if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnection, 
                        dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                        sqlconnection, dataManagementShareholder.getValue(GlobalVariable.NumofFamily, 52), dataManagementShareholder.getValue(
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
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) || (Integer.parseInt(
                CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(sqlconnection, dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(
                    sqlconnection, dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(
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
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(
                    sqlconnection, dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnection, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                    sqlconnection, dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 12))) > 0)) {
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
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany').getValue(GlobalVariable.NumofGuarantorCompany, 
        12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        13)) {
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
    }
}

println(DupcheckResult)

if (DupcheckResult.contains('REVIEW') || DupcheckResult.contains('LOCK')) {
    GlobalVariable.DupcheckVerif = 'Yes'
}

