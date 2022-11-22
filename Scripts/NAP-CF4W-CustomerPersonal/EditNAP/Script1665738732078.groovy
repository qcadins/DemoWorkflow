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
import org.openqa.selenium.WebElement as WebElement
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String appNo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 8)

String appStep = CustomKeywords.'dbconnection.checkStep.checkAppCurrStep'(sqlConnectionLOS, appNo)

String custStep = CustomKeywords.'dbconnection.checkStep.checkCustCheckStep'(sqlConnectionLOS, appNo)

'Write to excel Appno'
CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, 
    GlobalVariable.NumofColm - 1, appNo)

if(GlobalVariable.Role=="Data Entry"){
	if (appStep == 'CUST') {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if (appStep == 'FAM') {
		'click Menu customer main data'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabFamilyCopyApp'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
		
		getCustdata(sqlConnectionLOS, appNo, appStep)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if (appStep == 'GUAR') {
		'click Menu customer main data'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
		
		getCustdata(sqlConnectionLOS, appNo, appStep)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if (appStep == 'NAPD' || appStep == 'REF' || appStep == 'APP' || appStep == 'ASSET' || appStep == 'INS' || appStep == 'LFI' || appStep == 'FIN' || appStep == 'TC') {
//		getCustdata(sqlConnectionLOS, appNo, appStep)
//	
//		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
//			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		if ((appStep == 'NAPD') || (appStep == 'REF')) {
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'APP') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'ASSET') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'INS') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'LFI') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'FIN') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		} else if (appStep == 'TC') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		}
	} else if (appStep == 'UPL_DOC') {
		'click menu application data'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if ((appStep == 'COM') || (appStep == 'RSV')) {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	} else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	
}
else if(GlobalVariable.Role=="Testing"){
	if (appStep == 'CUST') {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:],
			FailureHandling.STOP_ON_FAILURE)
		
		if(GlobalVariable.DupcheckVerif == 'Yes'){
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
				[:], FailureHandling.STOP_ON_FAILURE)
		}
		
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	} else if (appStep == 'FAM') {
		'click Menu customer main data'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabFamilyCopyApp'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'),
			[:], FailureHandling.STOP_ON_FAILURE)
		
		getCustdata(sqlConnectionLOS, appNo, appStep)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:],
			FailureHandling.STOP_ON_FAILURE)
		
		if(GlobalVariable.DupcheckVerif == 'Yes'){
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
				[:], FailureHandling.STOP_ON_FAILURE)
		}
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	} else if (appStep == 'GUAR') {
		'click Menu customer main data'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'),
			[:], FailureHandling.STOP_ON_FAILURE)
		
		getCustdata(sqlConnectionLOS, appNo, appStep)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:],
			FailureHandling.STOP_ON_FAILURE)
		
		if(GlobalVariable.DupcheckVerif == 'Yes'){
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
				[:], FailureHandling.STOP_ON_FAILURE)
		}
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	} else if (appStep == 'NAPD' || appStep == 'REF' || appStep == 'APP' || appStep == 'ASSET' || appStep == 'INS' || appStep == 'LFI' || appStep == 'FIN' || appStep == 'TC') {
		getCustdata(sqlConnectionLOS, appNo, appStep)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:],
			FailureHandling.STOP_ON_FAILURE)
		
		if(GlobalVariable.DupcheckVerif == 'Yes'){
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'),
				[:], FailureHandling.STOP_ON_FAILURE)
		}
	
		if ((appStep == 'NAPD') || (appStep == 'REF')) {
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'APP') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'ASSET') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
			
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'INS') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'LFI') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'FIN') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		} else if (appStep == 'TC') {
			'click menu application data'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
			inputAppNo(appNo)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
				[:], FailureHandling.STOP_ON_FAILURE)
	
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
				[:], FailureHandling.STOP_ON_FAILURE)
		}
	} else if (appStep == 'UPL_DOC') {
		'click menu application data'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))
	
		inputAppNo(appNo)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	} else if ((appStep == 'COM') || (appStep == 'RSV')) {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'),
			[:], FailureHandling.STOP_ON_FAILURE)
	
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	} else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'),
			[:], FailureHandling.STOP_ON_FAILURE)
	}
	
}

def inputAppNo(String appNo) {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        appNo)

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
}

