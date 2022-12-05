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

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabFamily'
datafileTabFamily = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData')

'get country value'
String Country = datafileTabFamily.getValue(GlobalVariable.NumofFamily, 36)

'get job position value'
String JobPosition = datafileTabFamily.getValue(GlobalVariable.NumofFamily, 38)

'get department aml value'
String DepartmentAML = datafileTabFamily.getValue(GlobalVariable.NumofFamily, 41)

'get authority aml value'
String AuthorityAML = datafileTabFamily.getValue(GlobalVariable.NumofFamily, 43)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get familydata from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.FamilyDataStoreDBPersonal'(sqlconnectionLOS, datafileTabFamily.getValue(
        GlobalVariable.NumofFamily, 12), datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19))

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'declare arrayindex'
int arrayindex = 0

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 18).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify family name'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 19).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 20).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 21).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'jika idtype bukan ektp atau bukan akta atau bukan npwp'
if ((!(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 22).equalsIgnoreCase('E-KTP')) || !(datafileTabFamily.getValue(
    GlobalVariable.NumofFamily, 22).equalsIgnoreCase('AKTA'))) || !(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
    22).equalsIgnoreCase('NPWP'))) {
    'verify id expired date'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 22).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
    arrayindex++
}

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 23).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile phone'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 24).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 25).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify profession code'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 26).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify employee establishment date'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 28).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 29).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 30).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 31).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 32).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 33).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 34).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify nationality'
arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify nationality value'
if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 35).equalsIgnoreCase('LOCAL')) {
    'set country menjadi string kosong'
    Country = ''
}

'verify country'
arrayMatch.add(WebUI.verifyMatch(Country.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify job position value'
if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 38) == '#N/A') {
    'set job position value'
    JobPosition = ''
}

'verify jobposition'
arrayMatch.add(WebUI.verifyMatch(JobPosition.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//'verify department aml value'
//if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
//    41) == '#N/A') {
//    'set department aml menjadi string kosong'
//    DepartmentAML = ''
//}
//
//'verify department aml'
//WebUI.verifyMatch(DepartmentAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//
//'verify authority aml value'
//if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 
//    43) == '#N/A') {
//    'set authority aml menjadi string kosong'
//    AuthorityAML = ''
//}
//
//'verify authority aml'
//WebUI.verifyMatch(AuthorityAML.toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))


'verify copy address atau tidak'
if (datafileTabFamily.getValue(GlobalVariable.NumofFamily, 46).equalsIgnoreCase('Yes')) {
	int confinsdataindex = 0
	
    'verify addres copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership copy dari customer'
    arrayMatch.add(WebUI.verifyMatch((GlobalVariable.Confinsdata[confinsdataindex++]), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 47).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 48).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 49).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 50).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 51).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 52).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 53).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership'
    arrayMatch.add(WebUI.verifyMatch(datafileTabFamily.getValue(GlobalVariable.NumofFamily, 54).toUpperCase(), 
            (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabFamilyData', GlobalVariable.NumofFamily, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}