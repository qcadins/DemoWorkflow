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

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileGuarantorPersonal'
datafileGuarantorPersonal = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal')

'get departmentAML dari data file'
String DepartmentAML = datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 33)

'get AuthorityAML dari data file'
String AuthorityAML = datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 35)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare arraymatch arraylist'
ArrayList<String> arrayMatch = new ArrayList<String>()

'call keyword guarantor data store db personal'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.GuarantorDataStoreDBPersonal'(sqlconnectionLOS, 
    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12), datafileGuarantorPersonal.getValue(
        GlobalVariable.NumofGuarantorPersonal, 19))

int arrayindex = 0

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify guarantor name'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 20).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if ((datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21).equalsIgnoreCase('E-KTP') || datafileGuarantorPersonal.getValue(
    GlobalVariable.NumofGuarantorPersonal, 21).equalsIgnoreCase('AKTA')) || datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
    21).equalsIgnoreCase('NPWP')) {
    'skip id expired date'
    arrayindex++
} else {
    'verify id expired date'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 22).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 23).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile phone'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 24).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 25).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 26).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 29).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 31).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//'verify department aml value'
//if (datafileGuarantorPersonal.getValue(
//    GlobalVariable.NumofGuarantorPersonal, 33) == '#N/A') {
//    'set department aml value menjadi string kosong'
//    DepartmentAML = ''
//}
//
//'verify department aml'
//WebUI.verifyMatch(DepartmentAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//
//'verify authority aml value'
//if (datafileGuarantorPersonal.getValue(
//    GlobalVariable.NumofGuarantorPersonal, 35) == '#N/A') {
//    'set authority aml value menjadi kosong'
//    AuthorityAML = ''
//}
//
//'verify authority aml'
//WebUI.verifyMatch(AuthorityAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
'verify copy address atau tidak'
if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 38).equalsIgnoreCase('Yes')) {
    'verify addres copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 24).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 25).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 26).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 27).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 28).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 29).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 30).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerCompany.getValue(GlobalVariable.NumofGuarantorPersonal, 31).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 39).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 40).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 41).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 42).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 43).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 44).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 45).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership'
    arrayMatch.add(WebUI.verifyMatch(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 46).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
        0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
        1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.ReasonFailedStoredDB)
}

