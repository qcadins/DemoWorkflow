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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

datafiledupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

String CDCCustomerPersonal = userDir + GlobalVariable.DataFileCustomerCompany

String CDCManagementShareholderPersonalPath = userDir + GlobalVariable.DataFileManagementShareholderPersonal

String CDCManagementShareholderCompanyPath = userDir + GlobalVariable.DataFileManagementShareholderCompany

String CDCGuarantorPersonalPath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

String CDCGuarantorCompanyPath = userDir + GlobalVariable.DataFileGuarantorCompanyCompany

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String databaseLOS = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database
String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

String DupcheckAppNo = datafiledupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnection, DupcheckAppNo)

def StoreCDCCustomerName = ''

	'declare variable untuk Store nama customer'
	def StoreCDCManagementShareholderPersonalName = '', StoreCDCManagementShareholderCompanyName = '', StoreCDCGuarantorPersonalName = '', StoreCDCGuarantorCompanyName = ''

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
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12,
			GuarantorName, StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
	}
	
	for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12,
			GuarantorName, StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
	}