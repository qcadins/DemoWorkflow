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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/AddressInformation - Company - GuarantorCompany')

int copyAppColm = 0

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (GlobalVariable.FindDataFile.getValue(
        index, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 12)) && GlobalVariable.FindDataFile.getValue(
        index, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 13))) {
        copyAppColm = index

        break
    }
}

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
    10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

    for (i = 1; i <= variable.size(); i++) {
        'modify object address type'
        modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
            'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]', true)

        'modify object button edit'
        modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
            'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span/span/span/span/span/span/span/a/i', true)

        for (Address = copyAppColm; Address <= (countcolm + 1); Address++) {
            GlobalVariable.FlagFailed = 0

            if (GlobalVariable.FindDataFile.getValue(
                Address, 9).length() != 0) {
                if (GlobalVariable.FindDataFile.getValue(
                    Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 12)) && GlobalVariable.FindDataFile.getValue(
                    Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
                            Address, 12))) {
                        if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                            'click button edit'
                            WebUI.click(modifyNewbuttonedit)

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                
								'connect DB FOU'
								Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connectFOU'()

                                ArrayList<String> AddressType

                                ArrayList<String> Ownership

                                'get data array dari db'
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlconnectionFOU)

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlconnectionFOU)

                                'verify array dari db == option list confins'

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                                if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                                    AddressType) == false) {
                                    'Write To Excel GlobalVariable.StatusFailed'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 0, Address - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.ReasonFailedDDL'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 1, Address - 1, GlobalVariable.ReasonFailedDDL)

                                    (GlobalVariable.FlagFailed)++
                                }
                                
                                'get total label from ddl'
                                int totalowneship = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalowneship - 1, Ownership.size())

                                'verify array dari db == option list confins'
                                if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
                                    Ownership) == false) {
                                    'Write To Excel GlobalVariable.StatusFailed'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 0, Address - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.ReasonFailedDDL'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 1, Address - 1, GlobalVariable.ReasonFailedDDL)

                                    (GlobalVariable.FlagFailed)++
                                }
                            }
                            
                            'call function input address'
                            inputaddress()

                            break
                        }
                    }
                }
            } else {
                break
            }
        }
    }
    
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

    for (Address = copyAppColm; Address <= (countcolm + 1); Address++) {
        GlobalVariable.FlagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(
            Address, 9).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object address type'
                modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[1]', true)

                if (GlobalVariable.FindDataFile.getValue(
                    Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 12)) && GlobalVariable.FindDataFile.getValue(
                    Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (!(WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
                            Address, 12)))) {
                        if (i == variable.size()) {
                            'click button add'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Add'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                
								'connect DB FOU'
								Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connectFOU'()

                                ArrayList<String> AddressType

                                ArrayList<String> Ownership

                                'get data array dari db'
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlconnectionFOU)

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlconnectionFOU)

                                'verify array dari db == option list confins'

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                                if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                                    AddressType) == false) {
                                    'Write To Excel GlobalVariable.StatusFailed'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 0, Address - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.ReasonFailedDDL'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 1, Address - 1, GlobalVariable.ReasonFailedDDL)

                                    (GlobalVariable.FlagFailed)++
                                }
                                
                                'get total label from ddl'
                                int totalowneship = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalowneship - 1, Ownership.size())

                                'verify array dari db == option list confins'
                                if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
                                    Ownership) == false) {
                                    'Write To Excel GlobalVariable.StatusFailed'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 0, Address - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.ReasonFailedDDL'
                                    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '2.AddressInformation', 1, Address - 1, GlobalVariable.ReasonFailedDDL)

                                    (GlobalVariable.FlagFailed)++
                                }
                            }
                            
                            'call function input address'
                            inputaddress()

                            break
                        }
                    } else if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.FindDataFile.getValue(
                            Address, 12))) {
                        break
                    }
                }
            }
        } else {
            break
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    for (Address = copyAppColm; Address <= (countcolm + 1); Address++) {
        if (GlobalVariable.FindDataFile.getValue(
            Address, 9).length() != 0) {
            if (GlobalVariable.FindDataFile.getValue(
                Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                    GlobalVariable.NumofGuarantor, 12)) && GlobalVariable.FindDataFile.getValue(
                Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                    GlobalVariable.NumofGuarantor, 13))) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Add'))

                if (GlobalVariable.RoleCompany == 'Testing') {

					'connect DB FOU'
					Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connectFOU'()

                    ArrayList<String> AddressType

                    ArrayList<String> Ownership

                    'get data array dari db'
                    AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlconnectionFOU)

                    'get data array dari db'
                    Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlconnectionFOU)

                    'verify array dari db == option list confins'

                    'get total label from ddl'
                    int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                    'verify total ddl confins = total ddl db'
                    WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                        AddressType) == false) {
                        'Write To Excel GlobalVariable.StatusFailed'
                        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                            0, Address - 1, GlobalVariable.StatusFailed)

                        'Write To Excel GlobalVariable.ReasonFailedDDL'
                        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                            1, Address - 1, GlobalVariable.ReasonFailedDDL)

                        (GlobalVariable.FlagFailed)++
                    }
                    
                    'get total label from ddl'
                    int totalowneship = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'))

                    'verify total ddl confins = total ddl db'
                    WebUI.verifyEqual(totalowneship - 1, Ownership.size())

                    'verify array dari db == option list confins'
                    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
                        Ownership) == false) {
                        'Write To Excel GlobalVariable.StatusFailed'
                        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                            0, Address - 1, GlobalVariable.StatusFailed)

                        'Write To Excel GlobalVariable.ReasonFailedDDL'
                        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                            1, Address - 1, GlobalVariable.ReasonFailedDDL)

                        (GlobalVariable.FlagFailed)++
                    }
                }
                
                'call function input address'
                inputaddress()

                break
            }
        } else {
            break
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Save  Continue'))

def inputaddress() {
    GlobalVariable.FlagFailed = 0

    'pilih address type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/AddressInformation - Company - ManagementShareholderCompany').getValue(
            Address, 12), false, FailureHandling.OPTIONAL)

    if (GlobalVariable.FindDataFile.getValue(
        Address, 13).length() > 1) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_CopyAddressFrom'), 
            5, FailureHandling.OPTIONAL)) {
            'Select option dropdownlist Copy Address From '
            WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_CopyAddressFrom'), 
                GlobalVariable.FindDataFile.getValue(
                    Address, 13), false, FailureHandling.OPTIONAL)
        }
        
        'Click copy'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Copy'))

        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 32), false)
    } else {
        'input alamat'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/textarea_Address_'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 14))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_RT'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 15))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_RW'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 16))

        'click button search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Zipcode_btn btn-raised btn-primary'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Zip Code_ZipCode'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 17))

        'input kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kecamatan_kecamatan'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 18))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kelurahan_Kelurahan'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 19))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Kota_kota'), 
            GlobalVariable.FindDataFile.getValue(
                Address, 20))

        'click search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Search'), 
            FailureHandling.OPTIONAL)

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/Button_X'))

            'Click cancel'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'))

            'write to excel if failed'
            CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                0, Address - 1, GlobalVariable.StatusFailed)

            'write to excel reason lookup'
            CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                1, Address - 1, GlobalVariable.StatusReasonLookup)

            'Flagfailed +1 karena gagal melakukan lookup'
            (GlobalVariable.FlagFailed)++
        }
    }
    
    'input phone1 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_Area'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 21))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_Number'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 22))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 1_ Ext'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 23))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Area'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 24))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Number'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 25))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 2_Ext'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 26))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Area'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 27))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Number'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 28))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Phone 3_Ext'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 29))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Fax_Area'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 30))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/input_Fax_Number'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 31))

    'pilih status rumah'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/select_Ownership'), 
        GlobalVariable.FindDataFile.getValue(
            Address, 32), false)

    'click save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Save'))

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(
            Address, 4)) == 0 && GlobalVariable.FlagFailed == 0) {
        'Check alert'
        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(Address, '2.AddressInformation')
    }
    
    if (GlobalVariable.FlagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                    Address, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Add'), 
            Address, '2.AddressInformation')

        if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                Address, 4)) == 0) {
            'Check error validasi'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
                Address, '2.AddressInformation')
        }
    }
    
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'), 
        5, FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/AddressInformation - Company/button_Cancel'))
    }
    
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
        GlobalVariable.NumofVerifStore = Address

        'call test case verify address store data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabAddressVerifStoreDataDB'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

