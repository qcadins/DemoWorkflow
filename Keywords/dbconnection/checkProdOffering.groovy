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

public class checkProdOffering {

	@Keyword
	public countProdOffering(Sql instance, String officeLogin){
		Integer countData
		instance.eachRow(("SELECT count(*) FROM dbo.V_REF_OFFICE ro WITH ( NOLOCK ) JOIN dbo.PROD_OFFERING_BRANCH_MBR pobm WITH ( NOLOCK ) ON ro.OFFICE_CODE = pobm.OFFICE_CODE INNER JOIN dbo.PROD_OFFERING po WITH ( NOLOCK ) ON pobm.PROD_OFFERING_H_ID = po.CURRENT_PROD_OFFERING_H_ID INNER JOIN dbo.PROD_OFFERING_H poh WITH ( NOLOCK ) ON poh.PROD_OFFERING_ID = po.PROD_OFFERING_ID INNER JOIN dbo.PROD_OFFERING_D pod WITH ( NOLOCK ) ON pod.PROD_OFFERING_H_ID = poh.PROD_OFFERING_H_ID INNER JOIN dbo.V_REF_LOB rlob WITH ( NOLOCK ) ON rlob.LOB_CODE = pod.COMPNT_VALUE WHERE pod.REF_PROD_COMPNT_CODE = 'LOB' AND ro.IS_ACTIVE = 1 AND poh.PROD_STAT = 'ACT' AND ro.OFFICE_NAME = '"+officeLogin+"' AND rlob.BIZ_TMPLT_CODE = 'CF4W'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
}
