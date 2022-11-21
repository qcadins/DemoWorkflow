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


	@Keyword
	public checkAssetRegionBasedOnRule(Sql instanceLOS, String appNo){

		String result, taxCityIssuer

		instanceLOS.eachRow(("select TAX_CITY_ISSUER from app_asset aa with(nolock) join app a with(nolock) on aa.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
			taxCityIssuer = row[0]
		})

		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.AssetRegRulePath

		Integer startRow = -1
		startRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'InsRegion', "AppAsset.TaxCityIssuer == @val")+2

		def ruleAssetRegion = findTestData('DownloadRule/AssetRegionRule')

		int matchLOB = 0, matchCustType = 0, matchCustModel = 0, matchNationalityCode = 0, matchMaritalStat = 0

		for(int i = startRow;i<=ruleAssetRegion.getRowNumbers();i++){
			if(ruleAssetRegion.getValue(1,i).equalsIgnoreCase(taxCityIssuer) || ruleAssetRegion.getValue(1,i)=="-"){
				result = ruleAssetRegion.getValue(4,i)
			}
			if((ruleAssetRegion.getValue(1,i)=="" && ruleAssetRegion.getValue(2,i)=="" && ruleAssetRegion.getValue(3,i)=="" && ruleAssetRegion.getValue(4,i)=="")||
			result!=null){
				break
			}
		}
		return result
	}
}
