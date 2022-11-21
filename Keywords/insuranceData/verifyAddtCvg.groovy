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

public class verifyAddtCvg {

	@Keyword
	public verifyAddtPremiRate(Sql instanceLOS, Sql instanceFOU, String appNo, String inscoBranchName, String region, String covAmt, String mainCvgType, String yearNo){
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> sumInsuredAmt = new ArrayList<>()
		ArrayList<String> addtPremiRate = new ArrayList<>()

		ArrayList<String> addtCvgType = new ArrayList<>()

		ArrayList<String> addtCvg = new ArrayList<>()

		String userDir = System.getProperty('user.dir')

		String filePathAssetCat = userDir+GlobalVariable.MainCovRulePath

		String filePath = userDir+GlobalVariable.AddtCovRulePath

		String appId, assetCode, assetCondition, assetCategoryCode, inscoHOCode, insAssetCategory, inscoBranchCode, year

		instanceLOS.eachRow(("select app_id from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
		})

		instanceLOS.eachRow(("select full_asset_code from app_asset where app_id = '"+appId+"'"), { def row ->
			assetCode = row[0]
		})

		instanceLOS.eachRow(("select asset_category_code from ASSET_MASTER_LOS where FULL_ASSET_CODE = '"+assetCode+"'"), { def row ->

			assetCategoryCode = row[0]
		})

		instanceFOU.eachRow(("SELECT VENDOR_CODE FROM VENDOR WHERE VENDOR_ID = (select VENDOR_PARENT_ID from vendor where mr_vendor_category_code IN ('ASSET_INSCO_BRANCH') AND VENDOR_NAME ='"+inscoBranchName+"')"), { def row ->

			inscoHOCode = row[0]
		})

		instanceFOU.eachRow(("select VENDOR_CODE from vendor where vendor_name ='"+inscoBranchName+"' AND MR_VENDOR_CATEGORY_CODE = 'ASSET_INSCO_BRANCH'"), { def row ->

			inscoBranchCode = row[0]
		})

		instanceLOS.eachRow(("SELECT MANUFACTURING_YEAR FROM APP_ASSET WHERE APP_ID = '"+appId+"'"), { def row ->

			year = row[0]
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

		inscoHORow=-1

		inscoHORow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'RuleAddCvg', inscoHOCode)+1

		def ruleAddtCvg = findTestData('DownloadRule/InsuranceAddtCvgRule')
		Integer assetAge = Integer.parseInt(GlobalVariable.BDYear)-Integer.parseInt(year)+(Integer.parseInt(yearNo)-1)

		int match = 0 , sameAssetCat = 0

		String tempAddtCvg = ""

		for(int i = inscoHORow;i<=ruleAddtCvg.getRowNumbers();i++){
			if(ruleAddtCvg.getValue(1,i)!=inscoHOCode && ruleAddtCvg.getValue(1,i)!=""){
				match=0
			}
			if(ruleAddtCvg.getValue(1,i)==inscoHOCode || (match>=1 && ruleAddtCvg.getValue(1,i)=="")){
				if(match==0){
					match = 1
				}
				if(ruleAddtCvg.getValue(4,i)==insAssetCategory){
					sameAssetCat=1
				}
				else if(ruleAddtCvg.getValue(4,i)!=insAssetCategory&&ruleAddtCvg.getValue(4,i)!=""){
					sameAssetCat=0
				}
				if(ruleAddtCvg.getValue(5,i)!=mainCvgType && ruleAddtCvg.getValue(5,i)!=""){
					match = 1
				}
				if(ruleAddtCvg.getValue(5,i)==mainCvgType||match==2 && ruleAddtCvg.getValue(5,i)==""){
					if(match==1){
						match = 2
					}
					if((ruleAddtCvg.getValue(2,i)==inscoBranchCode || ruleAddtCvg.getValue(2,i)=="-")
					&& (ruleAddtCvg.getValue(3,i)==region || ruleAddtCvg.getValue(3,i)=="-") && (ruleAddtCvg.getValue(4,i)==insAssetCategory || ruleAddtCvg.getValue(4,i)=="-" || (sameAssetCat==1 && ruleAddtCvg.getValue(4,i)==""))
					&& Long.parseLong(ruleAddtCvg.getValue(6,i))<=Long.parseLong(covAmt) && Long.parseLong(ruleAddtCvg.getValue(7,i))>=Long.parseLong(covAmt)){
						sameAssetCat = 1
						if(ruleAddtCvg.getValue(8,i)!=tempAddtCvg){
							addtCvg.add(ruleAddtCvg.getValue(8,i))
							tempAddtCvg = ruleAddtCvg.getValue(8,i)
						}
						if((ruleAddtCvg.getValue(10,i)!="0" && Integer.parseInt(ruleAddtCvg.getValue(9,i))<=assetAge
						&& Integer.parseInt(ruleAddtCvg.getValue(10,i))>=assetAge)||ruleAddtCvg.getValue(10,i)=="0"){
							if(ruleAddtCvg.getValue(11,i)=="PRCNT"){
								addtCvgType.add(ruleAddtCvg.getValue(8,i))
								addtPremiRate.add(ruleAddtCvg.getValue(13,i))
								sumInsuredAmt.add(ruleAddtCvg.getValue(14,i))
								println(ruleAddtCvg.getValue(8,i))
								println(ruleAddtCvg.getValue(13,i))
							}
							else if(ruleAddtCvg.getValue(11,i)=="AMT"){
								addtCvgType.add(ruleAddtCvg.getValue(8,i))
								addtPremiRate.add(ruleAddtCvg.getValue(16,i))
								sumInsuredAmt.add(ruleAddtCvg.getValue(14,i))
								println(ruleAddtCvg.getValue(8,i))
								println(ruleAddtCvg.getValue(16,i))
								println(ruleAddtCvg.getValue(14,i))
							}
						}
					}
				}
			}
			else if((ruleAddtCvg.getValue(1,i)=="" && ruleAddtCvg.getValue(2,i)=="" && ruleAddtCvg.getValue(3,i)=="")
			|| match==0){
				break
			}
		}
		result.put("AddtCvg",addtCvgType)
		result.put("AddtRate",addtPremiRate)
		result.put("SumInsuredAmt",sumInsuredAmt)
		result.put("AddCvgList",addtCvg)
		return result
	}

	@Keyword
	public checkAddtCvgCode(Sql instanceLOS, String addtName){
		String refcode
		instanceLOS.eachRow(("select ref_master_code from REF_MASTER_LOS where REF_MASTER_TYPE_CODE = 'INS_ADD_CVG_TYPE' and is_active=1 and ref_master_name = '"+addtName+"'"), { def row ->
			refcode = row[0]
		})
		return refcode
	}
}
