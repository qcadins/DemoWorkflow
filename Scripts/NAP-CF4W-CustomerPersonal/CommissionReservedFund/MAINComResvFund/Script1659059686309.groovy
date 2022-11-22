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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

if (WebUI.verifyElementNotVisible(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'),
	FailureHandling.OPTIONAL)) {
	'click menu Consumer finance 4w'
	WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/a_Consumer Finance'))
}

'Klik Commission Reserved Fund'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'))

WebUI.delay(5)

//Verify sort & paging
if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckPagingPersonal=="Yes"){
	ArrayList<Boolean> resultReset,checkVerifySort,checkVerifyFooter = new ArrayList<>()
	
	'Verif reset'
	resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()
	
	ArrayList<String> listString = new ArrayList<String>()
	
	'Klik Search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Search'))
	
	'Inisialisasi driver'
	WebDriver driver = DriverFactory.getWebDriver()
	
	'Inisialisasi variabel'
	ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

	'Klik header office'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_Office'))
	
	'Verify alert tidak muncul'
	checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
	
	'Klik header appNo'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_appNo'))
	
	'Verify alert tidak muncul'
	checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
	
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
	}
	'Verif sort appno ascending'
	Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	listApp = new ArrayList<String>()
	'Klik header appNo'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_appNo'))

	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
		
		listApp.add(WebUI.getText(appNoObject))
	}
	'Verif sort appno descending'
	isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	'Klik header custname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_custName'))
	
	'Verify alert tidak muncul'
	checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/custName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort custname ascending'
	isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	'Klik header custname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_custName'))
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/custName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort custname descending'
	isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	'Klik header product offering name'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_POName'))
	
	'Verify alert tidak muncul'
	checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort po name ascending'
	isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	'Klik header product offering name'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_POName'))
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort po name descending'
	isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
	checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
	
	'Klik header appno 2x supaya urutan descending'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_appNo'))
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/span_appNo'))
	
	'Ambil string count data untuk menentukan total data secara keseluruhan'
	String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/countData')).replace(' ', '').replace(':', ';').split(';')
	Integer countDt = Integer.parseInt(textCountData[1])
	
	'Jika total data secara keseluruhan lebih besar daripada data page 1'
	if(countDt>rowData.size()){
		'Klik page 2'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/nextPage'))
		
		'Verif page 2 active'
		checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/nextPage'),'aria-current',2))
		
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'Verif appno page 2 descending tidak ada di page 1'
		Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp,listString)
		checkVerifyFooter.add(WebUI.verifyEqual(isPaging,true))
		
		'Klik button prev'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/button_Prev'))
		
		'Verify page 1 active'
		checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/pageOne'),
			'aria-current', 2))
		
		listApp = listString
				
		listString = new ArrayList<String>()
		
		listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingComResFund'(listString)
				
		'Verif appno yang ada di page 1 tidak ada di page 2'
		isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
		
		checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		
		'Klik button next'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/button_Next'))
		
		'Verify page 2 active'
		checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/nextPage'),
			'aria-current', 2))
		
		listApp = listString
		
		listString = new ArrayList<String>()
		
		listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingComResFund'(listString)
				
		'Verif appno yang ada di page 2 tidak ada di page 1'
		isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
		
		checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		
	}
	
	'Klik button page 1'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/pageOne'))
	
	checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.ComResFundCountDataInPage'(),true))
	
	if(resultReset.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 1)){
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
	
			(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
				1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 2).replace("-","")+GlobalVariable.ReasonFailedReset+"Comresfund"+";\n")
	
			GlobalVariable.FlagWarning=1
	}
	
	if(checkVerifySort.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 1)){
				(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
						0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
		
				(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
						1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedSort+"Comresfund"+";\n"))
		
			GlobalVariable.FlagWarning=1
	}
		
	if(checkVerifyFooter.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 1)){
				(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
						0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
		
				(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
						1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedFooter+"Comresfund"+";\n"))
		

			GlobalVariable.FlagWarning=1
	}
}

'Input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'),
	findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13))

'Klik Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Search'))

'Klik Select'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/i_Select'))

WebUI.delay(5)

if (GlobalVariable.Role == 'Data Entry') {
	
	'dijalankan tanpa copy app / dengan edit hasil copy app'
	if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')) {
		'call test case tab commission'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/TabCommissionData'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')){
	
		'Klik Calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Calculate'))
	
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
		'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_AmountPercentage'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
	
			'Klik cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Cancel'))
	
			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
			}
		}
	
	}
	
	'dijalankan tanpa copy app / dengan edit hasil copy app'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')){
		'call test case tab reserved fund data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/TabReservedFundData'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')){
		'Klik Button Calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Calculate'))
	
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
		'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
	
			'Klik cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))
	
			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
			}
		}
	}

} else {

    'dijalankan tanpa copy app / dengan edit hasil copy app'
	if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')) {
		'call test case tab commission'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/TabCommissionData'),
			[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')){
	
		'Klik Calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Calculate'))
	
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
		'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_AmountPercentage'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
	
			'Klik cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Cancel'))
	
			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
			}
		}
	
	}
	
	'dijalankan tanpa copy app / dengan edit hasil copy app'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')){
		'call test case tab reserved fund data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/TabReservedFundData'),
		[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')){
		'Klik Button Calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Calculate'))
	
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
		'Pengecekan jika setelah klik save, button cancel masih bisa diklik'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
	
			'Klik cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabReservedFundData/button_Cancel'))
	
			'Pengecekan jika new consumer finance belum diexpand'
			if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
			}
		}
	}
}


