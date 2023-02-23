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

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathTabAsset'
excelPathTabAsset = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabAssetData'

'get supplier data from db'
ArrayList<String> resultsupplierinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabAssetSupplierInfoStoreDB'(
    sqlconnection, findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofAsset, 13))

'get asset data from db'
ArrayList<String> resultassetinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabAssetStoreDB'(sqlconnection, 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofAsset, 13))

'get asset document from db'
ArrayList<String> resultdocumentinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2AssetDocumentStoreDB'(sqlconnection, 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofAsset, 13))

ArrayList<String> arrayMatch = new ArrayList<String>()

int arraysuppinfoindex = 0

int arrayassetinfoindex = 0

'verify supplier code'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 12).toUpperCase(), (resultsupplierinformation[
        arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify supplier name'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 13).toUpperCase(), (resultsupplierinformation[
        arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify sales person'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 14).toUpperCase(), (resultsupplierinformation[
        arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify admin head'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 15).toUpperCase(), (resultsupplierinformation[
        arraysuppinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset code'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 17).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset condition'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 18).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify asset price'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 19).replace(',', ''), resultassetinformation[
        arrayassetinfoindex++], false, FailureHandling.OPTIONAL))

'verify asset usage'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 20).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 21).length() > 0){
'verify color'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 21).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'skip color'
arrayassetinfoindex++
}

if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 22).length() > 0){
'verify BPKB ISSUER'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 22).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'skip bpkb'
arrayassetinfoindex++
}

if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 23).length() > 0){
'verify BPKB ISSUE DATE'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 23).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'skip bpkb issue date'
arrayassetinfoindex++
}

if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 24).length() > 0){
'verify note'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 24).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
'skip note'
arrayassetinfoindex++
}

'verify manufacturing year'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 25).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26).equalsIgnoreCase('Percentage')) {
    'verify DP percent'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 27).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'skip amount'
    arrayassetinfoindex++
} else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 26).equalsIgnoreCase('Amount')) {
    'skip percentage'
    arrayassetinfoindex++

    'verify DP Amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 28).replace(',', ''), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'verify chasis number'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 29).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify engine number'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 30).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify license plate'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 31).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

serial4 = resultassetinformation[arrayassetinfoindex++]

if(serial4 != ''){
'verify serial no 4'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 32).toUpperCase(), (serial4).toUpperCase(), false, FailureHandling.OPTIONAL))
}

serial5 = resultassetinformation[arrayassetinfoindex++]

if(serial5 != ''){
'verify serial no 5'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 33).toUpperCase(), (serial5).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'verify asset region'
arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 35).toUpperCase(), (resultassetinformation[
        arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 37).equalsIgnoreCase('No')) {
    'verify user name'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 38).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify user relation'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 39).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	'skip verif user name'
	arrayassetinfoindex++
	
	'skip verif user relation'
	arrayassetinfoindex++
}

if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 41).equalsIgnoreCase('No')) {
    'verify owner type'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner name'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 43).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner relation'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 44).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 42).equalsIgnoreCase('Company')) {
	   'verify owner profession'
	    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 47).toUpperCase(), (resultassetinformation[
	         arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    } else {
		if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 46).length() > 0){
	        'verify owner profession'
	        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 46).toUpperCase(), (resultassetinformation[
	                arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		}else{
			arrayassetinfoindex++
		}
    }
    
    'verify owner id type'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 48).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner id no'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 49).toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 50).length() > 0){
	    'verify owner mobile no'
	    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 50).toUpperCase(), (resultassetinformation[
	            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else{
		arrayassetinfoindex++
	}
	
	'verify copy address'
	if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52).equalsIgnoreCase('Yes')) {
		
		address = 0
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Rt'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify RW'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Zipcode'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		
		address = 0
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Rt'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify RW'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify Zipcode'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	} else {
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 54).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rt'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 55).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rw'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 56).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify zipcode'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 57).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 58).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 59).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 60).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		
		'verify address'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 54).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rt'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 55).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify rw'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 56).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify zipcode'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 57).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kelurahan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 58).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kecamatan'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 59).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
		'verify kota'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 60).toUpperCase(), (resultassetinformation[
				arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
}else{
	confins = 0
	
	'verify owner type'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner name'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner relation'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner profession'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
              arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
   
    'verify owner id type'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner id no'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify owner mobile no'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confins++].toUpperCase(), (resultassetinformation[
            arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	address = confins
	
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Zipcode'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52).equalsIgnoreCase('Yes')){
	address = confins
	
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify RW'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Zipcode'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[address++].toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}else if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 52).equalsIgnoreCase('No')){
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 54).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rt'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 55).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rw'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 56).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify zipcode'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 57).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 58).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 59).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 60).toUpperCase(), (resultassetinformation[
			arrayassetinfoindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
}

'looping data document'
for (int i = 0; i < resultdocumentinformation.size(); i) {
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 64).length() > 0) {
        'verify received'
        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 64).toUpperCase(), (resultdocumentinformation[
                i++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    } else {
        i++
    }
    
    'verify document no'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 65).toUpperCase(), (resultdocumentinformation[
            i++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify expired date'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 66), resultdocumentinformation[
            i++], false, FailureHandling.OPTIONAL))

    'verify document notes'
    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 67).toUpperCase(), (resultdocumentinformation[
            i++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofAsset, GlobalVariable.StatusFailed, 
        findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
}

