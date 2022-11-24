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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import groovy.sql.Sql as Sql
import org.codehaus.groovy.ast.stmt.ContinueStatement as ContinueStatement

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'connect DB FOU'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

GlobalVariable.FlagFailed = 0

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Arraylist untuk menampung Income Information'
ArrayList<String> varIncomeInfo = driver.findElements(By.cssSelector('#viewIncomeInfo label'))

'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countIncomeInfo = varIncomeInfo.size() / 2

'Ambil appNo dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/span_appNo'))

//String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))
//Pengecekan app last step sementara dilakukan dengan pengecekan dari db karena pengecekan melalui view confins masih issue.
String appLastStep = CustomKeywords.'dbConnection.checkStep.checkLastStep'(sqlconnectionLOS, appNo)

if (!(appLastStep.equalsIgnoreCase('UPL_DOC')) && (GlobalVariable.FirstTimeEntry == 'Yes')) {
    GlobalVariable.FirstTimeEntry = 'No'
}

'Hashmap untuk menampung arraylist refund allocation dan refund amount dari membaca rule file'
HashMap<String, ArrayList> result = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.verifyIncomeInfoAmtRuleBased'(
    sqlconnectionLOS, appNo)

'dedclare arraylist refunForm'
ArrayList<String> refundFrom = result.get('From')

'declare arraylist refunAmt'
ArrayList<String> refundAmt = result.get('Amt')

'Arraylist untuk menampung total amount dari allocate commission (upping rate, admin fee, dsb)'
ArrayList<String> TotalAllocateCommissionAmt = new ArrayList<String>()

'declare arraylist commissiondelete'
ArrayList<String> commissiondelete = new ArrayList<String>()

'check if role = testing'
if (GlobalVariable.RoleCompany == 'Testing') {
    'Looping untuk set nilai awal 0 untuk total amount allocate commission dan verif income info berdasarkan rule file'
    for (int i = 0; i < countIncomeInfo; i++) {
        TotalAllocateCommissionAmt.add(0.00)

        if (i == (countIncomeInfo - 1)) {
            'modify object allocated amount'
            modifyObjectMaxAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_MaxAllocatedAmtIncome'), 
                'xpath', 'equals', ('//*[@id=\'viewIncomeInfo\']/div[' + (i + 1)) + ']/div[2]/label', true)

            'modify object remaining allocated amount'
            modifyObjectRemainingAllocatedAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_RemainingAllocatedAmount'), 
                'xpath', 'equals', ('//*[@id=\'viewRemainIncomeInfo\']/div[' + (i + 1)) + ']/div[2]/label', true)

            'cek max allocated amount = remaining allocated amount before calculate'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyObjectMaxAllocatedAmount), WebUI.getText(modifyObjectRemainingAllocatedAmount), 
                    false), '12.TabCommissionData', GlobalVariable.NumofColm)

            break
        }
        
        'modify object income info amt'
        modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'), 
            'xpath', 'equals', ('//*[@id=\'viewIncomeInfo\']/div[' + (i + 1)) + ']/div/div[2]/label', true)

        'modify object remaining info amt'
        modifyObjectRemainingInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_UppingRateRemaining'), 
            'xpath', 'equals', ('//*[@id=\'viewRemainIncomeInfo\']/div[' + (i + 1)) + ']/div/div[2]/label', true)

        'cek income info = remaining info before calculate'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyObjectIncomeInfoAmt), WebUI.getText(modifyObjectRemainingInfoAmt), 
                false), '12.TabCommissionData', GlobalVariable.NumofColm)

        if ((GlobalVariable.CheckRuleCompany == 'Yes') && (GlobalVariable.FirstTimeEntry == 'Yes')) {
            'modify objecy income info'
            modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Upping Rate'), 
                'xpath', 'equals', ('//*[@id=\'viewIncomeInfo\']/div[' + (i + 1)) + ']/div/div[1]/label', true)

            'Varibel String untuk mengambil dan menampung income information'
            String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo)

            'Verif jika income info allocation sesuai dengan rule file'
            if (WebUI.verifyMatch(textIncomeInfo, ('.*' + (refundFrom[i])) + '.*', true)) {
                BigDecimal getAmountFromAppDB = 1

                'Pengecekan income info allocation untuk menentukan data-data amount apa saja yang diambil dari db untuk penghitungan'
                if (textIncomeInfo.equalsIgnoreCase('Upping Rate')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkDiffRateAmtValue'(
                        sqlconnectionLOS, appNo)
                } else if (textIncomeInfo.equalsIgnoreCase('Insurance Income')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkInsValue'(sqlconnectionLOS, 
                        appNo)
                } else if (textIncomeInfo.equalsIgnoreCase('Life Insurance Income')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkLifeInsValue'(sqlconnectionLOS, 
                        appNo)
                } else if (textIncomeInfo.equalsIgnoreCase('Admin Fee')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkAdminFeeValue'(
                        sqlconnectionLOS, appNo)
                } else if (textIncomeInfo.equalsIgnoreCase('Provision Fee')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkProvisionFeeValue'(
                        sqlconnectionLOS, appNo)
                } else if (textIncomeInfo.equalsIgnoreCase('Other Fee')) {
                    getAmountFromAppDB = CustomKeywords.'commissionReserveFundData.verifyIncomeInfo.checkOtherFeeValue'(
                        sqlconnectionLOS, appNo)
                }
                
                'get text modify objecy income info amt'
                String textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt)

                'Verif income info amount yang muncul pada confins sesuai dengan rumus perhitungan rule'
                if (WebUI.verifyEqual(Math.round(Double.parseDouble(textIncomeInfoAmt.replace(',', ''))), Math.round(getAmountFromAppDB * 
                        Double.parseDouble(refundAmt[i]))) == false) {
                    'Write to Excel FAILED'
                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                    'Write To Excel GlobalVariable.ReasonFailedVerifyRule'
                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
                        1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedVerifyRule)

                    GlobalVariable.FlagFailed = 1
                }
            }
        }
    }
}

