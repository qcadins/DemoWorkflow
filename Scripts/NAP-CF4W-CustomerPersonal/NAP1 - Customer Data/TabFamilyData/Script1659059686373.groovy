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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabFamily'
datafileTabFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

countFamily = datafileTabFamily.getColumnNumbers() 

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
            'FAMILY', false, FailureHandling.OPTIONAL))
}

'Loop Multiple family data excel'
for (GlobalVariable.NumofFamily = GlobalVariable.StartIndex; GlobalVariable.NumofFamily <= (countFamily - 1); (GlobalVariable.NumofFamily)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        'count table family pada confins'
        ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#family-tab > app-family-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

		'looping tabel family data pada confins'
        for (int i = 1; i <= variableData.size(); i++) {
            'modify object family name'
            modifyNewFamilyName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

			'Jika copyappedit'
            if (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
				'jika ada familyname yang muncul pada confins'
                if (WebUI.verifyElementPresent(modifyNewFamilyName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					'jika familyname pada tabfamily = familyname confins atau familyname pada tab family lookup = familyname confins'
                    if (WebUI.getText(modifyNewFamilyName).equalsIgnoreCase(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                            19)) || WebUI.getText(modifyNewFamilyName).equalsIgnoreCase(datafileTabFamily.getValue(
                            GlobalVariable.NumofFamily, 16))) {
                        break
                    }
                }
            }
            
			'jika family merupakan index terakhir'
            if (i == variableData.size()) {
                if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_2Family Data'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        'click button add'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Add'))

                        'select customer relation'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 18), false)

                        'input family name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19))

                        'input birth place'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 20))

                        'select idtype'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_IDType'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21), false)

                        'Verify ID Type'
                        if (((datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21) != 'E-KTP') && 
                        (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21) != 'NPWP')) && (datafileTabFamily.getValue(
                            GlobalVariable.NumofFamily, 21) != 'AKTA')) {
							'input Id Expired Date'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id Expired Date'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 22))
                        }
                        
                        'select marital status'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Marital Status'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 23), false)

                        'input mobile phone'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 24))

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 25), false)

						'jika profession tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 27).length() > 1) {
                            'click lookup button proffession name'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

                            'input profession code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 26))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
                        'input employee establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Employee Establishment Date'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 28))

                        'select gender'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Gender'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 29), false)

                        'input birth date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 30))

                        'input id no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 32))

                        'input mother maiden name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mother Maiden Name'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 33))

                        'input email'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Email_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 34))

                        'select nationality'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35), false)

						'jika nationality = foreigner'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35) == 'Foreigner') {
                            'click button lookup country'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

                            'input country code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 36))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                        }
                        
						'jika job position tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 39).length() > 1) {
                            'click button lookup job position'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

                            'input job position code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 38))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
						'jika department aml tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 42).length() > 1) {
                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 41))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                        }
                        
						'jika authority aml tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 44).length() > 1) {
                            'click button lookup authority AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 43))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
						'jika copy address yes'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 46) == 'Yes') {
                            'click button copy'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Copy'))
							
							getDataCust()
                        } //jika copy address no
						else if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 46) == 'No') {
                            'input text address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/textarea_Address'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 47))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RT'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 48))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RW'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 49))

                            'click button zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 50))

                            'input kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kecamatan'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 51))

                            'input kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kelurahan'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 52))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kota'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 53))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_SearchZipcode'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Ownership'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 54), false)
                        }
                    }
                    //Ambil nilai dari confins untuk diverif dengan db lookup
                    //Ambil nilai dari confins untuk verif store db lookup
                } else if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'LookUp') {
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_2Family Data'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        'click button add'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Add'))

                        'select customer relation'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 18), false)

                        'click customer lookup'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Family Legal Name_btn btn-raised btn-primary'))

                        'input customer no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Customer No_custNoId'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 15))

                        'input customer name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Customer Name_custNameId'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 16))

                        'input customer id no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id No_IdNoId'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 17))

                        'click search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                            GlobalVariable.FlagFailed = 1

                            continue
                        }
                        
                        if (GlobalVariable.Role == 'Testing') {
							//getdatacust
                            getDataCust()

                            'call test case Family data verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabFamilyDataVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                        
                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 25), false)

						'jika profession tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 27).length() > 1) {
                            'click lookup button proffession name'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

                            'input profession code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 26))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
                        'input employee establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Employee Establishment Date'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 28))

                        'select nationality'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
                            datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35), false)

						'jika nationality = foreigner'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35) == 'Foreigner') {
                            'click button lookup country'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

                            'input country code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 36))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                        }
                        
						'jika jobposition tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 39).length() > 1) {
                            'click button lookup job position'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

                            'input job position code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 38))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
						'jika depatrment aml tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 42).length() > 1) {
                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 41))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                        }
                        
						'jika authority aml tidak kosong'
                        if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 44).length() > 1) {
                            'click button lookup authority AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                datafileTabFamily.getValue(GlobalVariable.NumofFamily, 43))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
                        if (GlobalVariable.Role == 'Testing') {
							'getdatacust'
                            getDataCust()

							'declare confins data'
                            def confinsdata = GlobalVariable.Confinsdata

							'add customerrelation ke dalam array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                                    'value'))

							'add profession ke array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession'), 
                                    'value'))

							'add job position ke array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_JobPosition'), 
                                    'value'))

							'add establishment date ke array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_EstablishmentDate'), 
                                    'value'))

                            GlobalVariable.Confinsdata = confinsdata
                        }
                    }
                }
                
                'get customer name'
                def custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                    'value', FailureHandling.OPTIONAL)

                'add name to Global variable'
                GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Save'))

                Integer iscompleteMandatory = Integer.parseInt(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
                        4))

                if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
                    'cek alert'
                    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, 
                        '2.TabFamilyData')
                }
                
                if (GlobalVariable.FlagFailed == 0) {
                    'check save process write to excel'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/TableFamilyHeader'), 
                        GlobalVariable.NumofFamily, '2.TabFamilyData')

                    if (iscompleteMandatory == 0) {
                        errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                        'cek validasi'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofFamily, 
                            '2.TabFamilyData')
                    }
                }
                
                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'click button cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))
                } else {
                    if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                        'Write to Excel WARNING'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                    }
                    
                    'customer added +1'
                    (GlobalVariable.CountNumofCustomer)++
                }
                
                if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
                    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                        'call test case Family data store verif'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabFamilyDataStoreDBVerif'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    }
                } else if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'LookUp') {
                    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                        'call test case Family data store verif lookup'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabFamilyDataStoreDBVerif-LookUp'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    }
                }
            }
        }
    }
	else{
		break
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Save and continue'))

'cek alert'
GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '2a.TabFamilyDataMain')

