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

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'get department aml value'
String DepartmentAML = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 33)

'get authority aml value'
String AuthorityAML = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 35)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get cust data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.CustomerDataStoreDBPersonal'(sqlconnectionLOS, 
    datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))

'declare arrayindex'
int arrayindex = 0

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'verify product offering code'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 12).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer name'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 19).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 20).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika id type e-ktp atau akta atau npwp'
if ((datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 21).equalsIgnoreCase('E-KTP') || datafileCustomerPersonal.getValue(
    GlobalVariable.NumofColm, 21).equalsIgnoreCase('AKTA')) || datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
    21).equalsIgnoreCase('NPWP')) {
    'skip expired date'
    arrayindex++
} else {
    'verify id expired date'
    arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 22).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 23).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile phone'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 24).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 25).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 26).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 27).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 28).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 29).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 30).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 31).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//'verify department aml no value'
//if (datafileCustomerPersonal.getValue(
//    GlobalVariable.NumofColm, 33) == '#N/A') {
//    'set department aml menjadi string kosong'
//    DepartmentAML = ''
//}
//
//'verify department aml'
//WebUI.verifyMatch(DepartmentAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
//
//'verify authority aml no value'
//if (datafileCustomerPersonal.getValue(
//    GlobalVariable.NumofColm, 35) == '#N/A') {
//    'set authority aml menjadi string kosong'
//    AuthorityAML = ''
//}
//
//'verify authority aml'
//WebUI.verifyMatch(AuthorityAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify address'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 38).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Rt'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 39).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify RW'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 40).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Zipcode'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 41).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kecamatan'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 42).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kelurahan'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 43).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify kota'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 44).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify ownership'
arrayMatch.add(WebUI.verifyMatch(datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 45).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.ReasonFailedStoredDB)
}