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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_CF4W_Company.xlsx")

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathDupcheck'
excelPathDupcheck = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/DuplicateChecking'

'declare excelPathDupcheck'
excelPathCustomerCompany = 'NAP-' + GlobalVariable.LOB + '-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'get app no from data file'
String DupcheckAppNo = findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 12)

'declare subjectname variable'
String subjectName

'declare modify object variable'
def modifyButtonEdit

def modifyCustomerNo

def modifyApplicantNo

def modifySubjectType

'check dupcheck status'
Boolean DupCheckStatus = CustomKeywords.'dupCheck.dupCheckVerif.checkDupCheckStatus'(sqlconnectionLOS, DupcheckAppNo)

if (DupCheckStatus == true) {
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/a_Customer Duplicate Checking CF4W'))

	WebUI.delay(3)
	
    'call paging testing function'
    pagingTesting()

    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/input_Application No_AppNoId'), findTestData(excelPathDupcheck).getValue(
            GlobalVariable.NumofColm, 12))

    'Looping delay untuk handling copy app selama +- 2 menit'
    for (int i = 1; i <= 8; i++) {
        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Search'))

        'Pengecekan ada/tidak adanya button action pencil yang muncul'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            break
        } else {
            'delay 14 detik'
            WebUI.delay(14)
        }
    }
    
    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

    WebDriver driver = DriverFactory.getWebDriver()

    'count dupcheck row confins'
    ArrayList<String> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    'declare dupcheck row variable'
    GlobalVariable.CountDupcheckRow = variable.size()

    'array customer name data inputan'
    def CustomerNameArray = GlobalVariable.CustomerName.split(';')

    'get customer name dari datafile dupcheck'
    custnamedupcheck = findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 13)

    'negative count = 0'
    GlobalVariable.NegativeCustCount = 0

    for (GlobalVariable.Index = 1; GlobalVariable.Index <= GlobalVariable.CountDupcheckRow; (GlobalVariable.Index)++) {
        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[2]', 
            true)

        'get text subject name'
        subjectName = WebUI.getText(modifySubjectName)

        'check if role == testing & tidak edit NAP'
        if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 8).length() == 0)) {
            'verify name == data inputan'
            checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomerNameArray.contains(subjectName), true))
        }
    }
    
    'verify name == data inputan'
    if (custnamedupcheck.length() > 0) {
        'modify object subjecttype'
        modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', '//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[1]/td[3]', true)

        'modify object edit icon'
        modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
            'xpath', 'equals', '//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[1]/td[7]/span/span/span/span/span/span/a/i', 
            true)

        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', '//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[1]/td[2]', true)

        'modify object Applicant No'
        modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNo'), 
            'xpath', 'equals', '//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[1]/td[4]', true)

        'modify object Customer No'
        modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNo'), 
            'xpath', 'equals', '//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[1]/td[5]', true)

        'get text subject name'
        subjectName = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        'get text subject type'
        String subjectType = WebUI.getText(modifySubjectType, FailureHandling.OPTIONAL)

        if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8).length() == 
        0)) {
            'verify name == data inputan'
            checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomerNameArray.contains(subjectName), true))
        }
        
        if (subjectName.equalsIgnoreCase(custnamedupcheck)) {
            'click button edit'
            if (WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                'if role == testing'
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative'
                    if (GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount] == 'NEGATIVE') {
                        ArrayList<String> variablenegcustno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecNegList > table > tbody tr'))

                        if (findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 15).equalsIgnoreCase('Yes')) {
                            def modifycheckbox

							'looping negative list untuk mencari negative cust no karena hanya yang memiliki negative cust no yang bisa di select'
                            for (int id = 1; id <= variablenegcustno.size(); id++) {
                                'modify negative cust no'
                                modifyNegativeCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                    'xpath', 'equals', ('//*[@id=\'subSecNegList\']/table/tbody/tr[' + id) + ']/td[1]', 
                                    true)

                                if (WebUI.getText(modifyNegativeCustNo) != '') {
                                    modifycheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativeCompany'), 
                                        'xpath', 'equals', ('//*[@id=\'subSecNegList\']/table/tbody/tr[' + id) + ']/td[6]/mat-checkbox/label/span[1]', 
                                        true)

                                    break
                                }
                            }
                            
                            if (modifycheckbox != null) {
                                'click negative checkbox index 1 yang ada negative cust no'
                                WebUI.click(modifycheckbox)
                            } else {
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativeCompany'))
                            }
                        }
                    }
                }
                
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    'count similar data row'
                    ArrayList<String> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                    for (int id = 1; id <= variableidno.size(); id++) {
                        'modify object id no customer match'
                        modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object Customer No'
                        modifyCustomerNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                        'get text custoner no value'
                        String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject, FailureHandling.OPTIONAL)

                        'get text id no customer match'
                        String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

                        'get text id no customer'
                        String IdNoCustomer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'), 
                            FailureHandling.OPTIONAL)

                        if (NewIdNoCustomerMatch != null) {
                            if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                'modify object id no customer match'
                                modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select match similar data'
                                WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                if (GlobalVariable.RoleCompany == 'Testing') {
                                    'verify match CustomerNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, 
                                            false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    'count application in process table'
                    ArrayList<String> applicationinprocessrow = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                    for (int id = 1; id <= applicationinprocessrow.size(); id++) {
                        'modify object id no customer match'
                        modifyIDNoCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object applicant No App in process'
                        modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                        'get applicant no value'
                        String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                        'get id no customer personal match'
                        String NewIdNoCustomerPersonalMatch = WebUI.getText(modifyIDNoCustomerPersonal, FailureHandling.OPTIONAL)

                        'get text id no cust personal'
                        String IdNoCustomerPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'), 
                            FailureHandling.OPTIONAL)

                        if (NewIdNoCustomerPersonalMatch != null) {
                            if (NewIdNoCustomerPersonalMatch.equalsIgnoreCase(IdNoCustomerPersonal)) {
                                'modify object select customer match'
                                modifyselectCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select'
                                WebUI.click(modifyselectCustomerPersonal, FailureHandling.OPTIONAL)

                                if (GlobalVariable.RoleCompany == 'Testing') {
                                    'verify match ApplicantNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), 
                                            newApplicantNoValue.toString(), false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
                if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    if (findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('New')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false))
                            } //Karena button action New tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', 
                                GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDupcheck)

                            KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newCustomervalue dari UI confins'
                            String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), 
                                        newCustomerNoValue, false))
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newApplicantNoValue dari UI confins'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select app in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        newApplicantNoValue, false))
                            }
                        }
                        //Karena button action similar data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        //Karena button action application in process tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                    } else if (findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select SimilarData')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newCustomervalue dari UI confins'
                            String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), 
                                        newCustomerNoValue, false))
                            }
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', 
                                GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDupcheck)

                            KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        '', false))
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newApplicantNovalue dari UI confins'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        newApplicantNoValue, false))
                            }
                        }
                    } else if (findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select ApplicationInProcess')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newApplcantNovalue dari UI confins'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInprocessCompany'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        newApplicantNoValue, false))
                            }
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', 
                                GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDupcheck)

                            KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        '', false))
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'gettext newCustomervalue dari UI confins'
                            String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), 
                                        newCustomerNoValue, false))
                            }
                        }
                    }
                }
            }
        }
        
        (GlobalVariable.NegativeCustCount)++
    }
    
    'call testcase dupcheck Guarantor'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingGuarantor'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

    'call testcase dupcheck Management Shareholder'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingMS'), [:], FailureHandling.CONTINUE_ON_FAILURE)

    'call test case get and write customer name'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/DuplicateChecking/CustomerDuplicateCheckingGetName'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

    'click button submit'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Submit'))

    if ((Integer.parseInt(findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 4)) == 0) && (GlobalVariable.FlagFailed == 
    0)) {
        'Check alert'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '4.DuplicateChecking')
    }
    
    if (GlobalVariable.FlagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(excelPathDupcheck).getValue(GlobalVariable.NumofColm, 
                    4)), findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/input_Application No_AppNoId'), GlobalVariable.NumofColm, 
            '4.DuplicateChecking')
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Back'), GlobalVariable.TimeOut, 
        FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_Back'))
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def pagingTesting() {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
		'declare appnoobject'
        Object appNoObject

		'declare result reset'
        ArrayList<String> resultReset = new ArrayList<String>()

		'declare check verify sort'
        ArrayList<String> checkVerifySort = new ArrayList<String>()

		'declare check verify footer'
        ArrayList<String> checkVerifyFooter = new ArrayList<String>()

        'Verif reset'
        resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Search'))

		'declare liststring'
        ArrayList<String> listString = new ArrayList<String>()

        'Inisialisasi driver'
        WebDriver driver = DriverFactory.getWebDriver()

        'Inisialisasi variabel'
        ArrayList<String> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'Klik header office'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_Office'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        'Klik header appno'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_appNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

		'looping untuk menyimpan appno ascending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno ascending'
        Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

		'verif sort appno ascending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        ArrayList<String> listApp = new ArrayList<String>()

        'Klik header appno'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_appNo'))

		'looping untuk menyimpan appno descending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listApp.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

		'verif sort appno descending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_custName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<String>()

		'looping untuk menyimpan custname ascending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

		'verif sort custname ascending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_custName'))

        listString = new ArrayList<String>()

		'looping untuk menyimpan custname descending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort custname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

		'verif sort custname descending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header product offering name'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_POName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut))

        listString = new ArrayList<String>()

		'looping untuk menyimpan po name ascending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

		'verif sort poname ascending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header product offering anme'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_POName'))

        listString = new ArrayList<String>()

		'looping untuk menyimpan po name descending'
        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

		'verif sort poname descending'
        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x header appno supaya appno descending'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_appNo'))

        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/span_appNo'))

        'Ambil nilai countdata dari confins'
        String[] textCountData = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/countData')).replace(
            ' ', '').replace(':', ';').split(';')

		'parsing countdata ke integer'
        Integer countDt = Integer.parseInt(textCountData[1])

        'Jika countdata secara keseluruhan lebih besar daripada data pada page 1'
        if (countDt > rowData.size()) {
            'Klik page 2'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/nextPage'))

            'Verif page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/nextPage'), 
                    'aria-current', GlobalVariable.TimeOut))

            rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

            listString = new ArrayList<String>()

			'looping untuk menyimpan appno di page 2'
            for (int i = 1; i <= rowData.size(); i++) {
                appNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/appNo'), 
                    'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[4]/span', true)

                listString.add(WebUI.getText(appNoObject))
            }
            
            'Verif appno descending pada page 2 tidak ada di page 1'
            Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

			'Verif appno descending pada page 2 tidak ada di page 1'
            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button prev'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Prev'))

            'Verify page 1 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/pageOne'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<String>()
			
			'keyword yang berisi looping untuk menyimpan appno di page 1'
            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingDupcheck'(listString)

            'Verif appno yang ada di page 1 tidak ada di page 2'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

			'Verif appno yang ada di page 1 tidak ada di page 2'
            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button next'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Next'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/nextPage'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<String>()

			'keyword yang berisi looping untuk menyimpan appno di page 2'
            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingDupcheck'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

			'Verif appno yang ada di page 2 tidak ada di page 1'
            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
        }
        
        'Klik button page 1'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/pageOne'))

		'verif jumlah data yang muncul pada paging sesuai'
        checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.DupcheckCountDataInPage'(), true))

		'Pengecekan jika pada verif reset ada yang tidak sesuai'
        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {

			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedFooter + 'Dupcheck') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
		'Pengecekan jika pada verif sort ada yang tidak sesuai'
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {

			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace(
                    '-', '') + ((GlobalVariable.ReasonFailedSort + 'Dupcheck') + ';\n'))
			
            GlobalVariable.FlagWarning = 1
        }
        
		'Pengecekan jika pada verif footer ada yang tidak sesuai'
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
            1))) {
						
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedFooter + 'Dupcheck') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
    }
}