import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'Write to dupcheck cust name'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0,
	GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare findTestData(excelPathCustomerCompany)'
excelPathCustomerCompany = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathMS'
excelPathMS = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder'

'declare excelPathGuarantorPersonal'
excelPathGuarantorPersonal = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal'

'declare excelPathGuarantorCompany'
excelPathGuarantorCompany = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany'

'get appno dari data file'
String appNo = findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8)

'check appCurrStep'
String appStep = CustomKeywords.'dbConnection.checkStep.checkAppCurrStep'(sqlconnectionLOS, appNo)

'check custStep'
String custStep = CustomKeywords.'dbConnection.checkStep.checkCustCheckStep'(sqlconnectionLOS, appNo)

'Write to excel Appno'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, GlobalVariable.NumofColm - 
    1, appNo)

'declare list untuk menampung step-step pada nap1'
List<String> NAP1Step = Arrays.asList("CUST", "SHR", "GUAR")

'declare list untuk menampung step-step pada nap2'
List<String> NAP2Step = Arrays.asList("REF", "APP", "ASSET", "INS","FIN","TC","UPL_DOC")

'menampung backward step dari excel'
String backStep = findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 7)

'Jika curr app step dan backward step keduanya ada di list step nap1'
if(NAP1Step.contains(backStep)&&NAP1Step.contains(appStep)){
	int posApp = NAP1Step.indexOf(appStep)
	int posBack = NAP1Step.indexOf(backStep)
	'Jika posisi curr app step lebih tinggi dari posisi backward step'
	if(posApp>posBack){
		appStep = backStep
	}
}
//Jika curr app step dan backward step keduanya ada di list step nap2
else if(NAP2Step.contains(backStep)&&NAP2Step.contains(appStep)){
	int posApp = NAP2Step.indexOf(appStep)
	int posBack = NAP2Step.indexOf(backStep)
	'Jika posisi curr app step lebih tinggi dari posisi backward step'
	if(posApp>posBack){
		appStep = backStep
	}
}

