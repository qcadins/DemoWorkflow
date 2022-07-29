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

WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/a_New Consumer Finance'))

'click menu Customer Data Completion'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/a_CUSTOMER DATA COMPLETION'))

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
        2))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Search'))

'click action'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/i_CF4W 2021_font-medium-3 ft-edit-2'))

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> variable = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-cust-completion-detail > div > div > div > div.px-3 > lib-ucgridview > div > table > tbody tr'))

int count = variable.size()

'verify equal number of customer'
WebUI.verifyEqual(GlobalVariable.countNumofCustomer, count, FailureHandling.OPTIONAL)

for (int i = 1; i <= count; i++) {
    String newCustomerName = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[2]'

    'modify object customername'
    modifynewCustomerName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/CustomerName'), 
        'xpath', 'equals', newCustomerName, true)

    String newCustomerType = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[3]'

    'modify object customertype'
    modifynewCustomerType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/AddressInformation - Personal/CustomerType'), 
        'xpath', 'equals', newCustomerType, true)

    String newisComplete = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[7]'

    'modify object iscomplete'
    modifynewisComplete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/td_isComplete'), 
        'xpath', 'equals', newisComplete, true)

    String newButtonAction = ('//*[@class="table-responsive"]/table/tbody/tr[' + i) + ']/td[8]'

    'modify object button action'
    modifynewButtonAction = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/buttonAction'), 
        'xpath', 'equals', newButtonAction, true)

    'get text customer name'
    String CustomerName = WebUI.getText(modifynewCustomerName)

    'get text iscomplete'
    String isComplete = WebUI.getText(modifynewisComplete)

    'get text customertype'
    String CustomerType = WebUI.getText(modifynewCustomerType)

    'tampung customer array excel'
    def CustomerArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 3).split(';')

    'tampung managementshareholder array excel'
    def ManagementShareholderArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 5).split(';')

    'tampung guarantor array excel'
    def GuarantorArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
        GlobalVariable.NumofColm, 7).split(';')

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
                        'call test case customer Company'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        'call test case customer Company'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 4) == 'YES') {
                    'verify if customerarray == customename'
                    if (CustomerName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case customer Company'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            'call test case customer Company'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
    
    'verify if managementshareholder array > 0'
    if (ManagementShareholderArray.size() > 0) {
        'looping managementshareholder array'
        for (f = 1; f <= ManagementShareholderArray.size(); f++) {
            'verify iscomplete == NO'
            if (isComplete == 'NO') {
                'verify customername == managementshareholder array'
                if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'PERSONAL')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case customer personal (ManagementShareholder)'
                       WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/MAINManagementShareholderPersonalTC'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        'call test case customer personal (ManagementShareholder)'
                       WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/MAINManagementShareholderPersonalTC'), 
    [:], FailureHandling.STOP_ON_FAILURE)
                    }
                    
                    'Verify iscomplete == yes'
                    WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL)
                } else if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'COMPANY')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case customer company (ManagementShareholder)'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/MAINManagementShareholderCompanyTC'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        'call test case customer company (ManagementShareholder)'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/MAINManagementShareholderCompanyTC'), 
    [:], FailureHandling.STOP_ON_FAILURE)
                    }
                    
                    'Verify iscomplete == yes'
                    WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL)
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 6) == 'YES') {
                    'verify customername == managementshareholder array'
                    if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'PERSONAL')) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case customer personal (ManagementShareholder)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/MAINManagementShareholderPersonalTC'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            'call test case customer personal (ManagementShareholder)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderPersonal/MAINManagementShareholderPersonalTC'), 
    [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    } else if (CustomerName.equalsIgnoreCase(ManagementShareholderArray[(f - 1)]) && (CustomerType == 'COMPANY')) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case customer company (ManagementShareholder)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/MAINManagementShareholderCompanyTC'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            'call test case customer personal (ManagementShareholder)'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/ManagementShareholderCompany/MAINManagementShareholderCompanyTC'), 
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
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                    
                    'Verify iscomplete == yes'
                    WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL)
                } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                    'click button action'
                    WebUI.click(modifynewButtonAction)

                    if (GlobalVariable.Role == 'Data Entry') {
                        'call test case guarantor company'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                            [:], FailureHandling.STOP_ON_FAILURE)
                    }
                }
            } else if (isComplete == 'YES') {
                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(
                    GlobalVariable.NumofColm, 8) == 'YES') {
                    'verify customername == guarantorarray'
                    if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'PERSONAL')) {
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case guarantor personal'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorPersonal/MAINGuarantorPersonalTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                        
                        'Verify iscomplete == yes'
                        WebUI.verifyMatch(isComplete, 'YES', false, FailureHandling.OPTIONAL)
                    } else if (CustomerName.equalsIgnoreCase(GuarantorArray[(g - 1)]) && (CustomerType == 'COMPANY')) {
                        'click button action'
                        WebUI.click(modifynewButtonAction)

                        if (GlobalVariable.Role == 'Data Entry') {
                            'call test case guarantor company'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        } else {
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/GuarantorCompany/MAINGuarantorCompanyTC'), 
                                [:], FailureHandling.STOP_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
    
    'Verify iscomplete == yes'
    WebUI.verifyMatch(WebUI.getText(modifynewisComplete), 'YES', false, FailureHandling.OPTIONAL)
}

'click button submit'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Submit'))



String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/CustomerCompany/MAINCustomerCompanyTC'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