'Mengambil nilai allocation type dari excel'
allocationType = datafileCommission.getValue(GlobalVariable.NumofColm, 12)

'Select dropdownlist allocation type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
    allocationType, false, FailureHandling.OPTIONAL)

//Supplier
'Arraylist untuk menampung List Supplier'
ArrayList<String> variableSupp = driver.findElements(By.cssSelector('#formInformationSupplier h4'))

'Mengambil nilai row keberapa dimulai data supplier commission pada excel'
def supRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Supplier Commission Data') + 
1

'Pengecekan Jika terdapat minimal 1 List Supplier'
if (variableSupp.size() > 0) {
    'Mengambil nilai supplier name dari web confins'
    String supplierName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SupplierName'))

    'Mengambil nilai supplier name yang akan didelete dari excel'
    String deleteSupp = datafileCommission.getValue(GlobalVariable.NumofColm, supRow + 
        1)

    'Pengecekan jika supplier name pada confins sama dengan supplier name yang akan didelete'
    if (supplierName.equalsIgnoreCase(deleteSupp)) {
        'Get supplier name'
        supplierDelete = WebUI.getText(supplierName)

        'Click icon delete (tempat sampah)'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/i_deletesupp'))

        'Click OK pada alert'
        WebUI.acceptAlert()

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SupplierName'), 
            5, FailureHandling.OPTIONAL)) {
            commissiondelete.add(supplierDelete)
        }
    } else {
        String supplierCode

        'Select dropdownlist bank account pada supplier'
        WebUI.selectOptionByIndex(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_BankAccountSupplier'), 
            datafileCommission.getValue(GlobalVariable.NumofColm, supRow + 2), FailureHandling.OPTIONAL)

        'Query Select pada database untuk mengambil nilai supplier code'
        supplierCode = CustomKeywords.'commissionReserveFundData.checkCommissionCode.checkSupplierCode'(sqlconnectionFOU, 
            supplierName)

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
        ArrayList<String> variableAllocateCommissionLeft = driver.findElements(By.cssSelector(('#ListAllocated' + supplierCode) + 
                ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
        ArrayList<String> variableAllocateCommissionRight = driver.findElements(By.cssSelector(('#ListAllocated' + supplierCode) + 
                ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))

        'Menghitung count (size dari variabel) sebelah kiri'
        int countAllocateCommissionLeft = variableAllocateCommissionLeft.size()

        'Menghitung count (size dari variabel) sebelah kanan'
        int countAllocateCommissionRight = variableAllocateCommissionRight.size()

        'Menghitung count (size dari variabel) gabungan kiri dan kanan'
        int countAllocateCommission = countAllocateCommissionLeft + countAllocateCommissionRight

        'Looping Jumlah Allocate Commission From'
        for (int i = 1; i <= countAllocateCommission; i++) {
            String newxpathCommissionAmt

            String newxpathCommissionPercentage

            String newxpathAllocateCommission

            'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
            if (i <= countAllocateCommissionLeft) {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + i) + 
                ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + 
                i) + ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + 
                i) + ']/div/label[1]')
            } else {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + i) + 
                ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + 
                i) + ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + 
                i) + ']/div/label[1]')
            }
            
            'modify object allocate commission'
            modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Allocate Commission Supp'), 
                'xpath', 'equals', newxpathAllocateCommission, true)

            'Menyimpan nama fee/income allocate commission from dari web confins'
            String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(' (AMT/%)', '').replaceAll(
                '\\s', '')

            'modify object commision amt'
            modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input_Admin Fee SUPP (Amt)'), 
                'xpath', 'equals', newxpathCommissionAmt, true)

            'modify object commission percentage'
            modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input__AdminFee SUPP (Percentage)'), 
                'xpath', 'equals', newxpathCommissionPercentage, true)

            'Pengecekan allocation type jika amount'
            if (allocationType == 'Amount') {
                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * i) + 1) + 
                    supRow) != '') {
                    'Input Amount, 2i+1, +1 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+1 = 3+supRow  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                    WebUI.setText(modifyObjectCommissionAmt, datafileCommission.getValue(
                            GlobalVariable.NumofColm, ((2 * i) + 1) + supRow), FailureHandling.OPTIONAL)
                }
                
                'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
                WebUI.click(modifyObjectCommissionPercentage)
            } else if (allocationType == 'Percentage') {
                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * i) + 2) + 
                    supRow) != '') {
                    'Input Percentage, 2i+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+2 = 4+supRow  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                    WebUI.setText(modifyObjectCommissionPercentage, datafileCommission.getValue(
                            GlobalVariable.NumofColm, ((2 * i) + 2) + supRow), FailureHandling.OPTIONAL)
                }
                
                'Klik pada inputan amount untuk merefresh/merubah nilai amount'
                WebUI.click(modifyObjectCommissionAmt)
            }
            
            'check if role == testing'
            if (GlobalVariable.RoleCompany == 'Testing') {
                'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
                String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt, 'value').replace(',', '')

                'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
                String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage, 'value')

                'Parsing string amount menjadi bentuk double'
                BigDecimal amt = Double.parseDouble(textAmount)

                'Parsing string percentage menjadi bentuk double'
                BigDecimal pctg = Double.parseDouble(textPercentage)

                String textIncomeInfoAmt

                BigDecimal incomeInfoAmt

                'Looping Income Information'
                for (int j = 1; j <= countIncomeInfo; j++) {
                    'modify object income info'
                    modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Upping Rate'), 
                        'xpath', 'equals', ('//*[@id="viewIncomeInfo"]/div[' + j) + ']/div/div[1]/label', true)

                    'Varibel String untuk mengambil dan menampung income information'
                    String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll('\\s', '')

                    'modify objecy income info amt'
                    modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'), 
                        'xpath', 'equals', ('//*[@id="viewIncomeInfo"]/div[' + j) + ']/div/div[2]/label', true)

                    'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
                    if (textIncomeInfo == allocateCommission) {
                        'Variabel untuk mengambil amount income information yang sesuai'
                        textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(',', '')

                        'Parsing string amount income information menjadi bentuk double'
                        incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)

                        'Ambil nilai total allocate commission amount ke j-1'
                        GetTotalAllocateCommissionAmt = TotalAllocateCommissionAmt.get(j - 1)

                        'Pengecekan apakah amount dan percentage keduanya tidak bernilai 0 atau tidak'
                        if ((amt != 0) && (pctg != 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(j - 1, GetTotalAllocateCommissionAmt + ((pctg / 100) * incomeInfoAmt))
                        } else if ((amt == 0) || (pctg == 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(j - 1, GetTotalAllocateCommissionAmt + amt)
                        }
                        
                        'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
                        break
                    }
                }
                
                'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
                if ((amt != 0) && (pctg != 0)) {
                    'Verify hasil hitungan percentage dengan amount'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round((pctg / 100) * incomeInfoAmt), amt), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)

                    'Verify hasil hitungan amount dengan percentage'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(((amt / incomeInfoAmt) * 100) * 100) / 100, Math.round(
                                pctg * 100) / 100), '12.TabCommissionData', GlobalVariable.NumofColm)
                } else if ((amt == 0) || ((pctg == 0) && (allocateCommission != 'PACKET'))) {
                    'Verify amount dan percentage keduanya bernilai sama yaitu nol'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(amt), Math.round(pctg)), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)
                }
            }
        }
    }
}

