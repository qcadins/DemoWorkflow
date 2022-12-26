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

'declare datafileDupcheck'
datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'declare datafilepath'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare cdccustomerpersonal'
String CDCCustomerPersonal = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerPersonal)

'declare cdcfamilypath'
String CDCFamilyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)

'declare cdcguarantorpersonalpath'
String CDCGuarantorPersonalPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)

'declare cdcguarantorcompanypath'
String CDCGuarantorCompanyPath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompany)

'connect DB Camunda SIT'
Sql sqlconnectionCamundaSIT = CustomKeywords.'dbConnection.connectDB.connectCAMUNDASIT'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get dupcheckappno dari excel dupcheck'
String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dupCheck.dupCheckVerif.checkDupcheck'(sqlconnectionCamundaSIT, DupcheckAppNo)

'declare variable untuk Store nama customer'
def StoreCDCCustomerName = '', StoreCDCFamilyName = '', StoreCDCGuarantorPersonalName = '', StoreCDCGuarantorCompanyName = ''

'looping countdupcheckrow'
for (index = 1; index <= GlobalVariable.CountDupcheckRow; index++) {
    'modify object subjectname'
    modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[2]', true)

    'modify object subjecttype'
    modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[3]', true)

	'Pengecekan modifysubjecttype bernilai customer/family/guarantor'
    if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Customer')) {
        'get customer name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        'store customer name'
        StoreCDCCustomerName = name
    } else if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Family')) {
        'get Family name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

		'Pengecekan apakah sudah ada nama sebelumnya atau masih kosong'
        if (StoreCDCFamilyName == '') {
            'store family name'
            StoreCDCFamilyName = name
        } else {
			'store family name'
            StoreCDCFamilyName = ((StoreCDCFamilyName + ';') + name)
        }
    } else {
        'get guarantor name'
        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

		'get guarantor type from db'
        String GuarantorType = CustomKeywords.'dupCheck.dupCheckVerif.checkCustomerType'(sqlconnectionLOS, DupcheckAppNo, 
            name)

		'Pengecekan guarantor type company/personal'
        if (GuarantorType.equalsIgnoreCase('COMPANY')) {
			'Pengecekan apakah sudah ada nama sebelumnya atau masih kosong'
            if (StoreCDCGuarantorCompanyName == '') {
                'store guarantor name'
                StoreCDCGuarantorCompanyName = name
            } else {
                'store guarantor name'
                StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
            }
        } else {
			'Pengecekan apakah sudah ada nama sebelumnya atau masih kosong'
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

'Jika storecdccustomername tidak null'
if (StoreCDCCustomerName != null) {
	'write to excel storecdccustomername'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
}

'Jika storecdcfamilyname tidak null'
if (StoreCDCFamilyName != null) {
	'write to excel storecdcfamilyname'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        14, GlobalVariable.NumofColm - 1, StoreCDCFamilyName)
}

'Jika storecdcguarantorpersonalname atau storecdcguarantorcompanyname tidak null'
if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
	'write to excel storecdcguarantorpersonal dan storecdcguarantorcompany'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        16, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
	
	'write to excel storecdcguarantorpersonal'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
		20, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName))
}

StoreCDCFamilyNameArray = StoreCDCFamilyName.split(';')

StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

'write to excel storecdccustomername'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCCustomerPersonal, '1.CustomerDetail', 12, GlobalVariable.NumofColm - 
    1, StoreCDCCustomerName)

for (FamilyName = 1; FamilyName <= StoreCDCFamilyNameArray.size(); FamilyName++) {
	'writetoexcel storecdcfamilynamearray'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCFamilyPath, '1.CustomerDetail', 12, FamilyName, StoreCDCFamilyNameArray[
        (FamilyName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
	'writetoexcel storecdcguarantorpersonalnamearray'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
}

for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
	'writetoexcel storecdcguarantorcompanynamearray'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12, GuarantorName, 
        StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
}

