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
	public checkDupcheckRulePersonal1 (Sql instance, String idno, String idtype, String Mothername){
		String Result1
		instance.eachRow(("SELECT count(a.CUST_ID) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND MR_ID_TYPE_CODE = '" + idtype + "' AND MOTHER_MAIDEN_NAME like '%" + Mothername + "%'"), { row ->

			Result1 = (row[0])
		})
		return Result1
	}

	@Keyword
	public checkDupcheckRulePersonal2 (Sql instance, String idno, String idtype, String custname){
		String Result2
		instance.eachRow(("SELECT count(CUST_ID) FROM CUST a WITH (NOLOCK) WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND MR_ID_TYPE_CODE = '" + idtype + "' AND CUST_NAME = '" + custname + "'"), { row ->

			Result2 = (row[0])
		})
		return Result2
	}

	@Keyword
	public checkDupcheckRulePersonal3 (Sql instance, String idno, String idtype, String birthdate){
		String Result3
		instance.eachRow(("SELECT count(a.CUST_ID) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND MR_ID_TYPE_CODE = '" + idtype + "' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "'"), { row ->

			Result3 = (row[0])
		})
		return Result3
	}

	@Keyword
	public checkDupcheckRulePersonal4 (Sql instance, String custname){
		String Result4
		instance.eachRow(("SELECT COUNT(DISTINCT CUST_NAME) FROM CUST WITH (NOLOCK) WHERE CUST_NAME LIKE '%" + custname + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), { row ->

			Result4 = (row[0])
		})
		return Result4
	}

	@Keyword
	public checkDupcheckRulePersonal5 (Sql instance, String idno, String idtype, String custname){
		String Result5
		instance.eachRow(("SELECT count(CUST_ID) FROM CUST a WITH (NOLOCK) WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND MR_ID_TYPE_CODE = '" + idtype + "' AND CUST_NAME like '%" + custname + "%'"), { row ->

			Result5 = (row[0])
		})
		return Result5
	}

	@Keyword
	public checkDupcheckRulePersonal6 (Sql instance, String custname, String mothername){
		String Result6
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE CUST_NAME LIKE '%" + custname + "%' AND MOTHER_MAIDEN_NAME LIKE '%" + mothername + "%'"), { row ->

			Result6 = (row[0])
		})
		return Result6
	}

	@Keyword
	public checkDupcheckRulePersonal7 (Sql instance, String custname, String birthdate){
		String Result7
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b ON a.CUST_ID = b.CUST_ID WHERE CUST_NAME LIKE '%" + custname + "%' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "'"), { row ->

			Result7 = (row[0])
		})
		return Result7
	}

	@Keyword
	public checkDupcheckRuleCompany1 (Sql instance, String custname, String taxidno){
		String Result8
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_COMPANY b ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME LIKE '%" + custname + "%' AND TAX_ID_NO = '" + taxidno + "'"), { row ->

			Result8 = (row[0])
		})
		return Result8
	}

	@Keyword
	public checkDupcheckRuleCompany2 (Sql instance, String custname){
		String Result9
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_COMPANY b ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME = '" + custname + "'"), { row ->

			Result9 = (row[0])
		})
		return Result9
	}
}

