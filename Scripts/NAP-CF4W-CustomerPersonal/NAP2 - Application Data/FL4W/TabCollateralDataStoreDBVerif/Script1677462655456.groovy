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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect DB'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathCollateral'
excelPathCollateral = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP2-ApplicationData/TabCollateralData')

'get asset data from db'
ArrayList<String> resultCollateralinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabCollateralStoreDB'(sqlconnection, 
    findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))

'get asset document from db'
ArrayList<String> resultdocumentinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2AssetDocumentStoreDB'(sqlconnection, 
    findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))

'keyword untuk mencari nama-nama asset document yang muncul pada tab collateral'
ArrayList<String> docName = CustomKeywords.'assetData.checkAssetDoc.checkCollateralDocument'(sqlConnectionFOU, findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14))

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'declare arrayCollateralinfoindex,arraydocinfoindex'
int arrayCollateralinfoindex = 1

int arraydocinfoindex = 0

'verify Collateral Status'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).toUpperCase(), (resultCollateralinformation[
        arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Collateral type'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14).toUpperCase(), (resultCollateralinformation[
        arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Collateral code'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 15).replace(',', ''), resultCollateralinformation[
        arrayCollateralinfoindex++], false, FailureHandling.OPTIONAL))

'verify collateral name'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 16).toUpperCase(), (resultCollateralinformation[
        arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'check if input manufacturing year'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 17).length() > 0){
	'verify manufacturing year'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 17).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else {
	'skip manufacturing year'
	arrayCollateralinfoindex++
}

'verify collateral condition'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 18).toUpperCase(), (resultCollateralinformation[
		arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify collateral usage'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 19).toUpperCase(), (resultCollateralinformation[
		arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'check if input tax date'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 20).length() > 0){
	'verify collateral tax date'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 20).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip color'
	arrayCollateralinfoindex++
}

'verify collateral price'
arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 21).replace(',', '')), Double.parseDouble((resultCollateralinformation[
		arrayCollateralinfoindex++])), FailureHandling.OPTIONAL))

'verify collateral portion percentage'
arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 22).replace(',', '')), Double.parseDouble((resultCollateralinformation[
		arrayCollateralinfoindex++])), FailureHandling.OPTIONAL))

'verify collateral portion amount'
arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 23).replace(',', '')), Double.parseDouble((resultCollateralinformation[
		arrayCollateralinfoindex++])), FailureHandling.OPTIONAL))

'verify collateral remaining portion percentage'
arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 24).replace(',', '')), Double.parseDouble((resultCollateralinformation[
		arrayCollateralinfoindex++])), FailureHandling.OPTIONAL))

'check if input notes'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 25).length() > 0){
	'verify collateral notes'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 25).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip collateral notes'
	arrayCollateralinfoindex++
}
	
'check if input serial 1'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 26).length() > 0){
	'verify serial 1'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 26).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip serial 1'
	arrayCollateralinfoindex++
}

'check if input serial 2'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 27).length() > 0){
	'verify serial 2'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 27).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip serial 2'
	arrayCollateralinfoindex++
}

'check if input serial 3'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 28).length() > 0){
	'verify serial 3'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 28).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip serial 3'
	arrayCollateralinfoindex++
}

'check if input serial 4'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 29).length() > 0){
	'verify serial 4'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 29).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip serial 4'
	arrayCollateralinfoindex++
}

'check if input serial 5'
if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 30).length() > 0){
	'verify serial 5'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 30).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip serial 5'
	arrayCollateralinfoindex++
}

if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 34).equalsIgnoreCase('No')) {
    'verify owner type'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner name'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 36).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner relation'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 37).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35).equalsIgnoreCase('Personal')) {
		if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 46).length() > 0){
        'verify owner profession'
        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 39).toUpperCase(), (resultCollateralinformation[
                arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		}else{
		arrayCollateralinfoindex++
		}
    } else {
        'verify owner profession'
        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 40).toUpperCase(), (resultCollateralinformation[
                arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
    
    'verify owner id type'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 41).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner id no'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 42).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner mobile no'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 43).toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify copy address'
	if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45).equalsIgnoreCase('Yes')) {
		address = 0
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Rt'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify RW'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Zipcode'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		
		address = 0
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Rt'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify RW'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Zipcode'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	} else {
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 47).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rt'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 48).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rw'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 49).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify zipcode'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 50).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 51).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 52).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 53).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 47).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rt'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 48).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rw'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 49).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify zipcode'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 50).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 51).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 52).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 53).toUpperCase(), (resultCollateralinformation[
				arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
}else{
	confins = 0
	
	'verify owner type'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
    'verify owner name'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner relation'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner profession'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
              arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
   
    'verify owner id type'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner id no'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner mobile no'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultCollateralinformation[
            arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	address = confins
	
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Zipcode'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45).equalsIgnoreCase('Yes')){
	address = confins
	
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Zipcode'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45).equalsIgnoreCase('No')){
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 47).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rt'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 48).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rw'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 49).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify zipcode'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 50).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 51).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 52).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 53).toUpperCase(), (resultCollateralinformation[
			arrayCollateralinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
}

'looping data document'
for (int i = 0; i < docName.size(); i) {
    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 57).length() > 0) {
        'verify received'
        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 57).toUpperCase(), (resultdocumentinformation[
                i++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    } else {
        i++
    }
    
    'verify document no'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 58).toUpperCase(), (resultdocumentinformation[
            i++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify expired date'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 59), resultdocumentinformation[
            i++], false, FailureHandling.OPTIONAL))

    'verify document notes'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 60).toUpperCase(), (resultdocumentinformation[
            i++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, GlobalVariable.StatusFailed, 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 2) + ';'+GlobalVariable.ReasonFailedStoredDB)
}

