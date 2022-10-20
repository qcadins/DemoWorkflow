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
dataCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'variable data file tab family data'
dataFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

'variable data file tab guarantor data personal'
dataGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'variable data file tab guarantor data company'
dataGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'variable data file Dupcheckrule'
ruledupcheck = findTestData('DownloadRule/DuplicateCheckingRule')

'declare variable untuk dupcheckresult'
def DupcheckResult = []

'array untuk menampung hasil status customer personal'
def CustomerPersonalStatus = ''

'array untuk menampung hasil status Family personal'
def FamilyPersonalStatus = ''

'array untuk menampung hasil status Guarantor Personal'
def GuarantorPersonalStatus = ''

'array untuk menampung hasil status Guarantor Company'
def GuarantorCompanyStatus = ''

for (i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check rule 1'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                sqlconnectionLOS, dataCustomer.getValue(GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    21), dataCustomer.getValue(GlobalVariable.NumofColm, 30), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 13)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 2'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                sqlconnectionLOS, dataCustomer.getValue(GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    21), dataCustomer.getValue(GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 14)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 3'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 27))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                sqlconnectionLOS, dataCustomer.getValue(GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    21), dataCustomer.getValue(GlobalVariable.NumofColm, 27), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 15)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 4'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(
                sqlconnectionLOS, dataCustomer.getValue(GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 16)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 5'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                sqlconnectionLOS, dataCustomer.getValue(GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    21), dataCustomer.getValue(GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 
                    13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 17)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 6'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 30))) > 0) || (Integer.parseInt(
            CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(sqlconnectionLOS, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 30), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 18)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                DupcheckResult.add('')
            } else {
                'declare value result = lock'
                DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 7'
        if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 27))) > 0) || (Integer.parseInt(
            CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(sqlconnectionLOS, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 27), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 13))) > 0)) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 19)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
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
    if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
        'declare value result = review'
        DupcheckResult.add('REVIEW')
    } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
        'declare no value'
        DupcheckResult.add('')
    } else {
        'declare value result = lock'
        DupcheckResult.add('LOCK')
    }
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnectionFOU, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 33))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 33), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 30), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        21), dataFamily.getValue(GlobalVariable.NumofFamily, 19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        33))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        33), dataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
                    dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        30), dataFamily.getValue(GlobalVariable.NumofFamily, 12))) > 0)) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
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
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
            'declare value result = review'
            DupcheckResult.add('REVIEW')
        } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
            'declare no value'
            DupcheckResult.add('')
        } else {
            'declare value result = lock'
            DupcheckResult.add('LOCK')
        }
    }
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check rule 1'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnectionFOU, 
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(
                    sqlconnectionLOS, dataFamily.getValue(GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(
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
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
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
                        GlobalVariable.NumofGuarantorPersonal, 21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
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
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
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

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
        GlobalVariable.NumofGuarantorCompany, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
            GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check rule company 1'
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(
                    sqlconnectionLOS, dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 19), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        12))) > 0)) {
                'add "LOCK" kedalam array GuarantorCompanyStatus'
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
            if ((Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnectionFOU, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18))) > 0) || (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(
                    sqlconnectionLOS, dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(
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
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
        GlobalVariable.NumofGuarantorCompany, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
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

def NegativeResult = []

for (i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 14) == 'Input Data') {
        'check negative rule 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 3'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 27))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 4'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 5'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 28), dataCustomer.getValue(GlobalVariable.NumofColm, 21), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check negative rule 6'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 30))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'check Negative rule 7'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 19), dataCustomer.getValue(GlobalVariable.NumofColm, 27))) > 0) {
            'add "NEGATIVE" kedalam array negative result'
            NegativeResult.add('NEGATIVE')

            break
        }
        
        'add "" kedalam array karena tidak kena negative check'
        NegativeResult.add('')
    }
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 33))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 31), dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 33))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 19), dataFamily.getValue(GlobalVariable.NumofFamily, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        }
    }
}

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
            'check negative rule 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal1'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal2'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 3'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal3'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 4'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal4'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 5'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal5'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 28), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        21), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 6'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal6'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        30))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule 7'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRulePersonal7'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 19), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        27))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
        }
    }
}

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
        GlobalVariable.NumofGuarantorCompany, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
            GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
            'check negative rule company 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany1'(sqlconnectionFOU, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        19))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'check negative rule company 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkNegativeRuleCompany2'(sqlconnectionFOU, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 18))) > 0) {
                'add "NEGATIVE" kedalam array negative result'
                NegativeResult.add('NEGATIVE')

                continue
            }
            
            'add "" kedalam array karena tidak kena negative check'
            NegativeResult.add('')
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

