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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
 
if(GlobalVariable.LOB == 'CF4W'){
	if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'), FailureHandling.OPTIONAL)){
		'click menu consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
	}
	
	if(WebUI.verifyElementNotVisible(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION CF4W'), FailureHandling.OPTIONAL)){
		'click menu new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}
	
	'click menu Customer Data Completion'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION CF4W'))
}else if(GlobalVariable.LOB == 'FL4W'){
	'Pengecekan jika finance leasing belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'), FailureHandling.OPTIONAL)) {
		'Klik new finance leasing'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_FinanceLeasing4W'))
	}
	
	'Pengecekan jika new finance leasing belum diexpand'
	if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA FL4W'), FailureHandling.OPTIONAL)) {
		'Klik new finance leasing'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
	}
	
	'click menu Customer Data Completion'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION FL4W'))
}

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

//'connect DB FOU'
//Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
//
//'get gen set cdc'
//ArrayLis<String> Excluded = CustomKeywords.'nap4Data.checkNAP4.GenSetCDC'(sqlConnectionFOU)

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathCDC'
excelPathCDC = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion'

'call funtion paging testing'
pagingTesting()

'input Appno'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), findTestData(excelPathCDC).getValue(
        GlobalVariable.NumofColm, 12))

'click search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Search'))

'click action'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/i_CF4W 2021_font-medium-3 ft-edit-2'))

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<String> variable = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-detail > div > div > div > div.px-3 > lib-ucgridview > div > table > tbody tr'))

int count = variable.size()

GlobalVariable.FlagFailed = 0

//'Jika role testing dan edit appno tidak ada isinya pada excel'
//if ((GlobalVariable.Role == 'Testing') && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8).length() ==
//0)) {
//	'verify equal number of customer'
//	checkVerifyEqualOrMatch(WebUI.verifyEqual(GlobalVariable.CountNumofCustomer, count, FailureHandling.OPTIONAL))
//
//}

for (int i = 1; i <= count; i++) {
    'modify object customername'
    modifynewCustomerName = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerName'), 'xpath', 
        'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[2]', true)

    'modify object customertype'
    modifynewCustomerType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerType'), 'xpath', 
        'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[3]', true)

    'modify object iscomplete'
    modifynewisComplete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/td_isComplete'), 
        'xpath', 'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[7]', true)

    'modify object button action'
    modifynewButtonAction = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/buttonAction'), 
        'xpath', 'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[8]', true)

    'get text customer name'
    String CustomerName = WebUI.getText(modifynewCustomerName)

    'get text iscomplete'
    String isComplete = WebUI.getText(modifynewisComplete)

    'get text customertype'
    String CustomerType = WebUI.getText(modifynewCustomerType)

    'tampung customer array excel'
    def CustomerArray = findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 13).split(';')

    'tampung managementshareholder array excel'
    def ManagementShareholderArray = findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 15).split(';')

    'tampung guarantor array excel'
    def GuarantorArray = findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 17).split(';')

    'verify customerarray > 0'
    if (CustomerArray.size() > 0) {
        'looping customer array'
        for (c = 1; c <= CustomerArray.size(); c++) {
            'verify action == Yes'
            if ((GlobalVariable.RoleCompany == 'Testing' && findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 14) == 'YES') || 
				(GlobalVariable.RoleCompany == 'Data Entry' && isComplete.equalsIgnoreCase('NO'))) {
                'verify if customerarray == customer name'
                if (CustomerName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    'call test case customer Company'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
                    		[:], FailureHandling.CONTINUE_ON_FAILURE)

					
					isComplete = WebUI.getText(modifynewisComplete)
					
					'Verify iscomplete == yes'
					checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                }
            } else {
                continue
            }
        }
    }
    
    'verify if managementshareholder array > 0'
    if (ManagementShareholderArray.size() > 0) {
        'looping managementshareholder array'
        for (f = 1; f <= ManagementShareholderArray.size(); f++) {
            'verify action == Yes'
            if (findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 16) == 'YES') {
                'verify customername == managementshareholder array'
                if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'PERSONAL')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    'call test case customer personal (ManagementShareholder)'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/MAINManagementShareholderPersonalTC'), 
                    		[:], FailureHandling.CONTINUE_ON_FAILURE)

					
					isComplete = WebUI.getText(modifynewisComplete)
					
					'Verify iscomplete == yes'
					checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                } else if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'COMPANY')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    'call test case customer personal (ManagementShareholder)'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/MAINManagementShareholderCompanyTC'), 
                    		[:], FailureHandling.CONTINUE_ON_FAILURE)
					
					isComplete = WebUI.getText(modifynewisComplete)
					
					'Verify iscomplete == yes'
					checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                }				
            } else {
                continue
            }
        }
    }
    
    'verify gurantor array > 0'
    if (GuarantorArray.size() > 0) {
        'looping guarantor array'
        for (g = 1; g <= GuarantorArray.size(); g++) {
            'verify action == YES'
            if (findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 18) == 'YES') {
                'verify customername == guarantorarray'
                if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'PERSONAL')) {
                    WebUI.click(modifynewButtonAction)

                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                    		[:], FailureHandling.CONTINUE_ON_FAILURE)
					
					isComplete = WebUI.getText(modifynewisComplete)
					
					'Verify iscomplete == yes'
					checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
					
                } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                    		[:], FailureHandling.CONTINUE_ON_FAILURE)
					
					isComplete = WebUI.getText(modifynewisComplete)
					
					'Verify iscomplete == yes'
					checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                }
            } else {
                continue
            }
        }
    }
}

