package paging

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.ArrayList

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class verifyPaging {
	// empty string: guaranteed to be less than or equal to any other
	@Keyword
	public verifySortAscending(ArrayList<String> listString){
		//asc
		String prev = ""
		for (final String current: listString) {
			println("prev:"+prev+"curr:"+current)
			//bawah (curr) harus lebih besar dari atas (prev) supaya true
			if (current.compareTo(prev) < 0){
				return false;
			}
			prev = current
		}

		return true;

	}

	//keyword verify sort descending
	@Keyword
	public verifySortDescending(ArrayList<String> listString){
		String prev = ""
		for (final String current: listString) {
			println("prev:"+prev+"curr:"+current)
			if(prev==""){
				prev = current
				continue
			}
			//bawah (curr) harus lebih kecil dari atas (prev) supaya true
			if (current.compareTo(prev) > 0){
				return false;
			}
			prev = current
		}

		return true;
	}

	//keyword verify paging function
	@Keyword
	public verifyPagingFunction(ArrayList<String> dataPageOne, ArrayList<String> dataPageTwo){
		ArrayList<String> commonData = new ArrayList<>()
		// Find common elements
		for (String temp : dataPageOne) {

			// Check if element is present in page 2 or not
			if (dataPageTwo.contains(temp)) {

				//If present, add it to commonData
				commonData.add(temp);
			}
		}

		if(commonData.size()==0){
			return true

		}
		else if(commonData.size()>0){
			return false

		}
	}

	//keyword reset paging
	@Keyword
	public resetPaging(){
		ArrayList<Boolean> arrayMatch = new ArrayList<>()
		//Reset paging nap1, nap2, comresfund, dupcheck
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),"AppNo")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),"CustName")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),"POName")
		WebUI.selectOptionByIndex(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_taskClaimStatus'),1)
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_ClaimBy'),"ClaimBy")
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),'value'),"",false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),'value'),"",false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),'value'),"",false))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_taskClaimStatus'),0,2))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_ClaimBy'),'value'),"",false))
		return arrayMatch
	}

	//keyword reset paging inquiry
	@Keyword
	public resetPagingAppInquiry(){
		ArrayList<Boolean> arrayMatch = new ArrayList<>()
		WebUI.setText(findTestObject('AppView/MainInformation/input_AppNo'),"AppNo")
		WebUI.setText(findTestObject('AppView/MainInformation/input_CustNo'),"CustNo")
		WebUI.setText(findTestObject('AppView/MainInformation/input_CustName'),"CustName")
		WebUI.setText(findTestObject('AppView/MainInformation/input_AgreementNo'),"AgreementNo")
		WebUI.setText(findTestObject('AppView/MainInformation/input_POname'),"POName")
		WebUI.setText(findTestObject('AppView/MainInformation/input_appDate'),"10/22/2022")
		WebUI.setText(findTestObject('AppView/MainInformation/input_appDateKurangDari'),"10/26/2022")
		WebUI.selectOptionByIndex(findTestObject('AppView/MainInformation/select_currentAppStep'),1)
		WebUI.selectOptionByIndex(findTestObject('AppView/MainInformation/select_currentAgreementStep'),1)
		WebUI.selectOptionByIndex(findTestObject('AppView/MainInformation/select_ApplicationStatus'),1)
		WebUI.selectOptionByIndex(findTestObject('AppView/MainInformation/select_ContractStatus'),1)
		WebUI.selectOptionByIndex(findTestObject('AppView/MainInformation/select_CustomerCheckingStep'),1)
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))

		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_AppNo'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_CustNo'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_CustName'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_AgreementNo'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_POname'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_appDate'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('AppView/MainInformation/input_appDateKurangDari'), 'value'),"", false))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('AppView/MainInformation/select_currentAppStep'),0,2))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('AppView/MainInformation/select_currentAgreementStep'),0,2))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('AppView/MainInformation/select_ApplicationStatus'),0,2))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('AppView/MainInformation/select_ContractStatus'),0,2))
		arrayMatch.add(WebUI.verifyOptionSelectedByIndex(findTestObject('AppView/MainInformation/select_CustomerCheckingStep'),0,2))

		return arrayMatch
	}

	//keyword reset paging CDC
	@Keyword
	public resetPagingCustDataCompletion(){

		ArrayList<Boolean> arrayMatch = new ArrayList<>()
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),"AppNo")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),"CustName")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),"POName")
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custNo'),"CustNo")
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),'value'),"",false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),'value'),"",false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),'value'),"",false))
		arrayMatch.add(WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custNo'),'value'),"",false))
		return arrayMatch
	}

	//keyword add ApNo for Paging NAP1
	@Keyword
	public addAppNoForPagingNAP1(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword add ApNo for Paging NAP2
	@Keyword
	public addAppNoForPagingNAP2(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword add ApNo for Paging ComResFund
	@Keyword
	public addAppNoForPagingComResFund(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP/CommissionReservedFund/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-commission-reserved-fund-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword add ApNo for Paging Dupcheck
	@Keyword
	public addAppNoForPagingDupcheck(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword add ApNo for Paging NAP4
	@Keyword
	public addAppNoForPagingNAP4(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/appNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[4]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword add ApNo for Paging Appview
	@Keyword
	public addAppNoForPagingAppView(ArrayList<String> listString){

		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()

		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

		for (int i = 1; i <= rowData.size(); i++) {
			Object appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AppNo'),
					'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[1]/span', true)

			listString.add(WebUI.getText(appNoObject))
		}
		return listString

	}

	//keyword count data in page NAP1
	@Keyword
	public NAP1CountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}

	//keyword count data in page NAP2
	@Keyword
	public NAP2CountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}

	//keyword count data in page ComResFund
	@Keyword
	public ComResFundCountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('NAP/CommissionReservedFund/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('NAP/CommissionReservedFund/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('NAP/CommissionReservedFund/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-commission-reserved-fund-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}

	//keyword count data in page Dupcheck
	@Keyword
	public DupcheckCountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}

	//keyword count data in page NAP4
	@Keyword
	public NAP4CountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}

	//keyword count data in page App Inquiry
	@Keyword
	public AppInquiryCountDataInPage(){
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		Boolean isCount = true
		WebUI.selectOptionByLabel(findTestObject('AppView/MainInformation/select_pageSize'),"20",false)
		def rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		int countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,20)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('AppView/MainInformation/select_pageSize'),"50",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,50)==false){
			isCount = false
		}
		WebUI.selectOptionByLabel(findTestObject('AppView/MainInformation/select_pageSize'),"10",false)
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		countDataInPage = rowData.size()
		if(WebUI.verifyLessThanOrEqual(countDataInPage,10)==false){
			isCount = false
		}

		return isCount
	}
}
