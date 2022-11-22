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

GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

String CDCCustomerPersonal = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerPersonal)

String CDCFamilyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)

String CDCGuarantorPersonalPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)

String CDCGuarantorCompanyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompany)

'connect DB Camunda SIT'
Sql sqlconnectionCamundaSIT = CustomKeywords.'dbConnection.connectDB.connectCAMUNDASIT'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheck'(sqlconnectionCamundaSIT, DupcheckAppNo)

'declare variable untuk Store nama customer'
def StoreCDCCustomerName = '', StoreCDCFamilyName = '', StoreCDCGuarantorPersonalName = '', StoreCDCGuarantorCompanyName = ''

for (index = 1; index <= GlobalVariable.CountDupcheckRow; index++) {
    'modify object subjectname'
    modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[2]', true)

    'modify object subjecttype'
    modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[3]', true)

    if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Customer')) {
        'get customer name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        'store customer name'
        StoreCDCCustomerName = name
    } else if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Family')) {
        'get Family name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        if (StoreCDCFamilyName == '') {
            'store customer name'
            StoreCDCFamilyName = name
        } else {
            StoreCDCFamilyName = ((StoreCDCFamilyName + ';') + name)
        }
    } else {
        'get guarantor name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        String GuarantorType = CustomKeywords.'dupCheck.dupCheckVerif.checkCustomerType'(sqlconnectionLOS, DupcheckAppNo, 
            name)

        if (GuarantorType.equalsIgnoreCase('COMPANY')) {
            if (StoreCDCGuarantorCompanyName == '') {
                'store guarantor name'
                StoreCDCGuarantorCompanyName = name
            } else {
                'store guarantor name'
                StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
            }
        } else {
            if (StoreCDCGuarantorPersonalName == '') {
                'store guarantor name'
                StoreCDCGuarantorPersonalName = name
            } else {
                'store guarantor name'
                StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
            }
        }
    }
}

if (StoreCDCCustomerName != null) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
}

if (StoreCDCFamilyName != null) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        14, GlobalVariable.NumofColm - 1, StoreCDCFamilyName)
}

if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        16, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
}

StoreCDCFamilyNameArray = StoreCDCFamilyName.split(';')

StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(CDCCustomerPersonal, '1.CustomerDetail', 11, GlobalVariable.NumofColm - 
    1, StoreCDCCustomerName)

for (FamilyName = 1; FamilyName <= StoreCDCFamilyNameArray.size(); FamilyName++) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(CDCFamilyPath, '1.CustomerDetail', 12, FamilyName, StoreCDCFamilyNameArray[
        (FamilyName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
}

