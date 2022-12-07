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
import org.openqa.selenium.WebElement as WebElement

'connect DB los'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect db fou'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData')

'get insuredby from excel'
String insuredBy = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 12)

'declare arrayindex'
int arrayindex = 0

'declare arraysuminsured'
ArrayList<String> arraysuminsured = new ArrayList<String>()

'declare arrayaddpremi'
ArrayList<String> arrayaddpremi = new ArrayList<String>()

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
    'call function insuredcust'
    insuredCust(arrayMatch, sqlconnectionLOS)
} else if (insuredBy == 'Customer - Multifinance') {
    'call function insuredcustmf'
    insuredCustMF(arrayMatch, sqlconnectionLOS, sqlconnectionFOU, arrayindex)
} else if (insuredBy == 'Multifinance') {
    'call function insuredmf'
    insuredMF(arrayMatch, sqlconnectionLOS, sqlconnectionFOU)
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, 
        GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

def insuredCust(ArrayList<String> arrayMatch, Sql sqlconnectionLOS) {
    'get insurance cust data from db'
    ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustStoreDB'(sqlconnectionLOS, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'index 14 karena mengikuti row di data file / excel'
    for (int index = 14; index < (result.size() + 14); index++) {
        'verify insco branch name'
        arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, index).toUpperCase(), (result[
                arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
}

def insuredCustMF(ArrayList<String> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU, int arrayindex) {
    'get insurance cust data from db'
    ArrayList<String> resultCustomerInsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustMFStoreDB'(
        sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'get insurance mf data from db'
    ArrayList<String> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'index 14 karena mengikuti row di data file / excel'
    for (int index = 14; index <= (resultCustomerInsurance.size() + 13); index++) {
        'index -13 supaya dapat verif apakah index sudah sampai index terakhir dari array'
        if ((index - 13) != resultCustomerInsurance.size()) {
            'verify insco branch name'
            arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, index).toUpperCase().replace(
                        ',', ''), (resultCustomerInsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
        } else if ((index - 13) == resultCustomerInsurance.size()) {
			
			String countDate = CustomKeywords.'customizeKeyword.convertDate.countDateInsurance'(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 19)) 
			
            arrayMatch.add(WebUI.verifyMatch(countDate, (resultCustomerInsurance[
                    arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
        }
    }
    
    arrayindex = 0

    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 22).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 23).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 25).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 26).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 27).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if ((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
        'verify insurance length'
        arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 28).toUpperCase().replace(
                    ',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    } else if ((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24) == 'Annualy') || (datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
        'skip verify length insurance'
        resultMFinsurance[arrayindex++]
    }
    
    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 31).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 32).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'get main coverage data from db'
    ArrayList<String> resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'verify main coverage'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 34).toUpperCase().replace(',', 
                ''), (resultMainCVG[0]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if (((((((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 
        38).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || 
    datafileTabInsurance.getValue(GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 
        42).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
        'get additional coverage data from db'
        ArrayList<String> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnectionLOS, 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        'looping verif additionalcoverage data from db'
        for (int index = 0; index < resultAddCVG.size(); index++) {
            if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 36), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 37), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 38), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 39), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 40), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 41), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 42), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 43), 'YES', false, 
                        FailureHandling.OPTIONAL))
            }
        }
    }
    
    if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 45).length() > 0) {
        'Mengambil nilai setting cap insurance dari db'
        String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)

        'get insurance mf multi main coverage from db'
        ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
            sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        'declare capitalized array'
        def capitalizedarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 45).split(';', -1)

        'declare paidbyarray'
        def paidbyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 46).split(';', -1)

        'declare suminsuredarray'
        def suminsuredarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 47).split(';', -1)

        'declare maincvgarray'
        def maincvgarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 48).split(';', -1)

        'declare indexdb'
        int indexdb = 0

        'looping verif resultmultimaincvg'
        for (int index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
            indexdb++

            if (capinssetting.equalsIgnoreCase('YEARLY')) {
                if ((capitalizedarray[index]).length() > 0) {
                    arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                            false, FailureHandling.OPTIONAL))
                }
            } else if (capinssetting.equalsIgnoreCase('PARTIAL')) {
                'skip is capitalized'
                indexdb++
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
        
        if (capinssetting.equalsIgnoreCase('PARTIAL')) {
            if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 84).equalsIgnoreCase('NO')) {
                'call keyword NAP2InsurancePartialCaptilizeStoreDB'
                ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
                    sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                        GlobalVariable.NumofColm, 13))

                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 85), (resultPartialCaptilized[
                        0]).replace('.00', ''), false, FailureHandling.OPTIONAL))
            }
        }
        
        if ((((((((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 50).length() > 0) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 51).length() > 0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 52).length() > 
        0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 53).length() > 0)) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 54).length() > 0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 55).length() > 
        0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 56).length() > 0)) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 57).length() > 0)) {
            'get insurance multi addittional coverage data from db'
            ArrayList<String> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
                sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'declare addrate'
            ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate

            def floodarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 50).split(';', -1)

            def tplarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 51).split(';', -1)

            def actofgodarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 52).split(';', -1)

            def srccarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 53).split(';', -1)

            def TJHTParray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 54).split(';', -1)

            def Kecelakaanarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 55).split(';', -1)

            def terroristarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 56).split(';', -1)

            def Theftrobberyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 57).split(';', -1)

            def floodratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 71).split(';', -1)

            def tplratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 72).split(';', -1)

            def actofgodratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 73).split(';', -1)

            def srccratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 74).split(';', -1)

            def TJHTPratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 75).split(';', -1)

            def Kecelakaanratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 76).split(';', -1)

            def terroristratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 77).split(';', -1)

            def Theftrobberyratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 78).split(';', -1)

            indexdb = 0

            'looping verif resultaddcvg'
            for (int index = 0; index < (resultAddCVG.size() / 3); index++) {
                year = Integer.parseInt(resultAddCVG[indexdb++])

                addcvg = (resultAddCVG[indexdb++])

                addpremirate = Double.parseDouble(resultAddCVG[indexdb++])

                if (addcvg.equalsIgnoreCase('Flood')) {
                    if ((floodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((floodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('TPL')) {
                    if ((tplarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((tplarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Act of God')) {
                    if ((actofgodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((actofgodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('SRCC')) {
                    if ((srccarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((srccarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    if ((TJHTParray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((TJHTParray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    if ((Kecelakaanarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Kecelakaanarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Terrorist')) {
                    if ((terroristarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((terroristarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Theft & Robbery')) {
                    if ((Theftrobberyarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Theftrobberyarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                }
                
                'verify additional premi rate db = confins'
                arrayMatch.add(WebUI.verifyEqual(AddRate[index], addpremirate))
            }
        }
        
        'cek jika ada main premi rate'
        if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 69).length() > 0) {
            ArrayList<String> MainRate = GlobalVariable.MainPremiRate

            'get arraylist main premi rate dari DB'
            ArrayList<String> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
                sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'get arraylist main premi rate dari excel'
            def mainpremirateArray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 69).split(';', -1)

            'looping untuk verify mainpremirate db = confins'
            for (int mainpremirate = 0; mainpremirate < MainRate.size(); mainpremirate++) {
                'verify main rate pada db = confins'
                arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(resultMainPremiRate[
                            mainpremirate])))
            }
        }
    }
}

def insuredMF(ArrayList<String> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU) {
    arrayindex = 0

    'get insurance mf data from db'
    ArrayList<String> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'verify asset region'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 22).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify coverage amount'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 23).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify cover period'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify payment type'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 25).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify insco branch name'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 26).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify insurance note'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 27).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if ((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
        'verify insurance length'
        arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 28).toUpperCase().replace(
                    ',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    } else if ((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 24) == 'Annualy') || (datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
        'skip verify length insurance'
        resultMFinsurance[arrayindex++]
    }
    
    'verify admin fee'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 31).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify customer stampduty fee'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 32).toUpperCase().replace(',', 
                ''), (resultMFinsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'get insurance main coverage from db'
    String resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS, 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13))

    'verify main coverage'
    arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 34).toUpperCase().replace(',', 
                ''), resultMainCVG.replace('[', '').replace(']', '').toUpperCase(), false, FailureHandling.OPTIONAL))

    if (((((((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 
        38).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || 
    datafileTabInsurance.getValue(GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
        GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 
        42).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
        'get insurance additional coverage from db'
        ArrayList<String> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(sqlconnectionLOS, 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        for (int index = 0; index < resultAddCVG.size(); index++) {
            if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 36), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 37), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 38), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 39), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 40), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 41), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 42), 'YES', false, 
                        FailureHandling.OPTIONAL))
            } else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 43), 'YES', false, 
                        FailureHandling.OPTIONAL))
            }
        }
    }
    
    if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 45).length() > 0) {
        'Mengambil nilai setting cap insurance dari db'
        String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)

        'get insurance multi main coverage from db'
        ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
            sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13))

        def capitalizedarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 45).split(';', -1)

        def paidbyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 46).split(';', -1)

        def suminsuredarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 47).split(';', -1)

        def maincvgarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 48).split(';', -1)

        int indexdb = 0

        'looping resultmultimaincvg verif'
        for (int index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
            indexdb++

            if (capinssetting.equalsIgnoreCase('YEARLY')) {
                if ((capitalizedarray[index]).length() > 0) {
                    arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(), 
                            false, FailureHandling.OPTIONAL))
                }
            } else if (capinssetting.equalsIgnoreCase('PARTIAL')) {
                'skip is capitalized'
                indexdb++
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
        
        if (capinssetting.equalsIgnoreCase('PARTIAL')) {
            if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 84).equalsIgnoreCase('NO')) {
                'call keyword NAP2InsurancePartialCaptilizeStoreDB'
                ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
                    sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                        GlobalVariable.NumofColm, 13))

                arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 85), (resultPartialCaptilized[
                        0]).replace('.00', ''), false, FailureHandling.OPTIONAL))
            }
        }
        
        if ((((((((datafileTabInsurance.getValue(GlobalVariable.NumofColm, 50).length() > 0) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 51).length() > 0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 52).length() > 
        0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 53).length() > 0)) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 54).length() > 0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 55).length() > 
        0)) || (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 56).length() > 0)) || (datafileTabInsurance.getValue(
            GlobalVariable.NumofColm, 57).length() > 0)) {
            'get insurance multi additional coverage from db'
            ArrayList<String> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
                sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'declare addrate'
            ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate

            def floodarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 50).split(';', -1)

            def tplarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 51).split(';', -1)

            def actofgodarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 52).split(';', -1)

            def srccarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 53).split(';', -1)

            def TJHTParray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 54).split(';', -1)

            def Kecelakaanarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 55).split(';', -1)

            def terroristarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 56).split(';', -1)

            def Theftrobberyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 57).split(';', -1)

            def floodratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 71).split(';', -1)

            def tplratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 72).split(';', -1)

            def actofgodratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 73).split(';', -1)

            def srccratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 74).split(';', -1)

            def TJHTPratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 75).split(';', -1)

            def Kecelakaanratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 76).split(';', -1)

            def terroristratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 77).split(';', -1)

            def Theftrobberyratearray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 78).split(';', -1)

            indexdb = 0

            'looping verif result add cvg'
            for (int index = 0; index < (resultAddCVG.size() / 3); index++) {
                int year = Integer.parseInt(resultAddCVG[indexdb++])

                addcvg = (resultAddCVG[indexdb++])

                addpremirate = Double.parseDouble(resultAddCVG[indexdb++])

                if (addcvg.equalsIgnoreCase('Flood')) {
                    if ((floodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((floodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('TPL')) {
                    if ((tplarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((tplarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Act of God')) {
                    if ((actofgodarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((actofgodarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('SRCC')) {
                    if ((srccarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((srccarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
                    if ((TJHTParray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((TJHTParray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
                    if ((Kecelakaanarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Kecelakaanarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Terrorist')) {
                    if ((terroristarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((terroristarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                } else if (addcvg.equalsIgnoreCase('Theft & Robbery')) {
                    if ((Theftrobberyarray[(year - 1)]).length() > 0) {
                        arrayMatch.add(WebUI.verifyMatch((Theftrobberyarray[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
                    }
                }
                
                'verify additional rate pada db = confins'
                arrayMatch.add(WebUI.verifyEqual(AddRate[index], addpremirate))
            }
        }
        
        'cek jika ada main premi rate'
        if (datafileTabInsurance.getValue(GlobalVariable.NumofColm, 69).length() > 0) {
            ArrayList<String> MainRate = GlobalVariable.MainPremiRate

            'get arraylist main premi rate dari DB'
            ArrayList<String> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
                sqlconnectionLOS, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13))

            'get arraylist main premi rate dari excel'
            def mainpremirateArray = datafileTabInsurance.getValue(GlobalVariable.NumofColm, 69).split(';', -1)

            'looping untuk verify mainpremirate db = confins'
            for (int mainpremirate = 0; mainpremirate < resultMainPremiRate.size(); mainpremirate++) {
                'verify main rate pada db = confins'
                arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(resultMainPremiRate[
                            mainpremirate])))
            }
        }
    }
}