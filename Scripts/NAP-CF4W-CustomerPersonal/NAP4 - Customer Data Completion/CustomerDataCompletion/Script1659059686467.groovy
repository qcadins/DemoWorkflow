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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

//'klik menu new consumer finance'
//WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_New Consumer Finance'))

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileCDC'
datafileCDC = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion')

if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'), FailureHandling.OPTIONAL)){
	'click menu consumer finance'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
}

if(WebUI.verifyElementNotVisible(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION'), FailureHandling.OPTIONAL)){
	'click menu new consumer finance'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
}

'click menu Customer Data Completion'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION'))

WebUI.delay(10)

'click menu Customer Data Completion'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION'))

'call function paging testing'
pagingTesting()

'input Appno'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), 
    datafileCDC.getValue(GlobalVariable.NumofColm, 12))

'click search'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Search'))

'click action'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/i_CF4W 2021_font-medium-3 ft-edit-2'))

'declare driver'
WebDriver driver = DriverFactory.getWebDriver()

'declare variable'
ArrayList<String> variable = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-detail > div > div > div > div.px-3 > lib-ucgridview > div > table > tbody tr'))

'declare count'
int count = variable.size()

GlobalVariable.FlagFailed = 0

'Jika role testing dan edit appno tidak ada isinya pada excel'
if ((GlobalVariable.Role == 'Testing') && (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 8).length() ==
0)) {
	'verify equal number of customer'
	checkVerifyEqualOrMatch(WebUI.verifyEqual(GlobalVariable.CountNumofCustomer, count, FailureHandling.OPTIONAL))
}

'looping cdc subject'
for (int i = 1; i <= count; i++) {

    'modify object customername'
    modifynewCustomerName = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/CustomerName'), 
        'xpath', 'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[2]', true)

    'modify object customertype'
    modifynewCustomerType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/CustomerType'), 
        'xpath', 'equals', ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[3]', true)

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
    def CustomerArray = datafileCDC.getValue(GlobalVariable.NumofColm, 13).split(';')

    'tampung family array excel'
    def FamilyArray = datafileCDC.getValue(GlobalVariable.NumofColm, 15).split(';')

    'tampung guarantor array excel'
    def GuarantorArray = datafileCDC.getValue(GlobalVariable.NumofColm, 17).split(';')

    'verify customerarray > 0'
    if (CustomerArray.size() > 0) {
        'looping customer array'
        for (c = 1; c <= CustomerArray.size(); c++) {
            	'verify if action == YES'            
                if ((GlobalVariable.Role == 'Testing' && datafileCDC.getValue(GlobalVariable.NumofColm, 14) == 'YES') || 
				(GlobalVariable.Role == 'Data Entry' && isComplete.equalsIgnoreCase('NO'))) {
                    'verify if customerarray == customer name'
                    if (CustomerName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        'call test case customer personal'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/MAINCustomerPersonalTC'), 
                        		[:], FailureHandling.CONTINUE_ON_FAILURE)                        
						
						isComplete = WebUI.getText(modifynewisComplete)
						
						'Verify iscomplete == yes'
						checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                    }
            }
        }
    }
    
    'verify if family array > 0'
    if (FamilyArray.size() > 0) {
        'looping family array'
        for (f = 1; f <= FamilyArray.size(); f++) {
            	'verify action == YES'
                if (datafileCDC.getValue(GlobalVariable.NumofColm, 16) == 'YES') {
                    'verify customername == family array'
                    if (CustomerName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        'call test case customer personal (Family)'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/FamilyPersonal/MAINFamilyPersonalTC'), 
                        		[:], FailureHandling.CONTINUE_ON_FAILURE)
						
						isComplete = WebUI.getText(modifynewisComplete)
						
						'Verify iscomplete == yes'
						checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                    }
            }
        }
    }
    
    'verify gurantor array > 0'
    if (GuarantorArray.size() > 0) {
        'looping guarantor array'
        for (g = 1; g <= GuarantorArray.size(); g++) {
            	'verify action == YES'
                if (datafileCDC.getValue(GlobalVariable.NumofColm, 18) == 'YES') {
                    'verify customername == guarantorarray'
                    if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'PERSONAL')) {
						'klik button action'
                        WebUI.click(modifynewButtonAction)

                        'call test case guarantor personal'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                        		[:], FailureHandling.CONTINUE_ON_FAILURE)
						
						isComplete = WebUI.getText(modifynewisComplete)
						
						'Verify iscomplete == yes'
						checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
						
                    } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        'call test case guarantor company'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                        		[:], FailureHandling.CONTINUE_ON_FAILURE)
						
						isComplete = WebUI.getText(modifynewisComplete)
						
						'Verify iscomplete == yes'
						checkVerifyEqualOrMatch(WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL))
                    }
            }
        }
    }
}

'click button submit'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Submit'))

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

