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

'get data file path company'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'get data file path CDC customer company'
String CDCCustomerCompany = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerCompany)

'get data file path CDC MS Personal'
String CDCManagementShareholderPersonalPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderPersonal)

'get data file path CDC MS Company'
String CDCManagementShareholderCompanyPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

'get data file path CDC Guarantor Personal'
String CDCGuarantorPersonalPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

'get data file path CDC Guarantor company'
String CDCGuarantorCompanyPath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)

'connect DB Camunda SIT'
Sql sqlconnectionCamundaSIT = CustomKeywords.'dbconnection.connectDB.connectCAMUNDASIT'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

'declare data file dupcheck variable'
datafiledupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

'get appno from datafile dupcheck'
String DupcheckAppNo = datafiledupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnectionCamundaSIT, DupcheckAppNo)

'declare variable untuk Store nama customer'
def StoreCDCCustomerName = '', StoreCDCManagementShareholderPersonalName = '', StoreCDCManagementShareholderCompanyName = '', StoreCDCGuarantorPersonalName = '', StoreCDCGuarantorCompanyName = ''
	
'looping dupcheck row confins'
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

		'get MS Customer type'
        String ManagementShareholderType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnectionLOS, 
            DupcheckAppNo, name)

		'check if MS is Company / Personal'
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

		'get guarantor customer type'
        String GuarantorType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnectionLOS, DupcheckAppNo, 
            name)

		'check if guarantor is company / Personal'
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

'check if storeCDCCustomerName is null'
if (StoreCDCCustomerName != null) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
}

'check if StoreCDCManagementShareholderPersonalName is null'
if (StoreCDCManagementShareholderPersonalName != null) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        14, GlobalVariable.NumofColm - 1, (StoreCDCManagementShareholderPersonalName + ';') + StoreCDCManagementShareholderCompanyName)
}

'check if StoreCDCGuarantorPersonalName and StoreCDCGuarantorCompanyName is null'
if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        16, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
}

'declare MS Personal array'
StoreCDCManagementShareholderPersonalNameArray = StoreCDCManagementShareholderPersonalName.split(';')

'declare MS Company array'
StoreCDCManagementShareholderCompanyNameArray = StoreCDCManagementShareholderCompanyName.split(';')

'declare Guarantor Personal Array'
StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

'declare Guarantor Company array'
StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

'write customer name to Excel CDC Customer Company'
CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCCustomerCompany, '1.CustomerDetail', 12, GlobalVariable.NumofColm - 
    1, StoreCDCCustomerName)

'looping untuk write MS Personal Name ke Excel CDC MS Personal'
for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderPersonalNameArray.size(); ManagementShareholderName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderPersonalPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderPersonalNameArray[(ManagementShareholderName - 1)])
}

'looping untuk write MS Company Name ke Excel CDC MS Company'
for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderCompanyNameArray.size(); ManagementShareholderName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderCompanyPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderCompanyNameArray[(ManagementShareholderName - 1)])
}

'looping untuk write Guarantor Personal Name ke Excel CDC Guarantor Personal'
for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
}

'looping untuk write Guarantor Company Name ke Excel CDC Guarantor Company'
for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
}