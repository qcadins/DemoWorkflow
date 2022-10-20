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

WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_New Consumer Finance'))

'click menu Customer Data Completion'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION'))

//Verify sort & paging
if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckSortPagingPersonal == 'Yes')) {
	//	verif reset nap4
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),"0002APP20211204858")
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),"KADEHARA KAZUHA")
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),"CF4W MRA")
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custNo'),"0002CUST20211224975")
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Reset'))
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_AppNo'),'value'),"",false)
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custName'),'value'),"",false)
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_POName'),'value'),"",false)
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/input_custNo'),'value'),"",false)

    ArrayList<String> listString = new ArrayList<String>()

    'click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Search'))

    'Inisialisasi driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi variabel'
    ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

    'Klik header office'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_Office'))

    'Verif tidak ada alert yang muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_appNo'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/appNo'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appno ascending'
    Boolean isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    listApp = new ArrayList<String>()

    'Klik header appno'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_appNo'))

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/appNo'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[4]/span', true)

        listApp.add(WebUI.getText(appNoObject))
    }
    
    'verif sort appno descending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listApp)

    WebUI.verifyEqual(isSorted, true)

    'Klik 2x header custno supaya descending'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_custNo'))

    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_custNo'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/custNo'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[5]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'Verif sort custno descending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header custname'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_custName'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/custName'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname ascending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header custname'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_custName'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/custName'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort custname descending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header poname'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_POName'))

    'Verify alert tidak muncul'
    WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 
        2)

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/POName'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname ascending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik header poname'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_POName'))

    listString = new ArrayList<String>()

    for (int i = 1; i <= rowData.size(); i++) {
        appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/POName'), 
            'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[7]/span', true)

        listString.add(WebUI.getText(appNoObject))
    }
    
    'verif sort poname descending'
    isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)

    WebUI.verifyEqual(isSorted, true)

    'Klik 2x appno supaya descending'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_appNo'))

    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/span_appNo'))

    'Ambil nilai count data dari confins'
    String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/countData')).replace(
        ' ', '').replace(':', ';').split(';')

    Integer countDt = Integer.parseInt(textCountData[1])

    'Jika count data secara keseluruhan lebih besar daripada data pada page 1'
    if (countDt > rowData.size()) {
        'Klik page 2'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/nextPage'))

        'Verif page 2 active'
        WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/nextPage'), 
            'aria-current', 2)

        rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        listString = new ArrayList<String>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-cust-completion-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif appno pada page 2 tidak ada di page 1'
        Boolean isPaging = CustomKeywords.'sortPaging.verifySortPaging.verifyPaging'(listApp, listString)

        WebUI.verifyEqual(isPaging, true)
    }
}

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 12))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Search'))

'click action'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/i_CF4W 2021_font-medium-3 ft-edit-2'))

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<String> variable = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-detail > div > div > div > div.px-3 > lib-ucgridview > div > table > tbody tr'))

int count = variable.size()

int flagFailed = 0

'verify equal number of customer'
WebUI.verifyEqual(GlobalVariable.countNumofCustomer, count, FailureHandling.OPTIONAL)

for (int i = 1; i <= count; i++) {
    String newCustomerName = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[2]'

    'modify object customername'
    modifynewCustomerName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/CustomerName'), 
        'xpath', 'equals', newCustomerName, true)

    String newCustomerType = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[3]'

    'modify object customertype'
    modifynewCustomerType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
        'xpath', 'equals', newCustomerType, true)

    String newisComplete = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[7]'

    'modify object iscomplete'
    modifynewisComplete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/td_isComplete'), 
        'xpath', 'equals', newisComplete, true)

    String newButtonAction = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[8]'

    'modify object button action'
    modifynewButtonAction = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/buttonAction'), 
        'xpath', 'equals', newButtonAction, true)

    'get text customer name'
    String CustomerName = WebUI.getText(modifynewCustomerName)

    'get text iscomplete'
    String isComplete = WebUI.getText(modifynewisComplete)

    'get text customertype'
    String CustomerType = WebUI.getText(modifynewCustomerType)

    'tampung customer array excel'
    def CustomerArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 13).split(';')

    'tampung family array excel'
    def FamilyArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 15).split(';')

    'tampung guarantor array excel'
    def GuarantorArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 17).split(';')

    'verify customerarray > 0'
    if (CustomerArray.size() > 0) {
        'looping customer array'
        for (c = 1; c <= CustomerArray.size(); c++) {
            'verify if iscomplete == no'
            if (isComplete == 'NO') {
                'verify if customerarray == customename'
                if (CustomerName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case customer personal'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/MAINCustomerPersonalTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        'call test case customer personal'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/MAINCustomerPersonalTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 14) == 'YES') {
                    'verify if customerarray == customename'
                    if (CustomerName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case customer personal'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/MAINCustomerPersonalTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            'call test case customer personal'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerPersonal/MAINCustomerPersonalTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
    
    'verify if family array > 0'
    if (FamilyArray.size() > 0) {
        'looping family array'
        for (f = 1; f <= FamilyArray.size(); f++) {
            'verify iscomplete == NO'
            if (isComplete == 'NO') {
                'verify customername == family array'
                if (CustomerName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case customer personal (Family)'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/FamilyPersonal/MAINFamilyPersonalTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        'call test case customer personal (Family)'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/FamilyPersonal/MAINFamilyPersonalTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 16) == 'YES') {
                    'verify customername == family array'
                    if (CustomerName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case customer personal (Family)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/FamilyPersonal/MAINFamilyPersonalTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            'call test case customer personal (Family)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/FamilyPersonal/MAINFamilyPersonalTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
    
    'verify gurantor array > 0'
    if (GuarantorArray.size() > 0) {
        'looping guarantor array'
        for (g = 1; g <= GuarantorArray.size(); g++) {
            'verify iscomplete == NO'
            if (isComplete == 'NO') {
                'verify customername == guarantorarray'
                if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'PERSONAL')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case guarantor personal'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case guarantor company'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 18) == 'YES') {
                    'verify customername == guarantorarray'
                    if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'PERSONAL')) {
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case guarantor personal'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case guarantor company'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
    
    'Verify iscomplete == yes'
    WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL)
}

'click button submit'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Submit'))

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

GlobalVariable.DataFilePath = filePath

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0) {
    'cek alert'
    flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '15.CustomerDataCompletion')
}

if (flagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/input_Application No_AppNoId'), 
        GlobalVariable.NumofColm, '15.CustomerDataCompletion')
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

if(GlobalVariable.Role == 'Testing' && GlobalVariable.CheckAppViewPersonal == 'Yes'){
	'call test case verify app view'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/VerifyAppView'), [:], FailureHandling.CONTINUE_ON_FAILURE)
}

