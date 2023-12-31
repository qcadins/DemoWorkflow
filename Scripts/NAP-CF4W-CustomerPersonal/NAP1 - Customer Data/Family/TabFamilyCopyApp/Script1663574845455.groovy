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
import groovy.sql.Sql as Sql
import org.openqa.selenium.support.ui.Select as Select

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1 DataFile_NAP_"+ GlobalVariable.LOB +".xlsx")

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathTabFamily'
excelPathTabFamily = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabFamilyData'

'Klik tab family'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP1-CustomerData/buttonTabFamily'))

ArrayList<String> custnamefaileddelete = new ArrayList<String>()

ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#family-tab > app-family-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

checkFamilyDataEditNAP(variableData)

for (int i = 1; i <= variableData.size(); i++) {
    'modify object family name'
    modifyNewFamilyName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'Loop Multiple family data'
    for (GlobalVariable.NumofFamily = GlobalVariable.StartIndex; GlobalVariable.NumofFamily <= (findTestData(excelPathTabFamily).getColumnNumbers() - 1); (GlobalVariable.NumofFamily)++) {
        if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 12) == findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 13)) {
			'jika familyname ada pada confins'
            if (WebUI.verifyElementPresent(modifyNewFamilyName, 5, FailureHandling.OPTIONAL)) {
				'jika familyname confins = familyname excel atau familyname confins = familyname excel lookup'
                if (WebUI.getText(modifyNewFamilyName).equalsIgnoreCase(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 
                        19)) || WebUI.getText(modifyNewFamilyName).equalsIgnoreCase(findTestData(excelPathTabFamily).getValue(
                        GlobalVariable.NumofFamily, 16))) {
                    'modify object button edit'
                    modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                        'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                        i) + ']/td[6]/span/span[1]/span/span/span/span/span/a/i', true)

					'jika ada button edit'
                    if (WebUI.verifyElementPresent(modifyNewButtonEdit, 5, FailureHandling.OPTIONAL)) {
                        'click button edit'
                        WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                        if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_2Family Data'), 
                                5, FailureHandling.OPTIONAL)) {
                                
								inputData()
								
								'jika proefession tidak kosong'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 27).length() > 
                                1) {
                                    'click lookup button proffession name'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

                                    'input profession code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 26))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
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
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 28))

                                'select gender'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Gender'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 29), false)

                                'input birth date'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 30))

                                'input id no'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 31))

                                'input tax id'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 32))

                                'input mother maiden name'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mother Maiden Name'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 33))

                                'input email'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Email_form-control ng-untouched ng-pristine ng-invalid'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 34))

                                'select nationality'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 35), false)

                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 35) == 'Foreigner') {
                                    'click button lookup country'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

                                    'input country code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 36))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        closeLookup()

                                        continue
                                    }
                                }
                                'jika job position tidak kosong'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 39).length() > 1) {
                                    'click button lookup job position'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

                                    'input job position code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 38))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
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
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 42).length() > 
                                1) {
                                    'click button lookup department AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                    'input department AML Code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 41))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        closeLookup()

                                        continue
                                    }
                                }
                                
								'jika authority aml tidak kosong'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 44).length() > 
                                1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML Code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 43))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
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
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 46) == 'Yes') {
                                    'click button copy'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Copy'))
									
									getDataCust()
                                } else if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 46) == 
                                'No') {
                                    'input text address'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/textarea_Address'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 47))

                                    'input RT'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RT'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 48))

                                    'input RW'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_RW'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 49))

                                    'click button zipcode'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Zipcode_btn btn-raised btn-primary'))

                                    'input zipcode'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 50))

                                    'input kecamatan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kecamatan'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 51))

                                    'input kelurahan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kelurahan'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 52))

                                    'input kota'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Kota'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 53))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_SearchZipcode'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        closeLookup()

                                        continue
                                    }
                                    
                                    'select ownership'
                                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Ownership'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 54), false)
                                }
                            }
                        } else if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'LookUp') {
                            //jika ada button family data pada confins
							if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_2Family Data'), 
                                5, FailureHandling.OPTIONAL)) {
                                LookupData()

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                    10, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                } else {
                                    closeLookup()

                                    continue
                                }
                                
                                if (GlobalVariable.Role == 'Testing') {
									//getdatacust
                                    getDataCust()

                                    'call test case Family data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyDataVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                                
                                'select customer relation'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 18), false)

                                'select customer model'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 25), false)

								//jika profession name tidak kosong
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 27).length() > 
                                1) {
                                    'click lookup button proffession name'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

                                    'input profession code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 26))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
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
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 28))

                                'select nationality'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_nationality'), 
                                    findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 35), false)

								'jika nationality foreigner'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 35) == 'Foreigner') {
                                    'click button lookup country'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

                                    'input country code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 36))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        closeLookup()

                                        continue
                                    }
                                }
                                
								'jika job position tidak kosong'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 39).length() > 
                                1) {
                                    'click button lookup job position'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

                                    'input job position code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 38))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
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
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 42).length() > 
                                1) {
                                    'click button lookup department AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                    'input department AML Code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 41))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        closeLookup()

                                        continue
                                    }
                                }
                                
								'jika authority aml tidak kosong'
                                if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 44).length() > 
                                1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML Code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                                        findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 43))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                        flagWarning++

                                        continue
                                    }
                                }
                            }
                        }
                        
                        //Ambil nilai dari confins untuk verif store db lookup
                        if ((GlobalVariable.Role == 'Testing') && (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 
                            13) == 'LookUp')) {
							'getdatacust'
                            getDataCust()

							'declare confinsdata'
                            def confinsdata = GlobalVariable.Confinsdata

							'add customer relation to array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                                    'value'))

							'add profession to array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Profession'), 
                                    'value'))

							'add job position to array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_JobPosition'), 
                                    'value'))

							'add establishment date to array'
                            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_EstablishmentDate'), 
                                    'value'))

                            GlobalVariable.Confinsdata = confinsdata
                        }
                        
                        'click button save'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Save'))

                        Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 
                                4))

                        if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
                            'cek alert'
                            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, 
                                '2.TabFamilyData')
                        }
                        
                        if (GlobalVariable.FlagFailed == 0) {
                            'check save process write to excel'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject(
                                    'NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/TableFamilyHeader'), 
                                GlobalVariable.NumofFamily, '2.TabFamilyData')

                            'customer added +1'
                            (GlobalVariable.CountNumofCustomer)++

                            if (iscompleteMandatory == 0) {
                                errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                                'cek validasi'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofFamily, 
                                    '2.TabFamilyData')
                            }
                        }
                        
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Cancel'))

							GlobalVariable.IsDataCancel = 1
							
                            'customer added -1'
                            (GlobalVariable.CountNumofCustomer)--
                        } else {
                            if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
								'write status warning to excel'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                                    0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                            }
							if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
								if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
										'call test case Family data store verif'
										WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyDataStoreDBVerif'),
											[:], FailureHandling.CONTINUE_ON_FAILURE)
								} else if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'LookUp') {
										'call test case family lookup store data verif'
										WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyDataStoreDBVerif-LookUp'),
											[:], FailureHandling.CONTINUE_ON_FAILURE)
								}
								
							}
                        }
                        
						
                        break
                    }
                } else {
                    //jika appno tab family tidak sama dengan appno custmaindata
					if (findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily + 1, 12) != findTestData(excelPathCustomerPersonal).getValue(
                        GlobalVariable.NumofColm, 13)) {
                        'modify object button delete'
                        modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                            'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                            i) + ']/td[6]/span/span[2]/span/span/span/span/span/a/i', true)

						'jika ada button delete'
                        if (WebUI.verifyElementPresent(modifyNewButtonDelete, 5, FailureHandling.OPTIONAL)) {
                            'get cust name sebelum delete'
                            CustNameBefore = WebUI.getText(modifyNewFamilyName)

                            'click button Delete'
                            WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                            'accept alert'
                            WebUI.acceptAlert()

							'jika family merupakan index terakhir'
                            if (i == variableData.size()) {
								'jika familyname tidak muncul dalam tabel'
                                if (WebUI.verifyElementPresent(modifyNewFamilyName, 5, FailureHandling.OPTIONAL)) {
									'add cust name failed kedalam array'
									custnamefaileddelete.add(CustNameBefore)
                                }
                            } else {
                                'get cust name sebelum delete'
                                CustNameAfter = WebUI.getText(modifyNewFamilyName)

								'jika familyname after delete tidak sama dengan familyname before delete'
                                if (WebUI.verifyMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
									'add cust name failed kedalam array'
									custnamefaileddelete.add(CustNameBefore)
								}
                            }
                            
							'count ulang table family setelah delete'
                            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#family-tab > app-family-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
							
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

'jika ada familyname gagal delete'
if (custnamefaileddelete.size() > 0) {

	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + custnamefaileddelete)
	
    (GlobalVariable.FlagWarning)++
}

