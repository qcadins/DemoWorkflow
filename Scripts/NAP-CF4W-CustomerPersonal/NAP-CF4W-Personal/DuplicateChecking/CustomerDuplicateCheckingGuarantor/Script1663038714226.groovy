import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

def GuarantorArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).split(';', -1)

def GuarantorActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 20).split(';', -1)

def GuarantorNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 21).split(';', -1)

def modifySubjectName

def modifySubjectType

def modifyApplicantNo

def modifyCustomerNo

def modifyButtonEdit

String subjectName

String subjectType

if (GuarantorArray.size() > 0) {
    for (g = 1; g <= GuarantorArray.size(); g++) {
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
                if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
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
        
        if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) {
            if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                'click button edit'
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                'if role == testing'
                if (GlobalVariable.Role == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative'
                    if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                    GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                    'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && 
                    WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                        5, FailureHandling.OPTIONAL))) {
                        if ((GuarantorNegativeArray[(g - 1)]).equalsIgnoreCase('Yes')) {
                            'click negative checkbox index 1'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/checkbox negative'))
                        }
                    }
                }
                
                ArrayList<WebElement> variabletd = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

                int counttd = variabletd.size()

                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata')), 
                    'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                    int countidnorow = variableidno.size()

                    for (id = 1; id <= countidnorow; id++) {
                        if (counttd == 10) {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object Guarantor No'
                            modifyGuarantorNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    'verify match GuarantorNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newGuarantorNoValue, false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        } else {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object Guarantor No'
                            modifyGuarantorNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    'verify match GuarantorNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newGuarantorNoValue, false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    }
                }
                
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess')), 
                    'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableGuarantorPersonalidno = DriverFactory.getWebDriver().findElements(By.cssSelector(
                            '#subSecAppProcess > table > tbody tr'))

                    int countGuarantorPersonalidrow = variableGuarantorPersonalidno.size()

                    if (counttd == 10) {
                        for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                            'modify object id no guarantor match'
                            modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal, FailureHandling.OPTIONAL)

                            String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoGuarantorPersonalMatch != null) {
                                if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click selct'
                                    WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    } else {
                        for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCompany'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany, FailureHandling.OPTIONAL)

                            String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoGuarantorCompanyMatch != null) {
                                if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select application in process'
                                    WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                    }
                }
                
                if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                    5, FailureHandling.OPTIONAL)) {
                    if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            'verify match ApplicantNo'
                            WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select applcation in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        }
                    } else if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            if (counttd == 10) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        }
                    } else {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                'verify match ApplicantNo'
                                WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            if (counttd == 10) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            } else {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            } else {
                                String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                'verify match CustomerNo'
                                WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                    false, FailureHandling.OPTIONAL)
                            }
                        }
                    }
                }
            }
        }
        
        if (g == GuarantorArray.size()) {
            break
        }
        
        '+ index negative customer count'
        (GlobalVariable.NegativeCustCount)++
    }
}
