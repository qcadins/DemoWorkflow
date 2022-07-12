import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By as By

public class dupcheck {
	
	@Keyword
	public Dupcheck(def GuarantorArray, String subjectName, Object modifyButtonEdit, id, Object modifyIDNoGuarantorPersonal, Object modifyApplicantNoAppInProcess, Object modifyselectGuarantorPersonal, Object modifyApplicantNo, Object modifySubjectName, def StoreCDCGuarantorPersonalName ) {
	if (GuarantorArray.size() > 0) {
		for (g = 1; g <= GuarantorArray.size(); g++) {
			if (subjectName.equalsIgnoreCase(GuarantorArray[(g - 1)])) {
				if (WebUI.verifyElementPresent(modifyButtonEdit, 5, FailureHandling.OPTIONAL)) {
					'click button edit'
					WebUI.click(modifyButtonEdit, FailureHandling.OPTIONAL)

					WebDriver Driver = DriverFactory.getWebDriver()
					
					ArrayList<WebElement> variableGuarantorPersonalidno = Driver.findElements(By.cssSelector('#subSecAppProcess > table > tbody tr'))

					int countGuarantorPersonalidrow = variableGuarantorPersonalidno.size()


					ArrayList<WebElement> variabletd = Driver.findElements(By.cssSelector('#subSecAppProcess > table > thead th'))

					int counttd = variabletd.size()

					if (counttd == 10) {
						for (id = 1; id <= countGuarantorPersonalidrow; id++) {
							String newIDNoGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
							']/td[4]'

							'modify object id no guarantor match'
							modifyIDNoGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoPersonal'),
								'xpath', 'equals', newIDNoGuarantorPersonal, true)

							String newApplicantNoObject = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) + ']/td[1]'

							'modify object applicant No App in process'
							modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
								'xpath', 'equals', newApplicantNoObject, true)

							String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

							String NewIdNoGuarantorPersonalMatch = WebUI.getText(modifyIDNoGuarantorPersonal, FailureHandling.OPTIONAL)

							String IdNoGuarantorPersonal = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

							if (NewIdNoGuarantorPersonalMatch != null) {
								if (NewIdNoGuarantorPersonalMatch.equalsIgnoreCase(IdNoGuarantorPersonal)) {
									String newselectGuarantorPersonal = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' +
									id) + ']/td[10]/a'

									'modify object select family match'
									modifyselectGuarantorPersonal = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
										'xpath', 'equals', newselectGuarantorPersonal, true)

									'click selct'
									WebUI.click(modifyselectGuarantorPersonal, FailureHandling.OPTIONAL)

									'verify match ApplicantNo'
									WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
										false, FailureHandling.OPTIONAL)

									'get guarantor name'
									String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

									if (StoreCDCGuarantorPersonalName == '') {
										'store guarantor name'
										StoreCDCGuarantorPersonalName = name
									} else {
										'store guarantor name'
										StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
									}
									
									break
								}
							}
						}
					} else if (counttd == 5) {
						for (id = 1; id <= countGuarantorPersonalidrow; id++) {
							String newIDNoGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
							']/td[3]'

							'modify object id no Guarantor match'
							modifyIDNoGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IDNoCompany'),
								'xpath', 'equals', newIDNoGuarantorCompany, true)

							String newApplicantNoAppInproccess = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' + id) +
							']/td[1]'

							'modify object applicant No App in process'
							modifyApplicantNoAppInProcess = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'),
								'xpath', 'equals', newApplicantNoAppInproccess, true)

							String newApplicantNoValue = WebUI.getText(modifyApplicantNoAppInProcess, FailureHandling.OPTIONAL)

							String NewIdNoGuarantorCompanyMatch = WebUI.getText(modifyIDNoGuarantorCompany, FailureHandling.OPTIONAL)

							String IdNoGuarantorCompany = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/IdNoCustomer'))

							if (NewIdNoGuarantorCompanyMatch != null) {
								if (NewIdNoGuarantorCompanyMatch.equalsIgnoreCase(IdNoGuarantorCompany)) {
									String newselectGuarantorCompany = ('//*[@id="subSecAppProcess"]/table/tbody/tr[' +
									id) + ']/td[5]/a'

									'modify object select family match'
									modifyselectGuarantorCompany = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'),
										'xpath', 'equals', newselectGuarantorCompany, true)

									'click select'
									WebUI.click(modifyselectGuarantorCompany, FailureHandling.OPTIONAL)

									'verify match ApplicantNo'
									WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
										false, FailureHandling.OPTIONAL)

									'get guarantor name'
									String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

									if (StoreCDCGuarantorCompanyName == '') {
										'store guarantor name'
										StoreCDCGuarantorCompanyName = name
									} else {
										'store guarantor name'
										StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
									}
									
									break
								}
							}
						}
					}
					
					if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/subjecttypeheader'),
						5, FailureHandling.OPTIONAL)) {
						if ((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('New')) {
							if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),
								5, FailureHandling.OPTIONAL)) {
							if(counttd == 10){
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							}else if(counttd == 5){
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

							'verify match ApplicantNo'
							WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							
							'get guarantor name'
							String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

							if (StoreCDCGuarantorCompanyName == '') {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = name
							} else {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
							}
							}
							}else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								 String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
							}else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
							}
						} else if((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select SimilarData')){
							if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								 String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
							} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),
								5, FailureHandling.OPTIONAL)) {
								if(counttd == 10){
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							}else if(counttd == 5){
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

							'verify match ApplicantNo'
							WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							
							'get guarantor name'
							String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

							if (StoreCDCGuarantorCompanyName == '') {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = name
							} else {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
							}
							}
							} else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
						} else if((GuarantorActionArray[(g - 1)]).equalsIgnoreCase('Select ApplicationInProcess')){
							 if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectApplicationInProcessPersonal'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								String newApplicantNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_ApplicantNoApplicationInProcess'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_selectApplicationInprocessCompany'))

								'verify match ApplicantNo'
								WebUI.verifyMatch(WebUI.getText(modifyApplicantNo).toString(), newApplicantNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
							} else if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'),
								5, FailureHandling.OPTIONAL)) {
							if(counttd == 10){
								'click button new customer'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

								'verify match ApplicantNo'
								WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							}else if(counttd == 5){
							'click button new customer'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_New Customer'))

							'verify match ApplicantNo'
							WebUI.verifyNotMatch(WebUI.getText(modifyApplicantNo), '', false, FailureHandling.OPTIONAL)
							
							'get guarantor name'
							String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

							if (StoreCDCGuarantorCompanyName == '') {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = name
							} else {
								'store guarantor name'
								StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
							}
							}
							} else if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'),
								5, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'),
								5, FailureHandling.OPTIONAL)){
							
							'verify tabel head == 10/5 untuk menentukan object select 10 untuk personal dan 5 untuk company'
							if (counttd == 10) {
								String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataPersonal'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorPersonalName == '') {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorPersonalName = ((StoreCDCGuarantorPersonalName + ';') + name)
								}
							} else if (counttd == 5) {
								 String newCustomerNoNoValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/Tr_CustomerNoSimilarData'))

								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateChecking/button_SelectMatchSimilarDataCompany'))

								'verify match CustomerNo'
								WebUI.verifyMatch(WebUI.getText(modifyCustomerNo).toString(), newCustomerNoNoValue.toString(),
									false, FailureHandling.OPTIONAL)
								
								'get guarantor name'
								String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)
		
								if (StoreCDCGuarantorCompanyName == '') {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = name
								} else {
									'store guarantor name'
									StoreCDCGuarantorCompanyName = ((StoreCDCGuarantorCompanyName + ';') + name)
								}
							}
							}
						}
					}
				} else {
					'get guarantor name'
					String name = WebUI.getText(modifySubjectName, FailureHandling.OPTIONAL)

					if (g == 1) {
						'store guarantor name'
						StoreCDCGuarantorPersonalName = name
					} else {
						'store guarantor name'
						StoreCDCGuarantorPersonalName = (StoreCDCGuarantorPersonalName + ';' + name)
					}
				}
			}
		}
	}
}
}
}