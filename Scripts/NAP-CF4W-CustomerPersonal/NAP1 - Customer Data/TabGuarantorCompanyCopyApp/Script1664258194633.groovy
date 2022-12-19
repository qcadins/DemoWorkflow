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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.support.ui.Select as Select

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabGuarantorCompany'
datafileTabGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare custnamefaileddelete'
ArrayList<String> custnamefaileddelete = new ArrayList<String>()

'declare variabledata'
ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

'looping tabel guarantor'
for (int i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor type'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'Loop Multiple guarantor company data excel'
    for (GlobalVariable.NumofGuarantorCompany = GlobalVariable.StartIndex; GlobalVariable.NumofGuarantorCompany <= datafileTabGuarantorCompany.getColumnNumbers() - 1; (GlobalVariable.NumofGuarantorCompany)++) {
        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerPersonal.getValue(
            GlobalVariable.NumofColm, 13)) {
			'Jika ada guarantor name pada confins'
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
				'jika guarantor type pada confins bernilai company'
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Company')) {
					'Jika guarantor name confins = guarantor name excel / excel lookup'
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileTabGuarantorCompany.getValue(
                            GlobalVariable.NumofGuarantorCompany, 18)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(
                        datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 16))) {
                        
						'modify object button edit'
                        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                            i) + ']/td[5]/span/span[1]/span/a/i', true)

                        'click button edit'
                        WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 
                        'Input Data') {
                            'click radio personal'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                            'click radio company'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                            'select customer relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    17), false)

                            'input guarantor name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    18))

                            'input tax id'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    19))

                            'select customer type'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    20), false)

                            'select customer model'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    21), false)

							'jika copy address yes'
                            if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                23) == 'Yes') {
                                'click button copy'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
								
								getDataGuarCompany()
                            } //jika copy address no
							else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                23) == 'No') {
                                'input text address'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        24))

                                'input RT'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        25))

                                'input RW'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        26))

                                'click button zipcode'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Zipcode_btn btn-raised btn-primary'))

                                'input zipcode'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        27))

                                'input Kecamatan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        28))

                                'input Kelurahan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        29))

                                'input kota'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        30))

                                'click search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)
									
                                    GlobalVariable.FlagFailed = 1

                                    continue
                                }
                                
                                'select ownership'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        31), false)
                            }
                        } else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                            13) == 'LookUp') {
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click radio company'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                                'click guarantor lookup'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                'input customer no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer No_custNoId'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        15))

                                'input customer name'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer Name_custNameId'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        16))

                                'click search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                    GlobalVariable.FlagFailed = 1

                                    continue
                                }
                                
                                if (GlobalVariable.Role == 'Testing') {
                                    'call function get data guarantor company'
                                    getDataGuarCompany()

                                    'call test case company data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorCompanyDataVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                                
                                'select customer relationship'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        17), false)

								'jika customer type tidak kosong'
                                if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    20).length() > 1) {
                                    'select customer type'
                                    WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                        datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                            20), false)
                                }
                            }
                        }
                        
                        'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                        if ((GlobalVariable.Role == 'Testing') && (datafileTabGuarantorCompany.getValue(
                            GlobalVariable.NumofGuarantorCompany, 13) == 'LookUp')) {
                            'call function get data guarantor company'
                            getDataGuarCompany()

                            'add relationship to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                    'value'))
                        }
                        
                        'Click save'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                        Integer iscompleteMandatory = Integer.parseInt(datafileTabGuarantorCompany.getValue(
                                GlobalVariable.NumofGuarantorCompany, 4))

                        if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
                            'cek alert'
                            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorCompany, 
                                '3b.TabGuarantorDataCompany')
                        }
                        
                        if (GlobalVariable.FlagFailed == 0) {
                            'check save process write to excel'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject(
                                    'Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableGuarantorHeader'), 
                                GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')

                            'customer added +1'
                            (GlobalVariable.CountNumofCustomer)++

                            if (iscompleteMandatory == 0) {
                                errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                                'cek validasi'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantorCompany, 
                                    '3b.TabGuarantorDataCompany')
                            }
                        }
                        
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

                            'customer added -1'
                            (GlobalVariable.CountNumofCustomer)--
                        } else {
                            if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
								'write to excel status warning'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusWarning)
                            }
                        }
                        
                        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 
                        'Input Data') {
                            if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                                'call test case company data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataCompanyStoreDBVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        } else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                            13) == 'LookUp') {
                            if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                                'call test case guarantor data company store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataCompanyStoreDBVerif-LookUp'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        }
                        
                        break
                    } else {
						'jika appno excel guarantorcompany tidak sama dengan appno customermaindata excel'
                        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany + 
                            1, 12) != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13)) {
                            'modify object button delete'
                            modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                                i) + ']/td[5]/span/span[2]/span/a/i', true)

                            'get cust name sebelum delete'
                            CustNameBefore = WebUI.getText(modifyNewGuarantorName)

                            'click button Delete'
                            WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                            'accept alert'
                            WebUI.acceptAlert()

							'Jika guarantor merupakan index terakhir'
                            if (i == variableData.size()) {
								'jika guarantor name tidak muncul pada confins'
                                if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                	'add cust name failed kedalam array'
                                	custnamefaileddelete.add(CustNameBefore)                                    
                                }
                            } else {
                                'get cust name sebelum delete'
                                CustNameAfter = WebUI.getText(modifyNewGuarantorName)

								'jika guarantor name after delete tidak sama dengan before delete'
                                if (WebUI.verifyMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
                                	'add cust name failed kedalam array'
                                	custnamefaileddelete.add(CustNameBefore)
                                }
                            }
                            
							'count ulang table guarantor setelah delete'
                            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
							
                            i--
                        }
                    }
                }
            } 
        }
		else {
			break
		}	
    }
}

'Jika ada delete guarantor gagal'
if (custnamefaileddelete.size() > 0) {

	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + custnamefaileddelete)
	
    (GlobalVariable.FlagWarning)++
}

def getDataGuarCompany() {
    'declare array for confins data'
    def confinsdata = []

	if(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany,
		13) == 'LookUp'){
	    
		'add customer name to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
	            'value'))
	
	    'add tax id to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
	            'value'))
	}

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKota'), 
            'value'))
	
	if(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany,
		13) == 'LookUp'){

	    'add company type to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
	            'value'))
	
	    'add customer model to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
	            'value'))
	
	    'add ownership to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_Ownership'), 
	            'value'))
	}
	else if(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany,
			13) == 'Input Data'){
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
	}

    GlobalVariable.Confinsdata = confinsdata
}