//Supplier Employee
'Mengambil nilai row keberapa dimulai data supplier employee commission pada excel'
def suppEmpRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 
    'Supplier Employee Commission Data') + 1

'Arraylist untuk menampung List Supplier Employee'
ArrayList<String> variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))

'Pengecekan jika terdapat minimal 1 Supplier Employee'
if (variableSuppEmp.size() > 0) {
    'Looping Supplier Employee List'
    for (int i = 1; i <= variableSuppEmp.size(); i++) {
        'Variabel untuk flagging ketika berhasil melakukan delete'
        int flagdelSuppEmployee = 0

        newxpathSupplierEmpName = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[1]/label')

        'modify object supp emp name'
        modifyObjectSuppEmpName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SuppEmpName'), 
            'xpath', 'equals', newxpathSupplierEmpName, true)

        'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
        String supplierEmployeeName = WebUI.getText(modifyObjectSuppEmpName)

        newxpathSuppName = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1)) + ']/div/div[2]/div[4]/label')

        'modify object supplier name'
        modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SupplierNameSuppEmp'), 
            'xpath', 'equals', newxpathSuppName, true)

        'Variabel string untuk mengambil nama dari supplier ke-i dari web confins'
        supplierName = WebUI.getText(modifyObjectSupplierName)

        newxpathSupplierEmpPos = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1)) + ']/div/div[2]/div[2]/label')

        'modify object supplier emp pos'
        modifyObjectSupplierEmpPos = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_SuppEmpPosition'), 
            'xpath', 'equals', newxpathSupplierEmpPos, true)

        'Variabel string untuk mengambil posisi dari supplier employee ke-i dari web confins'
        String supplierEmpPos = WebUI.getText(modifyObjectSupplierEmpPos)

        'Mengambil dan menyimpan nama supplier employee dari excel yang akan didelete'
        def deleteSuppEmpName = datafileCommission.getValue(GlobalVariable.NumofColm, suppEmpRow + 
            1)

        'Mengambil dan menyimpan posisi dari supplier employee dari excel yang akan didelete'
        def deleteSuppEmpPos = datafileCommission.getValue(GlobalVariable.NumofColm, suppEmpRow + 
            2)

        'Pengecekan pada excel jika ada supplier employee yang ingin didelete'
        if ((deleteSuppEmpName.length() > 0) && (deleteSuppEmpPos.length() > 0)) {
            'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 supplier employee'
            deleteSuppEmpPos = deleteSuppEmpPos.split(';')

            'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 posisi dari supplier employee'
            deleteSuppEmpName = deleteSuppEmpName.split(';')

            'Looping nama supplier employee yang akan didelete pada excel'
            for (j = 1; j <= deleteSuppEmpName.size(); j++) {
                'Pengecekan jika nama supplier employee pada web confins sama dengan nama supplier employee dari excel yang akan didelete dan posisinya pada web confins sama dengan posisi dari supplier employee dari excel yang akan didelete'
                if (supplierEmployeeName.equalsIgnoreCase(deleteSuppEmpName[(j - 1)]) && supplierEmpPos.equalsIgnoreCase(
                    deleteSuppEmpPos[(j - 1)])) {
                    newxpathDelSuppEmp = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[2]/button/i')

                    'modify object delete supp emp'
                    modifyDelSuppEmp = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/i_deleteSuppEmp'), 
                        'xpath', 'equals', newxpathDelSuppEmp, true)

                    supplierEmpDelete = supplierEmployeeName

                    'Klik icon delete pada supplier employee tersebut (tempat sampah)'
                    WebUI.click(modifyDelSuppEmp)

                    'Klik OK pada alert yang muncul'
                    WebUI.acceptAlert()

                    if (i == variableSuppEmp.size()) {
                        if (WebUI.verifyElementNotPresent(modifyObjectSuppEmpName, 5, FailureHandling.OPTIONAL)) {
                            'Digunakan untuk menghitung jumlah list supplier employee setelah operasi delete selesai dilakukan'
                            variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))
                        } else {
                            'add cust name failed kedalam array'
                            commissiondelete.add(supplierEmpDelete)

                            continue
                        }
                    } else {
                        'get cust name setelah delete'
                        supplierEmpNameAfter = WebUI.getText(modifyObjectSuppEmpName)

                        if (WebUI.verifyNotMatch(supplierEmpNameAfter, supplierEmpDelete, false, FailureHandling.OPTIONAL)) {
                            'Digunakan untuk menghitung jumlah list supplier employee setelah operasi delete selesai dilakukan'
                            variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))
                        } else {
                            'add cust name failed kedalam array'
                            commissiondelete.add(supplierEmpDelete)

                            continue
                        }
                    }
                    
                    i = (i - 1)

                    flagdelSuppEmployee++
                }
            }
        }
        
        'Pengecekan jika flag delete tidak sama dengan nol, terjadi setelah proses delete dilakukan'
        if (flagdelSuppEmployee != 0) {
            'Lanjut ke i/supplier Employee berikutnya'
            continue
        }
        
        String supplierEmployeeCode

        newxpathBankAccount = (('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1)) + ']/div/div[3]/div[1]/div[2]/select')

        'modify object bank account'
        modifyObjectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_BankAccountSuppEmp'), 
            'xpath', 'equals', newxpathBankAccount, true)

        'Mengambil nama bank account supplier employee pada excel'
        def bankAccSuppEmp = datafileCommission.getValue(GlobalVariable.NumofColm, suppEmpRow + 
            3).split(';')

        'Select dropdownlist bank account dari supplier employee'
        WebUI.selectOptionByIndex(modifyObjectBankAccount, bankAccSuppEmp[(i - 1)], FailureHandling.OPTIONAL)

        'Query select pada database untuk mengambil nilai supplier employee no'
        supplierEmployeeCode = CustomKeywords.'commissionReserveFundData.checkCommissionCode.checkSupplierEmployeeCode'(
            sqlconnectionFOU, supplierEmployeeName, supplierEmpPos, supplierName)

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
        variableAllocateCommissionLeft = driver.findElements(By.cssSelector(('#ListAllocated' + supplierEmployeeCode.replace(
                    ' ', '\\ ')) + ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
        variableAllocateCommissionRight = driver.findElements(By.cssSelector(('#ListAllocated' + supplierEmployeeCode.replace(
                    ' ', '\\ ')) + ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))

        'Menghitung count (size dari variabel) sebelah kiri'
        int countAllocateCommissionLeft = variableAllocateCommissionLeft.size()

        'Menghitung count (size dari variabel) sebelah kanan'
        int countAllocateCommissionRight = variableAllocateCommissionRight.size()

        'Menghitung count (size dari variabel) gabungan kiri dan kanan'
        int countAllocateCommission = countAllocateCommissionLeft + countAllocateCommissionRight

        'Looping Allocate Commission From'
        for (int j = 1; j <= countAllocateCommission; j++) {
            String newxpathCommissionAmt

            String newxpathCommissionPercentage

            String newxpathAllocateCommission

            'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
            if (j <= countAllocateCommissionLeft) {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + 
                j) + ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + 
                j) + ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + 
                j) + ']/div/label[1]')
            } else {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + 
                j) + ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + 
                j) + ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + 
                j) + ']/div/label[1]')
            }
            
            'modify objecy allocate commission'
            modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Allocate Commission Supp Emp'), 
                'xpath', 'equals', newxpathAllocateCommission, true)

            'Menyimpan nama fee/income allocate commission from dari web confins'
            String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(' (AMT/%)', '').replaceAll(
                '\\s', '')

            'modify obejct allocate commission amt'
            modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input_Admin Fee supp emp (Amt)'), 
                'xpath', 'equals', newxpathCommissionAmt, true)

            'modify object commission percentage'
            modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input__AdminFee()SuppEmp'), 
                'xpath', 'equals', newxpathCommissionPercentage, true)

            'Pengecekan allocation type jika amount'
            if (allocationType == 'Amount') {
                'Mengambil nilai amount dari excel, 2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+16(suppEmpRow) = 20  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                value = datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 
                    2) + suppEmpRow).split(';', -1)

                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 2) + 
                    suppEmpRow).length() > 0) {
                    if ((value[(i - 1)]) != '') {
                        'Input Amount'
                        WebUI.setText(modifyObjectCommissionAmt, value[(i - 1)], FailureHandling.OPTIONAL)
                    }
                }
                
                'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
                WebUI.click(modifyObjectCommissionPercentage)
            } else if (allocationType == 'Percentage') {
                'Mengambil nilai percentage dari excel, 2j+3, +3 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+3+16(suppEmpRow) = 21  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                value = datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 
                    3) + suppEmpRow).split(';', -1)

                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 3) + 
                    suppEmpRow).length() > 0) {
                    if ((value[(i - 1)]) != '') {
                        'Input Percentage'
                        WebUI.setText(modifyObjectCommissionPercentage, value[(i - 1)], FailureHandling.OPTIONAL)
                    }
                }
                
                'Klik pada inputan amount untuk merefresh/merubah nilai amount'
                WebUI.click(modifyObjectCommissionAmt)
            }
            
            if (GlobalVariable.RoleCompany == 'Testing') {
                'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
                String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt, 'value').replace(',', '')

                'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
                String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage, 'value')

                'Parsing string amount menjadi bentuk double'
                BigDecimal amt = Double.parseDouble(textAmount)

                'Parsing string percentage menjadi bentuk double'
                BigDecimal pctg = Double.parseDouble(textPercentage)

                String textIncomeInfoAmt

                BigDecimal incomeInfoAmt

                'Looping Income Information'
                for (int k = 1; k <= countIncomeInfo; k++) {
                    newxpathIncomeInfo = (('//*[@id="viewIncomeInfo"]/div[' + k) + ']/div/div[1]/label')

                    newxpathIncomeInfoAmt = (('//*[@id="viewIncomeInfo"]/div[' + k) + ']/div/div[2]/label')

                    'modify objecy income info'
                    modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Upping Rate'), 
                        'xpath', 'equals', newxpathIncomeInfo, true)

                    'Varibel String untuk mengambil dan menampung income information'
                    String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll('\\s', '')

                    'modify object income info amt'
                    modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'), 
                        'xpath', 'equals', newxpathIncomeInfoAmt, true)

                    'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
                    if (textIncomeInfo == allocateCommission) {
                        'Variabel untuk mengambil amount income information yang sesuai'
                        textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(',', '')

                        'Parsing string amount income information menjadi bentuk double'
                        incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)

                        'Ambil nilai total allocate commission amount ke k-1'
                        GetTotalAllocateCommissionAmt = TotalAllocateCommissionAmt.get(k - 1)

                        'Pengecekan apakah amount dan percentage keduanya tidak bernilai 0 atau tidak'
                        if ((amt != 0) && (pctg != 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(k - 1, GetTotalAllocateCommissionAmt + ((pctg / 100) * incomeInfoAmt))
                        } else if ((amt == 0) || (pctg == 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(k - 1, GetTotalAllocateCommissionAmt + amt)
                        }
                        
                        'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
                        break
                    }
                }
                
                'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
                if ((amt != 0) && (pctg != 0)) {
                    'Verify hasil hitungan percentage dengan amount'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round((pctg / 100) * incomeInfoAmt), amt), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)

                    'Verify hasil hitungan amount dengan percentage'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(((amt / incomeInfoAmt) * 100) * 100) / 100, Math.round(
                                pctg * 100) / 100), '12.TabCommissionData', GlobalVariable.NumofColm)
                } else if ((amt == 0) || ((pctg == 0) && (allocateCommission != 'PACKET'))) {
                    'Verify amount dan percentage keduanya bernilai sama yaitu nol'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(amt), Math.round(pctg)), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)
                }
            }
        }
    }
}

