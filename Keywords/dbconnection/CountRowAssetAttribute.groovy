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

public class CountRowAssetAttribute {
	@Keyword
	public countRowAssetAttribute (Sql instance, Sql instanceLOS, String assetcode,String POName){
		String countRowAssetAttributeResult, assetschmCode
		instanceLOS.eachRow(("select distinct compnt_value from prod_offering po WITH(NOLOCK) join prod_offering_h poHead on po.PROD_OFFERING_ID = poHead.PROD_OFFERING_ID join prod_offering_d c on c.PROD_OFFERING_H_ID = poHead.PROD_OFFERING_H_ID where prod_offering_name = '"+POName+"' and REF_PROD_COMPNT_CODE ='ASSETSCHM'"), { def row ->
			assetschmCode = row[0]
		})
		instance.eachRow(("select count(REF_ATTR_ID) from ASSET_MASTER am WITH(NOLOCK) join ASSET_ATTR attr on am.ASSET_TYPE_ID = attr.ASSET_TYPE_ID where am.asset_type_id= (select assetschmHead.asset_type_id from asset_schm_h assetschmHead join asset_schm_d assetschmDetail on assetschmHead.asset_schm_h_id = assetschmDetail.asset_schm_h_id join asset_master am on assetschmDetail.asset_master_id = am.asset_master_id where asset_schm_code = '"+assetschmCode+"' and am.is_active =1 and full_asset_code = '"+assetcode+"') and full_asset_code = '"+assetcode+"'"), {  row ->

			countRowAssetAttributeResult = (row[0])
		})
		return countRowAssetAttributeResult
	}
}
