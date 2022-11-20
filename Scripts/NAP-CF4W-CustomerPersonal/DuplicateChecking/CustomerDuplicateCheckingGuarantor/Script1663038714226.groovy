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

'delcare data file customer duplicate checking'
datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'declare data file customer main data'
datafilecustomermaindata = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'get guarantor array dupcheck dari excel'
def GuarantorArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).split(';', -1)

'get guarantor array action dupcheck dari excel'
def GuarantorActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 20).split(';', -1)

'get guarantor array negative action dari excel'
def GuarantorNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 21).split(';', -1)

'array customer name data inputan'
def CustomerNameArray = GlobalVariable.CustomerName.split(';')

'declare modify object variable'
def modifyButtonEdit, modifyCustomerNo, modifyApplicantNo, modifySubjectType

'declare subjectname variable'
String subjectName, newCustomerNoValue, newApplicantNoValue, newGuarNameAppInProcess, newGuarName

if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).length()>0) {
    for (int g = 1; g <= GuarantorArray.size(); g++) {
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
                if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyElementPresent(modifyButtonEdit, 5, 
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
            modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                ']/td[2]', true)

            'get text subject name'
            subjectName = WebUI.getText(modifySubjectName)

            'get text subject type'
            subjectType = WebUI.getText(modifySubjectType)
			
			if(GlobalVariable.Role=="Testing" && datafilecustomermaindata.getValue(GlobalVariable.NumofColm, 8).length()==0){
						'verify name == data inputan'
						checkVerifyEqualOrMatch(WebUI.verifyEqual(CustomerNameArray.contains(subjectName), true))
			}
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

                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata'),FailureHandling.OPTIONAL), 
                    'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

                    for (int id = 1; id <= variableidno.size(); id++) {
                        if (counttd == 10) {
							
							
                            'modify object id no Guarantor match'
                            modifyIDNoGuarantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object Guarantor No'
                            modifyGuarantorNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                                'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)
							
							'modify object Customer Name'
							modifyGuarNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'),
								'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

							'get text guarantor no value'
                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

							'get text guarantor new id no'
                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

							'get text guarantor id no value'
                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

							'get text guarantor name'
							newGuarName = WebUI.getText(modifyGuarNameObject)
							
							'check if id no match'
                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)
									
									if(GlobalVariable.Role=="Testing"){
										'verify match GuarantorNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newGuarantorNoValue, false))
									}
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
							
							'modify object Customer Name'
							modifyGuarNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'),
								'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)
							

                            'get text guarantor no value'
                            String newGuarantorNoValue = WebUI.getText(modifyGuarantorNoObject)

							'get text guarantor new id no'
                            String NewIdNoGuarantorMatch = WebUI.getText(modifyIDNoGuarantor, FailureHandling.OPTIONAL)

							'get text guarantor id no value'
                            String IdNoGuarantor = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

							'get text guarantor name'
							newGuarName = WebUI.getText(modifyGuarNameObject)
							
							'check if id no match'
                            if (NewIdNoGuarantorMatch != null) {
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

									if(GlobalVariable.Role=="Testing"){
										'verify match GuarantorNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newGuarantorNoValue, false))
	
									}
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess'),FailureHandling.OPTIONAL), 
                    'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableGuarantorPersonalidno = DriverFactory.getWebDriver().findElements(By.cssSelector(
                            '#subSecAppProcess > table > tbody tr'))

                    if (counttd == 10) {
                        for (int id = 1; id <= variableGuarantorPersonalidno.size(); id++) {
                            'modify object id no guarantor match'
                            modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                            'modify object applicant No App in process'
                            modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                                'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)
							
							'modify object applicant Name App in process'
							modifyGuarNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
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

									if(GlobalVariable.Role=="Testing"){
										'verify match ApplicantNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), newApplicantNoValue.toString(),
											false))
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
							modifyGuarNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
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

									if(GlobalVariable.Role=="Testing"){
										'verify match ApplicantNo'
										checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), newApplicantNoValue.toString(),
											false))
									}

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
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false))
							}
                            
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								
                            } else {
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newCustomerNoValue.toString(),
									false))
							}
							
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								
                               
                            } else {
								'get test new applicant no'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get guarantor name app in process'
								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select applcation in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

								
                                
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), newApplicantNoValue.toString(),
									false))
							}
                        }
                    } else if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')) {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get text new customer no'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get text guarantor name'
								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                                
                            } else {
								'get text customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get new guarantor name'
								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

                                
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newCustomerNoValue.toString(),
									false))
							}
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))
								
								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false)
								}
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get new applcant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get new guarantor name'
								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								
                            } else {
								'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get new guarantor name'
								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
							
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), newApplicantNoValue.toString(),
									false))
							}
							
                        }
                    } else {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                            5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            5, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get new guarantor name'
								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								
                                
                            } else {
								'get new applicant no value'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get text new guarantor name'
								newGuarNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
								
                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                                
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), newApplicantNoValue.toString(),
									false))
							}
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

                            continue
                        } else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            5, FailureHandling.OPTIONAL)) {
                         
                                'click button new customer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))
								
								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false)
								}
                            
                        } else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get new customer name'
								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								
                                
                            } else {
								'get new customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get new guarantor name'
								newGuarName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
								
                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

							
                                
                            }
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newCustomerNoValue.toString(),
									false))
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

public loopingSubjectApplicantNo(String newGuarNameAppInProcess){
	String applicantNo
	Object modifyAppliNo
	for (int z = 1; z <= GlobalVariable.CountDupcheckRow; (z)++) {
		'modify object subjecttype'
		Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[3]', true)
		
		'modify object subject name'
		Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[2]', true)

		'verify subject type dan button edit ada'
		if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyMatch(newGuarNameAppInProcess, WebUI.getText(modifySubjectName),false,
			FailureHandling.OPTIONAL)) {
			'modify object applicant no'
			modifyAppliNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[4]', true)
			
			
			
			break
		}
	}
	applicantNo = WebUI.getText(modifyAppliNo)
	return applicantNo
}

public loopingSubjectCustNo(String newGuarName){
	String custNo
	Object modifyCustNo
	for (int z = 1; z <= GlobalVariable.CountDupcheckRow; (z)++) {
		'modify object subjecttype'
		Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[3]', true)
		
		'modify object subject name'
		Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[2]', true)

		'verify subject type dan button edit ada'
		if ((WebUI.getText(modifySubjectType) == 'GUARANTOR') && WebUI.verifyMatch(newGuarName, WebUI.getText(modifySubjectName),false,
			FailureHandling.OPTIONAL)) {
			'modify object custno'
			modifyCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
			'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) +
			']/td[5]', true)
			
			break
		}
	}
	custNo = WebUI.getText(modifyCustNo)
	return custNo
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