def getCustdata(Sql sqlConnectionLOS, String appNo, String appStep){
	if(appStep == 'FAM'){
		ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataPersonal'(sqlConnectionLOS, appNo)
		
		int index = 0
		
		String custname = custdata[index++]
		'Write to Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
			GlobalVariable.NumofColm - 1, custname)
		
		'Write to ID Type'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 20,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to BirthDate'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 26,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to IDNo '
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 27,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to MotherMaidenName'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 29,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to tab duplicate checking Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12,
				GlobalVariable.NumofColm - 1, custname)
	}else if(appStep == 'GUAR'){
		ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataPersonal'(sqlConnectionLOS, appNo)
				
		ArrayList<String> famData = CustomKeywords.'dbconnection.EditNAP.FamilyDataPersonal'(sqlConnectionLOS, appNo)
		
		
		int index = 0
				
		String custname = custdata[index++]
		'Write to Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
			GlobalVariable.NumofColm - 1, custname)
		
		'Write to ID Type'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 20,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to BirthDate'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 26,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to IDNo '
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 27,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to MotherMaidenName'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 29,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to tab duplicate checking Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12,
				GlobalVariable.NumofColm - 1, custname)
		
		index = 0
		
		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) +
		1); (GlobalVariable.NumofFamily)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(GlobalVariable.NumofFamily,
				12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily
	
				break
			}
		}
	
		for(int colm = GlobalVariable.CopyAppColm; colm < famData.size()/5 ; colm++){
			
			custname = famData[index++]
			'Write to tab Family cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 18,
					colm - 1, custname)
			
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16).length() > 0){
			'Write to dupcheck fam name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16) + ';' + custname )
			}else{
			'Write to dupcheck fam name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, custname )
			}
			
			'Write to tab fam data id type'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 20,
					colm - 1, famData[index++])
			
			'Write to tab fam data birth date'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 29,
					colm - 1, famData[index++])
			
			'Write to tab fam data id no'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 30,
					colm - 1, famData[index++])
			
			'Write to tab fam data mother maiden name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 32,
				colm - 1, famData[index++])
			
			index = 0
		}
	}else if ((appStep == 'NAPD') || (appStep == 'REF') || (appStep == 'APP') || (appStep == 'ASSET') || (appStep ==
		'INS') || (appStep == 'FIN') || (appStep == 'TC')) {

		ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataPersonal'(sqlConnectionLOS, appNo)
		
		ArrayList<String> famData = CustomKeywords.'dbconnection.EditNAP.FamilyDataPersonal'(sqlConnectionLOS, appNo)
		
		ArrayList<String> guarPersonalData = CustomKeywords.'dbconnection.EditNAP.GuarantorDataPersonal'(sqlConnectionLOS, appNo)
		
		ArrayList<String> guarCompanyData = CustomKeywords.'dbconnection.EditNAP.GuarantorDataCompany'(sqlConnectionLOS, appNo)
		
		int index = 0
		
		String custname = custdata[index++]
		'Write to Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
				GlobalVariable.NumofColm - 1, custname)
			
		'Write to ID Type'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 20,
				GlobalVariable.NumofColm - 1, custdata[index++])
			
		'Write to BirthDate'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 26,
				GlobalVariable.NumofColm - 1, custdata[index++])
			
		'Write to IDNo '
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 27,
				GlobalVariable.NumofColm - 1, custdata[index++])
			
		'Write to MotherMaidenName'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 29,
				GlobalVariable.NumofColm - 1, custdata[index++])

		'Write to tab duplicate checking Cust Name'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12,
				GlobalVariable.NumofColm - 1, custname)
		
		index = 0

		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) +
		1); (GlobalVariable.NumofFamily)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(GlobalVariable.NumofFamily,
				12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily
	
				break
			}
		}
	
		for(int colm = GlobalVariable.CopyAppColm; colm < famData.size()/5 ; colm++){
			
			custname = famData[index++]
			'Write to tab Family cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 18,
					colm - 1, custname )
			
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16).length() > 0){
			'Write to dupcheck fam name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16) + ';' + custname )
			}else{
			'Write to dupcheck fam name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, custname )
			}
			
			'Write to tab fam data id type'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 20,
					colm - 1, famData[index++])
			
			'Write to tab fam data birth date'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 29,
					colm - 1, famData[index++])
			
			'Write to tab fam data id no'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 30,
					colm - 1, famData[index++])
			
			'Write to tab fam data mother maiden name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 32,
				colm - 1, famData[index++])
			
			index = 0
		}


		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) +
		1); (GlobalVariable.NumofGuarantorPersonal)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(GlobalVariable.NumofGuarantorPersonal,
				12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal
		
				break
			}
		}

		for(int colm = GlobalVariable.CopyAppColm; colm < guarPersonalData.size()/5 ; colm++){
			
			custname = guarPersonalData[index++]
			'Write guarantor personal cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 17,
				colm - 1, custname )
			
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				19).length() > 0){
			'Write to dupcheck cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
					colm - 1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				19) + ';' + custname )
			}else{
			'Write to dupcheck cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
					colm - 1, custname )
			}
			
			'Write guarantor personal data id type'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 20,
				colm - 1, guarPersonalData[index++])
			
			'Write to guarantor personal data birth date'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 26,
				colm - 1, guarPersonalData[index++])
			
			'Write to guarantor personal data id no'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 28,
				colm - 1, guarPersonalData[index++])
			
			'Write to guarantor personal data mother maiden name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 29,
				colm - 1, guarPersonalData[index++])
			
			index = 0
		}
	

		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) +
		1); (GlobalVariable.NumofGuarantorCompany)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany').getValue(GlobalVariable.NumofGuarantorCompany,
				12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorCompany
		
				break
			}
		}

		for(int colm = GlobalVariable.CopyAppColm; colm < guarCompanyData.size()/2; colm++){
			
			custname = guarCompanyData[index++]
			'Write to tab guarantor company cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 17,
				colm - 1, custname )
			
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				19).length() > 0){
			'Write to dupcheck cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
					colm - 1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				19) + ';' + custname )
			}else{
			'Write to dupcheck cust name'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
					colm - 1, custname )
			}
			
			'Write to tab guarantor company data id no'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 18,
				colm - 1, guarCompanyData[index++])
			
			index = 0
		}
	}
}

