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
import groovy.sql.Sql as Sql
import internal.GlobalVariable

public class checkStep {

	@Keyword
	public checkAppCurrStep(Sql instanceLOS, String appNo){
		String appStep
		instanceLOS.eachRow(("select app_curr_step from app with(nolock) where app_no = '"+appNo+"'"), { def row ->
			appStep = row[0]
		})
		return appStep
	}

	@Keyword
	public checkCustCheckStep(Sql instanceLOS, String appNo){
		String custCheckStep
		instanceLOS.eachRow(("select cust_checking_step from app with(nolock) where app_no = '"+appNo+"'"), { def row ->
			custCheckStep = row[0]
		})
		return custCheckStep
	}
	
	@Keyword
	public checkLastStep(Sql instanceLOS, String appno){
		String applaststep = ""
		instanceLOS.eachRow(("select app_last_step from APP where app_no = '"+appno+"'"), {  row ->
			applaststep=row[0]
		})
		return applaststep
	}
}
