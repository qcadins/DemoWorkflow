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

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathGuarantorCompany'
excelPathGuarantorCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany'

'declare arraylist custnamefaileddelete'
ArrayList<String> custnamefaileddelete = new ArrayList<>()

'count table guarantor row di confins'
ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

for (int i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor type'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'Loop Multiple guarantor data'
    for (GlobalVariable.NumofGuarantorCompany = GlobalVariable.StartIndex; GlobalVariable.NumofGuarantorCompany <= (findTestData(excelPathGuarantorCompany).getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorCompany)++) {
        if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Company')) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 
                            18)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData(excelPathGuarantorCompany).getValue(
                            GlobalVariable.NumofGuarantorCompany, 16))) {
                        'modify object button edit'
                        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                            i) + ']/td[5]/span/span[1]/span/a/i', true)

                        'click button edit'
                        WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                        if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
                            'click radio personal'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                            'click radio company'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                            'select customer relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 17), false)

                            'input guarantor name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                                findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 18))

                            'input tax id'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                                findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 19))

                            'select customer type'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 20), false)

                            'select customer model'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
                                findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 21), false)

                            if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 23) == 'Yes') {
                                'click button copy'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                            } else {
                                'input text address'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 24))

                                'input RT'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 25))

                                'input RW'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 26))

                                'click button zipcode'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Zipcode_btn btn-raised btn-primary'))

                                'input zipcode'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 27))

                                'input Kecamatan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 28))

                                'input Kelurahan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 29))

                                'input kota'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 30))

                                'click search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))
	
									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                    'Flagfailed +1 karena lookup gagal'
                                    (GlobalVariable.FlagFailed)++

                                    continue
                                }
                                
                                'select ownership'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 31), false)
                            }
                        } else if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'LookUp') {
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click radio personal'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                'click radio company'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                                'click guarantor lookup'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                'input customer no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer No_custNoId'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 15))

                                'input customer name'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer Name_custNameId'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 16))

                                'click search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                    'Flagfailed +1 karena lookup gagal'
                                    (GlobalVariable.FlagFailed)++

                                    continue
                                }
                                
                                'select customer relationship'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                    findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 17), false)

                                'check if role testing untuk get data customer digunakan untuk data verif'
                                if (GlobalVariable.RoleCompany == 'Testing') {
                                    'call function get data guarantor company'
                                    getDataGuarCompany()

                                    'call test case company data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyDataVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                                
                                'cek apakah ada value customer type pada excel'
                                if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 20).length() > 
                                1) {
                                    'select customer type'
                                    WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                        findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 20), 
                                        false)
                                }
                            }
                        }
                        
                        'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                        if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 
                            13) == 'LookUp')) {
                            'call function get data guarantor company'
                            getDataGuarCompany()

                            'add relationship to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                    'value'))
                        }
                        
                        'Click save'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                        if ((Integer.parseInt(findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 
                                4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
                            'Check alert'
                            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorCompany, 
                                '3b.TabGuarantorDataCompany')
                        }
                        
                        'verify flagfailed  lookup == 0 '
                        if (GlobalVariable.FlagFailed == 0) {
                            'Check save Process write to excel'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(excelPathGuarantorCompany).getValue(
                                        GlobalVariable.NumofGuarantorCompany, 4)), findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                                GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')

                            'customer added +1'
                            (GlobalVariable.CountNumofCustomer)++

                            if (Integer.parseInt(findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 
                                    4)) == 0) {
                                'Check error validasi'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                                    GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')
                            }
                        }
                        
                        'verify button cancel present'
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

							GlobalVariable.IsDataCancel = 1
							
                            'customer added -1'
                            (GlobalVariable.CountNumofCustomer)--
                        } else {
                            if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusWarning)
                            }
							
							
							if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
								if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
								'Yes')) {
									'call test case guarantor data company store verif'
									WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataCompanyStoreDBVerif'),
										[:], FailureHandling.CONTINUE_ON_FAILURE)
								}
							} else if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'LookUp') {
								if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
								'Yes')) {
									'call test case guarantor data company store verif'
									WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataCompanyStoreDBVerif-LookUp'),
										[:], FailureHandling.CONTINUE_ON_FAILURE)
								}
							}
                        }
						
                        break
                    } else {
                        'check if next colm != current appno'
                        if (findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.NumofGuarantorCompany + 1, 12) != findTestData(excelPathCustomerCompany).getValue(
                            GlobalVariable.NumofColm, 13)) {
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

                            if (i == variableData.size()) {
                                if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                     'add cust name failed kedalam array'
                                    custnamefaileddelete.add(CustNameBefore)
                                }
                            } else {
                                'get cust name sebelum delete'
                                CustNameAfter = WebUI.getText(modifyNewGuarantorName)

                                if (WebUI.verifyMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
									'add cust name failed kedalam array'
									custnamefaileddelete.add(CustNameBefore)
                                }
                            }
                            
                            'count ulang table pada confins'
                            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
							
                            i--
                        }
                    }
                } 
            }
        }else {
              break
        }
    }
}

if (custnamefaileddelete.size() > 0) {
	
	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, findTestData(excelPathGuarantorCompany).getValue(GlobalVariable.StartIndex, 2) + ';' + GlobalVariable.ReasonFailedDelete + custnamefaileddelete)
	
    (GlobalVariable.FlagWarning)++
}

WebUI.delay(5)

'call test case tab guarantor company'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompany'), [:], FailureHandling.CONTINUE_ON_FAILURE)

def getDataGuarCompany() {
    'declare array for confins data'
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

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

    'add company type to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
            'value'))

    'add customer model to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
            'value'))

    'add ownership to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_Ownership'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

