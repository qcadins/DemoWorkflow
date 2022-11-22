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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryCompany)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

GlobalVariable.FlagWarning = 0

'get appno dari excel'
appno = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    13)

'click menu app inquiry'
WebUI.click(findTestObject('AppView/MainInformation/MENU APP INQUIRY'))

'Verify sort & paging'
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
    'declare arraylist resultset, checkVerifySort, checkVerifyFooter'
    ArrayList<String> resultReset, checkVerifySort, checkVerifyFooter

    'Verif reset'
    resultReset = CustomKeywords.'paging.verifyPaging.resetPagingAppInquiry'()

    'declare arraylist liststring'
    ArrayList<WebElement> listString = new ArrayList<WebElement>()

    'click button search'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Search'))

    'Inisialisasi driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi variabel'
    ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

    'Klik header appno'
    WebUI.click(findTestObject('AppView/MainInformation/span_AppNo'))

    'Verif tidak ada alert yang muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        'add appno to arraylist liststring'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appno ascending'
    Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add result to arraylist checkVerify sort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'declare arraylist listApp'
    listApp = new ArrayList<WebElement>()

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/AppView/span_Appno'))

    'looping row data confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        'add appno to arraylist listapp'
        listApp.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appno descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement no 2x'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))

    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer no 2x'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))

    WebUI.click(findTestObject('AppView/MainInformation/span_CustNo'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[3]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort cust no descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer name 2x'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))

    WebUI.click(findTestObject('AppView/MainInformation/span_CustName'))

    'reset listString arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustName'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add result verify to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header PO Name'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset liststring arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add result verify to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header poname'
    WebUI.click(findTestObject('AppView/MainInformation/span_ProdOfferingName'))

    'reset listring arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort poname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header app date'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset listString arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appdate ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add verify result to checkverifysort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header appdate'
    WebUI.click(findTestObject('AppView/MainInformation/span_Appdate'))

    'reset listString arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify object appno'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appdate descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add result verify to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset liststring arraylist'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        'add appnoto listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort NAP Submitted ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('AppView/MainInformation/span_NAPSubmitted'))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_NAPSubmitted'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort NAP Submitted descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header agreement step'
    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))

    WebUI.click(findTestObject('AppView/MainInformation/span_AgreementStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_AgreementStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[8]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort agreement step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header contract status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))

    WebUI.click(findTestObject('AppView/MainInformation/span_ContractStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ContractStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[9]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort contract status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application step ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application step'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStep'))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application status ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header application status'
    WebUI.click(findTestObject('AppView/MainInformation/span_ApplicationStatus'))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Klik header customer checking step'
    WebUI.click(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'))

    'Verify alert tidak muncul'
    checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))

    'reset arraylist listString'
    listString = new ArrayList<WebElement>()

    'looping rowdata confins'
    for (int i = 1; i <= rowData.size(); i++) {
        'modify appno object'
        appNoObject = WebUI.modifyObjectProperty(findTestObject('AppView/MainInformation/span_CustomerCheckingStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[12]/span', true)

        'add appno to listString'
        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort customer checking step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    'add verify result to checkVerifySort'
    checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

    'Ambil count data dari confins'
    String[] textCountData = WebUI.getText(findTestObject('AppView/MainInformation/countdata')).replace(' ', '').replace(
        ':', ';').split(';')

    'declare variable countDt'
    Integer countDt = Integer.parseInt(textCountData[1])

    'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
    if (countDt > rowData.size()) {
        'Klik page 2'
        WebUI.click(findTestObject('AppView/MainInformation/nextPage'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', 
                2))

        'count row data'
        rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'reset listString arraylist'
        listString = new ArrayList<WebElement>()

        'looping rowdata confins'
        for (int i = 1; i <= rowData.size(); i++) {
            'modify appno object'
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[1]/span/a', true)

            'add appno to listString'
            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif appno yang ada di page 2 tidak ada di page 1'
        Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        'add verify result to checkVerifySort'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button prev'
        WebUI.click(findTestObject('AppView/MainInformation/button_Prev'))

        'Verify page 1 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/pageOne'), 'aria-current', 
                2))

		'declare listapp = liststring'
        listApp = listString

        'reset arraylist listString'
        listString = new ArrayList<WebElement>()

        'call addAppNoForPagingAppView keyword'
        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 1 tidak ada di page 2'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        'add verify result to checkVerifyFooter'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

        'Klik button next'
        WebUI.click(findTestObject('AppView/MainInformation/button_Next'))

        'Verify page 2 active'
        checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('AppView/MainInformation/nextPage'), 'aria-current', 
                2))

		'declare listapp = liststring'
        listApp = listString

        'reset arraylist ListString'
        listString = new ArrayList<WebElement>()

        'call addAppNoForPagingAppView keyword'
        listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingAppView'(listString)

        'Verif appno yang ada di page 2 tidak ada di page 1'
        isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        'add verify result to checkVerifyFooter'
        checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
    }
    
    'Klik button page 1'
    WebUI.click(findTestObject('AppView/MainInformation/pageOne'))

    'add verify result to checkVerifyFooter'
    checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.AppInquiryCountDataInPage'(), true))

    'check if resultReset contain false'
    if (resultReset.contains(false)) {
        'write status warning to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)

        'write reason failed reset to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 1, GlobalVariable.NumofColm - 
            1, findTestData('NAP-CF4W-CustomerCompany/AppView/MainInformation').getValue(GlobalVariable.NumofColm, 2).replace(
                '-', '') + (GlobalVariable.ReasonFailedReset + ';\n'))

		'flagwarning = 1'
        GlobalVariable.FlagWarning = 1
    }
    
    'check if checkVerifySort contain false'
    if (checkVerifySort.contains(false)) {
        'write status warning to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)

        'write reason failed sort to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 1, GlobalVariable.NumofColm - 
            1, findTestData('NAP-CF4W-CustomerCompany/AppView/MainInformation').getValue(GlobalVariable.NumofColm, 2).replace(
                '-', '') + (GlobalVariable.ReasonFailedSort + ';\n'))

		'flagwarning = 1'
        GlobalVariable.FlagWarning = 1
    }
    
    'check if checkVerifyFooter contain false'
    if (checkVerifyFooter.contains(false)) {
        'write status warning to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)

        'write reason failed footer to excel'
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '1.MainInformation', 1, GlobalVariable.NumofColm - 
            1, findTestData('NAP-CF4W-CustomerCompany/AppView/MainInformation').getValue(GlobalVariable.NumofColm, 2).replace(
                '-', '') + (GlobalVariable.ReasonFailedFooter + ';\n'))

		'flag warning = 1'
        GlobalVariable.FlagWarning = 1
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

	'write Status Warning'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm, '1. Customer')
}

