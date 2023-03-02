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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1 DataFile_NAP_CF4W.xlsx")

'connect DB los'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect db fou'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathTabInsurance'
excelPathTabInsurance = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData'

'get insuredby from excel'
String insuredBy = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 12)

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
        GlobalVariable.StatusFailed, findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedStoredDB)
}

def insuredCust(ArrayList<String> arrayMatch, Sql sqlconnectionLOS) {
	arrayindex = 0
	
    'get insurance cust data from db'
    ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustStoreDB'(sqlconnectionLOS, 
        findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 13))

    'index 14 karena mengikuti row di data file / excel'
    for (int index = 14; index < (result.size() + 14); index++) {
        'verify insco branch name'
        arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, index).toUpperCase(), (result[
                arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
    }
}

def insuredCustMF(ArrayList<String> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU, int arrayindex) {
	
	ArrayList<String> resultAddCVG
	
	'call keyword NAP2InsuranceCustMFStoreDB untuk get data dari db'
	ArrayList<Boolean> resultCustomerInsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceCustMFStoreDB'(
			sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
					GlobalVariable.NumofColm, 13))
	
	'call keyword NAP2InsuranceMFStoreDB untuk get data dari db'
	ArrayList<Boolean> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS,
			findTestData(excelPathCustomerPersonal).getValue(
					GlobalVariable.NumofColm, 13))
	
	'index 14 karena mengikuti row di data file / excel'
	for (int index = 14; index <= (resultCustomerInsurance.size() + 13); index++) {
		'index -13 supaya dapat verif apakah index sudah sampai index terakhir dari array'
		if ((index - 13) != resultCustomerInsurance.size()) {
			'verify insco branch name'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, index).toUpperCase().replace(',', ''), (resultCustomerInsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
		} else if ((index - 13) == resultCustomerInsurance.size()) {
			
			String countDate = CustomKeywords.'customizeKeyword.convertDate.countDateInsurance'(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 19))
					
					arrayMatch.add(WebUI.verifyMatch(countDate, (resultCustomerInsurance[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
		}
	}
	
	arrayindex = 0
			
			'verify asset region'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify coverage amount'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify cover period'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify payment type'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify insco branch name'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify insurance note'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			if ((findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (findTestData(excelPathTabInsurance).getValue(
							GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
				'verify insurance length'
				arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
						GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
						false, FailureHandling.OPTIONAL))
			} else if ((findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24) == 'Annualy') || (findTestData(excelPathTabInsurance).getValue(
							GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
				'skip verify length insurance'
				resultMFinsurance[arrayindex++]
			}
	
	'verify admin fee'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify customer stampduty fee'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	
	ArrayList<Boolean> resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS,
			findTestData(excelPathCustomerPersonal).getValue(
					GlobalVariable.NumofColm, 13))
	
	'verify main coverage'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), (resultMainCVG[0]).toUpperCase(), false,
			FailureHandling.OPTIONAL))
	
	int rowAddCvg = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Additional Coverage')
	
	int rowEditInsTable = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table')
	
	resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(
			sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
					GlobalVariable.NumofColm, 13))
	
	for(i = rowAddCvg+2; i <= rowEditInsTable; i++){
		if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).equalsIgnoreCase('Yes')) {
			for (int index = 0; index < resultAddCVG.size(); index++) {
				if ((resultAddCVG[index]).equalsIgnoreCase(findTestData(excelPathTabInsurance).getValue(1, i).replace('$',''))) {
					arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
							GlobalVariable.NumofColm, i), 'YES', false, FailureHandling.OPTIONAL))
					
					break
				}else{
					continue
				} 
			}
		}
	}
	
	'Mengambil nilai setting cap insurance dari db'
	String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)
			
			if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+2).length() > 0 || findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+3).length() > 0
					|| findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+4).length() > 0 || findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+5).length() > 0) {
				
				'call keyword NAP2InsuranceMultiMainCVGtoreDB untuk get data dari db'
				ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
						sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
								GlobalVariable.NumofColm, 13))
						
						def capitalizedarray = findTestData(excelPathTabInsurance).getValue(
								GlobalVariable.NumofColm, rowEditInsTable+2).split(';', -1)
						
						def paidbyarray = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
								rowEditInsTable+3).split(';', -1)
						
						def suminsuredarray = findTestData(excelPathTabInsurance).getValue(
								GlobalVariable.NumofColm, rowEditInsTable+4).split(';', -1)
						
						def maincvgarray = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
								rowEditInsTable+5).split(';', -1)
						
						int indexdb = 0
						
						for (int index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
							
							indexdb++
							
							if (capinssetting.equalsIgnoreCase('YEARLY')) {
								'verif capitalized'
								if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+2).length() > 0) {
									arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
											false, FailureHandling.OPTIONAL))
								}
							} else if (capinssetting.equalsIgnoreCase('PARTIAL')) {
								'skip is capitalized'
								indexdb++
							}
							
							indexdb++
							
							'verif paid by'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+3).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((paidbyarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
							
							'verif sum insured'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+4).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((suminsuredarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
							
							'verif main cvg'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+5).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((maincvgarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
						}
			}
	if(capinssetting.equalsIgnoreCase('PARTIAL')){
		if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
				84).equalsIgnoreCase('NO')) {
			
			'call keyword NAP2InsurancePartialCaptilizeStoreDB'
			ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
					sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
					
					arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
							GlobalVariable.NumofColm, 85), (resultPartialCaptilized[0]).replace('.00', ''), false,
							FailureHandling.OPTIONAL))
		}
	}
	
	int rowEditAddCvg = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Additional Coverage')
			
			int rowSuminsuredAmt = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Sum Insured Amount')
			
			resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
					sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
			
			for(i = rowEditAddCvg+2; i <= rowSuminsuredAmt; i++){
				if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).length() > 0) {
					
					ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate
							
							def arrayAddCvg = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).split(';', -1)
							
							indexdb = 0
							
							for (int index = 0; index < (resultAddCVG.size()/3); index++) {
								int year = Integer.parseInt(resultAddCVG[indexdb++])
										
										addcvg = (resultAddCVG[indexdb++])
										
										addpremirate = Double.parseDouble(resultAddCVG[indexdb++])
										
										if (addcvg.equalsIgnoreCase(findTestData(excelPathTabInsurance).getValue(1, i))) {
											if(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).length() > 0){
												if ((arrayAddCvg[(year - 1)]).length() > 0) {
													arrayMatch.add(WebUI.verifyMatch((arrayAddCvg[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
													
													continue
												}
											}
										} else {
											continue
										}
								
								'verify additional rate pada db = confins'
								arrayMatch.add(WebUI.verifyEqual(Math.round(AddRate[(index)]), Math.round(addpremirate)))
								
							}
				}
			}
	
	'cek jika ada main premi rate'
	if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 69).length() > 0) {
		ArrayList<String> MainRate = GlobalVariable.MainPremiRate
				'get arraylist main premi rate dari DB'
				ArrayList<String> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
						sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
								GlobalVariable.NumofColm, 13))
				
				'get arraylist main premi rate dari excel'
				def mainpremirateArray = findTestData(excelPathTabInsurance).getValue(
						GlobalVariable.NumofColm, 69).split(';', -1)
				
				'looping untuk verify mainpremirate db = confins'
				for (int mainpremirate = 0; mainpremirate < resultMainPremiRate.size(); mainpremirate++) {
					'verify main rate pada db = confins'
					arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(
							resultMainPremiRate[mainpremirate])))
				}
	}
}

