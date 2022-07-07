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

ExcelData data = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule') 

for (def index : (13..data.getRowNumbers() - 1)) { 
	
	if(data.getValue(1, index) != 'PERSONAL'){
		break
	}
	
	GlobalVariable.countRowPersonal = (index-12)
			
}



for (def index : (3..data.getColumnNumbers() - 1)) {
	
	if(data.getValue(index, 12) == 'TaxIdNo'){
		break
	}
	
	GlobalVariable.countColmPersonal = (index-2)
			
}

dupcheck = false

dataCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')
def datacust = []		
		
		'check idNo'
		if(CustomKeywords.'dbconnection.DupCheckVerif.checkCustIdNo'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 18)) == '1'){
			
			datacust.add('Exact')
			
		}else{
			datacust.add('-')
		}
		
		ArrayList<String> CustName = CustomKeywords.'dbconnection.DupCheckVerif.checkCustName'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 9))
		
		'check custName'
		if(CustName.size() >= 1){
			
			datacust.add('Partial')
			
			for(i = 1; i <= CustName.size() ; i++){
					if((dataCustomer.getValue(GlobalVariable.NumofColm, 9).equalsIgnoreCase(CustName[i-1]))){
						
						datacust.set(datacust.size()-1, 'Exact')
						break
					}
			}
		}else{
			datacust.add('-')
		}
		
		'check birthdate'
		if(CustomKeywords.'dbconnection.DupCheckVerif.checkBirthDate'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 17)) == '1'){
			
			datacust.add('Exact')
			
		}else{
			datacust.add('-')
		}
		
		ArrayList<String> MotherMaidenName = CustomKeywords.'dbconnection.DupCheckVerif.checkMotherMaidenCust'(sqlconnection, dataCustomer.getValue(GlobalVariable.NumofColm, 20))
		
		'check custName'
		if(MotherMaidenName.size() >= 1){
			
			datacust.add('Partial')
			
			for(i = 1; i <= MotherMaidenName.size() ; i++){
					if((dataCustomer.getValue(GlobalVariable.NumofColm, 20).equalsIgnoreCase(MotherMaidenName[i-1]))){
						
						datacust.set(datacust.size()-1, 'Exact')
						break
					}
			}
		}else{
			datacust.add('-')
		}
		
		println(datacust)
		
		int a,b,c,d,e,f,g
		ArrayList<Integer> ScoreDupCheck = new ArrayList<Integer>()
		ScoreDupCheck.add(a)
		ScoreDupCheck.add(b)
		ScoreDupCheck.add(c)
		ScoreDupCheck.add(d)
		ScoreDupCheck.add(e)
		ScoreDupCheck.add(f)
		ScoreDupCheck.add(g)
		for(row = 13; row <= GlobalVariable.countRowPersonal + 12; row++){
			
			for(colm = 3; colm <= GlobalVariable.countColmPersonal + 2; colm++){
			
			println(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule').getValue(colm, row))
			println(datacust[colm-3])
			if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DuplicateCheckingRule').getValue(colm, row).equalsIgnoreCase(datacust[colm-3])){
				ScoreDupCheck[row-13]++
				println(true)
			
			}
			
		}
		}
		int maxVal = Collections.max(ScoreDupCheck)
		int idx = ScoreDupCheck.indexOf(maxVal)
		println(ScoreDupCheck)
		println(idx)
		
		String DupCheckCondition = data.getValue(2, idx +13)
		println(DupCheckCondition)