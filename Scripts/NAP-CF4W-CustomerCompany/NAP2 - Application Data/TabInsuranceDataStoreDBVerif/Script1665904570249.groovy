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
import org.openqa.selenium.WebElement

'connect DB'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData')

String insuredBy = datafileTabInsurance.getValue(
	GlobalVariable.NumofColm, 12)

int arrayindex = 0

ArrayList<String> arraysuminsured = new ArrayList<Boolean>()

ArrayList<String> arrayaddpremi = new ArrayList<Boolean>()

ArrayList<String> arrayMatch = new ArrayList<Boolean>()

'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
	insuredCust(arrayMatch,sqlconnectionLOS)
} else if (insuredBy == 'Customer - Multifinance') {
	insuredCustMF(arrayMatch,sqlconnectionLOS,sqlconnectionFOU)
} else if (insuredBy == 'Multifinance') {
	insuredMF(arrayMatch,sqlconnectionLOS,sqlconnectionFOU)
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 0,
		GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 1,
		GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}

public insuredCust(ArrayList<Boolean> arrayMatch, Sql sqlconnectionLOS){
	ArrayList<Boolean> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustStoreDB'(sqlconnectionLOS,
		findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13))

	'ganti value null > "" (String kosong)'
	for (int i = 0; i <= (result.size() - 1); i++) {

		if ((result[i]) == 'true') {
			(result[i]) = 'Yes'
		} else if ((result[i]) == 'false') {
			(result[i]) = 'No'
		}
	}
	
	'index 14 karena mengikuti row di data file / excel'
	for (int index = 14; index < (result.size() + 14); index++) {
		'verify insco branch name'
		arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, index).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	}
}

public insuredCustMF(ArrayList<Boolean> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU){
	ArrayList<Boolean> resultCustomerInsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustMFStoreDB'(
		sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13))

	ArrayList<Boolean> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS,
		findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13))

	'ganti value null > "" (String kosong)'
	for (int i = 0; i <= (resultCustomerInsurance.size() - 1); i++) {

		if ((resultCustomerInsurance[i]) == 'true') {
			(resultCustomerInsurance[i]) = 'Yes'
		} else if ((resultCustomerInsurance[i]) == 'false') {
			(resultCustomerInsurance[i]) = 'No'
		}
	}
	
	'ganti value null > "" (String kosong)'
	for (int i = 0; i <= (resultMFinsurance.size() - 1); i++) {

		if ((resultMFinsurance[i]) == 'true') {
			(resultMFinsurance[i]) = 'Yes'
		} else if ((resultMFinsurance[i]) == 'false') {
			(resultMFinsurance[i]) = 'No'
		}
	}
	
	'index 14 karena mengikuti row di data file / excel'
	for (int index = 14; index <= (resultCustomerInsurance.size() + 13); index++) {
		'index -13 supaya dapat verif apakah index sudah sampai index terakhir dari array'
		if ((index - 13) != resultCustomerInsurance.size()) {
			'verify insco branch name'
			arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
						GlobalVariable.NumofColm, index).toUpperCase().replace(',', ''), (resultCustomerInsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
		} else if ((index - 13) == resultCustomerInsurance.size()) {
		   
			arrayMatch.add(WebUI.verifyMatch(convertDate(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 19)), (resultCustomerInsurance[arrayindex++]).toUpperCase(), false,
					FailureHandling.OPTIONAL))
		}
	}
	
	arrayindex = 0

	'verify asset region'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify coverage amount'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify cover period'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify payment type'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify insco branch name'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify insurance note'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	if ((datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
		'verify insurance length'
		arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
				false, FailureHandling.OPTIONAL))
	} else if ((datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Annualy') || (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
		'skip verify length insurance'
		resultMFinsurance[arrayindex++]
	}
	
	'verify admin fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify customer stampduty fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	if (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 36).length() == 0) {
		ArrayList<Boolean> resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS,
			findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13))

		'verify main coverage'
		arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), (resultMainCVG[0]).toUpperCase(), false,
				FailureHandling.OPTIONAL))

		if (((((((datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
			ArrayList<Boolean> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))

			for (int index = 0; index < resultAddCVG.size(); index++) {
				if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
				}
			}
		}
	} else {
		'Mengambil nilai setting cap insurance dari db'
		String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)

		ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
			sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13))

		'ganti value null > "" (String kosong)'
		for (int i = 0; i <= (resultMultiMainCVG.size() - 1); i++) {

			if ((resultMultiMainCVG[i]) == 'true') {
				(resultMultiMainCVG[i]) = 'Yes'
			} else if ((resultMultiMainCVG[i]) == 'false') {
				(resultMultiMainCVG[i]) = 'No'
			}
		}
		
		def capitalizedarray = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 45).split(';', -1)

		def paidbyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm,
			46).split(';', -1)

		def suminsuredarray = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 47).split(';', -1)

		def maincvgarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm,
			48).split(';', -1)

		int indexdb = 0

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
		
		if(capinssetting.equalsIgnoreCase('PARTIAL')){
			if (datafileTabInsurance.getValue(GlobalVariable.NumofColm,
				84).equalsIgnoreCase('NO')) {
				
				'call keyword NAP2InsurancePartialCaptilizeStoreDB'
				ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
					sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
						GlobalVariable.NumofColm, 13))
	
				arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
							GlobalVariable.NumofColm, 85), (resultPartialCaptilized[0]).replace('.00', ''), false,
						FailureHandling.OPTIONAL))
			}
		}
		
		if ((((((((datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 50).length() > 0) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 51).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 52).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 53).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 54).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 55).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 56).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 57).length() > 0)) {
			ArrayList<Boolean> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))
			
			ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate

			'ganti value null > "" (String kosong)'
			for (int i = 0; i <= (resultAddCVG.size() - 1); i++) {

				if ((resultAddCVG[i]) == 'true') {
					(resultAddCVG[i]) = 'Yes'
				} else if ((resultAddCVG[i]) == 'false') {
					(resultAddCVG[i]) = 'No'
				}
			}
			
			println(resultAddCVG)

			def floodarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 50).split(';', -1)

			def tplarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 51).split(';', -1)

			def actofgodarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 52).split(';', -1)

			def srccarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 53).split(';', -1)

			def TJHTParray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 54).split(';', -1)

			def Kecelakaanarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 55).split(';', -1)

			def terroristarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 56).split(';', -1)

			def Theftrobberyarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 57).split(';', -1)

			def floodratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 71).split(';', -1)

			def tplratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 72).split(';', -1)

			def actofgodratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 73).split(';', -1)

			def srccratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 74).split(';', -1)

			def TJHTPratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 75).split(';', -1)

			def Kecelakaanratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 76).split(';', -1)

			def terroristratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 77).split(';', -1)

			def Theftrobberyratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 78).split(';', -1)

			indexdb = 0

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
				arrayMatch.add(WebUI.verifyEqual(AddRate[(index)], addpremirate))
			}
		}
			
		
		'cek jika ada main premi rate'
		if (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 69).length() > 0) {
		
			ArrayList<String> MainRate = GlobalVariable.MainPremiRate
			'get arraylist main premi rate dari DB'
			ArrayList<Boolean> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))

			'get arraylist main premi rate dari excel'
			def mainpremirateArray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 69).split(';', -1)

			'looping untuk verify mainpremirate db = confins'
			for (int mainpremirate = 0; mainpremirate < MainRate.size(); mainpremirate++) {
				'verify main rate pada db = confins'
				arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(
								resultMainPremiRate[mainpremirate])))
			}
		}
	}
}

