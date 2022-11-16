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

datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String databaseLOS = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

'connect DB'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

Boolean DupCheckStatus = CustomKeywords.'dbconnection.DupCheckVerif.checkDupCheckStatus'(sqlconnectionLOS, DupcheckAppNo)


def modifySubjectName

def modifySubjectType

def modifyApplicantNo

def modifyCustomerNo

def modifyButtonEdit

String subjectName

String subjectType

 GlobalVariable.FlagFailed=0

if (DupCheckStatus == true) {
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/a_Customer Duplicate Checking'))

	pagingTesting()
	
    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_Application No_AppNoId'), 
        datafileDupcheck.getValue(GlobalVariable.NumofColm, 12))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Search'))

    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

    WebDriver driver = DriverFactory.getWebDriver()

    ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    GlobalVariable.CountDupcheckRow = variable.size()

    //    WebUI.verifyEqual(GlobalVariable.CountNumofCustomer, GlobalVariable.CountDupcheckRow, FailureHandling.OPTIONAL)
    'verify equal number of customer'
    def CustomerArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

    'array customer name data inputan'
    def CustomerNameArray = GlobalVariable.CustomerName.split(';')

    'declare variable count negative customer'
    GlobalVariable.NegativeCustCount = 0

    if (CustomerArray.size() > 0) {
        for (c = 1; c <= CustomerArray.size(); c++) {
            'define interger i'
            int i = 0

            for (GlobalVariable.Index = 1; GlobalVariable.Index <= GlobalVariable.CountDupcheckRow; (GlobalVariable.Index)++) {
                'modify object subjecttype'
                modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[3]', true)

                'modify object edit icon'
                modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[7]/span/span/span/span/span/span/a', true)

                'verify subject type dan button edit ada'
                if ((WebUI.getText(modifySubjectType, FailureHandling.OPTIONAL) == 'CUSTOMER') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
                    FailureHandling.OPTIONAL)) {
                    break
                } else {
                    i++

                    'verify i == total row dupcheck'
                    if (i == GlobalVariable.CountDupcheckRow) {
                        'set GV Index = 1'
                        GlobalVariable.Index = 1

                        'set i = 0'
                        i = 0

                        break
                    }
                }
            }
            
            'modify object subjectname'
            modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectName'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[2]', true)

            'modify object subjecttype'
            modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[3]', true)

            'modify object Applicant No'
            modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNo'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[4]', true)

            'modify object Customer No'
            modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNo'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[5]', true)

            'modify object edit icon'
            modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[7]/span/span/span/span/span/span/a', true)

            'get text subject name'
            subjectName = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

            'get text subject type'
            subjectType = WebUI.getText(modifySubjectType, FailureHandling.OPTIONAL)

			if(GlobalVariable.Role=="Testing" && findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 8).length()==0){
				'verify name == data inputan'
				checkVerifyEqualOrMatch(WebUI.verifyMatch(subjectName, CustomerNameArray[(i)], false))
			}
            

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
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata'),FailureHandling.OPTIONAL), 
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

                            String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject, FailureHandling.OPTIONAL)

                            String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

                            String IdNoCustomer = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'), FailureHandling.OPTIONAL)

                            if (NewIdNoCustomerMatch != null) {
                                if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                    'modify object id no customer match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

									if(GlobalVariable.Role=="Testing"){
										'verify match CustomerNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), newCustomerNoValue, false))
	
									}
                                    
                                    break
                                }
                            }
                        }
                    }
                    
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess'),FailureHandling.OPTIONAL), 
                        'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                        ArrayList<WebElement> applicationinprocessrow = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

                        for (id = 1; id <= applicationinprocessrow.size(); id++) {
                            'modify object id no customer match'
                            modifyIDNoCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            String NewIdNoCustomerPersonalMatch = WebUI.getText(modifyIDNoCustomerPersonal, FailureHandling.OPTIONAL)

                            String IdNoCustomerPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

                            if (NewIdNoCustomerPersonalMatch != null) {
                                if (NewIdNoCustomerPersonalMatch.equalsIgnoreCase(IdNoCustomerPersonal)) {
                                    'modify object select customer match'
                                    modifyselectCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click selct'
                                    WebUI.click(modifyselectCustomerPersonal, FailureHandling.OPTIONAL)
									
									if(GlobalVariable.Role=="Testing"){
										'verify match ApplicantNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL).toString(), newApplicantNoValue.toString(),
											false))
									}
                                    

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

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), '', false))
								}
                                
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

								if(GlobalVariable.Role=="Testing"){
									'verify match CustomerNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), newCustomerNoValue, false))
								}
                                
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select app in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), newApplicantNoValue, false))
								
								}
                                
							}
                        } else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select SimilarData')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								if(GlobalVariable.Role=="Testing"){
									'verify match CustomerNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false))
								}
                                
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), '', false))
								}
                               
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), newApplicantNoValue, false))
								}
                                
                            }
                        } else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select ApplicationInProcess')) {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                    FailureHandling.OPTIONAL)

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, false))
								}
                                
                            } else if (GlobalVariable.Role == 'Testing') {
                                'click button cancel'
                                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                                continue
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false))
								}
                                
                            } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                5, FailureHandling.OPTIONAL)) {
                                String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                    FailureHandling.OPTIONAL)

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								if(GlobalVariable.Role=="Testing"){
									'verify match CustomerNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, false))
								}
                                
                            }
                        }
                    }
                }
            }
            
            '+ index negative customer count'
            GlobalVariable.NegativeCustCount++
        }
    }
    
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingFamily'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
    
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingGuarantor'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
    
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingGetName'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	'click button submit'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Submit'))
	
	Integer iscompleteMandatory = Integer.parseInt(datafileDupcheck.getValue(GlobalVariable.NumofColm,
				4))
	
	if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
		'cek alert'
		GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '4.DuplicateChecking')
	}
	
	if(GlobalVariable.FlagFailed==0){
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

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '4.DuplicateChecking',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)

		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '4.DuplicateChecking',
				1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}

