import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.text.SimpleDateFormat as SimpleDateFormat
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.TexturePaintContext.Byte as Byte
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
import internal.GlobalVariable as GlobalVariable

GlobalVariable.CopyAppColm = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorPersonalCompany)

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal')

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

ArrayList<WebElement> variable

ArrayList<WebElement> financialDateDelete = new ArrayList<WebElement>()

ArrayList<WebElement> bankAccDelete = new ArrayList<WebElement>()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (GlobalVariable.FindDataFile.getValue(index, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 13))) {
        GlobalVariable.CopyAppColm = index

        GlobalVariable.NumofVerifStore = index

        break
    }
}

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofGuarantor, 
    10)

'Check if Edit Untuk financial data'
if (copyapp.equalsIgnoreCase('Edit')) {
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/labelfainancial_nodataavailable'), 
            FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        for (i = 1; i <= variable.size(); i++) {
            'modify object Date'
            modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

            'modify object button edit'
            modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[1]/i', true)

            'modify object button delete'
            modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[2]/i', true)

            for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
                int flagFailed = 0

                if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantor, 13))) {
                        if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                           
							'check if date sama'
                            if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(financialdata, 17)))) {
                                'click button edit'
                                WebUI.click(modifyNewbuttonedit)

								'call function input financial data'
                                inputFinancialData()

                                break
                            } else {
                                if (GlobalVariable.FindDataFile.getValue(financialdata + 1, 10).length() == 0) {
                                    'click button delete'
                                    WebUI.click(modifyNewbuttondelete)

                                    'accept alert'
                                    WebUI.acceptAlert(FailureHandling.OPTIONAL)

                                    if (i == variable.size()) {
                                        if (WebUI.verifyElementNotPresent(modifyNewDate, 5, FailureHandling.OPTIONAL)) {
                                            variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))
                                        } else {
                                            'add cust name failed kedalam array'
                                            financialDateDelete.add(modifyDateNew)

                                            continue
                                        }
                                    } else {
                                        'get cust name sesudah delete'
                                        modifyDateNewAfter = WebUI.getText(modifyNewDate).replace('-', ' ')

                                        if (WebUI.verifyNotMatch(modifyDateNewAfter, modifyDateNew, false, FailureHandling.OPTIONAL)) {
                                            variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))
                                        } else {
                                            'add cust name failed kedalam array'
                                            financialDateDelete.add(modifyDateNew)

                                            continue
                                        }
                                    }
                                    
                                    i--
                                }
                            }
                        } else {
                            break
                        }
                    }
                } else {
                    break
                }
            }
        }
    }
    
    if (financialDateDelete.size() > 0) {
		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

		'write to excel reason failed'
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
            1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + financialDateDelete)

		'flagwarning +1'
        (GlobalVariable.FlagWarning)++
    }
    
	'count table financial data row di confins'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object Date'
                modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

                'modify object button delete'
                modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[2]/i', true)

                if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 12).length() > 0) {
                        
                        'verify date beda'
                        if (!(WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(financialdata, 17))))) {
                            if (i == variable.size()) {
                                'click button add'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Add'))

                                inputFinancialData()

                                break
                            }
                        } else if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(financialdata, 17)))) {
                            break
                        }
                    }
                }
            }
        } else {
            break
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
            if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                    GlobalVariable.NumofGuarantor, 13))) {
                if (GlobalVariable.FindDataFile.getValue(financialdata, 12).length() > 0) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Add'))

                    inputFinancialData()
                }
            }
        } else {
            break
        }
    }
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.CopyAppColm, 21).length() > 1) {
    'input jumlah harta'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Jumlah Harta'), 
        GlobalVariable.FindDataFile.getValue(GlobalVariable.CopyAppColm, 21))
}