'delay 5 detik'
WebUI.delay(5)

'declare result arraylist'
ArrayList<WebElement> result = new ArrayList<WebElement>()

'call checkAppViewDataDB keyword'
result = CustomKeywords.'appView.verifyAppView.checkAppViewData'(sqlconnectionLOS, appno)

'ganti value null > "" (String kosong)'
for (i = 0; i < result.size(); i++) {
    if ((result[i]) == null) {
        (result[i]) = '-'
    }
}

'declare index = 0'
int index = 0

'verify app no'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(appno, WebUI.getText(findTestObject(
                'AppView/MainInformation/Label App No')).toString().toUpperCase(), (result[index++]).toString().toUpperCase()))

'verify office'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('Login/Login').getValue(
            4, 1).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Office')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    14) == 'LookUp') {
    'verify customer no'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 16).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Label Cust No')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 17).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
} else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    14) == 'Input Data') {
    'skip'
    result[index++]

    'verify customer name'
    checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 19).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Name')).toString().toUpperCase(), 
            (result[index++]).toString().toUpperCase()))
}

'verify product offering'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Product Offering Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tenor'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 20).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Tenor')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/MainInformation/Asset Name')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
checkVerifyEqualOrMatch(CustomKeywords.'customizeKeyword.tripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 22).toUpperCase(), WebUI.getText(findTestObject('AppView/MainInformation/Customer Model')).toString().toUpperCase(), 
        (result[index++]).toString().toUpperCase()))

'call test case view tab customer'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabCustomer'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Guarantor'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabGuarantor'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Referantor'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabReferantor'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Application'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabApplication'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Asset'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabAsset'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab insurance'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabInsurance'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Financial'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabFinancial'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Term condition'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabTC'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab Commission'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabCommission'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'call test case view tab reserved fund'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/AppView/ViewTabReservedFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'close window index 1'
WebUI.closeWindowIndex('1')

'switch window index 0'
WebUI.switchToWindowIndex('0')

'check if flagwarning = 0 & flagfailed = 0'
if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel success'
    new customizeKeyword.writeExcel().writeToExcel(GlobalVariable.DataFilePath, '1. MainInformation', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	'check ismatch = false & flagfailed = 0'
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel failed'
        new customizeKeyword.writeExcel().writeToExcel(GlobalVariable.DataFilePath, '1. MainInformation', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
        new customizeKeyword.writeExcel().writeToExcel(GlobalVariable.DataFilePath, '1. MainInformation', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		'flagfailed = 1'
        GlobalVariable.FlagFailed = 1
    }
}