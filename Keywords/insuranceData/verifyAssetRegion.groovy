package insuranceData

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

public class verifyAssetRegion {

	//keyword asset region
	@Keyword
	public checkAssetRegionBasedOnRule(Sql instanceLOS, String appNo, Sql instanceFOU){

		String result, taxCityIssuer, locationCity, originalCity, oriOfficeCode

		instanceLOS.eachRow(("select TAX_CITY_ISSUER from app_asset aa with(nolock) join app a with(nolock) on aa.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
			taxCityIssuer = row[0]
		})
		
		instanceLOS.eachRow(("select LOCATION_CITY from APP_COLLATERAL_REGISTRATION acr join app_collateral ac on ac.app_collateral_id = acr.APP_COLLATERAL_ID join app a on a.app_id = ac.app_id where app_no = '"+appNo+"'"), { def row ->
			locationCity = row[0]
		})
		
		instanceLOS.eachRow(("select ori_office_code from app where app_no = '"+appNo+"'"), { def row ->
			oriOfficeCode = row[0]
		})
		
		instanceFOU.eachRow(("select city from ref_office where office_code = '"+oriOfficeCode+"'"), { def row ->
			originalCity = row[0]
		})
		
		String userDir = System.getProperty('user.dir')

		String filePath = userDir+GlobalVariable.AssetRegRulePath

		Integer startRow = -1

		startRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'InsRegion', "AppAsset.TaxCityIssuer == @val")+2

		def ruleAssetRegion = findTestData('DownloadRule/AssetRegionRule')

		int matchTaxCity = 0, matchLocCity = 0, matchOriCity = 0

		for(int i = startRow;i<=ruleAssetRegion.getRowNumbers();i++){

			if(ruleAssetRegion.getValue(1,i)!=taxCityIssuer && ruleAssetRegion.getValue(1,i)!="" && ruleAssetRegion.getValue(1,i)!="-"){
				matchTaxCity=0
			}
			if(ruleAssetRegion.getValue(1,i)==taxCityIssuer || (matchTaxCity==1 && ruleAssetRegion.getValue(1,i)=="") || ruleAssetRegion.getValue(1,i)=="-"){
				if(matchTaxCity==0){
					matchTaxCity=1
				}
				if(ruleAssetRegion.getValue(2,i)==locationCity || ruleAssetRegion.getValue(2,i)=="-"){
					matchLocCity = 1
				}
				else if(ruleAssetRegion.getValue(2,i)!=locationCity && ruleAssetRegion.getValue(2,i)!="" && ruleAssetRegion.getValue(2,i)!="-"){
					matchLocCity = 0
				}
				if(ruleAssetRegion.getValue(3,i)==originalCity || ruleAssetRegion.getValue(3,i)=="-"){
					matchOriCity = 1
				}
				else if(ruleAssetRegion.getValue(3,i)!=originalCity && ruleAssetRegion.getValue(3,i)!="" && ruleAssetRegion.getValue(3,i)!="-"){
					matchOriCity = 0
				}
				
				if(((ruleAssetRegion.getValue(2,i)=="" && matchLocCity==1) || matchLocCity==1) && ((ruleAssetRegion.getValue(3,i)=="" && matchOriCity==1) || matchOriCity==1)
				){
					result = ruleAssetRegion.getValue(4,i)
				}
			}
			else if((ruleAssetRegion.getValue(1,i)=="" && ruleAssetRegion.getValue(2,i)=="" && ruleAssetRegion.getValue(3,i)=="" && ruleAssetRegion.getValue(4,i)=="")||
			result!=null){

				break
			}
		}

		return result
	}
}