if (GlobalVariable.RoleCompany == 'Data Entry') {
    if (appStep == 'CUST') {
        'call test case tab customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case Customer duplicate checking'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'SHR') {
//        'call function get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

        'call function input appno'
        inputAppNo()

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofMS, findTestData(excelPathMS))
		
        'call test case tab MS copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/MAINMSCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, findTestData(excelPathGuarantorPersonal))
		
        'call test case Tab Guarantor Personal Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, findTestData(excelPathGuarantorCompany))
		
		'call test case Tab Guarantor Company Copy App'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)

        'call test case dupcheck'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'GUAR') {
//        'call function get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

        'call function input appno'
        inputAppNo()

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, findTestData(excelPathGuarantorPersonal))
		
        'call test case Tab Guarantor Personal Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, findTestData(excelPathGuarantorCompany))
		
		'call test case Tab Guarantor Company Copy App'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)

        'call test case dupcheck'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (CustomKeywords.'dbConnection.checkStep.checkNAP2Step'(appStep) == true) {
//        'call funtion get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'call test case dupchcek'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        if ((appStep == 'NAPD') || (appStep == 'REF')) {
            'call test case NAP2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'APP') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case application data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabApplicationData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab asset data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'ASSET') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case tab asset data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'INS') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'TC') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input app no'
            inputAppNo()

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } else if (appStep == 'UPL_DOC') {
        'click menu application data'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

        'call function input appno'
        inputAppNo()

        'call test case upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((appStep == 'COM') || (appStep == 'RSV')) {
        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
} else if (GlobalVariable.RoleCompany == 'Testing') {
    if (appStep == 'CUST') {
        'call test case tab customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/MAIN_NAP1_CustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case dupcheck verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            'call test case dupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        'call test case Customer duplicate checking'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		loopingReturnComresfund()
		
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'SHR') {
//        'call function get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

        'call function input appno'
        inputAppNo()

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofMS, findTestData(excelPathMS))
		
        'call test case tab MS copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/MAINMSCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, findTestData(excelPathGuarantorPersonal))
	
        'call test case Tab Guarantor Personal Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
	
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, findTestData(excelPathGuarantorCompany))
		
		'call test case Tab Guarantor Company Copy App'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)

        'call test case dupcheck verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            'call test case dupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		loopingReturnComresfund()
		
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (appStep == 'GUAR') {
//        'call function get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

        'call function input appno'
        inputAppNo()

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, findTestData(excelPathGuarantorPersonal))
		
        'call test case Tab Guarantor Personal Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, findTestData(excelPathGuarantorCompany))
		
		'call test case Tab Guarantor Company Copy App'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)
		
        'call test case dupcheck verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            'call test case dupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        'call test case NAP2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		loopingReturnComresfund()
		
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (CustomKeywords.'dbConnection.checkStep.checkNAP2Step'(appStep) == true) {
//        'call funtion get cust data'
//        getCustdata(sqlconnectionLOS, appNo, appStep)

        'call test case dupcheck verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)

        if (GlobalVariable.DupcheckVerif == 'Yes') {
            'call test case dupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if ((appStep == 'NAPD') || (appStep == 'REF')) {
            'call test case NAP2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			loopingReturnComresfund()
			
            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'APP') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case application data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabApplicationData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab asset data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			loopingReturnComresfund()
			
            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'ASSET') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case tab asset data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			loopingReturnComresfund()
			
            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'INS') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input appno'
            inputAppNo()

            'call test case tab insurance data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case tab financial data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			loopingReturnComresfund()
			
            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } else if (appStep == 'TC') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

            'call function input app no'
            inputAppNo()

            'call test case term and condition data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call test case comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			loopingReturnComresfund()
			
            'call test case NAP4'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } else if (appStep == 'UPL_DOC') {
        'click menu application data'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))

        'call function input appno'
        inputAppNo()

        'call test case upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		loopingReturnComresfund()
		
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((appStep == 'COM') || (appStep == 'RSV')) {
        'call test case comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		loopingReturnComresfund()
		
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
        'call test case NAP4'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

def inputAppNo() {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
}

def getCustdata(Sql sqlconnectionLOS, String appNo, String appStep) {
    if (appStep == 'SHR') {
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataCompany'(sqlconnectionLOS, 
            appNo)

        int index = 0

        String custname = custdata[index++]

        'Write to Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)

        'Write to Tax ID'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            19, GlobalVariable.NumofColm - 1, custdata[index++])
    } else if (appStep == 'GUAR') {
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataCompany'(sqlconnectionLOS, 
            appNo)

        ArrayList<String> shrPersonalData = CustomKeywords.'dbConnection.getInfoForEditNAP.shareholderDataPersonal'(sqlconnectionLOS, 
            appNo)

        ArrayList<String> shrCompanyData = CustomKeywords.'dbConnection.getInfoForEditNAP.shareholderDataCompany'(sqlconnectionLOS, 
            appNo)

        String MsName = ''

        int index = 0

        int lastcolm = 0

        String custname = custdata[index++]

        'Write to tab customer main data Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)

        'Write to Tax ID'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            19, GlobalVariable.NumofColm - 1, custdata[index++])

        'untuk mendapatkan posisi copy app dari excel'
        for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (findTestData(excelPathMS).getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
            if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
                13)) {
                GlobalVariable.StartIndex = GlobalVariable.NumofMS

                break
            }
        }
        
        index = 0

        for (int colm = GlobalVariable.StartIndex; colm < ((shrPersonalData.size() / 5) + GlobalVariable.StartIndex); colm++) {
            'Write to tab Management Shareholder data customer type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                13, colm - 1, 'Personal')

            custname = (shrPersonalData[index++])

            'Write to tab Management Shareholder cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                18, colm - 1, custname)

            if (MsName == '') {
                'store MS Name'
                MsName = custname
            } else {
                'store MS Name'
                MsName = ((MsName + ';') + custname)
            }
            
            'Write to tab Management Shareholder data id type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                20, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data birth date'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                31, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                32, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data mother maiden name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                34, colm - 1, shrPersonalData[index++])

            lastcolm = colm
        }
        
        index = 0

        lastcolm++

        for (int colm = lastcolm; colm < ((shrCompanyData.size() / 2) + lastcolm); colm++) {
            'Write to tab Management Shareholder data customer type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                13, colm - 1, 'Company')

            custname = (shrCompanyData[index++])

            'Write to tab Management Shareholder cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                51, colm - 1, custname)

            if (MsName == '') {
                'store MS Name'
                MsName = custname
            } else {
                'store MS Name'
                MsName = ((MsName + ';') + custname)
            }
            
            'Write to tab Management Shareholder data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                52, colm - 1, shrCompanyData[index++])
        }
        
        'Write to dupcheck cust name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15, 
            GlobalVariable.NumofColm - 1, MsName)
    } else if (((((((appStep == 'NAPD') || (appStep == 'REF')) || (appStep == 'APP')) || (appStep == 'ASSET')) || (appStep == 
    'INS')) || (appStep == 'FIN')) || (appStep == 'TC')) {
        'get customer company data'
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataCompany'(sqlconnectionLOS, 
            appNo)

        'get MS Personal Data'
        ArrayList<String> shrPersonalData = CustomKeywords.'dbConnection.getInfoForEditNAP.shareholderDataPersonal'(sqlconnectionLOS, 
            appNo)

        'get MS Company Data'
        ArrayList<String> shrCompanyData = CustomKeywords.'dbConnection.getInfoForEditNAP.shareholderDataCompany'(sqlconnectionLOS, 
            appNo)

        'get Guarantor personal Data'
        ArrayList<String> guarPersonalData = CustomKeywords.'dbConnection.getInfoForEditNAP.guarantorDataPersonal'(sqlconnectionLOS, 
            appNo)

        'get Guarantor Company data'
        ArrayList<String> guarCompanyData = CustomKeywords.'dbConnection.getInfoForEditNAP.guarantorDataCompany'(sqlconnectionLOS, 
            appNo)

        'declare index, lastcolm variable'
        int index = 0

        int lastcolm = 0

        'declare MsName, Guarname Variable'
        String MsName = ''

        String GuarName = ''

        'declare Custname variable'
        String custname = custdata[index++]

        'Write to tab customer main data Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)

        'Write to Tax ID'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            19, GlobalVariable.NumofColm - 1, custdata[index++])

        'untuk mendapatkan posisi copy app dari excel'
        for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (findTestData(excelPathMS).getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
            if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
                13)) {
                GlobalVariable.StartIndex = GlobalVariable.NumofMS

                break
            }
        }
        
        'declare index variable'
        index = 0

        for (int colm = GlobalVariable.StartIndex; colm < ((shrPersonalData.size() / 5) + GlobalVariable.StartIndex); colm++) {
            'Write to tab Management Shareholder data customer type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                13, colm - 1, 'Personal')

            custname = (shrPersonalData[index++])

            'Write to tab Management Shareholder cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                18, colm - 1, custname)

            if (MsName == '') {
                'store MS Name'
                MsName = custname
            } else {
                'store MS Name'
                MsName = ((MsName + ';') + custname)
            }
            
            'Write to tab Management Shareholder data id type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                20, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data birth date'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                31, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                32, colm - 1, shrPersonalData[index++])

            'Write to tab Management Shareholder data mother maiden name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                34, colm - 1, shrPersonalData[index++])

            lastcolm = colm
        }
        
        'declare index variable'
        index = 0

        'lastcolm + 1'
        lastcolm++

        for (int colm = lastcolm; colm < ((shrCompanyData.size() / 2) + lastcolm); colm++) {
            'Write to tab Management Shareholder data customer type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                13, colm - 1, 'Company')

            custname = (shrCompanyData[index++])

            'Write to tab Management Shareholder cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                51, colm - 1, custname)

            if (MsName == '') {
                'store MS Name'
                MsName = custname
            } else {
                'store MS Name'
                MsName = ((MsName + ';') + custname)
            }
            
            'Write to tab Management Shareholder data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                52, colm - 1, shrCompanyData[index++])
        }
        
        'Write to dupcheck cust name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 15, 
            GlobalVariable.NumofColm - 1, MsName)

        'untuk mendapatkan posisi copy app dari excel'
        for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (findTestData(excelPathGuarantorPersonal).getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorPersonal)++) {
            if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 12) == findTestData(excelPathCustomerCompany).getValue(
                GlobalVariable.NumofColm, 13)) {
                GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorPersonal

                break
            }
        }
        
        index = 0

        for (int colm = GlobalVariable.StartIndex; colm < ((guarPersonalData.size() / 5) + GlobalVariable.StartIndex); colm++) {
            custname = (guarPersonalData[index++])

            'Write guarantor personal cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                18, colm - 1, custname)

            if (GuarName == '') {
                'Store Guar Name'
                GuarName = custname
            } else {
                'Store Guar Name'
                GuarName = ((GuarName + ';') + custname)
            }
            
            'Write guarantor personal data id type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                20, colm - 1, guarPersonalData[index++])

            'Write to guarantor personal data birth date'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                26, colm - 1, guarPersonalData[index++])

            'Write to guarantor personal data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                28, colm - 1, guarPersonalData[index++])

            'Write to guarantor personal data mother maiden name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                29, colm - 1, guarPersonalData[index++])
        }
        
        'untuk mendapatkan posisi copy app dari excel'
        for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (findTestData(excelPathGuarantorCompany).getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorCompany)++) {
            if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 12) == findTestData(excelPathCustomerCompany).getValue(
                GlobalVariable.NumofColm, 13)) {
                GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorCompany

                break
            }
        }
        
        index = 0

        for (int colm = GlobalVariable.StartIndex; colm < ((guarCompanyData.size() / 2) + GlobalVariable.StartIndex); colm++) {
            custname = (guarCompanyData[index++])

            'Write to tab guarantor company cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                17, colm - 1, custname)

            if (GuarName == '') {
                'Store Guar Name'
                GuarName = custname
            } else {
                'Store Guar Name'
                GuarName = ((GuarName + ';') + custname)
            }
            
            'Write to tab guarantor company data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                18, colm - 1, guarCompanyData[index++])
        }
        
        'Write to dupcheck cust name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 18, 
            GlobalVariable.NumofColm - 1, GuarName)
    }
}

