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

String filePath = userDir + GlobalVariable.DataFileGuarantorCompany

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor')

int copyAppColm = 0

'get count colm'
countcolm = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getColumnNumbers()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
        index, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 12)) && findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
        index, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 13))) {
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

        'modify object button edit'
        modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
            'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span/span/span/span/span/span/span/a/i', true)

        for (Address = copyAppColm; Address <= (countcolm + 1); Address++) {
            int flagFailed = 0

            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 9).length() != 0) {
                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                    Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                        GlobalVariable.NumofGuarantor, 12)) && findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                    Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                            Address, 12))) {
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
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                                'verify total ddl confins = total ddl db'
                                if(WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
								}

                                'verify array dari db == option list confins'
                                if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                                    AddressType)==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
                                }

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalOwnership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'))

                                'verify total ddl confins = total ddl db'
                                if(WebUI.verifyEqual(totalOwnership - 1, Ownership.size())==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
								}

                                'verify array dari db == option list confins'
                                if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'), 
                                    Ownership)==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
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
        int flagFailed = 0

        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 9).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object address type'
                modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="address-tab"]/app-cc-address-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[1]', true)

                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                    Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                        GlobalVariable.NumofGuarantor, 12)) && findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                    Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (!(WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                            Address, 12)))) {
                        if (i == variable.size()) {
                            'click button add'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Add'))

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
                                AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                                'verify total ddl confins = total ddl db'
                                if(WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
								}

                                'verify array dari db == option list confins'
                                if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                                    AddressType)==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
                                }

                                'get data array dari db'
                                Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                                'get total label from ddl'
                                int totalOwnership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'))

                                'verify total ddl confins = total ddl db'
                                if(WebUI.verifyEqual(totalOwnership - 1, Ownership.size())==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
								}

                                'verify array dari db == option list confins'
                                if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'), 
                                    Ownership)==false){
									'write to excel if failed'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										0, Address - 1, GlobalVariable.StatusFailed)
									'write to excel reasonfailedddl'
									CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
										1, Address - 1, GlobalVariable.ReasonFailedDDL)
									
									flagFailed=1
                                }
                            }
                            
							'call function input address'
                            inputaddress()

                            break
                        }
                    } else if (WebUI.getText(modifyNewAddressType).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
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
    for (Address = 2; Address <= (countcolm + 1); Address++) {
        int flagFailed = 0

        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 9).length() != 0) {
            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                    GlobalVariable.NumofGuarantor, 12)) && findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/CustomerDetail - Company - Guarantor').getValue(
                    GlobalVariable.NumofGuarantor, 13))) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Add'))

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
                    AddressType = CustomKeywords.'dbconnection.checkNAP4db.checkAddressTypeCompany'(sqlConnectionFOU)

                    'get total label from ddl'
                    int totalddladdresstype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'))

                    'verify total ddl confins = total ddl db'
                    if(WebUI.verifyEqual(totalddladdresstype - 1, AddressType.size())==false){
						'write to excel if failed'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							0, Address - 1, GlobalVariable.StatusFailed)
						'write to excel reasonfailedddl'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							1, Address - 1, GlobalVariable.ReasonFailedDDL)
						
						flagFailed=1
					}

                    'verify array dari db == option list confins'
                    if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
                        AddressType)==false){
						'write to excel if failed'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							0, Address - 1, GlobalVariable.StatusFailed)
						'write to excel reasonfailedddl'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							1, Address - 1, GlobalVariable.ReasonFailedDDL)
						
						flagFailed=1
                    }

                    'get data array dari db'
                    Ownership = CustomKeywords.'dbconnection.checkNAP4db.checkOwnership'(sqlConnectionFOU)

                    'get total label from ddl'
                    int totalOwnership = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'))

                    'verify total ddl confins = total ddl db'
                    if(WebUI.verifyEqual(totalOwnership - 1, Ownership.size())==false){
						'write to excel if failed'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							0, Address - 1, GlobalVariable.StatusFailed)
						'write to excel reasonfailedddl'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							1, Address - 1, GlobalVariable.ReasonFailedDDL)
						
						flagFailed=1
					}

                    'verify array dari db == option list confins'
                    if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'), 
                        Ownership)==false){
						'write to excel if failed'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							0, Address - 1, GlobalVariable.StatusFailed)
						'write to excel reasonfailedddl'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation',
							1, Address - 1, GlobalVariable.ReasonFailedDDL)
						
						flagFailed=1
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
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Save  Continue'))

def inputaddress() {
    'pilih address type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Select One Business  Legal  Mailing'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 12), false, FailureHandling.OPTIONAL)

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
        Address, 13).length() > 1) {
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_CopyAddressFrom'), 
            5, FailureHandling.OPTIONAL)) {
            'Select option dropdownlist Copy Address From '
            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_CopyAddressFrom'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                    Address, 13), false, FailureHandling.OPTIONAL)
        }
        
        'Click copy'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Copy'))

        'pilih status rumah'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 32), false)
    } else {
        'input alamat'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/textarea_Address_'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 14))

        'input RT'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_RT'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 15))

        'input RW'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_RW'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 16))

        'click button search zipcode'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Zipcode_btn btn-raised btn-primary'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Zip Code_ZipCode'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 17))

        'input kecamatan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Kecamatan_kecamatan'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 18))

        'input kelurahan'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Kelurahan_Kelurahan'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 19))

        'input kota'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Kota_kota'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
                Address, 20))

        'click search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Search'), 
            FailureHandling.OPTIONAL)

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'Click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Cancel'))

            'write to excel if failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                0, Address - 1, GlobalVariable.StatusFailed)

            'write to excel reason lookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.AddressInformation', 
                0, Address - 1, GlobalVariable.StatusReasonLookup)

            flagFailed = 1
        }
    }
    
    'input phone1 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 1_Area'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 21))

    'input phone1 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 1_Number'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 22))

    'input phone1 Extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 1_ Ext'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 23))

    'input phone2 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 2_Area'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 24))

    'input phone2 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 2_Number'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 25))

    'input phone2 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 2_Ext'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 26))

    'input phone3 area'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 3_Area'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 27))

    'input phone3 number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 3_Number'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 28))

    'input phone3 extension'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Phone 3_Ext'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 29))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Fax_Area'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 30))

    'input fax'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/input_Fax_Number'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 31))

    'pilih status rumah'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/select_Ownership'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 32), false)

    'click save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Save'))

    Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company - Guarantor').getValue(
            Address, 4))

    if (iscompleteMandatory == 0) {
        'cek alert'
        flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(Address, '2.AddressInformation')
    }
    
    if (flagFailed == 0) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Add'), 
            Address, '2.AddressInformation')

        if (iscompleteMandatory == 0) {
            errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

            'cek validasi'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, Address, '2.AddressInformation')
        }
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Cancel'), 
        5, FailureHandling.OPTIONAL)) {
        'click button back'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/AddressInformation - Company/button_Cancel'))
    }
    
    if (GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes") {
        GlobalVariable.NumofVerifStore = Address

        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabAddressVerifStoreDataDB'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}
