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
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import groovy.sql.Sql as Sql

'declare datafileMS'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'get value porfession'
String Profession = datafileMS.getValue(GlobalVariable.NumofMS, 27)

'get value posittion slik'
String PositionSLIK = datafileMS.getValue(GlobalVariable.NumofMS, 29)

'get value job position'
String JobPosition = datafileMS.getValue(GlobalVariable.NumofMS, 38)

'get value department aml'
String DepartmentAML = datafileMS.getValue(GlobalVariable.NumofMS, 45)

'get value authority aml'
String AuthorityAML = datafileMS.getValue(GlobalVariable.NumofMS, 46)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare arraymatch arraylist'
ArrayList<Boolean> arrayMatch = new ArrayList<>()

'call keyword MS Store DB Personal'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.MSDataStoreDBPersonal'(sqlconnectionLOS, datafileMS.getValue(
        GlobalVariable.NumofMS, 12), datafileMS.getValue(GlobalVariable.NumofMS, 19))

int arrayindex = 0

'verify shareholder legal name'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 19).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 20).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 21).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

if ((datafileMS.getValue(GlobalVariable.NumofMS, 21).equalsIgnoreCase('E-KTP') || datafileMS.getValue(GlobalVariable.NumofMS, 
    21).equalsIgnoreCase('AKTA')) || datafileMS.getValue(GlobalVariable.NumofMS, 21).equalsIgnoreCase('NPWP')) {
    'skip id expired date'
    arrayindex++
} else {
    'verify id expired date'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 22).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 23).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify mobile phone'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify profession code no value'
if (datafileMS.getValue(GlobalVariable.NumofMS, 26).equalsIgnoreCase('#N/A')) {
    'set profession code menjadi string kosong'
    Profession = ''
}

'verify profession code'
arrayMatch.add(WebUI.verifyMatch(Profession.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify positionSLIK no value'
if (datafileMS.getValue(GlobalVariable.NumofMS, 28).equalsIgnoreCase('#N/A')) {
    'set positionslik menjadi string kosong'
    PositionSLIK = ''
}

'verify positionslik'
arrayMatch.add(WebUI.verifyMatch(PositionSLIK.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify employee establishment date'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 32).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 33).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 34).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 35).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 36).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify job position code no value'
if (datafileMS.getValue(GlobalVariable.NumofMS, 37).equalsIgnoreCase('#N/A')) {
    'set job position code menjadi string kosong'
    JobPosition = ''
}

'verify job profession'
arrayMatch.add(WebUI.verifyMatch(JobPosition.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify share percent'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 39).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is owner'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 40).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is active'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 41).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify is signer'
arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 42).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify department aml no value'
if (datafileMS.getValue(GlobalVariable.NumofMS, 44).equalsIgnoreCase('#N/A')) {
    'set department aml menjadi string kosong'
    DepartmentAML = ''
}

'verify department aml'
arrayMatch.add(WebUI.verifyMatch(DepartmentAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify authority aml no value'
if (datafileMS.getValue(GlobalVariable.NumofMS, 46).equalsIgnoreCase('#N/A')) {
    'set authority aml menjadi string kosong'
    AuthorityAML = ''
}

'verify authority aml'
arrayMatch.add(WebUI.verifyMatch(AuthorityAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify is addres copy atau tidak'
if (datafileMS.getValue(GlobalVariable.NumofMS, 70).length() > 0) {
    if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14).equalsIgnoreCase('LookUp')) {
        for (int addr = 0; addr < GlobalVariable.LookupAddressData.size(); addr++) {
            'verify address'
            arrayMatch.add(WebUI.verifyMatch(GlobalVariable.LookupAddressData[addr], (result[arrayindex++]).toUpperCase(), 
                    false, FailureHandling.OPTIONAL))
        }
    } else {
        'verify address copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 24).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify RT copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 25).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify RW copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 26).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify Zipcode copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 27).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify kecamatan copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 28).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify kelurahan copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 29).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify kota copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 30).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))

        'verify ownership copy dari customer'
        arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofColm, 31).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))
    }
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 71).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify RT'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 72).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify RW'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 73).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify Zipcode'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 74).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 75).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 76).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 77).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify ownership'
    arrayMatch.add(WebUI.verifyMatch(datafileMS.getValue(GlobalVariable.NumofMS, 78).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, 
        GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

