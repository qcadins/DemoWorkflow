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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare findTestData(excelPathCustomerCompany)'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathMS'
excelPathMS = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder'

'declare excelPathGuarantorPersonal'
excelPathGuarantorPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal'

'declare excelPathGuarantorCompany'
excelPathGuarantorCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany'

if(GlobalVariable.LOB == 'CF4W'){
	'Pengecekan jika consumer finance belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'), FailureHandling.OPTIONAL)) {
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
	}
	
	'Pengecekan jika new consumer finance belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}
	
	'click Menu customer main data'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))
}else if(GlobalVariable.LOB == 'FL4W'){

	'Pengecekan jika finance leasing belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'), FailureHandling.OPTIONAL)) {
		'Klik new finance leasing'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_FinanceLeasing4W'))
	}
	
	'Pengecekan jika new consumer finance belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA FL4W'), FailureHandling.OPTIONAL)) {
		'Klik new finance leasing'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
	}
	
	'click Menu customer main data'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA FL4W'))
}

//Verify sort & paging
'call function pagingtesting'
pagingTesting()

'Ambil nilai office login dari confins'
String[] officeLogin = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OfficeLocLogin')).replace(
    ',', ';').split(';')

'declare POStat'
String POStat

if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8).length() > 1) {
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
        findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'click icon pensil untuk select'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
} else {
    'click button add'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Add'))
}

if ((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathCustomerCompany).getValue(
    GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
    8).length() < 1)) {
    if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
            findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'call custom keyword checkCopyAppPOStat'
        POStat = CustomKeywords.'dbConnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData(excelPathCustomerCompany).getValue(
                GlobalVariable.NumofColm, 9))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerCompany), 
            '1.TabCustomerMainData', GlobalVariable.NumofColm)

        if ((POStat == 'DEACT') || (POStat == 'EXP')) {
            WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut, 
                FailureHandling.OPTIONAL)
        }
    } else if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
        'click button lookup product offering'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

        'check if role = testing'
        if (GlobalVariable.RoleCompany == 'Testing') {
            String office = officeLogin[0]

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'Cek total data product offering pada db'
            Integer countPO = CustomKeywords.'dbConnection.checkProdOffering.countProdOffering'(sqlconnectionLOS, office)

            'Ambil nilai total data product offering pada lookup confins'
            String[] textTotalDataPO = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                ' ', '').replace(':', ';').split(';')

            'Parsing nilai total data PO confins ke integer(angka)'
            Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

            'Verif total data product offering confins sesuai dengan db'
            if (WebUI.verifyEqual(totalDataPO, countPO) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                    GlobalVariable.StatusFailed, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)

                GlobalVariable.FlagFailed = 1
            }
        }
        
        'input product offering code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_ProductOfferingCode'), 
            findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 12))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerCompany), 
            '1.TabCustomerMainData', GlobalVariable.NumofColm)
    }
    
    'click button next'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Next'))
} else if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes') && (findTestData(excelPathCustomerCompany).getValue(
    GlobalVariable.NumofColm, 8).length() < 1)) {
    'click button lookup copy app'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

    'input app no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 9))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

    POStat = CustomKeywords.'dbConnection.checkPOStat.checkCopyAppPOStat'(sqlconnectionLOS, findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 9))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerCompany), 
        '1.TabCustomerMainData', GlobalVariable.NumofColm)

    if ((POStat == 'DEACT') || (POStat == 'EXP')) {
        WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)
    }
    
    'click button next'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Next'))
}

'Get Appno'
String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/Applicant No'))

'Write to excel Appno'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 12, GlobalVariable.NumofColm - 
    1, appNo)

