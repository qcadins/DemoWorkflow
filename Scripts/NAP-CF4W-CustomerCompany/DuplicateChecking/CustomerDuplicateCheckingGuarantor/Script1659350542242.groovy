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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By

'declare datafileDupcheck'
datafileDupcheck = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking')

'declare guarantor array'
def GuarantorArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).split(';', -1)

'declare guarantor action array'
def GuarantorActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 20).split(';', -1)

'declare guarantor negative action array'
def GuarantorNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 21).split(';', -1)

'get app no from data file dupcheck'
String DupcheckAppNo = datafileDupcheck.getValue(GlobalVariable.NumofColm, 12)

'declare web driver'
WebDriver driver = DriverFactory.getWebDriver()

'array customer name data inputan'
def CustomerNameArray = GlobalVariable.CustomerName.split(';')

'declare modify object variable'
def modifyButtonEdit, modifyCustomerNo, modifyApplicantNo, modifySubjectType

'declare variable untuk table dupcheck'
String subjectName, newCustomerNoValue, newApplicantNoValue, newGuarNameAppInProcess, newGuarName

'check if guarantor array size > 0'
if (GuarantorArray.size() > 0) {
    for (int g = 1; g <= GuarantorArray.size(); g++) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
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

				'modify object subjectname'
				modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'),
					'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
					']/td[2]', true)
	
				'get text subject name'
				subjectName = WebUI.getText(modifySubjectName)
	
				'get text subject type'
				subjectType = WebUI.getText(modifySubjectType)
				
                'verify subject type dan button edit ada'
                if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, 
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
        }
        
		'verif subjectname == guarantorarray'
        if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) {
			'verify button edit present'
            if (WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'click button edit'
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

				'count table th application in process'
				ArrayList<WebElement> variabletd = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

				'declare variable counttd'
				int counttd = variabletd.size()
				
                'if role == testing'
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative'
                    if (GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount] == 'NEGATIVE') {
					
						ArrayList<String> variablenegcustno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecNegList > table > tbody tr'))

						'Jika negative check pada dupcheck guarantor bernilai yes'
                        if ((GuarantorNegativeArray[(g - 1)]).equalsIgnoreCase('Yes')) {
                            def modifycheckbox
							
							'looping negative list untuk mencari negative cust no karena hanya yang memiliki negative cust no yang bisa di select'
							for (int id = 1; id <= variablenegcustno.size(); id++) {
								'modify negative cust no'
								modifyNegativeCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'),
									'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[1]", true)
								
								if(WebUI.getText(modifyNegativeCustNo)!=""){
								if (counttd == 10) {
									modifycheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativePersonal'),
										'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[11]/mat-checkbox/label/span[1]", true)
								}else if (counttd == 5){
									modifycheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativeCompany'),
										'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[6]/mat-checkbox/label/span[1]", true)
								}
									break
								}

							}
							if(modifycheckbox!=null){
								'click negative checkbox index 1 yang ada negative cust no'
								WebUI.click(modifycheckbox)
							}
                            else{
								if (counttd == 10) {									
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativePersonal'))
								}else if (counttd == 5) {
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/checkbox_NegativeCompany'))
								}
							}
                        }
                    }
                }

				'verif if table is no data'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					
					'count similar data table tr'
                    ArrayList<WebElement> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

					'looping simillar data row'
                    for (int id = 1; id <= variableidno.size(); id++) {
						'check if counttd = 10 (Personal)'
                        if (counttd == 10) {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object Guarantor No'
                            modifyGuarantorNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object Customer Name'
                            modifyGuarNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text guarantor no value'
                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

                            'get text guarantor new id no'
                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

                            'get text guarantor id no value'
                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'))

                            'get text guarantor name'
                            newGuarName = WebUI.getText(modifyGuarNameObject)

                            'check if id no match'
                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match GuarantorNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newGuarantorNoValue, 
                                                false))
                                    }
                                    
                                    break
                                }
                            }
                        } else {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object Guarantor No'
                            modifyGuarantorNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object Customer Name'
                            modifyGuarNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text guarantor no value'
                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

                            'get text guarantor new id no'
                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

                            'get text guarantor id no value'
                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'))

                            'get text guarantor name'
                            newGuarName = WebUI.getText(modifyGuarNameObject)

                            'check if id no match'
                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match GuarantorNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newGuarantorNoValue, 
                                                false))
                                    }
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
				'check if table no data'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					
					'count application in process row'
                    ArrayList<WebElement> variableGuarantorPersonalidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

					'check if counttd = 10 (Personal)'
                    if (counttd == 10) {
                        for (int id = 1; id <= variableGuarantorPersonalidno.size(); id++) {
                            'modify object id no guarantor match'
                            modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object applicant Name App in process'
                            modifyGuarNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            ' get text applicant no value'
                            newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            'get text id no'
                            String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal, FailureHandling.OPTIONAL)

                            'get text id no guarantor'
                            String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            'get text guarantor name'
                            newGuarNameAppInProcess = WebUI.getText(modifyGuarNameAppInProcess)

                            'check if id no not match'
                            if (NewIdNoGuarantorPersonalMatch != null) {
                                if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click selct'
                                    WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ApplicantNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess).toString(), 
                                                newApplicantNoValue.toString(), false))
                                    }
                                    
                                    break
                                }
                            }
                        }
                    } else {
                        for (int id = 1; id <= variableGuarantorPersonalidno.size(); id++) {
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCompany'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[3]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                            'modify object applicant Name App in process'
                            modifyGuarNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

                            'get text applicant no value'
                            newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

                            'get text id no guarantor company'
                            String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany, FailureHandling.OPTIONAL)

                            'get text id no guarantor'
                            String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

                            'get text guarantor name'
                            newGuarNameAppInProcess = WebUI.getText(modifyGuarNameAppInProcess)

                            'check if idno no match'
                            if (NewIdNoGuarantorCompanyMatch != null) {
                                if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select application in process'
                                    WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'verify match ApplicantNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess).toString(), 
                                                newApplicantNoValue.toString(), false))
                                    }
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
				'check if subjecttype header stil present'
                if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'verify guarantor action array == new'
                    if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

							'check if role testing'
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
						//Karena button action New tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                            
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get guarantor name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get Guar name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName).toString(), 
                                        newCustomerNoValue.toString(), false))
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get text applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new guar name'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else {
                                'get text new applicant no'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get guarantor name app in process'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select applcation in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false))
                            }
                        }
                    } else if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get text new customer no'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get text guarantor name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new guarantor name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName).toString(), 
                                        newCustomerNoValue.toString(), false))
                            }
						//Karena button action similar data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new guarantor name'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new guarantor name'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false))
                            }
                        }
                    } else {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get new guarantor name'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else {
                                'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get text new guarantor name'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess).toString(), 
                                        newApplicantNoValue.toString(), false))
                            }
						//Karena button action application data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.RoleCompany == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new customer name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else {
                                'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new guarantor name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName).toString(), 
                                        newCustomerNoValue.toString(), false))
                            }
                        }
                    }
                }
            }
        }
        
        '+ index negative customer count'
        (GlobalVariable.NegativeCustCount)++

        if (g == GuarantorArray.size()) {
            break
        }
    }
}