'Check if Edit Untuk Bank Account dan Bank Statement'
if (copyapp.equalsIgnoreCase('Edit')) {
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

    if (variable.size() > 0) {
        for (i = 1; i <= variable.size(); i++) {
            'modify button edit'
            modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[1]', true)

            'modify button delete'
            modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[2]', true)

            'modify bank name - branch - bank no'
            modifyNewbankaccdetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/h5', true)

            BankDetail = WebUI.getText(modifyNewbankaccdetail)

            for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
                int flagFailed = 0

                if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantor, 13))) {
                        if (GlobalVariable.FindDataFile.getValue(financialdata, 24).length() > 0) {
                            if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                                bankdetailexcel = ((((((('- ' + GlobalVariable.FindDataFile.getValue(financialdata, 24)) + 
                                ' - ') + GlobalVariable.FindDataFile.getValue(financialdata, 25)) + ' - ') + GlobalVariable.FindDataFile.getValue(
                                    financialdata, 27)) + ' - ') + GlobalVariable.FindDataFile.getValue(financialdata, 26))

                                if (BankDetail.equalsIgnoreCase(bankdetailexcel)) {
                                    'click button edit'
                                    WebUI.click(modifyNewbuttonedit)

                                    inputBank(copyapp, variable, flagFailed)

                                    break
                                } else {
                                    if (GlobalVariable.FindDataFile.getValue(financialdata + 1, 10).length() == 0) {
                                        'click button delete'
                                        WebUI.click(modifyNewbuttondelete)

                                        'accept alert'
                                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                                        if (i == variable.size()) {
                                            if (WebUI.verifyElementNotPresent(modifyNewbankaccdetail, 5, FailureHandling.OPTIONAL)) {
                                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))
                                            } else {
                                                'add cust name failed kedalam array'
                                                bankAccDelete.add(BankDetail)

                                                continue
                                            }
                                        } else {
                                            'get cust name sebelum delete'
                                            modifyBankaccAfter = WebUI.getText(modifyNewbankaccdetail)

                                            if (WebUI.verifyNotMatch(modifyBankaccAfter, BankDetail, false, FailureHandling.OPTIONAL)) {
                                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))
                                            } else {
                                                'add cust name failed kedalam array'
                                                bankAccDelete.add(BankDetail)

                                                continue
                                            }
                                        }
                                        
                                        i--
                                    }
                                }
                            } else {
                                break
                            }
                        }
                    }
                } else {
                    break
                }
            }
        }
    }
    
    if (bankAccDelete.size() > 0) {
        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

        CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
            1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + financialDateDelete)

        (GlobalVariable.FlagWarning)++
    }
    
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
            if (variable.size() > 0) {
                for (i = 1; i <= variable.size(); i++) {
                    'modify button edit'
                    modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[1]', 
                        true)

                    'modify button delete'
                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[2]', 
                        true)

                    'modify bank name - branch - bank no'
                    modifyNewbankaccdetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/h5', true)

                    BankDetail = WebUI.getText(modifyNewbankaccdetail)

                    if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantor, 13))) {
                        if (GlobalVariable.FindDataFile.getValue(financialdata, 24).length() > 0) {
                            bankdetailexcel = ((((((('- ' + GlobalVariable.FindDataFile.getValue(financialdata, 24)) + ' - ') + 
                            GlobalVariable.FindDataFile.getValue(financialdata, 25)) + ' - ') + GlobalVariable.FindDataFile.getValue(
                                financialdata, 27)) + ' - ') + GlobalVariable.FindDataFile.getValue(financialdata, 26))

                            'verify date beda'
                            if (!(BankDetail.equalsIgnoreCase(bankdetailexcel)) || WebUI.verifyElementNotPresent(modifyNewbuttondelete, 
                                10, FailureHandling.OPTIONAL)) {
                                if (i == variable.size()) {
                                    'click button add bank'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_AddBank'))

                                    inputBank(copyapp, variable, flagFailed)
                                }
                            } else if (BankDetail.equalsIgnoreCase(bankdetailexcel)) {
                                break
                            }
                        }
                    }
                }
            } else {
                'click button add bank'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_AddBank'))

                inputBank(copyapp, variable, flagFailed)
            }
        } else {
            break
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 10).length() != 0) {
            if (GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                    GlobalVariable.NumofGuarantor, 13))) {
                if (GlobalVariable.FindDataFile.getValue(financialdata, 24).length() > 0) {
                    'click button add bank'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_AddBank'))

                    inputBank(copyapp, variable, flagFailed)
                }
            }
        } else {
            break
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_SaveFinancial'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/th_Date'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    'call test case verify store data financial'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabFinancialDataVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputFinancialData() {
    int flagWarning = 0

    String maritalStatus = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/label_MaritalStatus'))

    'input monthly income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Monthly Income_'), 
        GlobalVariable.FindDataFile.getValue(financialdata, 12))

    'input monthly other income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Other Income_'), 
        GlobalVariable.FindDataFile.getValue(financialdata, 13))

    'pilih source monthly other income'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_BusinessOnline ShopSalaryStock Trading'), 
        GlobalVariable.FindDataFile.getValue(financialdata, 14), false)

    monthlyInstallment = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Monthly Installment Amount_')

    monthlyExpense = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Monthly Expense Amount_')

    datesAsOf = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Date As Of_form-')

    buttonCalculate = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Calculate')

    buttonSave = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Save')

    'Pengecekan jika maritalstatus married'
    if (maritalStatus.equalsIgnoreCase('Married')) {
        monthlyInstallment = WebUI.modifyObjectProperty(monthlyInstallment, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[6]/div/input', 
            true)

        monthlyExpense = WebUI.modifyObjectProperty(monthlyExpense, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[7]/div/input', 
            true)

        datesAsOf = WebUI.modifyObjectProperty(datesAsOf, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[10]/div/input', 
            true)

        buttonCalculate = WebUI.modifyObjectProperty(buttonCalculate, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[11]/div/button', 
            true)

        'Pengecekan spouse monthly income pada excel terisi'
        if (GlobalVariable.FindDataFile.getValue(financialdata, 18).length() >= 1) {
            'Input spouse monthly income'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_SpouseMonthlyIncome'), 
                GlobalVariable.FindDataFile.getValue(financialdata, 18))
        }
        
        'Pengecekan isjoinincome bernilai yes atau no pada excel'
        if (GlobalVariable.FindDataFile.getValue(financialdata, 19).equalsIgnoreCase('Yes')) {
            'Jika is join income belum tercentang'
            if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'centang is join income'
                WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        } else if (GlobalVariable.FindDataFile.getValue(financialdata, 19).equalsIgnoreCase('No')) {
            'jika is join income sudah tercentang'
            if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'Un-centang is join income'
                WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        }
    }
    
    'input monthly installment'
    WebUI.setText(monthlyInstallment, GlobalVariable.FindDataFile.getValue(financialdata, 15))

    'input monthly expense'
    WebUI.setText(monthlyExpense, GlobalVariable.FindDataFile.getValue(financialdata, 16))

    'input dates as of'
    WebUI.setText(datesAsOf, GlobalVariable.FindDataFile.getValue(financialdata, 17))

    'click button calculate'
    WebUI.click(buttonCalculate)

    'click button save'
    WebUI.click(buttonSave)
}

