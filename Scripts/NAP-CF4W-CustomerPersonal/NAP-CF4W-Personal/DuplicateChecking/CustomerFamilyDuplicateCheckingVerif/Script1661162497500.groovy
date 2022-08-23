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
dataCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'variable data file tab family data'
dataFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

'variable data file Dupcheckrule'
ruledupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule')

'declare variable untuk dupcheckresult'
GlobalVariable.DupcheckResult = []

'array untuk menampung hasil status customer personal'
def CustomerPersonalStatus = ''

'array untuk menampung hasil status Family personal'
def FamilyPersonalStatus = ''

for (i = 1; i <= 1; i++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 4) == 'Input Data') {
        'check rule 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 20))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 13)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 14)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 3'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 17))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 15)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 4'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 16)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 5'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 17)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 6'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 20))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 18)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 7'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 17))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 19)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 1 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 20), dataCustomer.getValue(GlobalVariable.NumofColm, 3))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 13)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 2 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 3))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 14)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 3 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 17), dataCustomer.getValue(GlobalVariable.NumofColm, 3))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 15)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 4 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 3))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 16)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 5 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 3))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 17)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 6 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 20), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 3))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 18)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
        
        'check rule 7 with APP_CUST'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(sqlconnection, dataCustomer.getValue(
                    GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 17), dataCustomer.getValue(
                    GlobalVariable.NumofColm, 3))) > 0) {
            'add "REVIEW" kedalam array CustomerPersonalStatus'
            CustomerPersonalStatus = ruledupcheck.getValue(2, 19)

            'verify apakah hasil pengecekan mengandung status REVIEW'
            if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
                'declare value result = review'
                GlobalVariable.DupcheckResult.add('REVIEW')
            } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
                'declare no value'
                GlobalVariable.DupcheckResult.add('')
            } else {
                'declare value result = lock'
                GlobalVariable.DupcheckResult.add('LOCK')
            }
            
            break
        }
    }
    
    'verify apakah hasil pengecekan mengandung status REVIEW'
    if (CustomerPersonalStatus.equalsIgnoreCase('REVIEW')) {
        'declare value result = review'
        GlobalVariable.DupcheckResult.add('REVIEW')
    } else if (CustomerPersonalStatus.equalsIgnoreCase('')) {
        'declare no value'
        GlobalVariable.DupcheckResult.add('')
    } else {
        'declare value result = lock'
        GlobalVariable.DupcheckResult.add('LOCK')
    }
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 3) == 'Input Data') {
            'check rule 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 23))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 9))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 3'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 20))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 4'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 9))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 5'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11), dataFamily.getValue(
                        GlobalVariable.NumofFamily, 9))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 6'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 23))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 7'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataFamily.getValue(
                        GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 20))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 1 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 23), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        2))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 2 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        2))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 3 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 20), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        2))) > 0) {
                'add "LOCK" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 4 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        2))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 5 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        11), dataFamily.getValue(GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        2))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 6 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        23), dataFamily.getValue(GlobalVariable.NumofFamily, 2))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 7 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7LOS'(sqlconnection, 
                    dataFamily.getValue(GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 
                        20), dataFamily.getValue(GlobalVariable.NumofFamily, 2))) > 0) {
                'add "REVIEW" kedalam array FamilyPersonalStatus'
                FamilyPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (FamilyPersonalStatus.equalsIgnoreCase('REVIEW')) {
            'declare value result = review'
            GlobalVariable.DupcheckResult.add('REVIEW')
        } else if (FamilyPersonalStatus.equalsIgnoreCase('')) {
            'declare no value'
            GlobalVariable.DupcheckResult.add('')
        } else {
            'declare value result = lock'
            GlobalVariable.DupcheckResult.add('LOCK')
        }
    }
}

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/GuarantorDuplicateCheckingVerif'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)

println(GlobalVariable.DupcheckResult)

if (GlobalVariable.DupcheckResult.contains('REVIEW') || GlobalVariable.DupcheckResult.contains('LOCK')) {
    GlobalVariable.DupcheckVerif = 'Yes'
}

