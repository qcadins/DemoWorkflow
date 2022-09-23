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

public class verifMainRate {
	
	public verifyMainPremiRate(Sql instanceLOS, Sql instanceFOU, String appNo, String inscoBranchName, String region, String covAmt){
		covAmt = Double.parseDouble(covAmt)
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> maincvgType = new ArrayList<>()

		ArrayList<String> mainRate = new ArrayList<>()

		String userDir = System.getProperty('user.dir')
		
		String filePathAssetCat = userDir+GlobalVariable.MainCovRulePath
		
		String filePath = userDir+GlobalVariable.AddtCovRulePath
		
		String appId, assetCode, assetCondition, assetCategoryCode, inscoHOCode, insAssetCategory, inscoBranchCode
		
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
		
		Integer inscoHORow = -1
		
		inscoHORow = (new excelGetRow.getRow()).getExcelRow(filePathAssetCat, 'AssetCategory', inscoHOCode)+1
		
		def ruleAssetCategory = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/InsAssetCategory')
		
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

		
		inscoHORow = (new excelGetRow.getRow()).getExcelRow(filePath, 'MainCvg', inscoHOCode)+1
		
		def ruleMainCvg = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/InsuranceMainCvgRule')
		
		int match = 0
		for(int i = inscoHORow;i<=ruleMainCvg.getRowNumbers();i++){
			if(ruleMainCvg.getValue(1,i)!=inscoHOCode && ruleMainCvg.getValue(1,i)!=""){
				match=0
			}
			if(ruleMainCvg.getValue(1,i)==inscoHOCode || (match==1 && ruleMainCvg.getValue(1,i)=="")){
				if(match==0){
					match = 1
				}
				if((ruleMainCvg.getValue(2,i)==inscoBranchCode || ruleMainCvg.getValue(2,i)=="-") 
					&& (ruleMainCvg.getValue(3,i)==region || ruleMainCvg.getValue(3,i)=="-") && (ruleMainCvg.getValue(4,i)==insAssetCategory || ruleMainCvg.getValue(4,i)=="-") 
					&& ruleMainCvg.getValue(5,i)<=covAmt && ruleMainCvg.getValue(6,i)>=covAmt){
						maincvgType.add(ruleMainCvg.getValue(7,i))
						mainRate.add(ruleMainCvg.getValue(10,i))
				}
			}
			else if((ruleMainCvg.getValue(1,i)=="" && ruleMainCvg.getValue(2,i)=="" && ruleMainCvg.getValue(3,i)=="")
			|| match==0){
				break
			}
		}
		
		result.put("MainCvg",maincvgType)
		result.put("MainRate",mainRate)
		return result
		
	}
}
