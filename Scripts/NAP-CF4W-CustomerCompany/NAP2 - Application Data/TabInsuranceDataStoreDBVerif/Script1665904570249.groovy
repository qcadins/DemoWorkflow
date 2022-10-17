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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String insuredBy = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 12)

int arrayindex = 0

ArrayList<String> arraysuminsured = new ArrayList<String>()

ArrayList<String> arrayaddpremi = new ArrayList<String>()

ArrayList<String> arrayMatch = new ArrayList<String>()

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
    String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    resultarray = result.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultarray.size() - 1); i++) {
        if ((resultarray[i]).equalsIgnoreCase('null')) {
            (resultarray[i]) = ''
        } else if ((resultarray[i]) == 'true') {
            (resultarray[i]) = 'Yes'
        } else if ((resultarray[i]) == 'false') {
            (resultarray[i]) = 'No'
        }
    }
    
    for (index = 14; index < (resultarray.size() + 14); index++) {
        'verify insco branch name'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, index).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
} else if (insuredBy == 'Customer - Multifinance') {
    String resultCustomerInsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCStoreDB'(sqlconnection, 
        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    String resultMFinsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMStoreDB'(sqlconnection, findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)).replace('HEADER:', '').replace('[', '').replace(']', '')

    String resultMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnection, findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13))

    resultCustomerInsurancearray = resultCustomerInsurance.replace('HEADER:', '').replace('[', '').replace(']', '').split(
        ', ')

    resultMFInsurancearray = resultMFinsurance.split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultCustomerInsurancearray.size() - 1); i++) {
        if ((resultCustomerInsurancearray[i]).equalsIgnoreCase('null')) {
            (resultCustomerInsurancearray[i]) = ''
        } else if ((resultCustomerInsurancearray[i]) == 'true') {
            (resultCustomerInsurancearray[i]) = 'Yes'
        } else if ((resultCustomerInsurancearray[i]) == 'false') {
            (resultCustomerInsurancearray[i]) = 'No'
        }
    }
    
    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultMFInsurancearray.size() - 1); i++) {
        if ((resultMFInsurancearray[i]).equalsIgnoreCase('null')) {
            (resultMFInsurancearray[i]) = ''
        } else if ((resultMFInsurancearray[i]) == 'true') {
            (resultMFInsurancearray[i]) = 'Yes'
        } else if ((resultMFInsurancearray[i]) == 'false') {
            (resultMFInsurancearray[i]) = 'No'
        }
    }
    
    for (index = 14; index < (resultCustomerInsurancearray.size() + 14); index++) {
        'verify insco branch name'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, index).toUpperCase(), (resultCustomerInsurancearray[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
    }
    
    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insurance length'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify main coverage'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), resultMainCVG.replace('[', '').replace(']', '').toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    if (((((((findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
        String resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnection, findTestData(
                'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        resultAddCVGarray = resultAddCVG.replace('[', '').replace(']', '').split(', ')

        for (index = 0; index < resultAddCVGarray.size(); index++) {
            if ((resultAddCVGarray[index]).equalsIgnoreCase('Flood')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('TPL')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Act of God')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('SRCC')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Terrorist')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Theft & Robbery')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
            }
        }
    }
} else if (insuredBy == 'Multifinance') {
    String resultMFinsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMStoreDB'(sqlconnection, findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)).replace('HEADER:', '').replace('[', '').replace(']', '')

    String resultMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnection, findTestData(
            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13))

    resultMFInsurancearray = resultMFinsurance.split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultMFInsurancearray.size() - 1); i++) {
        if ((resultMFInsurancearray[i]).equalsIgnoreCase('null')) {
            (resultMFInsurancearray[i]) = ''
        } else if ((resultMFInsurancearray[i]) == 'true') {
            (resultMFInsurancearray[i]) = 'Yes'
        } else if ((resultMFInsurancearray[i]) == 'false') {
            (resultMFInsurancearray[i]) = 'No'
        }
    }
    
    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify insurance length'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFInsurancearray[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    'verify main coverage'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), resultMainCVG.replace('[', '').replace(']', '').toUpperCase(), 
        false, FailureHandling.OPTIONAL))

    if (((((((findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
        String resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnection, findTestData(
                'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        resultAddCVGarray = resultAddCVG.replace('[', '').replace(']', '').split(', ')

        for (index = 0; index < resultAddCVGarray.size(); index++) {
            if ((resultAddCVGarray[index]).equalsIgnoreCase('Flood')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('TPL')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Act of God')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('SRCC')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Terrorist')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
            } else if ((resultAddCVGarray[index]).equalsIgnoreCase('Theft & Robbery')) {
                arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
            }
        }
    }
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)

}