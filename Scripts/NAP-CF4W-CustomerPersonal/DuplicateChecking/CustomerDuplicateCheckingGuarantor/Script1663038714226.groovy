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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

'declare datafileDupcheck'
datafileDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking')

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

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

'declare subjectname, newcustomernovalue, newapplicantnovalue, newguarnameappinprocess, newguarname'
String subjectName, newCustomerNoValue, newApplicantNoValue, newGuarNameAppInProcess, newGuarName

'Pengecekan jika guarantor name pada dupcheck excel tidak kosong'
if (datafileDupcheck.getValue(GlobalVariable.NumofColm, 19).length() > 0) {
	'looping guarantor array dupcheck excel'
    for (int g = 1; g <= GuarantorArray.size(); g++) {
		'Pengecekan jika ada pada confins subjecttypeheader'
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'define interger i'
            int i = 0
			
			'looping countdupcheckrow'
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
				
                'verify subject type sesuai dan button edit ada atau tidak'
                if ((subjectType == 'GUARANTOR') && WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, 
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
        
		'Jika subjectname pada confins = guarantor name pada excel'
        if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) {
			'Jika ada button edit pada confins'
            if (WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'click button edit'
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

				CustomKeywords.'customizeKeyword.zoomKeyword.zoomOut'(20)
				
				ArrayList<WebElement> variabletd = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecAppProcess > table > thead th'))
				
				'declare counttd'
				int counttd = variabletd.size()
				
                'if role == testing'
                if (GlobalVariable.Role == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative atau dupcheck verif == lock dan negative check == negative dan ada button select appinprocess'
                    if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                    GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                    'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && 
                    WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL))) {
					
						ArrayList<String> variablenegcustno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecNegList > table > tbody tr'))

						'Jika negative check pada dupcheck guarantor bernilai yes'
                        if ((GuarantorNegativeArray[(g - 1)]).equalsIgnoreCase('Yes')) {
                            def modifycheckbox
							for (int id = 1; id <= variablenegcustno.size(); id++) {
								'modify negative cust no'
								modifyNegativeCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'),
									'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[1]", true)
								
								if(WebUI.getText(modifyNegativeCustNo)!=""){
								if (counttd == 10) {
									modifycheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/checkbox_NegativePersonal'),
										'xpath', 'equals', "//*[@id='subSecNegList']/table/tbody/tr["+id+"]/td[11]/mat-checkbox/label/span[1]", true)
								}else if (counttd == 5){
									modifycheckbox = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/checkbox_NegativeCompany'),
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
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/checkbox_NegativePersonal'))
								}else if (counttd == 5) {
								WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/checkbox_NegativeCompany'))
								}
							}
                        }
                    }
                }

				'Jika pada confins ada data match similar data'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

					'looping data match similar data'
                    for (int id = 1; id <= variableidno.size(); id++) {
						'jika jumlah kolom ada 10 atau bukan'
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

                            'check jika newidnoguarantormatch tidak null'
                            if (NewIdNoGuarantorMatch != null) {
								'Jika newidnoguarantormatch = idnoguarantor'
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.Role == 'Testing') {
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

                            'jika newidnoguarantor match tidak null'
                            if (NewIdNoGuarantorMatch != null) {
								'Jika newidnoguarantormatch = idnoguarantor'
                                if (NewIdNoGuarantorMatch.equalsIgnoreCase(IdNoGuarantor)) {
                                    'modify object id no Guarantor match'
                                    modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                        'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select match similar data'
                                    WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.Role == 'Testing') {
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
                
				'Pengecekan jika ada pada confins data app in process'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<WebElement> variableGuarantorPersonalidno = DriverFactory.getWebDriver().findElements(By.cssSelector(
                            '#subSecAppProcess > table > tbody tr'))

					'Pengecekan jumlah kolom pada confins 10 atau bukan'
                    if (counttd == 10) {
						'Looping data confins app in process'
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

                            'check if newidnoguarantorpersonalmatch tidak null'
                            if (NewIdNoGuarantorPersonalMatch != null) {
								'Jika newidnoguarantorpersonalmatch = idnoguarantorpersonal'
                                if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                        true)

                                    'click selct'
                                    WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.Role == 'Testing') {
                                        'verify match ApplicantNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), 
                                                newApplicantNoValue.toString(), false))
                                    }
                                    
                                    break
                                }
                            }
                        }
                    } else {
						'looping data confins appinprocess'
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

                            'check if NewIdNoGuarantorCompanyMatch tidak null'
                            if (NewIdNoGuarantorCompanyMatch != null) {
								'jika NewIdNoGuarantorCompanyMatch = IdNoGuarantorCompany'
                                if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) {
                                    'modify object select managementshareholder match'
                                    modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'), 
                                        'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[5]/a', 
                                        true)

                                    'click select application in process'
                                    WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

                                    if (GlobalVariable.Role == 'Testing') {
                                        'verify match ApplicantNo'
                                        checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), 
                                                newApplicantNoValue.toString(), false))
                                    }
                                    
                                    break
                                }
                            }
                        }
                    }
                }
                
				//Jika pada confins belum muncul object subjecttypeheader
                if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'Jika guarantor action pada excel bernilai new'
                    if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) {
						'Jika ada button new pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
						//Karena button action New tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } //Jika ada button match similar data pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get text newcustomerno'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get text newguarname'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))
                            } else {
								'get text newcustno'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'get text newguarname'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newCustomerNoValue.toString(), 
                                        false))
                            }
                        } //jika ada button appinprocess pada confins
						else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
								'get text newapplicantno'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'get text newguarname'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select application in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))
                            } else {
                                'get test new applicant no'
                                newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

                                'get guarantor name app in process'
                                newGuarNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                                'click select applcation in process'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))
                            }
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
                        }
                    } //Jika guarantor action pada excel bernilai select similardata
					else if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')) {
						'Jika ada button select similardata pada confins'
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
                                'get text customer no value'
                                newCustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

                                'get new guarantor name'
                                newGuarName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                                'click select match similar data'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))
                            }
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newGuarName), newCustomerNoValue.toString(), 
                                        false))
                            }
						//Karena button action similar data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
                            
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } //Jika ada button new
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false)
                            }
                        } //jika ada button appinprocess pada confins
						else {
                            'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
                            if (counttd == 10) {
                                'get new applcant no value'
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
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
                        }
                    } //Jika guarantor action bernilai select appplicationinprocess
					else {
						'Jika ada button select appinprocess pada confins'
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
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newGuarNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
						//Karena button action application in process tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
                            
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } //Jika ada button new pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', false)
                            }
                        } //Jika ada button select matchsimilardata pada confins
						else {
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
                            
                            if (GlobalVariable.Role == 'Testing') {
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

def loopingSubjectApplicantNo(String newGuarNameAppInProcess) {
	'declare applicantno'
    String applicantNo

	'declare modifyapplino'
    Object modifyAppliNo

	'looping countdupcheckrow'
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
    
	'get text modifyapplino'
    applicantNo = WebUI.getText(modifyAppliNo)

    return applicantNo
}

def loopingSubjectCustNo(String newGuarName) {
	'declare custno'
    String custNo

	'declare modifycustno'
    Object modifyCustNo

	'looping countdupcheckrow'
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
    
	'get text modifycustno'
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

