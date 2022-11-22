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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

ArrayList<Boolean> arrayMatch = new ArrayList<>()

for (index = GlobalVariable.NumofVerifStore; index < (countcolm + GlobalVariable.NumofVerifStore); index++) {
    if (GlobalVariable.FindDataFile.getValue(index, 10).length() != 0 && GlobalVariable.FindDataFile.getValue(index, 17).length() != 0) {
        ArrayList<String> resultfinancialdata = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialDataPersonalStoreData'(
            sqlconnectionLOS, appno, custname, GlobalVariable.FindDataFile.getValue(index, 17))

        ArrayList<String> resultfinancialattr = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialAttrStoreData'(sqlconnectionLOS, 
            appno, custname)

        int financialdataindex = 0

        int financialattr = 0

        r

        'ganti value null > "" (String kosong)'
        for (i = 0; i <= (resultfinancialdata.size() - 1); i++) {
//            if ((resultfinancialdata[i]) == null) {
//                (resultfinancialdata[i]) = ''
//            } else 
			if ((resultfinancialdata[i]).equalsIgnoreCase('true')) {
                (resultfinancialdata[i]) = 'Yes'
            } else if ((resultfinancialdata[i]).equalsIgnoreCase('false')) {
                (resultfinancialdata[i]) = 'No'
            }
        }
        
        'verify monthly income'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 12).split(',').join(), (resultfinancialdata[
                financialdataindex++]).split(',').join(), false, FailureHandling.OPTIONAL))

        'verify other income'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 13).split(',').join(), (resultfinancialdata[
                financialdataindex++]).split(',').join(), false, FailureHandling.OPTIONAL))

        'verify source other income'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 14).split(',').join(), (resultfinancialdata[
                financialdataindex++]).split(',').join(), false, FailureHandling.OPTIONAL))

        'verify monthly installment'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 15).split(',').join(), (resultfinancialdata[
                financialdataindex++]).split(',').join(), false, FailureHandling.OPTIONAL))

        'verify monthly expense'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 16).split(',').join(), (resultfinancialdata[
                financialdataindex++]).split(',').join(), false, FailureHandling.OPTIONAL))

        'verify date as of'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 17).toUpperCase(), resultfinancialdata[
                financialdataindex++], false, FailureHandling.OPTIONAL))

        if (index == GlobalVariable.NumofVerifStore) {
            'verify Jumlah harta'
            arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 21).split(',').join(), resultfinancialattr, 
                    false, FailureHandling.OPTIONAL))
        }
    } else {
        break
    }
}

for (index = GlobalVariable.NumofVerifStore; index < (countcolm + GlobalVariable.NumofVerifStore); index++) {
    if (GlobalVariable.FindDataFile.getValue(index, 10).length() != 0 && GlobalVariable.FindDataFile.getValue(index, 27) != 0) {
        ArrayList<String> resultbankacc = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinDataBankAccStoreData'(sqlconnectionLOS, 
            appno, custname, GlobalVariable.FindDataFile.getValue(index, 27))

        int bankacc = 0

        

        'ganti value null > "" (String kosong)'
        for (i = 0; i <= (resultbankacc.size() - 1); i++) {
//            if ((resultbankacc[i]) == null) {
//                (resultbankacc[i]) = ''
//            } else 
			if ((resultbankacc[i]).equalsIgnoreCase('true')) {
                (resultbankacc[i]) = 'Yes'
            } else if ((resultbankacc[i]).equalsIgnoreCase('false')) {
                (resultbankacc[i]) = 'No'
            }
        }
        
        'verify bank code'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 23).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify bank branch'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 25).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify bank name'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 26).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify bank acc no'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 27).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify is default'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 28).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify is active'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 29).toUpperCase(), (resultbankacc[
                bankacc++]).toUpperCase(), false, FailureHandling.OPTIONAL))

        'verify begining balance'
        arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 30).split(',').join(), (resultbankacc[
                bankacc++]).split(',').join().toUpperCase(), false, FailureHandling.OPTIONAL))

        String bankstat = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialCheckBankStatStoreData'(sqlconnectionLOS, 
            appno, custname, GlobalVariable.FindDataFile.getValue(index, 27))

        'verify bank statement yes/no'
        if (bankstat == null) {
            continue
        } else if (bankstat.equalsIgnoreCase('true')) {
            def montharray = GlobalVariable.FindDataFile.getValue(index, 31).split(';', -1)

            def yeararray = GlobalVariable.FindDataFile.getValue(index, 32).split(';', -1)

            def debittrscarray = GlobalVariable.FindDataFile.getValue(index, 33).split(';', -1)

            def debitarray = GlobalVariable.FindDataFile.getValue(index, 34).split(';', -1)

            def credittrscarray = GlobalVariable.FindDataFile.getValue(index, 35).split(';', -1)

            def creditarray = GlobalVariable.FindDataFile.getValue(index, 36).split(';', -1)

            ArrayList<String> resultbankstatdata = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4BankStatDataStoreData'(sqlconnectionLOS, 
                appno, custname, GlobalVariable.FindDataFile.getValue(index, 27))

            
            'ganti value null > "" (String kosong)'
            for (i = 0; i <= (resultbankstatdata.size() - 1); i++) {
                if ((resultbankstatdata[i]).equalsIgnoreCase('0')) {
                    (resultbankstatdata[i]) = ''
                }
            }
            
            int bankstatdb = 0

            for (bankstatexcel = 0; bankstatexcel < (resultbankstatdata.size() / 6); bankstatexcel++) {
                'verify month'
                arrayMatch.add(WebUI.verifyMatch((montharray[bankstatexcel]).toUpperCase(), (resultbankstatdata[bankstatdb++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify year'
                arrayMatch.add(WebUI.verifyMatch(yeararray[bankstatexcel], (resultbankstatdata[bankstatdb++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify debit transaction'
                arrayMatch.add(WebUI.verifyMatch(debittrscarray[bankstatexcel], (resultbankstatdata[bankstatdb++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify debit'
                arrayMatch.add(WebUI.verifyMatch((debitarray[bankstatexcel]).split(',').join(), (resultbankstatdata[
                        bankstatdb++]).toUpperCase().split(',').join(), false, FailureHandling.OPTIONAL))

                'verify credit transaction'
                arrayMatch.add(WebUI.verifyMatch(credittrscarray[bankstatexcel], (resultbankstatdata[bankstatdb++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify credit'
                arrayMatch.add(WebUI.verifyMatch((creditarray[bankstatexcel]).split(',').join(), (resultbankstatdata[
                        bankstatdb++]).split(',').join(), false, FailureHandling.OPTIONAL))
            }
        }
    } else {
        break
    }
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 0, GlobalVariable.NumofVerifStore - 
        1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 1, GlobalVariable.NumofVerifStore - 
        1, GlobalVariable.ReasonFailedStoredDB)
}

