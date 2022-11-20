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

GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

String CDCCustomerPersonal = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerCompany)

String CDCManagementShareholderPersonalPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderPersonal)

String CDCManagementShareholderCompanyPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

String CDCGuarantorPersonalPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

String CDCGuarantorCompanyPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)

'connect DB Camunda SIT'
Sql sqlconnectionCamundaSIT = CustomKeywords.'dbconnection.connectDB.connectCAMUNDASIT'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

datafiledupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

String DupcheckAppNo = datafiledupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnectionCamundaSIT, DupcheckAppNo)

'declare variable untuk Store nama customer'
def StoreCDCCustomerName = '', StoreCDCManagementShareholderPersonalName = '', StoreCDCManagementShareholderCompanyName = '', StoreCDCGuarantorPersonalName = '', StoreCDCGuarantorCompanyName = ''
	
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
    } else if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Share Holder')) {
        'get ManagementShareholder name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        String ManagementShareholderType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnectionLOS, 
            DupcheckAppNo, name)

        if (ManagementShareholderType.equalsIgnoreCase('COMPANY')) {
            if (StoreCDCManagementShareholderCompanyName == '') {
                'store ManagementShareholder name'
                StoreCDCManagementShareholderCompanyName = name
            } else {
                'store ManagementShareholder name'
                StoreCDCManagementShareholderCompanyName = ((StoreCDCManagementShareholderCompanyName + ';') + name)
            }
        } else if (ManagementShareholderType.equalsIgnoreCase('PERSONAL')) {
            if (StoreCDCManagementShareholderPersonalName == '') {
                'store ManagementShareholder name'
                StoreCDCManagementShareholderPersonalName = name
            } else {
                'store ManagementShareholder name'
                StoreCDCManagementShareholderPersonalName = ((StoreCDCManagementShareholderPersonalName + ';') + name)
            }
        }
    } else {
        'get guarantor name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        String GuarantorType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnectionLOS, DupcheckAppNo, 
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
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
}

if (StoreCDCManagementShareholderPersonalName != null) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        14, GlobalVariable.NumofColm - 1, (StoreCDCManagementShareholderPersonalName + ';') + StoreCDCManagementShareholderCompanyName)
}

if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        16, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
}

StoreCDCManagementShareholderPersonalNameArray = StoreCDCManagementShareholderPersonalName.split(';')

StoreCDCManagementShareholderCompanyNameArray = StoreCDCManagementShareholderCompanyName.split(';')

StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCCustomerPersonal, '1.CustomerDetail', 12, GlobalVariable.NumofColm - 
    1, StoreCDCCustomerName)

for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderPersonalNameArray.size(); ManagementShareholderName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderPersonalPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderPersonalNameArray[(ManagementShareholderName - 1)])
}

for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderCompanyNameArray.size(); ManagementShareholderName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderCompanyPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderCompanyNameArray[(ManagementShareholderName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
}