//Referantor
'Mengambil nilai row keberapa dimulai data referantor commission pada excel'
def refRow = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Referantor Commission Data') + 
1

'Arraylist untuk menampung List Referantor'
ArrayList<String> variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))

'Pengecekan jika terdapat minimal 1 Referantor'
if (variableRef.size() > 0) {
    'Looping Referantor List'
    for (int i = 1; i <= variableRef.size(); i++) {
        'Variabel untuk flagging ketika berhasil melakukan delete'
        int flagdelReferantor = 0

        newxpathRefName = (('//*[@id=\'formInformationReferantor\']/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[1]/label')

        'modify object ref name'
        modifyObjectRefName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_RefName'), 
            'xpath', 'equals', newxpathRefName, true)

        'Variabel string untuk mengambil nama dari referantor ke-i dari web confins'
        String refName = WebUI.getText(modifyObjectRefName)

        'Mengambil dan menyimpan nama referantor dari excel yang akan didelete'
        def deleteRefName = datafileCommission.getValue(GlobalVariable.NumofColm, refRow + 
            1)

        'Pengecekan pada excel jika ada referantor yang ingin didelete'
        if (deleteRefName.length() > 0) {
            'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 referantor'
            deleteRefName = deleteRefName.split(';')

            'Looping nama referantor dari excel yang akan didelete'
            for (j = 1; j <= deleteRefName.size(); j++) {
                'Pengecekan jika nama referantor pada web confins sama dengan nama referantor dari excel yang akan didelete'
                if (refName.equalsIgnoreCase(deleteRefName[(j - 1)])) {
                    newxpathDelRef = (('//*[@id=\'formInformationReferantor\']/div[' + (i + 1)) + ']/div/div[1]/div[2]/div/div[2]/button/i')

                    'modify object del ref'
                    modifyDelRef = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/i_deleteref'), 
                        'xpath', 'equals', newxpathDelRef, true)

                    'Klik icon delete pada referantor tersebut (tempat sampah)'
                    WebUI.click(modifyDelRef)

                    'Klik OK pada alert yang muncul'
                    WebUI.acceptAlert()

                    if (i == variableRef.size()) {
                        if (WebUI.verifyElementNotPresent(modifyObjectRefName, 5, FailureHandling.OPTIONAL)) {
                            'Digunakan untuk menghitung jumlah list referantor setelah operasi delete selesai dilakukan'
                            variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))
                        } else {
                            'add cust name failed kedalam array'
                            commissiondelete.add(refName)

                            continue
                        }
                    } else {
                        'get cust name setelah delete'
                        refNameAfter = WebUI.getText(modifyObjectRefName)

                        if (WebUI.verifyNotMatch(refNameAfter, refName, false, FailureHandling.OPTIONAL)) {
                            'Digunakan untuk menghitung jumlah list referantor setelah operasi delete selesai dilakukan'
                            variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))
                        } else {
                            'add cust name failed kedalam array'
                            commissiondelete.add(refName)

                            continue
                        }
                    }
                    
                    i = (i - 1)

                    flagdelReferantor++
                }
            }
        }
        
        'Pengecekan jika flag delete tidak sama dengan nol, terjadi setelah proses delete dilakukan'
        if (flagdelReferantor != 0) {
            'Lanjut ke i/referantor berikutnya'
            continue
        }
        
        String refCode

        newxpathBankAccount = (('//*[@id=\'formInformationReferantor\']/div[' + (i + 1)) + ']/div/div[2]/div[1]/div[2]/select')

        'modify object bank account'
        modifyObjectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_bankAccRef'), 
            'xpath', 'equals', newxpathBankAccount, true)

        'Mengambil nama bank account referantor pada excel'
        def bankAccRef = datafileCommission.getValue(GlobalVariable.NumofColm, refRow + 
            2).split(';')

        'Select dropdownlist bank account referantor'
        WebUI.selectOptionByIndex(modifyObjectBankAccount, bankAccRef[(i - 1)], FailureHandling.OPTIONAL)

        'Query select pada database untuk mengambil nilai referantor code/nomor'
        refCode = CustomKeywords.'commissionReserveFundData.checkCommissionCode.checkReferantorCode'(sqlconnectionFOU, refName)

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
        variableAllocateCommissionLeft = driver.findElements(By.cssSelector(('#ListAllocated' + refCode) + ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))

        'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
        variableAllocateCommissionRight = driver.findElements(By.cssSelector(('#ListAllocated' + refCode) + ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))

        'Menghitung count (size dari variabel) sebelah kiri'
        int countAllocateCommissionLeft = variableAllocateCommissionLeft.size()

        'Menghitung count (size dari variabel) sebelah kanan'
        int countAllocateCommissionRight = variableAllocateCommissionRight.size()

        'Menghitung count (size dari variabel) gabungan kiri dan kanan'
        int countAllocateCommission = countAllocateCommissionLeft + countAllocateCommissionRight

        'Looping Allocate Commission From'
        for (int j = 1; j <= countAllocateCommission; j++) {
            String newxpathCommissionAmt

            String newxpathCommissionPercentage

            String newxpathAllocateCommission

            'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
            if (j <= countAllocateCommissionLeft) {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + 
                ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + 
                ']/div/label[1]')
            } else {
                newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + ']/div/div[1]/input')

                newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + 
                ']/div/div[2]/input')

                newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + 
                ']/div/label[1]')
            }
            
            'modify object allocate commission'
            modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Allocate Commission Ref'), 
                'xpath', 'equals', newxpathAllocateCommission, true)

            'Menyimpan nama fee/income allocate commission from dari web confins'
            String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(' (AMT/%)', '').replaceAll(
                '\\s', '')

            'modify object commission amt'
            modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input_Admin Fee Ref(Amt)'), 
                'xpath', 'equals', newxpathCommissionAmt, true)

            'modify object commission percentage'
            modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/input__AdminFee Ref ()'), 
                'xpath', 'equals', newxpathCommissionPercentage, true)

            'Pengecekan allocation type jika amount'
            if (allocationType == 'Amount') {
                'Mengambil nilai amount dari excel, 2j+1, +1 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+1+32(refRow) = 35  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                value = datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 
                    1) + refRow).split(';', -1)

                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 1) + 
                    refRow).length() > 0) {
                    if ((value[(i - 1)]) != '') {
                        'Input Amount'
                        WebUI.setText(modifyObjectCommissionAmt, value[(i - 1)], FailureHandling.OPTIONAL)
                    }
                }
                
                'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
                WebUI.click(modifyObjectCommissionPercentage)
            } else if (allocationType == 'Percentage') {
                'Mengambil nilai percentage dari excel, 2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+32(refRow) = 36  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
                value = datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 
                    2) + refRow).split(';', -1)

                if (datafileCommission.getValue(GlobalVariable.NumofColm, ((2 * j) + 2) + 
                    refRow).length() > 0) {
                    if ((value[(i - 1)]) != '') {
                        'Input Percentage'
                        WebUI.setText(modifyObjectCommissionPercentage, value[(i - 1)], FailureHandling.OPTIONAL)
                    }
                }
                
                'Klik pada inputan amount untuk merefresh/merubah nilai amount'
                WebUI.click(modifyObjectCommissionAmt)
            }
            
            if (GlobalVariable.RoleCompany == 'Testing') {
                'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
                String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt, 'value').replace(',', '')

                'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
                String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage, 'value')

                'Parsing string amount menjadi bentuk double'
                BigDecimal amt = Double.parseDouble(textAmount)

                'Parsing string percentage menjadi bentuk double'
                BigDecimal pctg = Double.parseDouble(textPercentage)

                String textIncomeInfoAmt

                BigDecimal incomeInfoAmt

                'Looping Income Information'
                for (int k = 1; k <= countIncomeInfo; k++) {
                    newxpathIncomeInfo = (('//*[@id="viewIncomeInfo"]/div[' + k) + ']/div/div[1]/label')

                    newxpathIncomeInfoAmt = (('//*[@id="viewIncomeInfo"]/div[' + k) + ']/div/div[2]/label')

                    modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_Upping Rate'), 
                        'xpath', 'equals', newxpathIncomeInfo, true)

                    'Varibel String untuk mengambil dan menampung income information'
                    String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll('\\s', '')

                    'modify object income info amt'
                    modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'), 
                        'xpath', 'equals', newxpathIncomeInfoAmt, true)

                    'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
                    if (textIncomeInfo == allocateCommission) {
                        'Variabel untuk mengambil amount income information yang sesuai'
                        textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(',', '')

                        'Parsing string amount income information menjadi bentuk double'
                        incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)

                        'Ambil nilai total allocate commission amount ke k-1'
                        GetTotalAllocateCommissionAmt = TotalAllocateCommissionAmt.get(k - 1)

                        'Pengecekan apakah amount dan percentage keduanya tidak bernilai 0 atau tidak'
                        if ((amt != 0) && (pctg != 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(k - 1, GetTotalAllocateCommissionAmt + ((pctg / 100) * incomeInfoAmt))
                        } else if ((amt == 0) || (pctg == 0)) {
                            'Tambahkan komponen fee allocate commission from ke arraylist'
                            TotalAllocateCommissionAmt.set(k - 1, GetTotalAllocateCommissionAmt + amt)
                        }
                        
                        'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
                        break
                    }
                }
                
                'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
                if ((amt != 0) && (pctg != 0)) {
                    'Verify hasil hitungan percentage dengan amount'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round((pctg / 100) * incomeInfoAmt), amt), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)

                    'Verify hasil hitungan amount dengan percentage'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(((amt / incomeInfoAmt) * 100) * 100) / 100, Math.round(
                                pctg * 100) / 100), '12.TabCommissionData', GlobalVariable.NumofColm)
                } else if ((amt == 0) || ((pctg == 0) && (allocateCommission != 'PACKET'))) {
                    'Verify amount dan percentage keduanya bernilai sama yaitu nol'
                    checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(amt), Math.round(pctg)), '12.TabCommissionData', 
                        GlobalVariable.NumofColm)
                }
            }
        }
    }
}

