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

GlobalVariable.FlagFailed = 0

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorCompanyCompany

GlobalVariable.DataFilePath = filePath

'get count colm'
countcolm = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getColumnNumbers()

for (financialdata = 2; financialdata <= (countcolm + 1); financialdata++) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
        financialdata, 9).length() != 0) {
        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
            financialdata, 9).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 12)) && findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
            financialdata, 10).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 13))) {
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 12).length() > 0) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Add'))

                'input gross monthly income amount'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Monthly Income'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 12))

                'input gross profit amount'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Monthly Expense'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 13))

                'input return of investment percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Investment()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 14))

                'input return of equity percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Equity()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 15))

                'input return of asset percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Return Of Asset()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 16))

                'input profit margin percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Profit Margin()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 17))

                'input current ratio percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Ratio()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 18))

                'debt equity ratio percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Debt Equity Ratio()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 19))

                'inventory turn over percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Invetory Turn Over()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 20))

                'input account receivable turn over'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_AR Turn Over()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 21))

                'input growth percentage'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Growth()'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 22))

                'input working capital amount'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Working Capital (Amount)'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 23))

                'input other monthly installment amount'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Other Monthly Installment'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 24))

                'input operational cost'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Operational Cost'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 26))

                'input revenue '
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Revenue'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 25))

                'input current asset'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Asset'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 28))

                'input net fixed asset'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Profit Before Tax'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 27))

                'input total asset'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Total Asset'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 30))

                'input profit before tax'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Nett Fixed Asset'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 29))

                'input long term liabilities'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Long Term Liabilities'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 32))

                'input current liabilities'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Liabilities'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 31))

                'input current ratio'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Current Ratio'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 34))

                'input shareholder equity'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Shareholder Equity'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 33))

                'input date as of'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Date As Of'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 35))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_SaveFinancialData'))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 37).length() > 1) {
                'input posisi laporan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_POSISI LAPORAN KEUANGAN TAHUNAN'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 37))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 38).length() > 1) {
                'input aset'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_ASET'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 38))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 39).length() > 1) {
                'input aset lancar'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_ASET LANCAR'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 39))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 40).length() > 1) {
                'input kas dan setara kas'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_KAS DAN SETARA KAS'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 40))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 41).length() > 1) {
                'input piutang pembiayaan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PIUTANG USAHAPEMBIAYAAN'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 41))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 42).length() > 1) {
                'input investasi keuangan '
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_INVESTASIASET KEUANGAN LAINNYA'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 42))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 43).length() > 1) {
                'input asset lancar lainya'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_ASSET LANCAR LAINNYA'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 43))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 44).length() > 1) {
                'input asset tidak lancar'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_ASSET TIDAK LANCAR'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 44))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 45).length() > 1) {
                'input piutang usaha pembiayaan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PIUTANG USAHAPEMBIAYAAN (ASSET TIDAK LANCAR)'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 45))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 46).length() > 1) {
                'input investasi keuangan lainya'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_INVESTASIASSET KEUANGAN LAINNYA (ASSET TIDAK LANCAR)'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 46))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 47).length() > 1) {
                'input aset tidak lancar lainya'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_ASSET TIDAK LANCAR LAINNYA'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 47))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 48).length() > 1) {
                'input liabilitas'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LIABILITAS'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 48))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 49).length() > 1) {
                'input liabilitas jangka pendek'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LIABILITAS JANGKA PENDEK'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 49))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 50).length() > 1) {
                'input pinjaman jangka pendek'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PINJAMAN JANGKA PENDEK'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 50))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 51).length() > 1) {
                'input utang usaha jangka pendek'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_UTANG USAHA JANGKA PENDEK'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 51))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 52).length() > 1) {
                'input liabilitas jangka pendek lainya'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LIABILITAS JANGKA PENDEK LAINNYA'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 52))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 53).length() > 1) {
                'input liabilitas jangka panjang'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LIABILITAS JANGKA PANJANG'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 53))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 54).length() > 1) {
                'input pinjaman jangka panjang'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PINJAMAN JANGKA PANJANG'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 54))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 55).length() > 1) {
                'input utang usaha jangka panjang'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_UTANG USAHA JANGKA PANJANG'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 55))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 56).length() > 1) {
                'input liabilitas jangka panjang lainnya'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LIABILITAS JANGKA PANJANG LAINNYA'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 56))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 57).length() > 1) {
                'input ekuitas'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_EKUITAS'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 57))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 58).length() > 1) {
                'input pendapatan usaha operasional'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PENDAPATAN USAHA  OPERASIONAL'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 58))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 59).length() > 1) {
                'input beban pokok pendapatan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_BEBAN POKOK PENDAPATAN  BEBAN OPERASIONAL'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 59))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 60).length() > 1) {
                'input laba rugi bruto'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LABARUGI BRUTO'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 60))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 61).length() > 1) {
                'input pendapatan lain lain'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_PENDAPATAN LAIN-LAIN  NON OPERASIONAL'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 61))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 62).length() > 1) {
                'input beban lain lain'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_BEBAN LAIN-LAIN  NON OPERASIONAL'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 62))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 63).length() > 1) {
                'input laba rugi sebelum pajak'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LABARUGI SEBELUM PAJAK'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 63))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 64).length() > 1) {
                'input laba rugi tahun berjalan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_LABARUGI TAHUN BERJALAN'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 64))
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                financialdata, 67).length() > 1) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_addBank'))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Bank Name_btn btn-raised btn-primary'))

                'input bank code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Code_BankCode'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 66))

                'input bank name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Name_BankName'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 67))

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
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.FinancialData', 
                        0, financialdata - 1, GlobalVariable.StatusWarning)

                    'Write To Excel GlobalVariable.StatusReasonLookup'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.FinancialData', 
                        1, financialdata - 1, GlobalVariable.StatusReasonLookup)

                    flagWarning++
                }
                
                'input bank branch'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Bank Branch'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 68))

                'input account name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Account Name'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 69))

                'input account no'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Account No'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 70))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 71) == 'Yes') {
                        'click is default'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'), 
                    2)) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 71) == 'No') {
                        'click is default'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Default'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 72) == 'Yes') {
                        'click is active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'), 
                    2)) {
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 72) == 'No') {
                        'click is active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Is Active'))
                    }
                }
                
                'input begining balance'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Beginning Balance'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 73))

                'Month array'
                montharray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 74).split(';', -1)

                'Year array'
                yeararray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 75).split(';', -1)

                'Debit Transaction count array'
                debitTransactionarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 76).split(';', -1)

                'debit array'
                debitarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 77).split(';', -1)

                'credit transaction count array'
                creditTransactionArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 78).split(';', -1)

                'credit array'
                creditarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 79).split(';', -1)

                if ((((((findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 74).length() > 0) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 75).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 76).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 77).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 78).length() > 0)) || (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                    financialdata, 79).length() > 0)) {
                    for (i = 1; i <= montharray.size(); i++) {
                        'modify object from input credit'
                        modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_credit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[6]/input', true)

                        'modify object from input credit transaction count'
                        modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_credittransactioncount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[5]/input', true)

                        'modify object from input debit'
                        modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_debit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[4]/input', true)

                        'modify object from input debit transaction count'
                        modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_debittransactionaccount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[3]/input', true)

                        'modify object from input year'
                        modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/input_Year'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[2]/input', true)

                        'modify object from select month'
                        modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/select_Month'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            i) + ']/td[1]/select', true)

                        'click add new button'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_banknewrow'))

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
                
                'click calculate bank'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_CalculateBank'))

                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save'))

                if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                        financialdata, 4)) == 0) {
                    'Check alert'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(financialdata, '4.FinancialData')
                }
                
                if (GlobalVariable.FlagFailed == 0) {
                    'Check save Process write to excel'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                                financialdata, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save  Continue'), 
                        financialdata, '4.FinancialData')

                    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/FinancialData - Company - GuarantorCompany').getValue(
                            financialdata, 4)) == 0) {
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
                
                if (flagWarning > 0) {
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.FinancialData', 
                        0, financialdata - 1, GlobalVariable.StatusWarning)
                }
                
                WebUI.delay(5)
            }
        }
    } else {
        break
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/FinancialData - Company/th_Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

