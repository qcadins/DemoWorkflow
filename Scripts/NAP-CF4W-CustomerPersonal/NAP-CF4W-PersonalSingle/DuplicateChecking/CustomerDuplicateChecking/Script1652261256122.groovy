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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

String CDCCustomerPersonal = userDir + GlobalVariable.DataFileCustomerPersonal

String CDCFamilyPath = userDir + GlobalVariable.DataFileFamilyPersonal

String CDCGuarantorPersonalPath = userDir + GlobalVariable.DataFileGuarantorPersonal

String CDCGuarantorCompanyPath = userDir + GlobalVariable.DataFileGuarantorCompany

GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String DupcheckAppNo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
    GlobalVariable.NumofColm, 2)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.DupCheckVerif.checkDupcheck'(sqlconnection, DupcheckAppNo)

if (Integer.parseInt(DupCheckCount) == 1) {
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/a_Customer Duplicate Checking'))

    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/input_Application No_AppNoId'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(GlobalVariable.NumofColm, 
            2))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Search'))

    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

    WebDriver driver = DriverFactory.getWebDriver()

    ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    int count = variable.size()

    def StoreCDCCustomerName = '' 

	def StoreCDCFamilyName = ''
	
	def StoreCDCGuarantorPersonalName = ''
	
	def StoreCDCGuarantorCompanyName = ''
	
	'verify equal number of customer'
	WebUI.verifyEqual(GlobalVariable.countNumofCustomer, count, FailureHandling.OPTIONAL)
	
    for (int i = 1; i <= count; i++) {
       
        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[2]'), true)

        'modify object subjecttype'
        modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[3]'), true)

        'modify object Applicant No'
        modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNo'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[4]'), true)

        'modify object Customer No'
        modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNo'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[5]'), true)

        'modify object edit icon'
        modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i + ']/td[7]/span/span/span/span/span/span/a'), true)

        'get text subject name'
        String subjectName = WebUI.getText(modifySubjectName)

        'get text subject type'
        String subjectType = WebUI.getText(modifySubjectType)

        def CustomerArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
            GlobalVariable.NumofColm, 3).split(';')

        def FamilyArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
            GlobalVariable.NumofColm, 5).split(';')

        def GuarantorArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
            GlobalVariable.NumofColm, 7).split(';')

        def FamilyActionArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
            GlobalVariable.NumofColm, 6).split(';')

        def GuarantorActionArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
            GlobalVariable.NumofColm, 8).split(';')
			
		'array customer name data inputan'
		def CustomerNameArray = GlobalVariable.CustomerName.split(';')
		
		'verify name == data inputan'
		WebUI.verifyMatch(subjectName, CustomerNameArray[i-1], false)

        if (CustomerArray.size() > 0) {
            for (c = 1; c <= CustomerArray.size(); c++) {
                if (subjectName.equalsIgnoreCase(CustomerArray[(c - 1)])) {
                    'click button edit'
                    if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                        int countidnorow = variableidno.size()

                        for (id = 1; id <= countidnorow; id++) {
							'modify object id no customer match'
                            modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id + ']/td[4]'), true)

                            

                            'modify object Customer No'
                            modifyCustomerNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id + ']/td[1]'), true)

                            String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject)

                            String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

                            String IdNoCustomer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoCustomerMatch != null) {
                                if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                    

                                    'modify object id no customer match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id + ']/td[10]/a'), true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)

                                    'get customer name'
                                    String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

                                    'store customer name'
                                    StoreCDCCustomerName = name

                                    break
                                }
                            }
                        }
                        
                        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 
                            5, FailureHandling.OPTIONAL)) {
                            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
                                GlobalVariable.NumofColm, 4).equalsIgnoreCase('New')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                        FailureHandling.OPTIONAL)

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                        FailureHandling.OPTIONAL)

                                    'click select app in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                                }
                            } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
                                GlobalVariable.NumofColm, 4).equalsIgnoreCase('Select SimilarData')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                        FailureHandling.OPTIONAL)

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                        FailureHandling.OPTIONAL)

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                                }
                            } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking').getValue(
                                GlobalVariable.NumofColm, 4).equalsIgnoreCase('Select ApplicationInProcess')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                        FailureHandling.OPTIONAL)

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newCustomerNoValue = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                        FailureHandling.OPTIONAL)

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false, FailureHandling.OPTIONAL)
                                }
                            }
                        }
                    }else{
                        
                        'get customer name'
                        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
                        		
                        		'store customer name'
                        		StoreCDCCustomerName = name
                        }
                }
            }
        }
        
        if (FamilyArray.size() > 0) {
            for (f = 1; f <= FamilyArray.size(); f++) {
                if (subjectName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
                    if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                        'click button edit'
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variablefamilyidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        int countfamilyidrow = variablefamilyidno.size()

                        for (id = 1; id <= countfamilyidrow; id++) {
                           'modify object id no family match'
                            modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id + ']/td[4]'), true)

                            
                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id + ']/td[1]'), true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

                            String IdNoFamily = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoFamilyMatch != null) {
                                if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
                                    String newselectFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a'

                                    'modify object id no family match'
                                    modifyselectFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', newselectFamily, true)

                                    'click select application in process'
                                    WebUI.click(modifyselectFamily, FailureHandling.OPTIONAL)

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)

                                    'get family name'
                                    String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

                                    if (f == 1) {
                                        'store family name'
                                        StoreCDCFamilyName = name
                                    } else {
                                        'store family name'
                                        StoreCDCFamilyName = ((StoreCDCFamilyName + ';') + name)
                                    }
                                    
                                    break
                                }
                            }
                        }
                        
                        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 
                            5, FailureHandling.OPTIONAL)) {
                            if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('New')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                }
                            } else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                }
                            } else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select SimilarData')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), CustomerNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
                                } 
                            }
                            
                            'get family name'
                            String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

                            if (f == 1) {
                                'store family name'
                                StoreCDCFamilyName = name
                            } else {
                                'store family name'
                                StoreCDCFamilyName = ((StoreCDCFamilyName + ';') + name)
                            }
                        }
                    } else {
                        'get family name'
                        String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

                        if (f == 1) {
                            'store family name'
                            StoreCDCFamilyName = name
                        } else {
                            'store family name'
                            StoreCDCFamilyName = ((StoreCDCFamilyName + ';') + name)
                        }
                    }
                }
            }
        }
        
        if (GuarantorArray.size() > 0) {
            for (g = 1; g <= GuarantorArray.size(); g++) {
                if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) {
                    if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                        'click button edit'
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variableGuarantorPersonalidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        int countGuarantorPersonalidrow = variableGuarantorPersonalidno.size()

                        ArrayList<WebElement> variabletd = driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

                        int counttd = variabletd.size()

                        if (counttd == 10) {
                            for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                                String newIDNoGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[4]'

                                'modify object id no guarantor match'
                                modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoPersonal'), 
                                    'xpath', 'equals', newIDNoGuarantorPersonal, true)

                                String newApplicantNoObject = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]'

                                'modify object applicant No App in process'
                                modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    'xpath', 'equals', newApplicantNoObject, true)

                                String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                                String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal, FailureHandling.OPTIONAL)

                                String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                                if (NewIdNoGuarantorPersonalMatch != null) {
                                    if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) {
                                        String newselectGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + 
                                        id) + ']/td[10]/a'

                                        'modify object select family match'
                                        modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                            'xpath', 'equals', newselectGuarantorPersonal, true)

                                        'click selct'
                                        WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

                                        'verify match ApplicantNo'
                                        WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                            false, FailureHandling.OPTIONAL)

										'get guarantor name'
										String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
				
										if (StoreCDCGuarantorPersonalName == '') {
											'store guarantor name'
											StoreCDCGuarantorPersonalName = name
										} else {
											'store guarantor name'
											StoreCDCGuarantorPersonalName = (StoreCDCGuarantorPersonalName + ';' + name)
										}
                                        break
                                    }
                                }
                            }
                        } else if (counttd == 5) {
                            for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                                String newIDNoGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[3]'

                                'modify object id no Guarantor match'
                                modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoCompany'), 
                                    'xpath', 'equals', newIDNoGuarantorCompany, true)

                                String newApplicantNoAppInproccess = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[1]'

                                'modify object applicant No App in process'
                                modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    'xpath', 'equals', newApplicantNoAppInproccess, true)

                                String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                                String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany, FailureHandling.OPTIONAL)

                                String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                                if (NewIdNoGuarantorCompanyMatch != null) {
                                    if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) {
                                        String newselectGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + 
                                        id) + ']/td[5]/a'

                                        'modify object select family match'
                                        modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                            'xpath', 'equals', newselectGuarantorCompany, true)

                                        'click select application in process'
                                        WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

                                        'verify match ApplicantNo'
                                        WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                            false, FailureHandling.OPTIONAL)

										'get guarantor name'
										String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
				
										if (StoreCDCGuarantorCompanyName == '') {
											'store guarantor name'
											StoreCDCGuarantorCompanyName = name
										} else {
											'store guarantor name'
											StoreCDCGuarantorCompanyName = (StoreCDCGuarantorCompanyName + ';' + name)
										}
                                        break
                                    }
                                }
                            }
                        }
                        
                        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 
                            5, FailureHandling.OPTIONAL)) {
                            if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
								
								'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
																	
                                }else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                     String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
								
                                }
                                }else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select applcation in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                }
                                }
                            } else if((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')){
                                if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                     String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                }
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    if(counttd == 10){
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
									
									
								}else if(counttd == 5){
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
								
								
								}
                                } else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                }
                            } else if((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select ApplicationInProcess')){
                                 if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                    String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                    'click select application in process'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                }
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'), 
                                    5, FailureHandling.OPTIONAL)) {
								if(counttd == 10){
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

                                    'verify match ApplicantNo'
                                    WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
									
									
								}else if(counttd == 5){
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
								
								
								}
                                } else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                                    5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    5, FailureHandling.OPTIONAL)){
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                } else if (counttd == 5) {
                                     String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                    'click select match similar data'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                    'verify match CustomerNo'
                                    WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(), 
                                        false, FailureHandling.OPTIONAL)
									
									
                                }
                                }
                            }                  
                                'get guarantor name'
                                String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
							if(counttd == 5){
	
							if (StoreCDCGuarantorCompanyName == '') {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = name
							} else {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = (StoreCDCGuarantorCompanyName + ';' + name)
							}
							}else if(counttd == 10){
							
	
							if (StoreCDCGuarantorPersonalName == '') {
								'store guarantor name'
								StoreCDCGuarantorPersonalName = name
							} else {
								'store guarantor name'
								StoreCDCGuarantorPersonalName = (StoreCDCGuarantorPersonalName + ';' + name)
							}
							}
                        }
                    }
                }else{
                	'get guarantor name'
                	String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
				
				String GuarantorType =  CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnection, DupcheckAppNo, name)
				
			if(GuarantorType.equalsIgnoreCase('COMPANY')){

			if (StoreCDCGuarantorCompanyName == '') {
				'store guarantor name'
				StoreCDCGuarantorCompanyName = name
			} else {
				'store guarantor name'
				StoreCDCGuarantorCompanyName = (StoreCDCGuarantorCompanyName + ';' + name)
			}
			}else if(GuarantorType.equalsIgnoreCase('PERSONAL')){
			

			if (StoreCDCGuarantorPersonalName == '') {
				'store guarantor name'
				StoreCDCGuarantorPersonalName = name
			} else {
				'store guarantor name'
				StoreCDCGuarantorPersonalName = (StoreCDCGuarantorPersonalName + ';' + name)
			}
			}
                }
            }
        }
    }
    }

    if (StoreCDCCustomerName != null) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            2, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
    }
    
    if (StoreCDCFamilyName != null) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            4, GlobalVariable.NumofColm - 1, StoreCDCFamilyName)
    }
    
    if (StoreCDCGuarantorPersonalName != null || StoreCDCGuarantorCompanyName != null) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '15.CustomerDataCompletion', 
            6, GlobalVariable.NumofColm - 1, StoreCDCGuarantorPersonalName + ';' + StoreCDCGuarantorCompanyName)
    }
	
	 StoreCDCFamilyNameArray = StoreCDCFamilyName.split(';')
	 StoreCDCGuarantorPersonalNameArray = StoreCDCGuarantorPersonalName.split(';')
	 StoreCDCGuarantorCompanyNameArray = StoreCDCGuarantorCompanyName.split(';')
	 
	 CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCCustomerPersonal, '1.CustomerDetail',
		 2, GlobalVariable.NumofColm - 1, StoreCDCCustomerName)
	
	for(FamilyName = 1; FamilyName <= StoreCDCFamilyNameArray.size(); FamilyName++){
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCFamilyPath, '1.CustomerDetail',
			2, FamilyName, StoreCDCFamilyNameArray[FamilyName - 1])
	}
	
	for(GuarantorName = 1; GuarantorName <= StoreCDCGuarantorPersonalNameArray.size(); GuarantorName++){
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorPersonalPath, '1.CustomerDetail',
			2, GuarantorName, StoreCDCGuarantorPersonalNameArray[GuarantorName - 1])
	}
	
	for(GuarantorName = 1; GuarantorName <= StoreCDCGuarantorCompanyNameArray.size(); GuarantorName++){
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(CDCGuarantorCompanyPath, '1.CustomerDetail',
			2, GuarantorName, StoreCDCGuarantorCompanyNameArray[GuarantorName - 1])
	}
    
    'click button submit'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Submit'))

    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Back'), 
        10, FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
    }
    
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}