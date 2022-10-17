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

Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

String appNo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 8)

String appStep = CustomKeywords.'editNAP.checkStep.checkAppCurrStep'(sqlConnectionLOS, appNo)

String custStep = CustomKeywords.'editNAP.checkStep.checkCustCheckStep'(sqlConnectionLOS, appNo)

'Write to excel Appno'
CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, 
    GlobalVariable.NumofColm - 1, appNo)

if (GlobalVariable.RoleCompany == 'Data Entry') {
    if (appStep == 'CUST') {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'FAM') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'GUAR') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((((((((appStep == 'NAPD') || (appStep == 'REF')) || (appStep == 'APP')) || (appStep == 'ASSET')) || (appStep == 
    'INS')) || (appStep == 'LFI')) || (appStep == 'FIN')) || (appStep == 'TC')) {

			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:],
				FailureHandling.CONTINUE_ON_FAILURE)
			
        if ((appStep == 'NAPD') || (appStep == 'REF')) {

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

        } else if (appStep == 'APP') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'ASSET') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'INS') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'FIN') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'TC') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } else if (appStep == 'UPL_DOC') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'COM' || appStep == 'RSV') {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
} else if (GlobalVariable.Role == 'Testing') {
    if (appStep == 'CUST') {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (appStep == 'FAM') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCopyApp'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
        
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (appStep == 'GUAR') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if ((((((((appStep == 'NAPD') || (appStep == 'REF')) || (appStep == 'APP')) || (appStep == 'ASSET')) || (appStep == 
    'INS')) || (appStep == 'LFI')) || (appStep == 'FIN')) || (appStep == 'TC')) {

			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'),
				[:], FailureHandling.STOP_ON_FAILURE)
			
			if (GlobalVariable.DupcheckVerif == 'Yes') {
				WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'),
					[:], FailureHandling.STOP_ON_FAILURE)
			}
			
        if ((appStep == 'NAPD') || (appStep == 'REF')) {

            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } else if (appStep == 'APP') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } else if (appStep == 'ASSET') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } else if (appStep == 'INS') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } else if (appStep == 'FIN') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } else if (appStep == 'TC') {
            inputAppNo()

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    } else if (appStep == 'UPL_DOC') {
        inputAppNo()

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (appStep == 'COM' || appStep == 'RSV') {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    }
}

def inputAppNo() {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
}

