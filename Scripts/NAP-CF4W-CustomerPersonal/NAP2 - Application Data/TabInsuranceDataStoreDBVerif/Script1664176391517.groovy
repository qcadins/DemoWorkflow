import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.text.DateFormat as DateFormat
import java.text.SimpleDateFormat as SimpleDateFormat
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

String insuredBy = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 12)

int arrayindex = 0

ArrayList<String> arraysuminsured = new ArrayList<String>()

ArrayList<String> arrayaddpremi = new ArrayList<String>()

ArrayList<String> arrayMatch = new ArrayList<String>()

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
    ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCustStoreDB'(sqlconnection, findTestData(
            'NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13))

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (result.size() - 1); i++) {
        if ((result[i]) == null) {
            (result[i]) = ''
        } else if ((result[i]) == 'true') {
            (result[i]) = 'Yes'
        } else if ((result[i]) == 'false') {
            (result[i]) = 'No'
        }
    }
    
    for (index = 14; index < (result.size() + 14); index++) {
        'verify insco branch name'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                    GlobalVariable.NumofColm, index).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
} else if (insuredBy == 'Customer - Multifinance') {
    ArrayList<String> resultCustomerInsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCustMFStoreDB'(
        sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    ArrayList<String> resultMFinsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnection, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultCustomerInsurance.size() - 1); i++) {
        if ((resultCustomerInsurance[i]) == null) {
            (resultCustomerInsurance[i]) = ''
        } else if ((resultCustomerInsurance[i]) == 'true') {
            (resultCustomerInsurance[i]) = 'Yes'
        } else if ((resultCustomerInsurance[i]) == 'false') {
            (resultCustomerInsurance[i]) = 'No'
        }
    }
    
    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultMFinsurance.size() - 1); i++) {
        if ((resultMFinsurance[i]) == null) {
            (resultMFinsurance[i]) = ''
        } else if ((resultMFinsurance[i]) == 'true') {
            (resultMFinsurance[i]) = 'Yes'
        } else if ((resultMFinsurance[i]) == 'false') {
            (resultMFinsurance[i]) = 'No'
        }
    }
    
    for (index = 14; index <= (resultCustomerInsurance.size() + 13); index++) {
        if (index-13 != resultCustomerInsurance.size()) {
            'verify insco branch name'
            arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                        GlobalVariable.NumofColm, index).toUpperCase().replace(',', ''), (resultCustomerInsurance[arrayindex++]).toUpperCase(), 
                    false, FailureHandling.OPTIONAL))
        } else if (index-13 == resultCustomerInsurance.size()) {
            String enddate = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 19)

            Date enddate_Formated = new SimpleDateFormat('MM/dd/yyyy').parse(enddate)

            String inslength = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/insurancelength'), 
                'value')

            Calendar cal = Calendar.getInstance()

            cal.setTime(enddate_Formated)

            cal.add(Calendar.MONTH, Integer.parseInt(inslength))

            DateFormat dateFormat = new SimpleDateFormat('MM/dd/yyyy')

            String enddateFinal = dateFormat.format(cal.getTime())

            arrayMatch.add(WebUI.verifyMatch(enddateFinal, (resultCustomerInsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
        }
    }
    
    arrayindex = 0

    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    if ((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
        'verify insurance length'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                    GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))
    } else if ((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Annualy') || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
        'skip verify length insurance'
        resultMFinsurance[arrayindex++]
    }
    
    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 36).length() == 0) {
        ArrayList<String> resultMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnection, 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        'verify main coverage'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                    GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), (resultMainCVG[0]).toUpperCase(), false, 
                FailureHandling.OPTIONAL))

        if (((((((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
            ArrayList<String> resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnection, 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            for (index = 0; index < resultAddCVG.size(); index++) {
                if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
                }
            }
        }
    } else {
        ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
            sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        println(resultMultiMainCVG)

        'ganti value null > "" (String kosong)'
        for (i = 0; i <= (resultMultiMainCVG.size() - 1); i++) {
            if ((resultMultiMainCVG[i]) == null) {
                (resultMultiMainCVG[i]) = ''
            } else if ((resultMultiMainCVG[i]) == 'true') {
                (resultMultiMainCVG[i]) = 'Yes'
            } else if ((resultMultiMainCVG[i]) == 'false') {
                (resultMultiMainCVG[i]) = 'No'
            }
        }
        
        println(resultMultiMainCVG)

        def capitalizedarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 45).split(';', -1)

        def paidbyarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 46).split(';', -1)

        def suminsuredarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 47).split(';', -1)

        def maincvgarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 48).split(';', -1)

        int indexdb = 0

        for (index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
            indexdb++

            if ((capitalizedarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((paidbyarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((paidbyarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((suminsuredarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((suminsuredarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((maincvgarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((maincvgarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++
        }
        
        if ((((((((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 50).length() > 0) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 51).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 52).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 53).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 54).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 55).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 56).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 57).length() > 0)) {
            ArrayList<String> resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
                sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'ganti value null > "" (String kosong)'
            for (i = 0; i <= (resultAddCVG.size() - 1); i++) {
                if ((resultAddCVG[i]) == null) {
                    (resultAddCVG[i]) = ''
                } else if ((resultAddCVG[i]) == 'true') {
                    (resultAddCVG[i]) = 'Yes'
                } else if ((resultAddCVG[i]) == 'false') {
                    (resultAddCVG[i]) = 'No'
                }
            }
            
            println(resultAddCVG)

            def floodarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 50).split(';', -1)

            def tplarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 51).split(';', -1)

            def actofgodarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 52).split(';', -1)

            def srccarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 53).split(';', -1)

            def TJHTParray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 54).split(';', -1)

            def Kecelakaanarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 55).split(';', -1)

            def terroristarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 56).split(';', -1)

            def Theftrobberyarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 57).split(';', -1)

            def floodratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 71).split(';', -1)

            def tplratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 72).split(';', -1)

            def actofgodratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 73).split(';', -1)

            def srccratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 74).split(';', -1)

            def TJHTPratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 75).split(';', -1)

            def Kecelakaanratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 76).split(';', -1)

            def terroristratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 77).split(';', -1)

            def Theftrobberyratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 78).split(';', -1)

            indexdb = 0

            for (index = 0; index < (resultAddCVG.size() / 3); index++) {
                year = Integer.parseInt(resultAddCVG[indexdb++])

                addcvg = (resultAddCVG[indexdb++])

                addpremirate = Double.parseDouble(resultAddCVG[indexdb++])

                if (addcvg.equalsIgnoreCase('Flood')) {
                    if ((floodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((floodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((floodratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(floodratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('TPL')) {
                    if ((tplarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((tplarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((tplratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(tplratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Act of God')) {
                    if ((actofgodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((actofgodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((actofgodratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(actofgodratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('SRCC')) {
                    if ((srccarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((srccarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((srccratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(srccratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    if ((TJHTParray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((TJHTParray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((TJHTPratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(TJHTPratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    if ((Kecelakaanarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Kecelakaanarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((Kecelakaanratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(Kecelakaanratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Terrorist')) {
                    if ((terroristarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((terroristarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((terroristratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(terroristratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Theft & Robbery')) {
                    if ((Theftrobberyarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Theftrobberyarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((Theftrobberyratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(Theftrobberyratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                }
            }
        }
    }
} else if (insuredBy == 'Multifinance') {
    ArrayList<String> resultMFinsurance = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnection, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultMFinsurance.size() - 1); i++) {
        if ((resultMFinsurance[i]) == null) {
            (resultMFinsurance[i]) = ''
        } else if ((resultMFinsurance[i]) == 'true') {
            (resultMFinsurance[i]) = 'Yes'
        } else if ((resultMFinsurance[i]) == 'false') {
            (resultMFinsurance[i]) = 'No'
        }
    }
    
    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    if ((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
        'verify insurance length'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                    GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))
    } else if ((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Annualy') || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
        'skip verify length insurance'
        resultMFinsurance[arrayindex++]
    }
    
    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
        GlobalVariable.NumofColm, 36).length() == 0) {
        String resultMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnection, 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        'verify main coverage'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                    GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), resultMainCVG.replace('[', '').replace(
                    ']', '').toUpperCase(), false, FailureHandling.OPTIONAL))

        if (((((((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
            ArrayList<String> resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnection, 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            for (index = 0; index < resultAddCVG.size(); index++) {
                if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
                } else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
                    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                                GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
                }
            }
        }
    } else {
        ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
            sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        println(resultMultiMainCVG)

        'ganti value null > "" (String kosong)'
        for (i = 0; i <= (resultMultiMainCVG.size() - 1); i++) {
            if ((resultMultiMainCVG[i]) == null) {
                (resultMultiMainCVG[i]) = ''
            } else if ((resultMultiMainCVG[i]) == 'true') {
                (resultMultiMainCVG[i]) = 'Yes'
            } else if ((resultMultiMainCVG[i]) == 'false') {
                (resultMultiMainCVG[i]) = 'No'
            }
        }
        
        println(resultMultiMainCVG)

        def capitalizedarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 45).split(';', -1)

        def paidbyarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 46).split(';', -1)

        def suminsuredarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 47).split(';', -1)

        def maincvgarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 48).split(';', -1)

        int indexdb = 0

        for (index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
            indexdb++

            if ((capitalizedarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((paidbyarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((paidbyarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((suminsuredarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((suminsuredarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++

            if ((maincvgarray[index]).length() > 0) {
                arrayMatch.add(WebUI.verifyMatch((maincvgarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))
            }
            
            indexdb++
        }
        
        if ((((((((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 50).length() > 0) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 51).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 52).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 53).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 54).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 55).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 56).length() > 0)) || (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 57).length() > 0)) {
            ArrayList<String> resultAddCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
                sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'ganti value null > "" (String kosong)'
            for (i = 0; i <= (resultAddCVG.size() - 1); i++) {
                if ((resultAddCVG[i]) == null) {
                    (resultAddCVG[i]) = ''
                } else if ((resultAddCVG[i]) == 'true') {
                    (resultAddCVG[i]) = 'Yes'
                } else if ((resultAddCVG[i]) == 'false') {
                    (resultAddCVG[i]) = 'No'
                }
            }
            
            println(resultAddCVG)

            def floodarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 50).split(';', -1)

            def tplarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 51).split(';', -1)

            def actofgodarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 52).split(';', -1)

            def srccarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 53).split(';', -1)

            def TJHTParray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 54).split(';', -1)

            def Kecelakaanarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 55).split(';', -1)

            def terroristarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 56).split(';', -1)

            def Theftrobberyarray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 57).split(';', -1)

            def floodratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 71).split(';', -1)

            def tplratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 72).split(';', -1)

            def actofgodratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 73).split(';', -1)

            def srccratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 74).split(';', -1)

            def TJHTPratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 75).split(';', -1)

            def Kecelakaanratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 76).split(';', -1)

            def terroristratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 77).split(';', -1)

            def Theftrobberyratearray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, 78).split(';', -1)

            indexdb = 0

            for (index = 0; index < (resultAddCVG.size() / 3); index++) {
                year = Integer.parseInt(resultAddCVG[indexdb++])

                addcvg = (resultAddCVG[indexdb++])

                addpremirate = Double.parseDouble(resultAddCVG[indexdb++])

                if (addcvg.equalsIgnoreCase('Flood')) {
                    if ((floodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((floodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((floodratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(floodratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('TPL')) {
                    if ((tplarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((tplarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((tplratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(tplratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Act of God')) {
                    if ((actofgodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((actofgodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((actofgodratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(actofgodratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('SRCC')) {
                    if ((srccarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((srccarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((srccratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(srccratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    if ((TJHTParray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((TJHTParray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((TJHTPratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(TJHTPratearray[(year - 1)]), Math.round(addpremirate), 
                                FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    if ((Kecelakaanarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Kecelakaanarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((Kecelakaanratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(Kecelakaanratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Terrorist')) {
                    if ((terroristarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((terroristarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((terroristratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(terroristratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Theft & Robbery')) {
                    if ((Theftrobberyarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Theftrobberyarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                    
                    if ((Theftrobberyratearray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(Theftrobberyratearray[(year - 1)]), Math.round(
                                    addpremirate), FailureHandling.OPTIONAL))
                    }
                }
            }
        }
    }
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 0, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 1, 
        GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}