public insuredMF(ArrayList<Boolean> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU){
	arrayindex = 0
	
	ArrayList<Boolean> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS,
		findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13))

	'ganti value null > "" (String kosong)'
	for (int i = 0; i <= (resultMFinsurance.size() - 1); i++) {

		if ((resultMFinsurance[i]) == 'true') {
			(resultMFinsurance[i]) = 'Yes'
		} else if ((resultMFinsurance[i]) == 'false') {
			(resultMFinsurance[i]) = 'No'
		}
	}
	
	'verify asset region'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify coverage amount'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify cover period'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify payment type'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify insco branch name'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify insurance note'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	if ((datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
		'verify insurance length'
		arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
				false, FailureHandling.OPTIONAL))
	} else if ((datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Annualy') || (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
		'skip verify length insurance'
		resultMFinsurance[arrayindex++]
	}
	
	'verify admin fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	'verify customer stampduty fee'
	arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))

	if (datafileTabInsurance.getValue(
		GlobalVariable.NumofColm, 36).length() == 0) {
		String resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS,
			findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13))

		'verify main coverage'
		arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
					GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), resultMainCVG.replace('[', '').replace(
					']', '').toUpperCase(), false, FailureHandling.OPTIONAL))

		if (((((((datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 36).equalsIgnoreCase('Yes') || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 37).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 38).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 39).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 40).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 41).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 42).equalsIgnoreCase('Yes')) || datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 43).equalsIgnoreCase('Yes')) {
			ArrayList<Boolean> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))

			for (int index = 0; index < resultAddCVG.size(); index++) {
				if ((resultAddCVG[index]).equalsIgnoreCase('Flood')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 36), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('TPL')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 37), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Act of God')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 38), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('SRCC')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 39), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Tanggung Jawab Hukum Terhadap Penumpang')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 40), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Kecelakaan Diri Untuk Penumpang')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 41), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Terrorist')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 42), 'YES', false, FailureHandling.OPTIONAL))
				} else if ((resultAddCVG[index]).equalsIgnoreCase('Theft & Robbery')) {
					arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
								GlobalVariable.NumofColm, 43), 'YES', false, FailureHandling.OPTIONAL))
				}
			}
		}
	} else {
		'Mengambil nilai setting cap insurance dari db'
		String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)

		ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
			sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13))

		'ganti value null > "" (String kosong)'
		for (int i = 0; i <= (resultMultiMainCVG.size() - 1); i++) {

			if ((resultMultiMainCVG[i]) == 'true') {
				(resultMultiMainCVG[i]) = 'Yes'
			} else if ((resultMultiMainCVG[i]) == 'false') {
				(resultMultiMainCVG[i]) = 'No'
			}
		}
		
		def capitalizedarray = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 45).split(';', -1)

		def paidbyarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm,
			46).split(';', -1)

		def suminsuredarray = datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 47).split(';', -1)

		def maincvgarray = datafileTabInsurance.getValue(GlobalVariable.NumofColm,
			48).split(';', -1)

		int indexdb = 0

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
		
		if(capinssetting.equalsIgnoreCase('PARTIAL')){
			if (datafileTabInsurance.getValue(GlobalVariable.NumofColm,
				84).equalsIgnoreCase('NO')) {
				
				'call keyword NAP2InsurancePartialCaptilizeStoreDB'
				ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
					sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
						GlobalVariable.NumofColm, 13))
	
				arrayMatch.add(WebUI.verifyMatch(datafileTabInsurance.getValue(
							GlobalVariable.NumofColm, 85), (resultPartialCaptilized[0]).replace('.00', ''), false,
						FailureHandling.OPTIONAL))
			}
		}
		
		if ((((((((datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 50).length() > 0) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 51).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 52).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 53).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 54).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 55).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 56).length() > 0)) || (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 57).length() > 0)) {
			ArrayList<Boolean> resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))
			
			ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate

			'ganti value null > "" (String kosong)'
			for (int i = 0; i <= (resultAddCVG.size() - 1); i++) {

				if ((resultAddCVG[i]) == 'true') {
					(resultAddCVG[i]) = 'Yes'
				} else if ((resultAddCVG[i]) == 'false') {
					(resultAddCVG[i]) = 'No'
				}
			}
			
			println(resultAddCVG)

			def floodarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 50).split(';', -1)

			def tplarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 51).split(';', -1)

			def actofgodarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 52).split(';', -1)

			def srccarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 53).split(';', -1)

			def TJHTParray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 54).split(';', -1)

			def Kecelakaanarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 55).split(';', -1)

			def terroristarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 56).split(';', -1)

			def Theftrobberyarray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 57).split(';', -1)

			def floodratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 71).split(';', -1)

			def tplratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 72).split(';', -1)

			def actofgodratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 73).split(';', -1)

			def srccratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 74).split(';', -1)

			def TJHTPratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 75).split(';', -1)

			def Kecelakaanratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 76).split(';', -1)

			def terroristratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 77).split(';', -1)

			def Theftrobberyratearray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 78).split(';', -1)

			indexdb = 0

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
				arrayMatch.add(WebUI.verifyEqual(AddRate[(index)], addpremirate))
				
				
			}
		}
		
		'cek jika ada main premi rate'
		if (datafileTabInsurance.getValue(
			GlobalVariable.NumofColm, 69).length() > 0) {
			ArrayList<String> MainRate = GlobalVariable.MainPremiRate
			'get arraylist main premi rate dari DB'
			ArrayList<String> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
				sqlconnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					GlobalVariable.NumofColm, 13))

			'get arraylist main premi rate dari excel'
			def mainpremirateArray = datafileTabInsurance.getValue(
				GlobalVariable.NumofColm, 69).split(';', -1)

			'looping untuk verify mainpremirate db = confins'
			for (int mainpremirate = 0; mainpremirate < resultMainPremiRate.size(); mainpremirate++) {
				'verify main rate pada db = confins'
				arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(
								resultMainPremiRate[mainpremirate])))
			}
		}
	}
}

public convertDate(String enddate){
	Date enddate_Formated = new SimpleDateFormat('MM/dd/yyyy').parse(enddate)
	
	String inslength = GlobalVariable.InsuranceLength
	
	Calendar cal = Calendar.getInstance()
	
	cal.setTime(enddate_Formated)
	
	cal.add(Calendar.MONTH, Integer.parseInt(inslength))
	
	DateFormat dateFormat = new SimpleDateFormat('MM/dd/yyyy')
	
	String enddateFinal = dateFormat.format(cal.getTime())
	
	return enddateFinal
}