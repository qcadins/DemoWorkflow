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

public class verifyFee {

	//keyword verify financial fee
	@Keyword
	public verifyFinancialFee(Sql instanceLOS, String appNo){
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> listFee = new ArrayList<>()
		ArrayList<String> feeType = new ArrayList<>()
		ArrayList<String> fee = new ArrayList<>()
		//ArrayList<String> feePctg = new ArrayList<>()
		ArrayList<String> feeBhv = new ArrayList<>()
		ArrayList<String> feecapType = new ArrayList<>()
		ArrayList<String> feecap = new ArrayList<>()
		ArrayList<String> stdFee = new ArrayList<>()
		//		ArrayList<String> feecapPctg = new ArrayList<>()
		String appId, lobCode, oriOfficeCode, tenor, asPrice

		instanceLOS.eachRow(("select app_id,lob_code, ori_office_code, tenor from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
			lobCode = row[1]
			oriOfficeCode = row[2]
			tenor = row[3]
		})

		instanceLOS.eachRow(("select TOTAL_ASSET_PRICE_AMT from APP_FIN_DATA where app_id = '"+appId+"'"), { def row ->
			asPrice = row[0]
		})

		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.FinancialFeeRulePath

		Integer lobCodeRow = -1
		lobCodeRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'CreditFee', lobCode)+1

		def ruleFee = findTestData('DownloadRule/FinancialFeeRule')

		int matchLOB = 0, matchOfficeCode = 0

		for(int i = lobCodeRow;i<=ruleFee.getRowNumbers();i++){
			if(ruleFee.getValue(1,i)!=lobCode && ruleFee.getValue(1,i)!="" && ruleFee.getValue(1,i)!="-"){
				matchLOB=0
			}
			if(ruleFee.getValue(1,i)==lobCode || (matchLOB==1 && ruleFee.getValue(1,i)=="") || ruleFee.getValue(1,i)=="-"){
				if(matchLOB==0){
					matchLOB=1
				}
				if(ruleFee.getValue(2,i)==oriOfficeCode || ruleFee.getValue(2,i)=="-"){
					matchOfficeCode = 1
				}
				else if(ruleFee.getValue(2,i)!=oriOfficeCode && ruleFee.getValue(2,i)!="" && ruleFee.getValue(2,i)!="-"){
					matchOfficeCode = 0
				}
				if(((ruleFee.getValue(2,i)=="" && matchOfficeCode==1) || matchOfficeCode==1) && Integer.parseInt(tenor)>Integer.parseInt(ruleFee.getValue(3,i)) &&
				Integer.parseInt(tenor)<=Integer.parseInt(ruleFee.getValue(4,i)) && Double.parseDouble(asPrice)>Double.parseDouble(ruleFee.getValue(5,i).replace(",","")) &&
				Double.parseDouble(asPrice)<=Double.parseDouble(ruleFee.getValue(6,i).replace(",","")) ){
					listFee.add(ruleFee.getValue(7,i))
					feeType.add(ruleFee.getValue(8,i))
					if(ruleFee.getValue(8,i)=="AMT"){
						fee.add(ruleFee.getValue(10,i))
						stdFee.add(ruleFee.getValue(9,i))
					}
					else if(ruleFee.getValue(8,i)=="PRCNT"){
						fee.add(ruleFee.getValue(12,i))
						stdFee.add(ruleFee.getValue(11,i))
					}
					feeBhv.add(ruleFee.getValue(13,i))
					feecapType.add(ruleFee.getValue(18,i))
					if(ruleFee.getValue(18,i)=="AMT"){
						feecap.add(ruleFee.getValue(20,i))
					}
					else if(ruleFee.getValue(18,i)=="PRCNT"){
						feecap.add(ruleFee.getValue(19,i))
					}

				}
			}
			else if((ruleFee.getValue(1,i)=="" && ruleFee.getValue(2,i)=="" && ruleFee.getValue(3,i)=="" && ruleFee.getValue(4,i)=="" && ruleFee.getValue(5,i)=="" && ruleFee.getValue(6,i)=="")
			|| matchLOB==0){
				break
			}
		}

