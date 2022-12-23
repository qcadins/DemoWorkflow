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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData')

'declare datafileReservedFund'
datafileReservedFund = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData')

'function untuk input application no dan search'
inputAppNo()

'Mengambil nilai row keberapa dimulai data return pada excel'
def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData', 
    'Return Commission & Reserved Fund') + 1

'Mengambil nilai row keberapa dimulai data return pada excel'
def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
    'Return Commission & Reserved Fund') + 1

if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || datafileReservedFund.getValue(
    GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')) {
	'verify match status request pada paging return handling'
	WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatusPaging')), 'REQUEST', false)

	'klik icon pensil untuk select'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))

	'verify match task status request'
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatus')), 'REQUEST', false)
	
	'verify curr app step pada confins'
	WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'RETURN HANDLING', false, FailureHandling.OPTIONAL)

	'call test case edit application data '
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/ReturnHandling/EditApplicationData'), [:], FailureHandling.STOP_ON_FAILURE)

	'function untuk input application no dan search'
    inputAppNo()
	
	'Looping delay +-20detik'
	for(int i = 1;i<=4;i++){
		'klik search'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))
		
		'Pengecekan kolom status bernilai done'
		if(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatusPaging')), 'DONE', false,FailureHandling.OPTIONAL)){
			break
		}
		else{
			'delay 4 detik'
			WebUI.delay(4)
		}
	}

	'klik icon pensil untuk select'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))
	
	'verify task status bernilai done'
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatus')), 'DONE', false)
}

'klik save'
WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/button_Save'))

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get status return handling dari DB table ReturnHandling Header'
String resultHeader = CustomKeywords.'dbConnection.CustomerDataVerif.checkReturnHandlingH'(sqlconnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

'verify status return handling = FINISHED'
WebUI.verifyMatch(resultHeader.toUpperCase(), 'FINISHED', false)

def inputAppNo() {
	if(WebUI.verifyElementNotVisible(findTestObject('Object Repository/NAP/ReturnHandling/MenuReturnHandling'), FailureHandling.OPTIONAL)){
		'Klik extend menu return handling'
		WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/ExtendReturnHandling'))
	}
   

    'klik return handling'
    WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/MenuReturnHandling'))
	
	'verify paging'
	pagingTesting()

    'input application no'
    WebUI.setText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), datafileCustomerPersonal.getValue(
            GlobalVariable.NumofColm, 13))

    'klik search'
    WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))

}

