import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorPersonalCompany

GlobalVariable.DataFilePath = filePath

String maritalStatus = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/label_MaritalStatus'))

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 3).length() > 1) {
    'click button add'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Add'))

    'input monthly income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Monthly Income_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 3))

    'input monthly other income'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Other Income_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 4))

    'pilih source monthly other income'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/select_BusinessOnline ShopSalaryStock Trading'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 5), false)

    monthlyInstallment = findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Monthly Installment Amount_')

    monthlyExpense = findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Monthly Expense Amount_')

    datesAsOf = findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Date As Of_form-')

    buttonCalculate = findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Calculate')

    buttonSave = findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Save')

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
            GlobalVariable.NumofGuarantor, 9).length() >= 1) {
            'Input spouse monthly income'
            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_SpouseMonthlyIncome'), 
                findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
                    GlobalVariable.NumofGuarantor, 9))
        }
        
        'Pengecekan isjoinincome bernilai yes atau no pada excel'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 10).equalsIgnoreCase('Yes')) {
            'Jika is join income belum tercentang'
            if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'centang is join income'
                WebUI.check(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        } else {
            'jika is join income sudah tercentang'
            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'), 
                5, FailureHandling.OPTIONAL)) {
                'Un-centang is join income'
                WebUI.uncheck(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_checkboxIsJoinIncome'))
            }
        }
    }
    
    'input monthly installment'
    WebUI.setText(monthlyInstallment, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 6))

    'input monthly expense'
    WebUI.setText(monthlyExpense, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 7))

    'input dates as of'
    WebUI.setText(datesAsOf, findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 8))

    'click button calculate'
    WebUI.click(buttonCalculate)

    'click button save'
    WebUI.click(buttonSave)
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 12).length() > 1) {
    'input jumlah harta'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Jumlah Harta'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 12))
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantor, 15).length() > 1) {
    'click button add bank'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_AddBank'))

    'click lookup bank'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Bank Name_btn btn-raised btn-primary'))

    'input bank code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Bank Code_BankCode'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 14))

    'click button search bank'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/Button_X'))

        'click cancel'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_Cancel'))

        flagWarning++
    }
    
    'input bank branch name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Bank Branch_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 16))

    'input bank account name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Account Name_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 17))

    'input bank account number'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Account No_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 18))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
        2, FailureHandling.OPTIONAL)) {
        'checkbox default'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 19) == 'Yes') {
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
        }
    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'), 
        2)) {
        'checkbox default'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 19) == 'No') {
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Default_ng-untouched ng-pristine ng-valid'))
        }
    }
    
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
        2, FailureHandling.OPTIONAL)) {
        'checkbox active'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 20) == 'Yes') {
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
        }
    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
        2)) {
        'checkbox active'
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 20) == 'No') {
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
        }
    }
    
    'input bank begining balance'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/input_Beginning Balance_'), 
        findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/FinancialData - Company - GuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantor, 21))

    'click button calculate bank'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_CalculateBank'))

    'click button save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_SaveBank'))
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/button_SaveFinancial'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/FinancialData - Personal/th_Date'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))

    'write to excel if failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusFailed)
} else {
    'write to excel if success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 0, GlobalVariable.NumofGuarantor - 
        1, GlobalVariable.StatusSuccess)

    if (flagWarning > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData', 
            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusWarning)
    }
}