if (GlobalVariable.RoleCompany == 'Data Entry') {
    if ((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) || (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        8).length() > 1)) {
        'call Testcase tab Customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Customer/TabCustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {		
	
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (findTestData(excelPathMS).getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
        if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofMS

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        8).length() < 1)) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabManagementShareholder-Personal'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/MAINMSCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'Write to excel Success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
            0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (findTestData(excelPathGuarantorPersonal).getColumnNumbers() - 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'call test case guarantor tanpa copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonal'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataCompany', 
            0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        'Write to excel Success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (findTestData(excelPathGuarantorCompany).getColumnNumbers() - 
    1); (GlobalVariable.NumofGuarantorCompany)++) {
        if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorCompany

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'call test case guarantor tanpa copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompany'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    'call test case verify applicant'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/VerifyApplicant'), [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {

	if(GlobalVariable.IsDataCancel == 1){
		KeywordUtil.markFailedAndStop('Failed NAP1')
	}
	
    'check if copy app yes / no / edit'
    if ((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) || (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        8).length() > 1)) {
        'call Testcase tab Customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Customer/TabCustomerData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
	
		WebUI.delay(120)
		
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
	if(GlobalVariable.IsDataCancel == 1){
		KeywordUtil.markFailedAndStop('Failed NAP1')
	}
	
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (findTestData(excelPathMS).getColumnNumbers() - 1); (GlobalVariable.NumofMS)++) {
        if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofMS

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        8).length() < 1)) {
        'call test case management shareholder'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/MAINManagementShareholder'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case if edit'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/MAINMSCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathMS).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
	if(GlobalVariable.IsDataCancel == 1){
		KeywordUtil.markFailedAndStop('Failed NAP1')
	}
	
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (findTestData(excelPathGuarantorPersonal).getColumnNumbers() - 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonal'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel Success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
            0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        'Write to excel Success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.StatusFailedCopyApp)

            'customer added -1'
            (GlobalVariable.CountNumofCustomer)--

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
	if(GlobalVariable.IsDataCancel == 1){
		KeywordUtil.markFailedAndStop('Failed NAP1')
	}
	
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (findTestData(excelPathGuarantorCompany).getColumnNumbers() - 
    1); (GlobalVariable.NumofGuarantorCompany)++) {
        if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofGuarantorCompany

            break
        }
    }
    
    'check if copy app yes / no / edit'
    if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') && (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() < 1)) {
        'call test case guarantor tanpa copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompany'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit') || (findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 8).length() > 1)) {
        'call test case guarantor copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    'call test case verify applicant'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/VerifyApplicant'), [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def pagingTesting() {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
        ArrayList<WebElement> resultReset = new ArrayList<WebElement>()

        ArrayList<WebElement> checkVerifySort = new ArrayList<WebElement>()

        ArrayList<WebElement> checkVerifyFooter = new ArrayList<WebElement>()

        'Verif reset'
        resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()

        ArrayList<WebElement> listString = new ArrayList<WebElement>()

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/button_Search'))

        'Inisialisasi variabel'
        ArrayList<WebElement> rowData = DriverFactory.getWebDriver().findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'Klik header office'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_Office'))

        'Verif tidak ada alert yang muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        'Klik header appno'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_applicationNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span/a', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort appno ascending'
        Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        listApp = new ArrayList<WebElement>()

        'Klik header appno'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_applicationNo'))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span/a', true)

            listApp.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname 2x supaya descending'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_custName'))

        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_custName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_POName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_POName'))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort poname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x header appno supaya urutan descending'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_applicationNo'))

        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/span_applicationNo'))

        'Ambil count data dari confins'
        String[] textCountData = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/countData')).replace(
            ' ', '').replace(':', ';').split(';')

        Integer countDt = Integer.parseInt(textCountData[1])

        'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
        if (countDt > rowData.size()) {
            'Klik page 2'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/nextPage'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/nextPage'), 
                    'aria-current', 2))

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP1'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button prev'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/button_Prev'))

            'Verify page 1 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/pageOne'), 
                    'aria-current', 2))

            listApp = listString

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP1'(listString)

            'Verif appno yang ada di page 1 tidak ada di page 2'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button next'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/button_Next'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/nextPage'), 
                    'aria-current', 2))

            listApp = listString

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP1'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
        }
        
        'Klik button page 1'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/pageOne'))

        checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP1CountDataInPage'(), true))

        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + ((GlobalVariable.ReasonFailedReset + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + ((GlobalVariable.ReasonFailedSort + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + ((GlobalVariable.ReasonFailedFooter + 'NAP1') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
    }
}