public pagingTesting(){
	if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckPagingPersonal=="Yes"){
		'declare result reset'
		ArrayList<String> resultReset = new ArrayList<String>()

		'declare checkverifysort'
		ArrayList<String> checkVerifySort = new ArrayList<String>()

		'declare checkverifyfooter'
		ArrayList<String> checkVerifyFooter = new ArrayList<String>()
		
		'Verif reset'
		resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()
		
		'declare liststring'
		ArrayList<String> listString = new ArrayList<String>()
		
		'click button search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/button_Search'))
		
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		
		'Inisialisasi variabel'
		ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-return-handling-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
		'Klik header office'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_Office'))
		
		'Verif tidak ada alert yang muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
		
		'Klik header appno'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_appNo'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
		
		'looping untuk simpan appno ascending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'verif sort appno ascending'
		Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		
		'verif sort appno ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		listApp = new ArrayList<String>()
		
		'Klik header appno'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_appNo'))
		
		'looping untuk simpan appno descending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listApp.add(WebUI.getText(appNoObject))
		}
		'Verif sort appno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
		
		'Verif sort appno descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header custname'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_custName'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
		
		listString = new ArrayList<String>()
		
		'looping untuk simpan custname ascending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'Verif sort custname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		
		'Verif sort custname ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header custname'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_custName'))
		listString = new ArrayList<String>()
		
		'looping untuk simpan custname descending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'Verif sort custname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		
		'Verif sort custname descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header poname'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_POName'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
		
		listString = new ArrayList<String>()
		
		'looping untuk simpan poname ascending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		
		}
		'verif sort poname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		
		'verif sort poname ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header poname'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_POName'))
		
		listString = new ArrayList<String>()
		
		'looping untuk simpan poname descending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort poname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		
		'verif sort poname descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header status'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_status'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))
		
		listString = new ArrayList<String>()
		
		'looping untuk simpan status ascending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/Status')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[7]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		
		}
		'verif sort status ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		
		'verif sort status ascending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header status'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_status'))
		
		listString = new ArrayList<String>()
		
		'looping untuk simpan status descending'
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/ReturnHandling/Status')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-return-handling-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[7]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort status descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		
		'verif sort status descending'
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik 2x appno supaya urutan descending'
		WebUI.click(findTestObject('NAP/ReturnHandling/span_appNo'))
		WebUI.click(findTestObject('NAP/ReturnHandling/span_appNo'))
		
		'Ambil count data dari confins'
		String[] textCountData = WebUI.getText(findTestObject('NAP/ReturnHandling/countData')).replace(' ', '').replace(':', ';').split(';')
		
		'parsing countdata ke integer'
		Integer countDt = Integer.parseInt(textCountData[1])
		
		'Jika count data keseluruhan lebih besar daripada jumlah data page 1'
		if(countDt>rowData.size()){
			'Klik page 2'
			WebUI.click(findTestObject('NAP/ReturnHandling/nextPage'))
			
			'verif page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/ReturnHandling/nextPage'),'aria-current', GlobalVariable.TimeOut))
					
			listString = new ArrayList<String>()
			
			'looping untuk simpan appno page 2'
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingReturnHandling'(listString)
			
			'Verif appno pada page 2 tidak ada di page 1'
			Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp,listString)
			
			'Verif appno pada page 2 tidak ada di page 1'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging,true))
			
			'Klik button prev'
			WebUI.click(findTestObject('NAP/ReturnHandling/button_Prev'))
			
			'Verify page 1 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/ReturnHandling/pageOne'),
				'aria-current', GlobalVariable.TimeOut))
			
			listApp = listString
					
			listString = new ArrayList<String>()
			
			'looping untuk simpan appno page 1'
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingReturnHandling'(listString)
					
			'Verif appno yang ada di page 1 tidak ada di page 2'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp, listString)
			
			'Verif appno yang ada di page 1 tidak ada di page 2'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
			
			'Klik button next'
			WebUI.click(findTestObject('NAP/ReturnHandling/button_Next'))
			
			'Verify page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP/ReturnHandling/nextPage'),
				'aria-current', GlobalVariable.TimeOut))
			
			listApp = listString
			
			listString = new ArrayList<String>()
			
			'looping untuk simpan appno page 2'
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingReturnHandling'(listString)
					
			'Verif appno yang ada di page 2 tidak ada di page 1'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingcustomizeKeyword.Function'(listApp, listString)
			
			'Verif appno yang ada di page 2 tidak ada di page 1'
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		}
		
		'Klik button page 1'
		WebUI.click(findTestObject('NAP/ReturnHandling/pageOne'))
		
		'verif jumlah data yang muncul pada paging sesuai'
		checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.ReturnHandlingCountDataInPage'(),true))
		
		'Jika verif reset ada yang tidak sesuai'
		if(resultReset.contains(false) && GlobalVariable.StatusFailed!=datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 1)){
						
				'Write To Excel GlobalVariable.StatusWarning and reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, ((datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace(
						'-', '') + GlobalVariable.ReasonFailedReset) + 'MAINRH') + ';\n')
				
				GlobalVariable.FlagWarning=1
		}
		
		'Jika verif sort ada yang tidak sesuai'
		if(checkVerifySort.contains(false) && GlobalVariable.StatusFailed!=datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 1)){
			
				'Write To Excel GlobalVariable.StatusWarning and reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
						'') + ((GlobalVariable.ReasonFailedSort + 'MAINRH') + ';\n'))
			
				GlobalVariable.FlagWarning=1
		}
			
		'Jika verif footer ada yang tidak sesuai'
		if(checkVerifyFooter.contains(false) && GlobalVariable.StatusFailed!=datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 1)){
			
				'Write To Excel GlobalVariable.StatusWarning and reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
						'') + ((GlobalVariable.ReasonFailedFooter + 'MAINRH') + ';\n'))
			
				GlobalVariable.FlagWarning=1
		}
	}
}