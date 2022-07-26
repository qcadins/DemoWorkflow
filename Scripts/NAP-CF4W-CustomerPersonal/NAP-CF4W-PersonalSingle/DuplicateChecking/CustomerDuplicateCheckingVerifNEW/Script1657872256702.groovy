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

'variable data file tab guarantor data personal'
dataGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'variable data file tab guarantor data company'
dataGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'declare variable untuk dupcheckresult'
def DupcheckResult = []

'array untuk menampung hasil status customer personal'
def CustomerPersonalStatus = []

'array untuk menampung hasil status Family personal'
def FamilyPersonalStatus = []

'array untuk menampung hasil status Guarantor Personal'
def GuarantorPersonalStatus = []

'array untuk menampung hasil status Guarantor Company'
def GuarantorCompanyStatus = []

'check rule 1'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11).replace('-',''), dataCustomer.getValue(GlobalVariable.NumofColm, 
            20))) > 0) {
    'add "LOCK" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('LOCK')
}

'check rule 2'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11).replace('-',''), dataCustomer.getValue(GlobalVariable.NumofColm, 
            9))) > 0) {
    'add "LOCK" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('LOCK')
}

'check rule 3'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11).replace('-',''), dataCustomer.getValue(GlobalVariable.NumofColm, 
            17))) > 0) {
    'add "LOCK" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('LOCK')
}

'check rule 4'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 9))) > 0) {
    'add "REVIEW" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('REVIEW')
}

'check rule 5'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 18), dataCustomer.getValue(GlobalVariable.NumofColm, 11).replace('-',''), dataCustomer.getValue(GlobalVariable.NumofColm, 
            9))) > 0) {
    'add "REVIEW" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('REVIEW')
}

'check rule 6'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 20))) > 0) {
    'add "REVIEW" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('REVIEW')
}

'check rule 7'
if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataCustomer.getValue(
            GlobalVariable.NumofColm, 9), dataCustomer.getValue(GlobalVariable.NumofColm, 17))) > 0) {
    'add "REVIEW" kedalam array CustomerPersonalStatus'
    CustomerPersonalStatus.add('REVIEW')
}

'verify apakah hasil pengecekan mengandung status REVIEW'
if (CustomerPersonalStatus.contains('REVIEW')) {
    'declare value result = review'
    DupcheckResult.add('REVIEW')
} else if (CustomerPersonalStatus.isEmpty()) {
    'declare no value'
    DupcheckResult.add('')
} else {
    'declare value result = lock'
    DupcheckResult.add('LOCK')
}

for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 1); (GlobalVariable.NumofFamily)++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
        GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'check rule 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11).replace('-',''), dataFamily.getValue(
                    GlobalVariable.NumofFamily, 23))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            FamilyPersonalStatus.add('LOCK')
        }
        
        'check rule 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11).replace('-',''), dataFamily.getValue(
                    GlobalVariable.NumofFamily, 9))) > 0) {
            'add "LOCK" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('LOCK')
        }
        
        'check rule 3'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11).replace('-',''), dataFamily.getValue(
                    GlobalVariable.NumofFamily, 20))) > 0) {
            'add "LOCK" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('LOCK')
        }
        
        'check rule 4'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 9))) > 0) {
            'add "REVIEW" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('REVIEW')
        }
        
        'check rule 5'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 21), dataFamily.getValue(GlobalVariable.NumofFamily, 11).replace('-',''), dataFamily.getValue(
                    GlobalVariable.NumofFamily, 9))) > 0) {
            'add "REVIEW" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('REVIEW')
        }
        
        'check rule 6'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 23))) > 0) {
            'add "REVIEW" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('REVIEW')
        }
        
        'check rule 7'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofFamily, 9), dataFamily.getValue(GlobalVariable.NumofFamily, 20))) > 0) {
            'add "REVIEW" kedalam array FamilyPersonalStatus'
            FamilyPersonalStatus.add('REVIEW')
        }
        
				
					
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (FamilyPersonalStatus.contains('REVIEW')) {
            'declare value result = review'
            DupcheckResult.add('REVIEW')
        } else if (FamilyPersonalStatus.isEmpty()) {
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
        GlobalVariable.NumofGuarantorPersonal, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'check rule 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal1'(sqlconnection, dataFamily.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 11).replace('-',''), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 20))) > 0) {
            'add "LOCK" kedalam array CustomerPersonalStatus'
            GuarantorPersonalStatus.add('LOCK')
        }
        
        'check rule 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal2'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                    11).replace('-',''), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
            'add "LOCK" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('LOCK')
        }
        
        'check rule 3'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal3'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                    11).replace('-',''), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 17))) > 0) {
            'add "LOCK" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('LOCK')
        }
        
        'check rule 4'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal4'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
            'add "REVIEW" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('REVIEW')
        }
        
        'check rule 5'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal5'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 18), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                    11), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 9))) > 0) {
            'add "REVIEW" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('REVIEW')
        }
        
        'check rule 6'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal6'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                    20))) > 0) {
            'add "REVIEW" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('REVIEW')
        }
        
        'check rule 7'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRulePersonal7'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorPersonal, 9), dataGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                    17))) > 0) {
            'add "REVIEW" kedalam array GuarantorPersonalStatus'
            GuarantorPersonalStatus.add('REVIEW')
        }
        
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (GuarantorPersonalStatus.contains('REVIEW')) {
            'declare value result = review'
            DupcheckResult.add('REVIEW')
        } else if (GuarantorPersonalStatus.isEmpty()) {
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
        GlobalVariable.NumofGuarantorCompany, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3)) {
        'check rule company 1'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany1'(sqlconnection, dataGuarantorPersonal.getValue(
                    GlobalVariable.NumofGuarantorCompany, 8), dataGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                    9))) > 0) {
            'add "REVIEW" kedalam array GuarantorCompanyStatus'
            GuarantorCompanyStatus.add('LOCK')
        }
        
        'check rule company 2'
        if (Integer.parseInt(CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheckRuleCompany2'(sqlconnection, dataGuarantorCompany.getValue(
                    GlobalVariable.NumofGuarantorCompany, 8))) > 0) {
            'add "REVIEW" kedalam array GuarantorCompanyStatus'
            GuarantorCompanyStatus.add('REVIEW')
        }
        
					
					
        'verify apakah hasil pengecekan mengandung status REVIEW'
        if (GuarantorCompanyStatus.contains('REVIEW')) {
            'declare value result = review'
            DupcheckResult.add('REVIEW')
        } else if (GuarantorCompanyStatus.isEmpty()) {
            'declare no value'
            DupcheckResult.add('')
        } else {
            'declare value result = lock'
            DupcheckResult.add('LOCK')
        }
    }
}


if(DupcheckResult.contains('REVIEW') || DupcheckResult.contains('LOCK')){
	
	GlobalVariable.DupcheckVerif = 'Yes'
}