'click button submit'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Submit'))

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathCDC).getValue(GlobalVariable.NumofColm, 4))

if (GlobalVariable.FlagFailed == 0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '14.CustomerDataCompletion')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), 
        GlobalVariable.NumofColm, '14.CustomerDataCompletion')
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'), GlobalVariable.TimeOut, 
    FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed //Verify sort & paging
        //	verif reset nap4
        )
} else {
    'write to excel if success'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('14.CustomerDataCompletion', GlobalVariable.NumofColm, 
            GlobalVariable.StatusWarning, (findTestData(excelPathCDC).getValue(
                GlobalVariable.NumofColm, 2) + ';') + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def pagingTesting() {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
        ArrayList<String> resultReset = new ArrayList<String>()

        ArrayList<String> checkVerifySort = new ArrayList<String>()

        ArrayList<String> checkVerifyFooter = new ArrayList<String>()

        resultReset = CustomKeywords.'paging.verifyPaging.resetPagingCustDataCompletion'()

        ArrayList<String> listString = new ArrayList<String>()

        'click search'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Search'))

        'Inisialisasi driver'
        WebDriver driver = DriverFactory.getWebDriver()

        'Inisialisasi variabel'
        ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'Klik header office'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_Office'))

        'Verif tidak ada alert yang muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        'Klik header appno'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno ascending'
        Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        listApp = new ArrayList<String>()

        'Klik header appno'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listApp.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x header custno supaya descending'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custNo'))

        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custNo'), 'xpath', 
                'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort custno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custName'), 'xpath', 
                'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custName'))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custName'), 'xpath', 
                'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_POName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/POName'), 'xpath', 
                'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[7]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_POName'))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/POName'), 'xpath', 
                'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[7]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x appno supaya descending'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))

        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))

        'Ambil nilai count data dari confins'
        String[] textCountData = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/countData')).replace(' ', 
            '').replace(':', ';').split(';')

        Integer countDt = Integer.parseInt(textCountData[1])

        'Jika count data secara keseluruhan lebih besar daripada data pada page 1'
        if (countDt > rowData.size()) {
            'Klik page 2'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'))

            'Verif page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'), 
                    'aria-current', 2))

            rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

            listString = new ArrayList<String>()

            for (int i = 1; i <= rowData.size(); i++) {
                appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'), 'xpath', 
                    'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[4]/span', true)

                listString.add(WebUI.getText(appNoObject))
            }
            
            'Verif appno pada page 2 tidak ada di page 1'
            Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button prev'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/button_Prev'))

            'Verify page 1 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/pageOne'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<String>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP4'(listString)

            'Verif appno yang ada di page 1 tidak ada di page 2'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button next'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/button_Next'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<String>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP4'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
        }
        
        'Klik button page 1'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/pageOne'))

        checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP4CountDataInPage'(), true))

        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, ((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + GlobalVariable.ReasonFailedReset) + 'NAP4') + ';\n')

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + ((GlobalVariable.ReasonFailedSort + 'NAP4') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-', 
                    '') + ((GlobalVariable.ReasonFailedFooter + 'NAP4') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
    }
}

