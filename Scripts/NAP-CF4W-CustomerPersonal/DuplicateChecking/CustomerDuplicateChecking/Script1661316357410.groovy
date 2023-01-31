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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileDupcheck'
datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'get app no from data file dupcheck'
String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'cek dupcheckstatus from db'
Boolean DupCheckStatus = CustomKeywords.'dupCheck.dupCheckVerif.checkDupCheckStatus'(sqlconnectionLOS, DupcheckAppNo)

GlobalVariable.FlagFailed = 0

if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'), FailureHandling.OPTIONAL)){
	'click menu consumer finance'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
}

if(WebUI.verifyElementNotVisible(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/a_Customer Duplicate Checking'), FailureHandling.OPTIONAL)){
	'click menu new consumer finance'
	WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
}

if (DupCheckStatus == true) {
	
	WebUI.delay(3)
	
    'click menu duplicate Checking'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/a_Customer Duplicate Checking'))

	WebUI.delay(10)
	
	'click menu duplicate Checking'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/a_Customer Duplicate Checking'))
	
	'click menu duplicate Checking'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/a_Customer Duplicate Checking'))
	
    'call paging testing function'
    pagingTesting()

    'input Appno'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/input_Application No_AppNoId'), 
        datafileDupcheck.getValue(GlobalVariable.NumofColm, 12))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Search'))

    'click action'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

	'initialize driver'
    WebDriver driver = DriverFactory.getWebDriver()

    ArrayList<String> variable = driver.findElements(By.cssSelector('#ListSubjId > lib-ucgridview > div > table > tbody tr'))

    GlobalVariable.CountDupcheckRow = variable.size()

    'get cust name dari data file excel'
    custnamedupcheck = datafileDupcheck.getValue(GlobalVariable.NumofColm, 13)

    'array customer name data inputan'
    def CustomerNameArray = GlobalVariable.CustomerName.split(';')

    'declare variable count negative customer'
    GlobalVariable.NegativeCustCount = 0

	'looping countdupcheckrow'
	for (GlobalVariable.Index = 1; GlobalVariable.Index <= GlobalVariable.CountDupcheckRow; (GlobalVariable.Index)++) {
	
		'modify object subjectname'
		modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectName'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
			']/td[2]', true)

		'get text subject name'
		subjectName = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

		'Jika role testing dan edit appno kosong'
		if ((GlobalVariable.Role == 'Testing') && (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm,
			8).length() == 0)) {
			'verify name == data inputan'
			checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomerNameArray.contains(subjectName), true))
		}
	
	}
	
	GlobalVariable.Index = 1
	
	'Pengecekan jika dupcheck customer name tidak kosong'
    if (custnamedupcheck.length() > 0) {
        'modify object subjecttype'
        modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[3]', 
            true)

        'modify object edit icon'
        modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[7]/span/span/span/span/span/span/a', 
            true)

        'modify object subjectname'
        modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectName'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[2]', 
            true)

        'modify object Applicant No'
        modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNo'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[4]', 
            true)

        'modify object Customer No'
        modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNo'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + ']/td[5]', 
            true)

        'get text subject name'
        subjectName = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

        'get text subject type'
        subjectType = WebUI.getText(modifySubjectType, FailureHandling.OPTIONAL)
        
		'Jika subject name confins sama dengan excel'
        if (subjectName.equalsIgnoreCase(custnamedupcheck)) {
            'click button edit'
            if (WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                
				'click edit/icon pensil'
				WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

				CustomKeywords.'customizeKeyword.zoomKeyword.zoomOut'(20)
				
                'if role == testing'
                if (GlobalVariable.Role == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative atau dupcheck verif = lock dan negative check = negative dan ada select app in process'
                    if (GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount] == 'NEGATIVE') {
					
						ArrayList<String> variablenegcustno = driver.findElements(By.cssSelector('#subSecNegList > table > tbody tr'))
						
						'Jika pada excel negative checking bernilai yes'
                        if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 15).equalsIgnoreCase('Yes')) {
							def modifycheckbox
							
							'looping negative list untuk mencari negative cust no karena hanya yang memiliki negative cust no yang bisa di select'
							for (int id = 1; id <= variablenegcustno.size(); id++) {
								'modify negative cust no'
								modifyNegativeCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'),
									'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[1]", true)
								
								if(WebUI.getText(modifyNegativeCustNo)!=""){
									modifycheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'),
										'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[11]/mat-checkbox/label/span[1]", true)
									break
								}

							}
							if(modifycheckbox!=null){
								'click negative checkbox index 1 yang ada negative cust no'
								WebUI.click(modifycheckbox)
							}
                            else{
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/checkbox_NegativePersonal'))
							}
                        }
                    }
                }
                
				'Pengecekan jika ada data similar'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					
                    ArrayList<String> variableidno = driver.findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

					'looping similar data'
                    for (int id = 1; id <= variableidno.size(); id++) {
                        'modify object id no customer match'
                        modifyIDNoCustomer = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object Customer No'
                        modifyCustomerNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

						'get text new customer no'
                        String newCustomerNoValue = WebUI.getText(modifyCustomerNoObject, FailureHandling.OPTIONAL)

						'get text newidnocust'
                        String NewIdNoCustomerMatch = WebUI.getText(modifyIDNoCustomer, FailureHandling.OPTIONAL)

						'get text id no cust'
                        String IdNoCustomer = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'), 
                            FailureHandling.OPTIONAL)

						'Jika newidnocust tidak null'
                        if (NewIdNoCustomerMatch != null) {
							'Jika new id no cust = id no cust'
                            if (NewIdNoCustomerMatch.equalsIgnoreCase(IdNoCustomer)) {
                                'modify object id no customer match'
                                modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select match similar data'
                                WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                if (GlobalVariable.Role == 'Testing') {
                                    'verify match CustomerNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), 
                                            newCustomerNoValue, false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
				'Jika ada data app in process'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<String> applicationinprocessrow = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))
					
					'Looping data app in process'
                    for (int id = 1; id <= applicationinprocessrow.size(); id++) {
                        'modify object id no customer match'
                        modifyIDNoCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoPersonal'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object applicant No App in process'
                        modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

						'get text newapplicantno'
                        String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

						'get text newidnocust'
                        String NewIdNoCustomerPersonalMatch = WebUI.getText(modifyIDNoCustomerPersonal, FailureHandling.OPTIONAL)

						'get text idnocust'
                        String IdNoCustomerPersonal = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'))

						'Jika newidnocust tidak null'
                        if (NewIdNoCustomerPersonalMatch != null) {
							'Jika newidnocust = idnocust'
                            if (NewIdNoCustomerPersonalMatch.equalsIgnoreCase(IdNoCustomerPersonal)) {
                                'modify object select customer match'
                                modifyselectCustomerPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select'
                                WebUI.click(modifyselectCustomerPersonal, FailureHandling.OPTIONAL)

                                if (GlobalVariable.Role == 'Testing') {
                                    'verify match ApplicantNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL).toString(), 
                                            newApplicantNoValue.toString(), false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
				'Jika subjecttypeheader belum muncul'
                if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/subjecttypeheader'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'Jika excel action bernilai new'
                    if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('New')) {
						'Jika ada button new pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        '', false))
                            }
						//Karena button action New tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))
							
							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
						//pengecekan jika ada match similar data
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new custno'
                            String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo, FailureHandling.OPTIONAL), 
                                        newCustomerNoValue, false))
                            }
						//pengecekan jika ada appinprocess
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new applicant no'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select app in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        newApplicantNoValue, false))
                            }
                        }
					//jika action excel bernilai select similardata
                    } else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select SimilarData')) {
                        'Jika ada button select match similar data'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new cust no'
                            String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, 
                                        false))
                            }
						//Karena button action similar data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))
                        
							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
						} //jika ada button new
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        '', false))
                            }
                        } //jika ada select application in process
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new applicant no'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo, FailureHandling.OPTIONAL), 
                                        newApplicantNoValue, false))
                            }
                        }
                    } //Pengecekan jika action bernilai select applicationinprocess
					else if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('Select ApplicationInProcess')) {
						'jika select appinprocess ada pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new applicant no'
                            String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                FailureHandling.OPTIONAL)

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInprocessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyApplicantNo), newApplicantNoValue, 
                                        false))
                            }
						//Karena button action application in process tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))
							
							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } //jika button new ada pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false))
                            }
                        } //jika match similar data ada pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text newcustno'
                            String newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                FailureHandling.OPTIONAL)

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCustomerNo), newCustomerNoValue, 
                                        false))
                            }
                        }
                    }
                }
            }
        }
        
        '+ index negative customer count'
        (GlobalVariable.NegativeCustCount)++
    }
    
	'call test case dupcheckfamily'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingFamily'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

	'call test case dupcheckguarantor'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingGuarantor'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

	'call test case dupcheckgetname'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateCheckingGetName'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)

    'click button submit'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Submit'))

    Integer iscompleteMandatory = Integer.parseInt(datafileDupcheck.getValue(GlobalVariable.NumofColm, 4))

    if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
        'cek alert'
        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
            '4.DuplicateChecking')
    }
    
    if (GlobalVariable.FlagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/input_Application No_AppNoId'), 
            GlobalVariable.NumofColm, '4.DuplicateChecking')
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Back'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Back')) //Verify sort & paging
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def pagingTesting() {
    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckPagingPersonal == 'Yes')) {
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
        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
            1))) {

			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedFooter + 'Dupcheck') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
        
		'Pengecekan jika pada verif sort ada yang tidak sesuai'
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
            1))) {

			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 2).replace(
                    '-', '') + ((GlobalVariable.ReasonFailedSort + 'Dupcheck') + ';\n'))
			
            GlobalVariable.FlagWarning = 1
        }
        
		'Pengecekan jika pada verif footer ada yang tidak sesuai'
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
            1))) {
						
			'Write To Excel GlobalVariable.StatusWarning and reason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 2).replace('-',
					'') + ((GlobalVariable.ReasonFailedFooter + 'Dupcheck') + ';\n'))

            GlobalVariable.FlagWarning = 1
        }
    }
}