def loopingSubjectApplicantNo(String newGuarNameAppInProcess) {
    String applicantNo

    Object modifyAppliNo

    for (int z = 1; z <= GlobalVariable.CountDupcheckRow; z++) {
        'modify object subjecttype'
        Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[3]', true)

        'modify object subject name'
        Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[2]', true)

        'verify subject type dan button edit ada'
        if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyMatch(newGuarNameAppInProcess, WebUI.getText(
                modifySubjectName), false, FailureHandling.OPTIONAL)) {
            'modify object applicant no'
            modifyAppliNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[4]', true)

            break
        }
    }
    
    applicantNo = WebUI.getText(modifyAppliNo)

    return applicantNo
}

def loopingSubjectCustNo(String newGuarName) {
    String custNo

    Object modifyCustNo

    for (int z = 1; z <= GlobalVariable.CountDupcheckRow; z++) {
        'modify object subjecttype'
        Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[3]', true)

        'modify object subject name'
        Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[2]', true)

        'verify subject type dan button edit ada'
        if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyMatch(newGuarName, WebUI.getText(modifySubjectName), 
            false, FailureHandling.OPTIONAL)) {
            'modify object custno'
            modifyCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[5]', true)

            break
        }
    }
    
    custNo = WebUI.getText(modifyCustNo)

    return custNo
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