if (GlobalVariable.FlagFailed == 0) {
    'Write to Excel SUCCESS'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2a.TabFamilyDataMain', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def getDataCust() {
	'declare confinsdata'
    def confinsdata = []

	if(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'LookUp'){
		'add customer name to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add birth place to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add id type to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_IDType'),
				'value'))
	
		'add id expired date to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id Expired Date'),
				'value'))
	
		'add marital status to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Marital Status'),
				'value'))
	
		'add mobile phone to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add customer model to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'),
				'value'))
	
		'add gender to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Gender'),
				'value'))
	
		'add birth date to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add id no to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add tax id to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'),
				'value'))
	
		'add mother maiden name to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mother Maiden Name'),
				'value'))
	
		'add email to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Email_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))
	
		'add nationality to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'),
				'value'))
	
		'check if nationality = Local or Foreigner'
		if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'),
			'value').equalsIgnoreCase('LOCAL')) {
			'add country to array'
			confinsdata.add('INDONESIA')
		} else {
			'add country to array'
			confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelCountryLookup'),
					'value'))
		}
	}
    
    
    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelKota'), 
            'value'))

	if(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'LookUp'){
		'add ownership to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Ownership'),
				'value'))
	}else if(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data'){
		
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
	}
    
    GlobalVariable.Confinsdata = confinsdata
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
        GlobalVariable.FlagFailed = 1
    }
}