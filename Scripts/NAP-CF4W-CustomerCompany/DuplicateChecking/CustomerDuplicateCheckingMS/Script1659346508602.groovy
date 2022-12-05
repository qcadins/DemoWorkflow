import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

'declare datafileDupcheck'
datafileDupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

'get MS Array'
def ManagementShareholderArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

'get MS Action array'
def ManagementShareholderActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

'get MS Negative Action Array'
def ManagementShareholderNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

'get appno dari datafile dupcheck'
String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'delcare web driver'
WebDriver driver = DriverFactory.getWebDriver()

'array customer name data inputan'
def CustomerNameArray = GlobalVariable.CustomerName.split(';')

'declare modify object variable'
def modifyButtonEdit, modifyCustomerNo, modifyApplicantNo, modifySubjectType

'declare variable untuk table dupcheck'
String subjectName, newApplicantNoValue, newMSNameAppInProcess, newMSName, newCustomerNoValue

'check if MS Array > 0'
if (ManagementShareholderArray.size() > 0) {
    for (int m = 1; m <= ManagementShareholderArray.size(); m++) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
            5, FailureHandling.OPTIONAL)) {
            'define interger i'
            int i = 0

            for (GlobalVariable.Index = 1; GlobalVariable.Index <= GlobalVariable.CountDupcheckRow; (GlobalVariable.Index)++) {
                'modify object subjecttype'
                modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[3]', true)

                'modify object edit icon'
                modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[7]/span/span/span/span/span/span/a', true)

                'verify subject type dan button edit ada'
                if ((WebUI.getText(modifySubjectType) == 'SHARE HOLDER') && WebUI.verifyElementPresent(modifyButtonEdit, 
                    5, FailureHandling.OPTIONAL)) {
                    break
                } else {
                    i++

                    'verify i == total row dupcheck'
                    if (i == GlobalVariable.CountDupcheckRow) {
                        'set GV index = 1'
                        GlobalVariable.Index = 1

                        'set i = 0'
                        i = 0

                        break
                    }
                }
            }
            
            'modify object subjectname'
            modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[2]', true)

            'get text subject name'
            subjectName = WebUI.getText(modifySubjectName)

            'get text subject type'
            subjectType = WebUI.getText(modifySubjectType)

			'check if role == testing & tidak edit NAP'
            if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 8).length() == 0)) {
                'verify name == data inputan'
                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyEqual(CustomerNameArray.contains(subjectName), true), '4.DuplicateChecking', GlobalVariable.NumofColm)
            }
        }
        
		'verif if subjectname == MSArray'
        if (subjectName.equalsIgnoreCase(ManagementShareholderArray[(m - 1)])) {
            if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                'click button edit'
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                'if role == testing'
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative'
                    if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                    GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                    'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && 
                    WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                        5, FailureHandling.OPTIONAL))) {
                        if ((ManagementShareholderNegativeArray[(m - 1)]).equalsIgnoreCase('Yes')) {
                            'click negative checkbox index 1'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/checkbox negative'))
                        }
                    }
                }
                
				'count table application in process'
                ArrayList<WebElement> variabletd = driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

                int counttd = variabletd.size()

                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                    if (counttd == 10) {
                        for (int id = 1; id <= variableidno.size(); id++) {
                            'modify object id no managementsharholder match'
                            modifyIDNoManagementShareholder = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object ManagementShareholder No'
                            modifyManagementShareholderNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object MS Name'
                            modifyMSNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text MS no value'
                            String newManagementShareholderNoValue = WebUI.getText(modifyManagementShareholderNoObject)

                            'get text new id no MS'
                            String NewIdNoManagementShareholderMatch = WebUI.getText(modifyIDNoManagementShareholder, FailureHandling.OPTIONAL)

                            'get text id no MS'
                            String IdNoManagementShareholder = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            'get text MS name'
                            newMSName = WebUI.getText(modifyMSNameObject)

                            'check if id No MS not match'
                            if (NewIdNoManagementShareholderMatch != null) {
                                if (NewIdNoManagementShareholderMatch.equalsIgnoreCase(IdNoManagementShareholder)) {
                                    'modify object id no family match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ManagementShareholderNo'
                                        CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectCustNo(newMSName), newManagementShareholderNoValue, 
                                                false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                                    }
                                    
                                    break
                                }
                            }
                        }
                    } else {
                        for (int id = 1; id <= variableidno.size(); id++) {
                            'modify object id no managementsharholder match'
                            modifyIDNoManagementShareholder = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object ManagementShareholder No'
                            modifyManagementShareholderNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object MS Name'
                            modifyMSNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text MS no value'
                            String newManagementShareholderNoValue = WebUI.getText(modifyManagementShareholderNoObject)

                            'get text new id no MS'
                            String NewIdNoManagementShareholderMatch = WebUI.getText(modifyIDNoManagementShareholder, FailureHandling.OPTIONAL)

                            'get text id no MS'
                            String IdNoManagementShareholder = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            'get text MS name'
                            newMSName = WebUI.getText(modifyMSNameObject)

                            'check if id No MS not match'
                            if (NewIdNoManagementShareholderMatch != null) {
                                if (NewIdNoManagementShareholderMatch.equalsIgnoreCase(IdNoManagementShareholder)) {
                                    'modify object id no family match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ManagementShareholderNo'
                                        CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectCustNo(newMSName), newManagementShareholderNoValue, 
                                                false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                                    }
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableManagementShareholderPersonalidno = driver.findElements(By.cssSelector(
                            '#subSecAppProcess > table > tbody tr'))

                    if (counttd == 10) {
                        for (int id = 1; id <= variableManagementShareholderPersonalidno.size(); id++) {
                            'modify object id no ManagementShareholder match'
                            modifyIDNoManagementShareholderPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object applicant Name App in process'
                            modifyMSNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get data new applicant value'
                            newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            'get text new id no'
                            String NewIdNoManagementShareholderPersonalMatch = WebUI.getText(modifyIDNoManagementShareholderPersonal, 
                                FailureHandling.OPTIONAL)

                            'get text MS shareholder ID No'
                            String IdNoManagementShareholderPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            'get text MS name'
                            newMSNameAppInProcess = WebUI.getText(modifyMSNameAppInProcess)

							'chcek if NewIdNoManagementShareholderPersonalMatch not null'
                            if (NewIdNoManagementShareholderPersonalMatch != null) {
								'chcek if NewIdNoManagementShareholderPersonalMatch == IdNoManagementShareholderPersonal'
                                if (NewIdNoManagementShareholderPersonalMatch.equalsIgnoreCase(IdNoManagementShareholderPersonal)) {
                                    'modify object select managementshareholder match'
                                    modifyselectManagementShareholderPersonal = WebUI.modifyObjectProperty(findTestObject(
                                            'NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select'
                                    WebUI.click(modifyselectManagementShareholderPersonal, FailureHandling.OPTIONAL)

									'check if role == testing'
                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ApplicantNo'
                                        CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectApplicantNo(newMSNameAppInProcess).toString(), 
                                                newApplicantNoValue.toString(), false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                                    }
                                    
                                    break
                                }
                            }
                        }
                    } else {
                        for (int id = 1; id <= variableManagementShareholderPersonalidno.size(); id++) {
                            'modify object id no ManagementShareholder match'
                            modifyIDNoManagementShareholderCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCompany'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object applicant Name App in process'
                            modifyMSNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text applicant no value'
                            newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            'get text id no MS company'
                            String NewIdNoManagementShareholderCompanyMatch = WebUI.getText(modifyIDNoManagementShareholderCompany, 
                                FailureHandling.OPTIONAL)

                            'get text id no MS'
                            String IdNoManagementShareholderCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            'get text MS name'
                            newMSName = WebUI.getText(modifyMSNameAppInProcess)

                            'check if NewIdNoManagementShareholderCompanyMatch not null'
                            if (NewIdNoManagementShareholderCompanyMatch != null) {
								'check if NewIdNoManagementShareholderCompanyMatch == IdNoManagementShareholderCompany'
                                if (NewIdNoManagementShareholderCompanyMatch.equalsIgnoreCase(IdNoManagementShareholderCompany)) {
                                    'modify object select managementshareholder match'
                                    modifyselectManagementShareholderCompany = WebUI.modifyObjectProperty(findTestObject(
                                            'NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select application in process'
                                    WebUI.click(modifyselectManagementShareholderCompany, FailureHandling.OPTIONAL)

									'check if role = testing'
                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ApplicantNo'
                                        CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectApplicantNo(newMSName).toString(), newApplicantNoValue.toString(), 
                                                false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                                    }
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
                if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                    5, FailureHandling.OPTIONAL)) {
                    if ((ManagementShareholderActionArray[(m - 1)]).equalsIgnoreCase('New')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

							'check if role = testing'
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get MS name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else if (counttd == 5) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get MS name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectCustNo(newMSName).toString(), newCustomerNoValue.toString(), 
                                        false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get text applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else if (counttd == 5) {
                                'get text applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select applcation in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectApplicantNo(newMSNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        }
                    } else if ((ManagementShareholderActionArray[(m - 1)]).equalsIgnoreCase('Select SimilarData')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get text new customer no'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get text MS name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else if (counttd == 5) {
                                'get text new customer no'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get text MS name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectCustNo(newMSName).toString(), newCustomerNoValue.toString(), 
                                        false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else if (counttd == 5) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectApplicantNo(newMSNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        }
                    } else if ((ManagementShareholderActionArray[(m - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else if (counttd == 5) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new MS name'
                                newMSNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectApplicantNo(newMSNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new customer name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else if (counttd == 5) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new customer name'
                                newMSName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                CustomKeywords.'Function.checkVerifyEqualOrMatch'(WebUI.verifyMatch(loopingSubjectCustNo(newMSName).toString(), newCustomerNoValue.toString(), 
                                        false), '4.DuplicateChecking', GlobalVariable.NumofColm)
                            }
                        }
                    }
                }
            }
        }
        
        (GlobalVariable.NegativeCustCount)++

        if (m == ManagementShareholderArray.size()) {
            break
        }
    }
}

def loopingSubjectApplicantNo(String newMSNameAppInProcess) {
    String applicantNo

    Object modifyAppliNo

    for (int z = 1; z <= GlobalVariable.CountDupcheckRow; z++) {
        'modify object subjecttype'
        Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[3]', true)

        'modify object subject name'
        Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[2]', true)

        'verify subject type dan button edit ada'
        if ((WebUI.getText(modifySubjectType) == 'SHARE HOLDER') && WebUI.verifyMatch(newMSNameAppInProcess, WebUI.getText(
                modifySubjectName), false, FailureHandling.OPTIONAL)) {
            'modify object applicant no'
            modifyAppliNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[4]', true)

            break
        }
    }
    
    applicantNo = WebUI.getText(modifyAppliNo)

    return applicantNo
}

def loopingSubjectCustNo(String newMSName) {
    String custNo

    Object modifyCustNo

    for (int z = 1; z <= GlobalVariable.CountDupcheckRow; z++) {
        'modify object subjecttype'
        Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[3]', true)

        'modify object subject name'
        Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[2]', true)

        'verify subject type dan button edit ada'
        if ((WebUI.getText(modifySubjectType) == 'SHARE HOLDER') && WebUI.verifyMatch(newMSName, WebUI.getText(modifySubjectName), 
            false, FailureHandling.OPTIONAL)) {
            'modify object custno'
            modifyCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[5]', true)

            break
        }
    }
    
    custNo = WebUI.getText(modifyCustNo)

    return custNo
}