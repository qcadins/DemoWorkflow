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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By as By

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

def StoreCDCGuarantorPersonalName = ''

def StoreCDCGuarantorCompanyName = ''
	
def GuarantorArray = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 7).split(';')
	
def GuarantorActionArray = findTestData('NAP-CF4W-CustomerCompany/DuplicateChecking').getValue(GlobalVariable.NumofColm, 8).split(';')
			
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
	'equals', ('//*[@id="ListSubjId"]/lib-ucgridview/div/table/tbody/tr[' + GlobalVariable.index) + ']/td[7]/span/span/span/span/span/span/a', true)

'get text subject name'
String subjectName = WebUI.getText(modifySubjectName)

'get text subject type'
String subjectType = WebUI.getText(modifySubjectType)
	
WebDriver driver = DriverFactory.getWebDriver()

if (GuarantorArray.size() > 0) 
{
	for (g = 1; g <= GuarantorArray.size(); g++) 
	{
		if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) 
		{
			if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) 
			{
				'click button edit'
				WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

				ArrayList<WebElement> variableGuarantorPersonalidno = driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

				int countGuarantorPersonalidrow = variableGuarantorPersonalidno.size()

				ArrayList<WebElement> variabletd = driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

				int counttd = variabletd.size()

				if (counttd == 10)
				{
					for (id = 1; id <= countGuarantorPersonalidrow; id++)
					{
						'modify object id no guarantor match'
						modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoPersonal'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[4]',
							true)

						'modify object applicant No App in process'
						modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]',
							true)

						String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

						String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal, FailureHandling.OPTIONAL)

						String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

						if (NewIdNoGuarantorPersonalMatch != null) 
						{
							if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) 
							{
								'modify object select managementshareholder match'
								modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
									'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[10]/a',
									true)

								'click selct'
								WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)

								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

								if (StoreCDCGuarantorPersonalName == '') 
								{
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} 
								else 
								{
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
								
								break
							}
						}
					}
				}
				
				else 
				{
					for (id = 1; id <= countGuarantorPersonalidrow; id++)
					{
						'modify object id no Guarantor match'
						modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/IDNoCompany'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[3]',
							true)

						'modify object applicant No App in process'
						modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
							'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]',
							true)

						String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

						String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany, FailureHandling.OPTIONAL)

						String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/IdNoCustomer'))

						if (NewIdNoGuarantorCompanyMatch != null) 
						{
							if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) 
							{
								'modify object select managementshareholder match'
								modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
									'xpath', 'equals', ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[5]/a',
									true)

								'click select application in process'
								WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)

								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

								if (StoreCDCGuarantorCompanyName == '')
								{
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} 
								else
								{
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
								
								break
							}
						}
					}
				}
				
				if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/DuplicateChecking/subjecttypeheader'),
					5, FailureHandling.OPTIONAL)) 
				{
					if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) 
					{
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 5, FailureHandling.OPTIONAL)) 
						{
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

							'verify match ApplicantNo'
							WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
						} 
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
							5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
							5, FailureHandling.OPTIONAL))
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							}
							
						} 
						
						else 
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10)
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select application in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select applcation in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							}
						}
						
					}
					else if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')) 
					{
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
						5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
							5, FailureHandling.OPTIONAL)) 
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							}
						
						} 
						
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 5, FailureHandling.OPTIONAL)) 
						{
							if (counttd == 10) {
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							}
							
						} 
						
						else  
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) 
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select application in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select application in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							}
						} 
						
					}
					
					else 
					{
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
								5, FailureHandling.OPTIONAL)) 
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) 
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select application in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(), false, FailureHandling.OPTIONAL)
							} 
							else
							{
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select application in process'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
										false, FailureHandling.OPTIONAL)
							}
							
						} 
						
						else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'), 5, FailureHandling.OPTIONAL)) 
						{
							if (counttd == 10) 
							{
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							}
							
						} 
						
						else 
						{
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							} 
							else 
							{
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select match similar data'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
							}
						}
					}
						
					
					'get guarantor name'
					String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

					if (counttd == 5) 
					{
						if (StoreCDCGuarantorCompanyName == '') 
						{
							'store guarantor name'
							StoreCDCGuarantorCompanyName = name
						} 
						else 
						{
							'store guarantor name'
							StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
						}
					} 
					else
					{
						if (StoreCDCGuarantorPersonalName == '') 
						{
							'store guarantor name'
							StoreCDCGuarantorPersonalName = name
						} 
						else 
						{
							'store guarantor name'
							StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
						}
					}
				}
			}
		} 
		
		else 
		{
			'get guarantor name'
			String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

			String GuarantorType = CustomKeywords.'dbconnection.DupCheckVerif.checkCustomerType'(sqlconnection,
				DupcheckAppNo, name)

			if (GuarantorType.equalsIgnoreCase('COMPANY')) {
				if (StoreCDCGuarantorCompanyName == '') 
				{
					'store guarantor name'
					StoreCDCGuarantorCompanyName = name
				} 
				else 
				{
					'store guarantor name'
					StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
				}
			} 
			else 
			{
				if (StoreCDCGuarantorPersonalName == '') 
				{
					'store guarantor name'
					StoreCDCGuarantorPersonalName = name
				} 
				else
				{
					'store guarantor name'
					StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
				}
			}
		}
	}
}
