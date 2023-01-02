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

import groovy.sql.Sql
import internal.GlobalVariable

public class checkAssetValidation {
	//keyword check asset validation
	@Keyword
	public checkValidation(Sql instanceLOS, Sql instanceFOU, String poCode, String supplCode, String assetCode, String assetCondition, String manuYear, String tenor){
		HashMap<String, ArrayList> result = new HashMap<>()

		ArrayList<String> ManuYearVldt = new ArrayList<>()
		ArrayList<String> DPVldt = new ArrayList<>()

		String assetCategoryCode, assetSchmCode

		instanceLOS.eachRow(("select COMPNT_VALUE from prod_offering po with(nolock) join prod_offering_h poh with(nolock) on po.PROD_OFFERING_ID = poh.PROD_OFFERING_ID join prod_offering_d pod with(nolock) on pod.PROD_OFFERING_H_ID = poh.PROD_OFFERING_H_ID where prod_offering_code = '"+poCode+"' and poh.prod_stat = 'ACT' and REF_PROD_COMPNT_CODE = 'ASSETSCHM'"), { def row ->
			assetSchmCode = row[0]
		})

		instanceFOU.eachRow(("SELECT ASSET_CATEGORY_CODE FROM ASSET_MASTER am with(nolock) join asset_category ac with(nolock) on ac.ASSET_CATEGORY_ID = am.ASSET_CATEGORY_ID where FULL_ASSET_CODE='"+assetCode+"' and am.asset_type_id = (SELECT asset_type_id from asset_schm_h ash with(nolock) where asset_schm_code = '"+assetSchmCode+"')"), { def row ->
			assetCategoryCode = row[0]
		})

		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.AssetValRulePath

		Integer startRow = 15

		def ruleDP = findTestData('DownloadRule/AssetValidationDPRule')

		def ruleManuYear = findTestData('DownloadRule/AssetValidationManuYearRule')

		int matchSupplCode = 0, matchAssetCode = 0

		for(int i = startRow+1;i<=ruleManuYear.getRowNumbers();i++){
			if(ruleManuYear.getValue(1,i)!=supplCode && ruleManuYear.getValue(1,i)!="" && ruleManuYear.getValue(1,i)!="-"){
				matchSupplCode=0
			}
			if(ruleManuYear.getValue(1,i)==supplCode || (matchSupplCode==1 && ruleManuYear.getValue(1,i)=="") || ruleManuYear.getValue(1,i)=="-"){
				if(matchSupplCode==0){
					matchSupplCode=1
				}
				if(ruleManuYear.getValue(2,i)==assetCode || ruleManuYear.getValue(2,i)=="-"){
					matchAssetCode = 1
				}
				else if(ruleManuYear.getValue(2,i)!=assetCode && ruleManuYear.getValue(2,i)!="" && ruleManuYear.getValue(2,i)!="-"){
					matchAssetCode = 0
				}
				if(((ruleManuYear.getValue(2,i)=="" && matchAssetCode==1) || matchAssetCode==1) ){
					ManuYearVldt.add(ruleManuYear.getValue(3,i))
					ManuYearVldt.add(ruleManuYear.getValue(4,i))
				}
			}
			else if((ruleManuYear.getValue(1,i)=="" && ruleManuYear.getValue(2,i)=="" && ruleManuYear.getValue(3,i)=="" && ruleManuYear.getValue(4,i)=="")
			|| matchSupplCode==0){
				break
			}
		}

		int matchAssetCondition = 0, matchAssetCategory = 0

		for(int i = 16;i<=ruleDP.getRowNumbers();i++){
	
			if(ruleDP.getValue(1,i)!=assetCondition && ruleDP.getValue(1,i)!="" && ruleDP.getValue(1,i)!="-"){
				matchAssetCondition=0
		
			}
			if(ruleDP.getValue(1,i)==assetCondition || (matchAssetCondition==1 && ruleDP.getValue(1,i)=="") || ruleDP.getValue(1,i)=="-"){
				if(matchAssetCondition==0){
					matchAssetCondition=1
			
				}
				if(ruleDP.getValue(6,i)==assetCategoryCode || ruleDP.getValue(6,i)=="-"){
					matchAssetCategory = 1
			
				}
				else if(ruleDP.getValue(6,i)!=assetCategoryCode && ruleDP.getValue(6,i)!="" && ruleDP.getValue(6,i)!="-"){
					matchAssetCategory = 0
		
				}
				if(((ruleDP.getValue(6,i)=="" && matchAssetCategory==1) || matchAssetCategory==1) 
					&& Long.parseLong(ruleDP.getValue(2,i))<Long.parseLong(manuYear) && Long.parseLong(ruleDP.getValue(3,i))>=Long.parseLong(manuYear)
					&& Long.parseLong(ruleDP.getValue(4,i))<Long.parseLong(tenor) && Long.parseLong(ruleDP.getValue(5,i))>=Long.parseLong(tenor)){
					DPVldt.add(ruleDP.getValue(7,i))
					DPVldt.add(ruleDP.getValue(8,i))
					DPVldt.add(ruleDP.getValue(9,i))
					DPVldt.add(ruleDP.getValue(10,i))
			
					break
				}
			}
			else if((ruleDP.getValue(1,i)=="" && ruleDP.getValue(2,i)=="" && ruleDP.getValue(3,i)=="" && ruleDP.getValue(4,i)=="" && ruleDP.getValue(5,i)=="" && ruleDP.getValue(6,i)=="")){
	
				break
			}
		}
		result.put("ManuYearVldt",ManuYearVldt)
		result.put("DPVldt",DPVldt)
		return result
	}
}
