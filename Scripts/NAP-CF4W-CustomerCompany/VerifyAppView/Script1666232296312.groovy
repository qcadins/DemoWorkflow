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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

appno = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    13)

'click menu app inquiry'
WebUI.click(findTestObject('AppView/MENU APP INQUIRY'))

//Verify sort & paging
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
    'Verif reset'
    CustomKeywords.'paging.verifyPaging.resetPagingAppInquiry'()

    ArrayList<Boolean> listString = new ArrayList<Boolean>()

    'click button search'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Search'))

    'Inisialisasi driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi variabel'
    ArrayList<Boolean> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/AppView/span_AppNo'))

    'Verif tidak ada alert yang muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appno'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]/span/a', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appno ascending'
    Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    listApp = new ArrayList<Boolean>()

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

    WebUI.verifyEqual(isSorted, true)

    'Klik header agreement no 2x'
    WebUI.click(findTestObject('Object Repository/AppView/span_AgreementNo'))

    WebUI.click(findTestObject('Object Repository/AppView/span_AgreementNo'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_AgreementNo'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header customer no'
    WebUI.click(findTestObject('Object Repository/AppView/span_CustNo'))

    WebUI.click(findTestObject('Object Repository/AppView/span_CustNo'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_CustNo'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[3]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort cust no descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header customer name'
    WebUI.click(findTestObject('Object Repository/AppView/span_CustName'))

    WebUI.click(findTestObject('Object Repository/AppView/span_CustName'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_CustName'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header PO Name'
    WebUI.click(findTestObject('Object Repository/AppView/span_ProdOfferingName'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header poname'
    WebUI.click(findTestObject('Object Repository/AppView/span_ProdOfferingName'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ProdOfferingName'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort poname descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header app date'
    WebUI.click(findTestObject('Object Repository/AppView/span_Appdate'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appdate ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header appdate'
    WebUI.click(findTestObject('Object Repository/AppView/span_Appdate'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_Appdate'), 'xpath', 'equals', 
            ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort appdate descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('Object Repository/AppView/span_NAPSubmitted'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_NAPSubmitted'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort NAP Submitted ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header NAP Submitted'
    WebUI.click(findTestObject('Object Repository/AppView/span_NAPSubmitted'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_NAPSubmitted'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort NAP Submitted descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header agreement step'
    WebUI.click(findTestObject('Object Repository/AppView/span_AgreementStep'))

    WebUI.click(findTestObject('Object Repository/AppView/span_AgreementStep'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_AgreementStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[8]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort agreement step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header contract status'
    WebUI.click(findTestObject('Object Repository/AppView/span_ContractStatus'))

    WebUI.click(findTestObject('Object Repository/AppView/span_ContractStatus'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ContractStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[9]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort contract status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header application step'
    WebUI.click(findTestObject('Object Repository/AppView/span_ApplicationStep'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application step ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header application step'
    WebUI.click(findTestObject('Object Repository/AppView/span_ApplicationStep'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ApplicationStep'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[10]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header application status'
    WebUI.click(findTestObject('Object Repository/AppView/span_ApplicationStatus'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort application status ascending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header application status'
    WebUI.click(findTestObject('Object Repository/AppView/span_ApplicationStatus'))

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_ApplicationStatus'), 'xpath', 
            'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[11]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort application status descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header customer checking step'
    WebUI.click(findTestObject('Object Repository/AppView/span_CustomerCheckingStep'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<Boolean>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_CustomerCheckingStep'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[12]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort customer checking step descending'
    isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Ambil count data dari confins'
    String[] textCountData = WebUI.getText(findTestObject('Object Repository/AppView/countdata')).replace(' ', '').replace(
        ':', ';').split(';')

    Integer countDt = Integer.parseInt(textCountData[1])

    'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
    if (countDt > rowData.size()) {
        'Klik page 2'
        WebUI.click(findTestObject('Object Repository/AppView/nextPage'))

        'Verify page 2 active'
        WebUI.verifyElementHasAttribute(findTestObject('Object Repository/AppView/nextPage'), 'aria-current', 2)

        rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-inquiry-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        listString = new ArrayList<Boolean>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/span_appNo'), 'xpath', 'equals', 
                ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-inquiry-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[1]/span/a', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif appno yang ada di page 2 tidak ada di page 1'
        Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

        WebUI.verifyEqual(isPaging, true)
    }
}

'input app no'
WebUI.setText(findTestObject('AppView/input_AppNo'), appno)

'click button search'
WebUI.click(findTestObject('AppView/button_Search'))

'click hyperlink'
WebUI.click(findTestObject('AppView/hyperlink_APPNO'))

'swicth window ke tab baru'
WebUI.switchToWindowIndex('1')

'verify alert'
WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
    5, FailureHandling.OPTIONAL)

'delay 5 detik'
WebUI.delay(5)

ArrayList<Boolean> result = new ArrayList<Boolean>()

result = CustomKeywords.'dbconnection.CustomerDataVerif.checkAppViewDataDB'(sqlconnection, appno)

'ganti value null > "" (String kosong)'
for (i = 0; i < result.size(); i++) {
    if ((result[i]) == null) {
        (result[i]) = '-'
    }
}

int index = 0

'verify app no'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(appno, WebUI.getText(findTestObject('Object Repository/AppView/Label App No')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase())

'verify customer no'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('Login/Login').getValue(4, 1).toUpperCase(), 
    WebUI.getText(findTestObject('Object Repository/AppView/Label Office')).toString().toUpperCase(), (result[index++]).toString().toUpperCase())

'verify office'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 16).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Label Cust No')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase())

'verify customer name'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 19).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Customer Name')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase())

'verify product offering'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Product Offering Name')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify tenor'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 20).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Tenor')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase())

'verify asset name'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset Name')).toString().toUpperCase(), (result[
    index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify customer model'
CustomKeywords.'tripleVerifyMatch.TripleVerifyMatch.verifyMatch'(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 22).toUpperCase(), WebUI.getText(findTestObject('Object Repository/AppView/Customer Model')).toString().toUpperCase(), 
    (result[index++]).toString().toUpperCase())

'close window index 1'
WebUI.closeWindowIndex('1')

WebUI.switchToWindowIndex('0')

