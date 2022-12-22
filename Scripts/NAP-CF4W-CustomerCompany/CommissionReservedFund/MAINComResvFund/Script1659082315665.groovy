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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

'declare datafileReservedFund'
datafileReservedFund = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData')

if (WebUI.verifyElementNotVisible(findTestObject('NAP/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'), 
    FailureHandling.OPTIONAL)) {
    'click menu Consumer finance 4w'
    WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/a_Consumer Finance'))
}

'Klik Commission Reserved Fund'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'))

WebUI.delay(5)

//Verify sort & paging
pagingTesting()

'Input Appno'
WebUI.setText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), 
    datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))

'Klik Search'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))

'Klik Select'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))

WebUI.delay(5)

if (GlobalVariable.RoleCompany == 'Data Entry') {
    'dijalankan tanpa copy app / dengan edit hasil copy app'
    if (datafileCommission.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || datafileCommission.getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab commission'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData'), [:], FailureHandling.CONTINUE_ON_FAILURE) //dijalankan dengan copy app
    } else if (datafileCommission.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
		copyAppYesCommission()
    }
    
    'dijalankan tanpa copy app / dengan edit hasil copy app'
    if (datafileReservedFund.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || datafileReservedFund.getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab reserved fund data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData'), [:], FailureHandling.CONTINUE_ON_FAILURE) //dijalankan dengan copy app
    } else if (datafileReservedFund.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
		copyAppYesReservedFund()
    }
} else {

	'Mengambil nilai row keberapa dimulai data return pada excel'
	def returnRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Return Commission & Reserved Fund') +
	1

	if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("Yes")){
		'Klik button return'
		WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/TabCommissionData/button_Return'))
		
		'select return to'
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP/CommissionReservedFund/select_ReturnTo'),datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+2),false)
		
		'select return reason'
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP/CommissionReservedFund/select_ReturnReason'),datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+3),false)
		
		'input note'
		WebUI.setText(findTestObject('Object Repository/NAP/CommissionReservedFund/textarea_ReturnNotes'), datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+4))
	
		'klik save'
		WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/button_SaveReturn'))
		
		if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP/CommissionReservedFund/button_SaveReturn'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
			
			'klik cancel return'
			WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/button_CancelReturn'))
		}
			
		'call test case verif return handling = DB'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/ReturnHandling/ReturnHandlingVerifStoreDBAfter'), [:], FailureHandling.CONTINUE_ON_FAILURE)
			
		'call test case main return handling'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/ReturnHandling/MAINReturnHandling'), [:], FailureHandling.STOP_ON_FAILURE)
	}
	else if(datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("No")||datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("Done")||datafileCommission.getValue(GlobalVariable.NumofColm, returnRow+1).length()==0){
	    'dijalankan tanpa copy app / dengan edit hasil copy app'
	    if (datafileCommission.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || datafileCommission.getValue(
	        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
	        'call test case tab commission'
	        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData'), [:], FailureHandling.STOP_ON_FAILURE)
	    } else if (datafileCommission.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
			copyAppYesCommission()
	    }
	}
	
	returnRowCom = returnRow
	
	'Mengambil nilai row keberapa dimulai data return pada excel'
	returnRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 'Return Commission & Reserved Fund') +
	1
	
	if(datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("Yes") && !datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom+1).equalsIgnoreCase("Yes")){
		'Klik button return'
		WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/TabReservedFundData/button_Return'))
		
		'select return to'
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP/CommissionReservedFund/select_ReturnTo'),datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+2),false)
		
		'select return reason'
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP/CommissionReservedFund/select_ReturnReason'),datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+3),false)
		
		'input note'
		WebUI.setText(findTestObject('Object Repository/NAP/CommissionReservedFund/textarea_ReturnNotes'), datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+4))
	
		'klik save'
		WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/button_SaveReturn'))
		
		if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP/CommissionReservedFund/button_SaveReturn'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
			
			'klik cancel return'
			WebUI.click(findTestObject('Object Repository/NAP/CommissionReservedFund/button_CancelReturn'))
		}
			
		'call test case verif return handling = DB'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/ReturnHandling/ReturnHandlingVerifStoreDBAfter'), [:], FailureHandling.CONTINUE_ON_FAILURE)
			
		'call test case main return handling'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/ReturnHandling/MAINReturnHandling'), [:], FailureHandling.STOP_ON_FAILURE)
		
		
	}
	else if((datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("No")||datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+1).equalsIgnoreCase("Done")||datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRow+1).length()==0) && !datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom+1).equalsIgnoreCase("Yes")){
	    'dijalankan tanpa copy app / dengan edit hasil copy app'
	    if (datafileReservedFund.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || datafileReservedFund.getValue(
	        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
	        'call test case tab reserved fund data'
	        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData'), [:], FailureHandling.STOP_ON_FAILURE)
	    } else if (datafileReservedFund.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
			copyAppYesReservedFund()
	    }
	}
}

