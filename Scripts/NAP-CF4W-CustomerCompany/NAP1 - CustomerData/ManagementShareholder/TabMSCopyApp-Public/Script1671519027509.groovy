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

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/LabelKota'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def mscopyapp(){
	'Loop Multiple Shareholder data'
	for (GlobalVariable.NumofMS = GlobalVariable.StartIndex; GlobalVariable.NumofMS <= (datafileMS.getColumnNumbers() -
	1); (GlobalVariable.NumofMS)++) {
		if (datafileMS.getValue(GlobalVariable.NumofMS, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm,
			13)) {
			if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public') && WebUI.getText(modifyNewMSTypeName).equalsIgnoreCase(
				'Public')) {
				if (WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafileMS.getValue(GlobalVariable.NumofMS, 62))) {
					'click button edit'
					WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

					'check if '
					if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
						'click radio public'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Public'))

						'select Public Type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'),
							datafileMS.getValue(GlobalVariable.NumofMS, 62).toUpperCase(), false)

						if (datafileMS.getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Daerah') ||
						datafileMS.getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Pusat')) {
							'input shareholder name'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Name_form-control adInsInput ng-untouched ng-pristine ng-invalid'),
								datafileMS.getValue(GlobalVariable.NumofMS, 63))

							'input identity no'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Identity No_form-control adInsInput ng-untouched ng-pristine ng-invalid'),
								datafileMS.getValue(GlobalVariable.NumofMS, 64))
						}
						
						'click lookup position SLIK'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Position SLIK_btn btn-raised btn-primary'))

						'input jabatan code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Jabatan Code_JabatanCodeId'),
							datafileMS.getValue(GlobalVariable.NumofMS, 65))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

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
						
						'input share'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'),
							datafileMS.getValue(GlobalVariable.NumofMS, 67))

						if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('Yes')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
							}
						} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							if (datafileMS.getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('No')) {
								'click check box is Active'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
							}
						}
						
						if (datafileMS.getValue(GlobalVariable.NumofMS, 70).length() > 1) {
							'click button copy'
							WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Copy'))

							'call function MS Personal get data'
							getDataMS()
						} else {
							'input address'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'),
								datafileMS.getValue(GlobalVariable.NumofMS, 71))

							'input RT'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'),
								datafileMS.getValue(GlobalVariable.NumofMS, 72))

							'input RW'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'),
								datafileMS.getValue(GlobalVariable.NumofMS, 73))

							'click button lookup zipcode'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Zipcode_btn btn-raised btn-primary'))

							'input zipcode'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Zip Code_ZipCode'),
								datafileMS.getValue(GlobalVariable.NumofMS, 74))

							'input kecamatan'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kecamatan_kecamatan'),
								datafileMS.getValue(GlobalVariable.NumofMS, 75))

							'input kelurahan'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kelurahan_Kelurahan'),
								datafileMS.getValue(GlobalVariable.NumofMS, 76))

							'input kota'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kota_kota'),
								datafileMS.getValue(GlobalVariable.NumofMS, 77))

							'klik button search'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

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
						}
					
					'click button save'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

					if ((Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) && (GlobalVariable.FlagFailed ==
					0)) {
						'Check alert'
						GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS,
							'2.TabManagementShareholderData')
					}
					
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'),
						GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

						GlobalVariable.IsDataCancel = 1
						
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

						if (Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) {
							'Check error validasi'
							CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'),
								GlobalVariable.NumofMS, '2.TabManagementShareholderData')
						}
					}
					
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'),
						GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

						GlobalVariable.IsDataCancel = 1
					} else {
						if (GlobalVariable.FlagWarning > 0) {
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
								0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
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
								0, GlobalVariable.NumofMS - 1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofMS, 2) + ';' + GlobalVariable.ReasonFailedDelete + customertype)
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