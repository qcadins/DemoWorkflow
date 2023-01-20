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
import com.kms.katalon.core.util.KeywordUtil

GlobalVariable.FlagFailed = 0

if(GlobalVariable.APPSTEP == 'CUSTOMER'){
	
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerPersonal)

	datafilecustdetail = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail')

	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation')
}
else if(GlobalVariable.APPSTEP == 'FAMILY'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileFamilyPersonal)

	datafilecustdetail = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail')

	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/AddressInformation')
}
else if(GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonal)

	datafilecustdetail = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/CustomerDetail')

	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorPersonal/AddressInformation')
}

'declare GlobalVariable.StartIndex'
GlobalVariable.StartIndex = 1

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index < (countcolm + 1); index++) {
	if (GlobalVariable.FindDataFile.getValue(index, 9).equalsIgnoreCase(datafilecustdetail.getValue(
			GlobalVariable.ColmNAP4, 12)) && GlobalVariable.FindDataFile.getValue(index, 10).equalsIgnoreCase(datafilecustdetail.getValue(
			GlobalVariable.ColmNAP4, 13))) {
		GlobalVariable.StartIndex = index

		break
	}
}

println(GlobalVariable.StartIndex)
'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 10)

'check if copy app = edit'
if (copyapp.equalsIgnoreCase('Edit')) {
	'count table addres row di confins'
	ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

	'looping tabel address'
	for (i = 1; i <= variable.size(); i++) {
		'modify object address type'
		modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
			'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[1]', true)

		'modify object button edit'
		modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
			'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[6]/span/span/span/span/span/span/span/a/i', true)

		'looping address'
		for (int Address = GlobalVariable.StartIndex; Address < (countcolm + 1); Address++) {
			 GlobalVariable.FlagFailed = 0

				if (GlobalVariable.FindDataFile.getValue(Address, 9).equalsIgnoreCase(datafilecustdetail.getValue(
						GlobalVariable.ColmNAP4, 12)) && GlobalVariable.FindDataFile.getValue(Address, 10).equalsIgnoreCase(datafilecustdetail.getValue(
						GlobalVariable.ColmNAP4, 13))) {
					if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
							Address, 12))) {
						if (WebUI.verifyElementPresent(modifyNewbuttonedit, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click button edit'
							WebUI.click(modifyNewbuttonedit)

						   'verif ddl ownership dan address type'
							verifyDDLAddress(Address)
							
							'call function input address'
							inputaddress(Address)

							
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
		
		if (GlobalVariable.FindDataFile.getValue(Address, 9).equalsIgnoreCase(datafilecustdetail.getValue(
			GlobalVariable.ColmNAP4, 12)) && GlobalVariable.FindDataFile.getValue(Address, 10).equalsIgnoreCase(datafilecustdetail.getValue(
			GlobalVariable.ColmNAP4, 13))) {
		
			for (i = 1; i <= variable.size(); i++) {
				'modify object address type'
				modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
					'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[1]', true)

				
					if (!(WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
							Address, 12)))) {
						if (i == variable.size()) {
							'click button add'
							WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Add'))

							'verif ddl ownership dan address type'
							verifyDDLAddress(Address)
							
							'call function input address'
							inputaddress(Address)

							
						}
					} else if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
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
		
			if (GlobalVariable.FindDataFile.getValue(Address, 9).equalsIgnoreCase(datafilecustdetail.getValue(
					GlobalVariable.ColmNAP4, 12)) && GlobalVariable.FindDataFile.getValue(Address, 10).equalsIgnoreCase(datafilecustdetail.getValue(
					GlobalVariable.ColmNAP4, 13))) {
				
				'click button add'
				WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Add'))

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
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/th_Address'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Back'))
	
	KeywordUtil.markFailed()
}

