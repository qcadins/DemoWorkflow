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

public class CustomerDataVerif {

	@Keyword
	public CustomerDataPersonal (Sql instance, String name, String idtype, String custmodel){
		String customerdata
		instance.eachRow(("select DISTINCT Cust_name AS HEADER, BIRTH_PLACE AS HEADER, MR_ID_TYPE_CODE AS HEADER, ID_EXPIRED_DT AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, MR_CUST_MODEL_CODE AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from cust a join cust_addr b on a.CUST_ID = b.CUST_ID JOIN cust_personal c on a.cust_ID = c.CUST_ID where a.cust_name = '"+ name +"' and MR_ID_TYPE_CODE = '"+ idtype +"' AND MR_CUST_MODEL_CODE = '"+ custmodel + "' AND MR_CUST_ADDR_TYPE_CODE = 'legal'"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public CustomerDataCompany (Sql instance, String name, String companytype){
		String customerdata
		instance.eachRow(("select DISTINCT Cust_name AS HEADER, TAX_ID_NO AS HEADER, MR_COMPANY_TYPE_CODE AS HEADER ,MR_CUST_MODEL_CODE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from cust a join cust_addr b on a.CUST_ID = b.CUST_ID JOIN CUST_COMPANY c on a.cust_ID = c.CUST_ID where a.cust_name = '"+ name +"' AND MR_COMPANY_TYPE_CODE = '"+ companytype +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal'"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public FamilyDataPersonal (Sql instance, String name, String idno){
		String customerdata
		instance.eachRow(("select Cust_name AS HEADER, BIRTH_PLACE AS HEADER, MR_ID_TYPE_CODE AS HEADER, ID_EXPIRED_DT AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, MR_CUST_MODEL_CODE AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, MR_NATIONALITY_CODE AS HEADER, UPPER(COUNTRY_NAME) AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from cust a join cust_addr b on a.CUST_ID = b.CUST_ID join cust_personal c on a.cust_ID = c.CUST_ID join REF_COUNTRY d on c.WNA_COUNTRY_CODE = d.COUNTRY_CODE where a.cust_name = '"+ name + "' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal'"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public CheckCustomerExisting (Sql instance, String appno){
		int checkresult
		instance.eachRow(("USE LOS SELECT COUNT(IS_EXISTING_CUST) FROM APP_CUST a JOIN APP b on a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno + "' and IS_CUSTOMER = 1"), {  row ->

			checkresult = (row[0])
		})
		return checkresult
	}
}
