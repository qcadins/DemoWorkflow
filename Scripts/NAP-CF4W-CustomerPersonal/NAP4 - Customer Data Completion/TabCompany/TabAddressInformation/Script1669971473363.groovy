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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagFailed = 0

'call function get data file'
getDataFile()

GlobalVariable.StartIndex = 0

'get count colm'
countcolm = findTestData(GlobalVariable.excelPath).getColumnNumbers()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index < (countcolm + 1); index++) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(index, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 13))) {
		GlobalVariable.StartIndex = index

		break
	}
}

'copyapp'
copyapp = findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 10)

'check if copy app = edit'
if (copyapp.equalsIgnoreCase('Edit')) {
	'count table addres row di confins'
	ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

	for (i = 1; i <= variable.size(); i++) {
		'modify object address type'
		modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_addressType'),
			'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[1]', true)

		'modify object button edit'
		modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_addressType'),
			'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[6]/span/span/span/span/span/span/span/a/i', true)

		for (int Address = GlobalVariable.StartIndex; Address < (countcolm + 1); Address++) {
			GlobalVariable.FlagFailed = 0

				if (findTestData(GlobalVariable.excelPath).getValue(Address, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
						GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(Address, 10).equalsIgnoreCase(
					findTestData(excelPathCustDetail).getValue(
						GlobalVariable.ColmNAP4, 13))) {
					if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData(GlobalVariable.excelPath).getValue(Address,
							12))) {
						if (WebUI.verifyElementPresent(modifyNewbuttonedit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click button edit'
							WebUI.click(modifyNewbuttonedit)

							'verif ddl ownership dan address type'
							verifyDDLAddress(Address)
							
							'call function input address'
							inputaddress(Address)

							break
						}
					}
				}
				else {
					break
				}
		}
	}
	
	'count ulang table address row di confins'
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

	for (int Address = GlobalVariable.StartIndex; Address < (countcolm + 1); Address++) {
		GlobalVariable.FlagFailed = 0

		if (findTestData(GlobalVariable.excelPath).getValue(Address, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(Address, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 13))) {
		
			for (i = 1; i <= variable.size(); i++) {
				'modify object address type'
				modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_addressType'),
					'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[1]', true)

					if (!(WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData(GlobalVariable.excelPath).getValue(Address,
							12)))) {
						if (i == variable.size()) {
							'click button add'
							WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Add'))

							'verif ddl ownership dan address type'
							verifyDDLAddress(Address)
							
							'call function input address'
							inputaddress(Address)

							break
						}
					} else if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData(GlobalVariable.excelPath).getValue(
							Address, 12))) {
						break
					}

			}
		}
		else {
			break
		}
	}
} else if (copyapp.equalsIgnoreCase('No')) {
	GlobalVariable.FlagFailed = 0
	
	for (int Address = GlobalVariable.StartIndex; Address < (countcolm + 1); Address++) {
			if (findTestData(GlobalVariable.excelPath).getValue(Address, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
					GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(Address, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
					GlobalVariable.ColmNAP4, 13))) {
				
				'click button add'
				WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Add'))

				'verif ddl ownership dan address type'
				verifyDDLAddress(Address)
				
				'call function input address'
				inputaddress(Address)

				break
			}
			else {
				break
			}
	}
}

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/th_Address'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Back'))
}
	