		result.put("listFee",listFee)
		result.put("feeType",feeType)
		result.put("fee",fee)
		result.put("feeBhv",feeBhv)
		result.put("feecapType",feecapType)
		result.put("feecap",feecap)
		result.put("stdFee",stdFee)
		return result

	}

	//	//keyword check standard fee from rule
	//	public checkStdFee(Sql instanceLOS, String appNo){
	//		HashMap<String,ArrayList> result = new HashMap<>()
	//		ArrayList<String> listFee = new ArrayList<>()
	//		ArrayList<String> feeType = new ArrayList<>()
	//		ArrayList<String> stdFee = new ArrayList<>()
	//		String appId, lobCode, oriOfficeCode, tenor, asPrice
	//
	//		instanceLOS.eachRow(("select app_id,lob_code, ori_office_code, tenor from app where app_no = '"+appNo+"'"), { def row ->
	//			appId = row[0]
	//			lobCode = row[1]
	//			oriOfficeCode = row[2]
	//			tenor = row[3]
	//		})
	//
	//		instanceLOS.eachRow(("select TOTAL_ASSET_PRICE_AMT from APP_FIN_DATA where app_id = '"+appId+"'"), { def row ->
	//			asPrice = row[0]
	//		})
	//
	//		String userDir = System.getProperty('user.dir')
	//		String filePath = userDir+GlobalVariable.FinancialFeeRulePath
	//
	//		Integer lobCodeRow = -1
	//		lobCodeRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'CreditFee', lobCode)+1
	//
	//		def ruleFee = findTestData('DownloadRule/FinancialFeeRule')
	//
	//		int matchLOB = 0, matchOfficeCode = 0
	//
	//		for(int i = lobCodeRow;i<=ruleFee.getRowNumbers();i++){
	//			if(ruleFee.getValue(1,i)!=lobCode && ruleFee.getValue(1,i)!="" && ruleFee.getValue(1,i)!="-"){
	//				matchLOB=0
	//			}
	//			if(ruleFee.getValue(1,i)==lobCode || (matchLOB==1 && ruleFee.getValue(1,i)=="") || ruleFee.getValue(1,i)=="-"){
	//				if(matchLOB==0){
	//					matchLOB=1
	//				}
	//				if(ruleFee.getValue(2,i)==oriOfficeCode || ruleFee.getValue(2,i)=="-"){
	//					matchOfficeCode = 1
	//				}
	//				else if(ruleFee.getValue(2,i)!=oriOfficeCode && ruleFee.getValue(2,i)!="" && ruleFee.getValue(2,i)!="-"){
	//					matchOfficeCode = 0
	//				}
	//				if(((ruleFee.getValue(2,i)=="" && matchOfficeCode==1) || matchOfficeCode==1) && Integer.parseInt(tenor)>Integer.parseInt(ruleFee.getValue(3,i)) &&
	//				Integer.parseInt(tenor)<=Integer.parseInt(ruleFee.getValue(4,i)) && Double.parseDouble(asPrice)>Double.parseDouble(ruleFee.getValue(5,i).replace(",","")) &&
	//				Double.parseDouble(asPrice)<=Double.parseDouble(ruleFee.getValue(6,i).replace(",","")) ){
	//					listFee.add(ruleFee.getValue(7,i))
	//					feeType.add(ruleFee.getValue(8,i))
	//					if(ruleFee.getValue(8,i)=="AMT"){
	//						stdFee.add(ruleFee.getValue(9,i))
	//					}
	//					else if(ruleFee.getValue(8,i)=="PRCNT"){
	//						stdFee.add(ruleFee.getValue(11,i))
	//					}
	//
	//				}
	//			}
	//			else if((ruleFee.getValue(1,i)=="" && ruleFee.getValue(2,i)=="" && ruleFee.getValue(3,i)=="" && ruleFee.getValue(4,i)=="" && ruleFee.getValue(5,i)=="" && ruleFee.getValue(6,i)=="")
	//			|| matchLOB==0){
	//				break
	//			}
	//		}
	//
	//		result.put("listFee",listFee)
	//		result.put("feeType",feeType)
	//		result.put("stdFee",stdFee)
	//
	//		return result
	//	}



	//keyword check fee code
	@Keyword
	public checkFeeCode(Sql instanceLOS, String feeName){
		String feeCode
		instanceLOS.eachRow(("SELECT FEE_CODE FROM REF_FEE WHERE FEE_NAME = '"+feeName+"'"), { def row ->
			feeCode = row[0]
		})
		return feeCode
	}

	//check nilai rounding product offering
	@Keyword
	public checkDefaultRounding(Sql instanceLOS, String poCode){
		int feeCode
		instanceLOS.eachRow(("select COMPNT_VALUE from PROD_OFFERING po join PROD p on po.PROD_ID = p.PROD_ID join PROD_H ph on ph.PROD_ID = p.PROD_ID join PROD_D pd on pd.PROD_H_ID = p.CURRENT_PROD_H_ID where REF_PROD_COMPNT_CODE = 'INST_ROUNDING' and PROD_OFFERING_CODE = '"+ poCode +"' and ph.PROD_STAT = 'ACT'"), { def row ->
			feeCode = row[0]
		})
		return feeCode
	}

	@Keyword
	public checkProvisionFeeDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'PROVISION_SOURCE' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}
	
	@Keyword
	public checkRateTypeDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'RATE_TYPE' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}
}