def inputaddress(int Address) {
	GlobalVariable.FlagFailed = 0
	
    'pilih address type'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'), 
        GlobalVariable.FindDataFile.getValue(Address, 12), false, FailureHandling.OPTIONAL)

    if (GlobalVariable.FindDataFile.getValue(Address, 13).length() > 1) {
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_Legal  Residence  Job'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'pilih address type untuk di copy'
            WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_Legal  Residence  Job'), 
                GlobalVariable.FindDataFile.getValue(Address, 13), false, FailureHandling.OPTIONAL)
        }
        
        'click button copy'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Copy'))

    } else {
        'input alamat'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/Address text'), 
            GlobalVariable.FindDataFile.getValue(Address, 14))

        'input RT'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_RT'), 
            GlobalVariable.FindDataFile.getValue(Address, 15))

        'input RW'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_RW'), 
            GlobalVariable.FindDataFile.getValue(Address, 16))

        'click button search zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Zipcode_btn btn-raised btn-primary'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Zip Code_ZipCode'), 
            GlobalVariable.FindDataFile.getValue(Address, 17))

		'input kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Kecamatan_kecamatan'), 
            GlobalVariable.FindDataFile.getValue(Address, 18))

		'input kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Kelurahan_Kelurahan'), 
            GlobalVariable.FindDataFile.getValue(Address, 19))

		'input kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Kota_kota'), 
            GlobalVariable.FindDataFile.getValue(Address, 20))

		'click search'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Cancel'))
        }
        
    }
	
	if(GlobalVariable.FindDataFile.getValue(Address, 21).length() > 0){
        'input phone1 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_area'), 
            GlobalVariable.FindDataFile.getValue(Address, 21))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 22).length() > 0){
        'input phone1 number'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_Number'), 
            GlobalVariable.FindDataFile.getValue(Address, 22))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 23).length() > 0){
        'input phone1 extension'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_ext'), 
            GlobalVariable.FindDataFile.getValue(Address, 23))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 24).length() > 0){
        'input phone2 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Area'), 
            GlobalVariable.FindDataFile.getValue(Address, 24))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 25).length() > 0){
        'input phone2 number'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Number'), 
            GlobalVariable.FindDataFile.getValue(Address, 25))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 26).length() > 0){
        'input phone2 extension'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Ext'), 
            GlobalVariable.FindDataFile.getValue(Address, 26))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 27).length() > 0){
        'input phone3 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_Area'), 
            GlobalVariable.FindDataFile.getValue(Address, 27))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 28).length() > 0){
        'input phone3 number'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_Number'), 
            GlobalVariable.FindDataFile.getValue(Address, 28))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 29).length() > 0){
        'input phone3 extension'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_ext'), 
            GlobalVariable.FindDataFile.getValue(Address, 29))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 30).length() > 0){
        'input fax'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Fax_Area'), 
            GlobalVariable.FindDataFile.getValue(Address, 30))
	}
	if(GlobalVariable.FindDataFile.getValue(Address, 31).length() > 0){
        'input fax'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Fax_Number'), 
            GlobalVariable.FindDataFile.getValue(Address, 31))
	}
        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
            GlobalVariable.FindDataFile.getValue(Address, 32), false)
    
	
	if (GlobalVariable.FindDataFile.getValue(Address, 13).length() > 1) {
		'call function get address'
		getAddress()
	}
	
    'click save'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Save'))

    Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(Address, 4))

    if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
        'cek alert'
        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(Address, '2.AddressInformation')
    }
    
    if (GlobalVariable.FlagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Add'), 
            Address, '2.AddressInformation')

        if (iscompleteMandatory == 0) {
            errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

            'cek validasi'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, Address, '2.AddressInformation')
        }
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Cancel'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'Click button cancel'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/button_Cancel'))
    }
	else{
		if (GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes") {
			GlobalVariable.NumofVerifStore = Address
	
			'call test case verif store data db address'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabAddressVerifStoreDataDB'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
    
}

def verifyDDLAddress(int Address){
	
	if (GlobalVariable.Role == 'Testing') {

		'connect DB FOU'
		Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

		ArrayList<String> AddressType, Ownership

		'get data array dari db'
		AddressType = CustomKeywords.'nap4Data.checkNAP4.checkAddressTypePersonal'(sqlconnectionFOU)

		'get total label from ddl'
		int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'))

		'verify total ddl confins = total ddl db'
		if(WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())==false){
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
			
			GlobalVariable.FlagFailed=1
		}

		'verify array dari db == option list confins'
		if(WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
			AddressType)==false){
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
			
			GlobalVariable.FlagFailed=1
		}

		'get data array dari db'
		Ownership = CustomKeywords.'nap4Data.checkNAP4.checkOwnership'(sqlconnectionFOU)

		'get total label from ddl'
		int totalownership = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_Select One Dinas  Family  KPR  Rented  Self - Owned'))

		'verify total ddl confins = total ddl db'
		if(WebUI.verifyEqual(totalownership - 1, Ownership.size())==false){
						
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
			
			GlobalVariable.FlagFailed=1
		}

		'verify array dari db == option list confins'
		if(WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_Select One Dinas  Family  KPR  Rented  Self - Owned'),
			Ownership)==false){
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.AddressInformation', Address, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL)
			
			GlobalVariable.FlagFailed=1
		}
	}
}

def getAddress(){
	
	'declare array for confins data'
	def confinsdata = []

	'add address to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/textarea_Address'),
			'value'))

	'add RT to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_RT'),
			'value'))

	'add RW to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_RW'),
			'value'))

	'add zipcode to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/labelZipcode'), 'value'))
	
	'add kelurahan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/labelKelurahan'),
			'value'))

	'add kecamatan to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/labelKecamatan'),
			'value'))

	'add kota to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/labelKota'),
			'value'))
	
	'add Phone 1 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_Area'),
			'value'))
	
	'add Phone 1 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_Number'),
			'value'))
	
	'add Phone 1 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 1_Ext'),
			'value'))
	
	'add Phone 2 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Area'),
			'value'))
	
	'add Phone 2 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Number'),
			'value'))
	
	'add Phone 2 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 2_Ext'),
			'value'))
	
	'add Phone 3 Area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_Area'),
			'value'))
	
	'add Phone 3 number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_Number'),
			'value'))
	
	'add Phone 3 extension to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Phone 3_Ext'),
			'value'))
	
	'add Phone fax area to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Fax_Area'),
			'value'))
	
	'add Phone fax number to array'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/input_Fax_Number'),
			'value'))
	
	GlobalVariable.Confinsdata = confinsdata

}