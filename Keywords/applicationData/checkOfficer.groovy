package applicationData

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
		instance.eachRow(("select count(*) from ref_user_role rur WITH(NOLOCK) join ref_job_title rjt WITH(NOLOCK) on rur.REF_JOB_TITLE_ID = rjt.REF_JOB_TITLE_ID join ref_office ro WITH(NOLOCK) on  rur.REF_OFFICE_ID = rur.REF_OFFICE_ID where job_title_code = 'SALES_PERSON' and office_name = '"+officeLoc+"' and rur.is_active=1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public checkSPV(Sql instance, String username){
		String spvname
		instance.eachRow(("select EMP_NAME from REF_USER refuser WITH(NOLOCK) join REF_EMP emp on refuser.REF_EMP_ID = emp.REF_EMP_ID where REF_USER_ID = (select top 1 SPV_ID from REF_EMP emp JOIN REF_USER refuser on emp.REF_EMP_ID = refuser.REF_EMP_ID JOIN REF_USER_ROLE userrole on refuser.REF_USER_ID = userrole.REF_USER_ID JOIN REF_ROLE d on userrole.REF_ROLE_ID = d.REF_ROLE_ID where USERNAME = '"+username+"' and userrole.IS_ACTIVE = 1)"), { def row ->
			spvname = (row[0])
		})
		return spvname
	}

	@Keyword
	public checkOfficerName(Sql instance, String username){
		String officerName
		instance.eachRow(("select emp_name from ref_user refuser WITH(NOLOCK) join ref_emp refemp on refuser.REF_EMP_ID = refemp.REF_EMP_ID where username = '"+username+"' and refuser.IS_ACTIVE=1 and refemp.IS_ACTIVE=1"), { def row ->
			officerName = (row[0])
		})
		return officerName
	}
}
