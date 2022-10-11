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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileFamilyPersonal

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/AddressInformation - Personal - Family')

int copyAppColm = 0

'get count colm'
countcolm = GlobalVariable.findDataFile.getColumnNumbers()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (GlobalVariable.findDataFile.getValue(index, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail - Personal - Family').getValue(
            GlobalVariable.NumofFamily, 13))) {
        copyAppColm = index

        break
    }
}

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
    GlobalVariable.NumofColm, 10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#address-tab > app-cc-address-paging > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

    for (i = 1; i <= variable.size(); i++) {
        'modify object address type'
        modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
            'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]', true)

        'modify object address type'
        modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
            'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span/span/span/span/span/span/span/a/i', true)

        for (Address = copyAppColm; Address <= (countcolm + 1); Address++) {
            int flagFailed = 0

            if (GlobalVariable.findDataFile.getValue(Address, 10).length() != 0) {
                if (GlobalVariable.findDataFile.getValue(Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail - Personal - Family').getValue(
                        GlobalVariable.NumofFamily, 13))) {
                    if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.findDataFile.getValue(Address, 
                            12))) {
                        if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                            'click button edit'
                            WebUI.click(modifyNewbuttonedit)

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'Koneksi database'
                                String servername = findTestData('Login/Login').getValue(1, 7)

                                String instancename = findTestData('Login/Login').getValue(2, 7)

                                String username = findTestData('Login/Login').getValue(3, 7)

                                String password = findTestData('Login/Login').getValue(4, 7)

                                String databaseFOU = findTestData('Login/Login').getValue(5, 7)

                                String driverclassname = findTestData('Login/Login').getValue(6, 7)

                                String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + 
                                databaseFOU

                                Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, 
                                    password, driverclassname)

                                ArrayList<WebElement> AddressType

                                ArrayList<WebElement> Ownership

                                'get data array dari db'
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypePersonal'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                                'verify array dari db == option list confins'
                                WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                                    AddressType)

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalownership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalownership - 1, Ownership.size())

                                'verify array dari db == option list confins'
                                WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                    Ownership)
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
        int flagFailed = 0

        if (GlobalVariable.findDataFile.getValue(Address, 10).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object address type'
                modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[1]', true)

                if (GlobalVariable.findDataFile.getValue(Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail - Personal - Family').getValue(
                        GlobalVariable.NumofFamily, 13))) {
                    if (!(WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.findDataFile.getValue(Address, 
                            12)))) {
                        if (i == variable.size()) {
                            'click button add'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Add'))

                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'Koneksi database'
                                String servername = findTestData('Login/Login').getValue(1, 7)

                                String instancename = findTestData('Login/Login').getValue(2, 7)

                                String username = findTestData('Login/Login').getValue(3, 7)

                                String password = findTestData('Login/Login').getValue(4, 7)

                                String databaseFOU = findTestData('Login/Login').getValue(5, 7)

                                String driverclassname = findTestData('Login/Login').getValue(6, 7)

                                String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + 
                                databaseFOU

                                Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, 
                                    password, driverclassname)

                                ArrayList<WebElement> AddressType

                                ArrayList<WebElement> Ownership

                                'get data array dari db'
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypePersonal'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                                'verify array dari db == option list confins'
                                WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                                    AddressType)

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalownership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'))

                                'verify total ddl confins = total ddl db'
                                WebUI.verifyEqual(totalownership - 1, Ownership.size())

                                'verify array dari db == option list confins'
                                WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                    Ownership)
                            }
                            
							'call function input address'
                            inputaddress()

                            break
                        }
                    } else if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(GlobalVariable.findDataFile.getValue(
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
        int flagFailed = 0

        if (GlobalVariable.findDataFile.getValue(Address, 9).length() != 0) {
            if (GlobalVariable.findDataFile.getValue(Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail - Personal - Family').getValue(
                    GlobalVariable.NumofFamily, 12)) && GlobalVariable.findDataFile.getValue(Address, 10).equalsIgnoreCase(
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail - Personal - Family').getValue(
                    GlobalVariable.NumofFamily, 13))) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Add'))

                if (GlobalVariable.RoleCompany == 'Testing') {
                    'Koneksi database'
                    String servername = findTestData('Login/Login').getValue(1, 7)

                    String instancename = findTestData('Login/Login').getValue(2, 7)

                    String username = findTestData('Login/Login').getValue(3, 7)

                    String password = findTestData('Login/Login').getValue(4, 7)

                    String databaseFOU = findTestData('Login/Login').getValue(5, 7)

                    String driverclassname = findTestData('Login/Login').getValue(6, 7)

                    String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

                    Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

                    ArrayList<WebElement> AddressType

                    ArrayList<WebElement> Ownership

                    'get data array dari db'
                    AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypePersonal'(sqlConnectionFOU)

                    'get total label from ddl'
                    int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'))

                    'verify total ddl confins = total ddl db'
                    WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())

                    'verify array dari db == option list confins'
                    WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                        AddressType)

                    'get data array dari db'
                    Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                    'get total label from ddl'
                    int totalownership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'))

                    'verify total ddl confins = total ddl db'
                    WebUI.verifyEqual(totalownership - 1, Ownership.size())

                    'verify array dari db == option list confins'
                    WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                        Ownership)
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
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/th_Address'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Back'))
}

