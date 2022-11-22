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


public class checkInterestType {

	@Keyword
	public checkInterest(Sql instance,String prodname){
		String interestType
		instance.eachRow(("select distinct compnt_value from prod p WITH(NOLOCK) join prod_h ph WITH(NOLOCK) on p.prod_id = ph.prod_id join prod_d pd WITH(NOLOCK) on ph.prod_h_id = pd.prod_h_id join PROD_OFFERING po on po.PROD_ID = p.prod_id where prod_offering_name = '"+prodname+"' and ref_prod_compnt_code = 'intrsttype'"), { def row ->
			interestType = (row[0])
		})
		return interestType
	}
}