public pagingTesting(){
	if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
		ArrayList<String> resultReset = new ArrayList<String>()
	
		ArrayList<String> checkVerifySort = new ArrayList<String>()
	
		ArrayList<String> checkVerifyFooter = new ArrayList<String>()
	
		'Verif reset'
		resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()
	
		ArrayList<String> listString = new ArrayList<String>()
	
		'Klik Search'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))
	
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
	
		'Inisialisasi variabel'
		ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
		'Klik header office'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_Office'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))
	
		'Klik header appNo'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_appNo'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/appNo'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[4]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort appno ascending'
		Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		listApp = new ArrayList<String>()
	
		'Klik header appNo'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_appNo'))
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/appNo'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[4]/span', true)
	
			listApp.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort appno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header custname'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_custName'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))
	
		listString = new ArrayList<String>()
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/custName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[5]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort custname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header custname'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_custName'))
	
		listString = new ArrayList<String>()
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/custName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[5]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort custname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header product offering name'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_POName'))
	
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))
	
		listString = new ArrayList<String>()
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/POName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[6]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort po name ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header product offering name'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_POName'))
	
		listString = new ArrayList<String>()
	
		for (int i = 1; i <= rowData.size(); i++) {
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/POName'),
				'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
				i) + ']/td[6]/span', true)
	
			listString.add(WebUI.getText(appNoObject))
		}
		
		'Verif sort po name descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	
		checkVerifySort.add(WebUI.verifyEqual(isSorted, true))
	
		'Klik header appno 2x supaya urutan descending'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_appNo'))
	
		WebUI.click(findTestObject('NAP/CommissionReservedFund/span_appNo'))
	
		'Ambil string count data untuk menentukan total data secara keseluruhan'
		String[] textCountData = WebUI.getText(findTestObject('NAP/CommissionReservedFund/countData')).replace(
			' ', '').replace(':', ';').split(';')
	
		Integer countDt = Integer.parseInt(textCountData[1])
	
		'Jika total data secara keseluruhan lebih besar daripada data page 1'
		if (countDt > rowData.size()) {
			'Klik page 2'
			WebUI.click(findTestObject('NAP/CommissionReservedFund/nextPage'))
	
			'Verif page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/CommissionReservedFund/nextPage'),
					'aria-current', GlobalVariable.TimeOut))
	
			rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
			listString = new ArrayList<String>()
	
			for (int i = 1; i <= rowData.size(); i++) {
				appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)
	
				listString.add(WebUI.getText(appNoObject))
			}
			
			'Verif appno page 2 descending tidak ada di page 1'
			Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp, listString)
	
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
	
			'Klik button prev'
			WebUI.click(findTestObject('NAP/CommissionReservedFund/button_Prev'))
	
			'Verify page 1 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/CommissionReservedFund/pageOne'),
					'aria-current', GlobalVariable.TimeOut))
	
			listApp = listString
	
			listString = new ArrayList<String>()
	
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingComResFund'(listString)
	
			'Verif appno yang ada di page 1 tidak ada di page 2'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp, listString)
	
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
	
			'Klik button next'
			WebUI.click(findTestObject('NAP/CommissionReservedFund/button_Next'))
	
			'Verify page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/CommissionReservedFund/nextPage'),
					'aria-current', GlobalVariable.TimeOut))
	
			listApp = listString
	
			listString = new ArrayList<String>()
	
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingComResFund'(listString)
	
			'Verif appno yang ada di page 2 tidak ada di page 1'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp, listString)
	
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		}
		
		'Klik button page 1'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/pageOne'))
	
		checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.ComResFundCountDataInPage'(), true))
	
		if (resultReset.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
			1))) {
			
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, ((datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + GlobalVariable.ReasonFailedReset) + 'Comresfund') + ';\n')
	
			GlobalVariable.FlagWarning = 1
		}
		
		if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
			1))) {
	
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedSort + 'Comresfund') + ';\n'))
			
			GlobalVariable.FlagWarning = 1
		}
		
		if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
			1))) {
	
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedFooter + 'Comresfund') + ';\n'))
	
			GlobalVariable.FlagWarning = 1
		}
	}
}

public copyAppYesCommission(){
	'Klik Calculate'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Calculate'))

	'Klik save'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Save'))

	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 0,
		GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

	'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
	if (WebUI.verifyElementPresent(findTestObject('NAP/CommissionReservedFund/TabCommissionData/select_AmountPercentage'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('12.TabCommissionData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

		'Klik cancel'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Cancel'))

		'Pengecekan jika new consumer finance belum diexpand'
		if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
			'Klik new consumer finance'
			WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
		}
	}
}

public copyAppYesReservedFund(){
	'Klik Button Calculate'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Calculate'))

	'Klik save'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Save'))

	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

	'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
	if (WebUI.verifyElementPresent(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Cancel'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('13.TabReservedFundData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

		'Klik cancel'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/TabReservedFundData/button_Cancel'))

		'Pengecekan jika new consumer finance belum diexpand'
		if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
			'Klik new consumer finance'
			WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
		}
	}
}