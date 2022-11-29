package assetData

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

	//keyword count asset name
	@Keyword
	public countAssetName(Sql instanceLOS, Sql instanceFOU, String POName){
		Integer countAsset
		String assetschmCode
		instanceLOS.eachRow(("select distinct compnt_value from prod_offering po WITH(NOLOCK) join prod_offering_h poh WITH(NOLOCK) on po.PROD_OFFERING_ID = poh.PROD_OFFERING_ID join prod_offering_d pod WITH(NOLOCK) on pod.PROD_OFFERING_H_ID = poh.PROD_OFFERING_H_ID where prod_offering_name = '"+POName+"' and REF_PROD_COMPNT_CODE ='ASSETSCHM'"), { def row ->
			assetschmCode = row[0]
		})
		instanceFOU.eachRow(("SELECT count(*) FROM dbo.ASSET_MASTER am WITH ( NOLOCK ) JOIN dbo.ASSET_TYPE at WITH ( NOLOCK ) ON am.ASSET_TYPE_ID = at.ASSET_TYPE_ID JOIN dbo.ASSET_CATEGORY ac WITH ( NOLOCK ) ON am.ASSET_CATEGORY_ID = ac.ASSET_CATEGORY_ID JOIN dbo.ASSET_SCHM_D asDetail WITH ( NOLOCK ) ON am.ASSET_MASTER_ID = asDetail.ASSET_MASTER_ID JOIN dbo.ASSET_SCHM_H asHead WITH ( NOLOCK ) ON asDetail.ASSET_SCHM_H_ID = asHead.ASSET_SCHM_H_ID WHERE am.IS_ACTIVE = 1 AND am.ASSET_CATEGORY_ID IS NOT NULL AND am.IS_FINAL = 1 AND asHead.ASSET_SCHM_CODE = '"+assetschmCode+"' AND am.IS_ACTIVE = '1'"), { def row ->
			countAsset = row[0]
		})
		return countAsset
	}

	//keyword check personal relationship DDL
	@Keyword
	public checkPersonalRelationshipDDL(Sql instance){
		ArrayList<String> personalRelationDDL = new ArrayList<String>()
		instance.eachRow(("select descr from ref_master WITH (NOLOCK) where ref_master_type_code = 'cust_personal_relationship'"), { def row ->
			personalRelationDDL.add(row[0].toUpperCase())
		})
		return personalRelationDDL
	}

	//keyword check company relationship DDL
	@Keyword
	public checkCompanyRelationshipDDL(Sql instance){
		ArrayList<String> companyRelationDDL = new ArrayList<String>()
		instance.eachRow(("select descr from ref_master WITH(NOLOCK) where ref_master_type_code = 'cust_company_relationship'"), { def row ->
			companyRelationDDL.add(row[0].toUpperCase())
		})
		return companyRelationDDL
	}

	//keyword check asset usage DDL
	@Keyword
	public checkAssetUsageDDL(Sql instance){
		ArrayList<String> assetUsage = new ArrayList<String>()
		instance.eachRow(("select descr from REF_MASTER WITH(NOLOCK) where ref_master_type_code = 'asset_usage'"), { def row ->
			assetUsage.add(row[0].toUpperCase())
		})
		return assetUsage
	}

	//keyword check self owner personal
	@Keyword
	public checkSelfOwnerPersonal(){
		Boolean ismatch = false
		if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 41) == 'Yes' && WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Self Owner Checkbox'),'aria-checked')=="false"){
			ismatch = true
		}

		return ismatch
	}

	//keyword check self usage personal
	@Keyword
	public checkSelfUsagePersonal(){
		Boolean ismatch = false
		if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 37) == 'Yes' && WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Self Usage Checkbox'),'aria-checked')=="false"){
			ismatch = true
		}

		return ismatch
	}

	//keyword check self owner company
	@Keyword
	public checkSelfOwnerCompany(){
		Boolean ismatch = false
		if(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 41) == 'Yes' && WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Self Owner Checkbox'),'aria-checked')=="false"){
			ismatch = true
		}

		return ismatch
	}

	//keyword check self usage company
	@Keyword
	public checkSelfUsageCompany(){
		Boolean ismatch = false
		if(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(
		GlobalVariable.NumofColm, 37) == 'Yes' && WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/input_Self Usage Checkbox'),'aria-checked')=="false"){
			ismatch = true
		}


		return ismatch
	}
}