public loopingStartIndex(Integer NumOf, TestData datafile){
	'untuk mendapatkan posisi copy app dari excel'
	for (NumOf = 2; NumOf <= datafile.getColumnNumbers() - 1; (NumOf)++) {
		if (datafile.getValue(NumOf, 12) == datafileCustomerPersonal.getValue(
			GlobalVariable.NumofColm, 13)) {
			GlobalVariable.StartIndex = NumOf

			break
		}
	}
}

'Function untuk Looping hingga return flag pada sheet commission dan reserved fund tidak bernilai "yes" lagi'
public loopingReturnComresfund(){
	'declare datafileCommission'
	datafileCommission = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')
	
	'declare datafileReservedFund'
	datafileReservedFund = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData')
	
	'Mengambil nilai row keberapa dimulai data return pada excel'
	def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData',
		'Return Commission & Reserved Fund') + 1
	
	'Mengambil nilai row keberapa dimulai data return pada excel'
	def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
		'Return Commission & Reserved Fund') + 1
	
	for(;;){
		if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || datafileReservedFund.getValue(
			GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')){
		
			if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes')){
				'write to excel flag return done'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData',
						returnRowCom, GlobalVariable.NumofColm - 1, "Done")
			}
			else if(datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')){
				'write to excel flag return done'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
						returnRowRsv, GlobalVariable.NumofColm - 1, "Done")
			}
			
			'call tc main comresfund'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/MAINComResvFund'), [:],
				FailureHandling.CONTINUE_ON_FAILURE)
		}
		else{
			break
		}
	}
}