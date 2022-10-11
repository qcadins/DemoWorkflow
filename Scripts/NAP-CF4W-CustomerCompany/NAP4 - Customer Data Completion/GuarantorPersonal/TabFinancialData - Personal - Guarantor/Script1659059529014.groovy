import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.TexturePaintContext.Byte as Byte
import java.text.SimpleDateFormat as SimpleDateFormat
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
import internal.GlobalVariable as GlobalVariable

GlobalVariable.CopyAppColm = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal')

'get count colm'
countcolm = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getColumnNumbers()

ArrayList<WebElement> variable

'untuk mendapatkan posisi copy app dari excel'
for (index = 2; index <= (countcolm + 1); index++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
        index, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 13))) {
        GlobalVariable.CopyAppColm = index
		GlobalVariable.NumofVerifStore = index

        break
    }
}

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
    10)

if (copyapp.equalsIgnoreCase('Edit')) {
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/labelfainancial_nodataavailable')), 
        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
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

                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                    financialdata, 10).length() != 0) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                        financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantor, 13))) {
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                            financialdata, 13).length() > 0) {
                            if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                                'convert date confins dan excel agar sama'
                                SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

                                Date parsedDate = null

                                String sentDate = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                                    financialdata, 17)

                                parsedDate = sdf.parse(sentDate)

                                sdf = new SimpleDateFormat('dd MMM YYYY')

                                String sDate = sdf.format(parsedDate)

                                modifyDateNew = WebUI.getText(modifyNewDate).replace('-', ' ')

                                if (modifyDateNew.equalsIgnoreCase(sDate)) {
                                    'click button edit'
                                    WebUI.click(modifyNewbuttonedit)

                                    inputfinancialdata()

                                    break
                                } else {
                                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                                        financialdata + 1, 10).length() == 0) {
                                        'click button delete'
                                        WebUI.click(modifyNewbuttondelete)

                                        'accept alert'
                                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

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
    
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

    for (financialdata = GlobalVariable.CopyAppColm; financialdata <= (countcolm + 1); financialdata++) {
        int flagFailed = 0

        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 10).length() != 0) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object Date'
                modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

                'modify object button delete'
                modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation - Personal/select_addressType'), 
                    'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[2]/i', true)

                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                    financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                        GlobalVariable.NumofGuarantor, 13))) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                        financialdata, 13).length() > 0) {
                        'convert date confins dan excel agar sama'
                        SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

                        Date parsedDate = null

                        String sentDate = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                            financialdata, 17)

                        parsedDate = sdf.parse(sentDate)

                        sdf = new SimpleDateFormat('dd MMM YYYY')

                        String sDate = sdf.format(parsedDate)

                        modifyDateNew = WebUI.getText(modifyNewDate).replace('-', ' ')

                        'verify date beda'
                        if (!(modifyDateNew.equalsIgnoreCase(sDate)) || WebUI.verifyElementNotPresent(modifyNewbuttondelete, 
                            5, FailureHandling.OPTIONAL)) {
                            if (i == variable.size()) {
                                'click button add'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Add'))

                                inputfinancialdata()

                                break
                            }
                        } else if (WebUI.getText(modifyNewDate).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                                financialdata, 17))) {
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

        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 10).length() != 0) {
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/CustomerDetail - Company - GuarantorPersonal').getValue(
                    GlobalVariable.NumofGuarantor, 13))) {
                if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                    financialdata, 13).length() > 0) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Add'))

                    inputfinancialdata()
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

