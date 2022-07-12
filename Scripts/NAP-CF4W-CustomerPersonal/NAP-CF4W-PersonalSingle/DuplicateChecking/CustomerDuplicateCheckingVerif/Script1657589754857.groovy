import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.ExcelData
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 8)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'variable data file matrix rule dupcheck'
ExcelData MatrixRuleDupcheck = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule') 

'looping untuk menghitung row customer personal dalam rule'
for (def index : (13..MatrixRuleDupcheck.getRowNumbers() - 1)) { 
	
	if(MatrixRuleDupcheck.getValue(1, index) != 'PERSONAL'){
		break
	}
	
	'store value row personal (-12 karena row pertama dimulai dari row 13)'
	GlobalVariable.countRowPersonal = (index-12)
			
}



'looping untuk menghitung colm syarat yang digunakan untuk dupcheck'
for (def index : (3..MatrixRuleDupcheck.getColumnNumbers() - 1)) {
	
	if(MatrixRuleDupcheck.getValue(index, 12) == 'TaxIdNo'){
		break
	}
	
	'store value colm syarat (-2 karena colm pertama dimulai dari colm 3)'
	GlobalVariable.countColmPersonal = (index-2)
			
}

dupcheck = false

'variable data file tab customer data'
dataCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')
'array untuk menampung hasil dupcheck Exact / Partial / -'
def datacust = []		
		
		'check idNo'
		if(CustomKeywords.'dbconnection.DupCheckVerif.checkCustIdNo'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 18)) == '1'){
			
			'add "Exact" kedalam array datacust'
			datacust.add('Exact')
			
		}else{
		
		'add "-" kedalam array datacust'
			datacust.add('-')
		}
		
		'Array String untuk menyimpan Customer name yang terkena dupcheck'
		ArrayList<String> CustName = CustomKeywords.'dbconnection.DupCheckVerif.checkCustName'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 9))
		
		'check custName'
		if(CustName.size() >= 1){
			
			'add "Partial" kedalam array datacust'
			datacust.add('Partial')
			
			'looping untuk pemeriksaan kecocokan dupcheck sesuai rule'
			for(i = 1; i <= CustName.size() ; i++){
					if((dataCustomer.getValue(GlobalVariable.NumofColm, 9).equalsIgnoreCase(CustName[i-1]))){
						
						'add "Exact" kedalam array datacust'
						datacust.set(datacust.size()-1, 'Exact')
						break
					}
			}
		}else{
		'add "-" kedalam array datacust'
			datacust.add('-')
		}
		
		'check birthdate'
		if(CustomKeywords.'dbconnection.DupCheckVerif.checkBirthDate'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 17)) == '1'){
			
			'add "Exact" kedalam array datacust'
			datacust.add('Exact')
			
		}else{
		'add "-" kedalam array datacust'
			datacust.add('-')
		}
		
		'Array untuk menyimpan mother maiden name'
		ArrayList<String> MotherMaidenName = CustomKeywords.'dbconnection.DupCheckVerif.checkMotherMaidenCust'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 20))
		
		'check jika mother maiden name >= 1'
		if(MotherMaidenName.size() >= 1){
			
			'add "Partial" kedalam array datacust'
			datacust.add('Partial')
			
			'Looping untuk pemeriksaan kecocokan mother maiden name yang terkena dupcheck sesuai rule'
			for(i = 1; i <= MotherMaidenName.size() ; i++){
					if((dataCustomer.getValue(GlobalVariable.NumofColm, 20).equalsIgnoreCase(MotherMaidenName[i-1]))){
						
						'add "Exact" kedalam array datacust'
						datacust.set(datacust.size()-1, 'Exact')
						break
					}
			}
		}else{
		'add "=" kedalam array datacust'
			datacust.add('-')
		}
		
		println(datacust)
		
		'declare variable untuk menampun poin dupcheck per rule'
		int Score1 , Score2 , Score3 , Score4 , Score5 , Score6 , Score7
		'array list untuk menyimpan score dupcheck per poin'
		ArrayList<Integer> ScoreDupCheck = new ArrayList<Integer>()
		
		'add score kedalam array ScoreDupcheck'
		ScoreDupCheck.add(Score1)
		ScoreDupCheck.add(Score2)
		ScoreDupCheck.add(Score3)
		ScoreDupCheck.add(Score4)
		ScoreDupCheck.add(Score5)
		ScoreDupCheck.add(Score6)
		ScoreDupCheck.add(Score7)
		
		'looping row untuk pemeriksaan kecocokan dan perhitungan scoredupcheck per rule dimulai dari row 13 karena sesuai rule'
		for(row = 13; row <= GlobalVariable.countRowPersonal + 12; row++){
			
			'looping colm untuk pemeriksaan kecocokan dan perhitungan scoredupcheck per rule dimulai dari row 3 karena sesuai rule'
			for(colm = 3; colm <= GlobalVariable.countColmPersonal + 2; colm++){
			
			println(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule').getValue(colm, row))
			println(datacust[colm-3])
			
			
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule').getValue(colm, row).equalsIgnoreCase(datacust[colm-3])){
				'+1 poin untuk masing masing kecocokan'
				ScoreDupCheck[row-13]++
			
			}
			
		}
		}
		
		'variable untuk mendapatkan nilai terbesar dari arraylist'
		int maxVal = Collections.max(ScoreDupCheck)
		
		'variable untuk menentukan index keberapa nilai terbesarnya berada'
		int idx = ScoreDupCheck.indexOf(maxVal)
		println(ScoreDupCheck)
		println(idx)
		
		'variable untuk menyimpan hasil penentuan jenis dupcheck "Lock" / "Review", +13 karena table pada rule dimulai dari row 13'
		String DupCheckCondition = MatrixRuleDupcheck.getValue(2, idx +13)
		println(DupCheckCondition)