'call tc family data'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Family/TabFamilyData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

def getDataCust() {
	'declare confinsdata'
    def confinsdata = []

	if(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'LookUp'){
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
			confinsdata.add(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/LabelCountry')))
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

    if(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'LookUp'){
		'add ownership to array'
		confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Ownership'),
				'value'))
	}
	else if(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 13) == 'Input Data'){
		
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
	}

    GlobalVariable.Confinsdata = confinsdata
}

def checkFamilyDataEditNAP(ArrayList<String> variableData){
	if ((GlobalVariable.Role == 'Testing') && (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm,
		8).length() > 1)) {
		'Connect DB LOS'
		Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()
	
		ArrayList<String> listFam = new ArrayList<String>()
	
		listFam = CustomKeywords.'dbConnection.getInfoForEditNAP.getFamilyDataforEditNAP'(sqlConnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
				GlobalVariable.NumofColm, 8))
	
		ArrayList<String> arrayMatch = new ArrayList<String>()
	
		for (int familydt = 1; familydt <= variableData.size(); familydt++) {
			'modify object family name'
			modifyNewFamilyName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
				'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
				familydt) + ']/td[2]', true)
	
			modifyNewFamilyType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
				'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
				familydt) + ']/td[3]', true)
	
			modifyNewFamilyRelation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
				'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
				familydt) + ']/td[4]', true)
	
			modifyNewFamilyCustModel = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
				'xpath', 'equals', ('//*[@id="family-tab"]/app-family-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
				familydt) + ']/td[5]', true)
	
			Boolean isMatch = 0
	
			for (int familydb = 0; familydb < listFam.size()/4; familydb++) {
//				String result = listFam.get(familydb)
	
//				resultarray = result.split(', ')
	
				if ((((WebUI.verifyMatch(WebUI.getText(modifyNewFamilyName), '(?i)' + (listFam[0+(4*familydb)]), true, FailureHandling.OPTIONAL) ==
				false) || (WebUI.verifyMatch(WebUI.getText(modifyNewFamilyType), '(?i)' + (listFam[1+(4*familydb)]), true, FailureHandling.OPTIONAL) ==
				false)) || (WebUI.verifyMatch(WebUI.getText(modifyNewFamilyRelation), '(?i)' + (listFam[2+(4*familydb)]), true, FailureHandling.OPTIONAL) ==
				false)) || (WebUI.verifyMatch(WebUI.getText(modifyNewFamilyCustModel), '(?i)' + (listFam[3+(4*familydb)]), true, FailureHandling.OPTIONAL) ==
				false)) {
					isMatch = false
	
					continue
				} else {
					isMatch = true
	
					break
				}
			}
			
			arrayMatch.add(isMatch)
		}
		
		if (arrayMatch.contains(false)) {
	
			'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedLoadData'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedLoadData)
			
			'flagwarning +1'
			(GlobalVariable.FlagWarning)++
		}
	}
}

def closeLookup(){
	'click X'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

	

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 2) + ';' +GlobalVariable.StatusReasonLookup)

	GlobalVariable.FlagFailed = 1
}

def inputData(){
	'select customer relation'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 18), false)

	'input family name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 19))

	'input birth place'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 20))

	'select idtype'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_IDType'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 21), false)

	'Verify ID Type'
	if (((findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 21) != 'E-KTP') &&
	(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 21) != 'NPWP')) &&
	(findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 21) != 'AKTA')) {
		'input Id Expired Date'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id Expired Date'),
			findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 22))
	}
	
	'select marital status'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_Marital Status'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 23), false)

	'input mobile phone'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 24))

	'select customer model'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/select_CustomerModel'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 25), false)
}

def LookupData(){
	'click customer lookup'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Family Legal Name_btn btn-raised btn-primary'))

	'input customer no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Customer No_custNoId'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 15))

	'input customer name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Customer Name_custNameId'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 16))

	'input customer id no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/input_Id No_IdNoId'),
		findTestData(excelPathTabFamily).getValue(GlobalVariable.NumofFamily, 17))

	'click search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/button_Search'))
}