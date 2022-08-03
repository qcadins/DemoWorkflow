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

def StoreCDCManagementShareholderPersonalName = ''

	def StoreCDCManagementShareholderCompanyName = ''
	
	def ManagementShareholderArray = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 5).split(';')
	
			def ManagementShareholderActionArray = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 6).split(';')
			
			String DupcheckAppNo = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 2)

	
	'modify object subjectname'
	modifySubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectName'), 'xpath', 'equals',
		('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[2]', true)

	'modify object subjecttype'
	modifySubjectType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/SubjectType'), 'xpath', 'equals',
		('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[3]', true)

	'modify object Applicant No'
	modifyApplicantNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNo'), 'xpath', 'equals',
		('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[4]', true)

	'modify object Customer No'
	modifyCustomerNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNo'), 'xpath', 'equals',
		('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[5]', true)

	'modify object edit icon'
	modifyButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/i_-_font-medium-3 ft-edit-2'), 'xpath',
		'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[7]/span/span/span/span/span/span/a',
		true)

	'get text subject name'
	String subjectName = WebUI.getText(modifySubjectName)

	'get text subject type'
	String subjectType = WebUI.getText(modifySubjectType)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	if (ManagementShareholderArray.size() > 0) {
		for (m = 1; m <= ManagementShareholderArray.size(); m++) {
			if (subjectName.equalsIgnoreCase(ManagementShareholderArray[(m - 1)])) {
	if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
	'click button edit'
	WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

	ArrayList<WebElement> variableManagementShareholderPersonalidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

	int countManagementShareholderPersonalidrow = variableManagementShareholderPersonalidno.size()

	ArrayList<WebElement> variabletd = driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

	int counttd = variabletd.size()

	if (counttd == 10) {
		for (id = 1; id <= countManagementShareholderPersonalidrow; id++) {
			String newIDNoManagementShareholderPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
			']/td[4]'

			'modify object id no ManagementShareholder match'
			modifyIDNoManagementShareholderPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'),
				'xpath', 'equals', newIDNoManagementShareholderPersonal, true)

			String newApplicantNoObject = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]'

			'modify object applicant No App in process'
			modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
				'xpath', 'equals', newApplicantNoObject, true)

			String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

			String NewIdNoManagementShareholderPersonalMatch = WebUI.getText(modifyIDNoManagementShareholderPersonal, FailureHandling.OPTIONAL)

			String IdNoManagementShareholderPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

			if (NewIdNoManagementShareholderPersonalMatch != null) {
				if (NewIdNoManagementShareholderPersonalMatch.equalsIgnoreCase(IdNoManagementShareholderPersonal)) {
					String newselectManagementShareholderPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' +
					id) + ']/td[10]/a'

					'modify object select managementshareholder match'
					modifyselectManagementShareholderPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
						'xpath', 'equals', newselectManagementShareholderPersonal, true)

					'click selct'
					WebUI.click(modifyselectManagementShareholderPersonal, FailureHandling.OPTIONAL)

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)

					'get ManagementShareholder name'
					String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

					if (StoreCDCManagementShareholderPersonalName == '') {
						'store ManagementShareholder name'
						StoreCDCManagementShareholderPersonalName = name
					} else {
						'store ManagementShareholder name'
						StoreCDCManagementShareholderPersonalName = ((StoreCDCManagementShareholderPersonalName + ';') + name)
					}
					
					break
				}
			}
		}
	} else if (counttd == 5) {
		for (id = 1; id <= countManagementShareholderPersonalidrow; id++) {
			String newIDNoManagementShareholderCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
			']/td[3]'

			'modify object id no ManagementShareholder match'
			modifyIDNoManagementShareholderCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCompany'),
				'xpath', 'equals', newIDNoManagementShareholderCompany, true)

			String newApplicantNoAppInproccess = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
			']/td[1]'

			'modify object applicant No App in process'
			modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
				'xpath', 'equals', newApplicantNoAppInproccess, true)

			String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

			String NewIdNoManagementShareholderCompanyMatch = WebUI.getText(modifyIDNoManagementShareholderCompany, FailureHandling.OPTIONAL)

			String IdNoManagementShareholderCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

			if (NewIdNoManagementShareholderCompanyMatch != null) {
				if (NewIdNoManagementShareholderCompanyMatch.equalsIgnoreCase(IdNoManagementShareholderCompany)) {
					String newselectManagementShareholderCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' +
					id) + ']/td[5]/a'

					'modify object select managementshareholder match'
					modifyselectManagementShareholderCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
						'xpath', 'equals', newselectManagementShareholderCompany, true)

					'click select application in process'
					WebUI.click(modifyselectManagementShareholderCompany, FailureHandling.OPTIONAL)

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)

					'get ManagementShareholder name'
					String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

					if (StoreCDCManagementShareholderCompanyName == '') {
						'store ManagementShareholder name'
						StoreCDCManagementShareholderCompanyName = name
					} else {
						'store ManagementShareholder name'
						StoreCDCManagementShareholderCompanyName = ((StoreCDCManagementShareholderCompanyName + ';') + name)
					}
					
					break
				}
			}
		}
	}
	
	if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'),
		5, FailureHandling.OPTIONAL)) {
		if ((ManagementShareholderArray[(m - 1)]).equalsIgnoreCase('New')) {
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'),
				5, FailureHandling.OPTIONAL)) {
				'click button new customer'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

				'verify match ApplicantNo'
				WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
			} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
				5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
				5, FailureHandling.OPTIONAL)) {
				'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
				if (counttd == 10) {
					String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

					'click select match similar data'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

					'verify match CustomerNo'
					WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				} else if (counttd == 5) {
					String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

					'click select match similar data'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

					'verify match CustomerNo'
					WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				}
			} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
				5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
				5, FailureHandling.OPTIONAL)) {
				'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
				if (counttd == 10) {
					String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

					'click select application in process'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				} else if (counttd == 5) {
					String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

					'click select applcation in process'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				}
			}
		} else if ((ManagementShareholderArray[(m - 1)]).equalsIgnoreCase('Select SimilarData')) {
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
				5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
				5, FailureHandling.OPTIONAL)) {
				'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
				if (counttd == 10) {
					String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

					'click select match similar data'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

					'verify match CustomerNo'
					WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				} else if (counttd == 5) {
					String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

					'click select match similar data'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

					'verify match CustomerNo'
					WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				}
			} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'),
				5, FailureHandling.OPTIONAL)) {
				if (counttd == 10) {
					'click button new customer'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

					'verify match ApplicantNo'
					WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
				} else if (counttd == 5) {
					'click button new customer'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

					'verify match ApplicantNo'
					WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
				}
			} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
				5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
				5, FailureHandling.OPTIONAL)) {
				'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
				if (counttd == 10) {
					String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

					'click select application in process'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				} else if (counttd == 5) {
					String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

					'click select application in process'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

					'verify match ApplicantNo'
					WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
						false, FailureHandling.OPTIONAL)
				}
			}
		}else if ((ManagementShareholderArray[(m - 1)]).equalsIgnoreCase('Select ApplicationInProcess')) {
				if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
					5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
					5, FailureHandling.OPTIONAL)) {
					'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
					if (counttd == 10) {
						String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

						'click select application in process'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

						'verify match ApplicantNo'
						WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
							false, FailureHandling.OPTIONAL)
					} else if (counttd == 5) {
						String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

						'click select application in process'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

						'verify match ApplicantNo'
						WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
							false, FailureHandling.OPTIONAL)
					}
				} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'),
					5, FailureHandling.OPTIONAL)) {
					if (counttd == 10) {
						'click button new customer'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

						'verify match ApplicantNo'
						WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
					} else if (counttd == 5) {
						'click button new customer'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

						'verify match ApplicantNo'
						WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
					}
				} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
					5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
					5, FailureHandling.OPTIONAL)) {
					'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
					if (counttd == 10) {
						String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

						'click select match similar data'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

						'verify match CustomerNo'
						WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
							false, FailureHandling.OPTIONAL)
					} else if (counttd == 5) {
						String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

						'click select match similar data'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

						'verify match CustomerNo'
						WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
							false, FailureHandling.OPTIONAL)
					}
				}
			}
			
			'get ManagementShareholder name'
			String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

			if (counttd == 5) {
				if (StoreCDCManagementShareholderCompanyName == '') {
					'store ManagementShareholder name'
					StoreCDCManagementShareholderCompanyName = name
				} else {
					'store ManagementShareholder name'
					StoreCDCManagementShareholderCompanyName = ((StoreCDCManagementShareholderCompanyName + ';') + name)
				}
			} else if (counttd == 10) {
				if (StoreCDCManagementShareholderPersonalName == '') {
					'store ManagementShareholder name'
					StoreCDCManagementShareholderPersonalName = name
				} else {
					'store ManagementShareholder name'
					StoreCDCManagementShareholderPersonalName = ((StoreCDCManagementShareholderPersonalName + ';') + name)
				}
			}
		}
	}
} else {
	'get ManagementShareholder name'
	String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

	String ManagementShareholderType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnection,
		DupcheckAppNo, name)

	if (ManagementShareholderType.equalsIgnoreCase('COMPANY')) {
		if (StoreCDCManagementShareholderCompanyName == '') {
			'store ManagementShareholder name'
			StoreCDCManagementShareholderCompanyName = name
		} else {
			'store ManagementShareholder name'
			StoreCDCManagementShareholderCompanyName = ((StoreCDCManagementShareholderCompanyName + ';') + name)
		}
	} else if (ManagementShareholderType.equalsIgnoreCase('PERSONAL')) {
		if (StoreCDCManagementShareholderPersonalName == '') {
			'store ManagementShareholder name'
			StoreCDCManagementShareholderPersonalName = name
		} else {
			'store ManagementShareholder name'
			StoreCDCManagementShareholderPersonalName = ((StoreCDCManagementShareholderPersonalName + ';') + name)
		}
	}
}
			}
		}