Integer iscompleteMandatory = Integer.parseInt(datafileCDC.getValue(GlobalVariable.NumofColm, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '15.CustomerDataCompletion')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), 
        GlobalVariable.NumofColm, '15.CustomerDataCompletion')
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('15.CustomerDataCompletion', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
        GlobalVariable.FlagFailed = 1
    }
}

def pagingTesting(){
	//Verify sort & paging
	if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckPagingPersonal == 'Yes')) {
		'declare result reset'
		ArrayList<String> resultReset = new ArrayList<String>()
	
		'declare checkverifysort'
		ArrayList<String> checkVerifySort = new ArrayList<String>()
	
		'declare checkverifysort'
		ArrayList<String> checkVerifyFooter = new ArrayList<String>()
	
		//	verif reset nap4
		resultReset = CustomKeywords.'paging.verifyPaging.resetPagingCustDataCompletion'()
	
		'declare liststring'
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
	
		'looping simpan appno ascending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[4]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'verif sort appno ascending'
		Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		'verif sort appno ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		listApp = new ArrayList<String>()
	
		'Klik header appno'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))
	
		'looping simpan appno descending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[4]/span', true)
	
			listApp.add(WebUI.getText(appNoObject))
		}
		
		'verif sort appno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
	
		'verif sort appno descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik 2x header custno supaya descending'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custNo'))
	
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custNo'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
	
		listString = new ArrayList<String>()
	
		'looping simpan custno descending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custNo'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[5]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort custno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	
		'Verif sort custno descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header custname'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custName'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
	
		listString = new ArrayList<String>()
	
		'looping simpan custname ascending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[6]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'verif sort custname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		'verif sort custname ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header custname'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_custName'))
	
		listString = new ArrayList<String>()
	
		'looping simpan custname descending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/custName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[6]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'verif sort custname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	
		'verif sort custname descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header poname'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_POName'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
	
		listString = new ArrayList<String>()
	
		'looping simpan poname ascending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/POName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[7]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'verif sort poname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		'verif sort poname ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header poname'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_POName'))
	
		listString = new ArrayList<String>()
	
		'looping simpan poname descending'
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/POName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[7]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'verif sort poname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	
		'verif sort poname descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik 2x appno supaya descending'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))
	
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/span_appNo'))
	
		'Ambil nilai count data dari confins'
		String[] textCountData = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/countData')).replace(
			' ', '').replace(':', ';').split(';')
	
		'parsing integer countdata'
		Integer countDt = Integer.parseInt(textCountData[1])
	
		'Jika count data secara keseluruhan lebih besar daripada data pada page 1'
		if (countDt > rowData.size()) {
			'Klik page 2'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'))
	
			'Verif page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'),
					'aria-current', GlobalVariable.TimeOut))
	
			rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
			listString = new ArrayList<String>()
	
			'looping simpan appno page 2'
			for (int i = 1; i <= rowData.size(); i++) {
				appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)
	
				listString.add(WebUI.getText(appNoObject))
			}
			
			'Verif appno pada page 2 tidak ada di page 1'
			Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
	
			'Verif appno pada page 2 tidak ada di page 1'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
	
			'Klik button prev'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/button_Prev'))
	
			'Verify page 1 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/pageOne'),
					'aria-current', GlobalVariable.TimeOut))
	
			listApp = listString
	
			listString = new ArrayList<String>()
	
			'looping simpan appno page 1'
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP4'(listString)
	
			'Verif appno yang ada di page 1 tidak ada di page 2'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
	
			'Verif appno yang ada di page 1 tidak ada di page 2'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
	
			'Klik button next'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/button_Next'))
	
			'Verify page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/nextPage'),
					'aria-current', GlobalVariable.TimeOut))
	
			listApp = listString
	
			listString = new ArrayList<String>()
	
			'looping simpan appno page 2'
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP4'(listString)
	
			'Verif appno yang ada di page 2 tidak ada di page 1'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
	
			'Verif appno yang ada di page 2 tidak ada di page 1'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		}
		
		'Klik button page 1'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/pageOne'))
	
		'verif jumlah data yang muncul dalam paging sesuai footer'
		checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP4CountDataInPage'(), true))
	
		'jika verif reset tidak sesuai'
		if (resultReset.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm,
			1))) {
	
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, ((datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + GlobalVariable.ReasonFailedReset) + 'NAP4') + ';\n')
	
			GlobalVariable.FlagWarning = 1
		}
		
		'jika verif sort tidak sesuai'
		if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm,
			1))) {
	
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, ((datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + GlobalVariable.ReasonFailedSort) + 'NAP4') + ';\n')
			
			GlobalVariable.FlagWarning = 1
		}
		
		'jika verif footer tidak sesuai'
		if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm,
			1))) {
	
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, ((datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + GlobalVariable.ReasonFailedFooter) + 'NAP4') + ';\n')
			
			GlobalVariable.FlagWarning = 1
		}
	}
}