def inputaddress(int Address) {
    GlobalVariable.FlagFailed = 0

	'pilih address type'
	WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Select One Business  Legal  Mailing'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 12), false, FailureHandling.OPTIONAL)

	if (findTestData(GlobalVariable.excelPath).getValue(Address, 13).length() > 1) {
		if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_CopyAddressFrom'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'Select option dropdownlist Copy Address From '
			WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_CopyAddressFrom'),
				findTestData(GlobalVariable.excelPath).getValue(Address, 13), false, FailureHandling.OPTIONAL)
		}
		
		'Click copy'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Copy'))
		
	} else {
		'input alamat'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/textarea_Address'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 14))

		'input RT'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_RT'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 15))

		'input RW'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_RW'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 16))

		'click button search zipcode'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Zipcode_btn btn-raised btn-primary'))

		'input zipcode'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Zip Code_ZipCode'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 17))

		'input kecamatan'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Kecamatan_kecamatan'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 18))

		'input kelurahan'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Kelurahan_Kelurahan'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 19))

		'input kota'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Kota_kota'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 20))

		'click search'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Search'),
			FailureHandling.OPTIONAL)

		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/a_Select'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'click select'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/a_Select'))
		} else {
			'click X'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/Button_X'))

			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(Address, 2) + ';' +GlobalVariable.ReasonFailedVerifyRule)

			'Flagfailed +1 karena gagal melakukan lookup'
			(GlobalVariable.FlagFailed)++
		}
	
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 21).length()>0){
		'input phone1 area'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_Area'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 21))
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 22).length()>0){
		'input phone1 number'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_Number'),
			findTestData(GlobalVariable.excelPath).getValue(Address, 22))
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 23).length()>0){
	'input phone1 Extension'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_ Ext'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 23))
	}
	
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 24).length()>0){
	'input phone2 area'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Area'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 24))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 25).length()>0){
	'input phone2 number'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Number'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 25))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 26).length()>0){
	'input phone2 extension'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Ext'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 26))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 27).length()>0){
	'input phone3 area'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Area'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 27))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 28).length()>0){
	'input phone3 number'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Number'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 28))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 29).length()>0){
	'input phone3 extension'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Ext'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 29))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 30).length()>0){
	'input fax'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Fax_Area'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 30))
	}
	if(findTestData(GlobalVariable.excelPath).getValue(Address, 31).length()>0){
	'input fax'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Fax_Number'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 31))
	}
	
	'pilih status rumah'
	WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Ownership'),
		findTestData(GlobalVariable.excelPath).getValue(Address, 32), false)

	if (findTestData(GlobalVariable.excelPath).getValue(Address, 13).length() > 1) {
		'call function get address'
		getAddress()
	}
	
	'click save'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Save'))

	if ((Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(Address, 4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
		'Check alert'
		GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(Address, '2.AddressInformation')
	}
	
	if (GlobalVariable.FlagFailed == 0) {
		'Check save Process write to excel'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(
					Address, 4)), findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Add'),
			Address, '2.AddressInformation')

		if (Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(Address, 4)) == 0) {
			'Check error validasi'
			CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP/NAP4-CustomerDataCompletion/errorvalidasi'),
				Address, '2.AddressInformation')
		}
	}
	
	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Cancel'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click button cancel'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/button_Cancel'))
	}
    
    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
        GlobalVariable.NumofVerifStore = Address

        'call test case verify address store data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabAddressVerifStoreDataDB'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

def verifyDDLAddress(int Address){
	if (GlobalVariable.Role == 'Testing') {
		'connect DB FOU'
		Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

		ArrayList<String> AddressType, Ownership

		'get data array dari db'
		AddressType = CustomKeywords.'nap4Data.checkNAP4.checkAddressTypeCompany'(sqlconnectionFOU)

		'get data array dari db'
		Ownership = CustomKeywords.'nap4Data.checkNAP4.checkOwnership'(sqlconnectionFOU)

		'get total label from ddl'
		int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Select One Business  Legal  Mailing'))

		'verify total ddl confins = total ddl db'
		WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Select One Business  Legal  Mailing'),
			AddressType) == false) {

			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(Address, 2) + ';' +GlobalVariable.ReasonFailedDDL)

			(GlobalVariable.FlagFailed)++
		}
		
		'get total label from ddl'
		int totalowneship = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Ownership'))

		'verify total ddl confins = total ddl db'
		WebUI.verifyEqual(totalowneship - 1, Ownership.size())

		'verify array dari db == option list confins'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/select_Ownership'),
			Ownership) == false) {

			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(Address, 2) + ';' +GlobalVariable.ReasonFailedDDL)
			
			(GlobalVariable.FlagFailed)++
		}
	}
}

def getAddress(){
	
	'declare array for confins data'
	def confinsdata = []

	'add address to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/textarea_Address'),
			'value'))

	'add RT to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_RT'),
			'value'))

	'add RW to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_RW'),
			'value'))

	'add zipcode to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/labelZipcode'), 'value'))
	
	'add kelurahan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/labelKelurahan'),
			'value'))

	'add kecamatan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/labelKecamatan'),
			'value'))

	'add kota to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/labelKota'),
			'value'))
	
	'add Phone 1 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_Area'),
			'value'))
	
	'add Phone 1 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_Number'),
			'value'))
	
	'add Phone 1 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 1_Ext'),
			'value'))
	
	'add Phone 2 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Area'),
			'value'))
	
	'add Phone 2 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Number'),
			'value'))
	
	'add Phone 2 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 2_Ext'),
			'value'))
	
	'add Phone 3 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Area'),
			'value'))
	
	'add Phone 3 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Number'),
			'value'))
	
	'add Phone 3 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Phone 3_Ext'),
			'value'))
	
	'add Phone fax area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Fax_Area'),
			'value'))
	
	'add Phone fax number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/AddressInformation/input_Fax_Number'),
			'value'))
	
	GlobalVariable.Confinsdata = confinsdata

}

def getDataFile(){
	
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1.4 CustomerDataCompletion-Personal - GuarantorCompany.xlsm")
		
	'get data file customer'
	excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail'
				
	'declare data file Global variable'
	GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation'
	
	
}