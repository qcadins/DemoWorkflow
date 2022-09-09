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

public class checkAssetData {
	
	@Keyword
	public countAssetName(Sql instanceLOS, Sql instanceFOU, String POName){
		Integer countAsset
		String assetschmCode
		instanceLOS.eachRow(("select distinct compnt_value from prod_offering a join prod_offering_h b on a.PROD_OFFERING_ID = b.PROD_OFFERING_ID join prod_offering_d c on c.PROD_OFFERING_H_ID = b.PROD_OFFERING_H_ID where prod_offering_name = '"+POName+"' and REF_PROD_COMPNT_CODE ='ASSETSCHM'"), { def row ->
			assetschmCode = row[0]
		})
		instanceFOU.eachRow(("select distinct count(*) full_asset_code from asset_schm_h a join asset_schm_d b on a.asset_schm_h_id = b.asset_schm_h_id join asset_master c on b.asset_master_id = c.asset_master_id where asset_schm_code = '"+assetschmCode+"' and c.is_active =1"), { def row ->
			countAsset = row[0]
		})
		return countAsset
	}
	
	@Keyword
	public checkPersonalRelationshipDDL(Sql instance){
		ArrayList<String> personalRelationDDL = new ArrayList<String>()
		instance.eachRow(("select descr from ref_master where ref_master_type_code = 'cust_personal_relationship'"), { def row ->
			personalRelationDDL.add(row[0].toUpperCase())
		})
		return personalRelationDDL
	}
	
	@Keyword
	public checkCompanyRelationshipDDL(Sql instance){
		ArrayList<String> companyRelationDDL = new ArrayList<String>()
		instance.eachRow(("select descr from ref_master where ref_master_type_code = 'cust_company_relationship'"), { def row ->
			companyRelationDDL.add(row[0].toUpperCase())
		})
		return companyRelationDDL
	}
	
	@Keyword
	public checkAssetUsageDDL(Sql instance){
		ArrayList<String> assetUsage = new ArrayList<String>()
		instance.eachRow(("select descr from REF_MASTER where ref_master_type_code = 'asset_usage'"), { def row ->
			assetUsage.add(row[0].toUpperCase())
		})
		return assetUsage
	}
}