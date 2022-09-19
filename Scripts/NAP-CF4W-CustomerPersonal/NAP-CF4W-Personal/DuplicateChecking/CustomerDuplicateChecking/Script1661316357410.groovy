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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

String CDCCustomerPersonal = userDir + GlobalVariable.DataFileCustomerPersonal

String CDCFamilyPath = userDir + GlobalVariable.DataFileFamilyPersonal

String CDCGuarantorPersonalPath = userDir + GlobalVariable.DataFileGuarantorPersonal

String CDCGuarantorCompanyPath = userDir + GlobalVariable.DataFileGuarantorCompany

GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnection, DupcheckAppNo)

def modifySubjectName

def modifySubjectType

def modifyApplicantNo

def modifyCustomerNo

def modifyButtonEdit

String subjectName

String subjectType

int flagFailed=0

if (Integer.parseInt(DupCheckCount) == 1) {
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/a_Customer Duplicate Checking'))

    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_Application No_AppNoId'), 
        datafileDupcheck.getValue(GlobalVariable.NumofColm, 12))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Search'))

    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

    WebDriver driver = DriverFactory.getWebDriver()

    ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    GlobalVariable.countDupcheckRow = variable.size()

    //    WebUI.verifyEqual(GlobalVariable.countNumofCustomer, GlobalVariable.countDupcheckRow, FailureHandling.OPTIONAL)
    'verify equal number of customer'
    def CustomerArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

    def FamilyArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

    def FamilyActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

    def FamilyNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 18).split(';', -1)



    'array customer name data inputan'
    def CustomerNameArray = GlobalVariable.CustomerName.split(';')

    'declare variable count negative customer'
    GlobalVariable.NegativeCustCount = 0

    if (CustomerArray.size() > 0) {
        for (c = 1; c <= CustomerArray.size(); c++) {
            'define interger i'
            int i = 0

            for (GlobalVariable.index = 1; GlobalVariable.index <= GlobalVariable.countDupcheckRow; (GlobalVariable.index)++) {
                'modify object subjecttype'
                modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                    ']/td[3]', true)

                'modify object edit icon'
                modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                    ']/td[7]/span/span/span/span/span/span/a', true)

                'verify subject type dan button edit ada'
                if ((WebUI.getText(modifySubjectType) == 'CUSTOMER') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
                    FailureHandling.OPTIONAL)) {
                    break
                } else {
                    i++

                    'verify i == total row dupcheck'
                    if (i == GlobalVariable.countDupcheckRow) {
                        'set GV Index = 1'
                        GlobalVariable.index = 1

                        'set i = 0'
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
            subjectName = WebUI.getText(modifySubjectName)

            'get text subject type'
            subjectType = WebUI.getText(modifySubjectType)

            'verify name == data inputan'
            WebUI.verifyMatch(subjectName, CustomerNameArray[(i - 1)], false, FailureHandling.OPTIONAL)

            if (subjectName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                'click button edit'
                if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                    WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                    'if role == testing'
                    if (GlobalVariable.Role == 'Testing') {
                        'if dupcheck verif == review dan negative check == negative'
                        if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                        GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                        'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && WebUI.verifyElementPresent(
                            findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL))) {
                            if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 15).equalsIgnoreCase('Yes')) {
                                'click negative checkbox index 1'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/checkbox negative'))
                            }
                        }
                    }
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata')), 
                        'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                        ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                        int countidnorow = variableidno.size()

                        for (id = 1; id <= countidnorow; id++) {
                            'modify object id no customer match'
                            modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object Customer No'
                            modifyCustomerNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject)

                            String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

                            String IdNoCustomer = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoCustomerMatch != null) {
                                if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                    'modify object id no customer match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
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
                    }
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess')), 
                        'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                        ArrayList<WebElement> applicationinprocessrow = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        for (id = 1; id <= applicationinprocessrow.size(); id++) {
                            'modify object id no customer match'
                            modifyIDNoCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoCustomerPersonalMatch = WebUI.getText(modifyIDNoCustomerPersonal, FailureHandling.OPTIONAL)

                            String IdNoCustomerPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoCustomerPersonalMatch != null) {
                                if (NewIdNoCustomerPersonalMatch.equalsIgnoreCase(IdNoCustomerPersonal)) {
                                    'modify object select customer match'
                                    modifyselectCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click selct'
                                    WebUI.click(modifyselectCustomerPersonal, FailureHandling.OPTIONAL)

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    }
                    
                    if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/subjecttypeheader'), 
                        5, FailureHandling.OPTIONAL)) {
                        if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('New')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select app in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            }
                        } else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select SimilarData')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            }
                        } else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select ApplicationInProcess')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                            }
                        }
                    }
                }
            }
            
            '+ index negative customer count'
            GlobalVariable.NegativeCustCount++
        }
    }
    
    if (FamilyArray.size() > 0) {
        for (f = 1; f <= FamilyArray.size(); f++) {
            if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                5, FailureHandling.OPTIONAL)) {
                'define interger i'
                int i = 0

                for (GlobalVariable.index = 1; GlobalVariable.index <= GlobalVariable.countDupcheckRow; (GlobalVariable.index)++) {
                    'modify object subjecttype'
                    modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
                        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                        ']/td[3]', true)

                    'modify object edit icon'
                    modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                        'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + 
                        ']/td[7]/span/span/span/span/span/span/a', true)

                    'verify subject type dan button edit ada'
                    if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
                        FailureHandling.OPTIONAL)) {
                        break
                    } else {
                        i++

                        'verify i == total row dupcheck'
                        if (i == GlobalVariable.countDupcheckRow) {
                            'set GV Index = 1'
                            GlobalVariable.index = 1

                            'set i = 0'
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
                subjectName = WebUI.getText(modifySubjectName)

                'get text subject type'
                subjectType = WebUI.getText(modifySubjectType)
            }
            
            if (subjectName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
                if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                    'click button edit'
                    WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                    'if role == testing'
                    if (GlobalVariable.Role == 'Testing') {
                        'if dupcheck verif == review dan negative check == negative'
                        if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                        GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                        'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && WebUI.verifyElementPresent(
                            findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL))) {
                            if ((FamilyNegativeArray[(f - 1)]).equalsIgnoreCase('Yes')) {
                                'click negative checkbox index 1'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/checkbox negative'))
                            }
                        }
                    }
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata')), 
                        'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                        ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                        int countidnorow = variableidno.size()

                        for (id = 1; id <= countidnorow; id++) {
                            'modify object id no family match'
                            modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object Customer No'
                            modifyFamilyNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newFamilyNoValue = WebUI.getText(modifyFamilyNoObject)

                            String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

                            String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoFamilyMatch != null) {
                                if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
                                    'modify object id no family match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    'verify match FamilyNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newFamilyNoValue, false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    }
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess')), 
                        'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                        ArrayList<WebElement> variablefamilyidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        int countfamilyidrow = variablefamilyidno.size()

                        for (id = 1; id <= countfamilyidrow; id++) {
                            'modify object id no family match'
                            modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

                            String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoFamilyMatch != null) {
                                if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
                                    String newselectFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a'

                                    'modify object id no family match'
                                    modifyselectFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', newselectFamily, true)

                                    'click select application in process'
                                    WebUI.click(modifyselectFamily, FailureHandling.OPTIONAL)

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    }
                    
                    if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/subjecttypeheader'), 
                        5, FailureHandling.OPTIONAL)) {
                        if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('New')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        } else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        } else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select SimilarData')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        }
                    }
                }
            }
            
            if (f == FamilyArray.size()) {
                break
            }
            
            '+ index negative customer count'
            GlobalVariable.NegativeCustCount++
        }
    }
    
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/CustomerDuplicateCheckingGuarantor'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
    
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/CustomerDuplicateCheckingGetName'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	'click button submit'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Submit'))
	
	Integer iscompleteMandatory = Integer.parseInt(datafileDupcheck.getValue(GlobalVariable.NumofColm,
				4))
	
	if(iscompleteMandatory==0){
		'cek alert'
		flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '4.DuplicateChecking')
	}
	
	if(flagFailed==0){
		'Check save Process write to excel'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_Application No_AppNoId'),
			GlobalVariable.NumofColm, '4.DuplicateChecking')
	}
	
	
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Back'),
		10, FailureHandling.OPTIONAL)) {
		'click button back'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Back'))
	}
	
}


