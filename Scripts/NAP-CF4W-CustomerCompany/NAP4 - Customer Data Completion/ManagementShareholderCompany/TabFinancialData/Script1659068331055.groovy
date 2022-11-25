import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.ArrayList as ArrayList
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
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)

'declare GV finddatafile'
GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/FinancialData - Company - ManagementShareholderCompany')

'declare datafile cust detail'
datafilecustdetail = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail - Company - ManagementShareholderCompany')

GlobalVariable.CopyAppColm = 0

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

'declare arraylist financialdatadelete'
ArrayList<String> financialDateDelete = new ArrayList<>()

'declare arraylist bankaccdelete'
ArrayList<String> bankAccDelete = new ArrayList<>()

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (GlobalVariable.FindDataFile.getValue(index, 10).equalsIgnoreCase(datafilecustdetail.getValue(GlobalVariable.NumofMS, 
            13))) {
        GlobalVariable.CopyAppColm = index

        GlobalVariable.NumofVerifStore = index

        break
    }
}

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofMS, 
    10)

'Check if Edit Untuk Financial Data'
if (copyapp.equalsIgnoreCase('Edit')) {
    'count table financialdata confins'
    ArrayList<Boolean> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/label_nodataavailable'), 
            FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        for (i = 1; i <= variable.size(); i++) {
            'modify object Date'
            modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

            for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
                GlobalVariable.FlagFailed = 0

                if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(
                            GlobalVariable.NumofMS, 12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(
                        datafilecustdetail.getValue(GlobalVariable.NumofMS, 13))) {
                        'modify object button edit'
                        modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                            'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a/i', true)

                        if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(
                                    financialdata, 35)))) {
                            'click button edit'
                            WebUI.click(modifyNewbuttonedit)

                            'call function input data financial'
                            inputDataFinancial()

                            break
                        } else {
                            'modify object button delete'
                            modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                                'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[2]/i', 
                                true)

                            if (GlobalVariable.FindDataFile.getValue(financialdata + 1, 9).length() == 0) {
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
                    }
                } else {
                    break
                }
            }
        }
    }
    
    if (financialDateDelete.size() > 0) {
        'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.FinancialData', 0, GlobalVariable.CopyAppColm - 
            1, GlobalVariable.StatusWarning)

        'write to excel reason delete failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.FinancialData', 1, GlobalVariable.CopyAppColm - 
            1, GlobalVariable.ReasonFailedDelete + financialDateDelete)

        (GlobalVariable.FlagWarning)++
    }
    
    'count ulang table financial data setelah edit/delete'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        GlobalVariable.FlagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object Date'
                modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

                'modify object button edit'
                modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a/i', true)

                if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(
                        GlobalVariable.NumofMS, 12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(
                    datafilecustdetail.getValue(GlobalVariable.NumofMS, 13))) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 12).length() > 0) {
                        'verify date beda'
                        if (!(WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(
                                    financialdata, 35)))) || WebUI.verifyElementNotPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                            if (i == variable.size()) {
                                'click button add'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Add'))

                                inputDataFinancial()

                                break
                            }
                        } else if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(convertDate(GlobalVariable.FindDataFile.getValue(
                                    financialdata, 35)))) {
                            break
                        }
                    }
                }
            }
        } else {
            break
        }
    }
} else if (copyapp == 'No') {
    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
            if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(GlobalVariable.NumofMS, 
                    12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(datafilecustdetail.getValue(
                    GlobalVariable.NumofMS, 13))) {
                if (GlobalVariable.FindDataFile.getValue(financialdata, 12).length() > 0) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Add'))

                    'call funtion input financial data'
                    inputDataFinancial()
                }
            }
        } else {
            break
        }
    }
}

'count financial attr label'
variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#AttributeList > div label'))

int row = 37

for (i = 1; i <= variable.size(); i++) {
    'modify object input fin attr'
    modifyinputFinAttr = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
        'xpath', 'equals', ('//*[@id="AttributeList"]/div/div[' + i) + ']/div/div/input', true)

    'input posisi laporan'
    WebUI.setText(modifyinputFinAttr, GlobalVariable.FindDataFile.getValue(GlobalVariable.CopyAppColm, row++))
}