public pagingTesting(){
	//Verify sort & paging
	if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckPagingPersonal=="Yes"){
		Object appNoObject 
		
		'Verif reset'
		CustomKeywords.'paging.verifyPaging.resetPaging'()
		
		'click button search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Search'))
		
		ArrayList<String> listString = new ArrayList<String>()
		
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		
		'Inisialisasi variabel'
		ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
		'Klik header office'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_Office'))
		
		'Verify alert tidak muncul'
		WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)
		
		'Klik header appno'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_appNo'))
		
		'Verify alert tidak muncul'
		WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)
		
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'verif sort appno ascending'
		Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		WebUI.verifyEqual(isSorted,true)
		
		ArrayList<String> listApp = new ArrayList<String>()
		'Klik header appno'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_appNo'))
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listApp.add(WebUI.getText(appNoObject))
		}
		'verif sort appno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
		WebUI.verifyEqual(isSorted,true)
		
		'Klik header custname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_custName'))
		
		'Verify alert tidak muncul'
		WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort custname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		WebUI.verifyEqual(isSorted,true)
		
		'Klik header custname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_custName'))
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort custname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		WebUI.verifyEqual(isSorted,true)
		
		'Klik header product offering name'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_POName'))
		
		'Verify alert tidak muncul'
		WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort poname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		WebUI.verifyEqual(isSorted,true)
		
		'Klik header product offering anme'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_POName'))
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort poname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		WebUI.verifyEqual(isSorted,true)
		
		'Klik 2x header appno supaya appno descending'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_appNo'))
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_appNo'))
		
		'Ambil nilai countdata dari confins'
		String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/countData')).replace(' ', '').replace(':', ';').split(';')
		Integer countDt = Integer.parseInt(textCountData[1])
		
		'Jika countdata secara keseluruhan lebih besar daripada data pada page 1'
		if(countDt>rowData.size()){
			'Klik page 2'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/nextPage'))
			
			'Verif page 2 active'
			WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/nextPage'),'aria-current',2)
			
			rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > app-dup-check-md-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
			
			listString = new ArrayList<String>()
			for(int i=1;i<=rowData.size();i++){
				appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/appNo')
						,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/app-dup-check-md-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
				
				listString.add(WebUI.getText(appNoObject))
			}
			'Verif appno descending pada page 2 tidak ada di page 1'
			Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp,listString)
			WebUI.verifyEqual(isPaging,true)
			
			'Klik button prev'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Prev'))
			
			'Verify page 1 active'
			WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/pageOne'),
				'aria-current', 2)
			
			listApp = listString
					
			listString = new ArrayList<String>()
			
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingDupcheck'(listString)
					
			'Verif appno yang ada di page 1 tidak ada di page 2'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
			
			WebUI.verifyEqual(isPaging, true)
			
			'Klik button next'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Next'))
			
			'Verify page 2 active'
			WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/nextPage'),
				'aria-current', 2)
			
			listApp = listString
			
			listString = new ArrayList<String>()
			
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingDupcheck'(listString)
					
			'Verif appno yang ada di page 2 tidak ada di page 1'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
			
			WebUI.verifyEqual(isPaging, true)
			
			
		}
		
		'Klik button page 1'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/pageOne'))
		
		WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.DupcheckCountDataInPage'(),true)
	}
}




