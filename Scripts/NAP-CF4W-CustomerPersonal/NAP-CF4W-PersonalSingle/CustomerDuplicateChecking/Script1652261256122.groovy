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

String filePath = userDir + GlobalVariable.Path

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

String DupcheckAppNo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 3)

'count DupcheckAppNo'
String DupCheckCount = CustomKeywords.'dbconnection.FindDupCheckCountQuery.checkDupcheck'(sqlconnection, DupcheckAppNo)

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

    for (int i = 1; i <= count; i++) {
        String newSubjectName = ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[2]'

        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', newSubjectName, true)

        String newSubjectType = ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[3]'

        'modify object subjecttype'
        modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', newSubjectType, true)

        String newButtonedit = ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + i) + ']/td[7]/span/span/span/span/span/span/a'

        'modify object edit icon'
        modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
            'xpath', 'equals', newButtonedit, true)

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

        if (CustomerArray.size() > 0) {
            for (c = 1; c <= CustomerArray.size(); c++) {
                if (subjectName == (CustomerArray[(c - 1)])) {
                    'click button edit'
                    if (WebUI.verifyElementPresent(modifyButtonEdit,5, FailureHandling.OPTIONAL)) {
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                        int countidnorow = variableidno.size()

                        for (id = 1; id <= countidnorow; id++) {
                            String newIDNoCustomer = ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]'

                            'modify object id no customer match'
                            modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', newIDNoCustomer, true)


                            String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer)

                            String IdNoCustomer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoCustomerMatch == IdNoCustomer) {
                            	String newSelect = ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a'
                            			
                            			'modify object id no customer match'
                            			modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarData'), 
                            					'xpath', 'equals', newSelect, true)
										
                                WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                break
                            }
                        }
                        
                       if ((WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 5, FailureHandling.OPTIONAL))){
                            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                                GlobalVariable.NumofColm, 4) == 'New') {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                } else {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarData'))
                                }
                            } else {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarData'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarData'))
                                } else {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (FamilyArray.size() > 0) {
            for (f = 1; f <= FamilyArray.size(); f++) {
                if (subjectName == (FamilyArray[(f - 1)])) {
                    if (WebUI.verifyElementPresent(modifyButtonEdit,5, FailureHandling.OPTIONAL)) {
                        'click button edit'
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variablefamilyidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        int countfamilyidrow = variablefamilyidno.size()

                        for (id = 1; id <= countfamilyidrow; id++) {
                            String newIDNoFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]'

                            'modify object id no family match'
                            modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', newIDNoFamily, true)


                            String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily)

                            String IdNoFamily = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoFamilyMatch == IdNoFamily) {
                            	String newselectFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a'
                            			
                            			'modify object id no family match'
                            			modifyselectFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            					'xpath', 'equals', newselectFamily, true)
										
                                WebUI.click(modifyselectFamily, FailureHandling.OPTIONAL)

                                break
                            }
                        }
                        
                        if ((WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 5, FailureHandling.OPTIONAL))){
                            if ((FamilyActionArray[(f - 1)]) == 'New') {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                } else {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                                }
                            } else {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 5, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                                } else {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (GuarantorArray.size() > 0) {
            for (g = 1; g <= GuarantorArray.size(); g++) {
                if (subjectName == (GuarantorArray[(g - 1)])) {
                    if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
                        'click button edit'
                        WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

                        ArrayList<WebElement> variableGuarantorPersonalidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        int countGuarantorPersonalidrow = variableGuarantorPersonalidno.size()

                        WebDriver Driver = DriverFactory.getWebDriver()

                        ArrayList<WebElement> variabletd = Driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

                        int counttd = variabletd.size()

                        if (counttd == 10) {
                            for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                                String newIDNoGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[4]'

                                'modify object id no family match'
                                modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoPersonal'), 
                                    'xpath', 'equals', newIDNoGuarantorPersonal, true)


                                String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal)

                                String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                                if (NewIdNoGuarantorPersonalMatch == IdNoGuarantorPersonal) {
                                	String newselectGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                			']/td[10]/a'
                                			
                                			'modify object id no family match'
                                			modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                					'xpath', 'equals', newselectGuarantorPersonal, true)
											
                                    WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        } else if (counttd == 5) {
                            for (id = 1; id <= countGuarantorPersonalidrow; id++) {
                                String newIDNoGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[3]'

                                'modify object id no Guarantor match'
                                modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoCompany'), 
                                    'xpath', 'equals', newIDNoGuarantorCompany, true)

                                String newselectGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + 
                                ']/td[5]/a'

                                'modify object id no family match'
                                modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                    'xpath', 'equals', newselectGuarantorCompany, true)

                                String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany)

                                String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

                                if (NewIdNoGuarantorCompanyMatch == IdNoGuarantorCompany) {
                                    WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

                                    break
                                }
                            }
                        }
                        
                        if ((WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'), 5, FailureHandling.OPTIONAL))){
                            if (GuarantorActionArray[g-1] == 'New') {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                }
                                
                                'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                                if (counttd == 10) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                                } else if (counttd == 5) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))
                                }
                            } else {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                                } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'),5, 
                                    FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))
                                } else {
                                    'click button new customer'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    'click button submit'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Submit'))

    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Back'),10, 
        FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_Back'))

        'write to excel if failed'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 
            0, GlobalVariable.NumofColm - 1, 'FAILED')
    }
    
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.DuplicateChecking', 0, 
        GlobalVariable.NumofColm - 1, 'SUCCESS')
}