def inputBank(String copyapp, ArrayList<WebElement> variable, int flagFailed) {
    int flagWarning = 0

    if (GlobalVariable.FindDataFile.getValue(financialdata, 24).length() > 1) {
        'click lookup bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Bank Name_btn btn-raised btn-primary'))

        'input bank code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Bank Code_BankCode'), 
            GlobalVariable.FindDataFile.getValue(financialdata, 23))

        'click button search bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/FinancialData - Company/button_Cancel'))

            'Write To Excel GlobalVariable.StatusWarning'
            CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
                0, financialdata - 1, GlobalVariable.StatusWarning)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
                1, financialdata - 1, GlobalVariable.StatusReasonLookup)

            flagWarning++
        }
        
        'input bank branch name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Bank Branch_'), 
            GlobalVariable.FindDataFile.getValue(financialdata, 25))

        'input bank account name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Account Name_'), 
            GlobalVariable.FindDataFile.getValue(financialdata, 26))

        'input bank account number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Account No_'), 
            GlobalVariable.FindDataFile.getValue(financialdata, 27))

        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            2, FailureHandling.OPTIONAL)) {
            'checkbox default'
            if (GlobalVariable.FindDataFile.getValue(financialdata, 28) == 'Yes') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            2)) {
            'checkbox default'
            if (GlobalVariable.FindDataFile.getValue(financialdata, 28) == 'No') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            2, FailureHandling.OPTIONAL)) {
            'checkbox active'
            if (GlobalVariable.FindDataFile.getValue(financialdata, 29) == 'Yes') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            2)) {
            'checkbox active'
            if (GlobalVariable.FindDataFile.getValue(financialdata, 29) == 'No') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        'input begining balance'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Beginning Balance_'), 
            GlobalVariable.FindDataFile.getValue(financialdata, 30))

        'Month array'
        montharray = GlobalVariable.FindDataFile.getValue(financialdata, 31).split(';', -1)

        'Year array'
        yeararray = GlobalVariable.FindDataFile.getValue(financialdata, 32).split(';', -1)

        'Debit Transaction count array'
        debitTransactionarray = GlobalVariable.FindDataFile.getValue(financialdata, 33).split(';', -1)

        'debit array'
        debitarray = GlobalVariable.FindDataFile.getValue(financialdata, 34).split(';', -1)

        'credit transaction count array'
        creditTransactionArray = GlobalVariable.FindDataFile.getValue(financialdata, 35).split(';', -1)

        'credit array'
        creditarray = GlobalVariable.FindDataFile.getValue(financialdata, 36).split(';', -1)

        if (copyapp.equalsIgnoreCase('Edit')) {
            variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

            if (variablebankstatement.size() > 0) {
                for (BSindex = 1; BSindex <= variablebankstatement.size(); BSindex++) {
                    'modify object from input credit'
                    modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credit'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[6]/input', true)

                    'modify object from input credit transaction count'
                    modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credittransactioncount'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[5]/input', true)

                    'modify object from input debit'
                    modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debit'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[4]/input', true)

                    'modify object from input debit transaction count'
                    modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debittransactionaccount'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[3]/input', true)

                    'modify object from input year'
                    modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Year'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[2]/input', true)

                    'modify object from select month'
                    modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[1]/select', true)

                    'modify object delete button'
                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[8]/a/i', true)

                    Select selectmonth = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[1]/select')))

                    month = selectmonth.getFirstSelectedOption().getText()

                    year = WebUI.getAttribute(modifyNewinputYear, 'value')

                    if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 31).length() > 0) || (GlobalVariable.FindDataFile.getValue(
                        financialdata, 32).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 33).length() > 
                    0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 34).length() > 0)) || (GlobalVariable.FindDataFile.getValue(
                        financialdata, 35).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 36).length() > 
                    0)) {
                        for (int excelindex = 0; excelindex < montharray.size(); excelindex++) {
                            if ((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) {
                                inputBankStatement(excelindex + 1)

                                break
                            } else {
                                if (excelindex == (montharray.size() - 1)) {
                                    if (WebUI.verifyElementPresent(modifyNewbuttondelete, 3)) {
                                        'click delete button'
                                        WebUI.click(modifyNewbuttondelete)

                                        'acc alert'
                                        WebUI.acceptAlert()

                                        variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

                                        BSindex--
                                    } else {
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            for (int excelindex = 0; excelindex < montharray.size(); excelindex++) {
                'add data yang ada di excel dan belom ada di confins'
                variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

                flagFailed = 0

                if (variablebankstatement.size() > 0) {
                    for (BSindex = 1; BSindex <= variablebankstatement.size(); BSindex++) {
                        'modify object from input credit'
                        modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[6]/input', true)

                        'modify object from input credit transaction count'
                        modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credittransactioncount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[5]/input', true)

                        'modify object from input debit'
                        modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[4]/input', true)

                        'modify object from input debit transaction count'
                        modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debittransactionaccount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[3]/input', true)

                        'modify object from input year'
                        modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Year'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[2]/input', true)

                        'modify object from select month'
                        modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[1]/select', true)

                        'modify object delete button'
                        modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[8]/a/i', true)

                        Select selectmonth = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                BSindex) + ']/td[1]/select')))

                        month = selectmonth.getFirstSelectedOption().getText()

                        year = WebUI.getAttribute(modifyNewinputYear, 'value')

                        if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 31).length() > 0) || (GlobalVariable.FindDataFile.getValue(
                            financialdata, 32).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 33).length() > 
                        0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 34).length() > 0)) || (GlobalVariable.FindDataFile.getValue(
                            financialdata, 35).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 36).length() > 
                        0)) {
                            'verify month year beda'
                            if (!((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) || WebUI.verifyElementNotPresent(modifyNewbuttondelete, 10, FailureHandling.OPTIONAL)) {
                                if (BSindex == variablebankstatement.size()) {
                                    'click add new button'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_banknewrow'))

                                    BSindex++

                                    'modify object from input credit'
                                    modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credit'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[6]/input', true)

                                    'modify object from input credit transaction count'
                                    modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credittransactioncount'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[5]/input', true)

                                    'modify object from input debit'
                                    modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debit'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[4]/input', true)

                                    'modify object from input debit transaction count'
                                    modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debittransactionaccount'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[3]/input', true)

                                    'modify object from input year'
                                    modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Year'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[2]/input', true)

                                    'modify object from select month'
                                    modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[1]/select', true)

                                    'modify object delete button'
                                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[8]/a/i', true)

                                    inputBankStatement(excelindex + 1)
                                }
                            } else if ((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) {
                                break
                            }
                        }
                    }
                } else {
                    inputBankStatementFromEmpty()

                    break
                }
            }
        } else if (copyapp.equalsIgnoreCase('No')) {
            inputBankStatementFromEmpty()
        }
        
        'click button calculate bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_CalculateBank'))

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_SaveBank'))

        Integer iscompleteMandatory = Integer.parseInt(GlobalVariable.FindDataFile.getValue(financialdata, 4))

        if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
            'cek alert'
            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(financialdata, '5.FinancialData')
        }
        
        if (GlobalVariable.FlagFailed == 0) {
            'Check save Process write to excel'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_SaveFinancial'), 
                financialdata, '5.FinancialData')

            if (iscompleteMandatory == 0) {
                errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                'cek validasi'
                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, financialdata, '5.FinancialData')
            }
        }
        
        if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_SaveFinancial'), 
            5, FailureHandling.OPTIONAL)) {
            'click button Cancel'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Cancel'))

            flagWarning++
        }
        
        if (flagWarning > 0) {
            CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 
                0, financialdata - 1, GlobalVariable.StatusWarning)
        }
        
        WebUI.delay(5)
    }
}

