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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

String CDCCustomerPersonal = userDir + GlobalVariable.DataFileCustomerPersonal

String CDCFamilyPath = userDir + GlobalVariable.DataFileFamilyPersonal

String CDCGuarantorPersonalPath = userDir + GlobalVariable.DataFileGuarantorPersonal

String CDCGuarantorCompanyPath = userDir + GlobalVariable.DataFileGuarantorCompany

GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String databaseLOS = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database
String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnection, DupcheckAppNo)

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
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion',
			12, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
	}
	
	if (StoreCDCFamilyName != null) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion',
			14, GlobalVariable.NumofColm - 1, StoreCDCFamilyName)
	}
	
	if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion',
			16, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
	}
	
	StoreCDCFamilyNameArray = StoreCDCFamilyName.split(';')

	StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

	StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCCustomerPersonal, '1.CustomerDetail', 11, GlobalVariable.NumofColm -
		1, StoreCDCCustomerName)

	for (FamilyName = 1; FamilyName <= StoreCDCFamilyNameArray.size(); FamilyName++) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCFamilyPath, '1.CustomerDetail', 12, FamilyName,
			StoreCDCFamilyNameArray[(FamilyName - 1)])
	}
	
	for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail', 12,
			GuarantorName, StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
	}
	
	for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail', 12,
			GuarantorName, StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
	}