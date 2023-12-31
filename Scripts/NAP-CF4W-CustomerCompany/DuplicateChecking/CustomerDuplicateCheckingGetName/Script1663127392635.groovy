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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_CF4W_Company.xlsx")

'get data file path CDC customer company'
String CDCCustomerCompany = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.1 CustomerDataCompletion-Company - Customer.xlsm")

'get data file path CDC MS Personal'
String CDCManagementShareholderPersonalPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.2 CustomerDataCompletion-Company - ManagementShareholderPersonal.xlsm")

'get data file path CDC MS Company'
String CDCManagementShareholderCompanyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.3 CustomerDataCompletion-Company - ManagementShareholderCompany.xlsm")

'get data file path CDC Guarantor Personal'
String CDCGuarantorPersonalPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.4 CustomerDataCompletion-Company - GuarantorPersonal.xlsm")

'get data file path CDC Guarantor company'
String CDCGuarantorCompanyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.5 CustomerDataCompletion-Company - GuarantorCompany.xlsm")

'open close excel untuk refresh appno agar sama dengan excel datafile'
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(CDCManagementShareholderPersonalPath)

WebUI.delay(3)
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(CDCManagementShareholderCompanyPath)

WebUI.delay(3)
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(CDCGuarantorPersonalPath)

WebUI.delay(3)
CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFile'(CDCGuarantorCompanyPath)

'declare excelPathDupcheck'
excelPathDupcheck = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/DuplicateChecking'

'get app no from data file'
String DupcheckAppNo = findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 12)

'connect DB Camunda SIT'
Sql sqlconnectionCamundaSIT = CustomKeywords.'dbConnection.connectDB.connectCAMUNDASIT'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

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
        String ManagementShareholderType = CustomKeywords.'dupCheck.dupCheckVerif.checkCustomerType'(sqlconnectionLOS, 
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
        String GuarantorType = CustomKeywords.'dupCheck.dupCheckVerif.checkCustomerType'(sqlconnectionLOS, DupcheckAppNo, 
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
	'write to excel storeCDCCustomerName'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
}

'check if StoreCDCManagementShareholderName is null'
if (StoreCDCManagementShareholderPersonalName != null) {
	'write to excel storeCDCManagementShareholderName'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        14, GlobalVariable.NumofColm - 1, (StoreCDCManagementShareholderPersonalName + ';') + StoreCDCManagementShareholderCompanyName)
}

'check if StoreCDCGuarantorPersonalName and StoreCDCGuarantorCompanyName is null'
if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
	'write to excel StoreCDCGuarantorPersonalName and StoreCDCGuarantorCompanyName'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
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
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCCustomerCompany, '1.CustomerDetail', 12, GlobalVariable.NumofColm - 
    1, StoreCDCCustomerName)

'looping untuk mencari colm appno dimulai pada excel CDC'
loopingStartIndex(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerDetail'))

index = 0

'looping untuk write MS Personal Name ke Excel CDC MS Personal'
for (ManagementShareholderName = GlobalVariable.StartIndex; ManagementShareholderName < StoreCDCManagementShareholderPersonalNameArray.size() + GlobalVariable.StartIndex; ManagementShareholderName++) {
	'write to excel CDC Shareholder personal Name'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCManagementShareholderPersonalPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderPersonalNameArray[index++])
}

'looping untuk mencari colm appno dimulai pada excel CDC'
loopingStartIndex(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail'))

'reset index untuk array'
index = 0

'looping untuk write MS Company Name ke Excel CDC MS Company'
for (ManagementShareholderName = GlobalVariable.StartIndex; ManagementShareholderName < StoreCDCManagementShareholderCompanyNameArray.size() + GlobalVariable.StartIndex; ManagementShareholderName++) {
	'write to excel CDC Shareholder Company Name'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCManagementShareholderCompanyPath, '1.CustomerDetail', 
        12, ManagementShareholderName, StoreCDCManagementShareholderCompanyNameArray[index++])
}

'looping untuk mencari colm appno dimulai pada excel CDC'
loopingStartIndex(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail'))

'reset index untuk array'
index = 0

'looping untuk write Guarantor Personal Name ke Excel CDC Guarantor Personal'
for (GuarantorName = GlobalVariable.StartIndex; GuarantorName < StoreCDCGuarantorPersonalNameArray.size() + GlobalVariable.StartIndex; GuarantorName++) {
	'write to excel CDC Guarantor Personal Name'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorPersonalNameArray[index++])
}

'looping untuk mencari colm appno dimulai pada excel CDC'
loopingStartIndex(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail'))

'reset index untuk array'
index = 0

'looping untuk write Guarantor Company Name ke Excel CDC Guarantor Company'
for (GuarantorName = GlobalVariable.StartIndex; GuarantorName < StoreCDCGuarantorCompanyNameArray.size() + GlobalVariable.StartIndex; GuarantorName++) {
	'write to excel CDC Guarantor Company Name'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorCompanyNameArray[index++])
}

public loopingStartIndex(TestData datafile){	
	'untuk mendapatkan posisi copy app dari excel'
	for (int NumOf = 2; NumOf <= datafile.getColumnNumbers() - 1; (NumOf)++) {		
		if (datafile.getValue(NumOf, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13)) {
			GlobalVariable.StartIndex = NumOf - 1

			break
		}
	}
}