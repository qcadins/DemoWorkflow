package dbconnection

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

public class DupCheckVerif {

	@Keyword
	public checkDupcheck (Sql instance, String appNo){
		String dupcheckcount
		instance.eachRow(("select count(BUSINESS_KEY_) from [dbo].[ACT_HI_PROCINST] AHP join [dbo].[ACT_HI_VARINST] AHV on AHP.PROC_INST_ID_ = AHV.PROC_INST_ID_ where BUSINESS_KEY_ = '" + appNo + "' and AHV.PROC_DEF_KEY_ = 'WF_DUP_CHECK_MD_CF4W' and AHV.TEXT_ = 'true'"), {  row ->

			dupcheckcount = (row[0])
		})
		return dupcheckcount
	}

	@Keyword
	public checkCustIdNo (Sql instance, String IdNo){
		String custIdNoCount
		instance.eachRow(("SELECT COUNT(ID_NO) FROM CUST WHERE ID_NO = '" + IdNo + "' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), {  row ->

			custIdNoCount = (row[0])
		})
		return custIdNoCount
	}

	@Keyword
	public checkCustName (Sql instance, String Name){
		ArrayList<String> custName = new ArrayList<String>()
		instance.eachRow(("SELECT DISTINCT CUST_NAME FROM CUST WHERE CUST_NAME LIKE '%" + Name + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), {  row ->

			custName.add(row[0])
		})
		return custName
	}

	@Keyword
	public checkBirthDate (Sql instance, String BirthDate){
		String custBirthDateCount
		instance.eachRow(("SELECT COUNT(DISTINCT FORMAT(BIRTH_DT, 'MM/dd/yyyy')) FROM CUST a JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE BIRTH_DT = '" + BirthDate + "' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), {  row ->

			custBirthDateCount = (row[0])
		})
		return custBirthDateCount
	}

	@Keyword
	public checkMotherMaidenCust (Sql instance, String MotherMaidenName){
		ArrayList<String> motherMaidenName = new ArrayList<String>()
		instance.eachRow(("SELECT DISTINCT MOTHER_MAIDEN_NAME FROM CUST a JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE MOTHER_MAIDEN_NAME like '%" + MotherMaidenName + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), {  row ->

			motherMaidenName.add(row[0])
		})
		return motherMaidenName
	}
}

