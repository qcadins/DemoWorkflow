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

Integer copyAppColm = 2
GlobalVariable.CopyAppColm=2

'click menu application data'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/a_APPLICATION DATA'))

//Verify sort & paging
if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckSortPagingPersonal=="Yes"){
	'Verif reset'
	CustomKeywords.'sortPaging.verifyReset.resetPaging'()
	
	ArrayList<String> listString = new ArrayList<String>()
	
	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))
	
	'Inisialisasi driver'
	WebDriver driver = DriverFactory.getWebDriver()
	
	'Inisialisasi variabel'
	ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

	'Klik header office'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_Office'))
	'Verif tidak ada alert yang muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	'Klik header appno'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
	}
	'verif sort appno ascending'
	Boolean isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	listApp = new ArrayList<String>()
	'Klik header appno'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
		
		listApp.add(WebUI.getText(appNoObject))
	}
	'Verif sort appno descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listApp)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header custname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_custName'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/custName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort custname ascending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header custname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_custName'))
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/custName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'Verif sort custname descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header poname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_POName'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
	
	}
	'verif sort poname ascending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header poname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_POName'))
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
		
	}
	'verif sort poname descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik 2x appno supaya urutan descending'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
	
	'Ambil count data dari confins'
	String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/countData')).replace(' ', '').replace(':', ';').split(';')
	Integer countDt = Integer.parseInt(textCountData[1])
	
	'Jika count data keseluruhan lebih besar daripada jumlah data page 1'
	if(countDt>rowData.size()){
		'Klik page 2'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/nextPage'))
		
		'verif page 2 active'
		WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/nextPage'),'aria-current',2)
		
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'Verif appno pada page 2 tidak ada di page 1'
		Boolean isPaging = CustomKeywords.'sortPaging.verifySortPaging.verifyPaging'(listApp,listString)
		WebUI.verifyEqual(isPaging,true)
		
	}
}

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'),
	findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))

'click icon pensil untuk select'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

