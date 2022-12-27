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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'Write to excel status success'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0,
	GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabFamily'
datafileTabFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

'declare datafileTabGuarantorPersonal'
datafileTabGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'declare datafileTabGuarantorCompany'
datafileTabGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'declare datafileReferantor'
datafileReferantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData')

'declare datafileTabUploadDoc'
datafileTabUploadDoc = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument')

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'get appno from excel'
String appNo = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 8)

'get appstep from db'
String appStep = CustomKeywords.'dbConnection.checkStep.checkAppCurrStep'(sqlconnectionLOS, appNo)

'get cust checking step from db'
String custStep = CustomKeywords.'dbConnection.checkStep.checkCustCheckStep'(sqlconnectionLOS, appNo)

'Write to excel Appno'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, GlobalVariable.NumofColm - 
    1, appNo)

'declare list untuk menampung step-step pada nap1'
List<String> NAP1Step = Arrays.asList("CUST", "FAM", "GUAR")

'declare list untuk menampung step-step pada nap2'
List<String> NAP2Step = Arrays.asList("REF", "APP", "ASSET", "INS","LFI","FIN","TC","UPL_DOC")

'menampung backward step dari excel'
String backStep = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 7)

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

'Pengecekan role data entry'
if (GlobalVariable.Role == 'Data Entry') {
	'Jika appstep == cust'
    if (appStep == 'CUST') {
		'call test case main nap1'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call test case customer dupcheck'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call test case main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call test case maincomresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call test case customerdatacompletion'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE) 
        
    } //Jika appstep ==fam
	else if (appStep == 'FAM') {
		'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)
		
        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofFamily, datafileTabFamily)
		
		'call tc familycopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, datafileTabGuarantorPersonal)
		
		'call tc guarantorpersonalcopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, datafileTabGuarantorCompany)
		
		'call tc guarantorCompanycopyapp'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)

		'call tc verifyapplicant'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call test case customer dupcheck'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } //jika appstep == GUAR
	else if (appStep == 'GUAR') {
		'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)
		
        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, datafileTabGuarantorPersonal)
		
		'call tc guarantorpersonalcopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, datafileTabGuarantorCompany)
		
		'call tc guarantorCompanycopyapp'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.CONTINUE_ON_FAILURE)

		'call tc verifyapplicant'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc cust dupcheck'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } //Jika appstep = napd atau ref atau app atau asset atau ins atau lfi atau fin atau tc 
	else if (CustomKeywords.'dbConnection.checkStep.checkNAP2Step'(appStep) == true) {
		'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)

		'call tc cust dupcheck'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], FailureHandling.CONTINUE_ON_FAILURE)

        'Jika appstep == napd atau ref'
		if ((appStep == 'NAPD') || (appStep == 'REF')) {
			'call tc main nap2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc maincomresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //Jika appstep == app
		else if (appStep == 'APP') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab application data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab term condition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload doc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //jika appstep == asset
		else if (appStep == 'ASSET') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab asset data'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //jika appstep == ins
		else if (appStep == 'INS') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //jika appstep == lfi
		else if (appStep == 'LFI') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //Jika appstep == fin
		else if (appStep == 'FIN') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        } //Jika appstep == tc
		else if (appStep == 'TC') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } //jika appstep == upl_doc
	else if (appStep == 'UPL_DOC') {
        'click menu application data'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
		
		'call tc upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } //Jika appstep == com atau rsv
	else if ((appStep == 'COM') || (appStep == 'RSV')) {
        'call tc main comresfund'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } //Jika appstep == srv dan custstep == cda_req
	else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
        'call tc nap4 cdc'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
} //Jika role == testing
else if (GlobalVariable.Role == 'Testing') {
	'Jika appstep == cust'
    if (appStep == 'CUST') {
		'call tc main nap1'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), [:], FailureHandling.STOP_ON_FAILURE)

		'call tc custdupcheck verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

		'Jika dupcheckverif bernilai yes'
        if (GlobalVariable.DupcheckVerif == 'Yes') {
			'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
		'call tc main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.STOP_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

		loopingReturnComresfund()
		
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    }//Jika appstep == fam 
	else if (appStep == 'FAM') {
		'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)
		
        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofFamily, datafileTabFamily)
		
		'call tc tabfamilycopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyCopyApp'), [:], FailureHandling.STOP_ON_FAILURE)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, datafileTabGuarantorPersonal)
		
		'call tc guarantorpersonalcopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, datafileTabGuarantorCompany)
		
		'call tc guarantorCompanycopyapp'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.STOP_ON_FAILURE)

		'call tc verify applicant'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'), [:], FailureHandling.STOP_ON_FAILURE)

		'call tc custdupcheckverif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

		'Jika dupcheckverif bernilai yes'
        if (GlobalVariable.DupcheckVerif == 'Yes') {
			'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
		'call tc main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.STOP_ON_FAILURE)

		'call tc maincomresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

		loopingReturnComresfund()
		
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } //Jika appstep == guar
	else if (appStep == 'GUAR') {
		
		'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)
		
        'click Menu customer main data'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, datafileTabGuarantorPersonal)
		
		'call tc guarantorpersonalcopyapp'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorPersonalCopyApp'), [:], 
            FailureHandling.STOP_ON_FAILURE)
		
		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofGuarantorCompany, datafileTabGuarantorCompany)
		
		'call tc guarantorCompanycopyapp'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Guarantor/TabGuarantorCompanyCopyApp'), [:],
			FailureHandling.STOP_ON_FAILURE)

		'call tc verifyapplicant'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/VerifyApplicant'), [:], FailureHandling.STOP_ON_FAILURE)

		'call tc custdupcheckverif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

		'Pengecekan jika ducpheckverif bernilai yes'
        if (GlobalVariable.DupcheckVerif == 'Yes') {
			'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
		'call tc main nap2'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
            [:], FailureHandling.STOP_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

		loopingReturnComresfund()
		
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } //Jika appstep == napd atau ref atau app atau asset atau ins atau lfi atau fin atau tc
	else if (CustomKeywords.'dbConnection.checkStep.checkNAP2Step'(appStep) == true) {
        'getcustdata untuk proses dupcheck'
		getCustdata(sqlconnectionLOS, appNo, appStep)

		'call tc custdupcheckverif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingVerif'), [:], 
            FailureHandling.STOP_ON_FAILURE)

		'jika dupcheckverif bernilai yes'
        if (GlobalVariable.DupcheckVerif == 'Yes') {
			'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.STOP_ON_FAILURE)
        }
        
		'Jika appstep == napd atau ref'
        if ((appStep == 'NAPD') || (appStep == 'REF')) {
			'call tc main nap2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //Jika appstep == app
		else if (appStep == 'APP') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab application'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //jika appstep == asset
		else if (appStep == 'ASSET') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab asset'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //jika appstep == ins
		else if (appStep == 'INS') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //jika appstep == lfi
		else if (appStep == 'LFI') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab life insurance'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //jika appstep == fin
		else if (appStep == 'FIN') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab financial'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        } //jika appstep == tc
		else if (appStep == 'TC') {
            'click menu application data'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

			'input appno pada confins'
            inputAppNo(appNo)

			'call tc tab termcondition'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'untuk mendapatkan posisi copy app dari excel'
			loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
			
			'call tc tab upload document'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], 
                FailureHandling.STOP_ON_FAILURE)

			'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

			loopingReturnComresfund()
			
			'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.STOP_ON_FAILURE)
        }
    } //jika appstep == upl_doc
	else if (appStep == 'UPL_DOC') {
        'click menu application data'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/a_APPLICATION DATA'))

		'input appno pada confins'
        inputAppNo(appNo)

		'untuk mendapatkan posisi copy app dari excel'
		loopingStartIndex(GlobalVariable.NumofUploadDocument, datafileTabUploadDoc)
		
		'call tc tab upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.STOP_ON_FAILURE)

		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

		loopingReturnComresfund()
		
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } //jika appstep == com atau rsv
	else if ((appStep == 'COM') || (appStep == 'RSV')) {
		'call tc main comresfund'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.STOP_ON_FAILURE)

		loopingReturnComresfund()
		
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } //jika appstep == srv dan custstep == cda_req
	else if ((appStep == 'SRV') && (custStep == 'CDA_REQ')) {
		'call tc nap4 cdc'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    }
}

