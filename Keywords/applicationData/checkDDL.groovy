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

import groovy.sql.Sql
import internal.GlobalVariable

public class checkDDL {

	@Keyword
	public checkDDLApplicationSource(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("select UPPER(app_src_name) from REF_APP_SRC where is_active = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkDDLInstallmentScheme(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(pism.MR_INST_SCHM_NAME) FROM PROD_OFFERING_D pod CROSS APPLY STRING_SPLIT(pod.COMPNT_VALUE,';') join PROD_INST_SCHM_MAP pism on pism.MR_INST_SCHM_CODE = value join prod_offering_h poh on poh.PROD_OFFERING_H_ID = pod.PROD_OFFERING_H_ID join prod_offering po on po.PROD_OFFERING_ID = poh.PROD_OFFERING_ID where REF_PROD_COMPNT_CODE = 'INST_SCHM' and prod_offering_name = 'CF4W MRA' and prod_stat = 'ACT' group by MR_INST_SCHM_NAME"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkDDLWOP(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("select UPPER(DESCR) from ref_master where ref_master_type_code = 'WOP' AND IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}
}
