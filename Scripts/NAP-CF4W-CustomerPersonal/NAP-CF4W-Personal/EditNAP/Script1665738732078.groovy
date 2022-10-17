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
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

String appNo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 8)

String appStep = CustomKeywords.'editNAP.checkStep.checkAppCurrStep'(sqlConnectionLOS, appNo)

String custStep = CustomKeywords.'editNAP.checkStep.checkCustCheckStep'(sqlConnectionLOS, appNo)

'Write to excel Appno'
CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
	12, GlobalVariable.NumofColm - 1, appNo)

if(appStep=="CUST"){
	
}
else if(appStep=="FAM"){
	
}
else if(appStep=="GUAR"){

}
else if(appStep=="NAPD"||appStep=="REF"||appStep=="APP"||appStep=="ASSET"||appStep=="INS"||appStep=="LFI"
	||appStep=="FIN"||appStep=="TC"){
	if(appStep=="NAPD"||appStep=="REF"){
	
	}
	else if(appStep=="APP"){
	
	}
	else if(appStep=="ASSET"){
	
	}
	else if(appStep=="INS"){
	
	}
	else if(appStep=="LFI"){
	
	}
	else if(appStep=="FIN"){
	
	}
	else if(appStep=="TC"){
	
	}
}
else if(appStep=="UPL_DOC"){

}
else if(appStep=="COM"){

}
else if(appStep=="RSV"){

}
else if(appStep=="SRV" && custStep=="CDA_REQ"){

}

public inputAppNo(){
	'input Appno'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'),
		findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 13))
	
	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))
	
	'click icon pensil untuk select'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))
	
}

