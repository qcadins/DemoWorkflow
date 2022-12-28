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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'klik tab management shareholder'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/buttonTabMS'))

'delcare variableData'
ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

'looping MS table confins'
for (int i = 1; i <= variableData.size(); i++) {
    'modify object MS name'
    modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object is Active'
    modifyNewisActive = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[5]', true)

    'modify object MS type'
    modifyNewMSTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'modify object button edit'
    modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[8]/span/span/span/a/i', true)

    'get text customer type'
    customertype = WebUI.getText(modifyNewMSTypeName)

	'call function mscopyapp'
	mscopyapp()
}

def getDataMS() {
    'declare array for confins data'
    def confinsdata = []

    if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
        if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'LookUp') {
            'add customer name to array'
            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                    'value'))

            'add tax id to array'
            confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                    'value'))
        }
        
        'add address to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add RT to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                'value'))

        'add RW to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                'value'))

        'add zipcode to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelZipcode'), 
                'value'))

        'add kelurahan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKelurahan'), 
                'value'))

        'add kecamatan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKecamatan'), 
                'value'))

        'add kota to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKota'), 
                'value'))

        if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
            Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

            String optionLabel = select.getFirstSelectedOption().getText()

            'add ownership to array'
            confinsdata.add(optionLabel)
        }
    }
    
    GlobalVariable.Confinsdata = confinsdata
}

def getData() {
    'check if role testing dan input dengan lookup maka get data dari confins untuk verif store DB'
    if ((GlobalVariable.RoleCompany == 'Testing') && datafileMS.getValue(GlobalVariable.NumofMS, 13).equalsIgnoreCase('LookUp')) {
        if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
            'call function MS get Data'
            getDataMS()

            'add Position SLIK to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/label_positionSLIK'), 
                    'value'))

            Select selectcustomertype = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div/div[2]/div/div/lib-ucdropdownlist/div/select')))

            'add company type to array'
            GlobalVariable.Confinsdata.add(selectcustomertype.getFirstSelectedOption().getText())

            Select selectcustomermodel = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div[2]/div[2]/div/div/lib-ucdropdownlist/div/select')))

            'add customer model to array'
            GlobalVariable.Confinsdata.add(selectcustomermodel.getFirstSelectedOption().getText())

            'add share to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    'value').replace(' %', ''))

            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'add is active to array'
                GlobalVariable.Confinsdata.add('Yes')
            } else {
                'add is active to array'
                GlobalVariable.Confinsdata.add('No')
            }
            
            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'add is owner to array'
                GlobalVariable.Confinsdata.add('Yes')
            } else {
                'add is owner to array'
                GlobalVariable.Confinsdata.add('No')
            }
            
            Select selectownership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

            'add ownership to array'
            GlobalVariable.Confinsdata.add(selectownership.getFirstSelectedOption().getText())
        }
    }
}

