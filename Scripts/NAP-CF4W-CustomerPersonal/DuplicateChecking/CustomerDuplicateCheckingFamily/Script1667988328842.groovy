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

def FamilyArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

def FamilyActionArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 17).split(';', -1)

def FamilyNegativeArray = datafileDupcheck.getValue(GlobalVariable.NumofColm, 18).split(';', -1)

def modifySubjectName

def modifySubjectType

String modifyApplicantNo

String modifyCustomerNo

def modifyButtonEdit

String subjectName

String subjectType

'array customer name data inputan'
def CustomerNameArray = GlobalVariable.CustomerName.split(';')

if (FamilyArray.size() > 0 && datafileDupcheck.getValue(GlobalVariable.NumofColm, 16).length()>0) {
	for (f = 1; f <= FamilyArray.size(); f++) {
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/subjecttypeheader'),
			5, FailureHandling.OPTIONAL)) {
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
				if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyElementPresent(modifyButtonEdit, 5,
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
			
			subjectName = WebUI.getText(modifySubjectName)

			'modify object subjecttype'
			modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/SubjectType'),
				'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
				']/td[3]', true)

//			'modify object Applicant No'
//			modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNo'),
//				'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
//				']/td[4]', true)
//
//			'modify object Customer No'
//			modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNo'),
//				'xpath', 'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.Index) +
//				']/td[5]', true)

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
					checkVerifyEqualOrMatch(WebUI.verifyMatch(subjectName, CustomerNameArray[(i - 1)], false))
			}
		}
		
		if (subjectName.equalsIgnoreCase(FamilyArray[(f - 1)])) {
			if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
				'click button edit'
				WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

				'if role == testing'
				if (GlobalVariable.Role == 'Testing') {
					'if dupcheck verif == review dan negative check == negative'
					if ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) == 'REVIEW') && ((GlobalVariable.NegativeverifResult[
					GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) || ((((GlobalVariable.DupcheckVerifResult[GlobalVariable.NegativeCustCount]) ==
					'LOCK') && ((GlobalVariable.NegativeverifResult[GlobalVariable.NegativeCustCount]) == 'NEGATIVE')) && WebUI.verifyElementPresent(
						findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
						5, FailureHandling.OPTIONAL))) {
						if ((FamilyNegativeArray[(f - 1)]).equalsIgnoreCase('Yes')) {
							'click negative checkbox index 1'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/checkbox negative'))
						}
					}
				}
				
				if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundSimilardata'),FailureHandling.OPTIONAL),
					'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					ArrayList<WebElement> variableidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecMatch > table > tbody tr'))

					int countidnorow = variableidno.size()

					for (id = 1; id <= countidnorow; id++) {
						'modify object id no family match'
						modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoCustomerMatchSimilarData'),
							'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[4]', true)

						'modify object Customer No'
						modifyFamilyNoObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'),
							'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[1]', true)
						
						'modify object Customer Name'
						modifyFamilyNameObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'),
							'xpath', 'equals', ('//*[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[2]', true)
						
						
						String newFamilyNoValue = WebUI.getText(modifyFamilyNoObject)

						String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

						String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

						String newFamilyName = WebUI.getText(modifyFamilyNameObject)
						
						if (NewIdNoFamilyMatch != null) {
							if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
								'modify object id no family match'
								modifynewSelect = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
									'xpath', 'equals', ('//div[@id="subSecMatch"]/table/tbody/tr[' + id) + ']/td[10]/a',
									true)

								'click select match similar data'
								WebUI.click(modifynewSelect, FailureHandling.OPTIONAL)

								modifyCustomerNo = loopingSubjectCustNo(newFamilyName)
								
								
								if(GlobalVariable.Role=="Testing"){
									'verify match FamilyNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyCustomerNo, newFamilyNoValue, false))
								}
								

								break
							}
						}
					}
				}
				
				if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/label_NoDataFoundAppInProcess'),FailureHandling.OPTIONAL),
					'NO DATA FOUND', false, FailureHandling.OPTIONAL)) {
					ArrayList<WebElement> variablefamilyidno = DriverFactory.getWebDriver().findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

					int countfamilyidrow = variablefamilyidno.size()

					for (id = 1; id <= countfamilyidrow; id++) {
						'modify object id no family match'
						modifyIDNoFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IDNoPersonal'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]', true)

						'modify object applicant No App in process'
						modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]', true)
						
						'modify object applicant No App in process'
						modifyFamilyNameAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[2]', true)

						String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

						String NewIdNoFamilyMatch = WebUI.getText(modifyIDNoFamily, FailureHandling.OPTIONAL)

						String IdNoFamily = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/IdNoCustomer'))

						String newFamilyNameAppInProcess = WebUI.getText(modifyFamilyNameAppInProcess)
						
						if (NewIdNoFamilyMatch != null) {
							if (NewIdNoFamilyMatch.equalsIgnoreCase(IdNoFamily)) {
								String newselectFamily = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a'

								'modify object id no family match'
								modifyselectFamily = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
									'xpath', 'equals', newselectFamily, true)

								'click select application in process'
								WebUI.click(modifyselectFamily, FailureHandling.OPTIONAL)
								
								modifyApplicantNo = loopingSubjectApplicantNo(newFamilyNameAppInProcess)

								if(GlobalVariable.Role=="Testing"){
									'verify match ApplicantNo'
									checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyApplicantNo, newApplicantNoValue.toString(),
										false))
								}
								

								break
							}
						}
					}
				}
				
				if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/subjecttypeheader'),
					5, FailureHandling.OPTIONAL)) {
					if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('New')) {
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'),
							5, FailureHandling.OPTIONAL)) {
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

							modifyApplicantNo = loopingSubjectApplicantNo(subjectName)
							
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyNotMatch(modifyApplicantNo, '', false))
							}
							
						} else if (GlobalVariable.Role == 'Testing') {
							'click button cancel'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

							continue
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							
							newFamilyNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
							
							'click select application in process'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

							
							modifyApplicantNo = loopingSubjectApplicantNo(newFamilyNameAppInProcess)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyApplicantNo, newApplicantNoValue.toString(),
									false))
							}
						   
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							newFamilyName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
							
							'click select match similar data'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

							modifyCustomerNo = loopingSubjectCustNo(newFamilyName)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyCustomerNo, CustomerNoValue.toString(),
									false))
							}
							
						}
					} else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							newFamilyNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
							
							'click select application in process'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

							modifyApplicantNo = loopingSubjectApplicantNo(newFamilyNameAppInProcess)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyApplicantNo, newApplicantNoValue.toString(),
									false))
							}
							
						} else if (GlobalVariable.Role == 'Testing') {
							'click button cancel'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

							continue
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'),
							5, FailureHandling.OPTIONAL)) {
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

							
							modifyApplicantNo = loopingSubjectApplicantNo(subjectName)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyNotMatch(modifyApplicantNo, '', false))
							}
							
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							newFamilyName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
							
							'click select match similar data'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

							
							modifyCustomerNo = loopingSubjectCustNo(newFamilyName)
							
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyCustomerNo, CustomerNoValue.toString(),
									false))
							}
							
						}
					} else if ((FamilyActionArray[(f - 1)]).equalsIgnoreCase('Select SimilarData')) {
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String CustomerNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_CustomerNoSimilarData'))

							newFamilyName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameSimilarData'))
							'click select match similar data'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

							
							modifyCustomerNo = loopingSubjectCustNo(newFamilyName)
							
							
							if(GlobalVariable.Role=="Testing"){
								'verify match CustomerNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyCustomerNo, CustomerNoValue.toString(),
									false))
							}
							
						} else if (GlobalVariable.Role == 'Testing') {
							'click button cancel'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_Cancel'))

							continue
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'),
							5, FailureHandling.OPTIONAL)) {
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_New Customer'))

							modifyApplicantNo = loopingSubjectApplicantNo(subjectName)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyNotMatch(modifyApplicantNo, '', false))
							}
						   
						} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
							5, FailureHandling.OPTIONAL)) {
							String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

							newFamilyNameAppInProcess = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/tr_CustNameAppInProcess'))
							
							'click select application in process'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

							modifyApplicantNo = loopingSubjectApplicantNo(newFamilyNameAppInProcess)
							
							if(GlobalVariable.Role=="Testing"){
								'verify match ApplicantNo'
								checkVerifyEqualOrMatch(WebUI.verifyMatch(modifyApplicantNo, newApplicantNoValue.toString(),
									false))
							}
							
						}
					}
				}
			}
		}
		
		if (f == FamilyArray.size()) {
			break
		}
		
		'+ index negative customer count'
		GlobalVariable.NegativeCustCount++
	}
}

public loopingSubjectApplicantNo(String newFamilyNameAppInProcess){
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
		if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyMatch(newFamilyNameAppInProcess, WebUI.getText(modifySubjectName),false,
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

public loopingSubjectCustNo(String newFamilyName){
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
		if ((WebUI.getText(modifySubjectType) == 'FAMILY') && WebUI.verifyMatch(newFamilyName, WebUI.getText(modifySubjectName),false,
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