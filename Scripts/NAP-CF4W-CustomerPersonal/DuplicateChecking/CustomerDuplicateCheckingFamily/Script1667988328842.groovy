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

'declare familyarray'
def FamilyArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

'declare familyactionarray'
def FamilyActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

'declare familynegativearray'
def FamilyNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

'array customer name data inputan'
def CustomerNameArray = GlobalVariable.CustomerName.split(';')

'declare modify object variable'
def modifyButtonEdit, modifyCustomerNo, modifyApplicantNo, modifySubjectType

'declare subjectname,newapplicantnovalue,newfamilynameappinprocess,newfamilyname,newcustomernovalue'
String subjectName, newApplicantNoValue, newFamilyNameAppInProcess, newFamilyName, newCustomerNoValue

'Pengecekan jika family dupcheck tidak kosong'
if ((FamilyArray.size() > 0) && (datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).length() > 0)) {
	'looping family dupcheck excel'
    for (int f = 1; f <= FamilyArray.size(); f++) {
		'Pengecekan ada tidaknya elemen subjecttypeheader'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/subjecttypeheader'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'define interger i'
            int i = 0
			
			'looping countdupcheckrow'
            for (GlobalVariable.Index = 1; GlobalVariable.Index <= GlobalVariable.CountDupcheckRow; (GlobalVariable.Index)++) {
                'modify object subjecttype'
                modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[3]', true)

                'modify object edit icon'
                modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 
                    'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) + 
                    ']/td[7]/span/span/span/span/span/span/a', true)
				
				'modify object subjectname'
				modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectName'),
					'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
					']/td[2]', true)
	
				'get text subject name'
				subjectName = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
				
				'get text subject type'
				subjectType = WebUI.getText(modifySubjectType)
				
                'verify subject type dan button edit ada tidaknya'
                if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
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
        
		'Jika subject name pada confins sesuai dengan family dupcheck pada excel'
        if (subjectName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
			'Jika ada button edit pada confins'
            if (WebUI.verifyElementPresent(modifyButtonEdit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'click button edit'
                WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)
				
                'if role == testing'
                if (GlobalVariable.Role == 'Testing') {
                    'if dupcheck verif == review dan negative check == negative atau dupcheck verif == lock dan negative check == negative dan ada button select appinprocess'
                    if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
                    GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 
                    'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && 
                    WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)||(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)))) {
					
						ArrayList<String> variablenegcustno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecNegList > table > tbody tr'))
					
						'Jika negative check pada excel bernilai yes'
                        if ((FamilyNegativeArray[(f - 1)]).equalsIgnoreCase('Yes')) {
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
                
				'Jika ada data confins pada match similar data'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundSimilardata'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					
                    ArrayList<String> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

					'Looping confins match similar data'
                    for (int id = 1; id <= variableidno.size(); id++) {
                        'modify object id no family match'
                        modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoCustomerMatchSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object Customer No'
                        modifyFamilyNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)

                        'modify object Customer Name'
                        modifyFamilyNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'), 
                            'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)

						'get text newfamilyno'
                        String newFamilyNoValue = WebUI.getText(modifyFamilyNoObject)

						'get text new id no family'
                        String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

						'get text id no family'
                        String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'))

						'get text newfamilyname'
                        newFamilyName = WebUI.getText(modifyFamilyNameObject)

						'Jika newidnofamily tidak null'
                        if (NewIdNoFamilyMatch != null) {
							'Jika newidnofamilymatch = idnofamily'
                            if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
                                'modify object id no family match'
                                modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                                    'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a', 
                                    true)

                                'click select match similar data'
                                WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

                                if (GlobalVariable.Role == 'Testing') {
                                    'verify match FamilyNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newFamilyName), newFamilyNoValue, 
                                            false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
				'Jika ada data confins pada application in process'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/label_NoDataFoundAppInProcess'), 
                        FailureHandling.OPTIONAL), 'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
                    ArrayList<String> variablefamilyidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

					'Looping data confins application in process'
                    for (int id = 1; id <= variablefamilyidno.size(); id++) {
                        'modify object id no family match'
                        modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IDNoPersonal'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

                        'modify object applicant No App in process'
                        modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)

                        'modify object applicant No App in process'
                        modifyFamilyNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'), 
                            'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

						'get text newapplicantno'
                        newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

						'get text newidnofamily'
                        String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

						'get text idnofamily'
                        String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/IdNoCustomer'))

						'get text newfamilyname'
                        newFamilyNameAppInProcess = WebUI.getText(modifyFamilyNameAppInProcess)

						'Jika newidnofamilymatch tidak bernilai null'
                        if (NewIdNoFamilyMatch != null) {
							'Jika newidnofamilymatch = idnofamily'
                            if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
                                String newselectFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a'

                                'modify object id no family match'
                                modifyselectFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                                    'xpath', 'equals', newselectFamily, true)

                                'click select application in process'
                                WebUI.click(modifyselectFamily, FailureHandling.OPTIONAL)

                                if (GlobalVariable.Role == 'Testing') {
                                    'verify match ApplicantNo'
                                    checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newFamilyNameAppInProcess), 
                                            newApplicantNoValue.toString(), false))
                                }
                                
                                break
                            }
                        }
                    }
                }
                
				'Pengecekan jika subjecttypeheader belum muncul'
                if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/subjecttypeheader'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'Jika action pada excel dupcheck family bernilai new'
                    if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('New')) {
						'Jika button new ada pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

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
                        } //Jika button select application in process ada pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new applicant no'
                            newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							'get text newfamilyname'
                            newFamilyNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newFamilyNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
                        } //Jika ada select match similar data pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text custno'
                            String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							'get text newfamilyname'
                            newFamilyName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newFamilyName), CustomerNoValue.toString(), 
                                        false))
                            }
                        }
                    } //Jika family action pada dupcheck excel bernilai select applicationinprocess
					else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
						'Jika ada select appinprocess pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text new applicant no '
                            newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							'get text newfamilyname'
                            newFamilyNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newFamilyNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
						//Karena button action application in process tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
                            
							KeywordUtil.markFailedAndStop('gagal dupcheck')
							
                        } //Jika ada button new
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
                        } //Jika ada button select match similar data
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text cust no'
                            String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							'get text new family name'
                            newFamilyName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newFamilyName), CustomerNoValue.toString(), 
                                        false))
                            }
                        }
                    } //Jika family action pada excel dupcheck bernilai select similardata
					else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select SimilarData')) {
						'Jika button select similardata ada pada confins'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text customerno'
                            String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							'get text newfamilyname'
                            newFamilyName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameSimilarData'))

                            'click select match similar data'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match CustomerNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectCustNo(newFamilyName), CustomerNoValue.toString(), 
                                        false))
                            }
						//Karena button action similar data tidak ketemu cek if Role = testing, jika iya maka akan dicancel
                        } else if (GlobalVariable.Role == 'Testing') {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDupcheck'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.DuplicateChecking', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDupcheck)
							
							KeywordUtil.markFailedAndStop('gagal dupcheck')
                        } //Jika ada button new pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button new customer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_New Customer'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyNotMatch(loopingSubjectApplicantNo(subjectName), '', 
                                        false))
                            }
                        } //Jika ada button select application in process pada confins
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'get text newapplicantno'
                            newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							'get text newfamilyname'
                            newFamilyNameAppInProcess = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/tr_CustNameAppInProcess'))

                            'click select application in process'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

                            if (GlobalVariable.Role == 'Testing') {
                                'verify match ApplicantNo'
                                checkVerifyEqualOrMatch(WebUI.verifyMatch(loopingSubjectApplicantNo(newFamilyNameAppInProcess), 
                                        newApplicantNoValue.toString(), false))
                            }
                        }
                    }
                }
            }
        }
        
        '+ index negative customer count'
        (GlobalVariable.NegativeCustCount)++
		
        if (f == FamilyArray.size()) {
            break
        }
    }
}

def loopingSubjectApplicantNo(String newFamilyNameAppInProcess) {
	'declare applicantno'
    String applicantNo

	'declare modifyapplino'
    Object modifyAppliNo

	'Looping countdupcheckrow'
    for (int z = 1; z <= GlobalVariable.CountDupcheckRow; z++) {
        'modify object subjecttype'
        Object modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[3]', true)

        'modify object subject name'
        Object modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
            'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[2]', true)

        'verify subject type dan button edit ada'
        if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyMatch(newFamilyNameAppInProcess, WebUI.getText(
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

def loopingSubjectCustNo(String newFamilyName) {
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
        if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyMatch(newFamilyName, WebUI.getText(modifySubjectName), 
            false, FailureHandling.OPTIONAL)) {
            'modify object custno'
            modifyCustNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/DuplicateChecking/SubjectType'), 
                'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + z) + ']/td[5]', true)

            break
        }
    }
    
	'get text modify cust no'
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