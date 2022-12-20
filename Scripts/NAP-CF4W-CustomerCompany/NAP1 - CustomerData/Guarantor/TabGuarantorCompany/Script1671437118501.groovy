import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.Select as Select
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

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileGuarantorCompany'
datafileGuarantorCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorCompany')

'get copyapp status dari data file/excel'
copyapp = datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 10)

countGuarantorCompany = datafileGuarantorCompany.getColumnNumbers()

'declare variableData'
ArrayList<String> variableData

for (GlobalVariable.NumofGuarantorCompany = GlobalVariable.StartIndex; GlobalVariable.NumofGuarantorCompany <= (countGuarantorCompany - 1); (GlobalVariable.NumofGuarantorCompany)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > table > tbody tr'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        for (int i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                            18)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileGuarantorCompany.getValue(
                            GlobalVariable.NumofGuarantorCompany, 16))) {
                        break
                    }
                }
            }
            
            if ((i == variableData.size()) || (copyapp == 'No')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'click button Add'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'))

                    'click radio company'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                    if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
                        'select customer relationship'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                            datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 17), false)

                        'input guarantor name'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                            datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19))

                        'select customer type'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                            datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 20), false)

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
                            datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 21), false)

                        if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 23) == 'Yes') {
                            'click button copy'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
							
							'call function get data guarantor company'
							getDataGuarCompany()
                        } else {
                            'input text address'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 24))

                            'input RT'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 25))

                            'input RW'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 26))

                            'click button zipcode'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 27))

                            'input Kecamatan'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 28))

                            'input Kelurahan'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 29))

                            'input kota'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 30))

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

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)
								
                                'Flagfailed +1 karena lookup gagal'
                                (GlobalVariable.FlagFailed)++

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 31), false)
                        }
                    } else if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'LookUp') {
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click guarantor lookup'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                            'input customer no'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer No_custNoId'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 15))

                            'input customer name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer Name_custNameId'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 16))

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

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                'Flagfailed +1 karena lookup gagal'
                                (GlobalVariable.FlagFailed)++

                                continue
                            }
                            
                            'select customer relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 17), false)

                            'check if role testing untuk get data customer digunakan untuk data verif'
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'call function get data guarantor company'
                                getDataGuarCompany()

                                'call test case company data verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorCompanyDataVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                            
                            if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 20).length() > 1) {
                                'select customer type'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                    datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 20), false)
                            }
                        }
                    }
                    
                    'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                    if ((GlobalVariable.RoleCompany == 'Testing') && (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        13) == 'LookUp')) {
                        'call function get data guarantor company'
                        getDataGuarCompany()

                        'add relationship to array'
                        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                'value'))
                    }
                    
                    'get customer name'
                    custname = WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                        'value', FailureHandling.OPTIONAL)

                    'add name to Global variable'
                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                    'Click save'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                    if ((Integer.parseInt(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 4)) == 
                    0) && (GlobalVariable.FlagFailed == 0)) {
                        'Check alert'
                        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorCompany, 
                            '3b.TabGuarantorDataCompany')
                    }
                    
                    'verify flagfailed  lookup == 0'
                    if (GlobalVariable.FlagFailed == 0) {
                        'Check save Process write to excel'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileGuarantorCompany.getValue(
                                    GlobalVariable.NumofGuarantorCompany, 4)), findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                            GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')

                        'customer added +1'
                        (GlobalVariable.CountNumofCustomer)++

                        if (Integer.parseInt(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 4)) == 
                        0) {
                            'Check error validasi'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                                GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')
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
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusWarning)
                        }
                    }
                    
                    if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            'call test case guarantor data company store verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataCompanyStoreDBVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    } else if (datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'LookUp') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            'call test case guarantor data company store verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataCompanyStoreDBVerif-LookUp'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    }
                    
                    break
                }
            }
        }
    }else{
	break
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button save and continue'
    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))
}

WebUI.delay(10)

def getDataGuarCompany() {
    'declare array for confins data'
    def confinsdata = []

	if(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany,13) == 'LookUp'){
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

    if(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany,13) == 'LookUp'){
	    'add company type to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
	            'value'))

	    'add customer model to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
	            'value'))
	
	    'add ownership to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_Ownership'), 
	            'value'))
	}else if(datafileGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data'){
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
	}
	
    GlobalVariable.Confinsdata = confinsdata
}

def verifyInputError(int flagWarning) {
    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

        flagWarning++
    }
}