'Check if Edit Untuk Bank Account dan Bank Statement'
if (copyapp.equalsIgnoreCase('Edit')) {
    'count table bank acc confins'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

    if (variable.size() > 0) {
        for (i = 1; i <= variable.size(); i++) {
            'modify bank name - branch - bank no'
            modifyNewbankaccdetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/h5', true)

            BankDetail = WebUI.getText(modifyNewbankaccdetail)

            for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
                int flagFailed = 0

                if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
                    if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(
                            GlobalVariable.NumofMS, 12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(
                        datafilecustdetail.getValue(GlobalVariable.NumofMS, 13))) {
                        if (GlobalVariable.FindDataFile.getValue(financialdata, 67).length() > 0) {
                            bankdetailexcel = ((((((('- ' + GlobalVariable.FindDataFile.getValue(financialdata, 67)) + ' - ') + 
                            GlobalVariable.FindDataFile.getValue(financialdata, 68)) + ' - ') + GlobalVariable.FindDataFile.getValue(
                                financialdata, 70)) + ' - ') + GlobalVariable.FindDataFile.getValue(financialdata, 69))

                            if (BankDetail.equalsIgnoreCase(bankdetailexcel)) {
                                'modify button edit'
                                modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                                    'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[1]', 
                                    true)

                                'click button edit'
                                WebUI.click(modifyNewbuttonedit)

                                inputBank(copyapp, variable, flagFailed)

                                break
                            } else {
                                if (GlobalVariable.FindDataFile.getValue(financialdata + 1, 10).length() == 0) {
                                    'modify button delete'
                                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[2]', 
                                        true)

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
                        }
                    }
                } else {
                    break
                }
            }
        }
    }
    
    if (bankAccDelete.size() > 0) {
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 0, GlobalVariable.CopyAppColm - 
            1, GlobalVariable.StatusWarning)

        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 1, GlobalVariable.CopyAppColm - 
            1, GlobalVariable.ReasonFailedDelete + financialDateDelete)

        (GlobalVariable.FlagWarning)++
    }
    
    'count ulang bank acc confins'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
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

                    if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(
                            GlobalVariable.NumofMS, 12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(
                        datafilecustdetail.getValue(GlobalVariable.NumofMS, 13))) {
                        if (GlobalVariable.FindDataFile.getValue(financialdata, 67).length() > 0) {
                            bankdetailexcel = ((((((('- ' + GlobalVariable.FindDataFile.getValue(financialdata, 67)) + ' - ') + 
                            GlobalVariable.FindDataFile.getValue(financialdata, 68)) + ' - ') + GlobalVariable.FindDataFile.getValue(
                                financialdata, 70)) + ' - ') + GlobalVariable.FindDataFile.getValue(financialdata, 69))

                            'verify bank beda'
                            if (!(BankDetail.equalsIgnoreCase(bankdetailexcel)) || WebUI.verifyElementNotPresent(modifyNewbuttondelete, 
                                10, FailureHandling.OPTIONAL)) {
                                if (i == variable.size()) {
                                    'click button add bank'
                                    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_addBank'))

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
                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_addBank'))

                inputBank(copyapp, variable, flagFailed)
            }
        } else {
            break
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (GlobalVariable.FindDataFile.getValue(financialdata, 9).length() != 0) {
            if (GlobalVariable.FindDataFile.getValue(financialdata, 9).equalsIgnoreCase(datafilecustdetail.getValue(GlobalVariable.NumofMS, 
                    12)) && GlobalVariable.FindDataFile.getValue(financialdata, 10).equalsIgnoreCase(datafilecustdetail.getValue(
                    GlobalVariable.NumofMS, 13))) {
                if (GlobalVariable.FindDataFile.getValue(financialdata, 67).length() > 0) {
                    'click button add bank'
                    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_addBank'))

                    inputBank(copyapp, variable, flagFailed)
                }
            }
        } else {
            break
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/th_Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
	'call test case verify financial data store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabFinancialDataVerifStoreDataDB'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputDataFinancial() {
	GlobalVariable.FlagWarning = 0

	'input gross monthly income amount'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Monthly Income'),
		GlobalVariable.FindDataFile.getValue(financialdata, 12))

	'input gross profit amount'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Monthly Expense'),
		GlobalVariable.FindDataFile.getValue(financialdata, 13))

	'input return of investment percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Investment()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 14))

	'input return of equity percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Equity()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 15))

	'input return of asset percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Asset()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 16))

	'input profit margin percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Profit Margin()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 17))

	'input current ratio percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Ratio()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 18))

	'debt equity ratio percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Debt Equity Ratio()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 19))

	'inventory turn over percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Invetory Turn Over()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 20))

	'input account receivable turn over'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_AR Turn Over()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 21))

	'input growth percentage'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Growth()'),
		GlobalVariable.FindDataFile.getValue(financialdata, 22))

	'input working capital amount'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Working Capital (Amount)'),
		GlobalVariable.FindDataFile.getValue(financialdata, 23))

	'input other monthly installment amount'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Other Monthly Installment'),
		GlobalVariable.FindDataFile.getValue(financialdata, 24))

	'input operational cost'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Operational Cost'),
		GlobalVariable.FindDataFile.getValue(financialdata, 26))

	'input revenue '
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Revenue'),
		GlobalVariable.FindDataFile.getValue(financialdata, 25))

	'input current asset'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Asset'),
		GlobalVariable.FindDataFile.getValue(financialdata, 28))

	'input net fixed asset'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Profit Before Tax'),
		GlobalVariable.FindDataFile.getValue(financialdata, 27))

	'input total asset'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Total Asset'),
		GlobalVariable.FindDataFile.getValue(financialdata, 30))

	'input profit before tax'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Nett Fixed Asset'),
		GlobalVariable.FindDataFile.getValue(financialdata, 29))

	'input long term liabilities'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Long Term Liabilities'),
		GlobalVariable.FindDataFile.getValue(financialdata, 32))

	'input current liabilities'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Liabilities'),
		GlobalVariable.FindDataFile.getValue(financialdata, 31))

	'input current ratio'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Ratio'),
		GlobalVariable.FindDataFile.getValue(financialdata, 34))

	'input shareholder equity'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Shareholder Equity'),
		GlobalVariable.FindDataFile.getValue(financialdata, 33))

	'input date as of'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Date As Of'),
		GlobalVariable.FindDataFile.getValue(financialdata, 35))

	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_SaveFinancialData'))
}

