package financialData

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

public class verifyRate {


	@Keyword
	public verifyFinancialRate(Sql instanceLOS, String appNo){
		ArrayList<String> result = new ArrayList<>()
		String supplRate,efRate,efBhv

		String appId, lobCode, oriOfficeCode, tenor, asPrice, firstInstType, assetCondition, fullAssetCode

		instanceLOS.eachRow(("select app_id,lob_code, ori_office_code, tenor, mr_first_inst_type_code from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
			lobCode = row[1]
			oriOfficeCode = row[2]
			tenor = row[3]
			firstInstType = row[4]
		})

		instanceLOS.eachRow(("select TOTAL_ASSET_PRICE_AMT from APP_FIN_DATA where app_id = '"+appId+"'"), { def row ->
			asPrice = row[0]
		})

		instanceLOS.eachRow(("select full_asset_code, mr_asset_condition_code from app_asset where app_id = '"+appId+"'"), { def row ->
			fullAssetCode = row[0]
			assetCondition = row[1]
		})


		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.FinancialRateRulePath

		Integer lobCodeRow = -1
		lobCodeRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'CreditRate', lobCode)+1

		def ruleRate = findTestData('DownloadRule/FinancialRateRule')

		int matchLOB = 0, matchOfficeCode = 0, matchFullAsset = 0, matchAssetCondition = 0, matchFirstInst = 0

		for(int i = lobCodeRow;i<=ruleRate.getRowNumbers();i++){
			if(ruleRate.getValue(1,i)==lobCode || ruleRate.getValue(1,i)=="-"){
				matchLOB=1
			}
			else if(ruleRate.getValue(1,i)!=lobCode && ruleRate.getValue(1,i)!="" && ruleRate.getValue(1,i)!="-"){
				matchLOB=0
			}
			if(ruleRate.getValue(2,i)==oriOfficeCode || ruleRate.getValue(2,i)=="-"){
				matchOfficeCode=1
			}
			else if(ruleRate.getValue(2,i)!=oriOfficeCode && ruleRate.getValue(2,i)!="" && ruleRate.getValue(2,i)!="-"){
				matchOfficeCode=0
			}
			if(ruleRate.getValue(3,i)==fullAssetCode || ruleRate.getValue(3,i)=="-"){
				matchFullAsset=1
			}
			else if(ruleRate.getValue(3,i)!=fullAssetCode && ruleRate.getValue(3,i)!="" && ruleRate.getValue(3,i)!="-"){
				matchFullAsset=0
			}
			if(ruleRate.getValue(4,i)==assetCondition || ruleRate.getValue(4,i)=="-"){
				matchAssetCondition=1
			}
			else if(ruleRate.getValue(4,i)!=assetCondition && ruleRate.getValue(4,i)!="" && ruleRate.getValue(4,i)!="-"){
				matchAssetCondition=0
			}
			if(ruleRate.getValue(5,i)==firstInstType || ruleRate.getValue(5,i)=="-"){
				matchFirstInst=1
			}
			else if(ruleRate.getValue(5,i)!=firstInstType && ruleRate.getValue(5,i)!="" && ruleRate.getValue(5,i)!="-"){
				matchFirstInst=0
			}
			if(((ruleRate.getValue(1,i)=="" && matchLOB==1) || matchLOB==1) && ((ruleRate.getValue(2,i)=="" && matchOfficeCode==1) || matchOfficeCode==1)
			&& ((ruleRate.getValue(3,i)=="" && matchFullAsset==1) || matchFullAsset==1) && ((ruleRate.getValue(4,i)=="" && matchAssetCondition==1) || matchAssetCondition==1) && ((ruleRate.getValue(5,i)=="" && matchFirstInst==1) || matchFirstInst==1)
			&& Double.parseDouble(asPrice) > Double.parseDouble(ruleRate.getValue(6,i).replace(",","")) && Double.parseDouble(asPrice) <= Double.parseDouble(ruleRate.getValue(7,i).replace(",",""))
			&& Integer.parseInt(tenor) > Integer.parseInt(ruleRate.getValue(8,i)) && Integer.parseInt(tenor) <= Integer.parseInt(ruleRate.getValue(9,i))){
				supplRate = ruleRate.getValue(10,i)
				efRate = ruleRate.getValue(11,i)
				efBhv = ruleRate.getValue(12,i)
				break
			}
			else if((ruleRate.getValue(1,i)=="" && ruleRate.getValue(2,i)=="" && ruleRate.getValue(3,i)=="" && ruleRate.getValue(4,i)=="" && ruleRate.getValue(5,i)=="" && ruleRate.getValue(6,i)=="")){
				break
			}
		}

		result.add(supplRate)
		result.add(efRate)
		result.add(efBhv)

		return result
	}
}
