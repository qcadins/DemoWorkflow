import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
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

datafiledupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

String CDCCustomerPersonal = userDir + GlobalVariable.DataFileCustomerCompany

String CDCManagementShareholderPersonalPath = userDir + GlobalVariable.DataFileManagementShareholderPersonal

String CDCManagementShareholderCompanyPath = userDir + GlobalVariable.DataFileManagementShareholderCompany

String CDCGuarantorPersonalPath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

String CDCGuarantorCompanyPath = userDir + GlobalVariable.DataFileGuarantorCompanyCompany

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String DupcheckAppNo = datafiledupcheck.getValue(GlobalVariable.NumofColm, 2)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnection, DupcheckAppNo)

if (Integer.parseInt(DupCheckCount) == 1) {
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/a_Customer Duplicate Checking'))

    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/input_Application No_AppNoId'), datafiledupcheck.getValue(
            GlobalVariable.NumofColm, 2))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Search'))

    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

    WebDriver driver = DriverFactory.getWebDriver()

    ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    GlobalVariable.countDupcheckRow = variable.size()

    def CustomerArray = datafiledupcheck.getValue(GlobalVariable.NumofColm, 3).split(';')

    'array customer name data inputan'
    def CustomerNameArray = GlobalVariable.CustomerName.split(';')

    //		WebUI.verifyMatch(subjectName, CustomerNameArray[i-1], false, FailureHandling.OPTIONAL)
    int i = 0

    'verify name == data inputan'
    if (CustomerArray.size() > 0) {
        for (c = 1; c <= CustomerArray.size(); c++) {
            for (GlobalVariable.index = 1; GlobalVariable.index <= GlobalVariable.countDupcheckRow; (GlobalVariable.index)++) {
                'modify object subjecttype'
                modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                    ']/td[3]', true)

                'modify object edit icon'
                modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                    ']/td[7]/span/span/span/span/span/span/a', true)

                if ((WebUI.getText(modifySubjectType) == 'CUSTOMER') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
                    FailureHandling.OPTIONAL)) {
                    GlobalVariable.index = GlobalVariable.index

                    break
                } else {
                    i++

                    if (i == GlobalVariable.countDupcheckRow) {
                        GlobalVariable.index = 1

                        i = 0

                        break
                    }
                }
            }
            
            'modify object subjectname'
            modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                ']/td[2]', true)

            'modify object subjecttype'
            modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                ']/td[3]', true)

            'modify object Applicant No'
            modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNo'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                ']/td[4]', true)

            'modify object Customer No'
            modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNo'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                ']/td[5]', true)

            'modify object edit icon'
            modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                ']/td[7]/span/span/span/span/span/span/a', true)

            'get text subject name'
            String subjectName = WebUI.getText(modifySubjectName)

            'get text subject type'
            String subjectType = WebUI.getText(modifySubjectType)

            if (subjectName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                'click button edit'
                if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                    WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                    ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                    int countidnorow = variableidno.size()

                    for (id = 1; id <= countidnorow; id++) {
                        'modify object id no customer match'
                        modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object Customer No'
                        modifyCustomerNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                        String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject)

                        String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

                        String IdNoCustomer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                        if (NewIdNoCustomerMatch != null) {
                            if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                'modify object id no customer match'
                                modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select match similar data'
                                WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)

                                break
                            }
                        }
                    }
                    
                    if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                        5, FailureHandling.OPTIONAL)) {
                        if (datafiledupcheck.getValue(GlobalVariable.NumofColm, 4).equalsIgnoreCase('New')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.RoleCompany == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select app in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            }
                        } else if (datafiledupcheck.getValue(GlobalVariable.NumofColm, 4).equalsIgnoreCase('Select SimilarData')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.RoleCompany == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            }
                        } else if (datafiledupcheck.getValue(GlobalVariable.NumofColm, 4).equalsIgnoreCase('Select ApplicationInProcess')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.RoleCompany == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            }
                        }
                    }
                }
            }
            
            if (c == CustomerArray.size()) {
                break
            }
        }
    }
    
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingMS'), [:], FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingGuarantor'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

    def StoreCDCCustomerName = ''

    def StoreCDCManagementShareholderPersonalName = ''

    def StoreCDCManagementShareholderCompanyName = ''

    def StoreCDCGuarantorPersonalName = ''

    def StoreCDCGuarantorCompanyName = ''

    for (index = 1; index <= GlobalVariable.countDupcheckRow; index++) {
        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[2]', true)

        'modify object subjecttype'
        modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + index) + ']/td[3]', true)

        if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Customer')) {
            'get customer name'
            String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

            'store customer name'
            StoreCDCCustomerName = name
        } else if (WebUI.getText(modifySubjectType).equalsIgnoreCase('Share Holder')) {
            'get ManagementShareholder name'
            String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

            String ManagementShareholderType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnection, 
                DupcheckAppNo, name)

            if (ManagementShareholderType.equalsIgnoreCase('COMPANY')) {
                if (StoreCDCManagementShareholderCompanyName == '') {
                    'store ManagementShareholder name'
                    StoreCDCManagementShareholderCompanyName = name
                } else {
                    'store ManagementShareholder name'
                    StoreCDCManagementShareholderCompanyName = ((StoreCDCManagementShareholderCompanyName + ';') + name)
                }
            } else if (ManagementShareholderType.equalsIgnoreCase('PERSONAL')) {
                if (StoreCDCManagementShareholderPersonalName == '') {
                    'store ManagementShareholder name'
                    StoreCDCManagementShareholderPersonalName = name
                } else {
                    'store ManagementShareholder name'
                    StoreCDCManagementShareholderPersonalName = ((StoreCDCManagementShareholderPersonalName + ';') + name)
                }
            }
        } else {
            'get guarantor name'
            String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

            String GuarantorType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnection, DupcheckAppNo, 
                name)

            if (GuarantorType.equalsIgnoreCase('COMPANY')) {
                if (StoreCDCGuarantorCompanyName == '') {
                    'store guarantor name'
                    StoreCDCGuarantorCompanyName = name
                } else {
                    'store guarantor name'
                    StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
                }
            } else {
                if (StoreCDCGuarantorPersonalName == '') {
                    'store guarantor name'
                    StoreCDCGuarantorPersonalName = name
                } else {
                    'store guarantor name'
                    StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
                }
            }
        }
    }
    
    if (StoreCDCCustomerName != null) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            2, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
    }
    
    if (StoreCDCManagementShareholderPersonalName != null) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            4, GlobalVariable.NumofColm - 1, (StoreCDCManagementShareholderPersonalName + ';') + StoreCDCManagementShareholderCompanyName)
    }
    
    if ((StoreCDCGuarantorPersonalName != null) || (StoreCDCGuarantorCompanyName != null)) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            6, GlobalVariable.NumofColm - 1, (StoreCDCGuarantorPersonalName + ';') + StoreCDCGuarantorCompanyName)
    }
    
    StoreCDCManagementShareholderPersonalNameArray = StoreCDCManagementShareholderPersonalName.split(';')

    StoreCDCManagementShareholderCompanyNameArray = StoreCDCManagementShareholderCompanyName.split(';')

    StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')

    StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCCustomerPersonal, '1.CustomerDetail', 2, GlobalVariable.NumofColm - 
        1, StoreCDCCustomerName)

    for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderPersonalNameArray.size(); ManagementShareholderName++) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderPersonalPath, '1.CustomerDetail', 
            2, ManagementShareholderName, StoreCDCManagementShareholderPersonalNameArray[(ManagementShareholderName - 1)])
    }
    
    for (ManagementShareholderName = 1; ManagementShareholderName <= StoreCDCManagementShareholderCompanyNameArray.size(); ManagementShareholderName++) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCManagementShareholderCompanyPath, '1.CustomerDetail', 
            2, ManagementShareholderName, StoreCDCManagementShareholderCompanyNameArray[(ManagementShareholderName - 1)])
    }
    
    for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail', 2, 
            GuarantorName, StoreCDCGuarantorPersonalNameArray[(GuarantorName - 1)])
    }
    
    for (GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail', 2, 
            GuarantorName, StoreCDCGuarantorCompanyNameArray[(GuarantorName - 1)])
    }
}

'click button submit'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Submit'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Back'), 10, FailureHandling.OPTIONAL)) {
	'click button back'
	
	'write to excel if failed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
}

'write to excel if success'
CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

