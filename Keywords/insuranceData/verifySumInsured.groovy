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

public class verifySumInsured {


	public verifySumInsuredMainCov(Sql instanceLOS, Sql instanceFOU, String appNo, String inscoBranchName ){

		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> yearNo = new ArrayList<>()

		ArrayList<String> sumInsuredPctg = new ArrayList<>()

		String userDir = System.getProperty('user.dir')

		String filePathAssetCat = userDir+GlobalVariable.MainCovRulePath

		String appId, assetCode, assetCondition, assetCategoryCode, inscoHOCode, insAssetCategory

		instanceLOS.eachRow(("select app_id from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
		})

		instanceLOS.eachRow(("select full_asset_code, mr_asset_condition_code from app_asset where app_id = '"+appId+"'"), { def row ->
			assetCode = row[0]
			assetCondition = row[1]
		})

		instanceLOS.eachRow(("select asset_category_code from ASSET_MASTER_LOS where FULL_ASSET_CODE = '"+assetCode+"'"), { def row ->

			assetCategoryCode = row[0]
		})

		instanceFOU.eachRow(("SELECT VENDOR_CODE FROM VENDOR WHERE VENDOR_ID = (select VENDOR_PARENT_ID from vendor where mr_vendor_category_code IN ('ASSET_INSCO_BRANCH') AND VENDOR_NAME ='"+inscoBranchName+"')"), { def row ->

			inscoHOCode = row[0]
		})

		Integer inscoHORow = -1

		inscoHORow = (new customizeKeyword.getRow()).getExcelRow(filePathAssetCat, 'AssetCategory', inscoHOCode)+1

		def ruleAssetCategory = findTestData('DownloadRule/InsAssetCategory')

		int indexAC = -1

		for(int i=inscoHORow;;i++){

			if(ruleAssetCategory.getValue(1, i)==inscoHOCode || ruleAssetCategory.getValue(1, i)==""){

				if(ruleAssetCategory.getValue(3, i)!="-" && ruleAssetCategory.getValue(3, i)==assetCategoryCode){
					insAssetCategory = ruleAssetCategory.getValue(4, i)

					indexAC=i

					break
				}
				else if(ruleAssetCategory.getValue(3, i)=="-"){
					insAssetCategory = ruleAssetCategory.getValue(4, i)

					indexAC=i

					break
				}
			}
			else if((ruleAssetCategory.getValue(1, i)!=inscoHOCode && ruleAssetCategory.getValue(1, i)!="")
			|| (ruleAssetCategory.getValue(1, i)=="" && ruleAssetCategory.getValue(2, i)=="")){

				break
			}
		}


		inscoHORow = (new customizeKeyword.getRow()).getExcelRow(filePathAssetCat, 'SumInsured', inscoHOCode)+1

		def ruleSumInsuredRate = findTestData('DownloadRule/InsuranceSumInsuredRule')

		int match = 0
		for(int i=inscoHORow;i<=ruleSumInsuredRate.getRowNumbers();i++){
			if(ruleSumInsuredRate.getValue(1,i)!=inscoHOCode && ruleSumInsuredRate.getValue(1,i)!=""){
				match=0
			}
			if(ruleSumInsuredRate.getValue(1,i)==inscoHOCode || (match==1 && ruleSumInsuredRate.getValue(1,i)=="")){
				if(match==0){
					match = 1
				}
				if(ruleSumInsuredRate.getValue(2,i)==insAssetCategory && ruleSumInsuredRate.getValue(3,i)==assetCondition){
					yearNo.add(ruleSumInsuredRate.getValue(4,i))
					sumInsuredPctg.add(ruleSumInsuredRate.getValue(5,i))
				}
			}
			else if((ruleSumInsuredRate.getValue(1,i)=="" && ruleSumInsuredRate.getValue(2,i)=="" && ruleSumInsuredRate.getValue(3,i)=="")
			|| match==0){
				break
			}
		}
		result.put("Year",yearNo)
		result.put("Pctg",sumInsuredPctg)
		return result
	}
}
