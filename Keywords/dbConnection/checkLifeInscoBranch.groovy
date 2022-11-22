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

public class checkLifeInscoBranch {

	@Keyword
	public checkDDLLifeInscoBranch(Sql instance, String officeName){
		ArrayList<String> lifeInscoBranch = new ArrayList<String>()
		instance.eachRow(("SELECT VENDOR_NAME FROM VENDOR v WITH(NOLOCK) JOIN VENDOR_OFFICE_MBR vom WITH(NOLOCK) ON v.VENDOR_ID = vom.VENDOR_ID JOIN REF_OFFICE ro WITH(NOLOCK) ON vom.REF_OFFICE_ID = ro.REF_OFFICE_ID AND MR_VENDOR_CATEGORY_CODE = 'LIFE_INSCO_BRANCH' AND v.IS_ACTIVE = 1 AND OFFICE_NAME = '"+officeName+"' ORDER BY VENDOR_NAME"), { def row ->
			lifeInscoBranch.add(row[0].toUpperCase())
		})
		return lifeInscoBranch
	}

	@Keyword
	public countDDLLifeInscoBranch(Sql instance, String officeName){
		Integer countData
		instance.eachRow(("SELECT count(*) VENDOR_NAME FROM VENDOR v WITH(NOLOCK) JOIN VENDOR_OFFICE_MBR vOffice ON v.VENDOR_ID = vOffice.VENDOR_ID JOIN REF_OFFICE office ON vOffice.REF_OFFICE_ID = office.REF_OFFICE_ID AND MR_VENDOR_CATEGORY_CODE = 'LIFE_INSCO_BRANCH' AND v.IS_ACTIVE = 1 AND OFFICE_NAME = '"+officeName+"' ORDER BY VENDOR_NAME"), { def row ->
			countData = (row[0])
		})
		return countData
	}
}
