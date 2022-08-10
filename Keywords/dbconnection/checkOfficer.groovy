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
import groovy.sql.Sql as Sql
import internal.GlobalVariable


public class checkOfficer {

	@Keyword
	public countOfficerLookup(Sql instance, String officeLoc){
		Integer countData
		instance.eachRow(("select count(*) from ref_user_role c join ref_job_title d on c.REF_JOB_TITLE_ID = d.REF_JOB_TITLE_ID join ref_office e on c.REF_OFFICE_ID = e.REF_OFFICE_ID where job_title_code = 'SALES_PERSON' and office_name = '"+officeLoc+"' and c.is_active=1 "), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public checkSPV(Sql instance, String username){
		String spvname
		instance.eachRow(("select EMP_NAME from REF_USER a join REF_EMP b on a.REF_EMP_ID = b.REF_EMP_ID where REF_USER_ID = (select top 1 SPV_ID from REF_EMP a JOIN REF_USER b on a.REF_EMP_ID = b.REF_EMP_ID JOIN REF_USER_ROLE c on b.REF_USER_ID = c.REF_USER_ID JOIN REF_ROLE d on c.REF_ROLE_ID = d.REF_ROLE_ID where USERNAME = '"+username+"' and c.IS_ACTIVE = 1)"), { def row ->
			spvname = (row[0])
		})
		return spvname
	}
}