def getCustdata(sql sqlConnectionFOU, String appNo, String appStep){
		if(appStep == 'SHR'){
		ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataCompany'(sqlConnectionFOU, appNo)
		
		int index = 0
		
		'Write to Cust Name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
			GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to Tax ID'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 19,
			GlobalVariable.NumofColm - 1, custdata[index++])
	}else if(appStep == 'GUAR'){
		ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataCompany'(sqlConnectionFOU, appNo)
				
		ArrayList<String> shrPersonalData = CustomKeywords.'dbconnection.EditNAP.ShareholderDataPersonal'(sqlConnectionFOU, appNo)
		
		ArrayList<String> shrCompanyData = CustomKeywords.'dbconnection.EditNAP.ShareholderDataCompany'(sqlConnectionFOU, appNo)
		
		int index = 0
				
		'Write to tab customer main data Cust Name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
				GlobalVariable.NumofColm - 1, custdata[index++])
		
		'Write to tab duplicate checking Cust Name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12,
				GlobalVariable.NumofColm - 1, custdata[index++])
				
		'Write to Tax ID'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 19,
				GlobalVariable.NumofColm - 1, custdata[index++])
		
		index = 0
		
		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) +
		1); (GlobalVariable.NumofFamily)++) {
			if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily,
				12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily
	
				break
			}
		}
		
		for(int colm = GlobalVariable.CopyAppColm; colm < shrPersonalData.size()/5 ; colm++){
			'Write to tab Management Shareholder data customer type'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 13,
					colm - 1, 'Personal')
			
			'Write to tab Management Shareholder cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 18,
					colm - 1, shrPersonalData[index++])
			
			if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16).length() > 0){
			'Write to dupcheck cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16) + ';' + shrPersonalData[index++])
			}else{
			'Write to dupcheck cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, shrPersonalData[index++])
			}
			
			'Write to tab Management Shareholder data id type'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 20,
					colm - 1, shrPersonalData[index++])
			
			'Write to tab Management Shareholder data birth date'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 31,
					colm - 1, shrPersonalData[index++])
			
			'Write to tab Management Shareholder data id no'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 32,
					colm - 1, shrPersonalData[index++])
			
			'Write to tab Management Shareholder data mother maiden name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 34,
				colm - 1, shrPersonalData[index++])
			
			index = 0
		}
		
		for(int colm = GlobalVariable.CopyAppColm; colm < shrCompanyData.size()/2; colm++){
			'Write to tab Management Shareholder data customer type'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 13,
					colm - 1, 'Company')
			
			'Write to tab Management Shareholder cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 51,
					colm - 1, shrCompanyData[index++])
			
			if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16).length() > 0){
			'Write to dupcheck cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
				16) + ';' + shrCompanyData[index++])
			}else{
			'Write to dupcheck cust name'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
					colm - 1, shrCompanyData[index++])
			}
			
			'Write to tab Management Shareholder data id no'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 52,
					colm - 1, shrCompanyData[index++])
			
			index = 0
		}
	}else if ((appStep == 'NAPD') || (appStep == 'REF') || (appStep == 'APP') || (appStep == 'ASSET') || (appStep == 
    'INS') || (appStep == 'FIN') || (appStep == 'TC')) {

	ArrayList<String> custdata = CustomKeywords.'dbconnection.EditNAP.CustomerDataCompany'(sqlConnectionFOU, appNo)
	
	ArrayList<String> shrPersonalData = CustomKeywords.'dbconnection.EditNAP.ShareholderDataPersonal'(sqlConnectionFOU, appNo)
	
	ArrayList<String> shrCompanyData = CustomKeywords.'dbconnection.EditNAP.ShareholderDataCompany'(sqlConnectionFOU, appNo)
	
	ArrayList<String> guarPersonalData = CustomKeywords.'dbconnection.EditNAP.GuarantorDataPersonal'(sqlConnectionFOU, appNo)
	
	ArrayList<String> guarCompanyData = CustomKeywords.'dbconnection.EditNAP.GuarantorDataCompany'(sqlConnectionFOU, appNo)
	
	int index = 0
	
	'Write to tab customer main data Cust Name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 18,
	GlobalVariable.NumofColm - 1, custdata[index++])
	
	'Write to tab duplicate checking Cust Name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12,
	GlobalVariable.NumofColm - 1, custdata[index++])
	
	'Write to Tax ID'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 19,
	GlobalVariable.NumofColm - 1, custdata[index++])
	
	index = 0
	
	'untuk mendapatkan posisi copy app dari excel'
	for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) +
	1); (GlobalVariable.NumofFamily)++) {
	if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily,
	12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
	13)) {
	GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily
	
	break
	}
	}
	
	for(int colm = GlobalVariable.CopyAppColm; colm < shrPersonalData.size()/5 ; colm++){
	'Write to tab Management Shareholder data customer type'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 13,
		colm - 1, 'Personal')
	
	'Write to tab Management Shareholder cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 18,
		colm - 1, shrPersonalData[index++])
	
	if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
		16).length() > 0){
	'Write to dupcheck cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
			colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
		16) + ';' + shrPersonalData[index++])
	}else{
	'Write to dupcheck cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
			colm - 1, shrPersonalData[index++])
	}
	
	'Write to tab Management Shareholder data id type'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 20,
		colm - 1, shrPersonalData[index++])
	
	'Write to tab Management Shareholder data birth date'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 31,
		colm - 1, shrPersonalData[index++])
	
	'Write to tab Management Shareholder data id no'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 32,
		colm - 1, shrPersonalData[index++])
	
	'Write to tab Management Shareholder data mother maiden name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 34,
	colm - 1, shrPersonalData[index++])
	
	index = 0
	}
	
	for(int colm = GlobalVariable.CopyAppColm; colm < shrCompanyData.size()/2; colm++){
	'Write to tab Management Shareholder data customer type'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 13,
		colm - 1, 'Company')
	
	'Write to tab Management Shareholder cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 51,
		colm - 1, shrCompanyData[index++])
	
	if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
		16).length() > 0){
	'Write to dupcheck cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
			colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
		16) + ';' + shrCompanyData[index++])
	}else{
	'Write to dupcheck cust name'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15,
			colm - 1, shrCompanyData[index++])
	}
	
	
	'Write to tab Management Shareholder data id no'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 52,
		colm - 1, shrCompanyData[index++])
	
	index = 0
	}
	
	'untuk mendapatkan posisi copy app dari excel'
	for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) +
	1); (GlobalVariable.NumofGuarantorPersonal)++) {
		if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal,
			12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
			13)) {
			GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal

			break
		}
	}
	
	for(int colm = GlobalVariable.CopyAppColm; colm < guarPersonalData.size()/5 ; colm++){
		
		'Write guarantor personal cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 17,
			colm - 1, guarPersonalData[index++])
		
		if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
			19).length() > 0){
		'Write to dupcheck cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
				colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
			19) + ';' + guarPersonalData[index++])
		}else{
		'Write to dupcheck cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
				colm - 1, guarPersonalData[index++])
		}
		
		'Write guarantor personal data id type'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 20,
			colm - 1, guarPersonalData[index++])
		
		'Write to guarantor personal data birth date'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 26,
			colm - 1, guarPersonalData[index++])
		
		'Write to guarantor personal data id no'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 28,
			colm - 1, guarPersonalData[index++])
		
		'Write to guarantor personal data mother maiden name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 29,
			colm - 1, guarPersonalData[index++])
		
		index = 0
		}
		
		for(int colm = GlobalVariable.CopyAppColm; colm < guarCompanyData.size()/2; colm++){
		
		'Write to tab guarantor company cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 17,
			colm - 1, guarCompanyData[index++])
		
		if(findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
			19).length() > 0){
		'Write to dupcheck cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
				colm - 1, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm,
			19) + ';' + guarCompanyData[index++])
		}else{
		'Write to dupcheck cust name'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18,
				colm - 1, guarCompanyData[index++])
		}
		
		'Write to tab guarantor company data id no'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 18,
			colm - 1, guarCompanyData[index++])
		
		index = 0
		}
	}
}