def insuredMF(ArrayList<String> arrayMatch, Sql sqlconnectionLOS, Sql sqlconnectionFOU) {
	arrayindex = 0
			
			ArrayList<String> resultAddCVG
			
			'call keyword NAP2InsuranceMFStoreDB untuk get data dari db'
			ArrayList<Boolean> resultMFinsurance = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMFStoreDB'(sqlconnectionLOS,
					findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
			
			'verify asset region'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify coverage amount'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify cover period'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify payment type'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			'verify insco branch name'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
					false, FailureHandling.OPTIONAL))
			
			if(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 27).length() > 0){
				'verify insurance note'
				arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
						GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
						false, FailureHandling.OPTIONAL))
			}else{
				arrayindex++
			}
	
	if ((findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 24) == 'Partial Tenor') || (findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24) == 'Over Tenor')) {
		'verify insurance length'
		arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
				GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
				false, FailureHandling.OPTIONAL))
	} else if ((findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 24) == 'Annualy') || (findTestData(excelPathTabInsurance).getValue(
					GlobalVariable.NumofColm, 24) == 'Full Tenor')) {
		'skip verify length insurance'
		resultMFinsurance[arrayindex++]
	}
	
	'verify admin fee'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	'verify customer stampduty fee'
	arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
			GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultMFinsurance[arrayindex++]).toUpperCase(),
			false, FailureHandling.OPTIONAL))
	
	
	'get insurance main coverage from db'
	ArrayList<String> resultMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainCVGtoreDB'(sqlconnectionLOS, 
			findTestData(excelPathCustomerPersonal).getValue(
					GlobalVariable.NumofColm, 13))
	
	if(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 48).length() == 0){
		for(int cvg = 0; cvg < resultMainCVG.size(); cvg++){
			'verify main coverage'
			arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 34).toUpperCase(), resultMainCVG[cvg].toUpperCase(), false, FailureHandling.OPTIONAL))
		}
	}
	
	int rowAddCvg = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Additional Coverage')
			
			int rowEditInsTable = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Generated Insurance Table')
			
			resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceAddCVGtoreDB'(
					sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
			
			for(i = rowAddCvg+2; i <= rowEditInsTable; i++){
				if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).equalsIgnoreCase('Yes')) {
					for (int index = 0; index < resultAddCVG.size(); index++) {
						if ((resultAddCVG[index]).equalsIgnoreCase(findTestData(excelPathTabInsurance).getValue(1, i).replace('$',''))) {
							arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
									GlobalVariable.NumofColm, i), 'YES', false, FailureHandling.OPTIONAL))
							
							break
						}else{
							continue
						} 
					}
				}
			}
	
	'Mengambil nilai setting cap insurance dari db'
	String capinssetting = CustomKeywords.'insuranceData.checkCapitalizeSetting.checkInsuranceCapSetting'(sqlconnectionFOU)
			
			if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+2).length() > 0 || findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+3).length() > 0
					|| findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+4).length() > 0 || findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+5).length() > 0) {
				
				'call keyword NAP2InsuranceMultiMainCVGtoreDB untuk get data dari db'
				ArrayList<String> resultMultiMainCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiMainCVGtoreDB'(
						sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
								GlobalVariable.NumofColm, 13))
						
						def capitalizedarray = findTestData(excelPathTabInsurance).getValue(
								GlobalVariable.NumofColm, rowEditInsTable+2).split(';', -1)
						
						def paidbyarray = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
								rowEditInsTable+3).split(';', -1)
						
						def suminsuredarray = findTestData(excelPathTabInsurance).getValue(
								GlobalVariable.NumofColm, rowEditInsTable+4).split(';', -1)
						
						def maincvgarray = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
								rowEditInsTable+5).split(';', -1)
						
						int indexdb = 0
						
						for (int index = 0; index < (resultMultiMainCVG.size() / 5); index++) {
							
							indexdb++
							
							if (capinssetting.equalsIgnoreCase('YEARLY')) {
								'verif capitalized'
								if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+2).length() > 0) {
									arrayMatch.add(WebUI.verifyMatch((capitalizedarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
											false, FailureHandling.OPTIONAL))
								}
							} else if (capinssetting.equalsIgnoreCase('PARTIAL')) {
								'skip is capitalized'
								indexdb++
							}
							
							indexdb++
							
							'verif paid by'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+3).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((paidbyarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
							
							'verif sum insured'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+4).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((suminsuredarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
							
							'verif main cvg'
							if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, rowEditInsTable+5).length() > 0) {
								arrayMatch.add(WebUI.verifyMatch((maincvgarray[index]).toUpperCase(), (resultMultiMainCVG[indexdb]).toUpperCase(),
										false, FailureHandling.OPTIONAL))
							}
							
							indexdb++
						}
			}
	if(capinssetting.equalsIgnoreCase('PARTIAL')){
		if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm,
				84).equalsIgnoreCase('NO')) {
			
			'call keyword NAP2InsurancePartialCaptilizeStoreDB'
			ArrayList<String> resultPartialCaptilized = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsurancePartialCaptilizeStoreDB'(
					sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
					
					arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathTabInsurance).getValue(
							GlobalVariable.NumofColm, 85), (resultPartialCaptilized[0]).replace('.00', ''), false,
							FailureHandling.OPTIONAL))
		}
	}
	
	int rowEditAddCvg = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Edit Additional Coverage')
			
			int rowSuminsuredAmt = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Sum Insured Amount')
			
			resultAddCVG = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMultiAddCVGtoreDB'(
					sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
							GlobalVariable.NumofColm, 13))
			
			for(i = rowEditAddCvg+2; i <= rowSuminsuredAmt; i++){
				if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).length() > 0) {
					
					ArrayList<String> AddRate = GlobalVariable.AdditionalPremiRate
							
							def arrayAddCvg = findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).split(';', -1)
							
							indexdb = 0
							
							for (int index = 0; index < (resultAddCVG.size()/3); index++) {
								int year = Integer.parseInt(resultAddCVG[indexdb++])
										
										addcvg = (resultAddCVG[indexdb++])
										
										addpremirate = Double.parseDouble(resultAddCVG[indexdb++])
										
										if (addcvg.equalsIgnoreCase(findTestData(excelPathTabInsurance).getValue(1, i))) {
											if(findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, i).length() > 0){
												if ((arrayAddCvg[(year - 1)]).length() > 0) {
													arrayMatch.add(WebUI.verifyMatch((arrayAddCvg[(year - 1)]).toUpperCase(), 'YES', false, FailureHandling.OPTIONAL))
													
													continue
												}
											}
										} else {
											continue
										}
								
//								'verify additional rate pada db = confins'
//								arrayMatch.add(WebUI.verifyEqual(Math.round(AddRate[(index)]), Math.round(addpremirate)))
								
							}
				}
			}
	
	'cek jika ada main premi rate'
	if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 69).length() > 0) {
		ArrayList<String> MainRate = GlobalVariable.MainPremiRate
				'get arraylist main premi rate dari DB'
				ArrayList<String> resultMainPremiRate = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2InsuranceMainPremiRateStoreDB'(
						sqlconnectionLOS, findTestData(excelPathCustomerPersonal).getValue(
								GlobalVariable.NumofColm, 13))
				
				'get arraylist main premi rate dari excel'
				def mainpremirateArray = findTestData(excelPathTabInsurance).getValue(
						GlobalVariable.NumofColm, 69).split(';', -1)
				
				'looping untuk verify mainpremirate db = confins'
				for (int mainpremirate = 0; mainpremirate < resultMainPremiRate.size(); mainpremirate++) {
					'verify main rate pada db = confins'
					arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(MainRate.get(mainpremirate)), Double.parseDouble(
							resultMainPremiRate[mainpremirate])))
				}
	}
}