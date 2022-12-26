import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement

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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'decare datafileMainInforamtion'
datafileMainInformation = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/AppView/MainInformation')

'declare datafileLogin'
datafileLogin = findTestData('Login/Login')

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData')

'get appno from excel'
appno = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13)

'click menu app inquiry'
WebUI.click(findTestObject('AppView/MainInformation/MENU APP INQUIRY'))

//Verify sort & paging
if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckPagingPersonal == 'Yes')) {
	'declare result reset'
	ArrayList<String> resultReset = new ArrayList<String>()

	'declare check verify sort'
    ArrayList<String> checkVerifySort = new ArrayList<String>()

	'declare check verify footer'
    ArrayList<String> checkVerifyFooter = new ArrayList<String>()
    
	'Verif reset'
    resultReset = CustomKeywords.'paging.verifyPaging.resetPagingAppInquiry'()

	'declare list string'
    ArrayList<String> listString = new ArrayList<String>()

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/button_Search'))

    'Inisialisasi driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi variabel'
    ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

    'Klik header appno'
    WebUI.click(findTestObject('AppView/MainInformation/span_AppNo'))

    'Verif tidak ada alert yang muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

	'looping untuk menyimpan appno ascending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appno ascending'
    Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort appno ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    listApp = new ArrayList<String>()

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/AppView/span_Appno'))

	'looping untuk menyimpan appno descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        listApp.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appno descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

	'verif sort appno descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement no 2x'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))
	WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan agreement no descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort agreement no descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'verif sort agreement no descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer no'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))
    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan cust no descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[3]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort cust no descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'verif sort cust no descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer name'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))
    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan customer name descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustName'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'verif sort custname descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header PO Name'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan po name ascending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort poname ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header poname'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan po name descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort poname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort poname descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header app date'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

	'looping untuk menyimpan app date ascending'
    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appdate ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort appdate ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header appdate'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan app date descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appdate descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort appdate descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan nap submitted ascending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort NAP Submitted ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort NAP Submitted ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan nap submitted descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort NAP Submitted descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort NAP Submitted descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement step'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan aggreement step descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[8]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort agreement step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort agreement step descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header contract status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))
	WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan contract status descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ContractStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[9]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort contract status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort contract status descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan app step ascending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application step ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort application step ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan app step descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort application step descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan app status ascending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application status ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

	'verif sort application status ascending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    listString = new ArrayList<String>()

	'looping untuk menyimpan app status descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort application status descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik 2x header customer checking step'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'))
	WebUI.click(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

    listString = new ArrayList<String>()

	'looping untuk menyimpan cust checking step descending'
    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[12]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort customer checking step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

	'Verif sort customer checking step descending'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Ambil count data dari confins'
    String[] textCountData = WebUI.getText(findTestObject('AppView/MainInformation/countdata')).replace(' ', '').replace(
        ':', ';').split(';')

	'Parsing count data dari confins ke dalam bentuk integer'
    Integer countDt = Integer.parseInt(textCountData[1])

    'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
    if (countDt > rowData.size()) {
        'Klik page 2'
        WebUI.click(findTestObject('AppView/MainInformation/nextPage'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', GlobalVariable.TimeOut))

        rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        listString = new ArrayList<String>()

		'looping untuk menyimpan appno di page 2'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[1]/span/a', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif appno yang ada di page 2 tidak ada di page 1'
        Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

		'Verif appno yang ada di page 2 tidak ada di page 1'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button prev'
        WebUI.click(findTestObject('AppView/MainInformation/button_Prev'))

        'Verify page 1 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/pageOne'), 'aria-current', GlobalVariable.TimeOut))

        listApp = listString

        listString = new ArrayList<String>()

		'keyword yang berisi looping untuk menyimpan appno di page 1'
        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 1 tidak ada di page 2'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

		'Verif appno yang ada di page 1 tidak ada di page 2'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button next'
        WebUI.click(findTestObject('AppView/MainInformation/button_Next'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', GlobalVariable.TimeOut))

        listApp = listString

        listString = new ArrayList<String>()

		'keyword yang berisi looping untuk menyimpan appno di page 2'
        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 2 tidak ada di page 1'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

		'Verif appno yang ada di page 2 tidak ada di page 1'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
    }
    
    'Klik button page 1'
    WebUI.click(findTestObject('AppView/MainInformation/pageOne'))

	'verif jumlah data yang muncul pada confins sesuai'
    checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.AppInquiryCountDataInPage'(), true))
	
	'Pengecekan jika verif reset ada yang tidak sesuai'
	if(resultReset.contains(false)){
				'write to excel status warning'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
					0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
		
				'write to excel reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
					1, GlobalVariable.NumofColm-1, datafileMainInformation.getValue(
						GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedReset+";\n"))
		
				GlobalVariable.FlagWarning=1
	}
			
	'Pengecekan jika verif sort ada yang tidak sesuai'
	if(checkVerifySort.contains(false) ){
				'write to excel status warning'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
				'write to excel reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
							1, GlobalVariable.NumofColm-1, datafileMainInformation.getValue(
				GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedSort+";\n"))
			
				GlobalVariable.FlagWarning=1
	}
			
	'Pengecekan jika verif footer ada yang tidak sesuai'
	if(checkVerifyFooter.contains(false)){
				'write to excel status warning'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
				'write to excel reason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.MainInformation',
							1, GlobalVariable.NumofColm-1, datafileMainInformation.getValue(
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
if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut) == false) {
    GlobalVariable.FlagWarning = 1

	'write warning status'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm, '1. Customer')
}

'delay 5 detik'
WebUI.delay(5)

ArrayList<String> result = new ArrayList<String>()

'keyword untuk get main info data dari db'
result = CustomKeywords.'appView.verifyAppView.checkAppViewData'(sqlconnection, appno)

'declare index'
int index = 0

'verify app no'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(appno, WebUI.getText(findTestObject(
                'AppView/MainInformation/Label App No')).toString().toUpperCase(), (result[index++]).toString().toUpperCase()))

'verify office'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileLogin.getValue(
            4, 1).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Office')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'Pengecekan sheet customermaindata lookup/input data'
if (datafileCustomerPersonal.getValue(
    GlobalVariable.NumofColm, 14) == 'LookUp') {
    'verify customer no'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileCustomerPersonal.getValue(
                GlobalVariable.NumofColm, 16).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Cust No')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileCustomerPersonal.getValue(
                GlobalVariable.NumofColm, 17).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
} else if (datafileCustomerPersonal.getValue(
    GlobalVariable.NumofColm, 14) == 'Input Data') {
    'Skip'
    result[index++]

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileCustomerPersonal.getValue(
                GlobalVariable.NumofColm, 19).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
}

'verify product offering'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Product Offering Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tenor'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 20).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Tenor')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Asset Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(datafileCustomerPersonal.getValue(
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

'switch window ke tab lama'
WebUI.switchToWindowIndex('0')

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1. MainInformation', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1. Customer', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1. Customer', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}