def inputaddress() {
    'pilih address type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
        GlobalVariable.findDataFile.getValue(Address, 12), false, FailureHandling.OPTIONAL)

    if (GlobalVariable.findDataFile.getValue(Address, 13).length() > 1) {
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Legal  Residence  Job'), 
            5, FailureHandling.OPTIONAL)) {
            'pilih address type untuk di copy'
            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Legal  Residence  Job'), 
                GlobalVariable.findDataFile.getValue(Address, 13), false, FailureHandling.OPTIONAL)
        }
        
        'click button copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Copy'))

        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
            GlobalVariable.findDataFile.getValue(Address, 32), false)
    } else {
        'input alamat'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/Address text'), 
            GlobalVariable.findDataFile.getValue(Address, 14))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_RT'), 
            GlobalVariable.findDataFile.getValue(Address, 15))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_RW'), 
            GlobalVariable.findDataFile.getValue(Address, 16))

        'click button search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Zipcode_btn btn-raised btn-primary'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Zip Code_ZipCode'), 
            GlobalVariable.findDataFile.getValue(Address, 17))

        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Kecamatan_kecamatan'), 
            GlobalVariable.findDataFile.getValue(Address, 18))

        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Kelurahan_Kelurahan'), 
            GlobalVariable.findDataFile.getValue(Address, 19))

        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Kota_kota'), 
            GlobalVariable.findDataFile.getValue(Address, 20))

        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Cancel'))
        }
        
        'input phone1 area'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 1_area'), 
            GlobalVariable.findDataFile.getValue(Address, 21))

        'input phone1 number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 1_Number'), 
            GlobalVariable.findDataFile.getValue(Address, 22))

        'input phone1 extension'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 1_ext'), 
            GlobalVariable.findDataFile.getValue(Address, 23))

        'input phone2 area'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 2_Area'), 
            GlobalVariable.findDataFile.getValue(Address, 24))

        'input phone2 number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 2_Number'), 
            GlobalVariable.findDataFile.getValue(Address, 25))

        'input phone2 extension'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 2_Ext'), 
            GlobalVariable.findDataFile.getValue(Address, 26))

        'input phone3 area'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 3_Area'), 
            GlobalVariable.findDataFile.getValue(Address, 27))

        'input phone3 number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 3_Number'), 
            GlobalVariable.findDataFile.getValue(Address, 28))

        'input phone3 extension'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Phone 3_ext'), 
            GlobalVariable.findDataFile.getValue(Address, 29))

        'input fax'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Fax_Area'), 
            GlobalVariable.findDataFile.getValue(Address, 30))

        'input fax'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/input_Fax_Number'), 
            GlobalVariable.findDataFile.getValue(Address, 31))

        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
            GlobalVariable.findDataFile.getValue(Address, 32), false)
    }
    
    'click save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Save'))

    iscompleteMandatory = Integer.parseInt(GlobalVariable.findDataFile.getValue(Address, 4))

    if (iscompleteMandatory == 0) {
        'cek alert'
        flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(Address, '2.AddressInformation')
    }
    
    if (flagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Add'), 
            Address, '2.AddressInformation')

        if (iscompleteMandatory == 0) {
            errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

            'cek validasi'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, Address, '2.AddressInformation')
        }
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Cancel'), 
        5, FailureHandling.OPTIONAL)) {
        'Click button cancel'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/button_Cancel'))
    }
    
    if (GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes") {
        GlobalVariable.NumofVerifStore = Address

        'call test case verif store data db address'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabAddressVerifStoreDataDB'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