if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonalCompany=="Yes"){
		
'call test case verify financial data store data'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabFinancialDataVerifStoreData'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputfinancialdata() {
    int flagWarning = 0

    String maritalStatus = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/label_MaritalStatus'))

    'input monthly income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Monthly Income_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 12))

    'input monthly other income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Other Income_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 13))

    'pilih source monthly other income'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_BusinessOnline ShopSalaryStock Trading'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 14), false)

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
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 18).length() >= 1) {
            'Input spouse monthly income'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_SpouseMonthlyIncome'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                    financialdata, 18))
        }
        
        'Pengecekan isjoinincome bernilai yes atau no pada excel'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 19).equalsIgnoreCase('Yes')) {
            'Jika is join income belum tercentang'
            if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'centang is join income'
                WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        } else if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 19).equalsIgnoreCase('No')) {
            'jika is join income sudah tercentang'
            if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'Un-centang is join income'
                WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        }
    }
    
    'input monthly installment'
    WebUI.setText(monthlyInstallment, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 15))

    'input monthly expense'
    WebUI.setText(monthlyExpense, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 16))

    'input dates as of'
    WebUI.setText(datesAsOf, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 17))

    'click button calculate'
    WebUI.click(buttonCalculate)

    'click button save'
    WebUI.click(buttonSave)

    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
        financialdata, 21).length() > 1) {
        'input jumlah harta'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Jumlah Harta'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 21))
    }
    
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
        financialdata, 24).length() > 1) {
        'click button add bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_AddBank'))

        'click lookup bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_Bank Name_btn btn-raised btn-primary'))

        'input bank code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Bank Code_BankCode'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 23))

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
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 
                0, financialdata - 1, GlobalVariable.StatusWarning)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 
                1, financialdata - 1, GlobalVariable.StatusReasonLookup)

            flagWarning++
        }
        
        'input bank branch name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Bank Branch_'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 25))

        'input bank account name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Account Name_'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 26))

        'input bank account number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Account No_'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 27))

        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            2, FailureHandling.OPTIONAL)) {
            'checkbox default'
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 28) == 'Yes') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            2)) {
            'checkbox default'
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 28) == 'No') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            2, FailureHandling.OPTIONAL)) {
            'checkbox active'
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 29) == 'Yes') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            2)) {
            'checkbox active'
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 29) == 'No') {
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        'input begining balance'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Beginning Balance_'), 
            findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 30))

        'Month array'
        montharray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 31).split(';', -1)

        'Year array'
        yeararray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 32).split(';', -1)

        'Debit Transaction count array'
        debitTransactionarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 33).split(';', -1)

        'debit array'
        debitarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 34).split(';', -1)

        'credit transaction count array'
        creditTransactionArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 35).split(';', -1)

        'credit array'
        creditarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 36).split(';', -1)

        if ((((((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 31).length() > 0) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 32).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 33).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 34).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 35).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            financialdata, 36).length() > 0)) {
            for (i = 1; i <= montharray.size(); i++) {
                'modify object from input credit'
                modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credit'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[6]/input', 
                    true)

                'modify object from input credit transaction count'
                modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_credittransactioncount'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[5]/input', 
                    true)

                'modify object from input debit'
                modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debit'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[4]/input', 
                    true)

                'modify object from input debit transaction count'
                modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_debittransactionaccount'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[3]/input', 
                    true)

                'modify object from input year'
                modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/input_Year'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[2]/input', 
                    true)

                'modify object from select month'
                modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/select_Month'), 
                    'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + i) + ']/td[1]/select', 
                    true)

                'click add new button'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_banknewrow'))

                'select month'
                WebUI.selectOptionByLabel(modifyNewselectMonth, montharray[(i - 1)], false, FailureHandling.OPTIONAL)

                'input year'
                WebUI.setText(modifyNewinputYear, yeararray[(i - 1)])

                'input debit transaction'
                WebUI.setText(modifyNewinputDebitTransactioncount, debitTransactionarray[(i - 1)])

                'input debit balance'
                WebUI.setText(modifyNewinputDebit, debitarray[(i - 1)])

                'input credit transaction'
                WebUI.setText(modifyNewinputCreditTransactioncount, creditTransactionArray[(i - 1)])

                'input credit balance'
                WebUI.setText(modifyNewinputCredit, creditarray[(i - 1)])
            }
        }
        
        'click button calculate bank'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_CalculateBank'))

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData - Personal/button_SaveBank'))

        Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                financialdata, 4))

        if (iscompleteMandatory == 0) {
            'cek alert'
            flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(financialdata, '5.FinancialData')
        }
        
        if (flagFailed == 0) {
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
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 
                0, financialdata - 1, GlobalVariable.StatusWarning)
        }
        
        WebUI.delay(5)
    }
}

