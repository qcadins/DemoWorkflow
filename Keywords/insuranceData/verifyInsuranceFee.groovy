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

public class verifyInsuranceFee {


	@Keyword
	public verifyFee(Sql instanceLOS, String appNo, String inscoBranchName,Sql instanceFOU){

		ArrayList<String> defAmt = new ArrayList<String>()

		ArrayList<String> feeBhv = new ArrayList<String>()

		String userDir = System.getProperty('user.dir')

		String filePath = userDir+GlobalVariable.InsFeeRulePath

		String filePathAssetCat = userDir+GlobalVariable.MainCovRulePath

		String appId, assetCode, assetCondition, assetCategoryCode, inscoHOCode, insAssetCategory

		String assetPrice

		instanceLOS.eachRow(("select app_id from app where app_no = '"+appNo+"'"), { def row ->

			appId = row[0]
		})

		instanceLOS.eachRow(("select full_asset_code, asset_price_amt+TOTAL_ACCESSORY_PRICE_AMT, mr_asset_condition_code from app_asset where app_id = '"+appId+"'"), { def row ->
			assetCode = row[0]

			assetPrice = row[1]

			assetCondition = row[2]
		})

		instanceLOS.eachRow(("select asset_category_code from ASSET_MASTER_LOS where FULL_ASSET_CODE = '"+assetCode+"'"), { def row ->

			assetCategoryCode = row[0]
		})

		instanceFOU.eachRow(("SELECT VENDOR_CODE FROM VENDOR WHERE VENDOR_ID = (select VENDOR_PARENT_ID from vendor where mr_vendor_category_code IN ('ASSET_INSCO_BRANCH') AND VENDOR_NAME ='"+inscoBranchName+"')"), { def row ->

			inscoHOCode = row[0]
		})

		BigDecimal asPrice = Double.parseDouble(assetPrice)

		Integer inscoHORow = -1, insHOCodeRow = -1

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

		//check admin & stampduty
		insHOCodeRow = (new customizeKeyword.getRow()).getRowWithColumn(filePathAssetCat, 'Fee', inscoHOCode,0)+1

		def ruleFee = findTestData('DownloadRule/InsuranceFeeRule')

		int matchInscoHO = 0, matchAssetCategory = 0, matchAssetCondition = 0

		for(int i=insHOCodeRow;i<=ruleFee.getRowNumbers();i++){
			println(i)
			if(ruleFee.getValue(1,i)!=inscoHOCode && ruleFee.getValue(1,i)!="" && ruleFee.getValue(1,i)!="-"){
				matchInscoHO=0
				
			}
			if(ruleFee.getValue(1,i)==inscoHOCode || (matchInscoHO==1 && ruleFee.getValue(1,i)=="") || ruleFee.getValue(1,i)=="-"){
				if(matchInscoHO==0){
					matchInscoHO=1
					
				}
				if(ruleFee.getValue(3,i)==insAssetCategory || ruleFee.getValue(3,i)=="-"){
					matchAssetCategory = 1
					
				}
				else if(ruleFee.getValue(3,i)!=insAssetCategory && ruleFee.getValue(3,i)!="" && ruleFee.getValue(3,i)!="-"){
					matchAssetCategory = 0
					
				}
				println(assetCondition+""+appNo)
				if(ruleFee.getValue(6,i)==assetCondition || ruleFee.getValue(6,i)=="-"){
					matchAssetCondition = 1
					
				}
				else if(ruleFee.getValue(6,i)!=assetCondition && ruleFee.getValue(6,i)!="" && ruleFee.getValue(6,i)!="-"){
					matchAssetCondition = 0
					

				}


				if(((ruleFee.getValue(1,i)=="" && matchInscoHO==1) || matchInscoHO==1) && ((ruleFee.getValue(3,i)=="" && matchAssetCategory==1) || matchAssetCategory==1) && ((ruleFee.getValue(6,i)=="" && matchAssetCondition==1) || matchAssetCondition==1)
				&& Double.parseDouble(ruleFee.getValue(4,i))<asPrice && Double.parseDouble(ruleFee.getValue(5,i))>=asPrice
				){
					feeBhv.add(ruleFee.getValue(7,i))

					feeBhv.add(ruleFee.getValue(10,i))

					defAmt.add(ruleFee.getValue(8,i))

					defAmt.add(ruleFee.getValue(11,i))

					
					break
				}
			}
			else if((ruleFee.getValue(1,i)=="" && ruleFee.getValue(2,i)=="" && ruleFee.getValue(3,i)=="" && ruleFee.getValue(4,i)=="" && ruleFee.getValue(5,i)=="" && ruleFee.getValue(6,i)=="")){
				
				break
			}

		}

		HashMap<String,ArrayList> result = new HashMap<>()

		result.put("Bhv",feeBhv)

		result.put("Amt",defAmt)

		return result
	}
}
