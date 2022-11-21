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
		lobCodeRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'CreditFee', lobCode)+1

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
					}
					else if(ruleFee.getValue(8,i)=="PRCNT"){
						fee.add(ruleFee.getValue(12,i))
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
		return result

	}

	@Keyword
	public checkFeeCode(Sql instanceLOS, String feeName){
		String feeCode
		instanceLOS.eachRow(("SELECT FEE_CODE FROM REF_FEE WHERE FEE_NAME = '"+feeName+"'"), { def row ->
			feeCode = row[0]
		})
		return feeCode
	}
}