if (commissiondelete.size() > 0) {
    'write to excel status warning'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 0, GlobalVariable.CopyAppColm - 
        1, GlobalVariable.StatusWarning)

    'write to excel reason failed delete'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 1, GlobalVariable.CopyAppColm - 
        1, GlobalVariable.ReasonFailedDelete + commissiondelete)

    (GlobalVariable.FlagWarning)++
}

'Klik Calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Calculate'))

'declare alertCalculate object'
alertCalculate = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/alert_Commission')

'Pengecekan jika calculate error'
if (WebUI.verifyElementPresent(alertCalculate, 2, FailureHandling.OPTIONAL)) {
    'Write to Excel FAILED'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.StatusReasonCalculateGagal'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusReasonCalculateGagal)

    'Pengecekan error alert amount/percentage melebihi limit'
    if (WebUI.getText(alertCalculate).toLowerCase().contains('Cannot be more than'.toLowerCase())) {
        'Write To Excel GlobalVariable.StatusReasonAmountOverLimit'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 1, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonAmountOverLimit)
    }
    
    'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Cancel'))

    GlobalVariable.FlagFailed = 1
}

if (GlobalVariable.RoleCompany == 'Testing') {
    GlobalVariable.AllocatedCommissionAmt = TotalAllocateCommissionAmt

    'Call test case untuk verif summary dan remaining info'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionDataVerifInfo'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)
}

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Save'))

WebUI.delay(2)

Integer iscompleteMandatory = Integer.parseInt(datafileCommission.getValue(GlobalVariable.NumofColm, 
        4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '12.TabCommissionData')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('Object Repository/NAP-CF4W-CustomerCompany/CommissionReservedFund/TabReservedFundData/label_TotalReservedFundAmt'), 
        GlobalVariable.NumofColm, '12.TabCommissionData')

    if (iscompleteMandatory == 0) {
        'declare errorValObject'
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '12.TabCommissionData')
    }
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    'call test case commission datastore db verif'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionDataStoreDBVerif'), [:], 
        FailureHandling.CONTINUE_ON_FAILURE)
}

'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
    5, FailureHandling.OPTIONAL)) {
    'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData/button_Cancel'))

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch, String sheetname, int numofcolm) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname, 0, numofcolm - 
            1, GlobalVariable.StatusFailed)

        'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, sheetname, 1, numofcolm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