def inputBank(String copyapp, ArrayList<Boolean> variable, int flagFailed) {
	GlobalVariable.FlagWarning = 0

	if (GlobalVariable.FindDataFile.getValue(financialdata, 67).length() > 1) {
		'click bank lookup'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Bank Name_btn btn-raised btn-primary'))

		'input bank code'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Code_BankCode'),
			GlobalVariable.FindDataFile.getValue(financialdata, 66))

		'input bank name'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Name_BankName'),
			GlobalVariable.FindDataFile.getValue(financialdata, 67))

		'click button search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Search'),
			FailureHandling.OPTIONAL)

		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/a_Select'),
			10, FailureHandling.OPTIONAL)) {
			'click select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/a_Select'))
		} else {
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/Button_X'))

			'Click cancel'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Cancel'))

			'Write To Excel GlobalVariable.StatusWarning'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.FinancialData', 0,
				financialdata - 1, GlobalVariable.StatusWarning)

			'Write To Excel GlobalVariable.StatusReasonLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.FinancialData', 1,
				financialdata - 1, GlobalVariable.StatusReasonLookup)

			GlobalVariable.FlagWarning++
		}
		
		'input bank branch'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Branch'),
			GlobalVariable.FindDataFile.getValue(financialdata, 68))

		'input account name'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Account Name'),
			GlobalVariable.FindDataFile.getValue(financialdata, 69))

		'input account no'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Account No'),
			GlobalVariable.FindDataFile.getValue(financialdata, 70))

		if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'),
			2, FailureHandling.OPTIONAL)) {
			if (GlobalVariable.FindDataFile.getValue(financialdata, 71) == 'Yes') {
				'click is default'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'))
			}
		} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'),
			2)) {
			if (GlobalVariable.FindDataFile.getValue(financialdata, 71) == 'No') {
				'click is default'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'))
			}
		}
		
		if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'),
			2, FailureHandling.OPTIONAL)) {
			if (GlobalVariable.FindDataFile.getValue(financialdata, 72) == 'Yes') {
				'click is active'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'))
			}
		} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'),
			2)) {
			if (GlobalVariable.FindDataFile.getValue(financialdata, 72) == 'No') {
				'click is active'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'))
			}
		}
		
		'input begining balance'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Beginning Balance'),
			GlobalVariable.FindDataFile.getValue(financialdata, 73))

		'Month array'
		montharray = GlobalVariable.FindDataFile.getValue(financialdata, 74).split(';', -1)

		'Year array'
		yeararray = GlobalVariable.FindDataFile.getValue(financialdata, 75).split(';', -1)

		'Debit Transaction count array'
		debitTransactionarray = GlobalVariable.FindDataFile.getValue(financialdata, 76).split(';', -1)

		'debit array'
		debitarray = GlobalVariable.FindDataFile.getValue(financialdata, 77).split(';', -1)

		'credit transaction count array'
		creditTransactionArray = GlobalVariable.FindDataFile.getValue(financialdata, 78).split(';', -1)

		'credit array'
		creditarray = GlobalVariable.FindDataFile.getValue(financialdata, 79).split(';', -1)

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

					if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 74).length() > 0) || (GlobalVariable.FindDataFile.getValue(
						financialdata, 75).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 76).length() >
					0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 77).length() > 0)) || (GlobalVariable.FindDataFile.getValue(
						financialdata, 78).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 79).length() >
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

						if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 74).length() > 0) || (GlobalVariable.FindDataFile.getValue(
							financialdata, 75).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 76).length() >
						0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 77).length() > 0)) || (GlobalVariable.FindDataFile.getValue(
							financialdata, 78).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 79).length() >
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
		
		'click calculate bank'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_CalculateBank'))

		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save'))

		if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(financialdata, 4)) == 0) && (GlobalVariable.FlagFailed ==
		0)) {
			'Check alert'
			GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(financialdata, '4.FinancialData')
		}
		
		if (GlobalVariable.FlagFailed == 0) {
			'Check save Process write to excel'
			CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
						financialdata, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save  Continue'),
				financialdata, '4.FinancialData')

			if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(financialdata, 4)) == 0) {
				'Check error validasi'
				CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'),
					financialdata, '4.FinancialData')
			}
		}
		
		if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save  Continue'),
			10, FailureHandling.OPTIONAL)) {
			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Cancel Bank'))
		}
		
		if (GlobalVariable.FlagWarning > 0) {
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4.FinancialData', 0,
				financialdata - 1, GlobalVariable.StatusWarning)
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
	montharray = GlobalVariable.FindDataFile.getValue(financialdata, 74).split(';', -1)

	'Year array'
	yeararray = GlobalVariable.FindDataFile.getValue(financialdata, 75).split(';', -1)

	'Debit Transaction count array'
	debitTransactionarray = GlobalVariable.FindDataFile.getValue(financialdata, 76).split(';', -1)

	'debit array'
	debitarray = GlobalVariable.FindDataFile.getValue(financialdata, 77).split(';', -1)

	'credit transaction count array'
	creditTransactionArray = GlobalVariable.FindDataFile.getValue(financialdata, 78).split(';', -1)

	'credit array'
	creditarray = GlobalVariable.FindDataFile.getValue(financialdata, 79).split(';', -1)

	if ((((((GlobalVariable.FindDataFile.getValue(financialdata, 74).length() > 0) || (GlobalVariable.FindDataFile.getValue(
		financialdata, 75).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 76).length() > 0)) ||
	(GlobalVariable.FindDataFile.getValue(financialdata, 77).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata,
		78).length() > 0)) || (GlobalVariable.FindDataFile.getValue(financialdata, 79).length() > 0)) {
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

def convertDate(String date) {
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