def mscopyapp(){
	'Loop Multiple Shareholder data'
	for (GlobalVariable.NumofMS = GlobalVariable.StartIndex; GlobalVariable.NumofMS <= (datafileMS.getColumnNumbers() -
	1); (GlobalVariable.NumofMS)++) {
		if (datafileMS.getValue(GlobalVariable.NumofMS, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
			13)) {
			if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
				if (WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafileMS.getValue(GlobalVariable.NumofMS, 52)) ||
				WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafileMS.getValue(GlobalVariable.NumofMS, 50))) {
					'click button edit'
					WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

					'check if '
					if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
						'click radio company'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

						'input shareholder name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'),
							datafileMS.getValue(GlobalVariable.NumofMS, 52))

						'input tax id'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'),
							datafileMS.getValue(GlobalVariable.NumofMS, 53))

						'click button position SLIK'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

						'input jabatan code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 54))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
						} else {
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

							(GlobalVariable.FlagWarning)++

							continue
						}
						
						'select company type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'),
							datafileMS.getValue(GlobalVariable.NumofMS, 56), false)

						'select customer model'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'),
							datafileMS.getValue(GlobalVariable.NumofMS, 57), false)

						'input share'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'),
							datafileMS.getValue(GlobalVariable.NumofMS, 58))

						if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('Yes')) {
								'click check box is Owner'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
							}
						} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('No')) {
								'click check box is Owner'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
							}
						}
						
						if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('Yes')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
							}
						} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('No')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
							}
						}
						
						if (datafileMS.getValue(GlobalVariable.NumofMS, 70).length() > 1) {
							'click button copy'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Copy'))

							'call function MS Personal get data'
							getDataMS()
						} else {
							'input address'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'),
								datafileMS.getValue(GlobalVariable.NumofMS, 71))

							'input RT'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'),
								datafileMS.getValue(GlobalVariable.NumofMS, 72))

							'input RW'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'),
								datafileMS.getValue(GlobalVariable.NumofMS, 73))

							'click button lookup zipcode'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Zipcode_btn btn-raised btn-primary'))

							'input zipcode'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Zip Code_ZipCode'),
								datafileMS.getValue(GlobalVariable.NumofMS, 74))

							'input kecamatan'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kecamatan_kecamatan'),
								datafileMS.getValue(GlobalVariable.NumofMS, 75))

							'input kelurahan'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kelurahan_Kelurahan'),
								datafileMS.getValue(GlobalVariable.NumofMS, 76))

							'input kota'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kota_kota'),
								datafileMS.getValue(GlobalVariable.NumofMS, 77))

							'klik button search'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Search'))

							'verify input error'
							if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'),
								GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
							} else {
								'click X'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

								(GlobalVariable.FlagWarning)++

								continue
							}
							
							'select ownership'
							WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'),
								datafileMS.getValue(GlobalVariable.NumofMS, 78), false)
						}
					} else if ((datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'LookUp') && datafileMS.getValue(GlobalVariable.NumofMS,
						14).equalsIgnoreCase('Company')) {
						'click radio company'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

						'click button lookup shareholder'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Shareholder Legal Name_btn btn-raised btn-primary'))

						'input customer no'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer No_custNoId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 49))

						'input customer name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer Name_custNameId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 50))

						'input NPWP'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_NPWP_NpwpId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 51))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
						} else {
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/Button_X'))

							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData',
								GlobalVariable.NumofMS, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

							'Flagfailed +1 karena lookup gagal'
							(GlobalVariable.FlagFailed)++

							continue
						}
						
						'check if role testing maka get data MS untuk data verif'
						if (GlobalVariable.RoleCompany == 'Testing') {
							'call function get data'
							getDataMS()

							'add company type to array'
							GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'),
									'value'))

							'add customer model to array'
							GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'),
									'value'))

							'add ownership to array'
							GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'),
									'value'))

							'call test case company data verif'
							WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSCompanyDataVerif'),
								[:], FailureHandling.CONTINUE_ON_FAILURE)
						}
						
						'click button position SLIK'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

						'input jabatan code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 54))

						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
						} else {
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

							(GlobalVariable.FlagWarning)++

							continue
						}
						
						'select company type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'),
							datafileMS.getValue(GlobalVariable.NumofMS, 56), false)

						'input share'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'),
							datafileMS.getValue(GlobalVariable.NumofMS, 58))

						if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('Yes')) {
								'click check box is Owner'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
							}
						} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('No')) {
								'click check box is Owner'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
							}
						}
						
						if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('Yes')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
							}
						} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('No')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
							}
						}
						
						'call function untuk get data MS yang sudah di input untuk verif Store DB'
						getData()
					}
					
					'get MS Company name'
					custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'),
						'value', FailureHandling.OPTIONAL)

					'add name to Global variable'
					GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

					'click button save'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

					if ((Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) && (GlobalVariable.FlagFailed ==
					0)) {
						'Check alert'
						GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS,
							'2.TabManagementShareholderData')
					}
					
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'),
						GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

						'customer added -1'
						(GlobalVariable.CountNumofCustomer)--
					} else {
						if (GlobalVariable.FlagWarning > 0) {
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
								0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
					
					'verify flagfailed lookup == 0 '
					if (GlobalVariable.FlagFailed == 0) {
						'Check save Process write to excel'
						CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileMS.getValue(
									GlobalVariable.NumofMS, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'),
							GlobalVariable.NumofMS, '2.TabManagementShareholderData')

						'customer added +1'
						(GlobalVariable.CountNumofCustomer)++

						if (Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) {
							'Check error validasi'
							CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'),
								GlobalVariable.NumofMS, '2.TabManagementShareholderData')
						}
						
						if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofMS, 1) == 'SUCCESS') {
						if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
							if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
							'Yes')) {
								'call test case MS Company data store verif'
								WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSCompanyDataStoreDBVerif'),
									[:], FailureHandling.CONTINUE_ON_FAILURE)
							}
						} else {
							if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
							'Yes')) {
								'call test case MS Company  data store verif'
								WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSCompanyDataStoreDBVerif-LookUp'),
									[:], FailureHandling.CONTINUE_ON_FAILURE)
							}
						}
						}
					}
					
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'),
						GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

						'customer added -1'
						(GlobalVariable.CountNumofCustomer)--
					} else {
						if (GlobalVariable.FlagWarning > 0) {
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
								0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
					
					break
				} else {
					if (datafileMS.getValue(GlobalVariable.NumofMS + 1, 12) != datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
						13)) {
						'click button edit'
						WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

						if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click checkbox is active'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
						}
						
						'click button save'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

						'verify status MS is active = No'
						if (WebUI.verifyMatch(WebUI.getText(modifyNewisActive), 'NO', false)) {
							break
						} else {
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
								0, GlobalVariable.NumofMS - 1, GlobalVariable.ReasonFailedDelete + customertype)
						}
						
						break
					}
				}
			}
		} else {
			break
		}
	}
}