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

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'variable data file tab guarantor data personal'
dataGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'variable data file tab guarantor data company'
dataGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'variable data file Dupcheckrule'
ruledupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule')

'array untuk menampung hasil status Guarantor Personal'
def GuarantorPersonalStatus = ''

'array untuk menampung hasil status Guarantor Company'
def GuarantorCompanyStatus = ''

for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 3) == 'Input Data') {
            'check rule 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 20))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 3'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 17))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 4'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 5'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 6'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        20))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule 7'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        17))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        20), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 2))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 13)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 2))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 14)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        17), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 2))) > 0) {
                'add "LOCK" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 15)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 2))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 16)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 2))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 17)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 20), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        2))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 18)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
                    dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(
                        GlobalVariable.NumofGuarantorPersonal, 17), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        2))) > 0) {
                'add "REVIEW" kedalam array GuarantorPersonalStatus'
                GuarantorPersonalStatus = ruledupcheck.getValue(2, 19)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
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
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (GuarantorPersonalStatus.equalsIgnoreCase('REVIEW')) {
            'declare value result = review'
            GlobalVariable.DupcheckResult.add('REVIEW')
        } else if (GuarantorPersonalStatus.equalsIgnoreCase('')) {
            'declare no value'
            GlobalVariable.DupcheckResult.add('')
        } else {
            'declare value result = lock'
            GlobalVariable.DupcheckResult.add('LOCK')
        }
    }
}

for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
        GlobalVariable.NumofGuarantorCompany, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
            GlobalVariable.NumofGuarantorCompany, 3) == 'Input Data') {
            'check rule company 1'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnection, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 8), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        9))) > 0) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule company 2'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnection, dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 8))) > 0) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 21)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule company 1 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1LOS'(sqlconnection, 
                    dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 8), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 9), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        2))) > 0) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 20)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
                    'declare no value'
                    GlobalVariable.DupcheckResult.add('')
                } else {
                    'declare value result = lock'
                    GlobalVariable.DupcheckResult.add('LOCK')
                }
                
                continue
            }
            
            'check rule company 2 with APP_CUST'
            if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2LOS'(sqlconnection, 
                    dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 8), dataGuarantorCompany.getValue(
                        GlobalVariable.NumofGuarantorCompany, 2))) > 0) {
                'add "REVIEW" kedalam array GuarantorCompanyStatus'
                GuarantorCompanyStatus = ruledupcheck.getValue(2, 21)

                'verify apakah hasil pengecekan mengandung status REVIEW'
                if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
                    'declare value result = review'
                    GlobalVariable.DupcheckResult.add('REVIEW')
                } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
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
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(
        GlobalVariable.NumofGuarantorCompany, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (GuarantorCompanyStatus.equalsIgnoreCase('REVIEW')) {
            'declare value result = review'
            GlobalVariable.DupcheckResult.add('REVIEW')
        } else if (GuarantorCompanyStatus.equalsIgnoreCase('')) {
            'declare no value'
            GlobalVariable.DupcheckResult.add('')
        } else {
            'declare value result = lock'
            GlobalVariable.DupcheckResult.add('LOCK')
        }
    }
}