if (GlobalVariable.Role == 'Data Entry') {
	
	if((Integer.parseInt(GlobalVariable.CountofReferantor) > 0)){
		'Looping untuk mencari nilai colm yang menunjukkan colm appno'
		for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) +
		1); (GlobalVariable.NumofReferantor)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
				GlobalVariable.NumofReferantor, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13)) {
					copyAppColm = GlobalVariable.NumofReferantor
					break
			
			}
		}
	}
	
	'Dijalankan tanpa copy app tab referantor atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    copyAppColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    copyAppColm, 10).equalsIgnoreCase("Edit")){
		GlobalVariable.CopyAppColm = copyAppColm
		'call test case tab referantor'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabReferantorData'),
					[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app tab referantor
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    copyAppColm, 10).equalsIgnoreCase("Yes")){
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Save'))
		
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
		'verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
					'REFERANTOR', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
		
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
		}
	}
	'Dijalankan tanpa copy app tab application atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'Call test case tab application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app tab application
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		WebUI.delay(5)
			
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
		}
	}
	'Dijalankan tanpa copy app tab asset atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab asset data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
		
	}
	//dijalankan dengan copy app tab asset
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
					
					
		'input chasis number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 29))
					
		'input engine number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 30))
		
		'input license plate number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
				GlobalVariable.NumofColm, 31))
		
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 4'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
					GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
		}
		
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 5'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
					GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
		}
					
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Save'))
			
		'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
		WebUI.waitForAlert(3)
			
		'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)
		
		'Menunggu Alert konfirmasi integrator muncul'
		WebUI.waitForAlert(3)
	
		'Accept Alert Konfirmasi Integrator'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
						GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	println(GlobalVariable.NumofColm)
	'Dijalankan tanpa copy app tab insurance atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab insurance'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
		
	}
	//dijalankan dengan copy app tab insurance
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
					
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'),3,FailureHandling.OPTIONAL)){
			'Klik calculate insurance'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
		}
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Save'))
			
		WebUI.delay(5)
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		'verify fail'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
		}
	}
    
	'Dijalankan tanpa copy app tab life insurance atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab life insurance'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app tab life insurance
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
		
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
		
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))
		 
		}
	}

   
	'Dijalankan tanpa copy app tab financial atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab financial'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//Dijalankan dengan copy app tab financial
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click button calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
			
		WebUI.delay(5)
			
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}


	'Dijalankan tanpa copy app tab term & condition atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab term condition'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//Dijalankan dengan copy app tab term&condition
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}

	
	'Looping untuk mencari nilai colm yang menunjukkan colm appno'
	for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (Integer.parseInt(GlobalVariable.CountofUploadDocument) + 
		1); (GlobalVariable.NumofUploadDocument)++) {
        	if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
				GlobalVariable.NumofUploadDocument, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13)) {
			copyAppColm = GlobalVariable.NumofUploadDocument
			break
        }
	}
	
	'Dijalankan tanpa copy app tab upload document atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab upload document'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
						[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
	//dijalankan dengan copy app tab upload document
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("Yes")){
		'click button submit'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Submit'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}

} else {

	if((Integer.parseInt(GlobalVariable.CountofReferantor) > 0)){
		'Looping untuk mencari nilai colm yang menunjukkan colm appno'
		for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) +
		1); (GlobalVariable.NumofReferantor)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
				GlobalVariable.NumofReferantor, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13)) {
					copyAppColm = GlobalVariable.NumofReferantor
					break
			
			}
		}
	}
	
	'Dijalankan tanpa copy app tab referantor atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
					copyAppColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
					copyAppColm, 10).equalsIgnoreCase("Edit")){
				GlobalVariable.CopyAppColm == copyAppColm
		'call test case tab referantor'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabReferantorData'),
					[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app tab referantor
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
					copyAppColm, 10).equalsIgnoreCase("Yes")){
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Save'))
		
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
		'verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
					'REFERANTOR', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
		
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
		}
	}
	'Dijalankan tanpa copy app tab application atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'Call test case tab application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'),
						[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app tab application
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		WebUI.delay(5)
			
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
		}
	}
	'Dijalankan tanpa copy app tab asset atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab asset data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
						[:], FailureHandling.STOP_ON_FAILURE)
		
	}
	//dijalankan dengan copy app tab asset
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
					
		'input chasis number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 29))
								
		'input engine number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
						GlobalVariable.NumofColm, 30))
					
		'input license plate number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
				GlobalVariable.NumofColm, 31))
					
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 4'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
					GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
		}
					
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 5'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
				findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
					GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
		}
						
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Save'))
			
		'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
		WebUI.waitForAlert(3)
			
		'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)
		
		'Menunggu Alert konfirmasi integrator muncul'
		WebUI.waitForAlert(3)
	
		'Accept Alert Konfirmasi Integrator'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
						GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
		WebUI.delay(5)
			
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	println("num of colm:"+GlobalVariable.NumofColm)
	'Dijalankan tanpa copy app tab insurance atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab insurance'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
						[:], FailureHandling.STOP_ON_FAILURE)
		
	}
	//dijalankan dengan copy app tab insurance
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
					
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'),3,FailureHandling.OPTIONAL)){
			'Klik calculate insurance'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
		}
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Save'))
			
		WebUI.delay(5)
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		'verify fail'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
		}
	}
	
	'Dijalankan tanpa copy app tab life insurance atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab life insurance'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
						[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app tab life insurance
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))
	
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
		
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
		
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))
		 
		}
	}
	
	
	'Dijalankan tanpa copy app tab financial atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab financial'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
						[:], FailureHandling.STOP_ON_FAILURE)
	}
	//Dijalankan dengan copy app tab financial
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'click button calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
			
		WebUI.delay(5)
			
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	
	'Dijalankan tanpa copy app tab term & condition atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab term condition'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
						[:], FailureHandling.STOP_ON_FAILURE)
	}
	//Dijalankan dengan copy app tab term&condition
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData').getValue(
						GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
		'Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Save'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
						0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')),
			'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}
	
	
	'Looping untuk mencari nilai colm yang menunjukkan colm appno'
	for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (Integer.parseInt(GlobalVariable.CountofUploadDocument) +
		1); (GlobalVariable.NumofUploadDocument)++) {
			if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
				GlobalVariable.NumofUploadDocument, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13)) {
			copyAppColm = GlobalVariable.NumofUploadDocument
			break
		}
	}
	
	'Dijalankan tanpa copy app tab upload document atau copy app dengan edit'
	if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("No") || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("Edit")){
		'call test case tab upload document'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
						[:], FailureHandling.STOP_ON_FAILURE)
	}
	//dijalankan dengan copy app tab upload document
	else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
						copyAppColm, 10).equalsIgnoreCase("Yes")){
		'click button submit'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Submit'))
			
		'Write to excel success'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
			
		if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
			
			'Write to excel failed reason'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)
			
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}
}
