import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathAppInquiryPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

GlobalVariable.FlagWarning = 0

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connect'(url, username, password, driverclassname)

appno = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 13)

'click menu app inquiry'
WebUI.click(findTestObject('AppView/MainInformation/MENU APP INQUIRY'))

//Verify sort & paging
if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckPagingPersonal == 'Yes')) {
	ArrayList<Boolean> resultReset,checkVerifySort,checkVerifyFooter = new ArrayList<>()
    'Verif reset'
    resultReset = CustomKeywords.'paging.verifyPaging.resetPagingAppInquiry'()

    ArrayList<String> listString = new ArrayList<String>()

    'click button search'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Search'))

    'Inisialisasi driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi variabel'
    ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

    'Klik header appno'
    WebUI.click(findTestObject('AppView/MainInformation/span_AppNo'))

    'Verif tidak ada alert yang muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appno ascending'
    Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    listApp = new ArrayList<String>()

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/AppView/span_Appno'))

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        listApp.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appno descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement no 2x'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))

    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer no'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))

    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[3]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort cust no descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer name'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))

    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustName'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header PO Name'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header poname'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort poname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header app date'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appdate ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header appdate'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appdate descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort NAP Submitted ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort NAP Submitted descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement step'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))

    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[8]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort agreement step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header contract status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))

    WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ContractStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[9]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort contract status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application step ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application status ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer checking step'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[12]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort customer checking step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Ambil count data dari confins'
    String[] textCountData = WebUI.getText(findTestObject('AppView/MainInformation/countdata')).replace(' ', '').replace(
        ':', ';').split(';')

    Integer countDt = Integer.parseInt(textCountData[1])

    'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
    if (countDt > rowData.size()) {
        'Klik page 2'
        WebUI.click(findTestObject('AppView/MainInformation/nextPage'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', 2))

        rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[1]/span/a', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif appno yang ada di page 2 tidak ada di page 1'
        Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button prev'
        WebUI.click(findTestObject('AppView/MainInformation/button_Prev'))

        'Verify page 1 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/pageOne'), 'aria-current', 2))

        listApp = listString

        listString = new ArrayList<String>()

        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 1 tidak ada di page 2'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button next'
        WebUI.click(findTestObject('AppView/MainInformation/button_Next'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', 2))

        listApp = listString

        listString = new ArrayList<String>()

        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 2 tidak ada di page 1'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
    }
    
    'Klik button page 1'
    WebUI.click(findTestObject('AppView/MainInformation/pageOne'))

    checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.AppInquiryCountDataInPage'(), true))
	
	if(resultReset.contains(false)){
				(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
					0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
		
				(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
					1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/AppView/MainInformation').getValue(
						GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedReset+";\n"))
		
				GlobalVariable.FlagWarning=1
	}
			
	if(checkVerifySort.contains(false) ){
					(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
					(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
							1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/AppView/MainInformation').getValue(
				GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedSort+";\n"))
			
					GlobalVariable.FlagWarning=1
	}
			
	if(checkVerifyFooter.contains(false)){
					(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
					(new customizeKeyword.writeToExcel()).writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation',
							1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/AppView/MainInformation').getValue(
			    GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedFooter+";\n"))
			
	
					GlobalVariable.FlagWarning=1
	}
}

'input app no'
WebUI.setText(findTestObject('AppView/MainInformation/input_AppNo'), appno)

'click button search'
WebUI.click(findTestObject('AppView/MainInformation/button_Search'))

'click hyperlink'
WebUI.click(findTestObject('AppView/MainInformation/hyperlink_APPNO'))

'swicth window ke tab baru'
WebUI.switchToWindowIndex('1')

'Verif tidak ada alert yang muncul'
if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2) == false) {
    GlobalVariable.FlagWarning = 1

    CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm, '1. Customer')
}

'delay 5 detik'
WebUI.delay(5)

ArrayList<String> result = new ArrayList<String>()

result = CustomKeywords.'appView.verifyAppView.checkAppViewData'(sqlconnection, appno)

'ganti value null > "" (String kosong)'
for (i = 0; i < result.size(); i++) {
    if ((result[i]) == null) {
        (result[i]) = '-'
    }
}

int index = 0

'verify app no'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(appno, WebUI.getText(findTestObject(
                'AppView/MainInformation/Label App No')).toString().toUpperCase(), (result[index++]).toString().toUpperCase()))

'verify office'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('Login/Login').getValue(
            4, 1).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Office')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 14) == 'LookUp') {
    'verify customer no'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 16).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Cust No')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 17).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
} else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 14) == 'Input Data') {
    'Skip'
    result[index++]

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 19).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
}

'verify product offering'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Product Offering Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tenor'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 20).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Tenor')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Asset Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 25).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Model')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'call test case view tab customer'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabCustomer'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab guarantor'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabGuarantor'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab referantor'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabReferantor'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab application'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabApplication'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab asset'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabAsset'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab insurance'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabInsurance'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab life insurance'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabLifeInsurance'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab financial'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabFinancial'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Term condition'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabTC'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Commission'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabCommission'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab reserved fund'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabReservedFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'close window index 1'
WebUI.closeWindowIndex('1')

WebUI.switchToWindowIndex('0')

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
    new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1. MainInformation', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1. Customer', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1. Customer', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}