def inputBankStatement(int index) {
    'select month'
    WebUI.selectOptionByLabel(modifyNewselectMonth, montharray[(index - 1)], false, FailureHandling.OPTIONAL)

    'input year'
    WebUI.setText(modifyNewinputYear, yeararray[(index - 1)])

    'input debit transaction'
    WebUI.setText(modifyNewinputDebitTransactioncount, debitTransactionarray[(index - 1)])

    'input debit balance'
    WebUI.setText(modifyNewinputDebit, debitarray[(index - 1)])

    'input credit transaction'
    WebUI.setText(modifyNewinputCreditTransactioncount, creditTransactionArray[(index - 1)])

    'input credit balance'
    WebUI.setText(modifyNewinputCredit, creditarray[(index - 1)])
}

def inputBankStatementFromEmpty() {
    'Month array'
    montharray = GlobalVariable.FindDataFile.getValue(financialdata, 31).split(';', -1)

    'Year array'
    yeararray = GlobalVariable.FindDataFile.getValue(financialdata, 32).split(';', -1)

    'Debit Transaction count array'
    debitTransactionarray = GlobalVariable.FindDataFile.getValue(financialdata, 33).split(';', -1)

    'debit array'
    debitarray = GlobalVariable.FindDataFile.getValue(financialdata, 34).split(';', -1)

    'credit transaction count array'
    creditTransactionArray = GlobalVariable.FindDataFile.getValue(financialdata, 35).split(';', -1)

    'credit array'
    creditarray = GlobalVariable.FindDataFile.getValue(financialdata, 36).split(';', -1)

    if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 31).length() > 0) || (GlobalVariable.FindDataFile.getValue(
        financialdata, 32).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 33).length() > 0)) || 
    (GlobalVariable.FindDataFile.getValue(financialdata, 34).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 
        35).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 36).length() > 0)) {
        for (BSindex = 1; BSindex <= montharray.size(); BSindex++) {
            'modify object from input credit'
            modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credit'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[6]/input', true)

            'modify object from input credit transaction count'
            modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credittransactioncount'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[5]/input', true)

            'modify object from input debit'
            modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debit'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[4]/input', true)

            'modify object from input debit transaction count'
            modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debittransactionaccount'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[3]/input', true)

            'modify object from input year'
            modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Year'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[2]/input', true)

            'modify object from select month'
            modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[1]/select', true)

            'click add new button'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_banknewrow'))

            inputBankStatement(BSindex)
        }
    }
}

def convertDate(String date){
	'convert date confins dan excel agar sama'
	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

	Date parsedDate = null

	String sentDate = date

	String sDate

	if (sentDate != '') {
		parsedDate = sdf.parse(sentDate)

		sdf = new SimpleDateFormat('dd MMM YYYY')

		sDate = sdf.format(parsedDate)
	}
	
	return sDate
}
