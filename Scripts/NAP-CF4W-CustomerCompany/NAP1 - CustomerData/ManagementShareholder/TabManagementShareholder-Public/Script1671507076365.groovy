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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'declare variableData'
ArrayList<String> variableData

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathMS'
excelPathMS = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder'

'get copyapp value dari findTestData(excelPathMS)'
copyapp = findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 10)

countMS =  findTestData(excelPathMS).getColumnNumbers()

'check if testing'
if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL))
}

'Loop Multiple ManagementSharholder Data'
for (GlobalVariable.NumofMS = GlobalVariable.StartIndex; GlobalVariable.NumofMS <= (countMS - 1); (GlobalVariable.NumofMS)++) {
    GlobalVariable.FlagFailed = 0

    if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/td_MSName'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > thead'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        if(findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public')){
        for (i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementNotPresent(modifyNewMSName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewMSName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewMSName).equalsIgnoreCase(findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 62))) {
                        break
                    }
                }
            }
        
		
            if ((i == variableData.size()) || (copyapp == 'No')) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

                if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
                        'click radio public'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Public'))

						if(GlobalVariable.RoleCompany == 'Testing'){
							
							'connect DB FOU'
							Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
							
							'get publictype ddl value from db'
							ArrayList<String> publictype = CustomKeywords.'dbConnection.checkCustomer.checkPublicTypeDDL'(sqlConnectionFOU)
							
							'get total label from ddl publictype'
							int totalddlpublictype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'))
						
							'verify total ddl publictype confins = total ddl db'
							WebUI.verifyEqual(totalddlpublictype - 1, publictype.size())
							
							'verify isi ddl publictype confins = db'
							if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'),
								publictype) == false) {
						
								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Public Type')
						
								(GlobalVariable.FlagFailed)++
							}
						}
						
                        'select Public Type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'), 
                            findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 62).toUpperCase(), false)

                        if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Daerah') || 
                        findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Pusat')) {
                            'input shareholder name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Name_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 63))

                            'input identity no'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Identity No_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 64))
                        }
                        
                        'click lookup position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Jabatan Code_JabatanCodeId'), 
                            findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 65))

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
                            findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 67))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                            }
                        }
                    
                        if (findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 70).length() > 1) {
                            'click button copy'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Copy'))
							
                        } else {
                            'input address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 71))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 72))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 73))

                            'click button lookup zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Zip Code_ZipCode'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 74))

                            'input kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kecamatan_kecamatan'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 75))

                            'input kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kelurahan_Kelurahan'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 76))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kota_kota'), 
                                findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 77))

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
                    
                }
                
                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

                if ((Integer.parseInt(findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 4)) == 0) && (GlobalVariable.FlagFailed == 
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
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(excelPathMS).getValue(
                                GlobalVariable.NumofMS, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), 
                        GlobalVariable.NumofMS, '2.TabManagementShareholderData')

                    if (Integer.parseInt(findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 4)) == 0) {
                        'Check error validasi'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                            GlobalVariable.NumofMS, '2.TabManagementShareholderData')
                    }
                    
                    
                }
                
            
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

                    } else {
                        if (GlobalVariable.FlagWarning > 0) {
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                                0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
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

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

'cek alert'
GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '2a.TabManagementShareholderMain')

if (GlobalVariable.FlagFailed == 0) {
    'Write to Excel SUCCESS'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2a.TabManagementShareholderMain', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, GlobalVariable.StatusFailed, findTestData(excelPathMS).getValue(GlobalVariable.NumofMS, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}