def inputAppNo(String appNo) {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        appNo)

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
}

def getCustdata(Sql sqlconnectionLOS, String appNo, String appStep) {
	'Jika appstep == fam'
    if (appStep == 'FAM') {
		'get cust data from db'
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataPersonal'(sqlconnectionLOS, 
            appNo)

		'declare index'
        int index = 0

		'declare custname'
        String custname = custdata[index++]

        'Write to Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to ID Type'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            20, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to BirthDate'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            26, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to IDNo '
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            27, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to MotherMaidenName'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            29, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)
    } //jika appstep == guar
	else if (appStep == 'GUAR') {
		'get custdata from db'
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataPersonal'(sqlconnectionLOS, 
            appNo)

		'get famdata from db'
        ArrayList<String> famData = CustomKeywords.'dbConnection.getInfoForEditNAP.familyDataPersonal'(sqlconnectionLOS, 
            appNo)

		'declare index'
        int index = 0
		
		'declare custname'
        String custname = custdata[index++]

        'Write to Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to ID Type'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            20, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to BirthDate'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            26, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to IDNo '
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            27, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to MotherMaidenName'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            29, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)

        index = 0

        'untuk mendapatkan posisi copy app dari excel'
        loopingStartIndex(GlobalVariable.NumofFamily, datafileTabFamily)
        
		'looping famdata'
        for (int colm = GlobalVariable.StartIndex; colm < (famData.size() / 5); colm++) {
            custname = (famData[index++])

            'Write to tab Family cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 18, 
                colm - 1, custname)

            if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).length() > 0) {
                'Write to dupcheck fam name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    15, colm - 1, (datafileDupcheck.getValue(GlobalVariable.NumofColm, 16) + 
                    ';') + custname)
            } else {
                'Write to dupcheck fam name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    15, colm - 1, custname)
            }
            
            'Write to tab fam data id type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 20, 
                colm - 1, famData[index++])

            'Write to tab fam data birth date'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 29, 
                colm - 1, famData[index++])

            'Write to tab fam data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 30, 
                colm - 1, famData[index++])

            'Write to tab fam data mother maiden name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 32, 
                colm - 1, famData[index++])

            index = 0
        }
    } //Jika appstep == napd atau ref atau app atau asset atau ins atau fin atau tc
	else if (((((((appStep == 'NAPD') || (appStep == 'REF')) || (appStep == 'APP')) || (appStep == 'ASSET')) || (appStep == 
    'INS')) || (appStep == 'FIN')) || (appStep == 'TC')) {
		'get custdata from db'
        ArrayList<String> custdata = CustomKeywords.'dbConnection.getInfoForEditNAP.customerDataPersonal'(sqlconnectionLOS, 
            appNo)

		'get famdata from db'
        ArrayList<String> famData = CustomKeywords.'dbConnection.getInfoForEditNAP.familyDataPersonal'(sqlconnectionLOS, 
            appNo)

		'get guarpersonaldata from db'
        ArrayList<String> guarPersonalData = CustomKeywords.'dbConnection.getInfoForEditNAP.guarantorDataPersonal'(sqlconnectionLOS, 
            appNo)

		'get guarcompanydata from db'
        ArrayList<String> guarCompanyData = CustomKeywords.'dbConnection.getInfoForEditNAP.guarantorDataCompany'(sqlconnectionLOS, 
            appNo)

		'declare index'
        int index = 0

		'declare custname'
        String custname = custdata[index++]

        'Write to Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            18, GlobalVariable.NumofColm - 1, custname)

        'Write to ID Type'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            20, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to BirthDate'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            26, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to IDNo '
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            27, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to MotherMaidenName'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            29, GlobalVariable.NumofColm - 1, custdata[index++])

        'Write to tab duplicate checking Cust Name'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 12, 
            GlobalVariable.NumofColm - 1, custname)

        index = 0

        'untuk mendapatkan posisi copy app dari excel'
        loopingStartIndex(GlobalVariable.NumofFamily, datafileTabFamily)
        
		'looping famdata'
        for (int colm = GlobalVariable.StartIndex; colm < (famData.size() / 5); colm++) {
            custname = (famData[index++])

            'Write to tab Family cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 18, 
                colm - 1, custname)

            if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).length() > 0) {
                'Write to dupcheck fam name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    15, colm - 1, (datafileDupcheck.getValue(GlobalVariable.NumofColm, 16) + 
                    ';') + custname)
            } else {
                'Write to dupcheck fam name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    15, colm - 1, custname)
            }
            
            'Write to tab fam data id type'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 20, 
                colm - 1, famData[index++])

            'Write to tab fam data birth date'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 29, 
                colm - 1, famData[index++])

            'Write to tab fam data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 30, 
                colm - 1, famData[index++])

            'Write to tab fam data mother maiden name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 32, 
                colm - 1, famData[index++])

            index = 0
        }
        
        'untuk mendapatkan posisi copy app dari excel'
        loopingStartIndex(GlobalVariable.NumofGuarantorPersonal, datafileTabGuarantorPersonal)
        
		'looping guarpersonaldata'
        for (int colm = GlobalVariable.StartIndex; colm < (guarPersonalData.size() / 5); colm++) {
            custname = (guarPersonalData[index++])

            'Write guarantor personal cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                17, colm - 1, custname)

            if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).length() > 0) {
                'Write to dupcheck cust name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    18, colm - 1, (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19) + 
                    ';') + custname)
            } else {
                'Write to dupcheck cust name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    18, colm - 1, custname)
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

            index = 0
        }
        
        'untuk mendapatkan posisi copy app dari excel'
        loopingStartIndex(GlobalVariable.NumofGuarantorCompany, datafileTabGuarantorCompany)
        
		'looping guarcompanydata'
        for (int colm = GlobalVariable.StartIndex; colm < (guarCompanyData.size() / 2); colm++) {
            custname = (guarCompanyData[index++])

            'Write to tab guarantor company cust name'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                17, colm - 1, custname)

            if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).length() > 0) {
                'Write to dupcheck cust name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    18, colm - 1, (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19) + 
                    ';') + custname)
            } else {
                'Write to dupcheck cust name'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
                    18, colm - 1, custname)
            }
            
            'Write to tab guarantor company data id no'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                18, colm - 1, guarCompanyData[index++])

            index = 0
        }
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
	datafileCommission = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData')
	
	'declare datafileReservedFund'
	datafileReservedFund = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData')
	
	'Mengambil nilai row keberapa dimulai data return pada excel'
	def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData',
		'Return Commission & Reserved Fund') + 1
	
	'Mengambil nilai row keberapa dimulai data return pada excel'
	def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
		'Return Commission & Reserved Fund') + 1
	
	for(int i = 1; i <= i; i++){
		if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || datafileReservedFund.getValue(
			GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')){
		
			if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes')){
				'write to excel flag return done'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabCommissionData',
						returnRowCom, GlobalVariable.NumofColm - 1, "Done")
			}
			else if(datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')){
				'write to excel flag return done'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
						returnRowRsv, GlobalVariable.NumofColm - 1, "Done")
			}
			
			'call tc main comresfund'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:],
				FailureHandling.STOP_ON_FAILURE)
		}
		else{
			break
		}
	}
}