package dbConnection

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.sql.Sql
import internal.GlobalVariable

public class checkCustomer {
	@Keyword
	public checkCustomerModelPersonal(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND IS_ACTIVE = 1 AND MAPPING_CODE = 'PERSONAL'"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkCustomerModelCompany(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND IS_ACTIVE = 1 AND MAPPING_CODE = 'COMPANY'"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}
	
	@Keyword
	public checkCustomerNegativeLOS(Sql instance, String name){
		int value
		instance.eachRow(("SELECT COUNT(CUST_NAME) FROM APP_NEGATIVE_CHECK_CUST WHERE CUST_NAME = '"+ name +"'"), { def row ->
			value = (row[0])
		})
		return value
	}
	
	@Keyword
	public checkCustomerNegativeFOU(Sql instance, String name){
		int value
		instance.eachRow(("SELECT COUNT(CUST_NAME) FROM NEGATIVE_CUST WHERE CUST_NAME = '"+ name +"'"), { def row ->
			value = (row[0])
		})
		return value
	}
	
	@Keyword
	public checkCustomerNegativeGenSet(Sql instance){
		int value
		instance.eachRow(("SELECT * FROM GENERAL_SETTING_LOS WHERE GS_CODE = 'NEGCUSTALLOWNAP'"), { def row ->
			value = (row[0])
		})
		return value
	}
}