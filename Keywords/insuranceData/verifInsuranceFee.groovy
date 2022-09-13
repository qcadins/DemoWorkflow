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

public class verifInsuranceFee {


	@Keyword
	public verifFee(Sql instanceLOS, String appNo, String inscoBranchName,Sql instanceFOU){
		
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
		
		instanceLOS.eachRow(("select full_asset_code, asset_price_amt, mr_asset_condition_code from app_asset where app_id = '"+appId+"'"), { def row ->
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
		
		Integer inscoHORow = -1, insAssetCategoryRow = -1
		
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
		
		//check admin & stampduty
		insAssetCategoryRow = (new excelGetRow.getRowWithColumn()).getExcelRow(filePath, 'Fee', insAssetCategory,1)+1
		
		def ruleFee = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/InsuranceFeeRule')
		
		int match=0
		
		for(int i=insAssetCategoryRow;;i++){
			
			if(ruleFee.getValue(2,i)==insAssetCategory || (ruleFee.getValue(2,i)=="-" && match>=1)){
				
				if(match==0){
					
					match=1
				}
				
				if(assetCondition==ruleFee.getValue(5,i) || (match==2 && ruleFee.getValue(5,i)=="-")){
					
					match=2
					
					if(asPrice>Double.parseDouble(ruleFee.getValue(3,i)) && asPrice<=Double.parseDouble(ruleFee.getValue(4,i))){
						
						feeBhv.add(ruleFee.getValue(6,i))
						
						feeBhv.add(ruleFee.getValue(9,i))
						
						defAmt.add(ruleFee.getValue(7,i))
						
						defAmt.add(ruleFee.getValue(10,i))
						
						break
					}
					else{
						
						continue
					}
				}
				
				if(ruleFee.getValue(5,i+1)!="-"){
					
					match=1
				}
				
				if(ruleFee.getValue(2,i+1)!="-"){
					
					match=0
				}
			}
			else if(ruleFee.getValue(2,i)=="" && ruleFee.getValue(3,i)==""){
				
				break
			}

		}
		
		HashMap<String,ArrayList> result = new HashMap<>()
		
		result.put("Bhv",feeBhv)
		
		result.put("Amt",defAmt)
		
		return result
	}
}
